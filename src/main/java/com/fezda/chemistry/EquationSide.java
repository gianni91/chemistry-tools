package com.fezda.chemistry;

public class EquationSide {
	
	public static void main (String[] args) {
		String eq = "3H2O + 2CO2";
		EquationSide side = new EquationSide(eq);
		side.display();
	}
	
	private Molecule[] molecules;
	private Integer[] quantities;
	
	public EquationSide (String contents) {
		String trimmed = contents.replace(" ","");
		String[] parts = trimmed.split("\\+");
		molecules = new Molecule[parts.length];
		quantities = new Integer[parts.length];
		
		for (int i = 0; i < parts.length; i++) {
			if ( Character.isDigit(parts[i].charAt(0)) ) {
				int splitIndex = 0;
				for (int c = 0; c < parts[i].length(); c++) {
					if (!Character.isDigit(parts[i].charAt(c))) {
						splitIndex = c;
						break;
					}
				}
				molecules[i] = new Molecule(parts[i].substring(splitIndex));
				quantities[i] = Integer.parseInt(parts[i].substring(0,splitIndex));
			} else {
				molecules[i] = new Molecule(parts[i]);
				quantities[i] = 1;
			}
		}
	}
	
	public void display() {
		for (int i = 0; i < molecules.length; i++) {
			System.out.print(quantities[i] == 1 ? "" : quantities[i]);
			molecules[i].display();
			System.out.println();
		}
	}
}
