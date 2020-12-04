package ua.edu.ucu.tries;


public class Queue<T> {
    private int value = 0;
    private Node<T> main;

    public Queue() {}

    public void enstack(T data) {
        main = new Node<T>(data, main);
        value++;
    }

    public Object destack() {
        Node<T> before = null;
        Node<T> current = main;
        value--;
        if (main.getNext() == null) {
            main = null;
            return current.getData();
        }

        while(current.getNext() != null) {
            before = current;
            current = current.getNext();
        }
        current = before.getNext();
        before.setNext(null);
        return current.getData();

    }

    public String[] toStack() {
        String[] stack = new String[value];
        int j = 0;
        Node<T> current = main;
        while (current != null) {
            stack[j] = (String) current.getData();
            j++;
            current = current.getNext();
        }
        return stack;
    }


    static class Node<T> {
        private T database;
        private Node<T> next;


        public Node(T database, Node<T> next) {
            this.database = database;
            this.next = next;
        }

        public T getData() {
            return database;
        }

        public void setData(T database) {
            this.database = database;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

}
