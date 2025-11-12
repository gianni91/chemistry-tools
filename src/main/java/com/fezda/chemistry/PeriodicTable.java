package com.fezda.chemistry;

import java.util.HashMap;

public class PeriodicTable {
	private HashMap<String,Element> elsBySymbol;
	private HashMap<String,Element> elsByName;
	
	public PeriodicTable () {
		elsBySymbol = new HashMap<String,Element>();
		elsByName = new HashMap<String,Element>();
		add("Hydrogen","H",1,1.008,1,1);				// weight is in g/mol
		add("Helium","He",2,4.0026,18,1);
		
		add("Lithium","Li",3,6.94,1,2);
		add("Beryllium","Be",4,9.0122,2,2);
		add("Boron","B",5,10.81,13,2);
		add("Carbon","C",6,12.011,14,2);
		add("Nitrogen","N",7,14.007,15,2);
		add("Oxygen","O",8,15.999,16,2);
		add("Fluorine","F",9,18.998,17,2);
		add("Neon","Ne",10,20.180,18,2);
		
		add("Sodium","Na",11,22.99,1,3);
		add("Magnesium","Mg",12,24.305,2,3);
		add("Aluminum","Al",13,26.982,13,23);
		add("Silicon","Si",14,28.085,14,23);
		add("Phosphorus","P",15,30.974,15,3);
		add("Sulfer","S",16,32.06,16,3);
		add("Chlorine","Cl",17,35.45,17,3);
		add("Argon","Ar",18,39.95,18,3);
		
		//...
		add("Iron","Fe",26,55.845,8,4);
		// TODO - Finish
	}
	
	private void add (String name, String symbol, int num, double weight, int group, int period) {
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
