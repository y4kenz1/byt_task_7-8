package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
	        DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
        assertEquals("SweBank", SweBank.getName());
        assertEquals("Nordea", Nordea.getName());
        assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
        assertEquals(SEK, SweBank.getCurrency());
        assertEquals(SEK, Nordea.getCurrency());
        assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
        SweBank.openAccount("John");
        assertTrue(SweBank.getBalance("John") == 0);

        // Test for AccountExistsException
        try {
            SweBank.openAccount("Ulrika");
            fail("Expected AccountExistsException");
        } catch (AccountExistsException e) {
        }
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(5000, SEK));
        assertEquals(5000, SweBank.getBalance("Ulrika").intValue());

        // Test for AccountDoesNotExistException
        try {
            SweBank.deposit("NonExistentAccount", new Money(1000, SEK));
            fail("Expected AccountDoesNotExistException");
        } catch (AccountDoesNotExistException e) {
        }
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
        SweBank.deposit("Bob", new Money(11000, SEK));
        SweBank.withdraw("Bob", new Money(3300, SEK));
        assertEquals(7700, SweBank.getBalance("Bob").intValue());

        // Test for AccountDoesNotExistException
        try {
            SweBank.withdraw("NonExistentAccount", new Money(100, SEK));
            fail("Expected AccountDoesNotExistException");
        } catch (AccountDoesNotExistException e) {
        }
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
        assertEquals(0, DanskeBank.getBalance("Gertrud").intValue());

        DanskeBank.deposit("Gertrud", new Money(5000, DKK));
        assertEquals(5000, DanskeBank.getBalance("Gertrud").intValue());

        // Test for AccountDoesNotExistException
        try {
            DanskeBank.getBalance("NonExistentAccount");
            fail("Expected AccountDoesNotExistException");
        } catch (AccountDoesNotExistException e) {
            // Expected exception
        }
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(15000, SEK));
        Nordea.deposit("Bob", new Money(5500, SEK));

        SweBank.transfer("Ulrika", Nordea, "Bob", new Money(3000, SEK));

        assertEquals(12000, SweBank.getBalance("Ulrika").intValue());
        assertEquals(8500, Nordea.getBalance("Bob").intValue());

        // Test for AccountDoesNotExistException
        try {
            SweBank.transfer("NonExistentAccount", Nordea, "Bob", new Money(1200, SEK));
            fail("Expected AccountDoesNotExistException");
        } catch (AccountDoesNotExistException e) {
        }
	}
	
	@Test
	public void testTimedPayment() throws AccountExistsException, AccountDoesNotExistException {
        SweBank.openAccount("Sue");
        SweBank.deposit("Sue", new Money(1000, SEK));

        SweBank.addTimedPayment("Sue", "payment1", 1, 1, new Money(200, SEK), Nordea, "Bob");

        // Perform two ticks to trigger the timed payment
        SweBank.tick();
        SweBank.tick();

        assertEquals(800, SweBank.getBalance("Sue").intValue());
        assertEquals(200, Nordea.getBalance("Bob").intValue());
	}
}
