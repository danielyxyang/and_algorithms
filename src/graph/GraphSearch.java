package graph;

import datastructure.Queue;
import datastructure.Stack;

public class GraphSearch {
	public static final int NULL = -1;
	
	/**
	 * Depth-First-Search
	 */
	public static DFSResult dfs(int[][] matrix) {
		int n = matrix.length;
		DFSParameters parameters = new DFSParameters(n);
		for(int i = 0; i < n; i++) {
			if(!parameters.visited[i]) dfsVisit(matrix, i, parameters);
		}
		return new DFSResult(parameters.preOrders, parameters.postOrders, parameters.isCyclic);
	}
	public static DFSResult dfs(int[][] matrix, int node) {
		int n = matrix.length;
		DFSParameters parameters = new DFSParameters(n);
		dfsVisit(matrix, node, parameters);
		return new DFSResult(parameters.preOrders, parameters.postOrders, parameters.isCyclic);
	}
	private static void dfsVisit(int[][] matrix, int node, DFSParameters parameters) {
		int n = matrix.length;
		parameters.visited[node] = true;
		// pre-visit
		parameters.preOrders[node] = parameters.preCounter++;
		// iterate through adjacent nodes
		for(int i = 0; i < n; i++) if(matrix[node][i] != Integer.MAX_VALUE) {
			if(!parameters.visited[i]) {
				dfsVisit(matrix, i, parameters);
			}
			else if(parameters.preOrders[i] < parameters.preOrders[node] && parameters.postOrders[i] == NULL) { // back-edge
				parameters.isCyclic = true;
			}
		}
		// post-visit
		parameters.postOrders[node] = parameters.postCounter++;
	}
	
	/**
	 * Breadth-First-Search
	 */
	public static BFSResult bfs(int[][] matrix, int startNode) {
		int n = matrix.length;
		// initialization
		Queue<Integer> queue = new Queue<Integer>();
		boolean[] visited = new boolean[n]; // can be replaced by "bfsOrder[i] != NULL"
		boolean[] activated = new boolean[n]; // in the queue
		// initialize BFS results
		int bfsCount = 0;
		int[] bfsOrder = new int[n];
		int[] distance = new int[n];
		for(int i = 0; i < n; i++) bfsOrder[i] = distance[i] = NULL;
		boolean cyclic = false;
		
		queue.enqueue(startNode);
		distance[startNode] = 0;
		while(queue.size() != 0) {
			int node = queue.dequeue();
			visited[node] = true;
			bfsOrder[node] = bfsCount++;
			// iterate over adjacent nodes
			for(int i = 0; i < n; i++) if(matrix[node][i] != Integer.MAX_VALUE) {
				if(!visited[i] && !activated[i]) {
					// enqueue adjacent (not visited/activated) nodes
					queue.enqueue(i);
					activated[i] = true;
					if(distance[i] == NULL) distance[i] = distance[node] + 1;
				} else if(visited[i] && distance[node] > distance[i]) {
					// adjacent vertex has smaller distance
					cyclic = true;
				}
			}
		}
		
		return new BFSResult(bfsOrder, distance, cyclic);
	}
}

class DFSParameters {
	boolean[] visited; // can be replaced by "preOrders[i] != NULL"
	int[] preOrders;
	int[] postOrders;
	int preCounter = 0;
	int postCounter = 0;
	boolean isCyclic = false;
	public DFSParameters(int n) {
		visited = new boolean[n];
		preOrders = new int[n];
		postOrders = new int[n];
		for(int i = 0; i < n; i++) preOrders[i] = postOrders[i] = GraphSearch.NULL;
	}
}

class DFSResult {
	int[] preOrder;
	int[] postOrder;
	boolean isCyclic;
	
	public DFSResult(int[] preOrder, int[] postOrder, boolean isCyclic) {
		this.preOrder = preOrder;
		this.postOrder = postOrder;
		this.isCyclic = isCyclic;
	}
	
	public int[] getTopoOrder() {
		if(isCyclic) return null;
		else {
			int[] postOrderSorted = getPostOrderSorted();
			int n = postOrderSorted.length;
			int[] topoOrder = new int[n];
			for(int i = 0; i < n; i++) topoOrder[n-i-1] = postOrderSorted[i];
			return topoOrder;	
		}
	}
	public int[] getPreOrderSorted() {
		int[] order = new int[preOrder.length];
		for(int i = 0; i < preOrder.length; i++) order[i] = GraphSearch.NULL;
		for(int i = 0; i < preOrder.length; i++) if(preOrder[i] != GraphSearch.NULL) order[preOrder[i]] = i;
		return order;
	}
	public int[] getPostOrderSorted() {
		int[] order = new int[postOrder.length];
		for(int i = 0; i < postOrder.length; i++) order[i] = GraphSearch.NULL;
		for(int i = 0; i < postOrder.length; i++) if(postOrder[i] != GraphSearch.NULL) order[postOrder[i]] = i;
		return order;
	}
}

class BFSResult {
	int[] bfsOrder;
	int[] distance;
	boolean cyclic;
	
	public BFSResult(int[] bfsOrder, int[] distance, boolean cyclic) {
		this.bfsOrder = bfsOrder;
		this.distance = distance;
		this.cyclic = cyclic;
	}
	
	public int[] getBFSOrderSorted() {
		int[] order = new int[bfsOrder.length];
		for(int i = 0; i < order.length; i++) order[i] = GraphSearch.NULL;
		for(int i = 0; i < bfsOrder.length; i++) if(bfsOrder[i] != GraphSearch.NULL) order[bfsOrder[i]] = i;
		return order;
	}
}
