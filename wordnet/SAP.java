/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class SAP {
    private final Digraph copyDigraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        copyDigraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        HashMap<Integer, Integer> vAncestors = new HashMap<>();
        BreadthFirstDirectedPaths vBfs = new BreadthFirstDirectedPaths(copyDigraph, v);
        for (int i = 0; i < copyDigraph.V(); i += 1) {
            if (vBfs.hasPathTo(i)) {
                vAncestors.put(i, vBfs.distTo(i));
            }
        }

        HashMap<Integer, Integer> wAncestors = new HashMap<>();
        BreadthFirstDirectedPaths wBfs = new BreadthFirstDirectedPaths(copyDigraph, w);
        for (int i = 0; i < copyDigraph.V(); i += 1) {
            if (wBfs.hasPathTo(i)) {
                wAncestors.put(i, wBfs.distTo(i));
            }
        }
        int distance = Integer.MAX_VALUE;
        for (int key : vAncestors.keySet()) {
            if (wAncestors.keySet().contains(key) && (vBfs.distTo(key) + wBfs.distTo(key)
                    < distance)) {
                distance = vBfs.distTo(key) + wBfs.distTo(key);
            }
        }
        if (distance == Integer.MAX_VALUE) {
            return -1;
        }
        else {
            return distance;
        }
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        HashMap<Integer, Integer> vAncestors = new HashMap<>();
        BreadthFirstDirectedPaths vBfs = new BreadthFirstDirectedPaths(copyDigraph, v);
        for (int i = 0; i < copyDigraph.V(); i += 1) {
            if (vBfs.hasPathTo(i)) {
                vAncestors.put(i, vBfs.distTo(i));
            }
        }

        HashMap<Integer, Integer> wAncestors = new HashMap<>();
        BreadthFirstDirectedPaths wBfs = new BreadthFirstDirectedPaths(copyDigraph, w);
        for (int i = 0; i < copyDigraph.V(); i += 1) {
            if (wBfs.hasPathTo(i)) {
                wAncestors.put(i, wBfs.distTo(i));
            }
        }
        int distance = Integer.MAX_VALUE;
        int commonAncestor = -1;
        for (int key : vAncestors.keySet()) {
            if (wAncestors.keySet().contains(key) && (vBfs.distTo(key) + wBfs.distTo(key)
                    < distance)) {
                distance = vBfs.distTo(key) + wBfs.distTo(key);
                commonAncestor = key;
            }
        }
        return commonAncestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        int counterV = 0;
        for (Object i : v) {
            if (i == null) {
                throw new IllegalArgumentException();
            }
            counterV++;
        }
        int counterW = 0;
        for (Object i : w) {
            if (i == null) {
                throw new IllegalArgumentException();
            }
            counterW++;
        }
        if (counterV == 0 || counterW == 0) {
            throw new IllegalArgumentException();
        }

        HashMap<Integer, Integer> vAncestors = new HashMap<>();
        BreadthFirstDirectedPaths vBfs = new BreadthFirstDirectedPaths(copyDigraph, v);
        for (int i = 0; i < copyDigraph.V(); i += 1) {
            if (vBfs.hasPathTo(i)) {
                vAncestors.put(i, vBfs.distTo(i));
            }
        }

        HashMap<Integer, Integer> wAncestors = new HashMap<>();
        BreadthFirstDirectedPaths wBfs = new BreadthFirstDirectedPaths(copyDigraph, w);
        for (int i = 0; i < copyDigraph.V(); i += 1) {
            if (wBfs.hasPathTo(i)) {
                wAncestors.put(i, wBfs.distTo(i));
            }
        }
        int distance = Integer.MAX_VALUE;
        for (int key : vAncestors.keySet()) {
            if (wAncestors.keySet().contains(key) && (vBfs.distTo(key) + wBfs.distTo(key)
                    < distance)) {
                distance = vBfs.distTo(key) + wBfs.distTo(key);
            }
        }
        if (distance == Integer.MAX_VALUE) {
            return -1;
        }
        else {
            return distance;
        }
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        HashMap<Integer, Integer> vAncestors = new HashMap<>();
        BreadthFirstDirectedPaths vBfs = new BreadthFirstDirectedPaths(copyDigraph, v);
        for (int i = 0; i < copyDigraph.V(); i += 1) {
            if (vBfs.hasPathTo(i)) {
                vAncestors.put(i, vBfs.distTo(i));
            }
        }

        HashMap<Integer, Integer> wAncestors = new HashMap<>();
        BreadthFirstDirectedPaths wBfs = new BreadthFirstDirectedPaths(copyDigraph, w);
        for (int i = 0; i < copyDigraph.V(); i += 1) {
            if (wBfs.hasPathTo(i)) {
                wAncestors.put(i, wBfs.distTo(i));
            }
        }
        int distance = Integer.MAX_VALUE;
        int commonAncestor = -1;
        for (int key : vAncestors.keySet()) {
            if (wAncestors.keySet().contains(key) && (vBfs.distTo(key) + wBfs.distTo(key)
                    < distance)) {
                distance = vBfs.distTo(key) + wBfs.distTo(key);
                commonAncestor = key;
            }
        }
        return commonAncestor;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
