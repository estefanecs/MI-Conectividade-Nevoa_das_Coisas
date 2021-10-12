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

/**
 * Esta classe é para objetos do tipo Paciente, contendo seus atributos como
 * nome, cpf, temperatura, frequencia cardiaca, frequencia respiratória, pressão
 * arterial, saturação do oxigênio e o estado de gravidade do paciente.
 *
 */
public class Paciente implements Comparable<Paciente>, Comparator<Object> {

    private String cpf;
    private String nome;
    private int pressaoArterial;
    private int freqCardiaca;
    private int saturacao;
    private int temperatura;
    private int gravidade;
    private int freqRespiratoria;

    /**
     * Método construtor para a classe Paciente
     *
     * @param cpf - cpf do paciente
     * @param nome - nome do paciente
     * @param pressaoArterial - presao arterial do paciente
     * @param freqCardiaca - frequência cardíaca do paciente
     * @param saturacao - saturação do oxigênio do paciente
     * @param temperatura - temperatura do paciente
     * @param gravidade - gravidade do paciente
     * @param freqRespiratoria - frequência respiratória do paciente
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
     * Método que altera o cpf do paciente
     *
     * @param cpf - novo cpf
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Método que retorna o nome do paciente
     *
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método que altera o nome do paciente
     *
     * @param nome - novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Método que retorna a pressao do paciente
     *
     * @return pressaoArterial
     */
    public int getPressaoArterial() {
        return pressaoArterial;
    }

    /**
     * Método que altera a pressão arterial do paciente
     *
     * @param pressaoArterial - nova pressao arterial
     */
    public void setPressaoArterial(int pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
    }

    /**
     * Método que retorna a frequencia cardiaca do paciente
     *
     * @return freqCardiaca
     */
    public int getFreqCardiaca() {
        return freqCardiaca;
    }

    /**
     * Método que altera a frequência cardíaca do paciente
     *
     * @param freqCardiaca - nova frequência cardíaca
     */
    public void setFreqCardiaca(int freqCardiaca) {
        this.freqCardiaca = freqCardiaca;
    }

    /**
     * Método que retorna a frequencia respiratoria do paciente
     *
     * @return freqRespiratoria
     */
    public int getFreqRespiratoria() {
        return freqRespiratoria;
    }

    /**
     * Método que altera a frequência respiratória do paciente
     *
     * @param freqRespiratoria - nova frequência respiratória
     */
    public void setFreqRespiratoria(int freqRespiratoria) {
        this.freqRespiratoria = freqRespiratoria;
    }

    /**
     * Método que retorna a saturacao do oxigenio do paciente
     *
     * @return saturacao
     */
    public int getSaturacao() {
        return saturacao;
    }

    /**
     * Método que altera a saturação do oxigênio do paciente
     *
     * @param saturacao - nova saturação do oxigênio
     */
    public void setSaturacao(int saturacao) {
        this.saturacao = saturacao;
    }

    /**
     * Método que retorna a temperatua do paciente
     *
     * @return temperatura
     */
    public int getTemperatura() {
        return temperatura;
    }

    /**
     * Método que altera a temperatura do paciente
     *
     * @param temperatura - nova temperatura
     */
    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    /**
     * Método que retorna a gravidade do paciente
     *
     * @return gravidade
     */
    public int getGravidade() {
        return gravidade;
    }

    /**
     * Método que altera a gravidade do paciente
     *
     * @param gravidade - nova gravidade
     */
    public void setGravidade(int gravidade) {
        this.gravidade = gravidade;
    }
    
     /**
     * Método toString da classe, que retorna todos os valores de cada atributo
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
                + "\"freqRespiratoria\": \"" + freqRespiratoria+"\" ,"
                + " \"saturacao\": \"" + saturacao + "\" , "
                + "\"temperatura\": \"" + temperatura + "\","
                + " \"gravidade\": \"" + gravidade + "\" }";
    }

     /**
     * Método que compara a gravidade do paciente
     *
     * @param p - paciente
     * @return int
     */
    @Override
    public int compareTo(Paciente p) {
        return p.gravidade - this.gravidade;
    }
    /**
     * Método que compara a gravidade de dois pacientes
     *
     * @param p1 - paciente 1
     * @param p2 - paciente 2
     * @return int
     */
    @Override
    public int compare(Object p1, Object p2) {
        return ((Paciente) p2).gravidade - ((Paciente) p1).gravidade;
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
