package com.paymentgateway.payment_gateway.dto;


public record FirstPaymentRequest(

        Long amount,
        String currency,
        String provider, // ADYEN, STRIPE, PAYPAL
        String description,
        Long quantity,
        String name,
        String email,
        String id
) {}
