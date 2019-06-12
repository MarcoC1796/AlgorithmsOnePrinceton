/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class PercolationStats {
    private double[] fOpenSites;
    private int trials;

    public PercolationStats(int n,
                            int trials) { // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        this.trials = trials;
        fOpenSites = new double[trials];
        int row, col;
        int size = n * n;
        double openSites;
        Percolation pc;

        for (int i = 0; i < trials; i++) {
            pc = new Percolation(n);
            while (!pc.percolates()) {
                row = (int) (Math.random() * n + 1);
                col = (int) (Math.random() * n + 1);
                pc.open(row, col);
            }
            openSites = pc.numberOfOpenSites();
            fOpenSites[i] = openSites / size;
        }
    }

    public double mean() { // sample mean of percolation threshold
        double mean;
        double sum = 0;
        for (int i = 0; i < trials; i++) {
            sum += fOpenSites[i];
        }
        mean = sum / trials;
        return mean;
    }

    public double stddev() { // sample standard deviation of percolation threshold
        double mean = mean();
        double std;
        double sum = 0;
        for (int i = 0; i < trials; i++) {
            sum += Math.pow(fOpenSites[i] - mean, 2);
        }
        std = Math.sqrt(sum / (trials));
        return std;
    }

    public double confidenceLo() { // low  endpoint of 95% confidence interval
        double confidenceLo = mean() - 1.96 * stddev() / Math.sqrt(trials);
        return confidenceLo;
    }

    public double confidenceHi() { // high endpoint of 95% confidence interval
        double confidenceHi = mean() + 1.96 * stddev() / Math.sqrt(trials);
        return confidenceHi;
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(2,
                                                   10000);
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println(
                "95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
