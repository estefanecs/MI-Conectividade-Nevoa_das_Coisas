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

package util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RequisicaoHTTP {

    private String protocolo;
    private String recurso;
    private String metodo;
    private String body;
    private boolean manterViva = true;
    private long tempoLimite = 3000;
    private Map<String, List> cabecalhos;

    public static RequisicaoHTTP lerRequisicao(InputStream entrada) throws IOException {
        RequisicaoHTTP requisicao = new RequisicaoHTTP();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
        /* Lê a primeira linha
         contem as informaçoes da requisição
         */
        String linhaRequisicao = buffer.readLine();

        //quebra a string pelo espaço em branco
        String[] dadosReq = linhaRequisicao.split(" ");
        
        //pega o metodo
        requisicao.setMetodo(dadosReq[0]);
        //paga o caminho do arquivo
        requisicao.setRecurso(dadosReq[1]);
        //pega o protocolo
        requisicao.setProtocolo(dadosReq[2]);
        String dadosHeader = buffer.readLine();
        //Enquanto a linha nao for nula e nao for vazia
        while (dadosHeader != null && !dadosHeader.isEmpty()) {
            String[] linhaCabecalho = dadosHeader.split(":");
            requisicao.setCabecalho(linhaCabecalho[0], linhaCabecalho[1].trim().split(","));
            dadosHeader = buffer.readLine();
        }
        requisicao.body = "";
        int pilha = 0;  //Stack auxiliar
        String line;
        do{
            if(!buffer.ready())
                break;
            line = buffer.readLine();

            if(line == null || line.isEmpty()){
                pilha = 0;
            }
            else if(line.charAt(0) == '{'){
                pilha++;
            }
            else if(line.charAt(0) == '}'){
                pilha--;
            }
            requisicao.body = requisicao.body + line;            
        }while(pilha != 0);
        //se existir a chave Connection no cabeçalho
        if (requisicao.getCabecalhos().containsKey("Connection")) {
            //seta o manter viva a conexao se o connection for keep-alive
            requisicao.setManterViva(requisicao.getCabecalhos().get("Connection").get(0).equals("keep-alive"));
        }
        return requisicao;
    }

    /**
     * M�todo que altera o cabe�alho da requisicao
     * @param chave - chave 
     * @param valores - valores
     */
    public void setCabecalho(String chave, String... valores) {
        if (cabecalhos == null) {
            cabecalhos = new TreeMap();
        }
        cabecalhos.put(chave, Arrays.asList(valores));
    }

    /**
     * M�todo que retorna o protocolo da requisi��o
     * @return String - o protocolo
     */
    public String getProtocolo() {
        return protocolo;
    }

    /**
     * M�todo que altera o protocolo da requisicao
     * @param protocolo - novo protocolo
     */
    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    /**
     * M�todo que retorna o recurso da requisi��o
     * @return String - recurso
     */
    public String getRecurso() {
        return recurso;
    }
    
    /**
     * M�todo que altera o recurso da requisi��o
     * @param recurso - novo recurso
     */
    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }
    
    /**
     * M�todo que obt�m o m�todo da requisi��o
     * @return String - m�todo
     */
    public String getMetodo() {
        return metodo;
    }
    
    /**
     * M�todo que altera o m�todo da requisi��o
     * @param metodo - novo m�todo
     */
    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    /**
     * M�todo que verifica se deve manter a requisi��o viva
     * @return boolean
     */
    public boolean isManterViva() {
        return manterViva;
    }

    /**
     * M�todo que altera se a requisi��o deve se manter viva
     * @param manterViva 
     */
    public void setManterViva(boolean manterViva) {
        this.manterViva = manterViva;
    }

    /**
     * M�todo que retorna o tempo limite da requisi��o
     * @return long - o tempo
     */
    public long getTempoLimite() {
        return tempoLimite;
    }

    /**
     * M�todo que altera o tempo limite da requisi��o
     * @param tempoLimite - novo tempo
     */
    public void setTempoLimite(long tempoLimite) {
        this.tempoLimite = tempoLimite;
    }
    
    /**
     * M�todo que retorna a lista de cabe�alhos
     * @return Map - a lista
     */
    public Map<String, List> getCabecalhos() {
        return cabecalhos;
    }
    /**
     * M�todo que altera a lista de cabe�alhos
     * @param cabecalhos - nova lista
     */
    public void setCabecalhos(Map<String, List> cabecalhos) {
        this.cabecalhos = cabecalhos;
    }

    /**
     * M�todo que retorna o corpo da requisi��o
     * @return String - o corpo
     */
    public String getBody() {
        return body;
    }
    
    /**
     * M�todo que altera o corpo da requisi��o
     * @param body - novo corpo
     */
    public void setBody(String body) {
        this.body = body;
    }
    
    
}