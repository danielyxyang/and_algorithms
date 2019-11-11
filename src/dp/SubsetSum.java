package dp;

import java.util.Arrays;

public class SubsetSum {
	public static void main(String[] args) {
		System.out.println(solve(new int[] {}, 0));
		System.out.println(solve(new int[] {1,5,3,7}, 9));
		System.out.println(solve(new int[] {1,5,3,7}, 2));
		System.out.println(solve(new int[] {1,2,3}, 0));
	}
	
	
	public static Solution solve(int[] array, int sum) {
		int n = array.length;
		// initialization
		boolean[][] dp = new boolean[n+1][sum+1];
		dp[0][0] = true; // no element + sum 0: possible
		for(int v = 1; v <= sum; v++) dp[0][v] = false; // 0 elements + sum v: not possible
		// solve the problem
		for(int i = 1; i <= n; i++) {
			for(int v = array[i-1]; v <= sum; v++) {
				dp[i][v] = dp[i-1][v] || dp[i-1][v-array[i-1]]; // recurrence
			}
		}
		// find the solution
		int solution = -1;
		for(int i = 0; i <= n; i++) {
			if(dp[i][sum]) {
				solution = i;
				break;
			}
		}
		// reconstruct the solution
		boolean[] elements = new boolean[n];
		int i = solution;
		int s = sum;
		while(i > 0) {
			if(dp[i-1][s]) { // recurrence case 1
				i--;
			}
			else if(dp[i-1][s-array[i-1]]) { // recurrence case 2
				elements[i-1] = true;
				s -= array[i-1];
				i--;
			}
		}		
		return new Solution(solution != -1, elements);
	}
	private static class Solution {
		boolean solution;
		boolean[] elements;
		Solution(boolean solution, boolean[] elements) {
			this.solution = solution;
			this.elements = elements;
		}
		@Override
		public String toString() {
			return solution + ": " + Arrays.toString(elements);
		}
	}
}
