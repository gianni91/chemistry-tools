package com.fezda.chemistry;

import java.util.ArrayList;

public class Molecule {
	
	public static void main (String[] args) {
		Molecule m = new Molecule("H2O");
		m.display();
	}
	
	private String [] elements;
	private Integer[] quantities;
	
	public Molecule (String formula) {
		ArrayList<String> parts = new ArrayList<String>();

		int startIndex = 0;
		int endIndex = 0;
		char currentChar;
		for (int i = 1; i < formula.length(); i++) {
			currentChar = formula.charAt(i);
			if (Character.isUpperCase(currentChar)) {
				endIndex = i;
				parts.add(formula.substring(startIndex,endIndex));
				startIndex = i;
			}
		}
		parts.add(formula.substring(startIndex,formula.length()));
		elements = new String[parts.size()];
		quantities = new Integer[parts.size()];
		
		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i).length() == 1) {
				elements[i] = parts.get(i);
				quantities[i] = 1;
			}
			else {
				endIndex = 0;
				for (int c = 0; c < parts.get(i).length(); c++) {
					currentChar = (parts.get(i)).charAt(c);
					if (Character.isDigit(currentChar)) {
						endIndex = c;
						break;
					}
				}
				elements[i] = parts.get(i).substring(0, endIndex);
				quantities[i] = Integer.parseInt( parts.get(i).substring(endIndex) );
			}
		}
	}
	
	public void display () {
		for (int i = 0; i < elements.length; i++) {
			System.out.print(elements[i] + (quantities[i] == 1 ? "" : quantities[i]));
		}
	}
}