package ua.edu.ucu.tries;

import java.util.Arrays;
import java.util.Comparator;

public class RWayTrie implements Trie {
    private Node main;
    private static int R = 256;

    private static class Node {
        private Node[] next = new Node[R];
        private Object val;
    }

    @Override
    public void add(Tuple t) {
        if (t.weight < 2)
            return;
        main = add(main, t.term, t.weight, 0);
    }

    private Node add(Node y, String key, int val, int i) {
        if (y == null)
            y = new Node();
        if (i == key.length()) {
            y.val = val;
            return y;
        }
        char a = key.charAt(i);
        y.next[a] = add(y.next[a], key, val, 1 + i);
        return y;
    }

    @Override
    public boolean contains(String word) {
        return get(main, word, 0) != null;
    }

    private Node get(Node y, String key, int i) {
        if (y == null) return null;
        if (i == key.length()) return y;
        char a = key.charAt(i);
        return get(y.next[a], key, 1 + i);
    }

    @Override
    public boolean delete(String word) {
        boolean res = contains(word);
        main = delete(main, word, 0);
        return res;
    }

    private Node delete(Node y, String key, int i) {
        if (y == null) return null;
        if (key.length() == i)
            y.val = null;
        else {
            char a = key.charAt(i);
            y.next[a] = delete(y.next[a], key, 1 + i);
        }
        if (y.val != null)
            return y;
        for (char a = 0; a < R; a++)
            if (y.next[a] != null) return y;
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
        String[] res = Arrays.copyOf(queue.toArray(), queue.toArray().length, String[].class);
        Arrays.sort(res, Comparator.comparingInt(String::length));
        return () -> Arrays.stream(res).iterator();

    }
    private void collect(Node y, String s, Queue<String> queue) {
        if (y == null) return;
        if (y.val != null)
            queue.enqueue(s);
        for (char a = 0; a < R; a++)
            collect(y.next[a], s + a, queue);
    }





    @Override
    public int size() {
        return size(main);
    }
    private int size(Node y) {
        if (y == null) return 0;
        int number = 0;
        if (y.val != null)
            number++;
        for (char a = 0; a < R; a++)
            number += size(y.next[a]);
        return number;
    }


}
