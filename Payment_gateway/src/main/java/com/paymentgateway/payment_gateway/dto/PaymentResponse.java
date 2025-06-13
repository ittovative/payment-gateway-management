package com.paymentgateway.payment_gateway.dto;

public record PaymentResponse(

        String redirectUrl,
        String referenceId,
        String status,
        String message
){}
