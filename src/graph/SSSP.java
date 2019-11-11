package graph;

import datastructure.Heap;

public class SSSP {
	public static int maxSize = 0;
	
	public static SSSPResult dijkstra(int[][] matrix, int startNode) {
		int n = matrix.length;
		Heap<Integer> heap = new Heap<Integer>(Heap.MIN);
    	// initialization
		boolean[] hasDistance = new boolean[n];
		int[] distances = new int[n];
		int[] parent = new int[n];
		for(int i = 0; i < n; i++) distances[i] = parent[i] = Integer.MAX_VALUE;
		
		heap.insert(startNode, 0);
		distances[startNode] = 0;
		while(heap.size() > 0) {
			int node = heap.extract();
			if(!hasDistance[node]) {
				hasDistance[node] = true;
				// iterate over adjacent nodes and relax edges
				for(int i = 0; i < matrix.length; i++) if(matrix[node][i] != Integer.MAX_VALUE) {
					int newUpperBound = distances[node] + matrix[node][i];
					if(!hasDistance[i] && distances[i] > newUpperBound) {
						// OPTION 1: changeKey in O(n)
//						if(distances[i] == Integer.MAX_VALUE) heap.insert(i, newUpperBound);
//						else heap.changeKey(i, newUpperBound);
						// OPTION 2: remove in O(n)
//						heap.remove(i);
//						heap.insert(i, newUpperBound);
						// OPTION 3: insert in O(logn), but larger queue size 
						heap.insert(i, newUpperBound);
						
						distances[i] = newUpperBound;
						parent[i] = node;
					}
				}
			}
			maxSize = Math.max(maxSize, heap.size());
		}
		return new SSSPResult(distances, parent);
	}
	
	public static SSSPResult bellmanford(int[][] matrix, int startNode) {
		int n = matrix.length;
		// initialize upper bounds
		int[] distances = new int[n];
		int[] parent = new int[n];
		for(int i = 0; i < n; i++) distances[i] = parent[i] = Integer.MAX_VALUE;
		// intialize start node
		distances[startNode] = 0;
		
		for(int k = 0; k < n-1; k++) {
			// iterate over all edges
			for(int i = 0; i < n; i++) for(int j = 0; j < n; j++) if(matrix[i][j] != Integer.MAX_VALUE) {
				if(distances[i] != Integer.MAX_VALUE && distances[j] > distances[i] + matrix[i][j]) {
					// relax edge over predecessor i
					distances[j] = distances[i] + matrix[i][j];
					parent[j] = i;
				}
			}
		}
		// negative cycle test
		for(int i = 0; i < n; i++) for(int j = 0; j < n; j++) if(matrix[i][j] != Integer.MAX_VALUE) {
			if(distances[i] != Integer.MAX_VALUE && distances[j] > distances[i] + matrix[i][j]) {
				return new SSSPResult(null, null);
			}
		}
		return new SSSPResult(distances, parent);
	}
}

class SSSPResult {
	public int[] distance;
	public int[] parent;
	public SSSPResult(int[] distance, int[] parent) {
		this.distance = distance;
		this.parent = parent;
	}
}
