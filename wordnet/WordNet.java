import java.util.ArrayList;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;

public class WordNet {
	final private ST<Integer, String> synsets;
	final private ST<String, ArrayList<Integer>> words;
	final private Digraph graph;
	final private SAP sap;

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
			String[] l = line.split(",");
			int id = Integer.parseInt(l[0]);

			this.synsets.put(id, l[1]);
			for (String word : l[1].split(" ")) {
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

		this.graph = new Digraph(nbLines);

		line = w.readLine();
		while (line != null) {
			String[] l = line.split(",");
			int id = Integer.parseInt(l[0]);

			for (int i = 1; i < l.length; i++) {
				this.graph.addEdge(id, Integer.parseInt(l[i]));
			}

			line = w.readLine();
		}

		this.sap = new SAP(this.graph);
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
			throw new IllegalArgumentException("word doesn't null");
		}

		return this.sap.length(this.words.get(nounA), this.words.get(nounB));
	}

	// a synset (second field of synsets.txt) that is the common ancestor of nounA
	// and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		if (!this.isNoun(nounA) || !this.isNoun(nounB)) {
			throw new IllegalArgumentException("word doesn't null");
		}

		return this.synsets.get(this.sap.ancestor(this.words.get(nounA), this.words.get(nounB)));
	}

	// do unit testing of this class
	public static void main(String[] args) {
	}
}
