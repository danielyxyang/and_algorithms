package dp;

import java.util.Arrays;

public class Knapsack {
	/**
	 * Solves the Knapsack problem by DP over the weights.
	 */
	public static Solution solveByWeight(int[] values, int[] weights, int maxWeight) {
		int n = values.length;
		// initialization
		int[][] dp = new int[n+1][maxWeight+1];
		for(int w = 0; w <= maxWeight; w++) dp[0][w] = 0;
		// solve the problem
		for(int i = 1; i <= n; i++) {
			for(int w = 0; w <= maxWeight; w++) {
				if(w < weights[i-1]) dp[i][w] = dp[i-1][w];
				else dp[i][w] = Math.max(dp[i-1][w], dp[i-1][w-weights[i-1]] + values[i-1]);
			}
		}
		// reconstruct solution
		boolean[] elements = new boolean[n];
		int i = n;
		int w = maxWeight;
		while(i > 0) {
			if(dp[i][w] == dp[i-1][w]) {
				i--;
			}
			else if(dp[i][w] == dp[i-1][w-weights[i-1]] + values[i-1]) {
				elements[i-1] = true;
				w -= weights[i-1];
				i--;
			}
		}
		return new Solution(dp[n][maxWeight], elements);
	}
	
	/**
	 * Solves the Knapsack problem by DP over the values.
	 */
	public static Solution solveByValue(int[] values, int[] weights, int maxWeight) {
		int n = values.length;
		int totalValue = 0;
		for(int i = 0; i < n; i++) totalValue += values[i];
		// initialization
		int[][] dp = new int[n+1][totalValue+1];
		dp[0][0] = 0;
		for(int v = 1; v <= totalValue; v++) dp[0][v] = Integer.MAX_VALUE;
		// solve the problem
		for(int i = 1; i <= n; i++) {
			for(int v = 0; v <= totalValue; v++) {
				if(v < values[i-1] || dp[i-1][v-values[i-1]] == Integer.MAX_VALUE) dp[i][v] = dp[i-1][v];
				else dp[i][v] = Math.min(dp[i-1][v], dp[i-1][v-values[i-1]] + weights[i-1]);
			}
		}
		// find solution
		int solution = 0;
		for(int v = 0; v <= totalValue; v++) {
			if(dp[n][v] <= maxWeight) solution = v;
		}
		// reconstruct solution
		boolean[] elements = new boolean[n];
		int i = n;
		int v = solution;
		while(i > 0) {
			if(dp[i][v] == dp[i-1][v]) {
				i--;
			}
			else if(dp[i][v] == dp[i-1][v-values[i-1]] + weights[i-1]) {
				elements[i-1] = true;
				v -= values[i-1];
				i--;
			}
		}
		return new Solution(solution, elements);
	}

	/**
	 * Calculates the approximated (1-e)-solution of the Knapsack problem by
	 * DP over values.
	 */
	public static Solution approxByValue(int[] values, int[] weights, int maxWeight, double e) {
		int n = values.length;
		int approxConst = calcApproxConst(values, weights, maxWeight, e);
		System.out.println("Approx. Const.: " + approxConst);
		// calculate approximated values
		int[] valuesApprox = new int[n];
		if(approxConst > 1) {
			for(int i = 0; i < n; i++) valuesApprox[i] = values[i] / approxConst;
		}
		else valuesApprox = values;
		// solve problem with approximated values
		Solution solution = solveByValue(valuesApprox, weights, maxWeight);
		// recalculate sum of values with not approximated values
		int solutionValue = 0;
		for(int i = 0; i < n; i++) {
			if(solution.elements[i]) solutionValue += values[i];
		}
		solution.maxValue = solutionValue;
		
		return solution;
	}
	private static int calcApproxConst(int[] values, int weights[], int maxWeight, double e) {
		int n = values.length;
		// find max value with weight <= maxWeight
		int maxValue = Integer.MIN_VALUE;
		for(int i = 0; i < n; i++) {
			if(weights[i] <= maxWeight) maxValue = Math.max(maxValue, values[i]);
		}
		// return approximation constant
		if(maxValue != Integer.MIN_VALUE) return (int) (maxValue * e / n);
		else return 1; // do not approx.
	}
	
	public static class Solution {
		int maxValue;
		boolean[] elements;
		Solution(int solution, boolean[] elements) {
			this.maxValue = solution;
			this.elements = elements;
		}
		public int[] elementsToIntArray() {
			int[] elements = new int[this.elements.length];
			for(int i = 0; i < elements.length; i++) elements[i] = this.elements[i] ? 1 : 0;
			return elements;
		}
		@Override
		public String toString() {
			return maxValue + ": " + Arrays.toString(elementsToIntArray());
		}
	}
}
