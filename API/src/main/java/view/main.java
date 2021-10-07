/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Paciente;
import thread.ThreadCliente;

/**
 *
 * @author matheusnascimento
 */
public class main {

    public static HashMap<String, Object> data_base;

    public static void main(String[] args) {
        data_base = new HashMap<String, Object>();
        ServerSocket serv = null;
        try {
            //Converte o parametro recebido para int (número da porta)
            System.out.println("Incializando o servidor...");
            //Iniciliza o servidor
            serv = new ServerSocket(8000);
            //serv = new ServerSocket(Integer.valueOf(System.getenv("PORT")));

            System.out.println("Servidor iniciado, ouvindo a porta " + serv.getLocalPort());
            System.out.println("Host: " + serv.toString());
            //Aguarda conexões

            while (true) {
                Socket clie = serv.accept();
                System.out.println(clie.getInetAddress().getHostAddress());
                //Inicia thread do cliente

                new ThreadCliente(clie, data_base).start();
            }
        } catch (Exception e) {
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
