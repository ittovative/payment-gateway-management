package com.paymentgateway.payment_gateway.exception;


/**
 * This exception is thrown when an error occurs during webhook processing.
 * <p>
 * Webhooks are callbacks from external services to notify your system of events,
 * such as payment updates. If processing these notifications fails,
 * this exception helps to handle those errors.
 * </p>
 */
public class WebhookException extends RuntimeException {
    public WebhookException(String message) {
        super(message);
    }
}
