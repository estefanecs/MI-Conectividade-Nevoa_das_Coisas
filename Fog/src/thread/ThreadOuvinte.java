/**
 * Componente Curricular: Módulo Integrado de Concorrência e Conectividade
 * Autor: Cleyton Almeida da Silva, Estéfane Carmo de Souza e Matheus Nascimento
 * Data: 11/10/2021
 *
 * Declaro que este código foi elaborado por nós de forma colaborativa e
 * não contém nenhum trecho de código de outro colega ou de outro autor,
 * tais como provindos de livros e apostilas, e páginas ou documentos
 * eletrônicos da Internet. Qualquer trecho de código de outra autoria que
 * uma citação para o  não a minha está destacado com  autor e a fonte do
 * código, e estou ciente que estes trechos não serão considerados para fins
 * de avaliação. Alguns trechos do código podem coincidir com de outros
 * colegas pois estes foram discutidos em sessões tutorias.
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
 * Thread que escuta tópicos de um broker
 */
public class ThreadOuvinte implements IMqttMessageListener{

    private static HashMap<String, Object> data_base = new HashMap<>(); //Base de dados
    private static FilaPrioridade pacientes = new FilaPrioridade(); //Fila de pacientes
    /**
     * Método que retorna a instância da fila
     * @return FilaPrioridade - instancia da fila
     */
    public static FilaPrioridade getPacientes(){
        return pacientes;
    }

    /**
     * Método que retorna a instância da HashMap
     * @return data_base - instancia da HashMap
     */
    public static HashMap<String, Object> getData_base() {
        return data_base;
    }
    
    /**Thread responsável por receber as mensagens enviadas à fog.
     * @param serverURI URI para o broker MQTT
     * @param user Identificador de usuário.
     * @param password Senha de conexão.
     * @param topic Tópico da mensagem.
     * @param qos Nível de conexão.
     */
    public ThreadOuvinte(String serverURI, String user, String password, String topic, int qos) {
        OuvinteInterno clienteMQTT = new OuvinteInterno(serverURI,user,password);
        clienteMQTT.iniciar();
        clienteMQTT.subscribe(qos, this, topic);
        
    }

    /**
     * Método executado quando uma mensagem é recebida do Broker.
     * @param topic Tópico em que a mensagem foi enviada.
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
     * Metódo executado quando a fog recebe uma mensagem proveniente do sensor.
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
            //Caso o paciente já esteja conectado à fog, ele é atualizado.
            if (pacienteBD != null) {
                pacienteBD.setFreqCardiaca(paciente.getFreqCardiaca());
                pacienteBD.setPressaoArterial(paciente.getPressaoArterial());
                pacienteBD.setTemperatura(paciente.getTemperatura());
                pacienteBD.setSaturacao(paciente.getSaturacao());
                pacienteBD.setNome(paciente.getNome());
                pacienteBD.setGravidade(paciente.getGravidade());
                pacienteBD.setFreqRespiratoria(paciente.getFreqRespiratoria());
            }else{
                //Adiciona o paciente à base de dados da fog.
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
 * Encarrega-se da conexão com o broker MQTT.
 * @author Cleyton
 */
class OuvinteInterno implements MqttCallbackExtended {

    private final String serverURI;
    private MqttClient client;
    private final MqttConnectOptions mqttOptions;

    /**Thread responsável por receber as mensagens enviadas à fog.
     * @param serverURI URI para o broker MQTT
     * @param usuario Identificador de usuário.
     * @param senha Senha de conexão.
     */
    public OuvinteInterno(String serverURI, String usuario, String senha) {
        this.serverURI = serverURI;
        //Configurações MQTT.
        mqttOptions = new MqttConnectOptions();
        mqttOptions.setMaxInflight(200);
        mqttOptions.setConnectionTimeout(3);
        mqttOptions.setKeepAliveInterval(10);
        mqttOptions.setAutomaticReconnect(true);
        mqttOptions.setCleanSession(false);

        //Configura o usuário caso haja.
        if (usuario != null && senha != null) {
            mqttOptions.setUserName(usuario);
            mqttOptions.setPassword(senha.toCharArray());
        }
    }

    /**
     * Configura a subscrição no tópico.
     * @param qos nível de conexão.
     * @param gestorMensagemMQTT objeto para receber as mensagens MQTT.
     * @param topicos Lista de tópicos em que a fog será inscrita.
     * @return Objeto para acompanhar as mensagens assíncronas recebidas.
     */
    public IMqttToken subscribe(int qos, IMqttMessageListener gestorMensagemMQTT, String... topicos) {
        if (client == null || topicos.length == 0) {
            return null;
        }
        int tamanho = topicos.length;
        int[] qoss = new int[tamanho];
        //Lista de listeners para os diversos tópicos.
        IMqttMessageListener[] listners = new IMqttMessageListener[tamanho];
        //Configura os qoss para cada tópico.
        for (int i = 0; i < tamanho; i++) {
            qoss[i] = qos;
            listners[i] = gestorMensagemMQTT;
        }
        try {//Realiza a inscrição no tópico.
            return client.subscribeWithResponse(topicos, qoss, listners);
        } catch (MqttException ex) {
            System.out.println(String.format("Ouvinte: Erro ao se inscrever nos tópicos %s - %s", Arrays.asList(topicos), ex));
            return null;
        }
    }

    /**
     * Método para remover a inscrição nos tópicos passados.
     * @param topicos Tópicos para remover a inscrição.
     */
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

    /**
     * Inicializa a conexão do cliente com o Broker.
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
     * Finaliza a conexão do cliente com o broker.
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
