package datastructure;
import java.util.ArrayList;

public class Heap<T> {
	public static final int MAX = 0;
	public static final int MIN = 1;
	
	private ArrayList<Node> heap;
	private int mode;
	
	public Heap() {
		this(MAX);
	}
	
	public Heap(int mode) {
		heap = new ArrayList<Node>();
		this.mode = mode;
	}
	
	
	public T top() {
		return heap.get(0).item;
	}
	
	public T extract() {
		T top = top();
		remove(0);
		return top;
	}
	
	public void insert(T object, int key) {
		heap.add(new Node(object, key)); // insert to end of heap
		siftUp(heap.size() - 1);
	}
	
	public boolean remove(T object) {
		for(int i = 0; i < heap.size(); i++) { // linear search of object
			if(object.equals(heap.get(i).item)) {
				remove(i);
				return true;
			}
		}
		return false;
	}
	
	public void changeKey(T object, int key) {
		for(int i = 0; i < heap.size(); i++) { // linear search of object
			if(object.equals(heap.get(i).item)) {
				heap.get(i).key = key;
				siftUp(i);
				siftDown(i);
				return;
			}
		}
	}
	
	public int size() {
		return heap.size();
	}
	
	
	private void remove(int i) {
		swap(i, heap.size() - 1); // swap with last element
		heap.remove(heap.size() - 1);
		if(heap.size() != i) { // i-th element not the last element
			Node node = heap.get(i);
			siftDown(i);
			if(heap.get(i).equals(node)) siftUp(i); // siftDown may not restore the heap condition
		}
	}
	
	private void siftDown(int parent) {
		while(left(parent) < heap.size()) {
			int child = left(parent); // child with largest key
			if(right(parent) < heap.size() && compare(heap.get(right(parent)), heap.get(left(parent)))) {
				child = right(parent); 
			}
			// check heap condition
			if(compare(heap.get(parent), heap.get(child))) return;
			else { // restore heap condition
				swap(parent, child);
				parent = child;
			}
		}
	}
	
	private void siftUp(int child) {
		while(child > 0) {
			int parent = parent(child);
			// check heap condition
			if(compare(heap.get(parent), heap.get(child))) return;
			else { // restore heap condition
				swap(child, parent);
				child = parent;
			}
		}
	}
	
	private void swap(int u, int v) {
		Node temp = heap.get(u);
		heap.set(u, heap.get(v));
		heap.set(v, temp);
	}
	
	private boolean compare(Node parent, Node child) {
		if(mode == MAX) return parent.key >= child.key; // max-heap
		else return parent.key <= child.key; // min-heap
	}
	
	private int left(int i) {
		return 2*i+1;
	}
	
	private int right(int i) {
		return 2*i+2;
	}
	
	private int parent(int i) {
		return (i-1)/2;
	}
	
	
	public class Node {
		public T item;
		private int key;
		public Node(T item, int key) {
			this.item = item;
			this.key = key;
		}
		public int getKey() {
			return key;
		}
	}
}
