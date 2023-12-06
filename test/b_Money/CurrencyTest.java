package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
        assertEquals("SEK", SEK.getName());
        assertEquals("DKK", DKK.getName());
        assertEquals("EUR", EUR.getName());
	}
	
	@Test
	public void testGetRate() {
        assertEquals(0.15, SEK.getRate(), 0.001);
        assertEquals(0.20, DKK.getRate(), 0.001);
        assertEquals(1.5, EUR.getRate(), 0.001);
	}
	
	@Test
	public void testSetRate() {
        SEK.setRate(0.25);
        assertEquals(0.25, SEK.getRate(), 0.001);
	}
	
	@Test
    public void testUniversalValue() {
        assertEquals(300.0, (double) SEK.universalValue(2000), 0.001);
        assertEquals(400.0, (double) DKK.universalValue(2000), 0.001);
        assertEquals(3000.0, (double) EUR.universalValue(2000), 0.001);
    }

	@Test
	public void testValueInThisCurrency() {
		assertEquals(30000.0, (double) SEK.valueInThisCurrency(3000, EUR), 0.001);
		assertEquals(30000.0, (double) DKK.valueInThisCurrency(4000, EUR), 0.001);
		assertEquals(300.0, (double) EUR.valueInThisCurrency(3000, SEK), 0.001);
	}
}