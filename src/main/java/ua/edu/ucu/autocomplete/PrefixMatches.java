package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author andrii
 */

public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        int j = 0;
        for (String s: strings)
            for (String word: s.split(" "))
                if (word.length() > 2 && !contains(word)) {
                    Tuple t = new Tuple(word, word.length());
                    trie.add(t);
                    j++;
                }
        return j;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        ArrayList<String> result = new ArrayList<>();
        int len1 = 0;
        int current_len = 0;
        for (String word : trie.wordsWithPrefix(pref)) {
            if (word.length() != current_len) {
                len1++;
                current_len = word.length();
            }
            if (len1 > k) break;
            result.add(word);
        }
        String[] res = Arrays.copyOf(result.toArray(), result.toArray().length, String[].class);
        return () -> Arrays.stream(res).iterator();
    }

    public int size() {
        return trie.size();
    }
}
