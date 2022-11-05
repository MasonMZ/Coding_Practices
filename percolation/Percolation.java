/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size1D;
    private boolean[] status; // Record "open" or "blocked" of each site
    private WeightedQuickUnionUF gridsWithVirtualTiles;
    private WeightedQuickUnionUF grids;
    private int virtualTopTileID;
    private int virtualBottomTileID;
    private int numberofOpenGrid;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grids size should be greater than 0");
        }
        size1D = n;
        /* Create an empty union-find data structure, which contains all the tiles
         *  and two virtual tiles, one of which is at the top of the first row (top row),
         *  the other one is below the last row (bottom tiles).**/
        gridsWithVirtualTiles = new WeightedQuickUnionUF(n * n + 2);
        grids = new WeightedQuickUnionUF(n * n);
        virtualTopTileID = n * n;
        virtualBottomTileID = n * n + 1;
        status = new boolean[n * n];
        for (int i = 0; i < status.length; i += 1) {
            status[i] = false;
        }
    }

    // help method to convert site coordinate to id value
    private int coordinateToID(int row, int col) {
        int id = (row - 1) * size1D + col - 1;
        return id;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > size1D || col < 1 || col > size1D) {
            throw new IllegalArgumentException("Grid of interest is out of prescribed range.");
        }
        /* Make the target grid open. **/
        int id = coordinateToID(row, col);
        if (!status[id]) {
            numberofOpenGrid += 1;
            status[id] = true;
            /* Make the target grid connected to its neighboring open gridsWithVirtualTiles. **/
            int upGridRow = row - 1;
            int downGridRow = row + 1;
            int leftGridCol = col - 1;
            int rightGridCol = col + 1;
            if (upGridRow >= 1 && isOpen(upGridRow, col) && (grids.find(id) != grids.find(
                    coordinateToID(upGridRow, col)))) {
                gridsWithVirtualTiles.union(id, coordinateToID(upGridRow, col));
                grids.union(id, coordinateToID(upGridRow, col));
            }
            if (downGridRow <= size1D && isOpen(downGridRow, col) && (grids.find(id) != grids.find(
                    coordinateToID(downGridRow, col)))) {
                gridsWithVirtualTiles.union(id, coordinateToID(downGridRow, col));
                grids.union(id, coordinateToID(downGridRow, col));
            }
            if (leftGridCol >= 1 && isOpen(row, leftGridCol) && (grids.find(id) != grids.find(
                    coordinateToID(row, leftGridCol)))) {
                gridsWithVirtualTiles.union(id, coordinateToID(row, leftGridCol));
                grids.union(id, coordinateToID(row, leftGridCol));
            }
            if (rightGridCol <= size1D && isOpen(row, rightGridCol) && (grids.find(id)
                    != grids.find(coordinateToID(row, rightGridCol)))) {
                gridsWithVirtualTiles.union(id, coordinateToID(row, rightGridCol));
                grids.union(id, coordinateToID(row, rightGridCol));
            }
            /* If opening a grid in the top row, connect it to the virtual top tile. **/
            if (row == 1) {
                gridsWithVirtualTiles.union(id, virtualTopTileID);
            }
            /* If opening a grid in the bottom row, connect it to the virtual bottom tile if not percolates. **/
            if (row == size1D) {
                gridsWithVirtualTiles.union(id, virtualBottomTileID);
            }
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > size1D || col < 1 || col > size1D) {
            throw new IllegalArgumentException("Grid of interest is out of prescribed range.");
        }
        return status[coordinateToID(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > size1D || col < 1 || col > size1D) {
            throw new IllegalArgumentException("Grid of interest is out of prescribed range.");
        }
        if (!isOpen(row, col)) {
            return false;
        }
        else {
            for (int i = 1; i <= size1D; i += 1) {
                if (grids.find(coordinateToID(row, col)) == grids.find(coordinateToID(1, i))) {
                    return true;
                }
            }
            return false;
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberofOpenGrid;
    }

    // does the system percolate?
    public boolean percolates() {
        return (gridsWithVirtualTiles.find(virtualBottomTileID) == gridsWithVirtualTiles.find(
                virtualTopTileID));
    }

    public static void main(String[] args) {
        // /* Test case 1: should print out false at first then true. **/
        // Percolation P1 = new Percolation(5);
        // System.out.println(P1.percolates());
        // int c1 = P1.size1D / 2;
        // for (int r = 1; r <= P1.size1D; r += 1) {
        //     P1.open(r, c1);
        // }
        // System.out.println(P1.percolates());
        //
        // /* Test case 2: should print out false. **/
        // Percolation P2 = new Percolation(10);
        // int c2 = P2.size1D / 2;
        // for (int r = 1; r <= P2.size1D; r += 2) {
        //     P2.open(r, c2);
        // }
        // System.out.println(P2.percolates());
        //
        // /* Test case 3: should print out false. **/
        // Percolation P3 = new Percolation(5);
        // int r3 = P3.size1D / 2;
        // for (int c = 1; c <= P3.size1D; c += 1) {
        //     P3.open(r3, c);
        // }
        // System.out.println(P3.percolates());
        //
        // /* Test case 4: should print out false. **/
        // Percolation P4 = new Percolation(5);
        // int c = 1;
        // for (int row = 1; row <= P4.size1D; row += 1) {
        //     P4.open(row, c);
        //     c += 1;
        // }
        // System.out.println(P4.percolates());
        //
        // /* Test case 4: should print out true. **/
        // Percolation P5 = new Percolation(10);
        // int col = 1;
        // for (int row = 1; row <= P5.size1D; row += 1) {
        //     P5.open(row, col);
        //     if (col > 1) {
        //         P5.open(row, col - 1);
        //     }
        //     col += 1;
        // }
        // System.out.println(P5.percolates());
        //
        // /* Test case 5: isFull method test. Result should be false. **/
        // Percolation P6 = new Percolation(3);
        // P6.open(1, 3);
        // P6.open(2, 3);
        // P6.open(3, 3);
        // P6.open(3, 1);
        // System.out.println(P6.isFull(3, 1));
    }
}
