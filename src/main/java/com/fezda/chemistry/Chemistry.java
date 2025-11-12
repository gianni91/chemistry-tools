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
}
