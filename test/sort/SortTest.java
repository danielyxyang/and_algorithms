package sort;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import tools.FastScanner;
import tools.TestTools;

@TestMethodOrder(OrderAnnotation.class)
public class SortTest {
	@Test
	@Order(1)
	public void testBubblesort() throws FileNotFoundException {
		new SortTestCase("Bubblesort") {
			@Override
			protected void sort(int[] array) {Sort.bubblesort(array);}
		}.check();
	}
	
	@Test
	@Order(2)
	public void testSelectionSort() throws FileNotFoundException {
		new SortTestCase("Selectionsort") {
			@Override
			protected void sort(int[] array) {Sort.selectionsort(array);}
		}.check();
	}
	
	@Test
	@Order(3)
	public void testInsertionsort() throws FileNotFoundException {
		new SortTestCase("Insertionsort") {
			@Override
			protected void sort(int[] array) {Sort.insertionsort(array);}
		}.check();
	}
	
	@Test
	@Order(4)
	public void testHeapsort() throws FileNotFoundException {
		new SortTestCase("Heapsort") {
			@Override
			protected void sort(int[] array) {Sort.heapsort(array);}
		}.check();
	}
	
	@Test
	@Order(5)
	public void testMergesort() throws FileNotFoundException {
		new SortTestCase("Mergesort") {
			@Override
			protected void sort(int[] array) {Sort.mergesort(array);}
		}.check();
	}
	
	@Test
	@Order(6)
	public void testQuicksort() throws FileNotFoundException {
		new SortTestCase("Quicksort") {
			@Override
			protected void sort(int[] array) {Sort.quicksort(array);}
		}.check();
	}
	
	@Test
	@Order(7)
	public void testQuicksortRandomized() throws FileNotFoundException {
		new SortTestCase("Quicksort Rd") {
			@Override
			protected void sort(int[] array) {Sort.quicksortRandomized(array);}
		}.check();
	}
	
//	private static void generateTestFile(int count, int size) throws FileNotFoundException {
//		java.io.PrintStream stream = new java.io.PrintStream(new java.io.File("test/sort/files/sortLarge" + count + "_" + size + ".txt"));
//		for(int i = 0; i < count; i++) {
//			stream.println(size);
//			int[] array = new int[size];
//			for(int k = 0; k < size; k++) array[k] = (int) (Math.random() * 2 * size);
//			// print array
//			for(int k = 0; k < size; k++) stream.print(array[k] + " ");
//			stream.println();
//			// print sorted array
//			Arrays.sort(array);
//			for(int k = 0; k < size; k++) stream.print(array[k] + " ");
//			stream.println();
//			stream.flush();
//		}
//		System.out.println("Test file " + count + "x" + size + " generated.");
//	}
}

abstract class SortTestCase {
	private String name = "";
	
	public SortTestCase(String name) {
		this.name = name;
	}
	
	public void check() throws FileNotFoundException {
		FastScanner scanner = TestTools.getFastScanner("test/sort/files/sortLarge1000_1000.txt");
//		FastScanner scanner = TestTools.getFastScanner("test/sort/files/sort.txt");
		ArrayList<Integer> swapCounts = new ArrayList<Integer>();
		long time1 = System.currentTimeMillis();
		while(scanner.hasNext()) {
			int length = scanner.nextInt();
			int[] array = TestTools.parseLine(scanner, length);
			int[] result = TestTools.parseLine(scanner, length);

			Sort.swapCount = 0;
			sort(array);
			swapCounts.add(Sort.swapCount);
			assertArrayEquals(result, array);
		}
		long time2 = System.currentTimeMillis();
		
		// print statistics
		int sum = 0;
		for(int swapCount : swapCounts) sum += swapCount;
		System.out.println(name + ":\t" + (time2-time1) + "ms\t" + Collections.max(swapCounts) + "\t" + sum / swapCounts.size());
	}
	abstract protected void sort(int[] array);
}
