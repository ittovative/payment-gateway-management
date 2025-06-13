package com.paymentgateway.payment_gateway.exception;

public class UnsupportPaymentProvider extends RuntimeException {
    public UnsupportPaymentProvider(String message) {
        super(message);
    }
}
