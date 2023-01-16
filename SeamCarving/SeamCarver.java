/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Topological;

public class SeamCarver {
    private Picture workingPic;
    private double[][] energyArray;
    private Digraph workingGraph;
    private boolean isTransposed = false;


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
        if (isTransposed) {
            transpose(workingPic);
            Picture p = new Picture(workingPic);
            transpose(workingPic);
            return p;
        }
        return new Picture(workingPic);
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
        return energyArray[x][y];
    }

    private double xGradientSquared(int x, int y) {
        int left = x - 1;
        int right = x + 1;
        int rgbLeft = workingPic.getRGB(left, y);
        int rgbRight = workingPic.getRGB(right, y);
        double rxSquared = Math.pow(
                ((double) ((rgbLeft >> 16) & 0xFF) - (double) ((rgbRight >> 16) & 0xFF)), 2);
        double gxSquared = Math.pow(
                ((double) ((rgbLeft >> 8) & 0xFF) - (double) ((rgbRight >> 8) & 0xFF)), 2);
        double bxSquared = Math.pow(
                ((double) ((rgbLeft >> 0) & 0xFF) - (double) ((rgbRight >> 0) & 0xFF)), 2);
        return rxSquared + gxSquared + bxSquared;
    }

    private double yGradientSquared(int x, int y) {
        int upside = y - 1;
        int downside = y + 1;
        int rgbUp = workingPic.getRGB(x, upside);
        int rgbDown = workingPic.getRGB(x, downside);
        double rySquared = Math.pow(
                ((double) ((rgbUp >> 16) & 0xFF) - (double) ((rgbDown >> 16) & 0xFF)), 2);
        double gySquared = Math.pow(
                ((double) ((rgbUp >> 8) & 0xFF) - (double) ((rgbDown >> 8) & 0xFF)), 2);
        double bySquared = Math.pow(
                ((double) ((rgbUp >> 0) & 0xFF) - (double) ((rgbDown >> 0) & 0xFF)), 2);
        return rySquared + gySquared + bySquared;
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
        int w = width();
        int h = height();
        workingGraph = new Digraph(w * (h - 2) + 2);
        for (int i = 0; i < w; i += 1) {
            workingGraph.addEdge(0, i + 1);
            workingGraph.addEdge(w * (h - 3) + i + 1, w * (h - 2) + 1);
        }
        for (int row = 0; row < (h - 3); row += 1) {
            for (int col = 0; col < w; col += 1) {
                connect(workingGraph, col, row);
            }
        }
    }

    private void connect(Digraph d, int x, int y) {
        int w = width();
        int target = y * w + x + 1;
        if (x == 0) {
            int targetDownSide = (y + 1) * w + x + 1;
            int targetLowerRight = (y + 1) * w + x + 2;
            d.addEdge(target, targetDownSide);
            d.addEdge(target, targetLowerRight);
        }
        else if (x == w - 1) {
            int targetDownSide = (y + 1) * w + x + 1;
            int targetLowerLeft = (y + 1) * w + x;
            d.addEdge(target, targetDownSide);
            d.addEdge(target, targetLowerLeft);
        }
        else {
            int targetLowerLeft = (y + 1) * w + x;
            int targetDownSide = (y + 1) * w + x + 1;
            int targetLowerRight = (y + 1) * w + x + 2;
            d.addEdge(target, targetLowerLeft);
            d.addEdge(target, targetDownSide);
            d.addEdge(target, targetLowerRight);
        }
    }


    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (workingPic.height() == 1) {
            int[] res = new int[width()];
            return res;
        }
        if (workingPic.width() == 1) {
            return new int[] { 0 };
        }
        if (workingPic.width() == 2) {
            return new int[] { 0, 0 };
        }
        if (!isTransposed) {
            transpose(workingPic);
            isTransposed = !isTransposed;
        }
        pic2Graph();
        return minimizeEnergyPath();
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        if (workingPic.width() == 1) {
            int[] res = new int[height()];
            return res;
        }
        if (workingPic.height() == 1) {
            return new int[] { 0 };
        }
        if (workingPic.height() == 2) {
            return new int[] { 0, 0 };
        }
        if (isTransposed) {
            transpose(workingPic);
            isTransposed = !isTransposed;
        }
        pic2Graph();
        return minimizeEnergyPath();
    }

    private int[] minimizeEnergyPath() {
        Topological topo = new Topological(workingGraph);
        int verticesNum = workingGraph.V();
        double[] totalEnergy = new double[verticesNum];
        for (int i = 0; i < verticesNum; i += 1) {
            totalEnergy[i] = Double.POSITIVE_INFINITY;
        }
        totalEnergy[0] = 1000;
        int[] fromVertex = new int[verticesNum];
        for (int i : topo.order()) {
            if (i < verticesNum - 1) {
                relax(i, totalEnergy, fromVertex);
            }
        }
        Stack<Integer> path = new Stack<>();
        for (int i = fromVertex[verticesNum - 1]; i != 0; i = fromVertex[i]) {
            path.push(i);
        }
        return pathStack2Array(path);
    }

    private int[] pathStack2Array(Stack<Integer> s) {
        int h = height();
        int w = width();
        int[] res = new int[h];
        int count = 0;
        for (int i : s) {
            count += 1;
            res[count] = i % w - 1;
        }
        res[0] = res[1];
        res[count + 1] = res[count];
        return res;
    }

    private void relax(int vertex, double[] totalEnergy, int[] fromVertex) {
        for (int i : verticesFrom(vertex)) {
            if (totalEnergy[vertex] + energyOf(i) < totalEnergy[i]) {
                totalEnergy[i] = totalEnergy[vertex] + energyOf(i);
                fromVertex[i] = vertex;
            }
        }
    }

    private Iterable<Integer> verticesFrom(int i) {
        Stack<Integer> s = new Stack<>();
        int h = height();
        int w = width();
        if (i == 0) {
            for (int j = 0; j < w; j += 1) {
                s.push(j + 1);
            }
        }
        else if (i > (h - 3) * w && i < (h - 2) * w + 1) {
            s.push((h - 2) * w + 1);
        }
        else if (i % w == 1) {
            s.push(i + w);
            s.push(i + w + 1);
        }
        else if (i % w == 0) {
            s.push(i + w - 1);
            s.push(i + w);
        }
        else {
            s.push(i + w - 1);
            s.push(i + w);
            s.push(i + w + 1);
        }
        return s;
    }

    private double energyOf(int i) {
        int h = height();
        int w = width();
        if (i == 0 || i == (h - 2) * w + 1) {
            return 1000;
        }
        else {
            return energyArray[(i - 1) % w][(i - 1) / w + 1];
        }
    }

    private void transpose(Picture p) {
        int h = height();
        int w = width();
        Picture auxPic = new Picture(h, w);
        double[][] auxEnergyArray = new double[h][w];
        for (int i = 0; i < h; i += 1) {
            for (int j = 0; j < w; j += 1) {
                auxPic.set(i, j, p.get(j, i));
                auxEnergyArray[i][j] = energyArray[j][i];
            }
        }
        workingPic = auxPic;
        energyArray = auxEnergyArray;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (isTransposed) {
            transpose(workingPic);
            isTransposed = !isTransposed;
        }
        int w = width();
        int h = height();
        if (h <= 1) {
            throw new IllegalArgumentException();
        }
        if (seam == null || seam.length != w) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < w; i += 1) {
            if (seam[i] < 0 || seam[i] >= h) {
                throw new IllegalArgumentException();
            }
            if (i + 1 < w) {
                if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                    throw new IllegalArgumentException();
                }
            }
        }

        double[][] auxArray = new double[w][h - 1];
        Picture auxPic = new Picture(w, h - 1);
        for (int i = 0; i < w; i += 1) {
            System.arraycopy(energyArray[i], 0, auxArray[i], 0, seam[i]);
            System.arraycopy(energyArray[i], seam[i] + 1, auxArray[i], seam[i], h - 1 - seam[i]);
            for (int j = 0; j < seam[i]; j += 1) {
                auxPic.set(i, j, workingPic.get(i, j));
            }
            for (int j = seam[i]; j < h - 1; j += 1) {
                auxPic.set(i, j, workingPic.get(i, j + 1));
            }
        }
        energyArray = auxArray;
        workingPic = auxPic;
        for (int i = 1; i < width() - 1; i += 1) {
            if (seam[i] == height()) {
                continue;
            }
            energyArray[i][seam[i]] = pixelwisedEnergy(i, seam[i]);
        }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (isTransposed) {
            transpose(workingPic);
            isTransposed = !isTransposed;
        }
        int w = width();
        int h = height();
        if (w <= 1) {
            throw new IllegalArgumentException();
        }
        if (seam == null || seam.length != h) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < h; i += 1) {
            if (seam[i] < 0 || seam[i] >= w) {
                throw new IllegalArgumentException();
            }
            if (i + 1 < h) {
                if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                    throw new IllegalArgumentException();
                }
            }
        }
        double[][] auxArray = new double[w - 1][h];
        Picture auxPic = new Picture(w - 1, h);
        for (int i = 0; i < h; i += 1) {
            for (int j = 0; j < seam[i]; j += 1) {
                auxArray[j][i] = energyArray[j][i];
                auxPic.set(j, i, workingPic.get(j, i));
            }
            for (int j = seam[i]; j < w - 1; j += 1) {
                auxArray[j][i] = energyArray[j + 1][i];
                auxPic.set(j, i, workingPic.get(j + 1, i));
            }
        }
        energyArray = auxArray;
        workingPic = auxPic;
        for (int i = 1; i < height() - 1; i += 1) {
            if (seam[i] == width()) {
                continue;
            }
            energyArray[seam[i]][i] = pixelwisedEnergy(seam[i], i);
        }
    }

    public static void main(String[] args) {
        Picture picture = new Picture("6x5.png");
        SeamCarver sc = new SeamCarver(picture);
        for (int row = 0; row < sc.height(); row++) {
            for (int col = 0; col < sc.width(); col++)
                StdOut.printf("%9.2f ", sc.energy(col, row));
            StdOut.println();
        }
        // StdOut.println();
        // StdOut.println();
        // for (double i : sc.energyArray[2]) {
        //     StdOut.printf("%9.2f ", i);
        // }
        // int[] res = sc.findVerticalSeam();
        int[] res = sc.findVerticalSeam();
        for (int i : res) {
            System.out.println(i);
        }
        // sc.removeVerticalSeam(res);
        sc.removeVerticalSeam(res);

        for (int row = 0; row < sc.height(); row++) {
            for (int col = 0; col < sc.width(); col++)
                StdOut.printf("%9.2f ", sc.energy(col, row));
            StdOut.println();
        }

    }
}
