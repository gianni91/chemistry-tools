package com.fezda.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LinearAlgebraTest {
	
	@Test
	public void testCopyMatrix() {
		double [][] matrix = new double[][] {{1,2,5},{2,3,8}};
		double [][] copy = LinearAlgebra.getCopy(matrix);
		assertNotEquals(matrix, copy,"Copied reference rather than values");
		assertArrayEquals(matrix, copy,"Matrix not copied correctly");
	}
	
	@Test
	public void testSolveSimpleSystemOfEquations1() {
		double [][] system = new double[][] {{1,2,5},{2,3,8}};
		double [] result = LinearAlgebra.solveSimpleSystemOfEquations(system);
		double [] expected = new double[] {1,2};
		assertArrayEquals(expected, result, "System of equations not giving expected result");
		
	}
	@Test
	public void testSolveSimpleSystemOfEquations2() {
		double [][] system = new double[][] {{1,2,5},{2,3,8},{3,4,11}};
		double [] result = LinearAlgebra.solveSimpleSystemOfEquations(system);
		double [] expected = new double[] {1,2};
		assertArrayEquals(expected, result, "Fails when provided more equations than variables");
		
	}
	@Test
	public void testSolveSimpleSystemOfEquations3() {
		double [][] system = new double[][] {{1,-1,-1},{2,-1,0}};
		double [] result = LinearAlgebra.solveSimpleSystemOfEquations(system);
		double [] expected = new double[] {1,2};
		assertArrayEquals(expected, result, "Fails when using negatives");
	}
	
	@Test
	public void testSolveSimpleSystemOfEquations4() {
		double [][] system = new double[][] {{0,0,1,3},{0,1,2,8},{1,2,3,14}};
		double [] result = LinearAlgebra.solveSimpleSystemOfEquations(system);
		double [] expected = new double[] {1,2,3};
		assertArrayEquals(expected, result, "Fails when 0 provided in first cols until last");
	}
	
	@Test
	public void testSolveSimpleSystemOfEquations5() {
		double [][] system = new double[][] {{1,2,3,14},{0,1,2,8},{0,0,1,3}};
		double [] result = LinearAlgebra.solveSimpleSystemOfEquations(system);
		double [] expected = new double[] {1,2,3};
		assertArrayEquals(expected, result, "Fails when in REF");
	}
	
	@Test
	public void testSolveSimpleSystemOfEquations6() {
		double [][] system = new double[][] {{1,0,0,1},{0,1,0,2},{0,0,1,3}};
		double [] result = LinearAlgebra.solveSimpleSystemOfEquations(system);
		double [] expected = new double[] {1,2,3};
		assertArrayEquals(expected, result, "Fails when in RREF");
	}
	
	@Test
	public void testSolveSimpleSystemOfEquations7() {
		double [][] system = new double[][] {{0,2,0,4},{0,1,0,2},{0,0,1,3}};
		double [] result = LinearAlgebra.solveSimpleSystemOfEquations(system);
		double [] expected = new double[] {Double.NaN,Double.NaN,Double.NaN};
		assertArrayEquals(expected, result, "Fails when no solution");
	}
	
	@Test
	public void testSolveSimpleSystemOfEquations8() {
		double [][] system1 = new double[][] {{1,2,3,14},{2,3,4,20},{3,2,1,10}};
		double [][] system2 = new double[][] {{2,3,4,20},{3,2,1,10},{1,2,3,14}};
		double [] result1 = LinearAlgebra.solveSimpleSystemOfEquations(system1);
		double [] result2 = LinearAlgebra.solveSimpleSystemOfEquations(system2);
		assertArrayEquals(result1, result2, "Different order of rows should get same result");
	}
	
	@Test
	public void testSolveSimpleSystemOfEquations9() {
		double [][] system = new double[][] {{0,0,1,3},{0,1,0,2},{1,0,0,1}};
		double [] result = LinearAlgebra.solveSimpleSystemOfEquations(system);
		double [] expected = new double[] {1,2,3};
		assertArrayEquals(expected, result, "Fails when rows in opposite order of REF");
	}
	
	@Test
	public void testSolveSimpleSystemOfEquations10() {
		double [][] system1 = new double[][] {{1,2,3,14},{0,1,2,8},{0,0,1,3}};
		double [][] system2 = new double[][] {{0,0,1,3},{1,2,3,14},{0,1,2,8}};
		double [][] system3 = new double[][] {{0,0,1,3},{0,1,2,8},{1,2,3,14}};
		double [] result1 = LinearAlgebra.solveSimpleSystemOfEquations(system1);
		double [] result2 = LinearAlgebra.solveSimpleSystemOfEquations(system2);
		double [] result3 = LinearAlgebra.solveSimpleSystemOfEquations(system3);
		assertArrayEquals(result1, result2, "Different result for different order of rows when some start with 0 in row 1");
		assertArrayEquals(result2, result3, "Different result for different order of rows when some start with 0 in row 1");
	}
	
	@Test
	public void testSolveSimpleSystemOfEquations11() {
		double [][] system = new double[][] {{0,1,1,5},{1,0,0,1},{1,2,0,5}};
		double [] result = LinearAlgebra.solveSimpleSystemOfEquations(system);
		double [] expected = new double[] {1,2,3};
		assertArrayEquals(expected, result, "Fails when earlier swap takes only row containing a later variable");
	}
	
	@Test
	public void testSolveSimpleSystemOfEquations12() {
		double [][] system = new double[][] {{1,0,1,4},{0,1,1,5},{0,1,0,2}};
		double [] result = LinearAlgebra.solveSimpleSystemOfEquations(system);
		double [] expected = new double[] {1,2,3};
		assertArrayEquals(expected, result, "Fails when first swap is swapped back to fix last row");
	}
	
	@Test
	public void testSolveSimpleSystemOfEquations13() {
		double [][] system = new double[][] {{1,2,3,14},{0,1,2,8},{0,0,1,3}};
		double [] result = LinearAlgebra.solveSimpleSystemOfEquations(system);
		double [] expected = new double[] {1,2,3};
		assertArrayEquals(expected, result, "Fails when can't satisfy both positions' requirements with 1 swap");
	}
	
	
}
