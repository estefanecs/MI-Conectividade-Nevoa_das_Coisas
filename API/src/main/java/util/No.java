package util;

import model.Paciente;

/**
 * Classe para objetos do tipo No, que contém seus atributos e métodos. Possui
 * uma referência para o conteúdo do nó e a referência para o nó seguinte.
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
     * Método que obtém o próximo Nó
     *
     * @return No - O próximo nó
     */
    public No getNext() {
        return next;
    }

    /**
     * Método que obtém o conteúdo do Nó
     *
     * @return Paciente - O conteúdo
     */
    public Paciente getConteudo() {
        return conteudo;
    }

    /**
     * Método que modifica a referência do próximo, para um outro nó.
     *
     * @param newNext - O novo próximo nó
     */
    public void setNext(No newNext) {
        next = newNext;
    }

    /**
     * Método que modifica o conteúdo do nó
     *
     * @param novoConteudo - O novo conteúdo do nó
     */
    public void setConteudo(Paciente novoConteudo) {
        conteudo = novoConteudo;
    }
}
