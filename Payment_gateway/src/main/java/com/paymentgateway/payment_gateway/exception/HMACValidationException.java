package com.paymentgateway.payment_gateway.exception;

public class HMACValidationException extends RuntimeException {
    public HMACValidationException(String message) {
        super(message);
    }
}
