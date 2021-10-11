/**
 * Componente Curricular: Módulo Integrado de Concorrência e Conectividade
 * Autor: 
 * Data: 
 *
 * Declaro que este código foi elaborado por mim de forma individual e
 * não contém nenhum trecho de código de outro colega ou de outro autor,
 * tais como provindos de livros e apostilas, e páginas ou documentos
 * eletrônicos da Internet. Qualquer trecho de código de outra autoria que
 * uma citação para o  não a minha está destacado com  autor e a fonte do
 * código, e estou ciente que estes trechos não serão considerados para fins
 * de avaliação. Alguns trechos do código podem coincidir com de outros
 * colegas pois estes foram discutidos em sessões tutorias.
 */
package controller;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import model.Paciente;

/**
 *
 * @author matheusnascimento
 */
public class PacienteController {

    public Paciente paciente;

    public PacienteController() {
        paciente = new Paciente();
    }

    public Paciente[] listPaciente(String baseUrl, int quantidadePacientes) {
        try {
            URL url = new URL(baseUrl + "medico/pacientes?sort=true&quantidade="+quantidadePacientes);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("Accept", "*/*");
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("Accept-Encoding", "gzip, deflate, br");
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder jsonBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();
            int responseCode = connection.getResponseCode();
            Gson gson = new Gson(); // Or use new GsonBuilder().create();

            Paciente[] pacientes = gson.fromJson(jsonBuilder.toString(), Paciente[].class);
            System.out.println("Client exiting");
            return pacientes;
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }

    public Paciente getPaciente(String baseUrl, String json) {
        try {
            URL url = new URL(baseUrl + "paciente?cpf=" + json);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("Accept", "*/*");
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("Accept-Encoding", "gzip, deflate, br");
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder jsonBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();
            Gson gson = new Gson(); // Or use new GsonBuilder().create();
            System.out.println(jsonBuilder.toString());

            Paciente paciente = gson.fromJson(jsonBuilder.toString(), Paciente.class);
            int responseCode = connection.getResponseCode();
            System.out.println("response code=" + responseCode);
            System.out.println("Client exiting");
            return paciente;
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }
    

}
