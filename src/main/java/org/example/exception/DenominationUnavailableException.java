package org.example.exception;


/**
 * The DenominationUnavailableException class represents an exception that is thrown when the required denominations
 * for a withdrawal request are unavailable in the ATM.
 */
public class DenominationUnavailableException extends  Exception{
    public DenominationUnavailableException(String message) {
        super(message);
    }
}
