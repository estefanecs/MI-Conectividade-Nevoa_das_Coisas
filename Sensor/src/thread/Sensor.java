/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thread;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.Editor;
import model.Paciente;

/**
 *
 * @author matheusnascimento
 */
public class Sensor extends Thread {
    private String cpf, nome, gravidade;
    
    public Sensor(String cpf,String nome, String gravidade) {
        this.cpf = cpf;
        this.nome = nome;
        this.gravidade = gravidade;
    }
    
    @Override
    public void run() {
        Paciente paciente = new Paciente(nome, cpf, gravidade);

        //Cria um publicador e inicia
        Editor publicador = new Editor("tcp://broker.mqttdashboard.com:1883");
        publicador.iniciar();

        String mensagem;

        while (true) {
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
            }
            paciente.atualizarSinaisVitais(); //Atualiza os sinais vitais do paciente
            mensagem = paciente.toString(); //Cria a mensagem com os dados do paciente
            publicador.publicar("problema2/dadosPaciente", mensagem.getBytes(), 0); //Envia os dados para o broker

        }
    }
}
