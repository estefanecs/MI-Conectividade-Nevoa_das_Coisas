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
package view;

import java.util.Scanner;
import model.Editor;
import model.Paciente;

/**
 * Esta classe faz a criação do paciente, a criação do publicador e fica sempre
 * atualizando os dados do paciente e enviando para o broker.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in); //Cria o scanner
        //Ler os dados do paciente: nome, cpf e perfil de gravidade
        System.out.println("Digite o nome do paciente");
        String nome = scanner.nextLine();
        System.out.println("Digite o cpf do paciente");
        String cpf = scanner.nextLine();
        System.out.println("Digite a tendencia do paciente: normal ou grave");
        String gravidade = scanner.nextLine();
         //Cria a instância do paciente com os dados lidos anteriormente
        Paciente paciente = new Paciente(nome, cpf, gravidade);

        //Cria um publicador e inicia
        Editor publicador = new Editor("tcp://broker.mqttdashboard.com:1883");
        publicador.iniciar();

        String mensagem;

        while (true) {
            Thread.sleep(1000);
            paciente.atualizarSinaisVitais(); //Atualiza os sinais vitais do paciente
            mensagem = paciente.toString(); //Cria a mensagem com os dados do paciente
            System.out.println(mensagem); //Printa a mensagem
            publicador.publicar("problema2/dadosPaciente", mensagem.getBytes(), 0); //Envia os dados para o broker

        }
    }

}
