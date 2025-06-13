package com.paymentgateway.payment_gateway.strategy;

import com.paymentgateway.payment_gateway.dto.SubsequentPaymentRequest;
import com.paymentgateway.payment_gateway.dto.SubsequentPaymentResponse;
import com.paymentgateway.payment_gateway.dto.FirstPaymentRequest;
import com.paymentgateway.payment_gateway.dto.FirstPaymentResponse;

public interface PaymentStrategy {

    FirstPaymentResponse createDirectPayment(FirstPaymentRequest request);

    FirstPaymentResponse createAuthorizationPayment(FirstPaymentRequest request);

    SubsequentPaymentResponse capturePayment(SubsequentPaymentRequest request);

    SubsequentPaymentResponse cancelPayment(SubsequentPaymentRequest request);

    SubsequentPaymentResponse refundPayment(SubsequentPaymentRequest request);

    String getProviderName();
}
