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
import thread.Sensor;
import com.github.javafaker.*;
/**
 * Esta classe faz a criação do paciente, a criação do publicador e fica sempre
 * atualizando os dados do paciente e enviando para o broker.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        for(int i=0; i < 10; i++){
            Faker faker = new Faker();
            String nome = faker.name().fullName();
            String cpf = faker.number().digits(11);
            String gravidade = faker.bool().bool() ? "normal" : "grave";
            new Sensor(cpf, nome, gravidade).start();
        }
    }


}
