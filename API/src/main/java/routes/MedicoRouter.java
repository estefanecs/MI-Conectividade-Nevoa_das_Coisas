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
