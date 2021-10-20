/**
 * Componente Curricular: Módulo Integrado de Concorrência e Conectividade
 * Autor: Cleyton Almeida da Silva, Estéfane Carmo de Souza e Matheus Nascimento
 * Data: 11/10/2021
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
            //Aguarda conexÃµes
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
