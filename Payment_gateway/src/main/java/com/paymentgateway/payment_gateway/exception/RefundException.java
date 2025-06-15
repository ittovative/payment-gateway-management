package com.paymentgateway.payment_gateway.exception;

/**
 * This exception is thrown when a refund process fails.
 * <p>
 * Refund means returning money to the customer after a payment.
 * If the refund fails due to any reason (e.g., invalid transaction ID, provider error),
 * this exception is used to handle that failure.
 * </p>
 */
public class RefundException extends RuntimeException {

    public RefundException(String message) {
        super(message);
    }

    public RefundException(String message, Throwable cause) {
        super(message, cause);
    }
}
