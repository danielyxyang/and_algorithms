package graph;

public class APSP {
	public static int[][] floydwarshall(int[][] matrix) {
		int n = matrix.length;
		// initialization
		for(int i = 0; i < n; i++) matrix[i][i] = 0;
		
		for(int k = 0; k < n; k++) {
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					if(matrix[i][k] != Integer.MAX_VALUE && matrix[k][j] != Integer.MAX_VALUE) {
						matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
					}
				}
			}
		}
		return matrix;
	}
	
	public static int[][] johnson(int[][] matrix) {
		int n = matrix.length;
		
		// CALCULATE HEIGHTS
		int[][] newMatrix = new int[n+1][n+1];
		// copy old matrix
		for(int i = 0; i < n; i++) for(int j = 0; j < n; j++) newMatrix[i][j] = matrix[i][j];
		// insert new node n
		for(int i = 0; i < n; i++) {
			newMatrix[n][i] = 0;
			newMatrix[i][n] = Integer.MAX_VALUE;
		}
		// calculate heights
		int[] height = SSSP.bellmanford(newMatrix, n).distance;
		
		// DETECT NEGATIVE CYCLE
		if(height == null) return null;
		
		// MODIFY EDGE WEIGHTS
		newMatrix = new int[n][n];
		for(int i = 0; i < n; i++) for(int j = 0; j < n; j++) {
			if(matrix[i][j] != Integer.MAX_VALUE) newMatrix[i][j] = matrix[i][j] + height[i] - height[j];
			else newMatrix[i][j] = Integer.MAX_VALUE;
		}
		
		// CALCULATE DISTANCES
		int[][] distances = new int[n][n];
		for(int i = 0; i < n; i++) {
			distances[i] = SSSP.dijkstra(newMatrix, i).distance;
			// adapt distances to old weights
			for(int j = 0; j < n; j++) distances[i][j] += height[j] - height[i];
		}
		
		return distances;
	}
}
