package datastructure;

public class Queue<T> extends LinkedList<T> {
	
	public void enqueue(T object) {
		this.addLast(object);
	}
	
	public T dequeue() {
		return this.removeFirst();
	}
		
	// TEST
	public static void main(String[] args) {
		Queue<Integer> queue = new Queue<Integer>();
		System.out.println(queue.size());
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(4);
		queue.print();
		System.out.println(queue.size());
		System.out.println(queue.dequeue() + " " + queue.dequeue() + " " + queue.dequeue());
		System.out.println(queue.size());
	}
}
