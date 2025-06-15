package com.paymentgateway.payment_gateway.strategy.impl;

import com.paymentgateway.payment_gateway.config.StripeConfigProperties;
import com.paymentgateway.payment_gateway.dto.*;
import com.paymentgateway.payment_gateway.exception.*;
import com.paymentgateway.payment_gateway.strategy.PaymentStrategy;
import com.paymentgateway.payment_gateway.util.Constants;
import com.paymentgateway.payment_gateway.util.CurrencyConverter;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCancelParams;
import com.stripe.param.PaymentIntentCaptureParams;
import com.stripe.param.RefundCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StripePaymentStrategy implements PaymentStrategy {

    private final StripeConfigProperties config;

    public StripePaymentStrategy(StripeConfigProperties config) {
        this.config = config;
    }

    @Override
    public FirstPaymentResponse createDirectPayment(FirstPaymentRequest request) {
        try {

            SessionCreateParams params = buildSessionParams(request, false);
            Session session = Session.create(params);

            return new FirstPaymentResponse(
                    session.getUrl(),
                    session.getId(),
                    Constants.CommonRsponseData.ACCEPT,
                    Constants.CommonSuccessMessage.PAYMENT_DIRECT,
                    session.getCustomer()
            );
        } catch (StripeException e) {
            throw new PaymentException(Constants.CommonExeption.PAYMENT_DIRECT + e.getMessage(), e);
        }
    }


    @Override
    public FirstPaymentResponse createAuthorizationPayment(FirstPaymentRequest request) {
        try {


            SessionCreateParams params = buildSessionParams(request, true);
            Session session = Session.create(params);

            return new FirstPaymentResponse(
                    session.getUrl(),
                    session.getId(),
                    Constants.CommonRsponseData.ACCEPT,
                    Constants.CommonSuccessMessage.PAYMENT_AUTHORIZATION,
                    request.id()
            );
        } catch (StripeException e) {
            throw new AuthenticationCreationException(Constants.CommonExeption.PAYMENT_AUTHORIZATION + e.getMessage());
        }
    }

    @Override
    public SubsequentPaymentResponse capturePayment(SubsequentPaymentRequest request) {
        try {


            PaymentIntent resource = PaymentIntent.retrieve(request.referenceId());
            PaymentIntentCaptureParams params = PaymentIntentCaptureParams.builder()
                    .setAmountToCapture(request.amount())
                    .build();

            PaymentIntent captured = resource.capture(params);

            return new SubsequentPaymentResponse(
                    request.referenceId(),
                    captured.getId(),
                    captured.getAmountCapturable(),
                    captured.getCurrency(),
                    Constants.CommonRsponseData.ACCEPT,
                    Constants.CommonSuccessMessage.PAYMENT_CAPTURE
            );
        } catch (StripeException e) {
            throw new CaptureException(Constants.CommonExeption.PAYMENT_CAPTURE + e.getMessage());
        }
    }

    @Override
    public SubsequentPaymentResponse cancelPayment(SubsequentPaymentRequest request) {
        try {


            PaymentIntent resource = PaymentIntent.retrieve(request.referenceId());
            PaymentIntentCancelParams params = PaymentIntentCancelParams.builder().build();
            PaymentIntent cancelled = resource.cancel(params);

            return new SubsequentPaymentResponse(
                    request.referenceId(),
                    cancelled.getId(),
                    null,
                    null,
                    Constants.CommonRsponseData.ACCEPT,
                    Constants.CommonSuccessMessage.PAYMENT_CANCEL
            );
        } catch (StripeException e) {
            throw new CancelException(Constants.CommonExeption.PAYMENT_CANCEL + e.getMessage());
        }
    }

    @Override
    public SubsequentPaymentResponse refundPayment(SubsequentPaymentRequest request) {
        try {


            RefundCreateParams params = RefundCreateParams.builder()
                    .setPaymentIntent(request.referenceId())
                    .build();

            Refund refund = Refund.create(params);

            return new SubsequentPaymentResponse(
                    request.referenceId(),
                    refund.getPaymentIntent(),
                    refund.getAmount(),
                    refund.getCurrency(),
                    Constants.CommonRsponseData.ACCEPT,
                    Constants.CommonSuccessMessage.PAYMENT_REFUND
            );
        } catch (StripeException e) {
            throw new RefundException(Constants.CommonExeption.PAYMENT_REFUND + e.getMessage());
        }
    }

    @Override
    public String getProviderName() {
        return Constants.Provider.STRIPE_PROVIDER;
    }


    public FirstPaymentResponse createSubscription() {
        try {

            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setPrice(config.getPriceId()) // Price ID must be for a recurring weekly price
                                    .setQuantity(Constants.Subscription.SUBSCRIPTION_QUANTITY)
                                    .build()
                    )
                    .setSuccessUrl(Constants.StripeURL.SUCCESS_URL)
                    .setCancelUrl(Constants.StripeURL.FAILED_URL)

                    .build();

            Session session = Session.create(params);

            return new FirstPaymentResponse(
                    session.getUrl(),
                    session.getId(),
                    Constants.CommonRsponseData.ACCEPT,
                    Constants.CommonSuccessMessage.PAYMENT_DIRECT,
                    null
            );
        } catch (StripeException e) {
            throw new PaymentException(Constants.CommonExeption.PAYMENT_DIRECT + e.getMessage(), e);
        }
    }

        private SessionCreateParams buildSessionParams (FirstPaymentRequest request,boolean requiresManualCapture ) throws
        StripeException {
            Long convertedAmount = CurrencyConverter.convertAmount(request.amount(), request.currency());

            String customerID = request.id();

            if (Objects.isNull(customerID)) {

                customerID = implementNewId(request.name(), request.email());
            }


            SessionCreateParams.LineItem.PriceData.ProductData productData =
                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName(request.description() != null ? request.description() : Constants.StripeStrategy.DESCRIPTION)
                            .build();

            SessionCreateParams.LineItem.PriceData priceData =
                    SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency(request.currency().toLowerCase())
                            .setUnitAmount(convertedAmount)
                            .setProductData(productData)
                            .build();

            SessionCreateParams.LineItem lineItem =
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(request.quantity())
                            .setPriceData(priceData)
                            .build();

            SessionCreateParams.Builder paramsBuilder =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl(Constants.StripeURL.SUCCESS_URL)
                            .setCancelUrl(Constants.StripeURL.FAILED_URL)
                            .addLineItem(lineItem)
                            .setCustomer(customerID);


            SessionCreateParams.PaymentIntentData.Builder paymentIntentBuilder =
                    SessionCreateParams.PaymentIntentData.builder();

            paymentIntentBuilder.setSetupFutureUsage(SessionCreateParams.PaymentIntentData.SetupFutureUsage.OFF_SESSION);

            if (requiresManualCapture) {
                paymentIntentBuilder.setCaptureMethod(SessionCreateParams.PaymentIntentData.CaptureMethod.MANUAL);
            }

            paramsBuilder.setPaymentIntentData(paymentIntentBuilder.build());


            return paramsBuilder.build();
        }


        private String implementNewId (String name, String email ) throws StripeException {

            CustomerCreateParams customerParams = CustomerCreateParams.builder()
                    .setName(name)
                    .setEmail(email)
                    .build();

            Customer customer = Customer.create(customerParams);
            return customer.getId();


        }

    }





