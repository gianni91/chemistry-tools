package com.fezda.chemistry;

import java.util.HashMap;
import java.util.HashSet;

import com.fezda.math.SystemOfEquations;

public class Equation {
	
	public static void main (String[] args) {
		String eq = "C8H18 + O2 -> H2O + CO2";
		Equation equation = new Equation(eq);
		equation.display();
		equation.balance();
		equation.display();
		equation.display(false);
	}
	
	private Molecule[] molecules;
	private int numReactants;
	private int numProducts;
	private int[] coefficients;		// Stoichiometric coefficients when balanced
	
	public Equation (String equation) {
		String trimmed = equation.replace(" ", "");
		String[] sides = trimmed.split("->");
		String[][] parts = new String[2][];
		parts[0] = sides[0].split("\\+");
		parts[1] = sides[1].split("\\+");
		
		molecules = new Molecule[parts[0].length + parts[1].length];
		coefficients = new int[parts[0].length + parts[1].length];
		numReactants = parts[0].length;
		numProducts = parts[1].length;
		
		boolean hasCoeffs = false;
		for (int side = 0; side < 2; side++) {
//			if (side == 0) numReactants = parts.length;
			
			for (int i = 0; i < parts[side].length; i++) {
				if ( Character.isDigit(parts[side][i].charAt(0)) ) {
					hasCoeffs = true;
					int splitIndex = 0;
					for (int c = 0; c < parts[side][i].length(); c++) {
						if (!Character.isDigit(parts[side][i].charAt(c))) {
							splitIndex = c;
							break;
						}
					}
					molecules[i+side*numReactants] = new Molecule(parts[side][i].substring(splitIndex));
					coefficients[i+side*numReactants] = Integer.parseInt(parts[side][i].substring(0,splitIndex));
				} else {
					molecules[i+side*numReactants] = new Molecule(parts[side][i]);
					coefficients[i+side*numReactants] = 1;
				}
			}
		}
	}
	
	public static String[] getElements (Molecule[] molecules) {
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
		for (int i = 0; i < molecules.length; i++) {
			if (i!=0) {
				if (i == numReactants) 
					System.out.print(" -> ");
				else 
					System.out.print(" + ");
			}
			if (coefficients != null && includeCoeffs) 
				System.out.print(coefficients[i] == 1 ? "" : coefficients[i]);
			molecules[i].display();
		}
		System.out.println();
	}
	
	/**************
	 * Use Bottomley's Method to balance a chemical equation
	 **************/
	public void balance() {
		String [] elements = this.getElements(molecules);
		double[][] sysOfEqMatrix = new double[elements.length + 1][molecules.length+ 1];
		for (int mol = 0; mol < molecules.length; mol++) {
			HashMap<String,Integer> molElCounts = molecules[mol].getElementCounts();
			for (int el = 0; el < elements.length; el++) {
				if (molElCounts.containsKey(elements[el])) {
					sysOfEqMatrix[el][mol] = molElCounts.get(elements[el]);
					if (mol >= numReactants)
						sysOfEqMatrix[el][mol] = sysOfEqMatrix[el][mol] * -1;		// Note: This makes products negative so that a + b = c + d  becomes a + b - c - d = 0
				}
				else
					sysOfEqMatrix[el][mol] = 0;
			}
		}
		
		// Set a value of 1 for one of the variables, in order to have enough equations to solve for all variables
		sysOfEqMatrix[elements.length][0] = 1;
		sysOfEqMatrix[elements.length][sysOfEqMatrix.length] = 1;
		
		//displayMatrix(sysOfEqMatrix, elements);
		
		double[] dCoefficients  = SystemOfEquations.solveSimpleSystemOfEquations(sysOfEqMatrix);
		if (hasFractions(dCoefficients))
			dCoefficients = getScaledToWholeNums(dCoefficients);
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
	
	public int getNumReactants () {
		return numReactants;
	}
	
	public int getNumProducts () {
		return numProducts;
	}
	
	public Molecule[] getMolecules () {
		return molecules;
	}
	
	public int[] getCoefficients () {
		if (coefficients == null)
			balance();
		return coefficients;
	}
}
