package org.example.atm;

import org.example.constant.ProjectConstants;
import org.example.exception.AmountNegativeException;
import org.example.exception.DenominationUnavailableException;
import org.example.exception.InsufficientFundsException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;



/**
 * Represents an Automated Teller Machine (ATM) that manages the dispensing of money.
 * <p>
 * The ATM class provides functionality for withdrawing money from the ATM. It maintains the counts of different
 * denominations of currency available in the ATM and ensures thread-safe access to withdrawal operations.
 */
public class ATM {


    private final ConcurrentHashMap<Denomination, Integer> atmCash;
    private final ReentrantLock lock;

    public ATM() {
        this.atmCash = new ConcurrentHashMap<>();
        initializeDenominations();
        this.lock = new ReentrantLock();
    }

    private void initializeDenominations() {
        this.atmCash.put(Denomination.HUNDRED, ProjectConstants.INITIAL_HUNDRED_NOTES);
        this.atmCash.put(Denomination.TWO_HUNDRED, ProjectConstants.INITIAL_TWO_HUNDRED_NOTES);
        this.atmCash.put(Denomination.FIVE_HUNDRED, ProjectConstants.INITIAL_FIVE_HUNDRED_NOTES);
    }


    public ConcurrentHashMap<Denomination, Integer> getAtmCash() {
        return atmCash;
    }



    /**
     * Withdraws the specified amount from the ATM.
     *
     * @param amount the amount to withdraw from the ATM
     *
     */
    public void withdraw(int amount) throws AmountNegativeException, DenominationUnavailableException, InsufficientFundsException {
        lock.lock();
        try {
            CashWithdrawal withdrawal = new CashWithdrawal(amount, atmCash);
            withdrawal.executeWithdrawal();
        } finally {
            lock.unlock();
        }
    }

    public int calculateTotalBalance() {
        return atmCash.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getValue() * entry.getValue())
                .sum();
    }
}
