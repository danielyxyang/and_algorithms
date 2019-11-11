package datastructure;

public class Array<T> { // implementation of array with dynamic size (same as java.util.ArrayList) 
	private T[] array;
	private int size;
	
	public Array() {
		this(10);
	}
	public Array(int initialCapacity) {
		array = (T[]) new Object[initialCapacity];
		size = 0;
	}
	
	public T get(int i) {
		if(array[i] != null) return array[i];
		else throw new AssertionError();
	}
	
	public void add(T object) {
		if(size == array.length) grow();
		array[size] = object;
		size++;
	}
	
	public T remove(int i) {
		T temp = array[i];
		
		// shift items to the left by one
		System.arraycopy(array, i+1, array, i, size-i-1); // native implementation (faster)
//		for(int k = i; k < size-1; k++) array[k] = array[k+1]; // custom implementation
		
		// reduce array size
		array[size-1] = null;
		size--;
		return temp;
	}
	
	public int size() {
		return size;
	}
	
	private void grow() {
		// create larger array (exponential growth)
		int newSize = (int) (array.length < 100 ? array.length * 2 : array.length * 1.5);
		T[] copy = (T[]) new Object[newSize];
		
		// copy items into larger array
		for(int i = 0; i < array.length; i++) copy[i] = array[i];
		array = copy;
	}
}
