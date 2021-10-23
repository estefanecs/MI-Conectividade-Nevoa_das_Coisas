/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package view;

import java.util.HashMap;
import thread.ThreadOuvinte;
import thread.ThreadPublisher;

/**
 *
 * @author matheusnascimento
 */
public class Main {
    private static HashMap<String, Object> data_base;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        data_base = new HashMap<>();

        //From cloud
        new ThreadOuvinte(data_base, true, "tcp://broker.mqttdashboard.com:1883", null, null, "problema2/quantidadePaciente", 2);
        
        // From sensor
        new ThreadOuvinte(data_base, false, "tcp://localhost:1883", null, null, "problema2/dadosPaciente", 2);
        
        new ThreadPublisher().start();


    }
    
}
