package com.paymentgateway.payment_gateway.exception;

/**
 * This exception is thrown for general payment-related errors.
 */
public class PaymentException extends RuntimeException {
  public PaymentException(String message) {
    super(message);
  }

  public PaymentException(String message, Throwable cause) {
    super(message, cause);
  }

}
