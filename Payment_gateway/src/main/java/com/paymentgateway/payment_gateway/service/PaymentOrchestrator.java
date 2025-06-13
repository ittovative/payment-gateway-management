package com.paymentgateway.payment_gateway.service;

import com.paymentgateway.payment_gateway.dto.PaymentOperationRequest;
import com.paymentgateway.payment_gateway.dto.PaymentOperationResponse;
import com.paymentgateway.payment_gateway.dto.PaymentRequest;
import com.paymentgateway.payment_gateway.dto.PaymentResponse;
import com.paymentgateway.payment_gateway.factory.PaymentStrategyFactory;
import com.paymentgateway.payment_gateway.strategy.PaymentStrategy;
import org.springframework.stereotype.Service;

@Service
public class PaymentOrchestrator {

    private final PaymentStrategyFactory strategyFactory;

    public PaymentOrchestrator(PaymentStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public PaymentResponse createDirectPayment(PaymentRequest request) {

        PaymentStrategy strategy = strategyFactory.getStrategy(request.provider());
        return strategy.createDirectPayment(request);
    }

    public PaymentResponse createAuthorizationPayment(PaymentRequest request) {

        PaymentStrategy strategy = strategyFactory.getStrategy(request.provider());
        return strategy.createAuthorizationPayment(request);
    }

    public PaymentOperationResponse capturePayment(PaymentOperationRequest request) {

        PaymentStrategy strategy = strategyFactory.getStrategy(request.provider());
        return strategy.capturePayment(request);
    }

    public PaymentOperationResponse cancelPayment(PaymentOperationRequest request) {

        PaymentStrategy strategy = strategyFactory.getStrategy(request.provider());
        return strategy.cancelPayment(request);
    }

    public PaymentOperationResponse refundPayment(PaymentOperationRequest request) {

        PaymentStrategy strategy = strategyFactory.getStrategy(request.provider());
        return strategy.refundPayment(request);
    }
}
