package com.paymentgateway.payment_gateway.controller;

import com.paymentgateway.payment_gateway.dto.*;
import com.paymentgateway.payment_gateway.service.PaymentOrchestrator;
import com.paymentgateway.payment_gateway.strategy.impl.StripePaymentStrategy;
import com.paymentgateway.payment_gateway.util.APIResponse;
import com.paymentgateway.payment_gateway.util.Constants;

import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.PaymentController.PAYMENT)
public class PaymentController {

    private final PaymentOrchestrator paymentOrchestrator;
    private final StripePaymentStrategy  stripePaymentStrategy ;


    public PaymentController(PaymentOrchestrator paymentOrchestrator, StripePaymentStrategy stripePaymentStrategy) {
        this.paymentOrchestrator = paymentOrchestrator;
        this.stripePaymentStrategy = stripePaymentStrategy;
    }

    @PostMapping(Constants.PaymentController.PAYMENT_DIRECT)
    public ResponseEntity<APIResponse<FirstPaymentResponse>> createDirectPayment(
            @RequestBody @Valid FirstPaymentRequest request) {

        FirstPaymentResponse response = paymentOrchestrator.createDirectPayment(request);

        return ResponseEntity.ok(
                APIResponse.ok(response, Constants.CommonSuccessMessage.PAYMENT_DIRECT)
        );
    }

    @PostMapping(Constants.PaymentController.PAYMENT_AUTHORIZATION)
    public ResponseEntity<APIResponse<FirstPaymentResponse>> createAuthorizationPayment(
            @RequestBody FirstPaymentRequest request) {

        FirstPaymentResponse response = paymentOrchestrator.createAuthorizationPayment(request);

        return ResponseEntity.ok(
                APIResponse.ok(response, Constants.CommonSuccessMessage.PAYMENT_AUTHORIZATION)
        );
    }

    @PostMapping(Constants.PaymentController.PAYMENT_CAPTURE)
    public ResponseEntity<APIResponse<SubsequentPaymentResponse>> capturePayment(
            @RequestBody SubsequentPaymentRequest request) {

        SubsequentPaymentResponse response = paymentOrchestrator.capturePayment(request);

        return ResponseEntity.ok(
                APIResponse.ok(response, Constants.CommonSuccessMessage.PAYMENT_CAPTURE)
        );
    }

    @PostMapping(Constants.PaymentController.PAYMENT_CANCEL)
    public ResponseEntity<APIResponse<SubsequentPaymentResponse>> cancelPayment(
            @RequestBody SubsequentPaymentRequest request) {

        SubsequentPaymentResponse response = paymentOrchestrator.cancelPayment(request);

        return ResponseEntity.ok(
                APIResponse.ok(response, Constants.CommonSuccessMessage.PAYMENT_CANCEL)
        );
    }

    @PostMapping(Constants.PaymentController.PAYMENT_REFUND)
    public ResponseEntity<APIResponse<SubsequentPaymentResponse>> refundPayment(
            @RequestBody SubsequentPaymentRequest request) {


        SubsequentPaymentResponse response = paymentOrchestrator.refundPayment(request);

        return ResponseEntity.ok(
                APIResponse.ok(response, Constants.CommonSuccessMessage.PAYMENT_REFUND)
        );
    }


    @PostMapping(Constants.PaymentController.SUBSCRIPTION)
    public ResponseEntity<APIResponse<FirstPaymentResponse>> subscriptionPayment() {


        FirstPaymentResponse response = stripePaymentStrategy.createSubscription();

        return ResponseEntity.ok(
                APIResponse.ok(response, Constants.CommonSuccessMessage.PAYMENT_REFUND)
        );

    }





}
