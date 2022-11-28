/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public final class Board {
    private final int size;
    private final int[][] squares;
    private Board twin;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        size = tiles.length;
        squares = new int[size][size];
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                squares[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        // String res = String.format("%d", size);
        // for (int i = 0; i < size; i += 1) {
        //     String newLine = "";
        //     for (int j = 0; j < size; j += 1) {
        //         newLine = String.format(newLine + "%2d", squares[i][j]);
        //     }
        //     res = String.format(res + "%n" + newLine);
        // }
        // res = String.format(res + "%n");
        // return res;
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(String.format("%2d ", squares[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        int res = 0;
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                if ((i * size + j + 1) != squares[i][j] && !(i == size - 1 && j == size - 1)) {
                    res += 1;
                }
            }
        }
        return res;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int score = 0;
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                if (squares[i][j] != 0) {
                    score += Math.abs((squares[i][j] - 1) / size - i) + Math.abs(
                            (squares[i][j] - 1) % size - j);
                }

            }
        }
        return score;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int res = hamming();
        return (res == 0);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;
        if (this.size != that.size) {
            return false;
        }
        if (!Arrays.deepEquals(this.squares, that.squares)) {
            return false;
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        if (isUpperLeftCorner()) {
            return upperLeftNeighbors();
        }
        else if (isUpperRightCorner()) {
            return upperRightNeighbors();
        }
        else if (isLowerLeftCorner()) {
            return lowerLeftNeighbors();
        }
        else if (isLowerRightCorner()) {
            return lowerRightNeighbors();
        }
        else if (isUpperSide()) {
            return upperSideNeighbors();
        }
        else if (isLowerSide()) {
            return lowerSideNeighbors();
        }
        else if (isLeftSide()) {
            return leftSideNeighbors();
        }
        else if (isRightSide()) {
            return rightSideNeighbors();
        }
        else {
            return innerNeighbors();
        }
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (twin == null) {
            int n1 = StdRandom.uniformInt(size * size);
            int row1 = n1 / size;
            int col1 = n1 % size;
            while (squares[row1][col1] == 0) {
                n1 = StdRandom.uniformInt(size * size);
                row1 = n1 / size;
                col1 = n1 % size;
            }
            int n2 = StdRandom.uniformInt(size * size);
            int row2 = n2 / size;
            int col2 = n2 % size;
            while (n1 == n2 || squares[row2][col2] == 0) {
                n2 = StdRandom.uniformInt(size * size);
                row2 = n2 / size;
                col2 = n2 % size;
            }
            int[][] newTiles = new int[size][size];
            for (int i = 0; i < size; i += 1) {
                for (int j = 0; j < size; j += 1) {
                    if (i == row1 && j == col1) {
                        newTiles[i][j] = squares[row2][col2];
                    }
                    else if (i == row2 && j == col2) {
                        newTiles[i][j] = squares[row1][col1];
                    }
                    else {
                        newTiles[i][j] = squares[i][j];
                    }
                }
            }
            Board res = new Board(newTiles);
            twin = res;
        }
        return twin;
    }

    private boolean isUpperLeftCorner() {
        return (this.squares[0][0] == 0);
    }

    private Iterable<Board> upperLeftNeighbors() {
        Board[] neighbors = new Board[2];
        int[][] newTiles = new int[size][size];
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                newTiles[i][j] = squares[i][j];
            }
        }
        newTiles[0][1] = 0;  // move left
        newTiles[0][0] = squares[0][1];
        neighbors[0] = new Board(newTiles);

        newTiles[0][1] = squares[0][1]; // recover
        newTiles[1][0] = 0; // move up
        newTiles[0][0] = squares[1][0];
        neighbors[1] = new Board(newTiles);
        return Arrays.asList(neighbors);
    }

    private boolean isUpperRightCorner() {
        return (this.squares[0][size - 1] == 0);
    }

    private Iterable<Board> upperRightNeighbors() {
        Board[] neighbors = new Board[2];
        int[][] newTiles = new int[size][size];
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                newTiles[i][j] = squares[i][j];
            }
        }
        newTiles[0][size - 2] = 0;  // move right
        newTiles[0][size - 1] = squares[0][size - 2];
        neighbors[0] = new Board(newTiles);

        newTiles[0][size - 2] = squares[0][size - 2]; // recover
        newTiles[1][size - 1] = 0; // move up
        newTiles[0][size - 1] = squares[1][size - 1];
        neighbors[1] = new Board(newTiles);
        return Arrays.asList(neighbors);
    }

    private boolean isLowerLeftCorner() {
        return (this.squares[size - 1][0] == 0);
    }

    private Iterable<Board> lowerLeftNeighbors() {
        Board[] neighbors = new Board[2];
        int[][] newTiles = new int[size][size];
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                newTiles[i][j] = squares[i][j];
            }
        }
        newTiles[size - 1][1] = 0;  // move left
        newTiles[size - 1][0] = squares[size - 1][1];
        neighbors[0] = new Board(newTiles);

        newTiles[size - 1][1] = squares[size - 1][1]; // recover
        newTiles[size - 2][0] = 0; // move down
        newTiles[size - 1][0] = squares[size - 2][0];
        neighbors[1] = new Board(newTiles);
        return Arrays.asList(neighbors);
    }

    private boolean isLowerRightCorner() {
        return (this.squares[size - 1][size - 1] == 0);
    }

    private Iterable<Board> lowerRightNeighbors() {
        Board[] neighbors = new Board[2];
        int[][] newTiles = new int[size][size];
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                newTiles[i][j] = squares[i][j];
            }
        }
        newTiles[size - 1][size - 2] = 0;  // move right
        newTiles[size - 1][size - 1] = squares[size - 1][size - 2];
        neighbors[0] = new Board(newTiles);

        newTiles[size - 1][size - 2] = squares[size - 1][size - 2]; // recover
        newTiles[size - 2][size - 1] = 0; // move up
        newTiles[size - 1][size - 1] = squares[size - 2][size - 1];
        neighbors[1] = new Board(newTiles);
        return Arrays.asList(neighbors);
    }

    private boolean isUpperSide() {
        for (int i = 1; i < size - 1; i += 1) {
            if (this.squares[0][i] == 0) {
                return true;
            }
        }
        return false;
    }

    private Iterable<Board> upperSideNeighbors() {
        Board[] neighbors = new Board[3];
        int[][] newTiles = new int[size][size];
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                newTiles[i][j] = squares[i][j];
            }
        }
        int rowID = 0;
        int colID = 0;
        for (int i = 1; i < size - 1; i += 1) {
            if (squares[rowID][i] == 0) {
                colID = i;
            }
        }
        newTiles[rowID][colID - 1] = 0;  // move right
        newTiles[rowID][colID] = squares[rowID][colID - 1];
        neighbors[0] = new Board(newTiles);

        newTiles[rowID][colID - 1] = squares[rowID][colID - 1]; // recover
        newTiles[rowID][colID + 1] = 0; // move left
        newTiles[rowID][colID] = squares[rowID][colID + 1];
        neighbors[1] = new Board(newTiles);

        newTiles[rowID][colID + 1] = squares[rowID][colID + 1]; // recover
        newTiles[rowID + 1][colID] = 0; // move up
        newTiles[rowID][colID] = squares[rowID + 1][colID];
        neighbors[2] = new Board(newTiles);
        return Arrays.asList(neighbors);
    }

    private boolean isLowerSide() {
        for (int i = 1; i < size - 1; i += 1) {
            if (this.squares[size - 1][i] == 0) {
                return true;
            }
        }
        return false;
    }

    private Iterable<Board> lowerSideNeighbors() {
        Board[] neighbors = new Board[3];
        int[][] newTiles = new int[size][size];
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                newTiles[i][j] = squares[i][j];
            }
        }
        int rowID = size - 1;
        int colID = 0;
        for (int i = 1; i < size - 1; i += 1) {
            if (squares[rowID][i] == 0) {
                colID = i;
            }
        }
        newTiles[rowID][colID - 1] = 0;  // move right
        newTiles[rowID][colID] = squares[rowID][colID - 1];
        neighbors[0] = new Board(newTiles);

        newTiles[rowID][colID - 1] = squares[rowID][colID - 1]; // recover
        newTiles[rowID][colID + 1] = 0; // move left
        newTiles[rowID][colID] = squares[rowID][colID + 1];
        neighbors[1] = new Board(newTiles);

        newTiles[rowID][colID + 1] = squares[rowID][colID + 1]; // recover
        newTiles[rowID - 1][colID] = 0; // move down
        newTiles[rowID][colID] = squares[rowID - 1][colID];
        neighbors[2] = new Board(newTiles);
        return Arrays.asList(neighbors);
    }

    private boolean isLeftSide() {
        for (int i = 1; i < size - 1; i += 1) {
            if (this.squares[i][0] == 0) {
                return true;
            }
        }
        return false;
    }

    private Iterable<Board> leftSideNeighbors() {
        Board[] neighbors = new Board[3];
        int[][] newTiles = new int[size][size];
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                newTiles[i][j] = squares[i][j];
            }
        }
        int rowID = 0;
        int colID = 0;
        for (int i = 1; i < size - 1; i += 1) {
            if (squares[i][colID] == 0) {
                rowID = i;
            }
        }
        newTiles[rowID][colID + 1] = 0; // move left
        newTiles[rowID][colID] = squares[rowID][colID + 1];
        neighbors[0] = new Board(newTiles);

        newTiles[rowID][colID + 1] = squares[rowID][colID + 1]; // recover
        newTiles[rowID - 1][colID] = 0; // move down
        newTiles[rowID][colID] = squares[rowID - 1][colID];
        neighbors[1] = new Board(newTiles);

        newTiles[rowID - 1][colID] = squares[rowID - 1][colID];
        newTiles[rowID + 1][colID] = 0; // move up
        newTiles[rowID][colID] = squares[rowID + 1][colID];
        neighbors[2] = new Board(newTiles);
        return Arrays.asList(neighbors);
    }

    private boolean isRightSide() {
        for (int i = 1; i < size - 1; i += 1) {
            if (this.squares[i][size - 1] == 0) {
                return true;
            }
        }
        return false;
    }

    private Iterable<Board> rightSideNeighbors() {
        Board[] neighbors = new Board[3];
        int[][] newTiles = new int[size][size];
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                newTiles[i][j] = squares[i][j];
            }
        }
        int rowID = 0;
        int colID = size - 1;
        for (int i = 1; i < size - 1; i += 1) {
            if (squares[i][colID] == 0) {
                rowID = i;
            }
        }
        newTiles[rowID][colID - 1] = 0; // move right
        newTiles[rowID][colID] = squares[rowID][colID - 1];
        neighbors[0] = new Board(newTiles);

        newTiles[rowID][colID - 1] = squares[rowID][colID - 1]; // recover
        newTiles[rowID - 1][colID] = 0; // move down
        newTiles[rowID][colID] = squares[rowID - 1][colID];
        neighbors[1] = new Board(newTiles);

        newTiles[rowID - 1][colID] = squares[rowID - 1][colID]; // recover
        newTiles[rowID + 1][colID] = 0; // move up
        newTiles[rowID][colID] = squares[rowID + 1][colID];
        neighbors[2] = new Board(newTiles);
        return Arrays.asList(neighbors);
    }

    private Iterable<Board> innerNeighbors() {
        Board[] neighbors = new Board[4];
        int[][] newTiles = new int[size][size];
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                newTiles[i][j] = squares[i][j];
            }
        }
        int rowID = 0;
        int colID = 0;
        for (int i = 1; i < size - 1; i += 1) {
            for (int j = 1; j < size - 1; j += 1) {
                if (squares[i][j] == 0) {
                    rowID = i;
                    colID = j;
                }
            }
        }

        newTiles[rowID][colID - 1] = 0; // move right
        newTiles[rowID][colID] = squares[rowID][colID - 1];
        neighbors[0] = new Board(newTiles);

        newTiles[rowID][colID - 1] = squares[rowID][colID - 1]; // recover
        newTiles[rowID - 1][colID] = 0; // move down
        newTiles[rowID][colID] = squares[rowID - 1][colID];
        neighbors[1] = new Board(newTiles);

        newTiles[rowID - 1][colID] = squares[rowID - 1][colID]; // recover
        newTiles[rowID + 1][colID] = 0; // move up
        newTiles[rowID][colID] = squares[rowID + 1][colID];
        neighbors[2] = new Board(newTiles);

        newTiles[rowID + 1][colID] = squares[rowID + 1][colID]; // recover
        newTiles[rowID][colID + 1] = 0; // move left
        newTiles[rowID][colID] = squares[rowID][colID + 1];
        neighbors[3] = new Board(newTiles);

        return Arrays.asList(neighbors);
    }

    // private boolean isInnerTile() {
    //     return (!(isSide() || isCorner()));
    // }

    // private boolean isCorner() {
    //     return (isLowerLeftCorner() || isLowerRightCorner() || isUpperLeftCorner()
    //             || isUpperRightCorner());
    // }
    //
    // private boolean isSide() {
    //     return (isUpperSide() || isLowerSide() || isLeftSide() || isRightSide());
    // }


    public static void main(String[] args) {
        // int[][] data1 = new int[][] { { 1, 2, 3 }, { 6, 5, 7 }, { 8, 0, 4 } };
        // Board B1 = new Board(data1);
        // System.out.println(B1.toString());
        // System.out.println(B1.hamming());
        // System.out.println(B1.manhattan());
        // System.out.println(B1.twin());
        //
        // int[][] data2 = new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        // Board B2 = new Board(data2);
        // System.out.println(B2.toString());
        // System.out.println(B2.hamming());
        // System.out.println(B2.manhattan());
        // System.out.println(B2.twin());

        int[][] data3 = new int[][] {
                { 2, 0, 3, 4 }, { 1, 10, 6, 8 }, { 5, 9, 7, 12 }, { 13, 14, 11, 15 }
        };
        Board B3 = new Board(data3);
        for (Board b : B3.neighbors()) {
            System.out.println(b);
        }


    }
}
