import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private class Node {
		public Item val;
		public Node next;
		public Node prev;

		public Node(Item item, Node next, Node prev) {
			this.val = item;
			this.next = next;
			this.prev = prev;
		}

		public Node(Item item, Node next) {
			this(item, next, null);
		}
	}

	private Node first;
	private Node last;
	private int length;

	// construct an empty deque
	public Deque() {
	}

	// is the deque empty?
	public boolean isEmpty() {
		return this.length == 0;
	}

	// return the number of items on the deque
	public int size() {
		return this.length;
	}

	// add the item to the front
	public void addFirst(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("item is null");
		}

		this.first = new Node(item, this.first);

		if (this.length == 0) {
			this.last = this.first;
		} else {
			this.first.next.prev = this.first;
		}

		this.length++;
	}

	// add the item to the back
	public void addLast(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("item is null");
		}

		this.last = new Node(item, null, this.last);

		if (this.length == 0) {
			this.first = this.last;
		} else {
			this.last.prev.next = this.last;
		}

		this.length++;
	}

	// remove and return the item from the front
	public Item removeFirst() {
		if (this.length == 0) {
			throw new NoSuchElementException("deque is empty");
		}

		Item firstItem = this.first.val;

		this.first = this.first.next;
		if (this.first == null) {
			this.last = null;
		} else {
			this.first.prev = null;
		}

		this.length--;
		return firstItem;
	}

	// remove and return the item from the back
	public Item removeLast() {
		if (this.length == 0) {
			throw new NoSuchElementException("deque is empty");
		}

		Item lastItem = this.last.val;

		this.last = this.last.prev;
		if (this.last == null) {
			this.first = null;
		} else {
			this.last.next = null;
		}

		this.length--;
		return lastItem;
	}

	private class DequeIterator implements Iterator<Item> {
		private Node curr;

		DequeIterator(Node first) {
			this.curr = first;
		}

		public boolean hasNext() {
			return this.curr != null;
		}

		public Item next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException("next is null");
			}

			Item currItem = this.curr.val;
			this.curr = this.curr.next;
			return currItem;
		}

		public void remove() {
			throw new UnsupportedOperationException("remove isn't supported");
		}
	}

	// return an iterator over items in order from front to back
	public Iterator<Item> iterator() {
		return new DequeIterator(this.first);
	}

	// unit testing (required)
	public static void main(String[] args) {
		Deque<Integer> test = new Deque<Integer>();

		System.out.printf("test.isEmpty() = %b (should be true)%n", test.isEmpty());
		System.out.printf("test.size() = %d (should be 0)%n", test.size());
		test.addFirst(3);
		test.addFirst(1);
		System.out.printf("test.isEmpty() = %b (should be false)%n", test.isEmpty());
		System.out.printf("test.size() = %d (should be 2)%n", test.size());
		System.out.printf("test.removeLast() = %d (should be 3)%n", test.removeLast());
		test.addLast(2);
		test.addLast(3);
		test.addLast(4);
		System.out.printf("test.isEmpty() = %b (should be false)%n", test.isEmpty());
		System.out.printf("test.size() = %d (should be 4)%n", test.size());
		for (int nb : test) {
			System.out.printf("%d ", nb);
		}
		System.out.printf("%ntest.removeFirst() = %d (should be 1)%n", test.removeFirst());
		System.out.printf("test.removeLast() = %d (should be 4)%n", test.removeLast());
		System.out.printf("test.removeFirst() = %d (should be 2)%n", test.removeFirst());
		System.out.printf("test.removeLast() = %d (should be 3)%n", test.removeLast());
		System.out.printf("test.isEmpty() = %b (should be true)%n", test.isEmpty());
		System.out.printf("test.size() = %d (should be 0)%n", test.size());
	}
}
