package sort;

public class Search {
	public static void main(String[] args) {
		int[] array = {1,2,4,5,6,7};
		System.out.println(binarySearch(array, 2) + " = 1");
		System.out.println(binarySearch(array, 3) + " = 2");
	}
	
	
	public static int binarySearch(int[] array, int key) {
		return binarySearch(array, key, 0, array.length-1);
	}
	public static int binarySearch(int[] array, int key, int left, int right) {
		int middle;
		while(left < right) {
			middle = left + (right-left) / 2;
			if(array[middle] == key) return middle;
			else if(array[middle] > key) right = middle; // possible right insertion point
			else left = middle + 1;
		}
		return right;
	}
}
