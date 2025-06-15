package com.paymentgateway.payment_gateway.exception;

import com.paymentgateway.payment_gateway.util.APIResponse;
import com.paymentgateway.payment_gateway.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AuthenticationCreationException.class)
    public ResponseEntity<APIResponse<String>> handleSessionCreationException(AuthenticationCreationException ex) {

        APIResponse<String> response = APIResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CaptureException.class)
    public ResponseEntity<APIResponse<String>> handleCaptureException(CaptureException ex) {

        APIResponse<String> response = APIResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CancelException.class)
    public ResponseEntity<APIResponse<String>> handleCancelException(CancelException ex) {

        APIResponse<String> response = APIResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RefundException.class)
    public ResponseEntity<APIResponse<String>> handleRefundException(RefundException ex) {

        APIResponse<String> response = APIResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<APIResponse<String>> handlePaymentException(PaymentException ex) {

        APIResponse<String> response = APIResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<APIResponse<String>> handleIOException(IOException ex) {

        APIResponse<String> response = APIResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.CommonExeption.GLOBAL_EXCEPTION);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<String>> handleAllExceptions(Exception ex) {

        APIResponse<String> response = APIResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),Constants.CommonExeption.GLOBAL_EXCEPTION);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HMACValidationException.class)
    public ResponseEntity<APIResponse<String>> handleHMACValidationException(HMACValidationException ex) {

        APIResponse<String> response = APIResponse.error(HttpStatus.UNAUTHORIZED.value(), Constants.CommonExeption.GLOBAL_EXCEPTION);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
