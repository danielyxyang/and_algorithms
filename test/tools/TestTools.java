package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import datastructure.Graph;

public class TestTools {
	public static TestScanner getTestScanner(String file) throws FileNotFoundException {
		return new TestScanner(new Scanner(new File(file)));
	}
	public static FastScanner getFastScanner(String file) throws FileNotFoundException {
		return new FastScanner(new FileInputStream(new File(file)));
	}
	public static Graph parseGraph(Scannable scanner, int n) {
		return parseGraph(scanner, n, 0, false); // default: unweighted graph
	}
	public static Graph parseGraph(Scannable scanner, int n, int noEdge, boolean weighted) {
		Graph graph = new Graph(n); // default: directed graph
		for(int i = 0; i < n; i++) for(int j = 0; j < n; j++) {
			int edge = scanner.nextInt();
			if(edge != noEdge) {
				if(weighted) graph.insertEdge(i, j, edge);
				else graph.insertEdge(i, j);
			}
		}
		return graph;
	}
	public static int[][] parseMatrix(Scannable scanner, int n) {
		int[][] result = new int[n][n];
		for(int i = 0; i < n; i++) result[i] = parseLine(scanner, n);
		return result;
	}
	public static int[] parseLine(Scannable scanner, int n) {
		int[] result = new int[n];
		for(int i = 0; i < n; i++) result[i] = scanner.nextInt();
		return result;
	}
}
