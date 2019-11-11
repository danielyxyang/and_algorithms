package datastructure;

public class UnionFind {
	private int[] parent;
	private int[] rank;
	private int size; // number of components
	
	public UnionFind(int size) {
		parent = new int[size];
		rank = new int[size];
		for(int i = 0; i < size; i++) make(i);
		this.size = size;
	}
	
	
	public void union(int node1, int node2) {
		int root1 = find(node1);
		int root2 = find(node2);
		if(root1 == root2) return;
		// union by rank
		if(rank[root1] < rank[root2]) parent[root1] = root2;
		else if(rank[root1] > rank[root2]) parent[root2] = root1;
		else {
			parent[root2] = root1;
			rank[root1]++;
		}
		size--;
	}
	
	public int find(int node) {
		// path compression
		if(node != parent[node]) parent[node] = find(parent[node]);
		return parent[node];
	}
	
	public void make(int i) {
		parent[i] = i;
		rank[i] = 1;
		size++;
	}
	
	public int size() {
		return size;
	}
}
