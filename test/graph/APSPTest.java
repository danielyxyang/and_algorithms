package graph;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import datastructure.Graph;

import tools.TestScanner;
import tools.TestTools;

public class APSPTest {

	@Test
	public void testFW() throws FileNotFoundException {
		TestScanner scanner = TestTools.getTestScanner("test/graph/files/apsp.txt");
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			Graph graph = TestTools.parseGraph(scanner, n, -1, true);
			int[][] distances = TestTools.parseMatrix(scanner, n);
			
			int[][] result = APSP.floydwarshall(graph.getMatrix());
			
			for(int i = 0; i < distances.length; i++) assertArrayEquals(distances[i], result[i]);
		}
	}
	
	@Test
	public void testJohnson() throws FileNotFoundException {
		TestScanner scanner = TestTools.getTestScanner("test/graph/files/apsp.txt");
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			Graph graph = TestTools.parseGraph(scanner, n, -1, true);
			int[][] distances = TestTools.parseMatrix(scanner, n);
			
			int[][] result = APSP.johnson(graph.getMatrix());
			
			for(int i = 0; i < distances.length; i++) {
				assertArrayEquals(distances[i], result[i]);
			}
		}
	}

}
