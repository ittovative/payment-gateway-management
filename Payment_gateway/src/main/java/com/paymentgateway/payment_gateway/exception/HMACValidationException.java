package com.paymentgateway.payment_gateway.exception;
/**
        * This exception is thrown when HMAC validation fails.
        * <p>
 * HMAC (Hash-based Message Authentication Code) is used to verify
 * that data has not been tampered with during communication.
 * If the expected HMAC and the received one do not match, this exception is triggered.
 */
public class HMACValidationException extends RuntimeException {
    public HMACValidationException(String message) {
        super(message);
    }
}
