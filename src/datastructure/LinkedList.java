package datastructure;

public class LinkedList<T> {
	public ListNode first;
	public ListNode last;
	public int size = 0;

	public ListNode addFirst(T object) {
		ListNode node = new ListNode(object);
		if(first == null) first = last = node;
		else {
			// change next/prev references
			node.next = first;
			first.prev = node;
			// change first reference
			first = node;
		}
		size++;
		return node;
	}
	
	public ListNode addLast(T object) {
		ListNode node = new ListNode(object);
		if(first == null) first = last = node;
		else {
			// change next/prev references
			last.next = node;
			node.prev = last;
			// change last reference
			last = node;
		}
		size++;
		return node;
	}
	
	public T removeFirst() {
		if(first == null) return null;
		else {
			ListNode node = first;
			
			if(first.next == null) first = last = null;
			else {
				first = first.next;
				first.prev = null;
			}
			size--;
			return node.object;
		}
	}
	
	public T removeLast() {
		if(last == null) return null;
		else {
			ListNode node = last;
			
			if(last.prev == null) first = last = null;
			else {
				last = last.prev;
				last.next = null;
			}
			size--;
			return node.object;
		}		
	}
	
	public void clear() {
		first = null;
		last = null;
	}
	
	public int size() {
		return size;
	}
	
	public void print() {
		System.out.print("[");
		for(ListNode current = first; current != null; current = current.next) {
			System.out.print(current.object + " ");
		}
		System.out.println("]");
	}
	
	public class ListNode {
		public T object;
		public ListNode next;
		public ListNode prev;
		ListNode(T object) {
			this.object = object;
		}
	}
	
	// TEST
	public static void main(String[] args) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		
		list.addLast(2);
		list.addLast(3);
		list.addLast(4);
		list.print();
		list.addFirst(1);
		list.addFirst(0);
		list.print();
		list.removeFirst();
		list.removeLast();
		list.print();
		list.clear();
		list.print();
	}
}
