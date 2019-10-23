import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

public class KdTree {
	private class Node {
		private final Point2D point;
		private final RectHV rect;
		private Node left;
		private Node right;
		private final boolean byX;

		public Node(Point2D point, RectHV rect, boolean byX) {
			this.point = point;
			this.rect = rect;
			this.byX = byX;
		}

		public Node(Point2D point) {
			this(point, new RectHV(0, 0, 1, 1), true);
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

	private RectHV getRect(Node parent, boolean larger) {
		if (parent.byX) {
			if (larger) {
				return new RectHV(parent.point.x(), parent.rect.ymin(), parent.rect.xmax(), parent.rect.ymax());
			} else {
				return new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.point.x(), parent.rect.ymax());
			}
		} else {
			if (larger) {
				return new RectHV(parent.rect.xmin(), parent.point.y(), parent.rect.xmax(), parent.rect.ymax());
			} else {
				return new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.rect.xmax(), parent.point.y());
			}
		}
	}

	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException("p is null");
		}

		if (this.head == null) {
			this.head = new Node(p);
			this.length = 1;
			return;
		}

		Node curr = this.head;

		while (true) {
			if (curr.point.equals(p)) {
				return;
			}

			if ((curr.byX && p.x() <= curr.point.x()) || (!curr.byX && p.y() <= curr.point.y())) {
				if (curr.left == null) {
					curr.left = new Node(p, this.getRect(curr, false), !curr.byX);
					this.length += 1;
					return;
				}

				curr = curr.left;
			} else {
				if (curr.right == null) {
					curr.right = new Node(p, this.getRect(curr, true), !curr.byX);
					this.length += 1;
					return;
				}

				curr = curr.right;
			}
		}
	}

	// does the set contain point p?
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException("p is null");
		}

		Node curr = this.head;

		while (curr != null) {
			if (curr.point.equals(p)) {
				return true;
			}

			if ((curr.byX && p.x() <= curr.point.x()) || (!curr.byX && p.y() <= curr.point.y())) {
				if (curr.left == null) {
					return false;
				}

				curr = curr.left;
			} else {
				if (curr.right == null) {
					return false;
				}

				curr = curr.right;
			}
		}

		return false;
	}

	// draw all points to standard draw
	public void draw() {
		// TODO?
	}

	private void range(Node curr, RectHV rect, Stack<Point2D> points) {
		if (curr == null) {
			return;
		}

		if (rect.contains(curr.point)) {
			points.push(curr.point);
		}

		if (curr.byX) {
			if (curr.point.x() >= rect.xmin()) {
				range(curr.left, rect, points);
			}

			if (curr.point.x() <= rect.xmax()) {
				range(curr.right, rect, points);
			}
		} else {
			if (curr.point.y() >= rect.ymin()) {
				range(curr.left, rect, points);
			}

			if (curr.point.y() <= rect.ymax()) {
				range(curr.right, rect, points);
			}
		}
	}

	// all points that are inside the rectangle (or on the boundary)
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new IllegalArgumentException("rect is null");
		}

		Stack<Point2D> points = new Stack<Point2D>();

		this.range(this.head, rect, points);

		return points;
	}

	private Point2D nearest(Node curr, Point2D p, Point2D n) {
		if (curr == null) {
			return n;
		}

		double dist = curr.point.distanceSquaredTo(p);

		if (dist < n.distanceSquaredTo(p)) {
			n = curr.point;
		}

		boolean left = false, right = false;

		if (curr.left != null && curr.left.rect.distanceSquaredTo(p) < dist) {
			left = true;
		}

		if (curr.right != null && curr.right.rect.distanceSquaredTo(p) < dist) {
			right = true;
		}

		if (left && right) {
			if (curr.left.rect.contains(p)) {
				n = this.nearest(curr.left, p, n);
				n = this.nearest(curr.right, p, n);
			} else {
				n = this.nearest(curr.right, p, n);
				n = this.nearest(curr.left, p, n);
			}
		} else if (left) {
			n = this.nearest(curr.left, p, n);
		} else if (right) {
			n = this.nearest(curr.right, p, n);
		}

		return n;
	}

	// a nearest neighbor in the set to point p; null if the set is empty
	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException("p is null");
		}

		if (this.isEmpty()) {
			return null;
		}

		return this.nearest(this.head, p, this.head.point);
	}

	// unit testing of the methods (optional)
	public static void main(String[] args) {
		// EMPTY
	}
}
