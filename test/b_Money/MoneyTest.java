package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
        assertEquals(10000, (int) SEK100.getAmount());
        assertEquals(1000, (int) EUR10.getAmount());
        assertEquals(0, (int) SEK0.getAmount());
	}

	@Test
	public void testGetCurrency() {
        assertEquals(SEK, SEK100.getCurrency());
        assertEquals(EUR, EUR10.getCurrency());
        assertEquals(SEK, SEK0.getCurrency());
	}

	@Test
	public void testToString() {
        assertEquals("100.00 SEK", SEK100.toString());
        assertEquals("10.00 EUR", EUR10.toString());
        assertEquals("0.00 SEK", SEK0.toString());
	}

	@Test
	public void testGlobalValue() {
        assertEquals(1500, (int) SEK100.universalValue());
        assertEquals(1500, (int) EUR10.universalValue());
        assertEquals(0, (int) SEK0.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		assertTrue(SEK100.equals(SEK100));
		assertTrue(EUR10.equals(EUR10));
		assertFalse(SEK100.equals(EUR10));
		assertFalse(SEK100.equals(SEK200));
	}

	@Test
	public void testAdd() {
		Money result = SEK100.add(EUR10);
		assertEquals(11500, (int) result.getAmount());
		assertEquals(SEK, result.getCurrency());
	
		result = EUR10.add(SEK100);
		assertEquals(2500, (int) result.getAmount());
		assertEquals(EUR, result.getCurrency());
	}

	@Test
	public void testSub() {
		Money result = SEK100.sub(EUR10);
		assertEquals(8500, (int) result.getAmount());
		assertEquals(SEK, result.getCurrency());
	
		result = EUR10.sub(SEK100);
		assertEquals(-500, (int) result.getAmount());
		assertEquals(EUR, result.getCurrency());
	}

	@Test
	public void testIsZero() {
        assertTrue(SEK0.isZero());
        assertTrue(EUR0.isZero());
        assertFalse(SEK100.isZero());
	}

	@Test
	public void testNegate() {
        Money negatedSEK100 = SEK100.negate();
        assertEquals(-10000, (int) negatedSEK100.getAmount());
        assertEquals(SEK, negatedSEK100.getCurrency());

        Money negatedEUR10 = EUR10.negate();
        assertEquals(-1000, (int) negatedEUR10.getAmount());
        assertEquals(EUR, negatedEUR10.getCurrency());
	}

	@Test
	public void testCompareTo() {
        assertTrue(SEK100.compareTo(SEK100) == 0);
        assertTrue(EUR10.compareTo(EUR10) == 0);
        assertTrue(SEK100.compareTo(EUR10) == 1);
        assertTrue(EUR10.compareTo(SEK200) == -1);
	}
}
