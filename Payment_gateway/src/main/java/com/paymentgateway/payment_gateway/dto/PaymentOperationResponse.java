package com.paymentgateway.payment_gateway.dto;

public record PaymentOperationResponse(
        String referenceId,
        String operationReference,
        Long amount,
        String currency,
        String status,
        String message
) {
}
