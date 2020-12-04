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
        int i = 0;
        for (String str: strings)
            for (String word: str.split(" "))
                if (word.length() > 2 && !contains(word)) {
                    Tuple tup = new Tuple(word, word.length());
                    trie.add(tup);
                    i++;
                }
        return i;
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
        ArrayList<String> res = new ArrayList<>();
        int current_len = 0;
        int len1 = 0;
        for (String word : trie.wordsWithPrefix(pref)) {
            if (word.length() != current_len) {
                len1++;
                current_len = word.length();
            }
            if (len1 > k) break;
            res.add(word);
        }
        String[] result = Arrays.copyOf(res.toArray(), res.toArray().length, String[].class);
        return () -> Arrays.stream(result).iterator();        
    }

    public int size() {
        return trie.size();
    }
}
