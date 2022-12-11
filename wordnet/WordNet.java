/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.TreeMap;

public class WordNet {
    private TreeMap<String, Stack<Integer>> dictionary;
    private TreeMap<Integer, String> reverseDictionary;
    private Digraph synsetsGraph;
    private int verticeNum;


    // private class intNode {
    //     private int id;
    //     private intNode next;
    //
    //     public intNode(int i) {
    //         id = i;
    //     }
    // }

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("File name nust NOT be null!");
        }
        In synsetsStream = new In(synsets);
        dictionary = new TreeMap<>();
        reverseDictionary = new TreeMap<>();

        while (synsetsStream.hasNextLine()) {
            verticeNum += 1;
            String currLineString = synsetsStream.readLine();
            String[] fields = currLineString.split(",");

            String[] words = fields[1].split(" ");

            for (int i = 0; i < words.length; i += 1) {
                if (dictionary.containsKey(words[i])) {
                    dictionary.get(words[i]).push(Integer.parseInt(fields[0]));
                }
                else {
                    Stack<Integer> s = new Stack<>();
                    s.push(Integer.parseInt(fields[0]));
                    dictionary.put(words[i], s);
                }
            }
            if (!reverseDictionary.containsKey(Integer.parseInt(fields[0]))) {
                reverseDictionary.put(Integer.parseInt(fields[0]), fields[1]);
            }

        }

        synsetsGraph = new Digraph(verticeNum);
        In hypernymsStream = new In(hypernyms);
        boolean hasRoot = false;
        while (hypernymsStream.hasNextLine()) {
            String currLineString = hypernymsStream.readLine();
            String[] fields = currLineString.split(",");
            if (fields.length == 1 && !hasRoot) {
                hasRoot = true;
                continue;
            }
            if (hasRoot && fields.length <= 1) {
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
        if (!dictionary.containsKey(nounA) || !dictionary.containsKey(nounB)) {
            throw new IllegalArgumentException("The WordNet must contain input nouns!");
        }
        Stack<Integer> iterableA = dictionary.get(nounA);
        Stack<Integer> iterableB = dictionary.get(nounB);
        SAP targetGraph = new SAP(synsetsGraph);
        return targetGraph.length(iterableA, iterableB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!dictionary.containsKey(nounA) || !dictionary.containsKey(nounB)) {
            throw new IllegalArgumentException("The WordNet must contain input nouns!");
        }
        Stack<Integer> iterableA = dictionary.get(nounA);
        Stack<Integer> iterableB = dictionary.get(nounB);
        SAP targetGraph = new SAP(synsetsGraph);
        int targetID = targetGraph.ancestor(iterableA, iterableB);
        return reverseDictionary.get(targetID);
    }


    public static void main(String[] args) {
        String s = "synsets.txt";
        String h = "hypernyms.txt";
        WordNet w1 = new WordNet(s, h);
        System.out.println(w1.isNoun("fire_control"));
        System.out.println(w1.distance("worm", "bird"));
        System.out.println(w1.sap("worm", "bird"));
        // for (int i : w1.dictionary.get("A")) {
        //     System.out.println(i);
        // }
    }
}
