package datastructure;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;

import tools.FastScanner;
import tools.TestTools;

public class BasicDatastructureTest {

	@Test
	public void testMaxHeap() {
		Heap<String> heap = new Heap<String>(Heap.MAX);
		heap.insert("b", 5);
		heap.insert("d", 7);
		heap.insert("c", 6);
		heap.insert("a", 4);
		assertEquals(4, heap.size());
		assertEquals("d", heap.top());
				
		heap.changeKey("d", 2);
		heap.changeKey("c", 9);
		heap.remove("a");
		heap.insert("a", 5);
		heap.remove("b");
		
		assertEquals("c", heap.extract());
		assertEquals("a", heap.extract());
		assertEquals("d", heap.extract());
		assertEquals(0, heap.size());
	}
	
	@Test
	public void testMinHeap() {
		Heap<String> heap = new Heap<String>(Heap.MIN);
		heap.insert("b", 5);
		heap.insert("d", 7);
		heap.insert("c", 6);
		heap.insert("a", 4);
		assertEquals(4, heap.size());
		assertEquals("a", heap.top());
				
		heap.changeKey("b", 2);
		heap.changeKey("c", 9);
		heap.remove("d");
		
		assertEquals("b", heap.extract());
		assertEquals("a", heap.extract());
		assertEquals("c", heap.extract());
		assertEquals(0, heap.size());
	}
	
	@Test
	public void testHeap() throws FileNotFoundException {
		FastScanner scanner = TestTools.getFastScanner("test/datastructure/files/heap.in.txt");
		FastScanner scannerResult = TestTools.getFastScanner("test/datastructure/files/heap.out.txt");
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			int[] array = TestTools.parseLine(scanner, n);
			int[] result = TestTools.parseLine(scannerResult, n);
			
			Heap<Integer> maxHeap = new Heap<Integer>(Heap.MAX);
			Heap<Integer> minHeap = new Heap<Integer>(Heap.MIN);
			for(int val : array) {
				maxHeap.insert(val, val);
				minHeap.insert(val, val);
			}
			int[] maxHeapResult = new int[n];
			int[] minHeapResult = new int[n];
			for(int i = 0; i < n; i++) {
				maxHeapResult[n-i-1] = maxHeap.extract();
				minHeapResult[i] = minHeap.extract();
			}
			
			assertArrayEquals(result, maxHeapResult);
			assertArrayEquals(result, minHeapResult);
		}
	}
	
	@Test
	public void testDictionary() {
		Dictionary dict = new Dictionary();
		dict.insertNode(5, 0);
		dict.insertNode(1, 0);
		dict.insertNode(2, 0);
		dict.insertNode(6, 0);
		dict.insertNode(4, 2);
		dict.insertNode(3, 0);
		dict.insertNode(7, 0);
		dict.insertNode(8, 0);
		dict.insertNode(9, 0);
		dict.printDict();
		assertEquals(2, dict.getNode(4).value);
		assertEquals(null, dict.getNode(10));
		try {
			dict.insertNode(9, 0);
			assertTrue(false);
		} catch(AssertionError e) {
			assertTrue(true);
		}
	}
	
//	@Test(timeout = 5000)
	public void testArrayPerformance() {
		Array<Integer> array = new Array<Integer>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		long time1, time2, time3;
		int n = 100000;
		
		System.out.println("PERFORMANCE: custom array vs. ArrayList");
		
		time1 = System.currentTimeMillis();
		for(int i = 0; i < n; i++) array.add(i);
		time2 = System.currentTimeMillis();
		for(int i = 0; i < n; i++) list.add(i);
		time3 = System.currentTimeMillis();
		System.out.println("add: " + (time2-time1) + " vs. " + (time3-time2));
		
		time1 = System.currentTimeMillis();
		while(array.size() != 0) array.remove(0);
		time2 = System.currentTimeMillis();
		while(list.size() != 0) list.remove(0);
		time3 = System.currentTimeMillis();
		System.out.println("remove: " + (time2-time1) + " vs. " + (time3-time2));
	}
}
