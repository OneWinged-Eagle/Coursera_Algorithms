public class Outcast {
	private final WordNet wordNet;

	// constructor takes a WordNet object
	public Outcast(WordNet wordnet) {
		if (wordnet == null) {
			throw new IllegalArgumentException("wordnet is null");
		}

		this.wordNet = wordnet;
	}

	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns) {
		if (nouns == null) {
			throw new IllegalArgumentException("arg is null");
		}

		String outcast = "";
		int[][] distances = new int[nouns.length][nouns.length];
		int max = 0;

		for (int i = 0; i < nouns.length; i++) {
			int tmp = 0;

			for (int n = 0; n < nouns.length; n++) {
				if (i == n) {
					continue;
				}

				if (distances[i][n] == 0) {
					distances[i][n] = this.wordNet.distance(nouns[i], nouns[n]);
					distances[n][i] = distances[i][n];
				}
				tmp += distances[i][n];
			}

			if (tmp > max) {
				max = tmp;
				outcast = nouns[i];
			}
		}

		return outcast;
	}

	// see test client below
	public static void main(String[] args) {
	}
}
