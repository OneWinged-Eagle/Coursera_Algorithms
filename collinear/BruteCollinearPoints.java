import java.util.Arrays;
import java.util.ArrayList;

public class BruteCollinearPoints {
	private final ArrayList<LineSegment> segments;

	// finds all line segments containing 4 points
	public BruteCollinearPoints(Point[] points) {
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

			for (int q = p + 1; q < points.length - 2; q++) {
				double slope = myPoints[p].slopeTo(myPoints[q]);

				for (int r = q + 1; r < points.length - 1; r++) {
					if (Double.compare(slope, myPoints[p].slopeTo(myPoints[r])) != 0) {
						continue;
					}

					for (int s = r + 1; s < points.length; s++) {
						if (Double.compare(slope, myPoints[p].slopeTo(myPoints[s])) == 0) {
							this.segments.add(new LineSegment(myPoints[p], myPoints[s]));
						}
					}
				}
			}
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
