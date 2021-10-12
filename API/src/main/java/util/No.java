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

import model.Paciente;

/**
 * Classe para objetos do tipo No, que cont�m seus atributos e m�todos. Possui
 * uma refer�ncia para o conte�do do n� e a refer�ncia para o n� seguinte.
 *
 * Exemplo de uso:
 *
 * No no= new No("conteudo");
 *
 */
public class No {

    private Paciente conteudo;
    private No next;

    public No(Paciente conteudo) {
        this.conteudo = conteudo;
    }

    /**
     * M�todo que obt�m o pr�ximo N�
     *
     * @return No - O pr�ximo n�
     */
    public No getNext() {
        return next;
    }

    /**
     * M�todo que obt�m o conte�do do N�
     *
     * @return Paciente - O conte�do
     */
    public Paciente getConteudo() {
        return conteudo;
    }

    /**
     * M�todo que modifica a refer�ncia do pr�ximo, para um outro n�.
     *
     * @param newNext - O novo pr�ximo n�
     */
    public void setNext(No newNext) {
        next = newNext;
    }

    /**
     * M�todo que modifica o conte�do do n�
     *
     * @param novoConteudo - O novo conte�do do n�
     */
    public void setConteudo(Paciente novoConteudo) {
        conteudo = novoConteudo;
    }
}
