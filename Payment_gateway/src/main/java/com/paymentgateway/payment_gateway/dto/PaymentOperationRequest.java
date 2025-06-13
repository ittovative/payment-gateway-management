package com.paymentgateway.payment_gateway.dto;

import jakarta.validation.constraints.NotNull;

public record PaymentOperationRequest(
        @NotNull String referenceId,
        Long amount,
        String currency,
        @NotNull String provider
) {}