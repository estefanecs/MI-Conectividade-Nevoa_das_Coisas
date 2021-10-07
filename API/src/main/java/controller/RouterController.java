/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import java.util.HashMap;
import routes.Router;
import routes.MedicoRouter;
import routes.PacienteRouter;

/**
 *
 * @author matheusnascimento
 */
public class RouterController {

    private Router route;

    public Object[] router(String url, String method, String body, HashMap data_base) {
        try {
            String[] urlSplit = url.split("\\?");
            String path = urlSplit[0];

            HashMap<String, String> params = new HashMap();
            if (urlSplit.length == 2) {
                urlSplit = urlSplit[1].split("\\&");

                for (int i = 0; i < urlSplit.length; i++) {
                    String[] entry = urlSplit[i].split("=");
                    params.put(entry[0], entry[1]);
                }
            }

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
