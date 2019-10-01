import edu.princeton.cs.algs4.Stack;
import java.util.Arrays;

public class Board {
	private final int n;
	private final int[][] tiles;

	// create a board from an n-by-n array of tiles,
	// where tiles[row][col] = tile at (row, col)
	public Board(int[][] tiles) {
		this.n = tiles.length;

		this.tiles = new int[this.n][];

		for (int i = 0; i < this.n; i++) {
			this.tiles[i] = Arrays.copyOf(tiles[i], this.n);
		}
	}

	private int tile(int y, int x) {
		return y * this.n + x + 1;
	}

	private int row(int n) {
		return (n - 1) / this.n;
	}

	private int column(int n) {
		return (n - 1) % this.n;
	}

	// string representation of this board
	public String toString() {
		final StringBuilder str = new StringBuilder(this.n + "\n");

		for (int[] row : this.tiles) {
			for (int val : row) {
				str.append("\t" + val);
			}
			str.append("\n");
		}

		return str.toString();
	}

	// board dimension n
	public int dimension() {
		return this.n;
	}

	// number of tiles out of place
	public int hamming() {
		int hamm = 0;

		for (int y = 0; y < this.n; y++) {
			for (int x = 0; x < this.n; x++) {
				if (this.tiles[y][x] != 0 && this.tiles[y][x] != this.tile(y, x)) {
					hamm++;
				}
			}
		}

		return hamm;
	}

	// sum of Manhattan distances between tiles and goal
	public int manhattan() {
		int manh = 0;

		for (int y = 0; y < this.n; y++) {
			for (int x = 0; x < this.n; x++) {
				if (this.tiles[y][x] != 0 && this.tiles[y][x] != this.tile(y, x)) {
					manh += Math.abs(y - this.row(this.tiles[y][x])) + Math.abs(x - this.column(this.tiles[y][x]));
				}
			}
		}

		return manh;
	}

	// is this board the goal board?
	public boolean isGoal() {
		return this.hamming() == 0;
	}

	// does this board equal y?
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}

		if (that == null || this.getClass() != that.getClass()) {
			return false;
		}

		if (this.n != ((Board) that).dimension()) {
			return false;
		}

		for (int y = 0; y < this.n; y++) {
			for (int x = 0; x < this.n; x++) {
				if (this.tiles[y][x] != ((Board) that).tiles[y][x]) {
					return false;
				}
			}
		}

		return true;
	}

	private boolean swap(int y, int x, int newY, int newX) {
		if (newY < 0 || newY >= this.n || newX < 0 || newX >= this.n) {
			return false;
		}

		final int tmp = this.tiles[y][x];

		this.tiles[y][x] = this.tiles[newY][newX];
		this.tiles[newY][newX] = tmp;

		return true;
	}

	// all neighboring boards
	public Iterable<Board> neighbors() {
		int y = 0, x = 0;
		boolean found = false;

		for (; y < this.n; y++) {
			for (x = 0; x < this.n; x++) {
				if (this.tiles[y][x] == 0) {
					found = true;
					break;
				}
			}
			if (found) {
				break;
			}
		}

		Stack<Board> neighboards = new Stack<Board>();

		Board b = new Board(this.tiles);
		if (b.swap(y, x, y - 1, x)) {
			neighboards.push(b);
		}

		b = new Board(this.tiles);
		if (b.swap(y, x, y, x + 1)) {
			neighboards.push(b);
		}

		b = new Board(this.tiles);
		if (b.swap(y, x, y + 1, x)) {
			neighboards.push(b);
		}

		b = new Board(this.tiles);
		if (b.swap(y, x, y, x - 1)) {
			neighboards.push(b);
		}

		return neighboards;
	}

	// a board that is obtained by exchanging any pair of tiles
	public Board twin() {
		Board b = new Board(this.tiles);

		if (b.tiles[0][0] != 0 && b.tiles[0][1] != 0) {
			b.swap(0, 0, 0, 1);
		} else {
			b.swap(1, 0, 1, 1);
		}

		return b;
	}

	// unit testing (not graded)
	public static void main(String[] args) {
	}
}
