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
package routes;

import java.util.HashMap;
import com.google.gson.Gson;
import java.util.Iterator;
import model.Paciente;
import thread.ThreadOuvinte;


public class MedicoRouter implements Router {

    public Object[] GET(Object body, HashMap data_base) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    public Object[] POST(Object o, HashMap hm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Object[] PUT(Object o, HashMap hm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Object[] DELETE(Object o, HashMap hm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Object[] GETPatientes(Object body, HashMap data_base) {
        Object[] res = new Object[3]; //Response

        try {
            Gson gson = new Gson(); // Or use new GsonBuilder().create();

            HashMap<String, String> entries = gson.fromJson((String) body, HashMap.class);
            String sort = entries.get("sort");

//            Iterator i = data_base.values().iterator();
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append('[');

//            LinkedList<Paciente> listPatientes = new LinkedList<Paciente>();
//            Object row;
//            while (i.hasNext()) {
//                row = i.next();
//                if (row instanceof Paciente) {
//                    jsonBuilder.append(row.toString());
//                    if (i.hasNext()) {
//                        jsonBuilder.append(',');
//                    }
//                }
//
//            }
            Iterator i = ThreadOuvinte.pacientes();
            Object row;
            String quant = entries.get("quantidade");
            int quantidade = 0;
            if (quant != null) {
                quantidade = Integer.parseInt(quant);
            }
            if (quantidade > 0) {
                int temp = 0;
                System.out.println(i.hasNext());
                while (i.hasNext() && temp < quantidade) {
                    row = i.next();
                    if (row instanceof Paciente) {
                        jsonBuilder.append(row.toString());
                        if (i.hasNext()) {
                            jsonBuilder.append(',');
                        }
                    }
                    temp++;
                }
            } else {
                while (i.hasNext()) {
                    row = i.next();
                    if (row instanceof Paciente) {
                        jsonBuilder.append(row.toString());
                        if (i.hasNext()) {
                            jsonBuilder.append(',');
                        }
                    }

                }
            }

            jsonBuilder.append(']');
            System.out.println(data_base.size());
            res[0] = "200";
            res[1] = "OK";
            res[2] = jsonBuilder.toString();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            res[0] = "500";
            res[1] = "Erro";
            res[2] = "Erro ao buscar dados";
        }
        return res;
    }
}
