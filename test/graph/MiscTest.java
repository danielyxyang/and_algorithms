package graph;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;

import datastructure.Graph;
import tools.TestScanner;
import tools.TestTools;

class MiscTest {

	@Test
	void testEulercycle() throws FileNotFoundException {
		TestScanner scanner = TestTools.getTestScanner("test/graph/files/eulercycle.txt");
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			int[][] matrix = TestTools.parseGraph(scanner, n).getMatrix();
			int pathSize = scanner.nextInt(); // number of edges
			
			int[] path = Misc.eulercycle(matrix);
			// sanity check: path size
			assertEquals(pathSize, path.length - 1);
			// check euler cycle
			boolean[][] usedEdges = new boolean[n][n];
			int lastNode = path[0];
			for(int i = 1; i < path.length; i++) {
				int nextNode = path[i];
				// check: each edge used exactly once
				assertFalse(usedEdges[lastNode][nextNode]);
				assertFalse(usedEdges[nextNode][lastNode]);
				usedEdges[lastNode][nextNode] = usedEdges[nextNode][lastNode] = true;
				lastNode = nextNode;
			}
			for(int i = 0; i < n; i++) for(int j = 0; j < n; j++) {
				// check: all edges used
				assertEquals(matrix[i][j] != Integer.MAX_VALUE, usedEdges[i][j]);
			}
		}
	}
	
	@Test
	void testTriangleCount() throws FileNotFoundException {
		TestScanner scanner = TestTools.getTestScanner("test/graph/files/triangleCount.txt");
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			Graph graph = TestTools.parseGraph(scanner, n);
			int result = scanner.nextInt();
			
			assertEquals(result, Misc.countTriangles(graph.getMatrix()));
			assertEquals(result, Misc.countTrianglesByMatrixMult(graph.getMatrix()));
		}
	}
	
	@Test
	void testComponentCount() throws FileNotFoundException {
		TestScanner scanner = TestTools.getTestScanner("test/graph/files/componentCount.txt");
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			Graph graph = TestTools.parseGraph(scanner, n);
			int result = scanner.nextInt();
			
			assertEquals(result, Misc.countComponents(graph.getMatrix()));
		}
	}
}
