package com.paymentgateway.payment_gateway.strategy.impl;

import com.paymentgateway.payment_gateway.dto.*;
import com.paymentgateway.payment_gateway.exception.AuthenticationCreationException;
import com.paymentgateway.payment_gateway.exception.PaymentException;
import com.paymentgateway.payment_gateway.strategy.PaymentStrategy;
import com.paymentgateway.payment_gateway.util.Constant;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PayPalPaymentStrategy implements PaymentStrategy {

    private final APIContext apiContext;

    public PayPalPaymentStrategy(APIContext apiContext) {
        this.apiContext = apiContext;
    }

    @Override
    public PaymentResponse createDirectPayment(PaymentRequest request) {
        try {
            Payment payment = createPayment(request, Constant.PayPalConstant.SALE);
            String approvalUrl = getApprovalUrl(payment);

            return new PaymentResponse(
                    approvalUrl,
                    payment.getId(),
                    Constant.CommonRsponseData.ACCEPT,
                    Constant.CommonSuccessMessage.PAYMENT_DIRECT
            );
        } catch (PayPalRESTException e) {
            throw new PaymentException(Constant.CommonExeption.PAYMENT_DIRECT + e.getMessage() );
        }
    }

    @Override
    public PaymentResponse createAuthorizationPayment(PaymentRequest request) {
        try {
            Payment payment = createPayment(request, Constant.PayPalConstant.AUTHORIZED);
            String approvalUrl = getApprovalUrl(payment);

            return new PaymentResponse(
                    approvalUrl,
                    payment.getId(),
                    Constant.CommonRsponseData.ACCEPT,
                    Constant.CommonSuccessMessage.PAYMENT_AUTHORIZATION
            );
        } catch (PayPalRESTException e) {
            throw new AuthenticationCreationException(Constant.CommonExeption.PAYMENT_AUTHORIZATION + e.getMessage() );
        }
    }

    @Override
    public PaymentOperationResponse capturePayment(PaymentOperationRequest request) {

        // PayPal capture implementation would go here
        // This requires additional PayPal API calls for authorization capture
        throw new UnsupportedOperationException( Constant.CommonExeption.PAYMENT_CAPTURE );
    }

    @Override
    public PaymentOperationResponse cancelPayment(PaymentOperationRequest request) {
        // PayPal cancel implementation would go here
        throw new UnsupportedOperationException(Constant.CommonExeption.PAYMENT_CANCEL  );
    }

    @Override
    public PaymentOperationResponse refundPayment(PaymentOperationRequest request) {
        // PayPal refund implementation would go here
        throw new UnsupportedOperationException(Constant.CommonExeption.PAYMENT_REFUND );
    }

    @Override
    public String getProviderName() {
        return Constant.Provider.PAYPAL_PROVIDER;
    }

    private Payment createPayment(PaymentRequest request, String intent) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(request.currency());
        amount.setTotal(String.format(Constant.PayPalConstant.FORMAT, request.amount() / Constant.PayPalConstant.VALUE));

        Transaction transaction = new Transaction();
        transaction.setDescription(request.description() != null ? request.description() : Constant.PayPalConstant.DESCRIPTION);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(Constant.PayPalConstant.PAYMENT_METHOD);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(Constant.StripeURL.FAILED_URL);
        redirectUrls.setReturnUrl(Constant.StripeURL.SUCCESS_URL);

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    private String getApprovalUrl(Payment payment) {
        return payment.getLinks().stream()
                .filter(link -> Constant.PayPalConstant.APPROVAL_URL.equals(link.getRel()))
                .findFirst()
                .map(Links::getHref)
                .orElseThrow(() -> new RuntimeException(Constant.PayPalConstant.EXCEPTION_MESSAGE));
    }
}
