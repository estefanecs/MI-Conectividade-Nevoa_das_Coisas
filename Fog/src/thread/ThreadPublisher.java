/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thread;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Editor;
import model.Paciente;

/**
 *
 * @author matheusnascimento
 */
public class ThreadPublisher extends Thread {
    private String cpf, nome, gravidade;
    
    public ThreadPublisher() {

    }
    
    @Override
    public void run() {

        //Cria um publicador e inicia
        Editor publicador = new Editor("tcp://broker.mqttdashboard.com:1883");
        publicador.iniciar();

        String mensagem;

        while (true) {
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadPublisher.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                ObjectOutputStream stream = new ObjectOutputStream(byteArray);
                System.out.println(ThreadOuvinte.getPacientes().size());
                stream.writeObject(ThreadOuvinte.getPacientes().listarPacientesGraves());
                System.out.println("Quantidade Enviada: "+ ThreadOuvinte.getPacientes().listarPacientesGraves().length);
                stream.flush();
                stream.close();
                byte[] bytes = byteArray.toByteArray();
                publicador.publicar("problema2/dadosPaciente", bytes, 0); //Envia os dados para o broker

            } catch (IOException ex) {
                Logger.getLogger(ThreadPublisher.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
