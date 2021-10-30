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

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RespostaHTTP {

    private String protocolo;
    private String codigoResposta;
    private String mensagem;
    private byte[] conteudoResposta;
    private Map<String, List> cabecalhos;
    private OutputStream saida;

    public RespostaHTTP() {

    }

    public RespostaHTTP(String protocolo, String codigoResposta, String mensagem) {
        this.protocolo = protocolo;
        this.codigoResposta = codigoResposta;
        this.mensagem = mensagem;
    }

    /**
     * Envia os dados da resposta ao cliente.
     *
     * @throws IOException
     */
    public void enviar() throws IOException {
        //escreve o headers em bytes
        saida.write(montaCabecalho());
        if(conteudoResposta != null)
            saida.write(conteudoResposta);          //escreve o conteudo em bytes
        //encerra a resposta
        saida.flush();
    }

    /**
     * Insere um item de cabeçalho no mapa
     *
     * @param chave
     * @param valores lista com um ou mais valores para esta chave
     */
    public void setCabecalho(String chave, String... valores) {
        if (cabecalhos == null) {
            cabecalhos = new TreeMap();
        }
        cabecalhos.put(chave, Arrays.asList(valores));
    }

    /**
     * pega o tamanho da resposta em bytes
     *
     * @return retorna o valor em bytes do tamanho do conteudo da resposta
     * convertido em string
     */
    public String getTamanhoResposta() {
        if(conteudoResposta != null)
            return getConteudoResposta().length + "";
        else
            return "0";
    }

    /**
     * converte o cabecalho em string.
     *
     * @return retorna o cabecalho em bytes
     */
    private byte[] montaCabecalho() {
        return this.toString().getBytes();
    }

    /**
     * M�todo que retorna o conte�do da resposta
     * @return byte[] - conteudo
     */
    public byte[] getConteudoResposta() {
        return conteudoResposta;
    }
    
    /**
     * M�todo que altera o conte�do da resposta.
     * @param conteudoResposta - novo conte�do
     */
    public void setConteudoResposta(byte[] conteudoResposta) {
        this.conteudoResposta = conteudoResposta;
    }
    
    /**
     * M�todo que retorna o protoloco da resposta
     * @return String - protocolo
     */
    public String getProtocolo() {
        return protocolo;
    }

    /**
     * M�todo que altera o protocolo da resposta
     * @param protocolo - novo protocolo
     */
    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    /** 
     * M�todo que retorna o c�digo da resposta
     * @return String - codigo
     */
    public String getCodigoResposta() {
        return codigoResposta;
    }

    /**
     * M�todo que altera o codigo de resposta
     * @param codigoResposta - novo codigo
     */
    public void setCodigoResposta(String codigoResposta) {
        this.codigoResposta = codigoResposta;
    }

    /**
     * M�todo que obt�m a mensagem da resposta
     * @return String - mensagem
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * M�todo que altera a mensagem da resposta
     * @param mensagem - nova mensagem
     */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    /**
     * M�todo que retorna a lista de cabe�alhos da resposta
     * @return Map - lista
     */
    public Map<String, List> getCabecalhos() {
        return cabecalhos;
    }

    /**
     * M�todo que altera a lista de cabe�alhos da resposta
     * @param cabecalhos - nova lista
     */
    public void setCabecalhos(Map<String, List> cabecalhos) {
        this.cabecalhos = cabecalhos;
    }

    /**
     * M�todo que obt�m o buffer de sa�da
     * @return OutputStream
     */
    public OutputStream getSaida() {
        return saida;
    }

    /**
     * M�todo que altera o buffer de sa�da
     * @param saida - novo buffer
     */
    public void setSaida(OutputStream saida) {
        this.saida = saida;
    }
    
    /**
     * M�todo que retorna os dados da reposta HTTP
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(protocolo).append(" ").append(codigoResposta).append(" ").append(mensagem).append("\r\n");
        for (Map.Entry<String, List> entry : cabecalhos.entrySet()) {
            str.append(entry.getKey());
            String stringCorrigida = Arrays.toString(entry.getValue().toArray()).replace("[", "").replace("]", "");
            str.append(": ").append(stringCorrigida).append("\r\n");
        }
        str.append("\r\n");
        return str.toString();
    }
    
}
