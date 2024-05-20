package org.example.exception;


/**
 * The AmountNegativeException class represents an exception that is thrown when a negative amount
 * is encountered in a context where only positive values are valid.
 */
public class AmountNegativeException extends  Exception{
    public AmountNegativeException(String message) {
        super(message);
    }
}
