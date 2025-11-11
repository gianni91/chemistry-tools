package com.fezda.chemistry;

import java.util.HashMap;

public class PeriodicTable {
	private HashMap<String,Element> elsBySymbol;
	private HashMap<String,Element> elsByName;
	
	public PeriodicTable () {
		elsBySymbol = new HashMap<String,Element>();
		elsByName = new HashMap<String,Element>();
		add("Hydrogen","H",1,1.008f,1,1);
		add("Helium","He",2,4.0026f,18,1);
		add("Lithium","Li",3,6.94f,18,1);
		add("Beryllium","Be",4,9.0122f,18,1);
		add("Boron","B",5,10.81f,18,1);
		add("Carbon","C",6,12.011f,18,1);
		add("Nitrogen","N",7,14.007f,18,1);
		add("Oxygen","O",8,15.999f,18,1);
		add("Fluorine","F",9,18.998f,18,1);
		add("Neon","Ne",10,20.180f,18,1);
		// TODO - Finish
	}
	
	private void add (String name, String symbol, int num, float weight, int group, int period) {
		Element el = new Element (name, symbol, num, weight, group, period);
		elsBySymbol.put(symbol,el);
		elsByName.put(name,el);
	}
	
	public Element getBySymbol (String symbol) {
		return elsBySymbol.get(symbol);
	}
	
	public Element getByName (String name) {
		return elsByName.get(name);
	}
}
