/**
 * Componente Curricular: M�dulo Integrado de Concorr�ncia e Conectividade
 * Autor: Cleyton Almeida da Silva, Est�fane Carmo de Souza e Matheus Nascimento
 * Data: 11/10/2021
 *
 * Declaro que este c�digo foi elaborado por n�s de forma colaborativa e
 * n�o cont�m nenhum trecho de c�digo de outro colega ou de outro autor,
 * tais como provindos de livros e apostilas, e p�ginas ou documentos
 * eletr�nicos da Internet. Qualquer trecho de c�digo de outra autoria que
 * uma cita��o para o  n�o a minha est� destacado com  autor e a fonte do
 * c�digo, e estou ciente que estes trechos n�o ser�o considerados para fins
 * de avalia��o. Alguns trechos do c�digo podem coincidir com de outros
 * colegas pois estes foram discutidos em sess�es tutorias.
 */
package thread;

import com.google.gson.Gson;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import model.Paciente;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import util.FilaPrioridade;

public class ThreadOuvinte implements IMqttMessageListener{

    private HashMap<String, Object> data_base; //Dados dos pacientes presente no server.
    private static FilaPrioridade pacientes = new FilaPrioridade(); //Fila de prioridade para processar os dados recebidos.
    public static Iterator pacientes (){
        return pacientes.getIterator();
    }

    public static FilaPrioridade getPacientes() {
        return pacientes;
    }


    public static void setPacientes(FilaPrioridade pacientes) {
        ThreadOuvinte.pacientes = pacientes;
    }

    /**
     * Thread para receber as informa��es do Broker.
     * @param data_base dados dos pacientes.
     * @param serverURI URI para o Broker MQTT.
     * @param user Usu�rio para conex�o ao Broker.
     * @param password Senha para conex�o ao Broker.
     * @param topic T�pico de conex�o.
     * @param qos N�veo de qos de comunica��o.
     */
    public ThreadOuvinte(HashMap<String, Object> data_base, String serverURI, String user, String password, String topic, int qos) {
        this.data_base = data_base;
        OuvinteInterno clienteMQTT = new OuvinteInterno(serverURI,user,password);
        clienteMQTT.iniciar();
        clienteMQTT.subscribe(qos, this, topic);
        
    }

    /**
     * M�todo executado quando uma mensagem � recebida do Broker.
     * @param topic T�pico de recebimento da mensagem.
     * @param mm Dados da mensagem.
     * @throws Exception 
     */
    @Override
    public void messageArrived(String topic, MqttMessage mm) throws Exception {
        if(topic.equals("problema2/dadosPaciente")){
            listOfFog(mm, data_base);
        }
        else {
            String body = new String(mm.getPayload());
            Gson gson = new Gson(); // Or use new GsonBuilder().create();
            Paciente paciente = gson.fromJson((String) body, Paciente.class);
            updatePaciente(data_base, paciente);
        }
    }
    
    /**
     * M�todo para atualiza��o dos dados do paciente.
     * @param data_base base de dados do servidor.
     * @param newPaciente O Paciente a ser atualizado/inserido.
     */
    private static void updatePaciente(HashMap data_base, Paciente newPaciente){
        Paciente pacienteBD = (Paciente)data_base.get(newPaciente.getCpf());
            if (pacienteBD != null) {
                pacienteBD.setFreqCardiaca(newPaciente.getFreqCardiaca());
                pacienteBD.setPressaoArterial(newPaciente.getPressaoArterial());
                pacienteBD.setTemperatura(newPaciente.getTemperatura());
                pacienteBD.setSaturacao(newPaciente.getSaturacao());
                pacienteBD.setNome(newPaciente.getNome());
                pacienteBD.setGravidade(newPaciente.getGravidade());
                pacienteBD.setFreqRespiratoria(newPaciente.getFreqRespiratoria());
                data_base.put(newPaciente.getCpf(), pacienteBD);

            }else{
                data_base.put(newPaciente.getCpf(), newPaciente);
            }
    }
    
    /**
     * Lista os pacientes a partir das v�rias FOGs.
     * @param mm Dados da mensagem recebida.
     * @param data_base Dados de pacientes presentes no servidor.
     * @throws IOException
     * @throws ClassNotFoundException caso n�o seja poss�vel fazer o casting para Paciente.
     */
    private static void listOfFog(MqttMessage mm, HashMap data_base) throws IOException, ClassNotFoundException{
        ObjectInputStream objStream = new ObjectInputStream(new ByteArrayInputStream(mm.getPayload()));
        Paciente[] pacientesStream = (Paciente[])objStream.readObject();
        System.out.println("Quantidade recebida: " + pacientesStream.length);
        for (Paciente pacientesStream1 : pacientesStream) {
            System.out.println(pacientesStream1);
            pacientes.remove(pacientesStream1);
            pacientes.add(pacientesStream1);
            updatePaciente(data_base, pacientesStream1);
        }
    }
}

class OuvinteInterno implements MqttCallbackExtended {
    
    //M�todos para conex�o ao Broker MQTT.

    private final String serverURI;
    private MqttClient client;
    private final MqttConnectOptions mqttOptions;

    public OuvinteInterno(String serverURI, String usuario, String senha) {
        this.serverURI = serverURI;

        mqttOptions = new MqttConnectOptions();
        mqttOptions.setMaxInflight(200);
        mqttOptions.setConnectionTimeout(3);
        mqttOptions.setKeepAliveInterval(10);
        mqttOptions.setAutomaticReconnect(true);
        mqttOptions.setCleanSession(false);

        if (usuario != null && senha != null) {
            mqttOptions.setUserName(usuario);
            mqttOptions.setPassword(senha.toCharArray());
        }
    }

    public IMqttToken subscribe(int qos, IMqttMessageListener gestorMensagemMQTT, String... topicos) {
        if (client == null || topicos.length == 0) {
            return null;
        }
        int tamanho = topicos.length;
        int[] qoss = new int[tamanho];
        IMqttMessageListener[] listners = new IMqttMessageListener[tamanho];

        for (int i = 0; i < tamanho; i++) {
            qoss[i] = qos;
            listners[i] = gestorMensagemMQTT;
        }
        try {
            return client.subscribeWithResponse(topicos, qoss, listners);
        } catch (MqttException ex) {
            System.out.println(String.format("Ouvinte: Erro ao se inscrever nos tópicos %s - %s", Arrays.asList(topicos), ex));
            return null;
        }
    }

    public void unsubscribe(String... topicos) {
        if (client == null || !client.isConnected() || topicos.length == 0) {
            return;
        }
        try {
            client.unsubscribe(topicos);
        } catch (MqttException ex) {
            System.out.println(String.format("Ouvinte: Erro ao se desinscrever no tópico %s - %s", Arrays.asList(topicos), ex));
        }
    }

    public void iniciar() {
        try {
            System.out.println("Ouvinte: Conectando no broker MQTT em " + serverURI);
            client = new MqttClient(serverURI, String.format("cliente_java_%d", System.currentTimeMillis()), new MqttDefaultFilePersistence(System.getProperty("java.io.tmpdir")));
            client.setCallback((MqttCallback) this);
            client.connect(mqttOptions);
        } catch (MqttException ex) {
            System.out.println("Ouvinte: Erro ao se conectar ao broker mqtt " + serverURI + " - " + ex);
        }
    }

    public void finalizar() {
        if (client == null || !client.isConnected()) {
            return;
        }
        try {
            client.disconnect();
            client.close();
        } catch (MqttException ex) {
            System.out.println("Ouvinte: Erro ao desconectar do broker mqtt - " + ex);
        }
    }


    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("Ouvinte: Conexão com o broker perdida -" + thrwbl);
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        System.out.println("Cliente ouvinte " + (reconnect ? "reconectado" : "conectado") + " com o broker " + serverURI);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage mm) throws Exception {
    }

}
