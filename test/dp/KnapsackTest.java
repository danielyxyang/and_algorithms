package dp;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import dp.Knapsack.Solution;
import tools.TestScanner;
import tools.TestTools;

public class KnapsackTest {

	@Test
	public void testKnapsackByWeight() throws FileNotFoundException {
		TestScanner scanner = TestTools.getTestScanner("test/dp/files/knapsack.txt");
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			int maxWeight = scanner.nextInt();
			int[] values = TestTools.parseLine(scanner, n);
			int[] weights = TestTools.parseLine(scanner, n);
			int resultValue = scanner.nextInt();
			int[] resultElements = TestTools.parseLine(scanner, n);
			
			Solution solution = Knapsack.solveByWeight(values, weights, maxWeight);
			assertEquals(resultValue, solution.maxValue);
			assertArrayEquals(resultElements, solution.elementsToIntArray());
		}
	}
	
	@Test
	public void testKnapsackByValue() throws FileNotFoundException {
		TestScanner scanner = TestTools.getTestScanner("test/dp/files/knapsack.txt");
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			int maxWeight = scanner.nextInt();
			int[] values = TestTools.parseLine(scanner, n);
			int[] weights = TestTools.parseLine(scanner, n);
			int resultValue = scanner.nextInt();
			int[] resultElements = TestTools.parseLine(scanner, n);
			
			Solution solution = Knapsack.solveByValue(values, weights, maxWeight);
			assertEquals(resultValue, solution.maxValue);
			assertArrayEquals(resultElements, solution.elementsToIntArray());
		}
	}

	@Test
	public void testKnapsackApprox() throws FileNotFoundException {
		double approxErrorRate = 0.9;
		TestScanner scanner = TestTools.getTestScanner("test/dp/files/knapsack.txt");
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			int maxWeight = scanner.nextInt();
			int[] values = TestTools.parseLine(scanner, n);
			int[] weights = TestTools.parseLine(scanner, n);
			int resultValue = scanner.nextInt();
			TestTools.parseLine(scanner, n);
			
			Solution solution = Knapsack.approxByValue(values, weights, maxWeight, approxErrorRate);
			assertEquals(resultValue, solution.maxValue, approxErrorRate * resultValue);
		}
	}

	@Test
	public void testKnapsackPerformance() throws FileNotFoundException {
		int[] values;
		int[] weights;
		Solution solution;
		
		values = new int[] {825594,1677009,1676628,1523970,943972,97426,69666,1296457,1679693,1902996,1844992,1049289,1252836,1319836,953277,2067538,675367,853655,1826027,65731,901489,577243,466257,369261};
		weights = new int[] {382745,799601,909247,729069,467902,44328,34610,698150,823460,903959,853665,551830,610856,670702,488960,951111,323046,446298,931161,31385,496951,264724,224916,169684};
		solution = new Solution(13549094, new boolean[] {true,true,false,true,true,true,false,false,false,true,true,false,true,false,false,true,false,false,false,false,false,true,true,true});
		
		long time1 = System.currentTimeMillis();
		Solution knapsackWei = Knapsack.solveByWeight(values, weights, 6404180);
		long time2 = System.currentTimeMillis();
		Solution knapsackVal = Knapsack.solveByValue(values, weights, 6404180);
		long time3 = System.currentTimeMillis();
		Solution knapsackApprox = Knapsack.approxByValue(values, weights, 6404180, 0.2);
		long time4 = System.currentTimeMillis();
		
		assertEquals(solution.toString(), knapsackWei.toString());
		assertEquals(solution.toString(), knapsackVal.toString());
		
		System.out.println("Time (by Weight): " + (time2-time1) + "ms");
		System.out.println("Time (by Value):  " + (time3-time2) + "ms");
		System.out.println("Time (approx.):   " + (time4-time3) + "ms");
		System.out.println("Solution:               " + solution);
		System.out.println("Approximative solution: " + knapsackApprox);
		System.out.println("Approximation error: " + (1 - ((double) knapsackApprox.maxValue / knapsackVal.maxValue)));
		System.out.println();
	}
}
