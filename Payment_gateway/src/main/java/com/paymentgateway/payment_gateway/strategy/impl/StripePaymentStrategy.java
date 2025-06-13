package com.paymentgateway.payment_gateway.strategy.impl;

import com.paymentgateway.payment_gateway.dto.*;
import com.paymentgateway.payment_gateway.exception.*;
import com.paymentgateway.payment_gateway.strategy.PaymentStrategy;
import com.paymentgateway.payment_gateway.util.Constant;
import com.paymentgateway.payment_gateway.util.CurrencyConverter;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCancelParams;
import com.stripe.param.PaymentIntentCaptureParams;
import com.stripe.param.RefundCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripePaymentStrategy implements PaymentStrategy {


    @Value("${stripe.api-key}")
    private String secretKey;


    @Override
    public PaymentResponse createDirectPayment(PaymentRequest request) {
        try {
            Stripe.apiKey = secretKey;

            SessionCreateParams params = buildSessionParams(request, false);
            Session session = Session.create(params);

            return new PaymentResponse(
                    session.getUrl(),
                    session.getId(),
                    Constant.CommonRsponseData.ACCEPT,
                    Constant.CommonSuccessMessage.PAYMENT_DIRECT
            );
        } catch (StripeException e) {
            throw new PaymentException(Constant.CommonExeption.PAYMENT_DIRECT + e.getMessage(), e);
        }
    }

    @Override
    public PaymentResponse createAuthorizationPayment(PaymentRequest request) {
        try {
            Stripe.apiKey = secretKey;

            SessionCreateParams params = buildSessionParams(request, true);
            Session session = Session.create(params);

            return new PaymentResponse(
                    session.getUrl(),
                    session.getId(),
                    Constant.CommonRsponseData.ACCEPT,
                    Constant.CommonSuccessMessage.PAYMENT_AUTHORIZATION
            );
        } catch (StripeException e) {
            throw new AuthenticationCreationException(Constant.CommonExeption.PAYMENT_AUTHORIZATION + e.getMessage() );
        }
    }

    @Override
    public PaymentOperationResponse capturePayment(PaymentOperationRequest request) {
        try {
            Stripe.apiKey = secretKey;

            PaymentIntent resource = PaymentIntent.retrieve(request.referenceId());
            PaymentIntentCaptureParams params = PaymentIntentCaptureParams.builder()
                    .setAmountToCapture(request.amount())
                    .build();

            PaymentIntent captured = resource.capture(params);

            return new PaymentOperationResponse(
                    request.referenceId(),
                    captured.getId(),
                    captured.getAmountCapturable(),
                    captured.getCurrency(),
                    Constant.CommonRsponseData.ACCEPT,
                    Constant.CommonSuccessMessage.PAYMENT_CAPTURE
            );
        } catch (StripeException e) {
            throw new CaptureException( Constant.CommonExeption.PAYMENT_CAPTURE + e.getMessage() );
        }
    }

    @Override
    public PaymentOperationResponse cancelPayment(PaymentOperationRequest request) {
        try {
            Stripe.apiKey = secretKey;

            PaymentIntent resource = PaymentIntent.retrieve(request.referenceId());
            PaymentIntentCancelParams params = PaymentIntentCancelParams.builder().build();
            PaymentIntent cancelled = resource.cancel(params);

            return new PaymentOperationResponse(
                    request.referenceId(),
                    cancelled.getId(),
                    null,
                    null,
                    Constant.CommonRsponseData.ACCEPT,
                    Constant.CommonSuccessMessage.PAYMENT_CANCEL
            );
        } catch (StripeException e) {
            throw new CancelException(Constant.CommonExeption.PAYMENT_CANCEL + e.getMessage() );
        }
    }

    @Override
    public PaymentOperationResponse refundPayment(PaymentOperationRequest request) {
        try {
            Stripe.apiKey = secretKey;

            RefundCreateParams params = RefundCreateParams.builder()
                    .setPaymentIntent(request.referenceId())
                    .build();

            Refund refund = Refund.create(params);

            return new PaymentOperationResponse(
                    request.referenceId(),
                    refund.getPaymentIntent(),
                    refund.getAmount(),
                    refund.getCurrency(),
                    Constant.CommonRsponseData.ACCEPT,
                    Constant.CommonSuccessMessage.PAYMENT_REFUND
            );
        } catch (StripeException e) {
            throw new RefundException( Constant.CommonExeption.PAYMENT_REFUND + e.getMessage() );
        }
    }

    @Override
    public String getProviderName() {
        return Constant.Provider.STRIPE_PROVIDER;
    }

    private SessionCreateParams buildSessionParams(PaymentRequest request, boolean requiresManualCapture) {

        Long  convertedAmount = CurrencyConverter.convertAmount(request.amount(), request.currency());

        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName( request.description() != null ? request.description() : Constant.StripeStrategy.DESCRIPTION)
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(request.currency().toLowerCase())
                        .setUnitAmount( convertedAmount )
                        .setProductData(productData)
                        .build();

        SessionCreateParams.LineItem lineItem =
                SessionCreateParams.LineItem.builder()
                        .setQuantity(request.quantity())  // request.quantity() != null ? request.quantity() : 1L
                        .setPriceData(priceData)
                        .build();

        SessionCreateParams.Builder paramsBuilder =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(Constant.StripeURL.SUCCESS_URL)
                        .setCancelUrl(Constant.StripeURL.FAILED_URL)
                        .addLineItem(lineItem);

        if (requiresManualCapture) {
            paramsBuilder.setPaymentIntentData(
                    SessionCreateParams.PaymentIntentData.builder()
                            .setCaptureMethod(SessionCreateParams.PaymentIntentData.CaptureMethod.MANUAL)
                            .build()
            );
        }

        return paramsBuilder.build();
    }


}
