/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size1D;
    private boolean[] status;


    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grids size should be greater than 0");
        }
        size1D = n;
        /* Create an empty union-find data structure, which contains all the tiles
         *  and two virtual tiles, one of which is at the top of the first row (top row),
         *  the other one is below the last row (bottom tiles).**/
        WeightedQuickUnionUF grids = new WeightedQuickUnionUF(n * n + 2);
        /* Connect the top virtual tile to the top row of tiles. **/
        for (int i = 0; i < n; i += 1) {
            grids.union(i, n * n);
        }
        /* Connect the bottom virtual tile to the bottom row of tiles. **/
        for (int i = (n - 1) * n; i < n * n; i += 1) {
            grids.union(i, n * n + 1);
        }
        status = new boolean[n * n];
        for (int i = 0; i < status.length; i += 1) {
            status[i] = false;
        }
    }

    public void open(int row, int col) {
        if (row < 1 || row > size1D || col < 1 || col > size1D) {
            throw new IllegalArgumentException("Grid of interest is out of prescribed range.");
        }
        int id = (row - 1) * size1D + col - 1;
        status[id] = true;
    }

    public boolean is_open(int row, int col) {
        if (row < 1 || row > size1D || col < 1 || col > size1D) {
            throw new IllegalArgumentException("Grid of interest is out of prescribed range.");
        }
        int id = (row - 1) * size1D + col - 1;
        return status[id];
    }


    public static void main(String[] args) {

    }
}
