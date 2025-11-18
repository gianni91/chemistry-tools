package com.fezda.chemistry;

enum Type {TEMP,VOL,PRES,AMT,NONE}

public class Quantity {	// Physical Quantity
	private double amount;
	private String unit;
	private Type type;
	
	public Quantity(String amountAndUnits) {
		amountAndUnits = amountAndUnits.trim();
		int splitIndex = 0;
		char currentChar;
		for (int i = 0; i < amountAndUnits.length(); i++) {
			currentChar = amountAndUnits.charAt(i);
			if (Character.isAlphabetic(currentChar)) {
				splitIndex = i;
				break;
			}
		}
		amount = Double.parseDouble(amountAndUnits.substring(0,splitIndex));
		unit = amountAndUnits.substring(splitIndex);
		type = getType(unit);
	}
	
	public Quantity(double amount, String unit) {
		this.amount = amount;
		this.unit = unit;
		this.type = getType(unit);
	}
	
	public void display () {
		System.out.println("" + amount + unit);
	}
	public void display (int decimalPlaces) {
		System.out.printf("%."+decimalPlaces+"f"+ unit + "\n", amount);
	}
	
	public Type getType (String unit) {
		// TODO - Add more units
		switch (unit) {
			case "K":
			case "C":
			case "F":
				return Type.TEMP;
			case "L":
			case "mm^3":
			case "in^3":
				return Type.VOL;
			case "atm":
			case "Pa":
			case "mmHg":
				return Type.PRES;
			case "g":
			case "mol":
				return Type.AMT;
			default:
				System.out.println("Error: unrecognized unit '" + unit + "'");
				return Type.NONE;
		}
	}
	
	public double getStandard () {
		switch (unit) {
			// Temperature
			case "K": return amount;
			case "F": return (amount - 32) * 5/9 + 273.15;
			case "C": return amount + 273.15;
			// Volume
			case "L": return amount;
			case "mm^3": return amount/1000000;
			case "in^3": return amount*0.0163871;
			// Pressure
			case "atm": return amount;
			case "Pa": return amount * 0.00000986923;
			case "mmHg": return amount/760;
			// TODO - more units
		}
		System.out.println("Error: type not recognized, failed to get standard value");
		return 0;
	}
	
	public void to (String toUnit) {
		Type toType = getType(toUnit);
		if (type != toType) {
			System.out.println("Error: types incompatible for conversion " + type + " " + toType);
			return;
		}
		
		switch (toUnit) {
			// Temperature
			case "K":
				amount = getStandard();
				break;
			case "F":
				amount = (getStandard() - 273.15) * 9/5 + 32;
				break;
			case "C":
				amount = getStandard() - 273.15;
				break;
				
			// Volume
			case "L":
				amount = getStandard();
				break;
			case "mm^3":
				amount = getStandard() * 1000000;
				break;
			case "in^3":
				amount = getStandard() / 0.0163871;
				break;
				
			// Pressure
			case "atm":
				amount = getStandard();
				break;
			case "Pa":
				amount = getStandard() / 0.00000986923;
				break;
			case "mmHg":
				amount = getStandard() * 760;
				break;
			// TODO - more units
		}
		unit = toUnit;
	}
}
