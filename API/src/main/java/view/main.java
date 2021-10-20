/**
 * Componente Curricular: M�dulo Integrado de Concorr�ncia e Conectividade
 * Autor: Cleyton Almeida da Silva, Est�fane Carmo de Souza e Matheus Nascimento
 * Data: 11/10/2021
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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import thread.ThreadCliente;
import thread.ThreadOuvinte;


public class main {

    public static HashMap<String, Object> data_base;

    public static void main(String[] args) {
        data_base = new HashMap<String, Object>();
        ServerSocket serv = null;
        try {
            System.out.println("Incializando o servidor...");
            //Iniciliza o servidor
            //serv = new ServerSocket(8000);
            serv = new ServerSocket(Integer.valueOf(System.getenv("PORT")));

            System.out.println("Servidor iniciado, ouvindo a porta " + serv.getLocalPort());
            System.out.println("Host: " + serv.toString());
            //Aguarda conexões
            new ThreadOuvinte(data_base, "tcp://broker.mqttdashboard.com:1883", null, null, "problema2/dadosPaciente", 2);

            
            while (true) {
                Socket clie = serv.accept();
                System.out.println(clie.getInetAddress().getHostAddress());
                //Inicia thread do cliente

                new ThreadCliente(clie, data_base).start();
            }
        } catch (IOException e) {
            try {
                serv.close();
            } catch (IOException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                serv.close();
            } catch (IOException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
