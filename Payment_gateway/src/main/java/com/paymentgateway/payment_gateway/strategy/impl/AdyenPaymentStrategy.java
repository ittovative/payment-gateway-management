package com.paymentgateway.payment_gateway.strategy.impl;

import com.adyen.model.checkout.*;
import com.adyen.service.checkout.ModificationsApi;
import com.adyen.service.checkout.PaymentLinksApi;
import com.adyen.service.exception.ApiException;
import com.paymentgateway.payment_gateway.config.AdyenConfigProperties;
import com.paymentgateway.payment_gateway.dto.PaymentOperationRequest;
import com.paymentgateway.payment_gateway.dto.PaymentOperationResponse;
import com.paymentgateway.payment_gateway.dto.PaymentRequest;
import com.paymentgateway.payment_gateway.dto.PaymentResponse;
import com.paymentgateway.payment_gateway.exception.*;
import com.paymentgateway.payment_gateway.strategy.PaymentStrategy;
import com.paymentgateway.payment_gateway.util.Constant;
import com.paymentgateway.payment_gateway.util.CurrencyConverter;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.UUID;

@Component
public class AdyenPaymentStrategy implements PaymentStrategy {

    private final PaymentLinksApi paymentLinksApi;
    private final AdyenConfigProperties config;
    private final ModificationsApi modificationsApi;

    public AdyenPaymentStrategy(PaymentLinksApi paymentLinksApi,
                                AdyenConfigProperties config,
                                ModificationsApi modificationsApi) {
        this.paymentLinksApi = paymentLinksApi;
        this.config = config;
        this.modificationsApi = modificationsApi;
    }

    @Override
    public PaymentResponse createDirectPayment(PaymentRequest request) {
        try {


            Long  convertedAmount = CurrencyConverter.convertAmount(request.amount(), request.currency());

            Amount amount = new Amount()
                    .currency(request.currency())
                    .value(convertedAmount * request.quantity() );

            String orderReference = Constant.AdyenService.PREFIX_UUID + UUID.randomUUID();

            PaymentLinkRequest linkRequest = new PaymentLinkRequest()
                    .amount(amount)
                    .reference(orderReference)
                    .merchantAccount(config.getMerchantAccount())
                    .returnUrl(config.getReturnUrl())
                    .captureDelayHours(Constant.AdyenService.CAPTURE_DELAY_HOURS);

            PaymentLinkResponse response = paymentLinksApi.paymentLinks(linkRequest);

            return new PaymentResponse(
                    response.getUrl(),
                    response.getReference(),
                    Constant.CommonRsponseData.ACCEPT,
                    Constant.CommonSuccessMessage.PAYMENT_DIRECT
            );
        } catch (IOException | ApiException e ) {
            throw new PaymentException(Constant.CommonExeption.PAYMENT_DIRECT + e.getMessage() );
        }
    }

    @Override
    public PaymentResponse createAuthorizationPayment(PaymentRequest request) {
        try {

            Long  convertedAmount = CurrencyConverter.convertAmount(request.amount(), request.currency());

            Amount amount = new Amount()
                    .currency(request.currency())
                    .value(convertedAmount * request.quantity() );

            String orderReference = Constant.AdyenService.PREFIX_UUID + UUID.randomUUID();

            PaymentLinkRequest linkRequest = new PaymentLinkRequest()
                    .amount(amount)
                    .reference(orderReference)
                    .merchantAccount(config.getMerchantAccount())
                    .returnUrl(config.getReturnUrl())
                    .manualCapture(true);

            PaymentLinkResponse response = paymentLinksApi.paymentLinks(linkRequest);

            return new PaymentResponse(
                    response.getUrl(),
                    response.getReference(),
                    Constant.CommonRsponseData.ACCEPT,
                    Constant.CommonSuccessMessage.PAYMENT_AUTHORIZATION
            );
        } catch (IOException | ApiException e) {
            throw new AuthenticationCreationException(Constant.CommonExeption.PAYMENT_AUTHORIZATION + e.getMessage() );
        }
    }

    @Override
    public PaymentOperationResponse capturePayment(PaymentOperationRequest request) {
        try {

            Long  convertedAmount = CurrencyConverter.convertAmount(request.amount(), request.currency());

            Amount amount = new Amount()
                    .currency(request.currency())
                    .value(convertedAmount );

            PaymentCaptureRequest captureRequest = new PaymentCaptureRequest()
                    .merchantAccount(config.getMerchantAccount())
                    .amount(amount);

            PaymentCaptureResponse response = modificationsApi
                    .captureAuthorisedPayment(request.referenceId(), captureRequest);

            return new PaymentOperationResponse(
                    request.referenceId(),
                    response.getPspReference(),
                    request.amount(),
                    request.currency(),
                    Constant.CommonRsponseData.ACCEPT,
                    Constant.CommonSuccessMessage.PAYMENT_CAPTURE
            );
        } catch (IOException | ApiException e) {
            throw new CaptureException( Constant.CommonExeption.PAYMENT_CAPTURE + e.getMessage(), e);
        }
    }

    @Override
    public PaymentOperationResponse cancelPayment(PaymentOperationRequest request) {
        try {
            PaymentCancelRequest cancelRequest = new PaymentCancelRequest()
                    .merchantAccount(config.getMerchantAccount());

            PaymentCancelResponse response = modificationsApi
                    .cancelAuthorisedPaymentByPspReference(request.referenceId(), cancelRequest);

            return new PaymentOperationResponse(
                    request.referenceId(),
                    response.getPspReference(),
                    null,
                    null,
                    Constant.CommonRsponseData.ACCEPT,
                    Constant.CommonSuccessMessage.PAYMENT_CANCEL
            );
        } catch (IOException | ApiException e) {
            throw new CancelException(Constant.CommonExeption.PAYMENT_CANCEL + e.getMessage() );
        }
    }

    @Override
    public PaymentOperationResponse refundPayment(PaymentOperationRequest request) {
        try {

            Amount amount = new Amount()
                    .currency(request.currency())
                    .value(request.amount() * Constant.AdyenService.VALUE);

            PaymentRefundRequest refundRequest = new PaymentRefundRequest()
                    .merchantAccount(config.getMerchantAccount())
                    .amount(amount);

            PaymentRefundResponse response = modificationsApi
                    .refundCapturedPayment(request.referenceId(), refundRequest);

            return new PaymentOperationResponse(
                    request.referenceId(),
                    response.getPspReference(),
                    request.amount(),
                    request.currency(),
                    Constant.CommonRsponseData.ACCEPT ,
                    Constant.CommonSuccessMessage.PAYMENT_REFUND
            );
        } catch (IOException | ApiException e) {
            throw new RefundException( Constant.CommonExeption.PAYMENT_REFUND + e.getMessage() );
        }
    }

    @Override
    public String getProviderName() {
        return Constant.Provider.ADYEN_PROVIDER;
    }
}
