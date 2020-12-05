package ua.edu.ucu.tries;


public class Queue<T> {
    private int size = 0;
    private Node<T> main;
   

    public Queue() {}

    public void enqueue(T data) {
        main = new Node<T>(data, main);
        size++;
    }

    public Object dequeue() {
        Node<T> before = null;
        Node<T> current = main;
        size--;
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

    public String[] toArray() {
        String[] array = new String[size];
        int j = 0;
        Node<T> current = main;
        while (current != null) {
            array[j] = (String) current.getData();
            j++;
            current = current.getNext();
        }
        return array;
    }


    static class Node<T> {
        private Node<T> next;
        private T data;


        public Node(T data, Node<T> next) {
            this.next = next;
            this.data = data;

        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

}
