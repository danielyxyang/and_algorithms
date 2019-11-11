package datastructure;

public class Stack<T> extends LinkedList<T> {
	
	public void push(T object) {
		this.addFirst(object);
	}
	
	public T pop() {
		return this.removeFirst();
	}
	
	// TEST
	public static void main(String[] args) {
		Stack<Integer> stack = new Stack<Integer>();
		System.out.println(stack.size());
		stack.push(2);
		stack.push(3);
		stack.push(4);
		stack.print();
		System.out.println(stack.size());
		System.out.println(stack.pop() + " " + stack.pop() + " " + stack.pop());
		System.out.println(stack.size());
	}	
}
