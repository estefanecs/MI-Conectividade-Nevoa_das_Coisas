/**
 * Componente Curricular: Módulo Integrado de Concorrência e Conectividade
 * Autor: Cleyton Almeida da Silva, Estéfane Carmo de Souza e Matheus Nascimento
 * Data: 02/10/2021
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

import java.util.logging.Level;
import java.util.logging.Logger;
import model.Editor;
import model.Paciente;

/**
 * Esta classe faz a criação do paciente, a criação do publicador e fica sempre
 * atualizando os dados do paciente e enviando para o broker.
 */
public class Sensor extends Thread {
    private String cpf, nome, gravidade;

    /**
     * Método que cria um sensor
     * @param cpf - cpf do paciente
     * @param nome -nome do paciente
     * @param gravidade - gravidade do paciene
     */
    public Sensor(String cpf,String nome, String gravidade) {
        this.cpf = cpf;
        this.nome = nome;
        this.gravidade = gravidade;
    }
    
    @Override
    public void run() {
        //Cria o paciente com os nomes enviados para o sensor
        Paciente paciente = new Paciente(nome, cpf, gravidade);

        //Cria um publicador e inicia
        Editor publicador = new Editor("tcp://localhost:1883");
        publicador.iniciar();

        String mensagem;
        
        while (true) { //Fica constantemente enviando dados para o broker
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
            }
            paciente.atualizarSinaisVitais(); //Atualiza os sinais vitais do paciente
            mensagem = paciente.toString(); //Cria a mensagem com os dados do paciente
            publicador.publicar("problema2/dadosPaciente", mensagem.getBytes(), 0); //Envia os dados para o broker

        }
    }
}
