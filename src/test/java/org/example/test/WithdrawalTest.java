package org.example.test;

import org.example.atm.ATM;
import org.example.atm.Denomination;
import org.example.atm.CashWithdrawal;
import org.example.exception.AmountNegativeException;
import org.example.exception.DenominationUnavailableException;
import org.example.exception.InsufficientFundsException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class WithdrawalTest {


    private static CountDownLatch getCountDownLatch(int numberOfThreads, int[] amounts, ATM atm) {
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < numberOfThreads; i++) {
            final int amountPerThread = amounts[i];
            Thread thread = new Thread(() -> {
                try {
                    CashWithdrawal withdrawal = new CashWithdrawal(amountPerThread, atm.getAtmCash());
                    withdrawal.executeWithdrawal();
                } catch (AmountNegativeException | DenominationUnavailableException | InsufficientFundsException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
            threads.add(thread);
            thread.start();
        }
        return latch;
    }

    @Test
    public void test_SuccessfulWithdrawal() throws InsufficientFundsException, DenominationUnavailableException, AmountNegativeException {
        ATM atm = new ATM();
        int amount = 700;
        CashWithdrawal withdrawal = new CashWithdrawal(amount, atm.getAtmCash());

        withdrawal.executeWithdrawal();
        assertEquals(2300, atm.calculateTotalBalance());


    }

    @Test
    public void test_UnavailableCombination() throws InsufficientFundsException, DenominationUnavailableException, AmountNegativeException {
        ATM atm = new ATM();
        int amount = 250;
        CashWithdrawal withdrawal = new CashWithdrawal(amount, atm.getAtmCash());
        assertThrows(DenominationUnavailableException.class, () -> withdrawal.executeWithdrawal());
    }

    @Test
    public void test_InsufficientFundsException() throws InsufficientFundsException, DenominationUnavailableException, AmountNegativeException {
        ATM atm = new ATM();

        ConcurrentHashMap<Denomination, Integer> denominations = atm.getAtmCash();
        denominations.put(Denomination.HUNDRED, 1);
        denominations.put(Denomination.TWO_HUNDRED, 0);
        denominations.put(Denomination.FIVE_HUNDRED, 0);

        int amount = 500;
        CashWithdrawal withdrawal = new CashWithdrawal(amount, denominations);
        assertThrows(InsufficientFundsException.class, () -> withdrawal.executeWithdrawal());
    }

    @Test
    public void test_ConcurrentWithdrawals() throws InterruptedException {
        final int numberOfThreads = 3;
        final int[] amounts = {500, 700, 300};

        ATM atm = new ATM();
        CountDownLatch latch = getCountDownLatch(numberOfThreads, amounts, atm);
        latch.await(); // Wait for all threads to finish
        int remainingTotalBalance = atm.calculateTotalBalance();
        // Assert the final state of the ATM after concurrent withdrawals
        ConcurrentHashMap<Denomination, Integer> denominations = atm.getAtmCash();
        assertEquals(0, denominations.get(Denomination.FIVE_HUNDRED));
        assertEquals(9, denominations.get(Denomination.HUNDRED));
        assertEquals(3, denominations.get(Denomination.TWO_HUNDRED));
        assertEquals(1500, remainingTotalBalance);

    }


}







