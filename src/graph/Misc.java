package graph;

import datastructure.UnionFind;

public class Misc {
	/**
	 * Computes Eulercycle
	 * 
	 * Note: If an euler cycle does not exist, it computes
	 * the longest possible cycle.
	 */
	public static int[] eulercycle(int[][] matrix) {
		int n = matrix.length;
		LinkedList path = new LinkedList();
		boolean[][] usedEdges = new boolean[n][n];
		
		// find eulercycle by finding longest path
		int startNode = 0;
		LinkedList.ListNode current = path.addLast(startNode);
		
		while(current != null) {
			LinkedList cycle = findCycle(matrix, usedEdges, current.key);
			if(cycle != null) {
				cycle.removeFirst();
				path.merge(cycle, current);
			}
			// traverse the path, until 
			current = current.next;
		}
		
		// copy path to array
		int[] pathArray = new int[path.size];
		for(int i = 0; i < pathArray.length; i++) {
			pathArray[i] = path.removeFirst();
		}
		
		return pathArray;
	}
	private static LinkedList findCycle(int[][] matrix, boolean[][] usedEdges, int startNode) {
		int n = matrix.length;
		LinkedList cycle = new LinkedList();
		int current = startNode;
		boolean foundUnusedEdge = true;
		while(foundUnusedEdge) {
			cycle.addLast(current);
			foundUnusedEdge = false;
			// iterate over adjacent nodes
			for(int i = 0; i < n && !foundUnusedEdge; i++) if(matrix[current][i] != Integer.MAX_VALUE) {
				if(!usedEdges[current][i]) { // unused edge
					usedEdges[current][i] = usedEdges[i][current] = true;
					current = i;
					foundUnusedEdge = true;
				}
			}
		}
		return current == startNode && cycle.size > 1 ? cycle : null;
	}
	
	/**
	 * Counts number of triangles (cycles of length 3)
	 */
	public static int countTriangles(int[][] matrix) {
		int n = matrix.length;
		int triangleCount = 0;
		// naive implementation (still one of the efficient ones)
		for(int i = 0; i < n; i++) for(int j = 0; j < n; j++) for(int k = 0; k < n; k++) {
			if(matrix[i][j] != Integer.MAX_VALUE && matrix[j][k] != Integer.MAX_VALUE && matrix[k][i] != Integer.MAX_VALUE) {
				triangleCount++;
			}
		}
		return triangleCount/6;
	}
	public static int countTrianglesByMatrixMult(int[][] matrix) {
		int n = matrix.length;
		// copy matrix and change no-edge representation
		int[][] matrixCopy = new int[n][n];
		for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) {
			if(matrix[i][j] == Integer.MAX_VALUE) matrixCopy[i][j] = 0;
			else matrixCopy[i][j] = matrix[i][j];
		}
		// calculate matrix^3
		int[][] result = matrixMult(matrixMult(matrixCopy, matrixCopy), matrixCopy);
		// calculate number of triangles by trace
		int triangles = 0;
		for(int i = 0; i < result.length; i++) triangles += result[i][i];
		return triangles/6;
	}
	private static int[][] matrixMult(int[][] a, int[][] b) {
		int n = b.length;
		int[][] result = new int[n][n];
		for(int i = 0; i < n; i++) for(int j = 0; j < n; j++) {
			for(int k = 0; k < n; k++) {
				result[i][j] += a[i][k] * b[k][j];
			}
		}
		return result;
	}
	
	/**
	 * Counts number of graph components
	 */
	public static int countComponents(int[][] matrix) {
		int n = matrix.length;
		boolean[] visited = new boolean[n];
		UnionFind uf = new UnionFind(n);
		for(int i = 0; i < n; i++) {
			if(!visited[i]) dfsVisit(matrix, visited, i, uf);
		}
		return uf.size();
	}
	private static void dfsVisit(int[][] matrix, boolean[] visited, int node, UnionFind uf) {
		int n = matrix.length;
		visited[node] = true;
		for(int i = 0; i < n; i++) if(matrix[node][i] != Integer.MAX_VALUE) {
			uf.union(node, i);
			if(!visited[i]) dfsVisit(matrix, visited, i, uf);
		}
	}
}

class LinkedList { // custom LinkedList implementation with "merge" function
	ListNode first;
	ListNode last;
	int size = 0;
	public ListNode addLast(int key) {
		ListNode node = new ListNode(key);
		if(last == null) first = last = node;
		else {
			last.next = node;
			last = last.next;
		}
		size++;
		return node;
	}
	public int removeFirst() {
		ListNode node = first;
		if(first == last) first = last = null;
		else first = first.next;
		size--;
		return node.key;
	}
	public void merge(LinkedList list, ListNode node) {
		if(node == last) last = list.last;
		else list.last.next = node.next;
		node.next = list.first;
		size += list.size;
	}
	
	class ListNode {
		int key;
		ListNode next;
		public ListNode(int key) {
			this.key = key;
		}
	}
}