package com.paymentgateway.payment_gateway.strategy;

import com.paymentgateway.payment_gateway.dto.SubsequentPaymentRequest;
import com.paymentgateway.payment_gateway.dto.SubsequentPaymentResponse;
import com.paymentgateway.payment_gateway.dto.FirstPaymentRequest;
import com.paymentgateway.payment_gateway.dto.FirstPaymentResponse;

/**
 * Strategy interface for handling various types of payments through different providers.
 * <p>
 * This interface defines a standard contract for processing payments, whether direct,
 * authorized, captured, canceled, or refunded. Each implementation should represent a
 * particular provider (e.g., Stripe, PayPal) and implement these operations accordingly.
 * <p>
 * It is a pleasure to use this strategy pattern, as it helps us keep provider-specific
 * logic somewhere else, avoiding coupling and improving maintainability.
 */
public interface PaymentStrategy {

    FirstPaymentResponse createDirectPayment(FirstPaymentRequest request);

    FirstPaymentResponse createAuthorizationPayment(FirstPaymentRequest request);

    SubsequentPaymentResponse capturePayment(SubsequentPaymentRequest request);

    SubsequentPaymentResponse cancelPayment(SubsequentPaymentRequest request);

    SubsequentPaymentResponse refundPayment(SubsequentPaymentRequest request);

    String getProviderName();
}
