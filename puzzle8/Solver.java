/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;


public class Solver {
    private int solutionNum = 0;
    private ArrayList<ArrayList<Board>> solutionList = new ArrayList<ArrayList<Board>>();

    private class SearchNode implements Comparable<SearchNode> {
        private int preMoves;
        private int hammingNum;
        private int manhattanNum;
        private SearchNode prevNode;
        private Board currBoard;

        private SearchNode(Board b, int moves) {
            preMoves = moves;
            currBoard = b;
            // hammingNum = b.hamming();
            manhattanNum = b.manhattan();
            prevNode = null;
        }

        private SearchNode(Board b, SearchNode s) {
            preMoves = s.preMoves + 1;
            currBoard = b;
            // hammingNum = b.hamming();
            manhattanNum = b.manhattan();
            prevNode = s;
        }

        public int compareTo(SearchNode s) {
            // return ((hammingNum + preMoves) - (s.hammingNum + s.preMoves));
            return ((manhattanNum + preMoves) - (s.manhattanNum + s.preMoves));
        }


    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Initial board can NOT be null.");
        }
        SearchNode prevNode;
        ArrayList<Board> visitedBoards = new ArrayList<>();
        int preMoves = 0;
        SearchNode curNode = new SearchNode(initial, preMoves);
        MinPQ<SearchNode> priorityQueue = new MinPQ<>();
        priorityQueue.insert(curNode);
        visitedBoards.add(initial);
        MinPQ<Integer> pqSteps = new MinPQ<>();
        if (initial.isGoal()) {
            pqSteps.insert(0);
            solutionList.add(recordSolution(curNode));
            solutionNum += 1;
        }
        while (!priorityQueue.isEmpty()) {
            prevNode = priorityQueue.delMin();
            Iterable<Board> nextBoardCandidate = prevNode.currBoard.neighbors();
            for (Board b : nextBoardCandidate) {
                if (visitedBoards.contains(b)) {
                    continue;
                }
                SearchNode node = new SearchNode(b, prevNode);
                priorityQueue.insert(node);
                visitedBoards.add(b);
                if (b.isGoal()) {
                    pqSteps.insert(prevNode.preMoves + 1);
                    solutionList.add(recordSolution(node));
                    solutionNum += 1;
                }
            }
            if (solutionNum >= 1 && (prevNode.preMoves > pqSteps.min())) {
                break;
            }
            if (solutionNum == 0 && ((prevNode.preMoves + prevNode.manhattanNum) > (
                    prevNode.prevNode.preMoves + prevNode.prevNode.manhattanNum))) {
                Solver altSolver = new Solver(initial.twin());
                if (altSolver.isSolvable()) {
                    break;
                }
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return (solutionNum > 0);
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        ArrayList<Board> solution = solutionList.get(0);
        int min = solution.size();
        for (ArrayList<Board> al : solutionList) {
            if (al.size() < min) {
                min = al.size();
            }
        }
        return (min - 1);
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solutionList.get(0);
    }


    private ArrayList<Board> recordSolution(SearchNode s) {
        Stack<Board> solutionStack = new Stack<>();
        ArrayList<Board> solution = new ArrayList<>();
        if (s == null) {
            throw new IllegalArgumentException("Solution node can NOT be null.");
        }
        solutionStack.push(s.currBoard);
        while (s.prevNode != null) {
            solutionStack.push(s.prevNode.currBoard);
            s = s.prevNode;
        }
        while (!solutionStack.isEmpty()) {
            solution.add(solutionStack.pop());
        }
        return solution;
    }

    public static void main(String[] args) {
        // int[][] data1 = new int[][] {
        //         { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 0, 15 }
        // };
        // Board b1 = new Board(data1);
        // Solver s1 = new Solver(b1);
        // System.out.println(b1);
        // for (Board b : s1.solution()) {
        //     System.out.println();
        //     System.out.println(b);
        //     System.out.println();
        // }

        int[][] data1 = new int[][] {
                { 1, 6, 2 }, { 4, 8, 0 }, { 7, 3, 5 }
        };
        Board b1 = new Board(data1);
        Solver s1 = new Solver(b1);
        for (Board b : s1.solution()) {
            System.out.println();
            System.out.println(b);
            System.out.println();
        }


    }
}
