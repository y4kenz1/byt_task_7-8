package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
        testAccount.addTimedPayment("payment1", 3, 3, new Money(500, SEK), SweBank, "Alice");
        assertTrue(testAccount.timedPaymentExists("payment1"));

        testAccount.removeTimedPayment("payment1");
        assertFalse(testAccount.timedPaymentExists("payment1"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
        // Add a timed payment
        testAccount.addTimedPayment("payment1", 3, 3, new Money(500, SEK), SweBank, "Alice");

        // Perform two ticks to trigger the timed payment
        testAccount.tick();
        testAccount.tick();

        // Check if the timed payment was successful
        assertEquals(new Money(9500000, SEK), testAccount.getBalance());
        assertEquals(new Money(1000500, SEK), SweBank.getBalance("Alice"));
	}

	@Test
	public void testAddWithdraw() {
        // Deposit money
        testAccount.deposit(new Money(5000, SEK));
        assertEquals(new Money(10005000, SEK), testAccount.getBalance());

        // Withdraw money
        testAccount.withdraw(new Money(2000, SEK));
        assertEquals(new Money(10003000, SEK), testAccount.getBalance());
	}
	
	@Test
	public void testGetBalance() {
        // Check initial balance
        assertEquals(new Money(10000000, SEK), testAccount.getBalance());

        // Deposit and withdraw money
        testAccount.deposit(new Money(5000, SEK));
        testAccount.withdraw(new Money(2000, SEK));

        // Check updated balance
        assertEquals(new Money(10003000, SEK), testAccount.getBalance());
	}
}
