package datastructure;

import java.util.Arrays;

public class HeapIntegerArray {
	public static final int ASC = 0;
	public static final int DESC = 1;

	private int[] heap;
	private int heapSize;
	private int mode; // 0: ASC (min-heap), 1: DESC (max-heap)
	
	public HeapIntegerArray(int[] array) {
		this(array, ASC);
	}
	
	public HeapIntegerArray(int[] array, int mode) {
		this.heap = Arrays.copyOf(array, array.length);
		this.heapSize = array.length;
		this.mode = mode;
		// create heap structure
		heapify();
	}
	
	public int extract() {
		// extract top
		int top = heap[0];
		// remove top
		swap(0, heapSize - 1);
		heapSize--;
		// restore heap condition
		siftDown(0);
		return top;
	}
	
	// PRIVATE METHODS
	private void heapify() {
		for(int i = heapSize/2; i >= 0; i--) siftDown(i);
	}
	
	private void siftDown(int parent) {
		while(left(parent) < heapSize) {
			// find child with largest value
			int child = left(parent);
			if(right(parent) < heapSize && compare(right(parent), left(parent))) child = right(parent);
			// check heap condition
			if(compare(parent, child)) return;
			// restore heap condition
			swap(parent, child);
			parent = child;
		}
	}
	
//	private void siftUp(int child) {
//		while(child > 0) {
//			int parent = parent(child);
//			// check heap condition
//			if(compare(parent, child)) return;
//			// restore heap condition
//			swap(parent, child);
//			child = parent;
//		}
//	}
	
	private boolean compare(int parent, int child) {
		// returns true if parent and child meet the heap condition
		if(mode == DESC) return heap[parent] >= heap[child]; // max heap
		else return heap[parent] <= heap[child]; // min heap
	}
	
	private int left(int parent) {
		return parent*2+1;
	}
	
	private int right(int parent) {
		return parent*2+2;
	}
	
//	private int parent(int child) {
//		return (child-1)/2;
//	}
	
	private void swap(int i, int j) {
		int temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}
	
	/*
	 * TEST
	 */
	public static void main(String[] args) {
		int[] array = {5,3,6,8,2,3,4,1};
				
		HeapIntegerArray maxHeap = new HeapIntegerArray(array, ASC);
		for(int i = 0; i < array.length; i++) System.out.print(maxHeap.extract() + " ");
		System.out.println();
		
		HeapIntegerArray minHeap = new HeapIntegerArray(array, DESC);
		for(int i = 0; i < array.length; i++) System.out.print(minHeap.extract() + " ");
		System.out.println();
	}
}
