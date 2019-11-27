public class Outcast {
	// constructor takes a WordNet object
	public Outcast(WordNet wordnet) {
		if (wordnet == null) {
			throw new IllegalArgumentException("wordnet is null");
		}
	}

	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns) {
		if (nouns == null) {
			throw new IllegalArgumentException("arg is null");
		}

		return "";
	}

	// see test client below
	public static void main(String[] args) {
	}
}
