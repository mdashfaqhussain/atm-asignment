package org.example.atm;

import lombok.extern.slf4j.Slf4j;
import org.example.constant.Constants;
import org.example.exception.AmountNegativeException;
import org.example.exception.DenominationUnavailableException;
import org.example.exception.InsufficientFundsException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;



/**
 * Represents an Automated Teller Machine (ATM) that manages the dispensing of money.
 * <p>
 * The ATM class provides functionality for withdrawing money from the ATM. It maintains the counts of different
 * denominations of currency available in the ATM and ensures thread-safe access to withdrawal operations.
 */
@Slf4j
public class ATM {


    private final ConcurrentHashMap<Denomination, Integer> atmCash;
    private final ReentrantLock lock;

    public ATM() {
        this.atmCash = new ConcurrentHashMap<>();
        initializeDenominations();
        this.lock = new ReentrantLock();
    }

    private void initializeDenominations() {
        this.atmCash.put(Denomination.HUNDRED, Constants.INITIAL_HUNDRED_NOTES);
        this.atmCash.put(Denomination.TWO_HUNDRED, Constants.INITIAL_TWO_HUNDRED_NOTES);
        this.atmCash.put(Denomination.FIVE_HUNDRED, Constants.INITIAL_FIVE_HUNDRED_NOTES);
    }

    /**
     * Executes the withdrawal process.
     *
     * @return
     * @throws InsufficientFundsException       If the ATM has insufficient funds.
     * @throws DenominationUnavailableException If a required denomination is not available.
     * @throws AmountNegativeException          If the withdrawal amount is negative.
     */
    public Map<Denomination, Integer> executeWithdrawal(int amount) throws InsufficientFundsException, DenominationUnavailableException, AmountNegativeException {
        lock.lock();
        try {
            log.info("Executing txn for amount: {}", amount);
            validateAmount(amount);
            Map<Denomination, Integer> dispensedNotes = dispenseNotes(amount);
            updateDenominations(dispensedNotes);
            handleRemainingAmount(dispensedNotes, amount);
            log.info(Constants.WITHDRAW_SUCCESS_MESSAGE);
            return dispensedNotes;
        } finally {
            lock.unlock();
        }

    }

    public ConcurrentHashMap<Denomination, Integer> getAtmCash() {
        return atmCash;
    }

    public int calculateTotalBalance() {
        return atmCash.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getValue() * entry.getValue())
                .sum();
    }


    /**
     * Validates that the withdrawal amount is positive.
     *
     * @throws AmountNegativeException If the withdrawal amount is not positive.
     */
    private void validateAmount(int amount) throws AmountNegativeException, InsufficientFundsException {
        if (amount <= 0) {
            throw new AmountNegativeException(Constants.AMOUNT_POSITIVE_MESSAGE);
        }
        int totalBalance = calculateTotalBalance();
        if (amount > totalBalance) {
            throw new InsufficientFundsException(Constants.INSUFFICIENT_FUNDS_MESSAGE);
        }
    }

    /**
     * Dispenses notes based on the withdrawal amount and available denominations.
     *
     * @return A map containing the dispensed notes.
     */
    private Map<Denomination, Integer> dispenseNotes(int amount) {
        Map<Denomination, Integer> dispensedNotes = new HashMap<>();
        List<Denomination> sortedDenominations = Arrays.stream(Denomination.values())
                .sorted(Comparator.comparingInt(Denomination::getValue).reversed())
                .toList();

        int remainingAmountToDispense = amount;
        for (Denomination denomination : sortedDenominations) {
            int quantity = atmCash.getOrDefault(denomination, 0);
            if (quantity <= 0 || denomination.getValue() > remainingAmountToDispense) {
                continue;
            }
            int notesToDispense = Math.min(remainingAmountToDispense / denomination.getValue(), quantity);
            dispensedNotes.put(denomination, notesToDispense);
            remainingAmountToDispense -= notesToDispense * denomination.getValue();
            if (remainingAmountToDispense == 0) {
                break;
            }
        }
        return dispensedNotes;
    }

    /**
     * Updates the ATM denominations after dispensing notes.
     *
     * @param dispensedNotes The dispensed notes to update denominations.
     */
    private void updateDenominations(Map<Denomination, Integer> dispensedNotes) {
        dispensedNotes.forEach((denomination, count) -> {
            int currentQuantity = atmCash.getOrDefault(denomination, 0);
            atmCash.put(denomination, currentQuantity - count);
        });
    }

    /**
     * Handles any remaining amount after dispensing notes.
     *
     * @param dispensedNotes The dispensed notes.
     * @throws DenominationUnavailableException If a required denomination is not available.
     */
    private void handleRemainingAmount(Map<Denomination, Integer> dispensedNotes, int amount) throws DenominationUnavailableException {
        int remainingAmountToDispense = amount - dispensedNotes.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getValue() * entry.getValue())
                .sum();
        if (remainingAmountToDispense > 0) {
            throw new DenominationUnavailableException(Constants.DENOMINATION_UNAVAILABLE_MESSAGE);
        }
    }
}

