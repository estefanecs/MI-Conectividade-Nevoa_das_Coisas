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
