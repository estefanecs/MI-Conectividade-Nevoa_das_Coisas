package paciente.problema2;

import java.util.Arrays;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;



/****
 * ESSA CLASE É SÓ A DO EDITOR EXEMPLO QUE TINHA NA INTERNET, AINDA NAO FUNCIONA
 * @author casa
 */
public class Editor implements MqttCallbackExtended {

    private final String serverURI;
    private MqttClient client;
    private final MqttConnectOptions mqttOptions;

    public Editor(String serverURI, String usuario, String senha) {
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
    
    public void iniciar() {
        try {
            System.out.println("Editor: Conectando no broker MQTT em " + serverURI);
            client = new MqttClient(serverURI, String.format("cliente_editor_java_%d", System.currentTimeMillis()), new MqttDefaultFilePersistence(System.getProperty("java.io.tmpdir")));
            client.setCallback(this);
            client.connect(mqttOptions);
        } catch (MqttException ex) {
            System.out.println("Editor: Erro ao se conectar ao broker mqtt " + serverURI + " - " + ex);
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
            System.out.println("Editor: Erro ao desconectar do broker mqtt - " + ex);
        }
    }

    public void publicar(String topic, byte[] payload, int qos) {
        publicar(topic, payload, qos, false);
    }

    public synchronized void publicar(String topic, byte[] payload, int qos, boolean retained) {
        try {
            if (client.isConnected()) {
                client.publish(topic, payload, qos, retained);
                System.out.println(String.format("Tópico %s publicado. %dB", topic, payload.length));
            } else {
                System.out.println("Cliente editor desconectado, não foi possível publicar o tópico " + topic);
            }
        } catch (MqttException ex) {
            System.out.println("Erro ao publicar " + topic + " - " + ex);
        }
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("Editor: Conexão com o broker perdida -" + thrwbl);
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        System.out.println("Editor: Cliente editor " + (reconnect ? "reconectado" : "conectado") + " com o broker " + serverURI);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage mm) throws Exception {
    }

}
