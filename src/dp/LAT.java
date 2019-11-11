package dp;

import java.util.Arrays;

public class LAT {
	public static void main(String[] args) {
		int[][] arrays = {
			{},
			{1,2,3},
			{3,2,1},
			{1,3,2,6,4,3,7,5,9},
		};
		for(int[] array : arrays) System.out.println(solve(array));
	}
	
	public static Solution solve(int[] array) {
		int[] dp = new int[array.length];
		int[] prev = new int[array.length];
		
		// initialization
		for(int i = 0; i < array.length; i++) dp[i] = Integer.MAX_VALUE;
		// solve DP problem
		for(int i = 0; i < array.length; i++) {
			int index = search(dp, array[i]);
			dp[index] = array[i]; // override first element greater than array[i]
			prev[i] = index > 0 ? dp[index-1] : -1;
		}
		// find solution
		int maxLength = 0;
		for(int i = 0; i < dp.length; i++) {
			if(dp[i] != Integer.MAX_VALUE) maxLength = i+1;
		}
		// reconstruct solution
		int[] solution = new int[maxLength];
		if(maxLength > 0) {
			int k = array.length - 1;
			int previousValue = dp[maxLength - 1];
			for(int i = maxLength-1; i >= 0; i--) {
				while(previousValue != array[k]) k--;
				solution[i] = previousValue;
				previousValue = prev[k];
			}			
		}
		
		return new Solution(maxLength, solution);
	}
	private static int search(int[] array, int key) {
		int left = 0;
		int right = array.length - 1;
		while(left < right) {
			int middle = left + (right-left)/2;
			if(array[middle] == key) return middle;
			else if(array[middle] > key) right = middle;
			else left = middle + 1;
		}
		return right; // right insertion point
	}
	private static class Solution {
		int length;
		int[] lat;
		Solution(int length, int[] lat) {
			this.length = length;
			this.lat = lat;
		}
		@Override
		public String toString() {
			return length + ": " + Arrays.toString(lat);
		}
	}
}
