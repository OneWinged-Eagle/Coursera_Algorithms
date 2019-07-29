import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private final double mean;
	private final double stddev;
	private final double confidenceLo;
	private final double confidenceHi;

	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException("n or trials is less than 0");
		}

		double[] x = new double[trials];
		for (int i = 0; i < trials; i++) {
			Percolation percolation = new Percolation(n);
			while (!percolation.percolates()) {
				percolation.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
			}
			x[i] = (double) percolation.numberOfOpenSites() / (n * n);
		}

		this.mean = StdStats.mean(x);
		this.stddev = StdStats.stddev(x);
		this.confidenceLo = this.mean - ((1.96 * this.stddev) / (Math.sqrt(trials)));
		this.confidenceHi = this.mean + ((1.96 * this.stddev) / (Math.sqrt(trials)));
	}

	// sample mean of percolation threshold
	public double mean() {
		return this.mean;
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return this.stddev;
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return this.confidenceLo;
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return this.confidenceHi;
	}

	// test client (see below)
	public static void main(String[] args) {
		if (args.length != 2) {
			throw new IllegalArgumentException("need two args");
		}

		PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

		System.out.printf("mean                    = %f%n", percolationStats.mean());
		System.out.printf("stddev                  = %f%n", percolationStats.stddev());
		System.out.printf("95% confidence interval = [%f, %f]%n", percolationStats.confidenceLo(),
				percolationStats.confidenceHi());
	}
}
