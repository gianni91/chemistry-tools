package com.fezda.chemistry;

public class Equation {
	
	public static void main (String[] args) {
		String eq = "3C8H18 + O2 -> H2O + CO2";
		Equation equation = new Equation(eq);
		equation.display();
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
		productSide.display();
	}
}
