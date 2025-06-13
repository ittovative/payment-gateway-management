package com.paymentgateway.payment_gateway.exception;

public class WebhookException extends RuntimeException {
    public WebhookException(String message) {
        super(message);
    }
}
