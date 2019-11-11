package graph;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import datastructure.Graph;
import tools.TestScanner;
import tools.TestTools;

public class GraphSearchTest {

	@Test
	public void testDFS() throws FileNotFoundException {
		TestScanner scanner = TestTools.getTestScanner("test/graph/files/dfs.txt");
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			int startNode = scanner.nextInt();
			
			Graph graph = TestTools.parseGraph(scanner, n);
			int[] preOrder = TestTools.parseLine(scanner, n);
			int[] postOrder = TestTools.parseLine(scanner, n);
			boolean cyclic = Boolean.valueOf(scanner.next());
			int[] topoOrder = !cyclic ? TestTools.parseLine(scanner, n) : null;
			
			DFSResult result = (startNode != -1 ? GraphSearch.dfs(graph.getMatrix(), startNode) : GraphSearch.dfs(graph.getMatrix()));
			assertArrayEquals(preOrder, result.getPreOrderSorted());	
			assertArrayEquals(postOrder, result.getPostOrderSorted());	
			assertEquals(cyclic, result.isCyclic);
			if(!cyclic) assertArrayEquals(topoOrder, result.getTopoOrder());
		}
	}
	
	@Test
	public void testBFS() throws FileNotFoundException {
		TestScanner scanner = TestTools.getTestScanner("test/graph/files/bfs.txt");
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			int startNode = scanner.nextInt();

			Graph graph = TestTools.parseGraph(scanner, n);
			int[] bfsOrder = TestTools.parseLine(scanner, n);
			int[] distance = TestTools.parseLine(scanner, n);
			boolean cyclic = Boolean.valueOf(scanner.next());
			
			BFSResult result = GraphSearch.bfs(graph.getMatrix(), startNode);
			assertArrayEquals(bfsOrder, result.getBFSOrderSorted());
			assertArrayEquals(distance, result.distance);
			assertEquals(cyclic, result.cyclic);	
		}
	}
}
