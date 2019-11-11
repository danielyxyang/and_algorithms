package graph;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import datastructure.Graph;
import tools.FastScanner;
import tools.TestScanner;
import tools.TestTools;

public class MSTTest {

	@Test
	public void testPrim() throws FileNotFoundException {
		TestScanner scanner = TestTools.getTestScanner("test/graph/files/mst.txt");
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			Graph graph = TestTools.parseGraph(scanner, n, 0, true);
			int totalWeight = scanner.nextInt();
			
			int result = MST.prim(graph.getMatrix());
			assertEquals(totalWeight, result);
		}
	}
	
	@Test
	public void testPrimRuntime() throws FileNotFoundException {
		FastScanner scanner = TestTools.getFastScanner("test/graph/files/ssspPositiveLarge.in.txt");
		long time1 = System.currentTimeMillis();
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			Graph graph = TestTools.parseGraph(scanner, n, 0, true);
			MST.prim(graph.getMatrix());
		}
		long time2 = System.currentTimeMillis();
		System.out.println(time2-time1);
	}

	@Test
	public void testKruskal() throws FileNotFoundException {
		TestScanner scanner = TestTools.getTestScanner("test/graph/files/mst.txt");
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			Graph graph = TestTools.parseGraph(scanner, n, 0, true);
			int totalWeight = scanner.nextInt();
			
			int result = MST.kruskal(graph.getMatrix());
			assertEquals(totalWeight, result);
		}
	}
	
	@Test
	public void testKruskalRuntime() throws FileNotFoundException {
		FastScanner scanner = TestTools.getFastScanner("test/graph/files/ssspPositiveLarge.in.txt");
		long time1 = System.currentTimeMillis();
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			Graph graph = TestTools.parseGraph(scanner, n, 0, true);
			MST.kruskal(graph.getMatrix());
		}
		long time2 = System.currentTimeMillis();
		System.out.println(time2-time1);
	}

}
