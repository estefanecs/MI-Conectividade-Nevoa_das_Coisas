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

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * Esta classe � para objetos do tipo Paciente, contendo seus atributos como
 * nome, cpf, temperatura, frequencia cardiaca, frequencia respirat�ria, press�o
 * arterial, satura��o do oxig�nio e o estado de gravidade do paciente.
 *
 */
public class Paciente implements Comparable<Paciente>, Comparator<Object>, Serializable {

    private String cpf;
    private String nome;
    private int pressaoArterial;
    private int freqCardiaca;
    private int saturacao;
    private int temperatura;
    private int gravidade;
    private int freqRespiratoria;

    /**
     * M�todo construtor para a classe Paciente
     *
     * @param cpf - cpf do paciente
     * @param nome - nome do paciente
     * @param pressaoArterial - presao arterial do paciente
     * @param freqCardiaca - frequ�ncia card�aca do paciente
     * @param freqRespiratoria - frequ�ncia respirat�ria do paciente
     * @param saturacao - satura��o do oxig�nio do paciente
     * @param temperatura - temperatura do paciente
     * @param gravidade - gravidade do paciente
     */
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

    /**
     * Metodo que retorna o cpf do paciente
     *
     * @return cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Metodo que altera o cpf do paciente
     *
     * @param cpf - novo cpf
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Metodo que retorna o nome do paciente
     *
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo que altera o nome do paciente
     *
     * @param nome - novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo que retorna a pressao do paciente
     *
     * @return pressaoArterial
     */
    public int getPressaoArterial() {
        return pressaoArterial;
    }

    /**
     * Metodo que altera a pressao arterial do paciente
     *
     * @param pressaoArterial - nova pressao arterial
     */
    public void setPressaoArterial(int pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
    }

    /**
     * Metodo que retorna a frequencia cardiaca do paciente
     *
     * @return freqCardiaca
     */
    public int getFreqCardiaca() {
        return freqCardiaca;
    }

    /**
     * Metodo que altera a frequencia cardiaca do paciente
     *
     * @param freqCardiaca - nova frequencia cardiaca
     */
    public void setFreqCardiaca(int freqCardiaca) {
        this.freqCardiaca = freqCardiaca;
    }

    /**
     * Metodo que retorna a frequencia respiratoria do paciente
     *
     * @return freqRespiratoria
     */
    public int getFreqRespiratoria() {
        return freqRespiratoria;
    }

    /**
     * Metodo que altera a frequencia respiratoria do paciente
     *
     * @param freqRespiratoria - nova frequencia respiratoria
     */
    public void setFreqRespiratoria(int freqRespiratoria) {
        this.freqRespiratoria = freqRespiratoria;
    }

    /**
     * Metodo que retorna a saturacao do oxigenio do paciente
     *
     * @return saturacao
     */
    public int getSaturacao() {
        return saturacao;
    }

    /**
     * Metodo que altera a saturacao do oxigenio do paciente
     *
     * @param saturacao - nova saturacao do oxigenio
     */
    public void setSaturacao(int saturacao) {
        this.saturacao = saturacao;
    }

    /**
     * Metodo que retorna a temperatua do paciente
     *
     * @return temperatura
     */
    public int getTemperatura() {
        return temperatura;
    }

    /**
     * Metodo que altera a temperatura do paciente
     *
     * @param temperatura - nova temperatura
     */
    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    /**
     * Metodo que retorna a gravidade do paciente
     *
     * @return gravidade
     */
    public int getGravidade() {
        return gravidade;
    }

    /**
     * Metodo que altera a gravidade do paciente
     *
     * @param gravidade - nova gravidade
     */
    public void setGravidade(int gravidade) {
        this.gravidade = gravidade;
    }

    /**
     * Metodo toString da classe, que retorna todos os valores de cada atributo
     * do paciente.
     *
     * @return String - dados do paciente
     */
    @Override
    public String toString() {
        return "{" + "\"cpf\": \"" + cpf + "\" , "
                + "\"nome\": \"" + nome + "\" ,"
                + " \"pressaoArterial\": \"" + pressaoArterial + "\","
                + " \"freqCardiaca\": \"" + freqCardiaca + "\" , "
                + "\"freqRespiratoria\": \"" + freqRespiratoria + "\" ,"
                + " \"saturacao\": \"" + saturacao + "\" , "
                + "\"temperatura\": \"" + temperatura + "\","
                + " \"gravidade\": \"" + gravidade + "\" }";
    }

    /**
     * Metodo que compara a gravidade do paciente
     *
     * @param p - paciente
     * @return int
     */
    @Override
    public int compareTo(Paciente p) {
        return p.gravidade - this.gravidade;
    }

    /**
     * Metodo que compara a gravidade de dois pacientes
     *
     * @param p1 - paciente 1
     * @param p2 - paciente 2
     * @return int
     */
    @Override
    public int compare(Object p1, Object p2) {
        return ((Paciente) p2).gravidade - ((Paciente) p1).gravidade;
    }

    /**
     * M�todo que comprara se um objeto � igual ao objeto da inst�ncia
     *
     * @param obj - objeto a ser comparado
     * @return true - se forem iguais
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) { //Se for o mesmo objeto da instancia
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) { //Se nao pertencer a mesma classe
            return false;
        }
        final Paciente other = (Paciente) obj;
        if (!Objects.equals(this.cpf, other.cpf)) { //Compara se possuem o mesmo cpf
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.cpf);
        return hash;
    }
}
