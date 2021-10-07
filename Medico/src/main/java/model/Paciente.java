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
package model;

import java.util.Comparator;

/**
 *
 * @author matheusnascimento
 */
public class Paciente implements Comparable<Paciente>, Comparator<Object> {

    private String cpf;
    private String nome;
    private int pressaoArterial;
    private int freqCardiaca;
    private int freqRespiratoria;
    private int saturacao;
    private int temperatura;
    private int gravidade;

    public Paciente(String cpf, String nome, int pressaoArterial, int freqCardiaca,int freqRespiratoria, int saturacao, int temperatura, int gravidade) {
        this.cpf = cpf;
        this.nome = nome;
        this.pressaoArterial = pressaoArterial;
        this.freqCardiaca = freqCardiaca;
        this.freqRespiratoria = freqRespiratoria;
        this.saturacao = saturacao;
        this.temperatura = temperatura;
        this.gravidade = gravidade;
    }
    
     public Paciente(String cpf, String nome, int pressaoArterial, int freqCardiaca, int saturacao, int temperatura, int gravidade) {
        this.cpf = cpf;
        this.nome = nome;
        this.pressaoArterial = pressaoArterial;
        this.freqCardiaca = freqCardiaca;
        this.saturacao = saturacao;
        this.temperatura = temperatura;
        this.gravidade = gravidade;
        this.freqRespiratoria= 0;
    }
     
    public Paciente(){
    }

    public Paciente(String cpf, String nome) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPressaoArterial() {
        return pressaoArterial;
    }

    public void setPressaoArterial(int pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
    }

    public int getFreqCardiaca() {
        return freqCardiaca;
    }

    public void setFreqCardiaca(int freqCardiaca) {
        this.freqCardiaca = freqCardiaca;
    }

    public int getFreqRespiratoria() {
        return freqRespiratoria;
    }

    public void setFreqRespiratoria(int freqRespiratoria) {
        this.freqRespiratoria = freqRespiratoria;
    }

    public int getSaturacao() {
        return saturacao;
    }

    public void setSaturacao(int saturacao) {
        this.saturacao = saturacao;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public int getGravidade() {
        return gravidade;
    }

    public void setGravidade(int gravidade) {
        this.gravidade = gravidade;
    }

    
    
  
    public String toString() {
        return "{" + "\"cpf\": \"" + cpf + "\" , "
                + "\"nome\": \"" + nome + "\" ,"
                + " \"pressaoArterial\": \"" + pressaoArterial + "\","
                + " \"freqCardiaca\": \"" + freqCardiaca  + "\" , "
                + " \"freqRespiratoria\": \"" + freqRespiratoria  + "\" , "
                + " \"saturacao\": \"" + saturacao  + "\" , "
                + "\"temperatura\": \"" + temperatura  + "\","
                + " \"gravidade\": \"" + gravidade  + "\" }";
    }

    public int compareTo(Paciente o) {
        return o.gravidade - this.gravidade;
    }

    public int compare(Object o1, Object o2) {
        return ((Paciente) o2).gravidade - ((Paciente) o1).gravidade;
    }

}
