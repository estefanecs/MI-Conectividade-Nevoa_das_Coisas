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
package util;

import java.util.ArrayList;
import java.util.Iterator;
import model.Paciente;

/**
 * Esta classe armazena todos os pacientes cadastrado no sistema.
 *
 * Exemplo de uso:
 *
 * FilaPaciente pacientes= new FilaPaciente();
 *
 */
public class FilaPrioridade {

    private No first; //primeiro no
    private static int qtd_list = 0; //quantidade de pacientes a serem listados
    
    
    public FilaPrioridade() {
    }
    
    /**
     * Metodo que retorna o primeiro elemento da fila.
     *
     * @return Object - Conteudo do primeiro no
     */
    public Paciente peek() {
        return first.getConteudo();
    }
    
    /**
     * Metodo que retorna o primeiro no da fila
     * @return No - primeiro no
     */
    public No getFirst() {
        return first;
    }
    
    /**
     * Método que retorna a quantidade de pacientes que deve ser listados. Se o
     * atributo for nulo, adiciona o valor passado no paramtero.
     *
     * @param flag - nova quantidade
     * @return int - a quantidade
     */
    public static synchronized int qtd_list(Integer flag){
        if(flag != null){
            qtd_list = flag;
        }
        return qtd_list;
        
    } 

    /**
     * Metodo que altera a referencia do primeiro elemento da fila
     *
     * @param first - Novo primeiro
     */
    public void setFirst(No first) {
        this.first = first;
    }

    /**
     * Metodo que retorna o tamanho da fila.
     *
     * @return int - O tamanho
     */
    public int size() {
        int tamanho = 0; //V?riavel para armazenar o tamanho da fila
        No auxiliar = first; //Vari?vel para percorrer 
        while (auxiliar != null) { //Enquanto n?o for o fim da fila
            tamanho++; //Incrementa-se 1 ao tamanho
            auxiliar = auxiliar.getNext(); //Passa para o pr?ximo n?
        }
        return tamanho;
    }

    /**
     * Metodo que retorna um no da fila, que esta na posicao indicada
     *
     * @param index - Posicao do no a ser buscado
     * @return Object - O objeto encontrado
     */
    public Paciente get(int index) {
        int posicao = 0; //indica a posicao atual
        No aux = first; //variavel para percorrer a fila
        if (isEmpty() || index < 0 || index > this.size()) {
            return null;
        } else { //Se o index for um numero dentro do tamanho da fila 
            while (posicao != index) { //Percorre ate encontrar a posicao
                aux = aux.getNext();
                posicao++;
            }
            return aux.getConteudo();
        }
    }

    /**
     * Metodo que verifica se a fila esta vazia
     *
     * @return true - Se estiver vazia
     */
    public boolean isEmpty() {
        return (first == null);
    }

    /**
     * Metodo que adiciona um paciente na fila por ordem de prioridade. A
     * prioridade corresponde a gravidade do paciente. Pacientes no inicio da
     * fila, sao os pacientes com maior gravidade.
     *
     * @param paciente - paciente a ser adicionado
     */
    public void add(Paciente paciente) {
        No novo = new No(paciente);
        //Se a fila estiver vazia, ou o paciente possuir maior prioridade que o primeiro no, adiciona no inicio
        if (this.isEmpty() || paciente.getGravidade() > first.getConteudo().getGravidade()) {
            novo.setNext(first);
            first = novo;
        } else { //Se nao for vazia
            No auxiliar = first;
            No auxiliar2 = first;
            //Pecorre a fila ate que encontre o ultimo elemento e o no atual tenha prioridade superior ao do novo no
            while (auxiliar2.getNext() != null && auxiliar2.getConteudo().getGravidade() > paciente.getGravidade()) {
                auxiliar = auxiliar2;
                auxiliar2 = auxiliar2.getNext();
            }
            //Se a prioridade do no atual ser menor que a do novo, o no é adicionado antes do no atual
            if (auxiliar2.getConteudo().getGravidade() < paciente.getGravidade()) {
                novo.setNext(auxiliar2);
                auxiliar.setNext(novo);
            }
            //Se a prioridade do no atual for maior ou igual, o novo no é adicionado depois do no atual
            if (auxiliar2.getConteudo().getGravidade() >= paciente.getGravidade()) {
                novo.setNext(auxiliar2.getNext());
                auxiliar2.setNext(novo);
            }
        }

    }

    /**
     * Metodo que remove o primeiro no da fila.
     *
     * @return Paciente - O no que foi removido
     */
    public Paciente dequeue() {
        if (first != null) {
            No auxiliar = first;
            first = first.getNext();
            return auxiliar.getConteudo();
        }
        return null;
    }

    /**
     * Motodo que remove um paciente
     *
     * @param recebido - paciente
     * @return Paciente - o paciente removido
     */
    public Paciente remove(Paciente recebido) {
        if (!this.isEmpty()) { //Se a lista não for vazia
            No auxiliar = first;
            No auxiliar2 = first;
            if (auxiliar.getConteudo().equals(recebido)) { //Se o primeiro no for igual ao paciente
                first = first.getNext();
                return auxiliar.getConteudo();
            } else {
                //Enquanto no for o fim da lista e o paciente nao ser o procurado
                while (auxiliar2.getNext() != null && !auxiliar2.getConteudo().equals(recebido)) {
                    auxiliar = auxiliar2;
                    auxiliar2 = auxiliar2.getNext();
                }
                if (auxiliar2.getConteudo().equals(recebido)) {//Se encontrou o paciente
                    auxiliar.setNext(auxiliar2.getNext()); //remove
                    return auxiliar2.getConteudo();//retorna o paciente removido
                }
            }
        }
        return null;
    }

    /**
     * Metodo que busca um paciente pelo cpf
     *
     * @param cpf - cpf do paciente
     * @return Paciente - o paciente encontrado
     */
    public Paciente buscarPaciente(String cpf) {
        if (!this.isEmpty()) { //Se a lista nao for vazia
            No auxiliar = first;
            No auxiliar2 = first;
            while (auxiliar2.getNext() != null) { //Enquanto nao for o fim da lista
                if (auxiliar2.getConteudo().getCpf().equals(cpf)) { //Se for o paciente procurado
                    return auxiliar2.getConteudo();//retorna o paciente
                }
                auxiliar = auxiliar2;
                auxiliar2 = auxiliar2.getNext();
            }
        }
        return null;
    }

    /**
     * Metodo que lista todos os pacientes cadastrados e salva em uma Arraylist
     *
     * @return ArrayList- lista contendo os pacientes
     */
    public ArrayList listarTodosPacientes() {
        No auxiliar = this.getFirst();
        ArrayList<String> lista = new ArrayList<>();
        while (auxiliar != null) {
            lista.add(auxiliar.getConteudo().getNome());
            auxiliar = auxiliar.getNext();
        }
        return lista;
    }

    /**
     * Metodo que lista todos os N pacientes mais graves.
     *
     * @return Paciente[]- vetor contendo os pacientes mais graves
     */
    public Paciente[] listarPacientesGraves() {
        System.out.println("Valor de qtd_list: "+qtd_list(null));

        No auxiliar = this.getFirst();
        Paciente[] lista= new Paciente[(qtd_list(null) > this.size() ? this.size(): qtd_list(null))];
        
        for (int i =0; i < lista.length; i++) {
            if(auxiliar != null){
                Paciente dado = auxiliar.getConteudo();
                lista[i] = dado;
                auxiliar = auxiliar.getNext();
            }    
        }
        return lista;
    }
    
    /**
     * Método que obtém o iterator
     * @return Iterator
     */
    public Iterator getIterator() {
        return new it();
    }

    /**
     * Implementa a classe Iterator
     */
    private class it implements Iterator {

        private No atual = first;
        
        /**
         * Método que verifica se tem proximo nó
         * @return true- se tem 
         */
        @Override
        public boolean hasNext() {
            return first != null && atual != null;
        }
        
        /**
         * Método que retorna o conteudo do nó atual
         * @return Object - conteudo do nó
         */
        @Override
        public Object next() {
            if (atual != null) {
                Object conteudo = atual.getConteudo();
                atual = atual.getNext();
                return conteudo;
            }
            return null;
        }

    }
}
