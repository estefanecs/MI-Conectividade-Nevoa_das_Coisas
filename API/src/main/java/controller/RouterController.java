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
package controller;

import com.google.gson.Gson;
import java.util.HashMap;
import routes.Router;
import routes.MedicoRouter;
import routes.PacienteRouter;

public class RouterController {

    private Router route;

    /**
     * Controlador de rotas.
     * @param url url da rota.
     * @param method m�todo http.
     * @param body conte�do da mensagem.
     * @param data_base base de dados do servidor.
     * @return 
     */
    public Object[] router(String url, String method, String body, HashMap data_base) {
        System.out.println(url);
        try {
            //Divide a url por par�metros "\?"
            String[] urlSplit = url.split("\\?");
            //Define o path como o primeiro par�metro da url.
            String path = urlSplit[0];
            //Mapeia os par�metros e seus valores.
            HashMap<String, String> params = new HashMap();
            if (urlSplit.length == 2) {
                urlSplit = urlSplit[1].split("\\&");

                for (int i = 0; i < urlSplit.length; i++) {
                    String[] entry = urlSplit[i].split("=");
                    params.put(entry[0], entry[1]);
                }
            }
            //Define a rota recebida e sua respectiva resposta.
            Gson gson = new Gson();
            String queryParams = gson.toJson(params);
            if (path.equals("/paciente")) {
                route = new PacienteRouter();
                if (method.equals("POST")) {
                    return route.POST(body, data_base);
                } else if (method.equals("PUT")) {
                    return route.PUT(body, data_base);
                } else if (method.equals("GET")) {
                    return route.GET(queryParams, data_base);
                } else if (method.equals("DELETE")) {
                    return route.DELETE(queryParams, data_base);
                }
            }
            if (path.equals("/paciente/status")) {
                route = new PacienteRouter();
                if (method.equals("GET")) {
                    return ((PacienteRouter) route).GETGravidade(queryParams, data_base);
                } else if (method.equals("PUT")) {
                    return ((PacienteRouter) route).PUTGravidade(body, data_base);
                }

            } else if (path.equals("/medico/pacientes")) {
                route = new MedicoRouter();
                if (method.equals("GET")) {
                    return ((MedicoRouter) route).GETPatientes(queryParams, data_base);
                }

            }
            String[] responseNotFoud = {"404", "Not found", ""};
            return responseNotFoud;
        } catch (Exception e) {
            String[] responseNotFoud = {"404", "Not found", ""};
            return responseNotFoud;
        }
    }
}
