package com.paymentgateway.payment_gateway.service;

import com.paymentgateway.payment_gateway.dto.SubsequentPaymentRequest;
import com.paymentgateway.payment_gateway.dto.SubsequentPaymentResponse;
import com.paymentgateway.payment_gateway.dto.FirstPaymentRequest;
import com.paymentgateway.payment_gateway.dto.FirstPaymentResponse;
import com.paymentgateway.payment_gateway.factory.PaymentStrategyFactory;
import com.paymentgateway.payment_gateway.strategy.PaymentStrategy;
import org.springframework.stereotype.Service;


/**
 * The PaymentOrchestrator is responsible for coordinating payment operations.
 * <p>
 * It uses the PaymentStrategyFactory to select the correct payment strategy based on the provider.
 * This allows your system to converse with different payment providers in a unified way.
 * </p>
 * <p>
 * It supports several operations like direct payment, authorization, capture, cancel, and refund.
 * </p>
 */
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
