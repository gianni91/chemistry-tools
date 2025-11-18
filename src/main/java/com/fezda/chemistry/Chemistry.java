package com.fezda.chemistry;

public class Chemistry {
	
	public static void main (String[] args) {
		Chemistry chem = new Chemistry();
		
		Molecule water = new Molecule("H2O");
		double waterMass = chem.getMolarMass(water);
		System.out.println(waterMass);
		
		// Vinegar + Baking Soda
		String eq = "CH3COOH + NaHCO3 -> CH3COONa + H2O + CO2";
		Equation equation = new Equation(eq);
		equation.balance();
		equation.display();
		
		Molecule lr = chem.getLimitingReagent(new double[] {13,22}, equation);
		lr.display();
		System.out.println();
		
		double[] masses = chem.getProductMasses(new double[] {13,22},equation);
		for (int i = 0; i < masses.length; i++) {
			System.out.println(masses[i]);
		}
		
		double pressure = 1;			// Atmospheres
		double volume = 5;				// Liters
		double amount = 0.2;			// Moles
		double temperature = chem.getTemperature(pressure, volume, amount);
		System.out.println("Temperature = " + K2F(temperature));
		
		double temp = 100;
		System.out.println(K2F(temp));
		System.out.println(K2C(temp));
		
		System.out.println(toK(temp,"F"));
		
		Quantity tempQ = new Quantity("0C");
		tempQ.to("F");
		tempQ.display(3);
		tempQ.to("K");
		tempQ.display(3);
		tempQ.to("C");
		tempQ.display(3);
		
		Quantity presQ = new Quantity("10mmHg");
		presQ.to("mmHg");
		presQ.display();
		presQ.to("atm");
		presQ.display();
		presQ.to("Pa");
		presQ.display();
		
		Quantity volQ = new Quantity("10in^3");
		volQ.to("L");
		volQ.display();
		volQ.to("mm^3");
		volQ.display();
		volQ.to("in^3");
		volQ.display();
	}
	
	private PeriodicTable pTable;
	
	public Chemistry () {
		pTable = new PeriodicTable();
	}
	
	public double getMolarMass (Molecule mol) {
		double mass = 0;
		for (int i = 0; i < mol.getElements().length; i++) {
			mass += pTable.getBySymbol(mol.getElements()[i]).getWeight() * mol.getQuantities()[i];
		}
		return mass;
	}
	
	public double getMolesByMass (double mass, Molecule mol) {
		return mass/getMolarMass(mol);
	}
	
	public Molecule getLimitingReagent(double[] reactantMasses, Equation eq) {
		int lrIndex = getLimitingReagentIndex(reactantMasses, eq);
		return eq.getMolecules()[lrIndex];
	}
	
	public int getLimitingReagentIndex(double[] reactantMasses, Equation eq) {
		int lr = 0;
		double minOutput = 0;
		for (int r = 0; r < reactantMasses.length; r++) {
			double output = getMolesByMass(reactantMasses[r], eq.getMolecules()[r]) / eq.getCoefficients()[r];
			if (r == 0 || output < minOutput) {
				lr = r;
				minOutput = output;
			}
		}
		return lr;
	}
	
	public double[] getProductMasses (double[] reactantMasses, Equation eq) {
		if (reactantMasses.length != eq.getNumReactants()) throw new IllegalArgumentException("Number of reactants is masses list must match number in equation");
		int lrIndex = getLimitingReagentIndex(reactantMasses, eq);
		
		double outputNum = getMolesByMass(reactantMasses[lrIndex], eq.getMolecules()[lrIndex]) / eq.getCoefficients()[lrIndex];
		double[] result = new double[eq.getNumProducts()];
		for (int p = 0; p < result.length; p++) {
			result[p] = getMolarMass(eq.getMolecules()[eq.getNumReactants()+p]) * eq.getCoefficients()[eq.getNumReactants()+p] * outputNum;
		}
		return result;
	}
	
	public double getPressure (double volumeL, double amountMol, double temperatureK) {
		return amountMol * 0.08206 * temperatureK / volumeL;
	}
	
	public double getVolume (double pressureAtm, double amountMol, double temperatureK) {
		return amountMol * 0.08206 * temperatureK / pressureAtm;
	}
	
	public double getNumMoles (double pressureAtm, double volume, double temperatureK) {
		return pressureAtm * volume / (0.08206 * temperatureK) ;
	}
	
	public double getTemperature (double pressureAtm, double volume, double amountMol) {
		return pressureAtm * volume / (0.08206 * amountMol);
	}
	
	public static double C2F(double temp) {
		return temp * 9/5 + 32;
	}
	public static double F2C(double temp) {
		return (temp - 32) * 5/9;
	}
	public static double C2K(double temp) {
		return temp + 273.15;
	}
	public static double K2C(double temp) {
		return temp - 273.15;
	}
	public static double F2K(double temp) {
		return C2K(F2C(temp));
	}
	public static double K2F(double temp) {
		return C2F(K2C(temp));
	}
	
	public static double toK(double temp, String unit) {
		if (unit.equals("F")) {
			temp = (temp - 32) * 5/9;
			unit = "C";
		}
		if (unit.equals("C")) {
			temp += 273.15;
			unit = "K";
		}
		return temp;
	}
	
}
