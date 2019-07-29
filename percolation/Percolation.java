import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private static final byte CLOSED = 0b000, OPENED = 0b001, CONNECTED_TO_TOP = 0b010, CONNECTED_TO_BOTTOM = 0b100;

	private final int n;
	private final WeightedQuickUnionUF uf;

	private byte[] grid;
	private int nbOpenSites;
	private boolean percolated;

	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("n is less than 0");
		}

		int gridSize = n * n;

		this.n = n;
		this.uf = new WeightedQuickUnionUF(gridSize);

		this.grid = new byte[gridSize];
	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {
		if (row <= 0 || row > this.n || col <= 0 || col > this.n) {
			throw new IllegalArgumentException("row and col are out of range");
		}

		int i = (row - 1) * this.n + (col - 1);

		if (this.grid[i] != CLOSED) {
			return;
		}

		this.grid[i] = OPENED;
		this.nbOpenSites++;

		int id = this.uf.find(i);

		if (row == 1) {
			this.grid[id] |= CONNECTED_TO_TOP;
		}
		if (row == this.n) {
			this.grid[id] |= CONNECTED_TO_BOTTOM;
		}

		byte newState = this.grid[id];

		if (row != 1 && this.grid[i - this.n] != CLOSED) {
			newState |= this.grid[this.uf.find(i - this.n)];
			this.uf.union(i, i - this.n);
		}

		if (col != this.n && this.grid[i + 1] != CLOSED) {
			newState |= this.grid[this.uf.find(i + 1)];
			this.uf.union(i, i + 1);
		}

		if (row != this.n && this.grid[i + this.n] != CLOSED) {
			newState |= this.grid[this.uf.find(i + this.n)];
			this.uf.union(i, i + this.n);
		}

		if (col != 1 && this.grid[i - 1] != CLOSED) {
			newState |= this.grid[this.uf.find(i - 1)];
			this.uf.union(i, i - 1);
		}

		id = this.uf.find(i);
		this.grid[id] |= newState;

		if (this.grid[id] == (OPENED | CONNECTED_TO_TOP | CONNECTED_TO_BOTTOM)) {
			this.percolated = true;
		}
	}

	// is the site (row, col) open?
	public boolean isOpen(int row, int col) {
		if (row <= 0 || row > this.n || col <= 0 || col > this.n) {
			throw new IllegalArgumentException("row and col are out of range");
		}

		return this.grid[(row - 1) * this.n + (col - 1)] != CLOSED;
	}

	// is the site (row, col) full?
	public boolean isFull(int row, int col) {
		if (row <= 0 || row > this.n || col <= 0 || col > this.n) {
			throw new IllegalArgumentException("row and col are out of range");
		}

		return (this.grid[this.uf.find((row - 1) * this.n + (col - 1))] & CONNECTED_TO_TOP) == CONNECTED_TO_TOP;
	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return this.nbOpenSites;
	}

	// does the system percolate?
	public boolean percolates() {
		return this.percolated;
	}
}
