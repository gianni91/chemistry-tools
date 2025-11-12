package com.fezda.chemistry;

public class Element {
	private String name;		// Full name
	private String symbol;		// Two-letter representation
	private int number;			// Atomic number
	private double weight;		// Atomic weight (g/mol)
	private int group;			// Column number in periodic table
	private int period;			// Row num in periodic table
	
	public Element (String name, String symbol, int number, double weight, int group, int period) {
		this.name = name;
		this.symbol = symbol;
		this.number = number;
		this.weight = weight;
		this.group = group;
		this.period = period;
	}

	public String getName() {
		return name;
	}

	public String getSymbol() {
		return symbol;
	}

	public int getNumber() {
		return number;
	}

	public double getWeight() {
		return weight;
	}
	
	public int getGroup() {
		return group;
	}

	public int getPeriod() {
		return period;
	}

}
