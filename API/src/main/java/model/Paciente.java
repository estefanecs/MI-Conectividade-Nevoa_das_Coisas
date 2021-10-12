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
package model;

import java.util.Comparator;


public class Paciente implements Comparable<Paciente>, Comparator<Object> {

    private String cpf;
    private String nome;
    private String pressaoArterial;
    private String freqCardiaca;
    private String saturacao;
    private float temperatura;
    private int gravidade;

    public Paciente(String cpf, String nome, String pressaoArterial, String freqCardiaca, String saturacao, float temperatura, int gravidade) {
        this.cpf = cpf;
        this.nome = nome;
        this.pressaoArterial = pressaoArterial;
        this.freqCardiaca = freqCardiaca;
        this.saturacao = saturacao;
        this.temperatura = temperatura;
        this.gravidade = gravidade;
    }

    public Paciente(String cpf, String nome, String pressaoArterial, String freqCardiaca, String saturacao, float temperatura) {
        this.cpf = cpf;
        this.nome = nome;
        this.pressaoArterial = pressaoArterial;
        this.freqCardiaca = freqCardiaca;
        this.saturacao = saturacao;
        this.temperatura = temperatura;
    }

    public Paciente(String cpf) {
        this.cpf = cpf;
    }

    public Paciente() {
        this.nome = "Anonimo";
    }

    public Paciente(String cpf, String nome) {
        this.nome = nome;
        this.cpf = cpf;
        this.freqCardiaca = "0";
        this.gravidade = 0;
        this.pressaoArterial = "0";
        this.saturacao = "0";
        this.temperatura = 0;
    }

    public String getSaturacao() {
        return saturacao;
    }

    public void setSaturacao(String saturacao) {
        this.saturacao = saturacao;
    }

    public int getGravidade() {
        return gravidade;
    }

    public void setGravidade(int gravidade) {
        this.gravidade = gravidade;
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

    public String getPressaoArterial() {
        return pressaoArterial;
    }

    public void setPressaoArterial(String altura) {
        this.pressaoArterial = altura;
    }

    public String getFreqCardiaca() {
        return freqCardiaca;
    }

    public void setFreqCardiaca(String freqCardiaca) {
        this.freqCardiaca = freqCardiaca;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    @Override
    public String toString() {
        return "{" + "\"cpf\": \"" + cpf + "\" , "
                + "\"nome\": \"" + nome + "\" ,"
                + " \"pressaoArterial\": \"" + pressaoArterial  + "\","
                + " \"freqCardiaca\": \"" + freqCardiaca  + "\" , "
                + " \"saturacao\": \"" + saturacao   + "\" , "
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
