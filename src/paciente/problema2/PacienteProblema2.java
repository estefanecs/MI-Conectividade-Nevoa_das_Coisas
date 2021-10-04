/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paciente.problema2;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author casa
 */
public class PacienteProblema2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
       System.out.println("Digite o nome do paanaciente");
       String nome= scanner.nextLine();
       System.out.println("Digite o cpf do paciente");
       String cpf = scanner.nextLine();
       System.out.println("Digite a tendencia do paciente: normal ou grave");
       String gravidade=scanner.nextLine();
       System.out.println(" "+nome+"  "+cpf+"  "+gravidade);
       Paciente paciente = new Paciente(nome,cpf,gravidade);
       System.out.println(paciente.toString());
       int i=0;
       while(i<25){
        paciente.atualizarSinaisVitais();
        System.out.println(paciente.toString());
        
        i++;
       }
    }

    
}
