package com.paymentgateway.payment_gateway.exception;

/**
 * This exception is thrown when a payment capture process fails.
 * <p>
 * Capturing a payment means completing a previously authorized transaction.
 * If the capture fails (for example, due to an expired authorization or a
 * provider error), this exception is used to handle that failure.
 * </p>
 */
public class CaptureException extends RuntimeException {
  public CaptureException(String message) {
    super(message);
  }

  public CaptureException(String message, Throwable cause) {
    super(message, cause);
  }
}
