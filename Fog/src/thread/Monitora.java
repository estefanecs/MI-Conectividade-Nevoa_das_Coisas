/**
 * Componente Curricular: M�dulo Integrado de Concorr�ncia e Conectividade
 * Autor: Cleyton Almeida da Silva, Est�fane Carmo de Souza e Matheus Nascimento
 * Data: 23/10/2021
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
     * M�todo para responder com o cliente monitorado.
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
