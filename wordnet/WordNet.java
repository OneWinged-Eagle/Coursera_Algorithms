import java.util.ArrayList;

import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Topological;

public class WordNet {
	private final ST<Integer, String> synsets;
	private final ST<String, ArrayList<Integer>> words;
	private final SAP sap;

	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		if (synsets == null || hypernyms == null) {
			throw new IllegalArgumentException("arg is null");
		}

		this.synsets = new ST<Integer, String>();
		this.words = new ST<String, ArrayList<Integer>>();

		In s = new In(synsets), w = new In(hypernyms);

		String line = s.readLine();
		int nbLines = 0;
		while (line != null) {
			String[] lineArr = line.split(",");
			int id = Integer.parseInt(lineArr[0]);

			this.synsets.put(id, lineArr[1]);
			for (String word : lineArr[1].split(" ")) {
				ArrayList<Integer> ids;

				if (this.words.contains(word)) {
					ids = this.words.get(word);
				} else {
					ids = new ArrayList<Integer>();
				}

				ids.add(id);
				this.words.put(word, ids);
			}

			nbLines++;
			line = s.readLine();
		}

		Digraph graph = new Digraph(nbLines);

		line = w.readLine();
		while (line != null) {
			String[] lineArr = line.split(",");
			int id = Integer.parseInt(lineArr[0]);

			for (int i = 1; i < lineArr.length; i++) {
				graph.addEdge(id, Integer.parseInt(lineArr[i]));
			}

			line = w.readLine();
		}

		DirectedCycle directedCycle = new DirectedCycle(graph);
		Topological topological = new Topological(graph);
		if (directedCycle.hasCycle() || !topological.hasOrder()) {
			throw new IllegalArgumentException("graph isn't DAG");
		}

		this.sap = new SAP(graph);
	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		return this.words;
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		if (word == null) {
			throw new IllegalArgumentException("word is null");
		}

		return this.words.contains(word);
	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		if (!this.isNoun(nounA) || !this.isNoun(nounB)) {
			throw new IllegalArgumentException("word doesn't exist");
		}

		return this.sap.length(this.words.get(nounA), this.words.get(nounB));
	}

	// a synset (second field of synsets.txt) that is the common ancestor of nounA
	// and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		if (!this.isNoun(nounA) || !this.isNoun(nounB)) {
			throw new IllegalArgumentException("word doesn't exist");
		}

		return this.synsets.get(this.sap.ancestor(this.words.get(nounA), this.words.get(nounB)));
	}

	// do unit testing of this class
	public static void main(String[] args) {
	}
}
