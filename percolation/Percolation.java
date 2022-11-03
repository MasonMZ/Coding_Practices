/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size1D;
    private boolean[] status;
    private WeightedQuickUnionUF grids;
    private int virtualTopTileID;
    private int virtualBottomTileID;


    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grids size should be greater than 0");
        }
        size1D = n;
        /* Create an empty union-find data structure, which contains all the tiles
         *  and two virtual tiles, one of which is at the top of the first row (top row),
         *  the other one is below the last row (bottom tiles).**/
        grids = new WeightedQuickUnionUF(n * n + 2);
        virtualTopTileID = n * n;
        virtualBottomTileID = n * n + 1;
        status = new boolean[n * n];
        for (int i = 0; i < status.length; i += 1) {
            status[i] = false;
        }
    }

    public void open(int row, int col) {
        if (row < 1 || row > size1D || col < 1 || col > size1D) {
            throw new IllegalArgumentException("Grid of interest is out of prescribed range.");
        }
        /* Make the target grid open. **/
        int id = (row - 1) * size1D + col - 1;
        status[id] = true;
        /* Make the target grid connected to its neighboring open grids. **/
        int upGridRow = row - 1;
        int downGridRow = row + 1;
        int leftGridCol = col - 1;
        int rightGridCol = col + 1;
        if (upGridRow >= 1 && isOpen(upGridRow, col)) {
            grids.union(id, id - size1D);
        }
        if (downGridRow <= size1D && isOpen(downGridRow, col)) {
            grids.union(id, id + size1D);
        }
        if (leftGridCol >= 1 && isOpen(row, leftGridCol)) {
            grids.union(id, id - 1);
        }
        if (rightGridCol <= size1D && isOpen(row, rightGridCol)) {
            grids.union(id, id + 1);
        }
        /* If opening a grid in the top row, connect it to the virtual top tile. **/
        if (row == 1) {
            grids.union(id, virtualTopTileID);
        }
        /* If opening a grid in the bottom row, connect it to the virtual bottom tile. **/
        if (row == size1D) {
            grids.union(id, virtualBottomTileID);
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || row > size1D || col < 1 || col > size1D) {
            throw new IllegalArgumentException("Grid of interest is out of prescribed range.");
        }
        int id = (row - 1) * size1D + col - 1;
        return status[id];
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || row > size1D || col < 1 || col > size1D) {
            throw new IllegalArgumentException("Grid of interest is out of prescribed range.");
        }
        int id = (row - 1) * size1D + col - 1;
        return (isOpen(row, col) && grids.find(id) == grids.find(virtualTopTileID));
    }

    public int numberofOpenSites() {
        int count = 0;
        for (int row = 1; row <= size1D; row += 1) {
            for (int col = 1; col < size1D; col += 1) {
                if (isOpen(row, col)) {
                    count += 1;
                }
            }
        }
        return count;
    }


    public static void main(String[] args) {

    }
}
