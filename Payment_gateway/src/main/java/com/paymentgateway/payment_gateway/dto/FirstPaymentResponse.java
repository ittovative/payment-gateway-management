package com.paymentgateway.payment_gateway.dto;

public record FirstPaymentResponse(

        String redirectUrl,
        String referenceId,
        String status,
        String message,
        String customerId
){}
