package com.fezda.chemistry;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PeriodicTableTest {

	@Test
	public void getBySymbolTest() {
		PeriodicTable table = new PeriodicTable();
		Element He = table.getBySymbol("He");
		assertEquals(He.getNumber(), 2);
		assertEquals(He.getSymbol(), "He");
		assertEquals(He.getName(), "Helium");
		assertEquals(He.getWeight(), 4.0026);
		assertEquals(He.getPeriod(), 1);
		assertEquals(He.getGroup(), 18);
	}
	
	@Test
	public void getBynameTest() {
		PeriodicTable table = new PeriodicTable();
		Element He = table.getByName("Helium");
		assertEquals(He.getNumber(), 2);
		assertEquals(He.getSymbol(), "He");
		assertEquals(He.getName(), "Helium");
		assertEquals(He.getWeight(), 4.0026);
		assertEquals(He.getPeriod(), 1);
		assertEquals(He.getGroup(), 18);
	}
}
