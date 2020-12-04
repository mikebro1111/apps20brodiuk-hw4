package ua.edu.ucu.tries;

import java.util.Comparator;
import java.util.Arrays;

public class RWayTrie implements Trie {
    private Node main;
    private static int R = 256;

    private static class Node
    {
        private Node[] next = new Node[R];
        private Object value;
    }


    @Override
    public void add(Tuple t) {
        if (t.weight < 2)
            return;
        main = add(main, t.term, t.weight, 0);
    }
    private Node add(Node y, String key, int value, int k) {
        if (y == null)
            y = new Node();
        if (k == key.length()) {
            y.value = value;
            return y;
        }
        char a = key.charAt(k);
        y.next[a] = add(y.next[a], key, value, 1+k);
        return y;
    }


    @Override
    public boolean contains(String word) {
        return get(main, word, 0) != null;
    }
    private Node get(Node y, String key, int k) {
        if (y == null) return null;
        if (k == key.length()) {
            return y;
        }
        char a = key.charAt(k);
        return get(y.next[a], key, 1+k);
    }

    @Override
    public boolean delete(String word) {
        boolean res = contains(word);
        main = delete(main, word, 0);
        return res;
    }
    private Node delete(Node y, String key, int k) {
        if (y == null)
            return null;
        if (key.length() == k)
            y.value = null;
        else
        {
            char a = key.charAt(k);
            y.next[a] = delete(y.next[a], key, 1+k);
        }
        if (y.value != null) return y;
        for (char i = 0; i < R; i++)
            if (y.next[i] != null) return y;
        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue<String> queue = new Queue<String>();
        collect(get(main, s, 0), s, queue);
        String[] res = Arrays.copyOf(queue.toStack(), queue.toStack().length, String[].class);
        Arrays.sort(res, Comparator.comparingInt(String::length));
        return () -> Arrays.stream(res).iterator();

    }
    private void collect(Node y, String s, Queue<String> queue) {
        if (y == null)
            return;
        if (y.value != null)
            queue.enstack(s);
        for (char a = 0; a < R; a++)
            collect(y.next[a], a + s, queue);
    }

    @Override
    public int size() {
        return size(main);
    }
    private int size(Node y) {
        if (y == null)
            return 0;
        int number = 0;
        if (y.value != null)
            number++;
        for (char a = 0; a < R; a++)
            number += size(y.next[a]);
        return number;
    }


}
