package com.paymentgateway.payment_gateway.dto;

public record SubscriptionRequest(
        String name ,
        String email ,
        String CustomerId
) {}
