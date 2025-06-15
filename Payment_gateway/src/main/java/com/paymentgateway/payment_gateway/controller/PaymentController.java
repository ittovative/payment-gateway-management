package com.paymentgateway.payment_gateway.controller;

import com.paymentgateway.payment_gateway.dto.*;
import com.paymentgateway.payment_gateway.service.PaymentOrchestrator;
import com.paymentgateway.payment_gateway.strategy.impl.StripePaymentStrategy;
import com.paymentgateway.payment_gateway.util.APIResponse;
import com.paymentgateway.payment_gateway.util.Constants;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling payment-related operations.
 * Provides endpoints for direct payments, authorizations, captures, cancellations,
 * refunds, and subscription creation.
 */
@RestController
@RequestMapping(Constants.PaymentController.PAYMENT)
public class PaymentController {

    private final PaymentOrchestrator paymentOrchestrator;
    private final StripePaymentStrategy  stripePaymentStrategy ;


    public PaymentController(PaymentOrchestrator paymentOrchestrator, StripePaymentStrategy stripePaymentStrategy) {
        this.paymentOrchestrator = paymentOrchestrator;
        this.stripePaymentStrategy = stripePaymentStrategy;
    }

    /**
     * Handles the creation of a direct payment.
     *
     * @param request the payment request details
     * @return a response entity containing the payment result
     */
    @PostMapping(Constants.PaymentController.PAYMENT_DIRECT)
    public ResponseEntity<APIResponse<FirstPaymentResponse>> createDirectPayment(
            @RequestBody @Valid FirstPaymentRequest request) {

        FirstPaymentResponse response = paymentOrchestrator.createDirectPayment(request);

        return ResponseEntity.ok(
                APIResponse.ok(response, Constants.CommonSuccessMessage.PAYMENT_DIRECT)
        );
    }

    /**
     * Handles the creation of an authorization-based payment (used for delayed capture scenarios).
     *
     * @param request the payment request details
     * @return a response entity containing the authorization result
     */
    @PostMapping(Constants.PaymentController.PAYMENT_AUTHORIZATION)
    public ResponseEntity<APIResponse<FirstPaymentResponse>> createAuthorizationPayment(
            @RequestBody FirstPaymentRequest request) {

        FirstPaymentResponse response = paymentOrchestrator.createAuthorizationPayment(request);

        return ResponseEntity.ok(
                APIResponse.ok(response, Constants.CommonSuccessMessage.PAYMENT_AUTHORIZATION)
        );
    }

    /**
     * Captures a previously authorized payment.
     *
     * @param request the capture request containing the authorization reference
     * @return a response entity containing the capture result
     */
    @PostMapping(Constants.PaymentController.PAYMENT_CAPTURE)
    public ResponseEntity<APIResponse<SubsequentPaymentResponse>> capturePayment(
            @RequestBody SubsequentPaymentRequest request) {

        SubsequentPaymentResponse response = paymentOrchestrator.capturePayment(request);

        return ResponseEntity.ok(
                APIResponse.ok(response, Constants.CommonSuccessMessage.PAYMENT_CAPTURE)
        );
    }

    /**
     * Cancels a previously authorized payment before it is captured.
     *
     * @param request the cancel request with relevant identifiers
     * @return a response entity containing the cancellation result
     */
    @PostMapping(Constants.PaymentController.PAYMENT_CANCEL)
    public ResponseEntity<APIResponse<SubsequentPaymentResponse>> cancelPayment(
            @RequestBody SubsequentPaymentRequest request) {

        SubsequentPaymentResponse response = paymentOrchestrator.cancelPayment(request);

        return ResponseEntity.ok(
                APIResponse.ok(response, Constants.CommonSuccessMessage.PAYMENT_CANCEL)
        );
    }

    /**
     * Processes a refund for a previously completed payment.
     *
     * @param request the refund request containing transaction details
     * @return a response entity containing the refund result
     */
    @PostMapping(Constants.PaymentController.PAYMENT_REFUND)
    public ResponseEntity<APIResponse<SubsequentPaymentResponse>> refundPayment(
            @RequestBody SubsequentPaymentRequest request) {


        SubsequentPaymentResponse response = paymentOrchestrator.refundPayment(request);

        return ResponseEntity.ok(
                APIResponse.ok(response, Constants.CommonSuccessMessage.PAYMENT_REFUND)
        );
    }


    /**
     * Creates a new subscription payment using Stripe.
     *
     * @return a response entity containing the subscription result
     */
    @PostMapping(Constants.PaymentController.SUBSCRIPTION)
    public ResponseEntity<APIResponse<FirstPaymentResponse>> subscriptionPayment() {


        FirstPaymentResponse response = stripePaymentStrategy.createSubscription();

        return ResponseEntity.ok(
                APIResponse.ok(response, Constants.CommonSuccessMessage.PAYMENT_REFUND)
        );

    }





}
