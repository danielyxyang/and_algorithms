package sort;

public class Sort {
	/**
	 * Bubblesort
	 */
	public static void bubblesort(int[] array) {
		int n = array.length;
		for(int i = 0; i < n-1; i++) {
			for(int k = 0; k < n-i-1; k++) {
				if(array[k] > array[k+1]) swap(array, k, k+1);
			}
		}
	}
	
	/**
	 * Selectionsort
	 */
	public static void selectionsort(int[] array) {
		int n = array.length;
		for(int i = 0; i < n-1; i++) { // i = lower limit of unsorted array
			// find element with smallest value
			int index = -1;
			int min = Integer.MAX_VALUE;
			for(int k = i; k < n; k++) {
				if(array[k] < min) {
					min = array[k];
					index = k;
				}
			}
			// swap element to its correct position
			swap(array, i, index);
		}
	}
	
	/**
	 * Insertionsort
	 */
	public static void insertionsort(int[] array) {
		int n = array.length;
		for(int i = 1; i < n; i++) { // i = upper limit of sorted array
			// find correct insertion point
			int index = Search.binarySearch(array, array[i], 0, i);
			// array shift
			for(int k = i-1; k >= index; k--) swap(array, k, k+1);
		}
	}
	
	/**
	 * Heapsort
	 */
	public static void heapsort(int[] array) {
		int[] heap = array;
		int heapSize = array.length;
		// heapify
		for(int i = heapSize/2; i >= 0; i--) siftDown(heap, i, heapSize);
		// sort
		while(heapSize > 0) {
			swap(heap, 0, heapSize-1);
			heapSize--;
			siftDown(heap, 0, heapSize);
		}
	}
	private static void siftDown(int[] heap, int i, int heapSize) {
		while(left(i) < heapSize) {
			int child = left(i);
			if(right(i) < heapSize && heap[right(i)] > heap[left(i)]) child = right(i);
			// check heap condition
			if(heap[i] > heap[child]) return;
			else {
				swap(heap, i, child);
				i = child;
			}
		}
	}
	private static int left(int i) {
		return 2*i+1;
	}
	private static int right(int i) {
		return 2*i+2;
	}
	
	/**
	 * Mergesort
	 */
	public static void mergesort(int[] array) {
		for(int size = 1; size < array.length; size *= 2) {
			int left, middle;
			int right = -1;
			while(right+size < array.length) {
				left = right + 1;
				middle = left + size - 1;
				right = Math.min(middle + size, array.length - 1);
				merge(array, left, middle, right);
			}
		}
	}
	private static void merge(int[] array, int left, int middle, int right) { // merging [left, middle] with [middle+1, right]
		int[] copy = new int[right - left + 1];
		// merge arrays (out-of-place)
		int lp = left; // left pointer
		int rp = middle+1; // right pointer
		int k = 0; // pointer to target array
		while(lp <= middle && rp <= right) {
			if(array[lp] <= array[rp]) copy[k] = array[lp++];
			else copy[k] = array[rp++];
			k++;
		}
		// copy rest of arrays
		while(lp <= middle) copy[k++] = array[lp++];
		while(rp <= right) copy[k++] = array[rp++];
		// copy back into original array
		for(int i = left; i <= right; i++) array[i] = copy[i-left];
	}
	
	/**
	 * Quicksort
	 */
	public static void quicksort(int[] array) {
		quicksort(array, 0, array.length - 1, false);
	}
	public static void quicksortRandomized(int[] array) {
		quicksort(array, 0, array.length - 1, true);
	}
	private static void quicksort(int[] array, int left, int right, boolean random) {
		if(left < right) {
			int r = partition(array, left, right, random);
			quicksort(array, left, r-1, random);
			quicksort(array, r+1, right, random);	
		}
	}
	private static int partition(int[] array, int left, int right, boolean random) {
		if(random) {
			int pivotIndex = (int) Math.random() * (right-left + 1) + left;
			swap(array, pivotIndex, right);
		}
		int pivot = array[right];
		int lp = left;
		int rp = right - 1;
		while(lp <= rp) { // use <= instead of < (edge cases: 1 2, 1 2 3, 2 3 1)
			while(lp <= rp && array[lp] <= pivot) lp++; // after loop: lp > rp (next larger element or pivot) OR found element larger than pivot
			while(lp <= rp && array[rp] >= pivot) rp--; // after loop: rp < lp (smaller element or -1) OR found element smaller than pivot
			if(lp < rp) swap(array, lp, rp);
		}
		swap(array, right, lp); // swap pivot with next larger element (or pivot itself)
		return lp;
	}
	@SuppressWarnings("unused")
	private static int partition2(int[] array, int left, int right, boolean random) {
		/*
		 * Needs 3 times more swaps as it only swaps the smaller elements to its 
		 * correct position. The larger elements may have to be swapped again.
		 */
		if(random) {
			int pivotIndex = (int) Math.random() * (right-left + 1) + left;
			swap(array, pivotIndex, right);
		}
		int pivot = array[right];
		int index = left - 1; // index of smaller element
		for(int i = left; i <= right - 1; i++) {
			if(array[i] < pivot) {
				// current element is smaller than the pivot
				index++;
				if(i != index) swap(array, index, i);
			}
		}
		swap(array, right, index+1); // swap pivot next to the smaller element
		return index+1;
	}
	
	public static int swapCount = 0;
	private static void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
		swapCount++;
	}
}
