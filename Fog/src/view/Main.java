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
package view;

import java.util.HashMap;
import thread.ThreadOuvinte;
import thread.ThreadPublisher;

/**
 * Classe Main para a Fog
 */
public class Main {
    private static HashMap<String, Object> data_base; //Base de dados

    public static void main(String[] args) {
     
        data_base = new HashMap<>(); //Cria a instancia

        //Instancia a threadOuvinte, que recebe a quantidade de pacientes que devem ser listados
        new ThreadOuvinte(data_base, true, "tcp://broker.mqttdashboard.com:1883", null, null, "problema2/quantidadePaciente", 2);
        
        //Instancia a ThreadOuvinte, que recebe os dados dos dispositivos dos pacientes regionais
        new ThreadOuvinte(data_base, false, "tcp://localhost:1883", null, null, "problema2/dadosPaciente", 2);
        
        //Instancia a ThreadPublisher, que publica os dados dos N pacientes na núvem
        new ThreadPublisher().start();


    }
    
}
