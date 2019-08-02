import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("need one args");
		}

		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> queue = new RandomizedQueue<String>();

		int i = 0;
		while (!StdIn.isEmpty()) {
			String string = StdIn.readString();

			if (i < k) {
				queue.enqueue(string);
			} else if (StdRandom.uniform(i + 1) < k) {
				queue.dequeue();
				queue.enqueue(string);
			}

			i++;
		}

		for (String string : queue) {
			System.out.printf("%s%n", string);
		}
	}
}
