package datastructure;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Graph {
	private int[][] matrix;
	private boolean directed;
	
	public Graph(int n) {
		this(n, true);
	}
	public Graph(int n, String matrix) {
		this(n, true);
		buildGraph(matrix);
	}
	public Graph(int n, boolean directed) {
		this.matrix = new int[n][n];
		this.directed = directed;
		for(int i = 0; i < matrix.length; i++) Arrays.fill(matrix[i], Integer.MAX_VALUE);
	}
	
	public void insertEdge(int u, int v) {
		insertEdge(u, v, 1);
	}
	public void insertEdge(int u, int v, int weight) {
		matrix[u][v] = weight;
		if(!directed) matrix[v][u] = weight;
	}
	public boolean[][] getBooleanMatrix() {
		int n = matrix.length;
		boolean[][] matrix = new boolean[n][n];
		for(int i = 0; i < n; i++) for(int j = 0; j < n; j++) matrix[i][j] = this.matrix[i][j] != Integer.MAX_VALUE;
		return matrix;
	}
	public int[][] getMatrix() {
		return matrix;
	}
	
	public void buildGraph(String matrix) {
		Scanner scanner = new Scanner(matrix);
		int n = this.matrix.length;
		for(int i = 0; i < n; i++) for(int j = 0; j < n; j++) this.matrix[i][j] = scanner.nextInt();
		scanner.close();
	}

	public String graphviz() {
		String graphviz = "digraph G {\n";
    	int n = matrix.length;
		for (int i = 0; i < n; i++) {
			graphviz += "\"" + i + "\" [xlabel=<<font color=\"red\">" + i + "</font>>];\n";
		}
		for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) {
			if (i != j && matrix[i][j] != Integer.MAX_VALUE) {
				graphviz += "\"" + i + "\" -> \"" + j + "\" [label=\"" + matrix[i][j] + "\"];\n";
			}
		}
		graphviz += "}\n";
		return graphviz;
	}
	public void saveGraphviz(String filename) {
		try {
	    	PrintWriter writer = new PrintWriter(filename, "UTF-8");
			writer.write(graphviz());
	    	writer.close();
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}
}
