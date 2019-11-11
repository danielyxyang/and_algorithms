package graph;

import java.util.Arrays;
import java.util.Comparator;

import datastructure.Heap;
import datastructure.UnionFind;

public class MST {
	public static int prim(int[][] matrix) {
		int n = matrix.length;
		Heap<Integer> heap = new Heap<Integer>(Heap.MIN);
		// initialization
		boolean[] mstSet = new boolean[n];
		int[] mstWeights = new int[n];
		int[] parent = new int[n];
		for(int i = 0; i < n; i++) mstWeights[i] = parent[i] = Integer.MAX_VALUE;
		
		int startNode = 0;
		heap.insert(startNode, 0);
		mstWeights[0] = 0;
		while(heap.size() > 0) {
			int node = heap.extract();
			if(!mstSet[node]) {
				mstSet[node] = true;
				// iterate over adjacent nodes and update weight of edges
				for(int i = 0; i < n; i++) if(matrix[node][i] != Integer.MAX_VALUE) {
					if(!mstSet[i] && mstWeights[i] > matrix[node][i]) {
						heap.insert(i, matrix[node][i]); // same options like Dijkstra
						mstWeights[i] = matrix[node][i]; 
						parent[i] = node;
					}
				}
			}
		}
		
		// calculate weight of MST
		int sum = 0;
		for(int i = 0; i < n; i++) sum += mstWeights[i];

		return sum;
	}
	
	public static int kruskal(int[][] matrix) {
		int n = matrix.length;
		UnionFind uf = new UnionFind(n);
		// initialization
		Edge[] mst = new Edge[n-1];
		Edge[] edges = new Edge[n*n];
		int edgeCount = 0;
		for(int i = 0; i < n; i++) for(int j = i; j < n; j++) {
			if(matrix[i][j] != Integer.MAX_VALUE) edges[edgeCount++] = new Edge(i, j, matrix[i][j]);
		}
		// sort edges by weight in ascending order
//		quicksort(edges, 0, edgeCount - 1); // stackOverflow
		Arrays.sort(edges, 0, edgeCount, new Comparator<Edge>() {
			@Override
			public int compare(Edge o1, Edge o2) {
				return o1.weight - o2.weight;
			}
		});
		// add edge to MST
		int mstCost = 0;
		for(int i = 0; uf.size() > 1 && i < edgeCount; i++) {
			if(uf.find(edges[i].node1) != uf.find(edges[i].node2)) {
				mst[n - uf.size()] = edges[i];
				mstCost += edges[i].weight;
				uf.union(edges[i].node1, edges[i].node2);
			}
		}

		return mstCost;
	}
	@SuppressWarnings("unused")
	private static void quicksort(Edge[] array, int left, int right) {
		if(left < right) {
			int p = partition(array, left, right);
			quicksort(array, left, p-1);
			quicksort(array, p+1, right);
		}
	}
	private static int partition(Edge[] array, int left, int right) {
		Edge pivot = array[right];
		int lp = left;
		int rp = right-1;
		while(lp <= rp) {
			while(lp <= rp && array[lp].weight <= pivot.weight) lp++;
			while(lp <= rp && array[rp].weight >= pivot.weight) rp--;
			if(lp < rp) swap(array, lp, rp);
		}
		swap(array, right, lp);
		return lp;
	}
	private static void swap(Edge[] array, int i, int j) {
		Edge temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}

class Edge {
	int node1;
	int node2;
	int weight;
	public Edge(int node1, int node2, int weight) {
		this.node1 = node1;
		this.node2 = node2;
		this.weight = weight;
	}
}
