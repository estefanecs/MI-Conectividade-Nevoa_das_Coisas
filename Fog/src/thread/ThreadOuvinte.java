/**
 * Componente Curricular: M?dulo Integrado de Concorr?ncia e Conectividade
 * Autor: Cleyton Almeida da Silva, Est?fane Carmo de Souza e Matheus Nascimento
 * Data: 11/10/2021
 *
 * Declaro que este c?digo foi elaborado por n?s de forma colaborativa e
 * n?o cont?m nenhum trecho de c?digo de outro colega ou de outro autor,
 * tais como provindos de livros e apostilas, e p?ginas ou documentos
 * eletr?nicos da Internet. Qualquer trecho de c?digo de outra autoria que
 * uma cita??o para o  n?o a minha est? destacado com  autor e a fonte do
 * c?digo, e estou ciente que estes trechos n?o ser?o considerados para fins
 * de avalia??o. Alguns trechos do c?digo podem coincidir com de outros
 * colegas pois estes foram discutidos em sess?es tutorias.
 */
package thread;

import com.google.gson.Gson;
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

    private HashMap<String, Object> data_base;
    private static FilaPrioridade pacientes = new FilaPrioridade();
    private boolean cloud;
    
    public static FilaPrioridade getPacientes(){
        return pacientes;
    }


    public ThreadOuvinte(HashMap<String, Object> data_base, boolean cloud, String serverURI, String user, String password, String topic, int qos) {
        this.data_base = data_base;
        this.cloud = cloud;
        OuvinteInterno clienteMQTT = new OuvinteInterno(serverURI,user,password);
        clienteMQTT.iniciar();
        clienteMQTT.subscribe(qos, this, topic);
        
    }

    @Override
    public void messageArrived(String topic, MqttMessage mm) throws Exception {
        if(this.cloud){
            System.out.println("Thread ");
            FilaPrioridade.qtd_list(5);
            //FilaPrioridade.qtd_list(Integer.parseInt(new String(mm.getPayload())));
        }else{
            this.listeningSensor(mm);
        }
    }
    
    private void listeningSensor(MqttMessage mm){
        String body = new String(mm.getPayload());
        
        try{
            Gson gson = new Gson(); // Or use new GsonBuilder().create();
            Paciente paciente = gson.fromJson((String) body, Paciente.class);
            Paciente pacienteBD = (Paciente)data_base.get(paciente.getCpf());

            if (pacienteBD != null) {
                pacienteBD.setFreqCardiaca(paciente.getFreqCardiaca());
                pacienteBD.setPressaoArterial(paciente.getPressaoArterial());
                pacienteBD.setTemperatura(paciente.getTemperatura());
                pacienteBD.setSaturacao(paciente.getSaturacao());
                pacienteBD.setNome(paciente.getNome());
                pacienteBD.setGravidade(paciente.getGravidade());
                pacienteBD.setFreqRespiratoria(paciente.getFreqRespiratoria());
            }else{
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

class OuvinteInterno implements MqttCallbackExtended {

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
