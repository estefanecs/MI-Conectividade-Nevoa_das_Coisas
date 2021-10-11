/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paciente.problema2;

import java.util.Random;

/**
 * Esta classe é para objetos do tipo Paciente, contendo seus atributos como
 * nome, cpf, temperatura, frequencia cardiaca, frequencia respiratória, pressão
 * arterial, saturação do oxigênio e o estado de gravidade do paciente.
 *
 * Exemplo de uso:
 *
 * Paciente paciente= new Paciente("Nome do paciente","cpf");
 *
 */
public class Paciente {

    private String nome;
    private String cpf;
    private int temperatura;
    private int freqCardiaca;
    private int freqRespiratoria;
    private int pressao;
    private int satOxigenio;
    private String perfilGravidade;
    private int gravidade;
    private Random geradorNumeros;

    /**
     * Construtor da classe
     *
     * @param nome nome do paciente
     * @param cpf cpf do paciente
     */
    public Paciente(String nome, String cpf, String perfilGravidade) {
        this.nome = nome;
        this.cpf = cpf;
        this.perfilGravidade = perfilGravidade;
        geradorNumeros = new Random();
        this.atualizarSinaisVitais();
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
     * Metodo que retorna o cpf do paciente
     *
     * @return cpf
     */
    public String getCpf() {
        return cpf;
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
     * Método que retorna a frequencia cardiaca do paciente
     *
     * @return freqCardiaca
     */
    public int getFreqCardiaca() {
        return freqCardiaca;
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
     * Método que retorna a pressao do paciente
     *
     * @return pressao
     */
    public float getPressao() {
        return pressao;
    }

    /**
     * Método que retorna a saturacao do oxigenio do paciente
     *
     * @return satOxigenio
     */
    public int getSatOxigenio() {
        return satOxigenio;
    }

    /**
     * Método que retorna a perfilGravidade do paciente
     *
     * @return perfilGravidade
     */
    public String getPerfilGravidade() {
        return perfilGravidade;
    }

    /**
     * Método que retorna o indice de gravidade do paciente
     *
     * @return perfilGravidade
     */
    public int getGravidade() {
        return gravidade;
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
     * Método que altera o cpf do paciente
     *
     * @param cpf - novo cpf
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Método que atualiza os sinais de cada paciente conforme o perfil de
     * gravidade que possui. Além disso, calcula a gravidade baseando-se no
     * nível de risco que cada valor do sinal vital apresenta.
     */
    public void atualizarSinaisVitais() {
        this.gravidade = 0;
        if (this.perfilGravidade.equalsIgnoreCase("normal")) {
            this.sinaisVitaisNormais(); //Define os valores normais de cada sinal
        } else if (this.perfilGravidade.equalsIgnoreCase("grave")) {
            this.sinaisVitaisGraves(); //Define os valores graves de cada sinal
            this.calcularGravidade(); //Metodo que calcula a gravidade
        } else {
            this.sinaisVitaisNormais();
        }
    }

    /**
     * Metodo que define de forma randomica os valores de cada sinal final,
     * dentro de uma faixa de valores consideradoros normais.
     */
    private void sinaisVitaisNormais() {
        this.temperatura = 35 + this.geradorNumeros.nextInt(37 - 35 + 1); //Numero entre 35 e 37
        this.freqCardiaca = 60 + this.geradorNumeros.nextInt(100 - 60 + 1); //Numero entre 60 e 100
        this.freqRespiratoria = 16 + this.geradorNumeros.nextInt(20 - 16 + 1); //Numero entre 16 e 20
        this.pressao = 100 + this.geradorNumeros.nextInt(140 - 100 + 1); //Numero entre 100 e 160
        this.satOxigenio = 90 + this.geradorNumeros.nextInt(100 - 90 + 1); //Numero entre 90 e 100
    }

    /**
     * Metodo que define de forma randomica os valores de cada sinal final,
     * dentro de uma faixa de valores consideradoros graves.
     */
    private void sinaisVitaisGraves() {
        this.temperatura = 38 + this.geradorNumeros.nextInt(50 - 38 + 1); //Numero entre 38 e 50
        this.freqCardiaca = 101 + this.geradorNumeros.nextInt(200 - 101 + 1); //Numero entre 101 e 200
        this.freqRespiratoria = 21 + this.geradorNumeros.nextInt(100 - 21 + 1); //Numero entre 21 e 100
        this.pressao = this.geradorNumeros.nextInt(99); //Numero de 0 a 99
        this.satOxigenio = this.geradorNumeros.nextInt(89); //Numero de 0 a 89
    }

    /**
     * Metodo que analisa o valor de cada sinal vital e define a gravidade do
     * paciente. Para cada faixa de valores de um sinal vital, a pontuacao
     * podera ser 1 ou 3.
     */
    private void calcularGravidade() {
        //Define a gravidade conforme a temperatura
        int variation = this.temperatura - 36;
        this.gravidade += (variation < 0 ? -variation : variation);
        //Define a gravidade conforme a frequencia Cardiaca
        variation = this.freqCardiaca - 80;
        this.gravidade += (variation < 0 ? -variation : variation);
        //Define a gravidade conforme a frequencia respiratoria
        variation = this.freqRespiratoria - 25;
        this.gravidade += (variation < 0 ? -variation : variation);
        //Define a gravidade conforme a saturacao do oxigenio
        variation = this.satOxigenio - 95;
        this.gravidade += (variation < 0 ? -variation : variation);
        //Define a gravidade conforme a pressao
        if (this.pressao >= 81 && this.pressao < 100) {
            this.gravidade += 1; //Soma 1 na gravidade
        } else if (this.pressao > 0 && this.pressao <= 80) {
            this.gravidade += 3; //Soma 3 na gravidade
        }

    }

    @Override
    public String toString() {
        return "{" + "\"cpf\": \"" + cpf + "\" , "
                + "\"nome\": \"" + nome + "\" ,"
                + " \"pressaoArterial\": \"" + pressao + "\","
                + " \"freqCardiaca\": \"" + freqCardiaca + "\" , "
                + " \"saturacao\": \"" + satOxigenio + "\" , "
                + "\"temperatura\": \"" + temperatura + "\","
                + " \"gravidade\": \"" + gravidade + "\" }";
    }

}
