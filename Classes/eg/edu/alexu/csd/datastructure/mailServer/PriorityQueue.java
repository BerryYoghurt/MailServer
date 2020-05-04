package eg.edu.alexu.csd.datastructure.mailServer;

public class PriorityQueue implements IPriorityQueue {

	private int size;
	private class Node{
		private Node prev = null;
		private Node next = null;
		private Object data = null;
		private Integer priority = null;
	}
	private Node head;
	private Node tail;
	
	public PriorityQueue() {
		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.prev = head;
		size = 0;
	}
	
	private void insertBetween(Node previous, Node next, Node newNode) {
		newNode.next = next;
		newNode.prev = previous;
		previous.next = newNode;
		next.prev = newNode;
	}
	
	@Override
	public void insert(Object item, int key) {//TODO needs to be corrected
		Node newNode = new Node();
		newNode.data = item;
		newNode.priority = key;
		
		Node current = tail.prev;
		while(current!= head && current.priority < key) {
			current = current.prev;
		}
		insertBetween(current, current.next, newNode);
		size++;
	}

	@Override
	public Object removeMin() {
		if(size == 0) {
			throw new IllegalStateException("Queue empty");
		}
		Node removed = head.next;
		head.next = removed.next;
		removed.next.prev = head;
		size--;
		return removed.data;
	}

	@Override
	public Object min() {
		if(size == 0) {
			throw new IllegalStateException("Queue empty");
		}
		return head.next.data;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

}
