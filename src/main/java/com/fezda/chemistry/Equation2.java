package com.fezda.chemistry;

import java.util.HashMap;
import java.util.HashSet;

import com.fezda.math.SystemOfEquations;

/***************
 * Alternative version of Equation that stores reactants and products separately
 **************/
public class Equation2 {
	
	public static void main (String[] args) {
		String eq = "C8H18 + O2 -> H2O + CO2";
		Equation2 equation = new Equation2(eq);
		equation.display();
		equation.balance();
		equation.display();
		equation.display(false);
	}
	
	private Molecule[] reactants;
	private Molecule[] products;
	private int[] coefficients;		// Stoichiometric coefficients when balanced
	
	public Equation2 (String equation) {
		String trimmed = equation.replace(" ", "");
		String[] sides = trimmed.split("->");
		
		int[][] sideCoefficients = new int[2][];
		Molecule[][] molecules = new Molecule[2][];
		
		boolean hasCoeffs = false;
		for (int side = 0; side < 2; side++) {
			String[] parts = sides[side].split("\\+");
			molecules[side] = new Molecule[parts.length];
			sideCoefficients[side] = new int[parts.length];
			
			for (int i = 0; i < parts.length; i++) {
				if ( Character.isDigit(parts[i].charAt(0)) ) {
					hasCoeffs = true;
					int splitIndex = 0;
					for (int c = 0; c < parts[i].length(); c++) {
						if (!Character.isDigit(parts[i].charAt(c))) {
							splitIndex = c;
							break;
						}
					}
					molecules[side][i] = new Molecule(parts[i].substring(splitIndex));
					sideCoefficients[side][i] = Integer.parseInt(parts[i].substring(0,splitIndex));
				} else {
					molecules[side][i] = new Molecule(parts[i]);
					sideCoefficients[side][i] = 1;
				}
			}
		}
		reactants = molecules[0];
		products = molecules[1];
		
		if (hasCoeffs) {
			coefficients = new int[sideCoefficients[0].length + sideCoefficients[1].length];
			for (int i = 0; i < sideCoefficients[0].length; i++) {
				coefficients[i] = sideCoefficients[0][i];
			}
			for (int i = 0; i < sideCoefficients[1].length; i++) {
				coefficients[i+sideCoefficients[0].length] = sideCoefficients[1][i];
			}
		}
	}
	
	public String[] getElements (Molecule[] molecules) {
		HashSet<String> elements = new HashSet<String>();
		for (int m = 0; m < molecules.length; m++) {
			String [] els = molecules[m].getElements();
			for (int el = 0; el < els.length; el++) {
				elements.add(els[el]);
			}
		}
		String[] result = new String [elements.size()];
		return elements.toArray(result);
	}
	
	public void display () {
		display(true);
	}
	public void display (boolean includeCoeffs) {
		for (int i = 0; i < reactants.length; i++) {
			if (i!=0) System.out.print(" + ");
			if (coefficients != null && includeCoeffs) 
				System.out.print(coefficients[i] == 1 ? "" : coefficients[i]);
			reactants[i].display();
		}
		System.out.print(" -> ");
		for (int i = 0; i < products.length; i++) {
			if (i!=0) System.out.print(" + ");
			if (coefficients != null &&includeCoeffs) 
				System.out.print(coefficients[i+reactants.length] == 1 ? "" : coefficients[i+reactants.length]);
			products[i].display();
		}
		System.out.println();
	}
	
	/**************
	 * Use Bottomley's Method to balance a chemical equation
	 **************/
	public void balance() {
		String [] elements = this.getElements(reactants);
		int rhsStart = reactants.length;
		double[][] sysOfEqMatrix = new double[elements.length + 1][reactants.length+products.length+ 1];
		for (int mol = 0; mol < reactants.length; mol++) {
			HashMap<String,Integer> molElCounts = reactants[mol].getElementCounts();
			for (int el = 0; el < elements.length; el++) {
				if (molElCounts.containsKey(elements[el])) {
					sysOfEqMatrix[el][mol] = molElCounts.get(elements[el]);
				}
				else {
					sysOfEqMatrix[el][mol] = 0;
				}
			}
		}
		for (int mol2 = 0; mol2 < products.length; mol2++) {
			HashMap<String,Integer> molElCounts = products[mol2].getElementCounts();
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
		
		double[] dCoefficients  = SystemOfEquations.solveSimpleSystemOfEquations(sysOfEqMatrix);
		if (hasFractions(dCoefficients)) dCoefficients = getScaledToWholeNums(dCoefficients);
		this.coefficients = toIntArray(dCoefficients);
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
