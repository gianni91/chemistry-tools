package com.fezda.chemistry;

import java.util.HashMap;

import com.fezda.math.SystemOfEquations;

public class Equation {
	
	public static void main (String[] args) {
		String eq = "C8H18 + O2 -> H2O + CO2";
		Equation equation = new Equation(eq);
		equation.display();
		int[] coefficients = equation.balance();
		displayArray(coefficients);		
	}
	
	private EquationSide reactantSide;
	private EquationSide productSide;
	
	public Equation (String equation) {
		String trimmed = equation.replace(" ", "");
		String[] sides = trimmed.split("->");
		reactantSide = new EquationSide(sides[0]);
		productSide = new EquationSide(sides[1]);
	}
	
	public void display () {
		reactantSide.display();
		System.out.print(" -> ");
		productSide.display();
		System.out.println();
	}
	
	/**************
	 * Use Bottomley's Method to balance a chemical equation
	 **************/
	public int[] balance() {
		String [] elements = reactantSide.getElements();
		
		// Fill out matrix for system of equations
		Molecule[] lhsMolecules = reactantSide.getMolecules();
		Molecule[] rhsMolecules = productSide.getMolecules();
		int rhsStart = lhsMolecules.length;
		double[][] sysOfEqMatrix = new double[elements.length + 1][reactantSide.getLength()+productSide.getLength() + 1];
		for (int mol = 0; mol < lhsMolecules.length; mol++) {
			HashMap<String,Integer> molElCounts = lhsMolecules[mol].getElementCounts();
			for (int el = 0; el < elements.length; el++) {
				if (molElCounts.containsKey(elements[el])) {
					sysOfEqMatrix[el][mol] = molElCounts.get(elements[el]);
				}
				else {
					sysOfEqMatrix[el][mol] = 0;
				}
			}
		}
		for (int mol2 = 0; mol2 < rhsMolecules.length; mol2++) {
			HashMap<String,Integer> molElCounts = rhsMolecules[mol2].getElementCounts();
			for (int el = 0; el < elements.length; el++) {
				if (molElCounts.containsKey(elements[el])) {
					sysOfEqMatrix[el][rhsStart+mol2] = -1 * molElCounts.get(elements[el]);
				}
				else {
					sysOfEqMatrix[el][rhsStart+mol2] = 0;
				}
			}
		}
		
		// Set a value of 1 for one of the variables, in order to have enough equations to solve for all variables
		sysOfEqMatrix[elements.length][0] = 1;
		sysOfEqMatrix[elements.length][sysOfEqMatrix.length] = 1;
		
//		displayMatrix(sysOfEqMatrix, elements);
		
//		int [] lhsCoefficients = new int[reactantSide.getLength() + productSide.getLength()];
//		int [] rhsCoefficients = new int[reactantSide.getLength() + productSide.getLength()];
		
		double[] coefficients  = SystemOfEquations.solveSimpleSystemOfEquations(sysOfEqMatrix);
		if (hasFractions(coefficients)) coefficients = getScaledToWholeNums(coefficients);
		
		return toIntArray(coefficients);
	}
	private static void displayMatrix(double[][] sysOfEqMatrix, String[] elements) {
		for (int r = 0; r < sysOfEqMatrix.length; r++) {
			if (r < sysOfEqMatrix.length - 1) System.out.print(elements[r] + ": ");
			else System.out.print( "   ");
			for (int c = 0; c < sysOfEqMatrix[r].length; c++) {
				System.out.print(sysOfEqMatrix[r][c] + ", ");
			}
			System.out.println();
		}
	}
	private boolean hasFractions (double [] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] % 1 != 0) return true;
		}
		return false;
	}
	// TODO - Make a better way to do this
	private double[] getScaledToWholeNums (double [] array) {
		double [] scaled = new double[array.length];
		int i = 2;
		for (; i < 101; i++) {
			for (int c = 0; c < array.length; c++) {
				scaled[c] = array[c] * i;
			}
			if (!hasFractions(scaled)) {
				return scaled;
			}
		}
		System.out.println("Warning: Failed to scale to whole numbers, requires multiple greater than 100");
		return array;
	}
	
	public int[] toIntArray(double[] array) {
		int [] result = new int[array.length];
		for (int i = 0 ; i < array.length; i++) {
			result[i] = (int)array[i];
		}
		return result;
	}
	
	public static void displayArray (int[] array) {
		for (int r = 0; r < array.length; r++) {
			if (r > 0) System.out.print(", ");
			System.out.print(array[r]);
		}
	}
	
}
