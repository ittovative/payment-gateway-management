package com.paymentgateway.payment_gateway.service;

import com.paymentgateway.payment_gateway.dto.SubsequentPaymentRequest;
import com.paymentgateway.payment_gateway.dto.SubsequentPaymentResponse;
import com.paymentgateway.payment_gateway.dto.FirstPaymentRequest;
import com.paymentgateway.payment_gateway.dto.FirstPaymentResponse;
import com.paymentgateway.payment_gateway.factory.PaymentStrategyFactory;
import com.paymentgateway.payment_gateway.strategy.PaymentStrategy;
import org.springframework.stereotype.Service;

@Service
public class PaymentOrchestrator {

    private final PaymentStrategyFactory strategyFactory;

    public PaymentOrchestrator(PaymentStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public FirstPaymentResponse createDirectPayment(FirstPaymentRequest request) {

        PaymentStrategy strategy = strategyFactory.getStrategy(request.provider());
        return strategy.createDirectPayment(request);
    }

    public FirstPaymentResponse createAuthorizationPayment(FirstPaymentRequest request) {

        PaymentStrategy strategy = strategyFactory.getStrategy(request.provider());
        return strategy.createAuthorizationPayment(request);
    }

    public SubsequentPaymentResponse capturePayment(SubsequentPaymentRequest request) {

        PaymentStrategy strategy = strategyFactory.getStrategy(request.provider());
        return strategy.capturePayment(request);
    }

    public SubsequentPaymentResponse cancelPayment(SubsequentPaymentRequest request) {

        PaymentStrategy strategy = strategyFactory.getStrategy(request.provider());
        return strategy.cancelPayment(request);
    }

    public SubsequentPaymentResponse refundPayment(SubsequentPaymentRequest request) {

        PaymentStrategy strategy = strategyFactory.getStrategy(request.provider());
        return strategy.refundPayment(request);
    }
}
