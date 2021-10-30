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
        
        //Instancia a ThreadPublisher, que publica os dados dos N pacientes na n�vem
        new ThreadPublisher().start();


    }
    
}
