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
package routes;

import java.util.HashMap;
import com.google.gson.Gson;
import model.Paciente;
import view.main;


public class PacienteRouter implements Router {

    public Object[] POST(Object body, HashMap data_base) {
        Object[] res = new Object[3];
        try {
            Gson gson = new Gson(); // Or use new GsonBuilder().create();
            Paciente paciente = gson.fromJson((String) body, Paciente.class);
            Boolean exists = data_base.containsKey(paciente.getCpf());
            if (!exists) {
                data_base.put(paciente.getCpf(), paciente);
                res[0] = "201";
                res[1] = "OK";
                res[2] = paciente.toString();

            } else {
                res[0] = "406";
                res[1] = "ERRO";
                res[2] = "Paciente jÃ¡ cadastrado";
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
            res[0] = "500";
            res[1] = "ERRO";
            res[2] = "Erro ao cadastrar paciente";
        }
        return res;
    }

    public Object[] GET(Object body, HashMap data_base) {
        Object[] res = new Object[3];
        try {
            Gson gson = new Gson(); // Or use new GsonBuilder().create();
            HashMap<String, String> entries = gson.fromJson((String) body, HashMap.class);
            String cpf = entries.get("cpf");
            main.publicador.publicar("problema2/pacienteMonitora", cpf.getBytes(), 0);

            Boolean exists = data_base.containsKey(cpf);
            if (exists) {
                Object row = data_base.get(cpf);
                System.out.println(row.toString());
                res[0] = "200";
                res[1] = "OK";
                res[2] = row.toString();

            } else {
                res[0] = "404";
                res[1] = "ERRO";
                res[2] = "Paciente nÃ£o encontrado";
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
            res[0] = "500";
            res[1] = "ERRO";
            res[2] = "Erro ao consultar paciente";
        }
        return res;
    }

    public Object[] PUT(Object body, HashMap data_base) {
        Object[] res = new Object[3];
        try {
            Gson gson = new Gson(); // Or use new GsonBuilder().create();
            Paciente paciente = gson.fromJson((String) body, Paciente.class);
            Paciente pacienteBD = (Paciente)data_base.get(paciente.getCpf());
            if (pacienteBD != null) {
                pacienteBD.setFreqCardiaca(paciente.getFreqCardiaca());
                pacienteBD.setPressaoArterial(paciente.getPressaoArterial());
                pacienteBD.setTemperatura(paciente.getTemperatura());
                pacienteBD.setSaturacao(paciente.getSaturacao());
                pacienteBD.setNome(paciente.getNome());
                data_base.put(paciente.getCpf(), pacienteBD);
                res[0] = "200";
                res[1] = "OK";
                res[2] = paciente.toString();

            } else {
                res[0] = "406";
                res[1] = "ERRO";
                res[2] = "Paciente nÃ£o cadastrado";
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
            res[0] = "500";
            res[1] = "ERRO";
            res[2] = "Erro ao atualizar dados";
        }
        return res;
    }

    public Object[] DELETE(Object body, HashMap data_base) {
        Object[] res = new Object[3];
        try {
            Gson gson = new Gson(); // Or use new GsonBuilder().create();
            HashMap<String, String> entries = gson.fromJson((String) body, HashMap.class);
            String cpf = entries.get("cpf");
            Boolean exists = data_base.containsKey(cpf);
            if (exists) {
                Object row = data_base.remove(cpf);
                res[0] = "200";
                res[1] = "OK";
                res[2] = row.toString();

            } else {
                res[0] = "404";
                res[1] = "ERRO";
                res[2] = "Paciente nÃ£o encontrado";
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
            res[0] = "500";
            res[1] = "ERRO";
            res[2] = "Erro ao deletar paciente";
        }
        return res;
    }
    
    public Object[] GETGravidade(Object body, HashMap data_base) {
        Object[] res = new Object[3];
        try {
            Gson gson = new Gson(); // Or use new GsonBuilder().create();
            HashMap<String, String> entries = gson.fromJson((String) body, HashMap.class);
            String cpf = entries.get("cpf");
            Boolean exists = data_base.containsKey(cpf);
            if (exists) {
                Object row = data_base.get(cpf);
                System.out.println(((Paciente)row).getGravidade());
                res[0] = "200";
                res[1] = "OK";
                res[2] = "{ gravidade: "+String.valueOf(((Paciente)row).getGravidade()) + "}";

            } else {
                res[0] = "404";
                res[1] = "ERRO";
                res[2] = "Paciente nÃ£o encontrado";
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
            res[0] = "500";
            res[1] = "ERRO";
            res[2] = "Erro ao consultar paciente";
        }
        return res;
    }
    
     public Object[] PUTGravidade(Object body, HashMap data_base) {
        Object[] res = new Object[3];
        try {
            Gson gson = new Gson(); // Or use new GsonBuilder().create();
            Paciente paciente = gson.fromJson((String) body, Paciente.class);
            System.out.println(paciente.toString());
            Paciente pacienteBD = (Paciente)data_base.get(paciente.getCpf());
            if (pacienteBD != null) {
                pacienteBD.setGravidade(paciente.getGravidade());
                data_base.put(paciente.getCpf(), pacienteBD);
                res[0] = "200";
                res[1] = "OK";
                res[2] = pacienteBD.toString();

            } else {
                res[0] = "406";
                res[1] = "ERRO";
                res[2] = "Paciente nÃ£o cadastrado";
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
            res[0] = "500";
            res[1] = "ERRO";
            res[2] = "Erro ao atualizar dados";
        }
        return res;
    }

}
