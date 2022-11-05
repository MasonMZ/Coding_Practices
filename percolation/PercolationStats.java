/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] trialResults;
    private double confidence = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Dimension or trial_numbers should be positive.");
        }
        trialResults = new double[trials];
        for (int i = 0; i < trials; i += 1) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int randomNum = StdRandom.uniformInt(n * n);
                int randomRow = randomNum / n + 1;
                int randomCol = randomNum % n + 1;
                while (p.isOpen(randomRow, randomCol)) {
                    randomNum = StdRandom.uniformInt(n * n);
                    randomRow = randomNum / n + 1;
                    randomCol = randomNum % n + 1;
                }
                p.open(randomRow, randomCol);
            }
            trialResults[i] = ((double) p.numberOfOpenSites()) / ((double) n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trialResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trialResults);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (this.mean() - confidence * this.stddev() / Math.sqrt(
                (double) trialResults.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (this.mean() + confidence * this.stddev() / Math.sqrt(
                (double) trialResults.length));
    }


    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Please input two arguments.");
        }
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println(
                "95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");

        // /* Test case 1: n = 20, trials = 2000 **/
        // Stopwatch sw1 = new Stopwatch();
        // PercolationStats PS1 = new PercolationStats(20, 2000);
        // System.out.println();
        // System.out.println(PS1.mean());
        // System.out.println(PS1.stddev());
        // System.out.println(PS1.confidenceLo());
        // System.out.println(PS1.confidenceHi());
        // System.out.println(sw1.elapsedTime());
        //
        //
        // /* Test case 1a: n = 20, trials = 4000 **/
        // Stopwatch sw2 = new Stopwatch();
        // PercolationStats PS2 = new PercolationStats(20, 4000);
        // System.out.println();
        // System.out.println(PS2.mean());
        // System.out.println(PS2.stddev());
        // System.out.println(PS2.confidenceLo());
        // System.out.println(PS2.confidenceHi());
        // System.out.println(sw2.elapsedTime());
        //
        // /* Test case 1b: n = 20, trials = 8000 **/
        // Stopwatch sw3 = new Stopwatch();
        // PercolationStats PS3 = new PercolationStats(20, 8000);
        // System.out.println();
        // System.out.println(PS3.mean());
        // System.out.println(PS3.stddev());
        // System.out.println(PS3.confidenceLo());
        // System.out.println(PS3.confidenceHi());
        // System.out.println(sw3.elapsedTime());
        //
        // /* Test case 1c: n = 20, trials = 16000 **/
        // Stopwatch sw4 = new Stopwatch();
        // PercolationStats PS4 = new PercolationStats(20, 16000);
        // System.out.println();
        // System.out.println(PS4.mean());
        // System.out.println(PS4.stddev());
        // System.out.println(PS4.confidenceLo());
        // System.out.println(PS4.confidenceHi());
        // System.out.println(sw4.elapsedTime());
        //
        // /* Test case 2: n = 10, trials = 1000 **/
        // Stopwatch sw5 = new Stopwatch();
        // PercolationStats PS5 = new PercolationStats(10, 2000);
        // System.out.println();
        // System.out.println(PS5.mean());
        // System.out.println(PS5.stddev());
        // System.out.println(PS5.confidenceLo());
        // System.out.println(PS5.confidenceHi());
        // System.out.println(sw5.elapsedTime());
        //
        // /* Test case 2a: n = 20, trials = 1000 **/
        // Stopwatch sw6 = new Stopwatch();
        // PercolationStats PS6 = new PercolationStats(20, 1000);
        // System.out.println();
        // System.out.println(PS6.mean());
        // System.out.println(PS6.stddev());
        // System.out.println(PS6.confidenceLo());
        // System.out.println(PS6.confidenceHi());
        // System.out.println(sw6.elapsedTime());
        //
        // /* Test case 2b: n = 40, trials = 1000 **/
        // Stopwatch sw7 = new Stopwatch();
        // PercolationStats PS7 = new PercolationStats(40, 1000);
        // System.out.println();
        // System.out.println(PS7.mean());
        // System.out.println(PS7.stddev());
        // System.out.println(PS7.confidenceLo());
        // System.out.println(PS7.confidenceHi());
        // System.out.println(sw7.elapsedTime());
        //
        // /* Test case 2c: n = 80, trials = 1000 **/
        // Stopwatch sw8 = new Stopwatch();
        // PercolationStats PS8 = new PercolationStats(80, 1000);
        // System.out.println();
        // System.out.println(PS8.mean());
        // System.out.println(PS8.stddev());
        // System.out.println(PS8.confidenceLo());
        // System.out.println(PS8.confidenceHi());
        // System.out.println(sw8.elapsedTime());
        //
        // /* Test case 2d: n = 160, trials = 1000 **/
        // Stopwatch sw9 = new Stopwatch();
        // PercolationStats PS9 = new PercolationStats(80, 1000);
        // System.out.println();
        // System.out.println(PS9.mean());
        // System.out.println(PS9.stddev());
        // System.out.println(PS9.confidenceLo());
        // System.out.println(PS9.confidenceHi());
        // System.out.println(sw9.elapsedTime());

    }
}
