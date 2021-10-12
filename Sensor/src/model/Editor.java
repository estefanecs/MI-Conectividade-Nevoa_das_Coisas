/**
 * Componente Curricular: Módulo Integrado de Concorrência e Conectividade
 * Autor: Cleyton Almeida da Silva, Estéfane Carmo de Souza e Matheus Nascimento
 * Data: 04/10/2021
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
package model;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;


/**
 * Esta classe é para objetos do tipo Editor, um cliente do servidor MQTT. Contem atributos como o
 * endereço do servidor, o cliente e as opções de conexões do MQTT Exemplo de
 * uso:
 *
 * Editor editor= new Paciente("servidor");
 *
 */
public class Editor implements MqttCallbackExtended {

    private final String servidor; //URL do servidor
    private MqttClient client; //Cliente MQTT
    private final MqttConnectOptions mqttOptions; //Opções de conexões
 
    /**
     * Construtor da classe
     * @param servidor - endereço do servidor
     */
    public Editor(String servidor) {
        this.servidor = servidor;

        mqttOptions = new MqttConnectOptions();
        mqttOptions.setMaxInflight(200);
        mqttOptions.setConnectionTimeout(3);
        mqttOptions.setKeepAliveInterval(10);
        mqttOptions.setAutomaticReconnect(true);
        mqttOptions.setCleanSession(false);
    }
    
    /**
     * Método que inicia a execução do cliente editor
     */
    public void iniciar() {
        try {
            System.out.println("Conectando ao broker MQTT " + servidor);
            //Cria o cliente e inciia a conexão
            client = new MqttClient(servidor, String.format("cliente_editor_java_%d", System.currentTimeMillis()), new MqttDefaultFilePersistence(System.getProperty("java.io.tmpdir")));
            client.setCallback(this);
            client.connect(mqttOptions); 
        } catch (MqttException ex) { //Se não conectar
            System.out.println("Erro ao tentar conectar-se com o broker MQTT " + servidor + " - " + ex);
        }
    }
    
    /**
     * Método que finaliza a conexão com o broker MQTT apenas se houver cliente
     * e estiver conectado com o broker
     */
    public void finalizar() {
        if (client != null && client.isConnected()) { //Se cliente não for nulo e estiver conectado
            try {
                client.disconnect(); //Disconecta
                client.close(); 
            } catch (MqttException ex) {
                System.out.println("Erro ao tentar desconectar do broker MQTT - " + ex);
            }
        }
    }

    /**
     * Método que realiza a publicação de uma informação em um tópico
     * @param topico -topico a ser publicado 
     * @param informacao - informação
     * @param qos - variavel de controle de qualidade de mensagem
     */
    public void publicar(String topico, byte[] informacao, int qos) {
        publicar(topico, informacao, qos, false);
    }
    
    /**
     * **
     * Método que realiza a publicação de uma informação em um tópico
     * @param topico -topico a ser publicado 
     * @param mensagem - mensagem
     * @param qos - variavel de controle de qualidade de mensagem
     * @param retained 
     */
    public synchronized void publicar(String topico, byte[] mensagem, int qos, boolean retained) {
        try {
            if (client.isConnected()) { //Se o cliente estiver conectado ao broker MQTT
                client.publish(topico, mensagem, qos, retained); //publica a informação no tópico
                System.out.println(String.format("Informação publicada no tópico %s com sucesso", topico));
            } else { //Se estiver desconectado
                System.out.println("Não foi possivel publicar no tópico " + topico+", pois o editor está desconectado");
            }
        } catch (MqttException ex) { //Se houver erros ao publicar o tópico
            System.out.println("Erro ao publicar o tópico:" + topico + ": " + ex);
        }
    }

     /**
     * Método que sinaliza se a conexão com o broker foi perdida
     * @param thrwbl 
     */
    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("Editor: Conexão com o broker perdida -" + thrwbl);
    }
    
    /**
     * Método que indica se o editor conseguiu conectar-se ou reconectar-se com o broker
     * @param reconnect
     * @param servidor - endereço do servidor 
     */
    @Override
    public void connectComplete(boolean reconnect, String servidor) {
        System.out.println("Editor " + (reconnect ? "reconectado" : "conectado") + " com o broker " + servidor);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage mm) throws Exception {
    }



}
