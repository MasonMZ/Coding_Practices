/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.TreeSet;

public class Outcast {
    private final WordNet w;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        w = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int distance = -1;
        String candidate = "";
        TreeSet<String> auxTree = new TreeSet<>();
        for (String s : nouns) {
            if (w.isNoun(s)) {
                auxTree.add(s);
            }
        }
        for (String i : auxTree) {
            int d = 0;
            for (String j : auxTree) {
                d += w.distance(i, j);
            }
            if (d > distance) {
                distance = d;
                candidate = i;
            }
        }
        return candidate;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
