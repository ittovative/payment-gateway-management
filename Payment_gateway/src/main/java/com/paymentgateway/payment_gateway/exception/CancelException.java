package com.paymentgateway.payment_gateway.exception;

public class CancelException extends RuntimeException {
  public CancelException(String message) {
    super(message);
  }

  public CancelException(String message, Throwable cause) {
    super(message, cause);
  }
}
