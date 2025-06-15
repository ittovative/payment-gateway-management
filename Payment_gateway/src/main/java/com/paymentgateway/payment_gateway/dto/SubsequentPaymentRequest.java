package com.paymentgateway.payment_gateway.dto;


public record SubsequentPaymentRequest(
        String referenceId,
        Long amount,
        String currency,
        String provider
) {}