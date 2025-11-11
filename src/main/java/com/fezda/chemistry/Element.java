package com.fezda.chemistry;

public class Element {
	private String name;
	private String symbol;
	private int number;
	private float weight;
	private int group;
	private int period;
	
	public Element (String name, String symbol, int number, float weight, int group, int period) {
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

	public float getWeight() {
		return weight;
	}
	
	public int getGroup() {
		return group;
	}

	public float getPeriod() {
		return period;
	}

}
