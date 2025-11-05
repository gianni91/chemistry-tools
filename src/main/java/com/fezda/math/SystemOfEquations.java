package com.fezda.math;

public class SystemOfEquations {
	
	public static double [][] getCopy (double [][] matrix) {
		double[][] result = new double[matrix.length][matrix[0].length];
		for (int r = 0; r < matrix.length; r++) {
			for (int c = 0; c < matrix[0].length; c++) {
				result[r][c] = matrix[r][c];
			}
		}
		return result;
	}
	
	public static double [][] getCropped (double [][] matrix, int numRows, int numCols) {
		double[][] result = new double[numRows][numCols];
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				result[r][c] = matrix[r][c];
			}
		}
		return result;
	}
	
	public static void displayMatrix (double [][] matrix) {
		System.out.print("[");
		for (int r = 0; r < matrix.length; r++) {
			if (r > 0) System.out.print(" ");
			displayArray(matrix[r]);
			if (r == matrix.length-1) System.out.print("]");
			System.out.println();
		}
	}
	
	public static void displayArray (double [] array) {
		System.out.print("[");
		for (int r = 0; r < array.length; r++) {
			if (r > 0) System.out.print(", ");
			System.out.print(array[r]);
		}
		System.out.print("]");
	}

	private static void swapRows (double[][] matrix, int rowIndex1, int rowIndex2) {
		double temp = 0;
		for (int i = 0; i < matrix[0].length; i++) {
			temp = matrix[rowIndex1][i];
			matrix[rowIndex1][i] = matrix[rowIndex2][i];
			matrix[rowIndex2][i] = temp;
		}
	}
	
	private static void addRowTo (double[][] matrix, int rowIndex1, int rowIndex2) {
		for (int i = 0; i < matrix[0].length; i++) {
			matrix[rowIndex2][i] += matrix[rowIndex1][i];
		}
	}
	
	private static void scaleRow (double[][] matrix, int rowIndex, double scaleBy) {
		if (scaleBy == 0) throw new IllegalArgumentException ("Can't scale row by 0");
		for (int i = 0; i < matrix[0].length; i++) {
			matrix[rowIndex][i] *= scaleBy;
		}
	}
	
	private static void addScaledRowTo (double[][] matrix, int rowIndex1, int rowIndex2, double scaleBy) {
		if (scaleBy == 0) throw new IllegalArgumentException ("Can't scale row by 0");
		scaleRow(matrix, rowIndex1, scaleBy);
		addRowTo(matrix, rowIndex1, rowIndex2);
		scaleRow(matrix, rowIndex1, 1/scaleBy);
	}
	
	// Converts from REF (row echelon form) to RREF (reduced REF)
	private static void simpleBackSubstitution (double[][] matrix) {
		for (int r = matrix.length-1; r > 0; r--) {
			for (int r2 = r-1; r2 > -1; r2--) {
				if (matrix[r2][r] != 0) addScaledRowTo(matrix,r,r2,-1*matrix[r2][r]);
			}
		}
	}
	
	// Makes sure row doesn't have 0 where column value equals row value
	private static void rearrangeRows (double[][] matrix) {
		for (int r = 0; r < matrix.length; r++) {
			if (matrix[r][r] == 0) {
				for (int r2 = 0; r2 < matrix.length; r2++) {
					if (r2 != r && matrix[r2][r] != 0 && !(matrix[r][r2] == 0 && r2 < r)) {
						swapRows(matrix,r,r2);
						break;
					}
				}
			}
		}
		for (int r = 0; r < matrix.length; r++) {
			if (matrix[r][r] == 0) {
				//throw (new IllegalStateException("Matrix rows still have 0 on the diagonal"));
				System.out.println("Warning: Failed to arrange matrix rows to prevent zeros at the diagonal");
			}
		}
	}
	
	private static void simpleGaussianElimination (double[][] matrix) {
		rearrangeRows(matrix);
		scaleRow(matrix,0,1/matrix[0][0]);
		for (int r = 1; r < matrix.length; r++) {
			for (int c = 0; c < r; c++) {
				if (matrix[r][c] != 0) addScaledRowTo(matrix,c,r,-1*matrix[r][c]);
			}
			if (matrix[r][r] != 0) scaleRow(matrix,r,1/matrix[r][r]);
		}
		simpleBackSubstitution(matrix);
	}
	
	public static double[] solveSimpleSystemOfEquations (double[][] matrix) {
		if (matrix.length < matrix[0].length-1) {
			throw new IllegalArgumentException ("Matrix must have shape [N,N+1], with 1 more column than there are rows");
		}
		
		double[][] moddable = getCopy(matrix);
		if (matrix.length >= matrix[0].length) {
			System.out.println("Warning: More equations than variables in systems of equations matrix, ignoring last " + (matrix.length - matrix[0].length + 1));
			moddable = getCropped(moddable, matrix[0].length-1, matrix[0].length);
		}
		simpleGaussianElimination(moddable);
		
		double[] result = new double[moddable.length];
		for (int r = 0; r < moddable.length; r++) {
			result[r] = moddable[r][moddable[0].length-1];
		}
		if (Double.isNaN(result[0])) System.out.println("Warning: No solution for provided equations");

		return result;
	}
	
	
}
