/**
 * Componente Curricular: Módulo Integrado de Concorrência e Conectividade
 * Autor: Cleyton Almeida da Silva, Estéfane Carmo de Souza e Matheus Nascimento
 * Data: 30/09/2021
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

import java.util.Random;

/**
 * Esta classe é para objetos do tipo Paciente, contendo seus atributos como nome,
 * cpf, temperatura, frequencia cardiaca, frequencia respiratória, pressão arterial,
 * saturação do oxigênio, perfil de gravidade e o estado de gravidade do paciente.
 *
 * Exemplo de uso:
 *
 * Paciente paciente= new Paciente("Nome do paciente","cpf","perfilGravidade");
 *
 */
public class Paciente {

    private String nome;
    private String cpf;
    private int temperatura;
    private int freqCardiaca;
    private int freqRespiratoria;
    private int pressaoArterial;
    private int saturacao;
    private String perfilGravidade;
    private int gravidade;
    private Random geradorNumeros;

    /**
     * Construtor da classe
     *
     * @param nome nome do paciente
     * @param cpf cpf do paciente
     * @param perfilGravidade perfil de gravidade do paciente
     */
    public Paciente(String nome, String cpf, String perfilGravidade) {
        this.nome = nome;
        this.cpf = cpf;
        this.perfilGravidade = perfilGravidade;
        geradorNumeros = new Random(); //Cria o gerador de números aleatórios
        this.atualizarSinaisVitais(); //Gera os dados do paciente
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
     * @return pressaoArterial
     */
    public int getPressao() {
        return pressaoArterial;
    }

    /**
     * Método que retorna a saturacao do oxigenio do paciente

     * @return saturacao
     */
    public int getSaturacao() {
        return saturacao;
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
    private void sinaisVitaisNormais(){
        this.temperatura = 35 + this.geradorNumeros.nextInt(37 -35 +1); //Numero entre 35 e 37
        this.freqCardiaca = 60 + this.geradorNumeros.nextInt(100 -60 +1); //Numero entre 60 e 100
        this.freqRespiratoria = 16 + this.geradorNumeros.nextInt(20 -16 +1); //Numero entre 16 e 20
        this.pressaoArterial = 100 + this.geradorNumeros.nextInt(140 -100 +1); //Numero entre 100 e 160
        this.saturacao = 90 + this.geradorNumeros.nextInt(100 -90 +1); //Numero entre 90 e 100
    }

    /**
     * Metodo que define de forma randomica os valores de cada sinal final,
     * dentro de uma faixa de valores consideradoros graves.
     */
    private void sinaisVitaisGraves(){
        this.temperatura = 38 + this.geradorNumeros.nextInt(50 -38 +1); //Numero entre 38 e 50
        this.freqCardiaca = 101 + this.geradorNumeros.nextInt(200 -101 +1); //Numero entre 101 e 200
        this.freqRespiratoria = 21 + this.geradorNumeros.nextInt(100 -21 +1); //Numero entre 21 e 100
        this.pressaoArterial = this.geradorNumeros.nextInt(99); //Numero de 0 a 99
        this.saturacao = this.geradorNumeros.nextInt(89); //Numero de 0 a 89
    }

    /**
     * Metodo que analisa o valor de cada sinal vital e define a gravidade do
     * paciente. 
     * 
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
        variation = this.saturacao - 95;
        this.gravidade += (variation < 0 ? -variation : variation);
        
        //Define a gravidade conforme a pressao
        if (this.pressaoArterial >= 81 && this.pressaoArterial < 100) {
            this.gravidade += 1; //Soma 1 na gravidade
        } else if (this.pressaoArterial > 0 && this.pressaoArterial <= 80) {
            this.gravidade += 3; //Soma 3 na gravidade
        }

    }
    
    /**
     * Método toString da classe, que retorna todos os valores de cada atributo do
     * paciente.
     * @return String - dados do paciente
     */
    @Override
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
}
