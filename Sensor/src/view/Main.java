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

import thread.Sensor;
import com.github.javafaker.*;

/**
 * Esta classe faz a criação de 10 sensores de pacientes por execução
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        for(int i=0; i < 10; i++){
            Faker faker = new Faker(); //Gera os dados fake;
            String nome = faker.name().fullName(); //Salva o nome
            String cpf = faker.number().digits(11); //Salva o cpf
            String gravidade = faker.bool().bool() ? "normal" : "grave"; //Gera o padrão de gravidade
            new Sensor(cpf, nome, gravidade).start(); //Inicia o sensor
        }
    }


}
