import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {
	final private Digraph graph;
	private int minDist;
	private int ancestor;

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		if (G == null) {
			throw new IllegalArgumentException("G is null");
		}

		this.graph = new Digraph(G);
	}

	private void bfdp(int v, int w) {
		BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(this.graph, v),
				bfdpW = new BreadthFirstDirectedPaths(this.graph, w);

		this.minDist = -1;
		this.ancestor = -1;

		for (int i = 0; i < this.graph.V(); i++) {
			if (bfdpV.hasPathTo(i) && bfdpW.hasPathTo(i)) {
				int dist = bfdpV.distTo(i) + bfdpW.distTo(i);

				if (this.minDist < 0 || dist < this.minDist) {
					this.minDist = dist;
					this.ancestor = i;
				}
			}
		}
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		this.bfdp(v, w);
		return this.minDist;
	}

	// a common ancestor of v and w that participates in a shortest ancestral path;
	// -1 if no such path
	public int ancestor(int v, int w) {
		this.bfdp(v, w);
		return this.ancestor;
	}

	private void bfdp(Iterable<Integer> v, Iterable<Integer> w) {
		BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(this.graph, v),
				bfdpW = new BreadthFirstDirectedPaths(this.graph, w);

		this.minDist = -1;
		this.ancestor = -1;

		for (int i = 0; i < this.graph.V(); i++) {
			if (bfdpV.hasPathTo(i) && bfdpW.hasPathTo(i)) {
				int dist = bfdpV.distTo(i) + bfdpW.distTo(i);

				if (this.minDist < 0 || dist < this.minDist) {
					this.minDist = dist;
					this.ancestor = i;
				}
			}
		}
	}

	// length of shortest ancestral path between any vertex in v and any vertex in
	// w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null) {
			throw new IllegalArgumentException("arg is null");
		}

		this.bfdp(v, w);
		return this.minDist;
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such
	// path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null) {
			throw new IllegalArgumentException("arg is null");
		}

		this.bfdp(v, w);
		return this.ancestor;
	}

	// do unit testing of this class
	public static void main(String[] args) {
	}
}
