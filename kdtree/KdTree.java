import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
	private class Node {
		private final Point2D point;
		private Node left;
		private Node right;
		private boolean byX;

		public Node(Point2D point) {
			this.point = point;
		}
	}

	private Node head;
	private int length;

	// construct an empty set of points
	public KdTree() {
	}

	// is the set empty?
	public boolean isEmpty() {
		return this.head == null;
	}

	// number of points in the set
	public int size() {
		return this.length;
	}

	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException("p is null");
		}
	}

	// does the set contain point p?
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException("p is null");
		}

		return false;
	}

	// draw all points to standard draw
	public void draw() {
	}

	// all points that are inside the rectangle (or on the boundary)
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new IllegalArgumentException("rect is null");
		}

		return null;
	}

	// a nearest neighbor in the set to point p; null if the set is empty
	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException("p is null");
		}

		if (this.isEmpty()) {
			return null;
		}

		return p;
	}

	// unit testing of the methods (optional)
	public static void main(String[] args) {
		// EMPTY
	}
}
