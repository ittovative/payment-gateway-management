package com.paymentgateway.payment_gateway.controller;

import com.paymentgateway.payment_gateway.dto.PaymentOperationRequest;
import com.paymentgateway.payment_gateway.dto.PaymentOperationResponse;
import com.paymentgateway.payment_gateway.dto.PaymentRequest;
import com.paymentgateway.payment_gateway.dto.PaymentResponse;
import com.paymentgateway.payment_gateway.service.PaymentOrchestrator;
import com.paymentgateway.payment_gateway.util.APIResponse;
import com.paymentgateway.payment_gateway.util.Constant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.PaymentController.PAYMENT)

public class PaymentController {

    private final PaymentOrchestrator paymentOrchestrator;

    public PaymentController(PaymentOrchestrator paymentOrchestrator) {
        this.paymentOrchestrator = paymentOrchestrator;
    }

    @PostMapping(Constant.PaymentController.PAYMENT_DIRECT)
    public ResponseEntity<APIResponse<PaymentResponse>> createDirectPayment(
            @RequestBody PaymentRequest request) {

        PaymentResponse response = paymentOrchestrator.createDirectPayment(request);

        return ResponseEntity.ok(
                APIResponse.ok(response, Constant.CommonSuccessMessage.PAYMENT_DIRECT)
        );
    }

    @PostMapping(Constant.PaymentController.PAYMENT_AUTHORIZATION)
    public ResponseEntity<APIResponse<PaymentResponse>> createAuthorizationPayment(
            @RequestBody PaymentRequest request) {

        PaymentResponse response = paymentOrchestrator.createAuthorizationPayment(request);

        return ResponseEntity.ok(
                APIResponse.ok(response, Constant.CommonSuccessMessage.PAYMENT_AUTHORIZATION)
        );
    }

    @PostMapping(Constant.PaymentController.PAYMENT_CAPTURE)
    public ResponseEntity<APIResponse<PaymentOperationResponse>> capturePayment(
            @RequestBody PaymentOperationRequest request) {

        PaymentOperationResponse response = paymentOrchestrator.capturePayment(request);

        return ResponseEntity.ok(
                APIResponse.ok(response, Constant.CommonSuccessMessage.PAYMENT_CAPTURE)
        );
    }

    @PostMapping(Constant.PaymentController.PAYMENT_CANCEL)
    public ResponseEntity<APIResponse<PaymentOperationResponse>> cancelPayment(
            @RequestBody PaymentOperationRequest request) {

        PaymentOperationResponse response = paymentOrchestrator.cancelPayment(request);

        return ResponseEntity.ok(
                APIResponse.ok(response, Constant.CommonSuccessMessage.PAYMENT_CANCEL)
        );
    }

    @PostMapping(Constant.PaymentController.PAYMENT_REFUND)
    public ResponseEntity<APIResponse<PaymentOperationResponse>> refundPayment(
            @RequestBody PaymentOperationRequest request) {


        PaymentOperationResponse response = paymentOrchestrator.refundPayment(request);

        return ResponseEntity.ok(
                APIResponse.ok(response, Constant.CommonSuccessMessage.PAYMENT_REFUND)
        );
    }
}
