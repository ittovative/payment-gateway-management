package com.paymentgateway.payment_gateway.strategy.impl;

import com.paymentgateway.payment_gateway.dto.*;
import com.paymentgateway.payment_gateway.exception.AuthenticationCreationException;
import com.paymentgateway.payment_gateway.exception.PaymentException;
import com.paymentgateway.payment_gateway.strategy.PaymentStrategy;
import com.paymentgateway.payment_gateway.util.Constants;
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
    public FirstPaymentResponse createDirectPayment(FirstPaymentRequest request) {
        try {
            Payment payment = createPayment(request, Constants.PayPalConstant.SALE);
            String approvalUrl = getApprovalUrl(payment);

            return new FirstPaymentResponse(
                    approvalUrl,
                    payment.getId(),
                    Constants.CommonRsponseData.ACCEPT,
                    Constants.CommonSuccessMessage.PAYMENT_DIRECT
            );
        } catch (PayPalRESTException e) {
            throw new PaymentException(Constants.CommonExeption.PAYMENT_DIRECT + e.getMessage() );
        }
    }

    @Override
    public FirstPaymentResponse createAuthorizationPayment(FirstPaymentRequest request) {
        try {
            Payment payment = createPayment(request, Constants.PayPalConstant.AUTHORIZED);
            String approvalUrl = getApprovalUrl(payment);

            return new FirstPaymentResponse(
                    approvalUrl,
                    payment.getId(),
                    Constants.CommonRsponseData.ACCEPT,
                    Constants.CommonSuccessMessage.PAYMENT_AUTHORIZATION
            );
        } catch (PayPalRESTException e) {
            throw new AuthenticationCreationException(Constants.CommonExeption.PAYMENT_AUTHORIZATION + e.getMessage() );
        }
    }

    @Override
    public SubsequentPaymentResponse capturePayment(SubsequentPaymentRequest request) {

        // PayPal capture implementation would go here
        // This requires additional PayPal API calls for authorization capture
        throw new UnsupportedOperationException( Constants.CommonExeption.PAYMENT_CAPTURE );
    }

    @Override
    public SubsequentPaymentResponse cancelPayment(SubsequentPaymentRequest request) {
        // PayPal cancel implementation would go here
        throw new UnsupportedOperationException(Constants.CommonExeption.PAYMENT_CANCEL  );
    }

    @Override
    public SubsequentPaymentResponse refundPayment(SubsequentPaymentRequest request) {
        // PayPal refund implementation would go here
        throw new UnsupportedOperationException(Constants.CommonExeption.PAYMENT_REFUND );
    }

    @Override
    public String getProviderName() {
        return Constants.Provider.PAYPAL_PROVIDER;
    }

    private Payment createPayment(FirstPaymentRequest request, String intent) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(request.currency());
        amount.setTotal(String.format(Constants.PayPalConstant.FORMAT, request.amount() / Constants.PayPalConstant.VALUE));

        Transaction transaction = new Transaction();
        transaction.setDescription(request.description() != null ? request.description() : Constants.PayPalConstant.DESCRIPTION);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(Constants.PayPalConstant.PAYMENT_METHOD);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(Constants.StripeURL.FAILED_URL);
        redirectUrls.setReturnUrl(Constants.StripeURL.SUCCESS_URL);

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    private String getApprovalUrl(Payment payment) {
        return payment.getLinks().stream()
                .filter(link -> Constants.PayPalConstant.APPROVAL_URL.equals(link.getRel()))
                .findFirst()
                .map(Links::getHref)
                .orElseThrow(() -> new RuntimeException(Constants.PayPalConstant.EXCEPTION_MESSAGE));
    }
}
