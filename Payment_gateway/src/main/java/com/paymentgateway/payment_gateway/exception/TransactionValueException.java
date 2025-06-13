package com.paymentgateway.payment_gateway.exception;

public class TransactionValueException extends RuntimeException {
    public TransactionValueException(String message) {
        super(message);
    }
}
