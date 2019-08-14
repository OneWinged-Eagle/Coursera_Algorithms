import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {
	private final ArrayList<LineSegment> segments;

	// finds all line segments containing 4 or more points
	public FastCollinearPoints(Point[] points) {
		if (points == null) {
			throw new IllegalArgumentException("points can't be null");
		}

		Point[] myPoints = new Point[points.length];
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null) {
				throw new IllegalArgumentException("points can't be null");
			}

			myPoints[i] = points[i];
		}

		Arrays.sort(myPoints);
		for (int i = 0; i < points.length - 1; i++) {
			if (myPoints[i].compareTo(myPoints[i + 1]) == 0) {
				throw new IllegalArgumentException("points can't have duplicates");
			}
		}

		this.segments = new ArrayList<LineSegment>();

		for (int p = 0; p < points.length - 3; p++) {
			Arrays.sort(myPoints, myPoints[p].slopeOrder());

			for (int q = 2; q < points.length; q++) {
				int first = q - 1;
				while (q < points.length
						&& Double.compare(myPoints[0].slopeTo(myPoints[first]), myPoints[0].slopeTo(myPoints[q])) == 0) {
					q++;
				}

				if (q - first >= 3 && myPoints[0].compareTo(myPoints[first]) < 0) {
					this.segments.add(new LineSegment(myPoints[0], myPoints[q - 1]));
				}
			}

			Arrays.sort(myPoints);
		}
	}

	// the number of line segments
	public int numberOfSegments() {
		return this.segments.size();
	}

	// the line segments
	public LineSegment[] segments() {
		return this.segments.toArray(new LineSegment[this.segments.size()]);
	}
}
