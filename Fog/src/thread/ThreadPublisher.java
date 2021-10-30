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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Editor;

/**
 * Classe que publica os dados de vários paciente no broker da nuvem.
 */
public class ThreadPublisher extends Thread {

    private String cpf, nome, gravidade;

    public ThreadPublisher() {
    }

    /**
     * Método run da thread, que busca a lista de pacientes que devem ser
     * enviados para a núvem
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
                //Cria o buffer de saída
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                ObjectOutputStream stream = new ObjectOutputStream(byteArray);
                System.out.println(ThreadOuvinte.getPacientes().size());
                //Salva a lista dos pacientes
                stream.writeObject(ThreadOuvinte.getPacientes().listarPacientesGraves());
                System.out.println("Quantidade Enviada: " + ThreadOuvinte.getPacientes().listarPacientesGraves().length);
                //Fecha o buffer de saída
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
