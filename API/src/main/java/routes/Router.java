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

public interface Router {
    
    public Object[] GET(Object body, HashMap data_base);
    public Object[] POST(Object body, HashMap data_base);
    public Object[] PUT(Object body, HashMap data_base);
    public Object[] DELETE(Object body, HashMap data_base);
    
}
