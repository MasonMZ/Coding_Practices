/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture workingPic;
    private double[][] energyArray;
    private Digraph workingGraph;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }
        workingPic = new Picture(picture);
        int w = width();
        int h = height();
        energyArray = new double[w][h];
        for (int i = 0; i < w; i += 1) {
            for (int j = 0; j < h; j += 1) {
                energyArray[i][j] = pixelwisedEnergy(i, j);
            }
        }
    }

    // current picture
    public Picture picture() {
        return workingPic;
    }

    // width of current picture
    public int width() {
        return workingPic.width();
    }

    // height of current picture
    public int height() {
        return workingPic.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x > (width() - 1) || y < 0 || y > (height() - 1)) {
            throw new IllegalArgumentException();
        }
        if (x == 0 || x == (width() - 1) || y == 0 || y == (height() - 1)) {
            return 1000;
        }
        return energyArray[x][y];
    }

    private double xGradientSquared(int x, int y) {
        int left = x - 1;
        int right = x + 1;
        int rgbLeft = workingPic.getRGB(left, y);
        int rgbRight = workingPic.getRGB(right, y);
        double r_xSquared = Math.pow(
                ((double) ((rgbLeft >> 16) & 0xFF) - ((rgbRight >> 16) & 0xFF)), 2);
        double g_xSquared = Math.pow(
                ((double) ((rgbLeft >> 8) & 0xFF) - ((rgbRight >> 8) & 0xFF)), 2);
        double b_xSquared = Math.pow(
                ((double) ((rgbLeft >> 0) & 0xFF) - ((rgbRight >> 0) & 0xFF)), 2);
        return r_xSquared + g_xSquared + b_xSquared;
    }

    private double yGradientSquared(int x, int y) {
        int upside = y - 1;
        int downside = y + 1;
        int rgbUp = workingPic.getRGB(x, upside);
        int rgbDown = workingPic.getRGB(x, downside);
        double r_ySquared = Math.pow(
                ((double) ((rgbUp >> 16) & 0xFF) - ((rgbDown >> 16) & 0xFF)), 2);
        double g_ySquared = Math.pow(
                ((double) ((rgbUp >> 8) & 0xFF) - ((rgbDown >> 8) & 0xFF)), 2);
        double b_ySquared = Math.pow(
                ((double) ((rgbUp >> 0) & 0xFF) - ((rgbDown >> 0) & 0xFF)), 2);
        // double r_ySquared = Math.pow(
        //         ((double) workingPic.get(x, upside).getRed() - workingPic.get(x, downside)
        //                                                                  .getRed()), 2);
        // double g_ySquared = Math.pow(
        //         ((double) workingPic.get(x, upside).getGreen() - workingPic.get(x, downside)
        //                                                                    .getGreen()),
        //         2);
        // double b_ySquared = Math.pow(
        //         ((double) workingPic.get(x, upside).getBlue() - workingPic.get(x, downside)
        //                                                                   .getBlue()),
        //         2);
        return r_ySquared + g_ySquared + b_ySquared;
    }

    private double pixelwisedEnergy(int x, int y) {
        if (x < 0 || x > (width() - 1) || y < 0 || y > (height() - 1)) {
            throw new IllegalArgumentException();
        }
        if (x == 0 || x == (width() - 1) || y == 0 || y == (height() - 1)) {
            return 1000;
        }
        return (Math.sqrt(xGradientSquared(x, y) + yGradientSquared(x, y)));
    }

    private void pic2Graph() {
        workingGraph = new Digraph(width() * (height() - 2) + 2);
        for (int i = 0; i < width(); i += 1) {
            workingGraph.addEdge(0, i + 1);
            workingGraph.addEdge(width() * (height() - 3) + i, width() * (height() - 2) + 1);
        }
        for (int row = 0; row < (height() - 3); row += 1) {
            for (int col = 0; col < width(); col += 1) {
                connect(workingGraph, col, row);
            }
        }
    }

    private void connect(Digraph d, int x, int y) {
        int target = y * width() + x + 1;
        if (x == 0) {
            int targetDownSide = (y + 1) * width() + x + 1;
            int targetLowerRight = (y + 1) * width() + x + 2;
            d.addEdge(target, targetDownSide);
            d.addEdge(target, targetLowerRight);
        }
        else if (x == width() - 1) {
            int targetDownSide = (y + 1) * width() + x + 1;
            int targetLowerLeft = (y + 1) * width() + x;
            d.addEdge(target, targetDownSide);
            d.addEdge(target, targetLowerLeft);
        }
        else {
            int targetLowerLeft = (y + 1) * width() + x;
            int targetDownSide = (y + 1) * width() + x + 1;
            int targetLowerRight = (y + 1) * width() + x + 2;
            d.addEdge(target, targetLowerLeft);
            d.addEdge(target, targetDownSide);
            d.addEdge(target, targetLowerRight);
        }
    }


    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        throw new UnsupportedOperationException();
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        throw new UnsupportedOperationException();
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        throw new UnsupportedOperationException();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {

    }
}
