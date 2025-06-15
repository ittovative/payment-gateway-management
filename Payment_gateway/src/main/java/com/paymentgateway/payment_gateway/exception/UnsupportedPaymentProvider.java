package com.paymentgateway.payment_gateway.exception;

/**
 * This exception is thrown when a payment provider is not supported.
 * <p>
 * For example, if the system only supports ADYEN, STRIPE, and PAYPAL,
 * but receives a request for a different provider, this exception is used.
 * </p>
 */
public class UnsupportedPaymentProvider extends RuntimeException {
    public UnsupportedPaymentProvider(String message) {
        super(message);
    }
}
