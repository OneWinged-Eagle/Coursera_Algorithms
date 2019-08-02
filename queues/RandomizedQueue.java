import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] queue;
	private int length;

	// construct an empty randomized queue
	public RandomizedQueue() {
		this.queue = (Item[]) new Object[1];
	}

	// is the randomized queue empty?
	public boolean isEmpty() {
		return this.length == 0;
	}

	// return the number of items on the randomized queue
	public int size() {
		return this.length;
	}

	private void copyAndReplace(int newLength) {
		Item[] newQueue = (Item[]) new Object[newLength];

		for (int i = 0; i < this.length; i++) {
			newQueue[i] = this.queue[i];
		}

		this.queue = newQueue;
	}

	// add the item
	public void enqueue(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("item is null");
		}

		if (this.queue.length == this.length) {
			this.copyAndReplace(this.length * 2);
		}

		this.queue[this.length++] = item;
	}

	// remove and return a random item
	public Item dequeue() {
		if (this.length == 0) {
			throw new NoSuchElementException("randomisedQueue is empty");
		}

		int index = StdRandom.uniform(this.length);
		Item item = this.queue[index];

		this.queue[index] = this.queue[--this.length];
		this.queue[this.length] = null;

		if (this.queue.length > 4 && this.queue.length >= this.length * 4) {
			this.copyAndReplace(this.queue.length / 2);
		}

		return item;
	}

	// return a random item (but do not remove it)
	public Item sample() {
		if (this.length == 0) {
			throw new NoSuchElementException("randomisedQueue is empty");
		}

		return this.queue[StdRandom.uniform(this.length)];
	}

	private class RandomizedQueueIterator implements Iterator<Item> {
		private Item[] queue;
		private int count;

		RandomizedQueueIterator(Item[] queue, int count) {
			this.queue = (Item[]) new Object[count];

			for (int i = 0; i < count; i++) {
				this.queue[i] = queue[i];
			}

			this.count = count;
		}

		public boolean hasNext() {
			return this.count > 0;
		}

		public Item next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException("next is null");
			}

			int index = StdRandom.uniform(this.count);
			Item item = this.queue[index];

			this.queue[index] = this.queue[--this.count];
			this.queue[this.count] = null;

			return item;
		}

		public void remove() {
			throw new UnsupportedOperationException("remove isn't supported");
		}
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator(this.queue, this.length);
	}

	// unit testing (required)
	public static void main(String[] args) {
		RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();

		System.out.printf("test.isEmpty() = %b (should be true)%n", test.isEmpty());
		System.out.printf("test.size() = %d (should be 0)%n", test.size());
		test.enqueue(3);
		test.enqueue(1);
		System.out.printf("test.isEmpty() = %b (should be false)%n", test.isEmpty());
		System.out.printf("test.size() = %d (should be 2)%n", test.size());
		System.out.printf("test.dequeue() = %d%n", test.dequeue());
		test.enqueue(2);
		test.enqueue(3);
		test.enqueue(4);
		System.out.printf("test.isEmpty() = %b (should be false)%n", test.isEmpty());
		System.out.printf("test.size() = %d (should be 4)%n", test.size());
		for (int nb : test) {
			System.out.printf("%d ", nb);
		}
		System.out.printf("test.dequeue() = %d%n", test.dequeue());
		System.out.printf("test.dequeue() = %d%n", test.dequeue());
		System.out.printf("test.dequeue() = %d%n", test.dequeue());
		System.out.printf("test.dequeue() = %d%n", test.dequeue());
		System.out.printf("test.isEmpty() = %b (should be true)%n", test.isEmpty());
		System.out.printf("test.size() = %d (should be 0)%n", test.size());
	}
}
