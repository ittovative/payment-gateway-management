package com.paymentgateway.payment_gateway.webhook;

import com.paymentgateway.payment_gateway.util.Constant;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(Constant.StripeEndpoint.WEBHOOK_CONTROLLER)
public class StripeWebhookController {


    private final StripeWebhookService stripeWebhookService;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @Value("${stripe.api-key}")
    private String secretKey;

    private static final long TOLERANCE = 54000;

    public StripeWebhookController(StripeWebhookService stripeWebhookService) {
        this.stripeWebhookService = stripeWebhookService;
    }

    @PostMapping(Constant.StripeEndpoint.WEBHOOK_ENDPOINT)
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader(Constant.StripeWebhookConstant.STRIPE_SIGNATURE) String sigHeader) {

        Event event = null;

        try {
            event = Webhook.constructEvent(
                    payload,
                    sigHeader,
                    webhookSecret,
                    TOLERANCE
            );

         //   System.out.println("you got a webhook and Success ");
        }
        catch (SignatureVerificationException e)
        {
           // System.out.println("Signature verification failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(Constant.StripeWebhookConstant.INVALID_SIGNATURE_MESSAGE);
        }



        return ResponseEntity.ok().body(Constant.StripeWebhookConstant.STRIPE_WEBHOOK_SUCCESS);
    }
}