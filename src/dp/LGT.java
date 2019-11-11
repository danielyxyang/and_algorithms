package dp;

public class LGT {
	public static void main(String[] args) {
		System.out.println(solve("", ""));
		System.out.println(solve("", "A"));
		System.out.println(solve("A", "A"));
		System.out.println(solve("ZIEGE", "TIGER"));
		System.out.println(solve("1A2B3C", "32A5BC"));
	}
	public static Solution solve(String string1, String string2) {
		char[] a = string1.toCharArray();
		char[] b = string2.toCharArray();
		int m = a.length;
		int n = b.length;
		
		// initialization
		int[][] dp = new int[m+1][n+1];
		for(int i = 0; i <= m; i++) dp[i][0] = 0;
		for(int j = 0; j <= n; j++) dp[0][j] = 0;
		// solve the problem
		for(int i = 1; i <= m; i++) {
			for(int j = 1; j <= n; j++) {
				int d = a[i-1] == b[j-1] ? 1 : 0;
				dp[i][j] = max(dp[i-1][j], dp[i][j-1], dp[i-1][j-1] + d);
			}
		}
		// reconstruct the solution
		String solution = "";
		int i = m;
		int j = n;
		while(i > 0 && j > 0) {
			if(a[i-1] == b[j-1]) {
				solution = a[i-1] + solution;
				i--;
				j--;
			}
			else if(dp[i][j] == dp[i-1][j-1]) {
				i--;
				j--;
			}
			else if(dp[i][j] == dp[i-1][j]) i--;
			else if(dp[i][j] == dp[i][j-1]) j--;
		}
		return new Solution(dp[m][n], solution);
	}
	private static int max(int a, int b, int c) {
		return Math.max(Math.max(a, b), c);
	}
	private static class Solution {
		int length;
		String lgt;
		Solution(int length, String lgt) {
			this.length = length;
			this.lgt = lgt;
		}
		@Override
		public String toString() {
			return length + ": " + lgt;
		}
	}
}
