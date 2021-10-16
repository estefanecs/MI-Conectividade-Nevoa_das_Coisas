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

    private No first;

    /**
     * Método que retorna o primeiro elemento da fila.
     *
     * @return Object - Conteúdo do primeiro nó
     */
    public Paciente peek() {
        return first.getConteudo();
    }

    public No getFirst() {
        return first;
    }

    /**
     * Método que altera a referência do primeiro elemento da fila
     *
     * @param first - Novo primeiro
     */
    public void setFirst(No first) {
        this.first = first;
    }

    /**
     * Método que retorna o tamanho da fila.
     *
     * @return int - O tamanho
     */
    public int size() {
        int tamanho = 0; //Váriavel para armazenar o tamanho da fila
        No auxiliar = first; //Variável para percorrer 
        while (auxiliar != null) { //Enquanto não for o fim da fila
            tamanho++; //Incrementa-se 1 ao tamanho
            auxiliar = auxiliar.getNext(); //Passa para o próximo nó
        }
        return tamanho;
    }

    /**
     * Método que retorna um nó da fila, que está na posição indicada
     *
     * @param index - Posição do nó a ser buscado
     * @return Object - O objeto encontrado
     */
    public Paciente get(int index) {
        int posicao = 0; //indica a posição atual
        No aux = first; //variável para percorrer a fila
        if (isEmpty() || index < 0 || index > this.size()) {
            return null;
        } else { //Se o index for um número dentro do tamanho da fila 
            while (posicao != index) { //Percorre até encontrar a posição
                aux = aux.getNext();
                posicao++;
            }
            return aux.getConteudo();
        }
    }

    /**
     * Método que verifica se a fila está vazia
     *
     * @return true - Se estiver vazia
     */
    public boolean isEmpty() {
        return (first == null);
    }

    /**
     * Método que adiciona um paciente na fila por ordem de prioridade. A
     * prioridade corresponde a gravidade do paciente. Pacientes no inicio da
     * fila, são os pacientes com maior gravidade.
     *
     * @param paciente - paciente a ser adicionado
     */
    public void add(Paciente paciente) {
        No novo = new No(paciente);
        //Se a fila estiver vazia, ou o paciente possuir maior prioridade que o primeiro nó, adiciona no início
        if (this.isEmpty() || paciente.getGravidade() > first.getConteudo().getGravidade()) {
            novo.setNext(first);
            first = novo;
        } else { //Se não for vazia
            No auxiliar = first;
            No auxiliar2 = first;
            //Pecorre a fila até que encontre o ultimo elemento e o nó atual tenha prioridade superior ao do novo nó
            while (auxiliar2.getNext() != null && auxiliar2.getConteudo().getGravidade() > paciente.getGravidade()) {
                auxiliar = auxiliar2;
                auxiliar2 = auxiliar2.getNext();
            }
            //Se a prioridade do nó atual ser menor que a do novo, o nó é adicionado antes do nó atual
            if (auxiliar2.getConteudo().getGravidade() < paciente.getGravidade()) {
                novo.setNext(auxiliar2);
                auxiliar.setNext(novo);
            }
            //Se a prioridade do nó atual for maior ou igual, o novo nó é adicionado depois do nó atual
            if (auxiliar2.getConteudo().getGravidade() >= paciente.getGravidade()) {
                novo.setNext(auxiliar2.getNext());
                auxiliar2.setNext(novo);
            }
        }

    }

    /**
     * Método que remove o primeiro nó da fila.
     *
     * @return Paciente - O nó que foi removido
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
     * Método que remove um paciente
     *
     * @param recebido - paciente
     * @return Paciente - o paciente removido
     */
    public Paciente remove(Paciente recebido) {
        if (!this.isEmpty()) {
            No auxiliar = first;
            No auxiliar2 = first;
            if (auxiliar.getConteudo().equals(recebido)) { //Se o primeiro nó for igual ao paciente
                first = first.getNext();
                return auxiliar.getConteudo();
            } else {
                //Enquanto não for o fim da lista e o paciente não ser o procurado
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
     * Método que busca um paciente pelo nome
     *
     * @param cpf cpf do paciente
     * @return Paciente - o paciente encontrado
     */
    public Paciente buscarPaciente(String cpf) {
        if (!this.isEmpty()) { //Se a lista não for vazia
            No auxiliar = first;
            No auxiliar2 = first;
            while (auxiliar2.getNext() != null) { //Enquanto não for o fim da lista
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
     * Método que lista todos os pacientes cadastrados e salva em uma Arraylist
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
     * Método que lista todos os 7 pacientes mais graves. Só são considerados
     * pacientes graves, com gravidade >=3
     *
     * @return ArrayList- lista contendo os pacientes mais graves
     */
    public ArrayList listarPacientesGraves() {
        No auxiliar = this.getFirst();
        ArrayList<String> lista = new ArrayList<>();
        int count = 0;
        while (count < 7 && auxiliar != null) {
            if (auxiliar.getConteudo().getGravidade() >= 3) { //Só adiciona se a gravidade for maior ou igual a 3
                String gravidade = " |Gravidade: " + auxiliar.getConteudo().getGravidade();
                String dado = auxiliar.getConteudo().getNome().concat(gravidade);
                lista.add(dado);
            }
            auxiliar = auxiliar.getNext();
            count++;
        }
        return lista;
    }

    public Iterator getIterator() {
        return new it();
    }

    private class it implements Iterator {

        private No atual = first;
        
        @Override
        public boolean hasNext() {
            return first != null && atual != null;
        }

        @Override
        public Object next() {
            if (first != null && first.getNext() != null) {
                Object ret = atual.getConteudo();
                atual = atual.getNext();
                return ret;
            }
            return null;
        }

    }
}
