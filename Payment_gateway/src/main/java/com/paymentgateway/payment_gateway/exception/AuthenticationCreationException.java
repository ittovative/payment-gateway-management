package com.paymentgateway.payment_gateway.exception;

/**
 * This exception is thrown when there is a problem while creating or setting up
 * authentication details,session, or user identity.
 */
public class AuthenticationCreationException extends RuntimeException {
    public AuthenticationCreationException(String message) {
        super(message);
    }
}
