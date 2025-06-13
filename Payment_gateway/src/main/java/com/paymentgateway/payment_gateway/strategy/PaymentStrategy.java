package com.paymentgateway.payment_gateway.strategy;

import com.paymentgateway.payment_gateway.dto.PaymentOperationRequest;
import com.paymentgateway.payment_gateway.dto.PaymentOperationResponse;
import com.paymentgateway.payment_gateway.dto.PaymentRequest;
import com.paymentgateway.payment_gateway.dto.PaymentResponse;

public interface PaymentStrategy {

    PaymentResponse createDirectPayment(PaymentRequest request);

    PaymentResponse createAuthorizationPayment(PaymentRequest request);

    PaymentOperationResponse capturePayment(PaymentOperationRequest request);

    PaymentOperationResponse cancelPayment(PaymentOperationRequest request);

    PaymentOperationResponse refundPayment(PaymentOperationRequest request);

    String getProviderName();
}
