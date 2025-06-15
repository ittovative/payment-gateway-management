package com.paymentgateway.payment_gateway.exception;

/**
 * This exception is thrown when a payment cancellation process fails.
 * <p>
 * It is used to handle errors that happen during cancel operations
 * with payment providers like Stripe, PayPal, or Adyen.
 * </p>
 */
public class CancelException extends RuntimeException {
  public CancelException(String message) {
    super(message);
  }

  public CancelException(String message, Throwable cause) {
    super(message, cause);
  }
}
