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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Editor;

/**
 * Classe que publica os dados de v�rios paciente no broker da nuvem.
 */
public class ThreadPublisher extends Thread {

    private String cpf, nome, gravidade;

    public ThreadPublisher() {
    }

    /**
     * M�todo run da thread, que busca a lista de pacientes que devem ser
     * enviados para a n�vem
     */
    @Override
    public void run() {

        //Cria um publicador e inicia
        Editor publicador = new Editor("tcp://broker.mqttdashboard.com:1883");
        publicador.iniciar();

        while (true) {
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadPublisher.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //Cria o buffer de sa�da
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                ObjectOutputStream stream = new ObjectOutputStream(byteArray);
                System.out.println(ThreadOuvinte.getPacientes().size());
                //Salva a lista dos pacientes
                stream.writeObject(ThreadOuvinte.getPacientes().listarPacientesGraves());
                System.out.println("Quantidade Enviada: " + ThreadOuvinte.getPacientes().listarPacientesGraves().length);
                //Fecha o buffer de sa�da
                stream.flush();
                stream.close();
                //Transforma a lista de pacientes obtidos em byte
                byte[] mensagem = byteArray.toByteArray();
                publicador.publicar("problema2/dadosPaciente", mensagem, 0); //Envia os dados para o broker

            } catch (IOException ex) {
                Logger.getLogger(ThreadPublisher.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
