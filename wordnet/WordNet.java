/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.Objects;
import java.util.TreeMap;

public class WordNet {
    private TreeMap<String, intNode> dictionary;
    private Digraph synsetsGraph;
    private int verticeNum;


    private class intNode {
        private int id;
        private intNode next;

        public intNode(int i) {
            id = i;
        }
    }

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("File name nust NOT be null!");
        }
        In synsetsStream = new In(synsets);
        dictionary = new TreeMap<>();

        while (synsetsStream.hasNextLine()) {
            verticeNum += 1;
            String currLineString = synsetsStream.readLine();
            String[] fields = currLineString.split(",");
            String[] words = fields[1].split(" ");

            for (int i = 0; i < words.length; i += 1) {
                intNode n = new intNode(Integer.parseInt(fields[0]));
                if (dictionary.containsKey(words[i])) {
                    n.next = dictionary.get(words[i]);
                    dictionary.put(words[i], n);
                }
                else {
                    dictionary.put(words[0], n);
                }
            }
        }

        synsetsGraph = new Digraph(verticeNum);
        In hypernymsStream = new In(hypernyms);
        while (hypernymsStream.hasNextLine()) {
            String currLineString = hypernymsStream.readLine();
            String[] fields = currLineString.split(",");
            if (Objects.equals(fields[0], "38003")) {
                continue;
            }
            if (fields.length <= 1) {
                throw new IllegalArgumentException();
            }
            for (int i = 1; i < fields.length; i += 1) {
                synsetsGraph.addEdge(Integer.parseInt(fields[0]), Integer.parseInt(fields[i]));
            }
        }

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return dictionary.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return dictionary.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        throw new UnsupportedOperationException();
    }


    public static void main(String[] args) {
        String s = "synsets.txt";
        String h = "hypernyms.txt";
        WordNet w1 = new WordNet(s, h);
        System.out.println(w1.isNoun("fire_control"));
    }
}
