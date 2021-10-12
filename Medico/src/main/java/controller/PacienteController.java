/**
 * Componente Curricular: Módulo Integrado de Concorrência e Conectividade
 * Autor: Cleyton Almeida da Silva, Estéfane Carmo de Souza e Matheus Nascimento
 * Data: 07/10/2021
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
package controller;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import model.Paciente;

/**
 * Esta classe é para objetos do tipo PacienteControlador, e realiza a busca de
 * dados na API
 *
 */
public class PacienteController {

    public Paciente paciente;

    public PacienteController() {
        paciente = new Paciente();
    }

    /**
     * Método que faz a listagem de N pacientes cadastrados no sistema. Esse
     * método realiza a conexão com a API.
     *
     * @param urlServidor - endereço do servidor
     * @param quantidadePacientes - quantidade de pacientes que deseja lista
     * @return Paciente[]- um vetor com todos os pacientes
     */
    public Paciente[] listPaciente(String urlServidor, int quantidadePacientes) {
        try {
            //Cria a url
            URL url = new URL(urlServidor + "medico/pacientes?sort=true?quantidade=" + quantidadePacientes);
            //Realiza a conexão e configura os campos da requisição
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("Accept", "*/*");
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("Accept-Encoding", "gzip, deflate, br");
            connection.setRequestMethod("GET");
            connection.connect();
            //Cria o buffer de leitura
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder jsonBuilder = new StringBuilder();
            //Realiza a leitura
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close(); //Fecha o buffer de leitura
           // int responseCode = connection.getResponseCode();
            Gson gson = new Gson(); //Cria o Gson
            //Cria o vetor de pacientes, com os dados do Gson
            Paciente[] pacientes = gson.fromJson(jsonBuilder.toString(), Paciente[].class);
            //Retorna o vetor
            return pacientes; 
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }

    /**
     * Método que faz a busca de um paciente pelo cpf. Esse método realiza a
     * conexão com a API.
     *
     * @param urlServidor - endereço do servidor
     * @param json - cpf do paciente
     * @return Paciente - o paciente encontrado
     */
    public Paciente getPaciente(String urlServidor, String json) {
        try {
            //Cria a url
            URL url = new URL(urlServidor + "paciente?cpf=" + json);
            //Realiza a conexão e configura os campos da requisição
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("Accept", "*/*");
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("Accept-Encoding", "gzip, deflate, br");
            connection.setRequestMethod("GET");
            connection.connect();
            //Cria o buffer de leitura
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder jsonBuilder = new StringBuilder();
            //Realiza a leitura
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close(); //Fecha o buffer de leitura
            Gson gson = new Gson(); //Cria o Gson
            System.out.println(jsonBuilder.toString());
            
            //Cria o paciente com as informações do Gson
            Paciente paciente = gson.fromJson(jsonBuilder.toString(), Paciente.class);
            int responseCode = connection.getResponseCode();
            System.out.println("response code=" + responseCode);
            return paciente; //Retorna o paciente
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }

}
