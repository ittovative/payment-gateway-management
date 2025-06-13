package com.paymentgateway.payment_gateway.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record FirstPaymentRequest(
        @NotNull Long amount,
        @NotNull String currency,
        @NotNull String provider, // ADYEN, STRIPE, PAYPAL
        String description,
        @Min(1)
        Long quantity
) {
        public FirstPaymentRequest {
                if (quantity == null || quantity < 1) {
                        quantity = 1L;
                }
        }
}
