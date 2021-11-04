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
import java.util.Arrays;
import java.util.HashMap;
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

/**
 * Thread que escuta t�picos de um broker
 */
public class ThreadOuvinte implements IMqttMessageListener{

    private static HashMap<String, Object> data_base = new HashMap<>(); //Base de dados
    private static FilaPrioridade pacientes = new FilaPrioridade(); //Fila de pacientes
    /**
     * M�todo que retorna a inst�ncia da fila
     * @return FilaPrioridade - instancia da fila
     */
    public static FilaPrioridade getPacientes(){
        return pacientes;
    }

    /**
     * M�todo que retorna a inst�ncia da HashMap
     * @return data_base - instancia da HashMap
     */
    public static HashMap<String, Object> getData_base() {
        return data_base;
    }
    
    /**Thread respons�vel por receber as mensagens enviadas � fog.
     * @param serverURI URI para o broker MQTT
     * @param user Identificador de usu�rio.
     * @param password Senha de conex�o.
     * @param topic T�pico da mensagem.
     * @param qos N�vel de conex�o.
     */
    public ThreadOuvinte(String serverURI, String user, String password, String topic, int qos) {
        OuvinteInterno clienteMQTT = new OuvinteInterno(serverURI,user,password);
        clienteMQTT.iniciar();
        clienteMQTT.subscribe(qos, this, topic);
        
    }

    /**
     * M�todo executado quando uma mensagem � recebida do Broker.
     * @param topic T�pico em que a mensagem foi enviada.
     * @param mm Mensagem recebida.
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage mm) throws Exception {
        String body = new String(mm.getPayload());
        switch (topic) {
            case "problema2/pacienteMonitora":
                new Monitora().publicar(body);
                break;
            case "problema2/quantidadePaciente":
                //FilaPrioridade.qtd_list(5);
                FilaPrioridade.qtd_list(Integer.parseInt(body));
                break;
            default:
                this.listeningSensor(mm);
                break;
        }
    }
    
    /**
     * Met�do executado quando a fog recebe uma mensagem proveniente do sensor.
     * @param mm Mensagem recebida.
     */
    private void listeningSensor(MqttMessage mm){
        String body = new String(mm.getPayload());
        System.out.println("Dados recebebidos do sensor: "+body);
        try{
            Gson gson = new Gson(); // Or use new GsonBuilder().create();
            Paciente paciente = gson.fromJson((String) body, Paciente.class);
            //Busca o paciente na base de dados da fog.
            Paciente pacienteBD = (Paciente)data_base.get(paciente.getCpf());
            //Caso o paciente j� esteja conectado � fog, ele � atualizado.
            if (pacienteBD != null) {
                pacienteBD.setFreqCardiaca(paciente.getFreqCardiaca());
                pacienteBD.setPressaoArterial(paciente.getPressaoArterial());
                pacienteBD.setTemperatura(paciente.getTemperatura());
                pacienteBD.setSaturacao(paciente.getSaturacao());
                pacienteBD.setNome(paciente.getNome());
                pacienteBD.setGravidade(paciente.getGravidade());
                pacienteBD.setFreqRespiratoria(paciente.getFreqRespiratoria());
            }else{
                //Adiciona o paciente � base de dados da fog.
                data_base.put(paciente.getCpf(), paciente);
                pacienteBD = paciente;
            }
            pacientes.remove(pacienteBD);
            pacientes.add(pacienteBD);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }

}
/**
 * Encarrega-se da conex�o com o broker MQTT.
 * @author Cleyton
 */
class OuvinteInterno implements MqttCallbackExtended {

    private final String serverURI;
    private MqttClient client;
    private final MqttConnectOptions mqttOptions;

    /**Thread respons�vel por receber as mensagens enviadas � fog.
     * @param serverURI URI para o broker MQTT
     * @param usuario Identificador de usu�rio.
     * @param senha Senha de conex�o.
     */
    public OuvinteInterno(String serverURI, String usuario, String senha) {
        this.serverURI = serverURI;
        //Configura��es MQTT.
        mqttOptions = new MqttConnectOptions();
        mqttOptions.setMaxInflight(200);
        mqttOptions.setConnectionTimeout(3);
        mqttOptions.setKeepAliveInterval(10);
        mqttOptions.setAutomaticReconnect(true);
        mqttOptions.setCleanSession(false);

        //Configura o usu�rio caso haja.
        if (usuario != null && senha != null) {
            mqttOptions.setUserName(usuario);
            mqttOptions.setPassword(senha.toCharArray());
        }
    }

    /**
     * Configura a subscri��o no t�pico.
     * @param qos n�vel de conex�o.
     * @param gestorMensagemMQTT objeto para receber as mensagens MQTT.
     * @param topicos Lista de t�picos em que a fog ser� inscrita.
     * @return Objeto para acompanhar as mensagens ass�ncronas recebidas.
     */
    public IMqttToken subscribe(int qos, IMqttMessageListener gestorMensagemMQTT, String... topicos) {
        if (client == null || topicos.length == 0) {
            return null;
        }
        int tamanho = topicos.length;
        int[] qoss = new int[tamanho];
        //Lista de listeners para os diversos t�picos.
        IMqttMessageListener[] listners = new IMqttMessageListener[tamanho];
        //Configura os qoss para cada t�pico.
        for (int i = 0; i < tamanho; i++) {
            qoss[i] = qos;
            listners[i] = gestorMensagemMQTT;
        }
        try {//Realiza a inscri��o no t�pico.
            return client.subscribeWithResponse(topicos, qoss, listners);
        } catch (MqttException ex) {
            System.out.println(String.format("Ouvinte: Erro ao se inscrever nos t�picos %s - %s", Arrays.asList(topicos), ex));
            return null;
        }
    }

    /**
     * M�todo para remover a inscri��o nos t�picos passados.
     * @param topicos T�picos para remover a inscri��o.
     */
    public void unsubscribe(String... topicos) {
        if (client == null || !client.isConnected() || topicos.length == 0) {
            return;
        }
        try {
            client.unsubscribe(topicos);
        } catch (MqttException ex) {
            System.out.println(String.format("Ouvinte: Erro ao se desinscrever no t�pico %s - %s", Arrays.asList(topicos), ex));
        }
    }

    /**
     * Inicializa a conex�o do cliente com o Broker.
     */
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

    /**
     * Finaliza a conex�o do cliente com o broker.
     */
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
        System.out.println("Ouvinte: Conex�o com o broker perdida -" + thrwbl);
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
