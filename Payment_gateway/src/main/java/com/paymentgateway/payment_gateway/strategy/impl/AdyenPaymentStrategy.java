package com.paymentgateway.payment_gateway.strategy.impl;

import com.adyen.model.checkout.*;
import com.adyen.service.checkout.ModificationsApi;
import com.adyen.service.checkout.PaymentLinksApi;
import com.adyen.service.exception.ApiException;
import com.paymentgateway.payment_gateway.config.AdyenConfigProperties;
import com.paymentgateway.payment_gateway.dto.SubsequentPaymentRequest;
import com.paymentgateway.payment_gateway.dto.SubsequentPaymentResponse;
import com.paymentgateway.payment_gateway.dto.FirstPaymentRequest;
import com.paymentgateway.payment_gateway.dto.FirstPaymentResponse;
import com.paymentgateway.payment_gateway.exception.*;
import com.paymentgateway.payment_gateway.strategy.PaymentStrategy;
import com.paymentgateway.payment_gateway.util.Constants;
import com.paymentgateway.payment_gateway.util.CurrencyConverter;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.UUID;

/**
 * This class handles payments using the Adyen provider.
 * It is part of a strategy pattern to help converse with different payment services
 * in a unified way. Each method performs a particular action like capture, refund, etc.
 */
@Component
public class AdyenPaymentStrategy implements PaymentStrategy {

    private final PaymentLinksApi paymentLinksApi;
    private final AdyenConfigProperties config;
    private final ModificationsApi modificationsApi;

    /**
     * Creates an instance of the AdyenPaymentStrategy.
     *
     * @param paymentLinksApi   used to create payment links
     * @param config            contains Adyen configuration like merchant account
     * @param modificationsApi  used to perform actions like capture, cancel, and refund
     */
    public AdyenPaymentStrategy(PaymentLinksApi paymentLinksApi,
                                AdyenConfigProperties config,
                                ModificationsApi modificationsApi) {
        this.paymentLinksApi = paymentLinksApi;
        this.config = config;
        this.modificationsApi = modificationsApi;
    }

    /**
     * Creates a direct payment. The user pays immediately.
     *
     * @param request contains amount, currency, and other info
     * @return a response with payment URL and reference
     * @throws PaymentException if something goes wrong while talking to Adyen
     */
    @Override
    public FirstPaymentResponse createDirectPayment(FirstPaymentRequest request) {
        try {


            Long  convertedAmount = CurrencyConverter.convertAmount(request.amount(), request.currency());

            Amount amount = new Amount()
                    .currency(request.currency())
                    .value(convertedAmount * request.quantity() );

            String orderReference = Constants.AdyenService.PREFIX_UUID + UUID.randomUUID();

            PaymentLinkRequest linkRequest = new PaymentLinkRequest()
                    .amount(amount)
                    .reference(orderReference)
                    .merchantAccount(config.getMerchantAccount())
                    .returnUrl(config.getReturnUrl())
                    .captureDelayHours(Constants.AdyenService.CAPTURE_DELAY_HOURS);

            PaymentLinkResponse response = paymentLinksApi.paymentLinks(linkRequest);

            return new FirstPaymentResponse(
                    response.getUrl(),
                    response.getReference(),
                    Constants.CommonRsponseData.ACCEPT,
                    Constants.CommonSuccessMessage.PAYMENT_DIRECT,
                    null
            );
        } catch (IOException | ApiException e ) {
            throw new PaymentException(Constants.CommonExeption.PAYMENT_DIRECT + e.getMessage() );
        }
    }

    /**
     * Creates an authorization payment. The user approves now, and the money is captured later.
     *
     * @param request the payment request
     * @return a response with a redirect URL and reference
     * @throws AuthenticationCreationException if something causes the authorization to fail
     */
    @Override
    public FirstPaymentResponse createAuthorizationPayment(FirstPaymentRequest request) {
        try {

            Long  convertedAmount = CurrencyConverter.convertAmount(request.amount(), request.currency());

            Amount amount = new Amount()
                    .currency(request.currency())
                    .value(convertedAmount * request.quantity() );

            String orderReference = Constants.AdyenService.PREFIX_UUID + UUID.randomUUID();

            PaymentLinkRequest linkRequest = new PaymentLinkRequest()
                    .amount(amount)
                    .reference(orderReference)
                    .merchantAccount(config.getMerchantAccount())
                    .returnUrl(config.getReturnUrl())
                    .manualCapture(true);

            PaymentLinkResponse response = paymentLinksApi.paymentLinks(linkRequest);

            return new FirstPaymentResponse(
                    response.getUrl(),
                    response.getReference(),
                    Constants.CommonRsponseData.ACCEPT,
                    Constants.CommonSuccessMessage.PAYMENT_AUTHORIZATION,
                    null
            );
        } catch (IOException | ApiException e) {
            throw new AuthenticationCreationException(Constants.CommonExeption.PAYMENT_AUTHORIZATION + e.getMessage() );
        }
    }


    /**
     * Captures money from an authorized payment.
     * This usually happens after the user approved the payment somewhere before.
     *
     * @param request contains the reference ID and amount to capture
     * @return a response with the result of the capture
     * @throws CaptureException if the capture fails
     */
    @Override
    public SubsequentPaymentResponse capturePayment(SubsequentPaymentRequest request) {
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

            return new SubsequentPaymentResponse(
                    request.referenceId(),
                    response.getPspReference(),
                    request.amount(),
                    request.currency(),
                    Constants.CommonRsponseData.ACCEPT,
                    Constants.CommonSuccessMessage.PAYMENT_CAPTURE
            );
        } catch (IOException | ApiException e) {
            throw new CaptureException( Constants.CommonExeption.PAYMENT_CAPTURE + e.getMessage(), e);
        }
    }

    /**
     * Cancels an authorized payment before the money is captured.
     *
     * @param request contains the payment reference
     * @return a response showing that the cancel was successful
     * @throws CancelException if cancel fails
     */
    @Override
    public SubsequentPaymentResponse cancelPayment(SubsequentPaymentRequest request) {
        try {
            PaymentCancelRequest cancelRequest = new PaymentCancelRequest()
                    .merchantAccount(config.getMerchantAccount());

            PaymentCancelResponse response = modificationsApi
                    .cancelAuthorisedPaymentByPspReference(request.referenceId(), cancelRequest);

            return new SubsequentPaymentResponse(
                    request.referenceId(),
                    response.getPspReference(),
                    null,
                    null,
                    Constants.CommonRsponseData.ACCEPT,
                    Constants.CommonSuccessMessage.PAYMENT_CANCEL
            );
        } catch (IOException | ApiException e) {
            throw new CancelException(Constants.CommonExeption.PAYMENT_CANCEL + e.getMessage() );
        }
    }

    /**
     * Refunds a completed payment. This gives the user back their money.
     *
     * @param request contains reference and amount to refund
     * @return a response with refund status
     * @throws RefundException if refund fails
     */
    @Override
    public SubsequentPaymentResponse refundPayment(SubsequentPaymentRequest request) {
        try {

            Amount amount = new Amount()
                    .currency(request.currency())
                    .value(request.amount() * Constants.AdyenService.VALUE);

            PaymentRefundRequest refundRequest = new PaymentRefundRequest()
                    .merchantAccount(config.getMerchantAccount())
                    .amount(amount);

            PaymentRefundResponse response = modificationsApi
                    .refundCapturedPayment(request.referenceId(), refundRequest);

            return new SubsequentPaymentResponse(
                    request.referenceId(),
                    response.getPspReference(),
                    request.amount(),
                    request.currency(),
                    Constants.CommonRsponseData.ACCEPT ,
                    Constants.CommonSuccessMessage.PAYMENT_REFUND
            );
        } catch (IOException | ApiException e) {
            throw new RefundException( Constants.CommonExeption.PAYMENT_REFUND + e.getMessage() );
        }
    }

    /**
     * Returns the name of the provider this strategy handles.
     *
     * @return the string "ADYEN"
     */
    @Override
    public String getProviderName() {
        return Constants.Provider.ADYEN_PROVIDER;
    }
}
