import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

public class PointSET {
	private final TreeSet<Point2D> set;

	// construct an empty set of points
	public PointSET() {
		this.set = new TreeSet<Point2D>();
	}

	// is the set empty?
	public boolean isEmpty() {
		return this.set.isEmpty();
	}

	// number of points in the set
	public int size() {
		return this.set.size();
	}

	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException("p is null");
		}

		this.set.add(p);
	}

	// does the set contain point p?
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException("p is null");
		}

		return this.set.contains(p);
	}

	// draw all points to standard draw
	public void draw() {
		for (Point2D p : this.set) {
			p.draw();
		}
	}

	// all points that are inside the rectangle (or on the boundary)
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new IllegalArgumentException("rect is null");
		}

		Stack<Point2D> points = new Stack<Point2D>();

		for (Point2D p : this.set) {
			if (rect.distanceSquaredTo(p) == 0) {
				points.push(p);
			}
		}

		return points;
	}

	// a nearest neighbor in the set to point p; null if the set is empty
	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException("p is null");
		}

		if (this.isEmpty()) {
			return null;
		}

		Point2D nearestPoint = this.set.first();
		double dist = nearestPoint.distanceSquaredTo(p);

		for (Point2D point : this.set) {
			double d = point.distanceSquaredTo(p);

			if (d < dist) {
				nearestPoint = point;
				dist = d;
			}
		}

		return nearestPoint;
	}

	// unit testing of the methods (optional)
	public static void main(String[] args) {
		// EMPTY
	}
}
