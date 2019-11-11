package graph;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import datastructure.Graph;
import tools.FastScanner;
import tools.TestScanner;
import tools.TestTools;

public class SSSPTest {

	@Test
	public void testDijkstra() throws FileNotFoundException {
		TestScanner scanner = TestTools.getTestScanner("test/graph/files/ssspPositive.in.txt");
		TestScanner scannerResult = TestTools.getTestScanner("test/graph/files/ssspPositive.out.txt");
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			
			Graph graph = TestTools.parseGraph(scanner, n, -1, true);
			int[] distance = TestTools.parseLine(scannerResult, n);
			
			SSSPResult result = SSSP.dijkstra(graph.getMatrix(), 0);
			assertArrayEquals(distance, result.distance);			
		}
	}
	@Test
	public void testDijkstraLarge() throws FileNotFoundException {
		// reference solution runs in 2.3 s on i7-8650U
		// and 1 s on Judge
		FastScanner scanner = TestTools.getFastScanner("test/graph/files/ssspPositiveLarge.in.txt");
		FastScanner scannerResult = TestTools.getFastScanner("test/graph/files/ssspPositiveLarge.out.txt");
		long time1 = System.currentTimeMillis();
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			
			Graph graph = TestTools.parseGraph(scanner, n, -1, true);
			int[] distance = TestTools.parseLine(scannerResult, n);
			
			SSSPResult result = SSSP.dijkstra(graph.getMatrix(), 0);
			assertArrayEquals(distance, result.distance);
		}
		long time2 = System.currentTimeMillis();
		System.out.println("Time: " + (time2-time1) + "ms");
		System.out.println("Max. Queue Size: " + SSSP.maxSize);
	}

	@Test
	public void testBF() throws FileNotFoundException {
		FastScanner scanner = TestTools.getFastScanner("test/graph/files/ssspPositive.in.txt");
		FastScanner scannerResult = TestTools.getFastScanner("test/graph/files/ssspPositive.out.txt");
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			
			Graph graph = TestTools.parseGraph(scanner, n, -1, true);
			int[] distance = TestTools.parseLine(scannerResult, n);
			
			SSSPResult result = SSSP.bellmanford(graph.getMatrix(), 0);
			assertArrayEquals(distance, result.distance);		
		}
	}
	
	@Test
	public void testBFNegativeCycle() throws FileNotFoundException {
		TestScanner scanner = TestTools.getTestScanner("test/graph/files/ssspNegative.txt");
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			
			Graph graph = TestTools.parseGraph(scanner, n, 0, true);
			boolean negativeCycle = Boolean.valueOf(scanner.next());
			int[] distance = !negativeCycle ? TestTools.parseLine(scanner, n) : null;
			
			SSSPResult result = SSSP.bellmanford(graph.getMatrix(), 0);
			assertArrayEquals(distance, result.distance);		
		}
	}
}
