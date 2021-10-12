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
     * M�todo que retorna o primeiro elemento da fila.
     *
     * @return Object - Conte�do do primeiro n�
     */
    public Paciente peek() {
        return first.getConteudo();
    }

    public No getFirst() {
        return first;
    }

    /**
     * M�todo que altera a refer�ncia do primeiro elemento da fila
     *
     * @param first - Novo primeiro
     */
    public void setFirst(No first) {
        this.first = first;
    }

    /**
     * M�todo que retorna o tamanho da fila.
     *
     * @return int - O tamanho
     */
    public int size() {
        int tamanho = 0; //V�riavel para armazenar o tamanho da fila
        No auxiliar = first; //Vari�vel para percorrer 
        while (auxiliar != null) { //Enquanto n�o for o fim da fila
            tamanho++; //Incrementa-se 1 ao tamanho
            auxiliar = auxiliar.getNext(); //Passa para o pr�ximo n�
        }
        return tamanho;
    }

    /**
     * M�todo que retorna um n� da fila, que est� na posi��o indicada
     *
     * @param index - Posi��o do n� a ser buscado
     * @return Object - O objeto encontrado
     */
    public Paciente get(int index) {
        int posicao = 0; //indica a posi��o atual
        No aux = first; //vari�vel para percorrer a fila
        if (isEmpty() || index < 0 || index > this.size()) {
            return null;
        } else { //Se o index for um n�mero dentro do tamanho da fila 
            while (posicao != index) { //Percorre at� encontrar a posi��o
                aux = aux.getNext();
                posicao++;
            }
            return aux.getConteudo();
        }
    }

    /**
     * M�todo que verifica se a fila est� vazia
     *
     * @return true - Se estiver vazia
     */
    public boolean isEmpty() {
        return (first == null);
    }

    /**
     * M�todo que adiciona um paciente na fila por ordem de prioridade. A
     * prioridade corresponde a gravidade do paciente. Pacientes no inicio da
     * fila, s�o os pacientes com maior gravidade.
     *
     * @param paciente - paciente a ser adicionado
     */
    public void add(Paciente paciente) {
        No novo = new No(paciente);
        //Se a fila estiver vazia, ou o paciente possuir maior prioridade que o primeiro n�, adiciona no in�cio
        if (this.isEmpty() || paciente.getGravidade() > first.getConteudo().getGravidade()) {
            novo.setNext(first);
            first = novo;
        } else { //Se n�o for vazia
            No auxiliar = first;
            No auxiliar2 = first;
            //Pecorre a fila at� que encontre o ultimo elemento e o n� atual tenha prioridade superior ao do novo n�
            while (auxiliar2.getNext() != null && auxiliar2.getConteudo().getGravidade() > paciente.getGravidade()) {
                auxiliar = auxiliar2;
                auxiliar2 = auxiliar2.getNext();
            }
            //Se a prioridade do n� atual ser menor que a do novo, o n� � adicionado antes do n� atual
            if (auxiliar2.getConteudo().getGravidade() < paciente.getGravidade()) {
                novo.setNext(auxiliar2);
                auxiliar.setNext(novo);
            }
            //Se a prioridade do n� atual for maior ou igual, o novo n� � adicionado depois do n� atual
            if (auxiliar2.getConteudo().getGravidade() >= paciente.getGravidade()) {
                novo.setNext(auxiliar2.getNext());
                auxiliar2.setNext(novo);
            }
        }

    }

    /**
     * M�todo que remove o primeiro n� da fila.
     *
     * @return Paciente - O n� que foi removido
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
     * M�todo que remove um paciente
     *
     * @param nome - nome do paciente
     * @return Paciente - o paciente removido
     */
    public Paciente remove(Paciente recebido) {
        if (!this.isEmpty()) {
            No auxiliar = first;
            No auxiliar2 = first;
            if (auxiliar.getConteudo().equals(recebido)) { //Se o primeiro n� for igual ao paciente
                first = first.getNext();
                return auxiliar.getConteudo();
            } else {
                //Enquanto n�o for o fim da lista e o paciente n�o ser o procurado
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
     * M�todo que busca um paciente pelo nome
     *
     * @param nome nome do paciente
     * @return Paciente - o paciente encontrado
     */
    public Paciente buscarPaciente(String cpf) {
        if (!this.isEmpty()) { //Se a lista n�o for vazia
            No auxiliar = first;
            No auxiliar2 = first;
            while (auxiliar2.getNext() != null) { //Enquanto n�o for o fim da lista
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
     * M�todo que lista todos os pacientes cadastrados e salva em uma Arraylist
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
     * M�todo que lista todos os 7 pacientes mais graves. S� s�o considerados
     * pacientes graves, com gravidade >=3
     *
     * @return ArrayList- lista contendo os pacientes mais graves
     */
    public ArrayList listarPacientesGraves() {
        No auxiliar = this.getFirst();
        ArrayList<String> lista = new ArrayList<>();
        int count = 0;
        while (count < 7 && auxiliar != null) {
            if (auxiliar.getConteudo().getGravidade() >= 3) { //S� adiciona se a gravidade for maior ou igual a 3
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
            return first != null && first.getNext() != null;
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
