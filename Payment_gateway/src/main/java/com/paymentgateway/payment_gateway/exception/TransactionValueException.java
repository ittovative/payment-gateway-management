package com.paymentgateway.payment_gateway.exception;


/**
 * This exception is thrown when there is an invalid or unexpected transaction value.
 * <p>
 * For example, this could happen if the transaction amount is negative,
 * zero, or exceeds allowed limits.
 * </p>
 */
public class TransactionValueException extends RuntimeException {
    public TransactionValueException(String message) {
        super(message);
    }
}
