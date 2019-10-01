import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
	private final Stack<Board> solutions;
	private final int mv;

	private class Node implements Comparable<Node> {
		private Board board;
		private int moves;
		private Node prev;
		private int cachedPriority = -1;

		public Node(Board board, int moves, Node prev) {
			this.board = board;
			this.moves = moves;
			this.prev = prev;
		}

		public Node(Board board) {
			this(board, 0, null);
		}

		private int priority() {
			if (this.cachedPriority == -1) {
				this.cachedPriority = this.moves + this.board.manhattan();
			}
			return this.cachedPriority;
		}

		@Override
		public int compareTo(Node that) {
			return this.priority() - that.priority();
		}
	}

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		if (initial == null) {
			throw new IllegalArgumentException("initial is null");
		}

		final MinPQ<Node> queue = new MinPQ<Node>();
		final MinPQ<Node> clone = new MinPQ<Node>();

		Node curr = new Node(initial);
		Node currClone = new Node(initial.twin());
		while (!curr.board.isGoal() && !currClone.board.isGoal()) {
			for (Board b : curr.board.neighbors()) {
				if (curr.prev == null || !b.equals(curr.prev.board)) {
					queue.insert(new Node(b, curr.moves + 1, curr));
				}
			}
			curr = queue.delMin();

			for (Board b : currClone.board.neighbors()) {
				if (currClone.prev == null || !b.equals(currClone.prev.board)) {
					clone.insert(new Node(b, currClone.moves + 1, currClone));
				}
			}
			currClone = clone.delMin();
		}

		if (curr.board.isGoal()) {
			this.mv = curr.moves;

			this.solutions = new Stack<Board>();
			while (curr != null) {
				this.solutions.push(curr.board);
				curr = curr.prev;
			}
		} else {
			this.solutions = null;
			this.mv = -1;
		}
	}

	// is the initial board solvable? (see below)
	public boolean isSolvable() {
		return this.mv != -1;
	}

	// min number of moves to solve initial board
	public int moves() {
		return this.mv;
	}

	// sequence of boards in a shortest solution
	public Iterable<Board> solution() {
		return this.solutions;
	}

	// test client (see below)
	public static void main(String[] args) {
	}
}
