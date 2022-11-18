/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public final class Board {
    private final int size;
    private final int[][] squares;

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
        String res = String.format("%d", size);
        for (int i = 0; i < size; i += 1) {
            String newLine = "";
            for (int j = 0; j < size; j += 1) {
                newLine = String.format(newLine + "  %d", squares[i][j]);
            }
            res = String.format(res + "%n" + newLine);
        }
        return res;
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int res = 0;
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                if ((i * size + j + 1) != squares[i][j]) {
                    res += 1;
                }
            }
        }
        return res;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int res = manhattan();
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
        if (isUpperRightCorner()) {
            return upperRightNeighbors();
        }

    }

    private Boolean isUpperLeftCorner() {
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

    private Boolean isUpperRightCorner() {
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

    private Boolean isLowerLeftCorner() {
        return (this.squares[size - 1][0] == 0);
    }

    private Boolean isLowerRightCorner() {
        return (this.squares[size - 1][size - 1] == 0);
    }

    private Boolean isUpperSide() {
        for (int i = 1; i < size - 1; i += 1) {
            if (this.squares[0][i] == 0) {
                return true;
            }
        }
        return false;
    }

    private Boolean isLowerSide() {
        for (int i = 1; i < size - 1; i += 1) {
            if (this.squares[size - 1][i] == 0) {
                return true;
            }
        }
        return false;
    }

    private Boolean isLeftSide() {
        for (int i = 1; i < size - 1; i += 1) {
            if (this.squares[i][0] == 0) {
                return true;
            }
        }
        return false;
    }

    private Boolean isRightSide() {
        for (int i = 1; i < size - 1; i += 1) {
            if (this.squares[i][size - 1] == 0) {
                return true;
            }
        }
        return false;
    }

    private Boolean isInnerTile() {
        return (!(isSide() || isCorner()));
    }

    private Boolean isCorner() {
        return (isLowerLeftCorner() || isLowerRightCorner() || isUpperLeftCorner()
                || isUpperRightCorner());
    }

    private Boolean isSide() {
        return (isUpperSide() || isLowerSide() || isLeftSide() || isRightSide());
    }


    public static void main(String[] args) {
        // int[][] data = new int[][] { { 1, 2, 3 }, { 6, 5, 7 }, { 8, 2, 9 } };
        // Board B = new Board(data);
        // System.out.println(B.toString());

    }
}
