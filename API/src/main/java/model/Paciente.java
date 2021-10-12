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
package model;

import java.util.Comparator;
import java.util.Objects;


public class Paciente implements Comparable<Paciente>, Comparator<Object> {

    private String cpf;
    private String nome;
    private int pressaoArterial;
    private int freqCardiaca;
    private int saturacao;
    private int temperatura;
    private int gravidade;
    private int freqRespiratoria;

    public Paciente(String cpf, String nome, int pressaoArterial, int freqCardiaca, int saturacao, int temperatura, int gravidade) {
        this.cpf = cpf;
        this.nome = nome;
        this.pressaoArterial = pressaoArterial;
        this.freqCardiaca = freqCardiaca;
        this.saturacao = saturacao;
        this.temperatura = temperatura;
        this.gravidade = gravidade;
    }

    
    
    public Paciente(String cpf, String nome, int pressaoArterial, int freqCardiaca, int saturacao, int temperatura, int gravidade, int freqRespiratoria) {
        this.cpf = cpf;
        this.nome = nome;
        this.pressaoArterial = pressaoArterial;
        this.freqCardiaca = freqCardiaca;
        this.saturacao = saturacao;
        this.temperatura = temperatura;
        this.gravidade = gravidade;
        this.freqRespiratoria = freqRespiratoria;
    }

    @Override
    public String toString() {
        return "{" + "\"cpf\": \"" + cpf + "\" , "
                + "\"nome\": \"" + nome + "\" ,"
                + " \"pressaoArterial\": \"" + pressaoArterial + "\","
                + " \"freqCardiaca\": \"" + freqCardiaca + "\" , "
                + "\"freqRespiratoria\": \"" + freqRespiratoria+"\" ,"
                + " \"saturacao\": \"" + saturacao + "\" , "
                + "\"temperatura\": \"" + temperatura + "\","
                + " \"gravidade\": \"" + gravidade + "\" }";
    }

    public int compareTo(Paciente o) {
        return o.gravidade - this.gravidade;
    }

    public int compare(Object o1, Object o2) {
        return ((Paciente) o2).gravidade - ((Paciente) o1).gravidade;
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

    public int getFreqRespiratoria() {
        return freqRespiratoria;
    }

    public void setFreqRespiratoria(int freqRespiratoria) {
        this.freqRespiratoria = freqRespiratoria;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.cpf);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Paciente other = (Paciente) obj;
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        return true;
    }
    
    
}
