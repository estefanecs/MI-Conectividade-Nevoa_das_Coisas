/**
 * Componente Curricular: Módulo Integrado de Concorrência e Conectividade
 * Autor: Cleyton Almeida da Silva, Estéfane Carmo de Souza e Matheus Nascimento
 * Data: 23/10/2021
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Editor;
import model.Paciente;

/**
 * Classe que publica os dados de um paciente no broker da nuvem.
 */
public class Monitora {


    public Monitora() {
    }

    /**
     * Método para responder com o cliente monitorado.
     */
    public void publicar(String cpf) {

        // Verifica se o paciente existe na fog
        String mensagem;
        Paciente paciente = (Paciente) ThreadOuvinte.getData_base().get(cpf);
        if(paciente != null){
            
            //Cria um publicador e inicia
            Editor publicador = new Editor("tcp://broker.mqttdashboard.com:1883");
            publicador.iniciar();
            mensagem = paciente.toString(); //Cria a mensagem com os dados do paciente
            publicador.publicar("problema2/pacienteMonitorado", mensagem.getBytes(), 0); //Envia os dados para o broker
            System.out.println("Paciente monitorado: " + paciente.toString());
        }
    }
}
