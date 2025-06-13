package com.paymentgateway.payment_gateway.webhook;

import com.paymentgateway.payment_gateway.util.Constants;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(Constants.StripeEndpoint.WEBHOOK_CONTROLLER)
@Slf4j
public class StripeWebhookController {




    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @Value("${stripe.api-key}")
    private String secretKey;


    @PostMapping(Constants.StripeEndpoint.WEBHOOK_ENDPOINT)
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader(Constants.StripeWebhookConstant.STRIPE_SIGNATURE) String sigHeader) {

        try {
            Event event = Webhook.constructEvent(
                    payload,
                    sigHeader,
                    webhookSecret,
                    Constants.Tolerance.TOLERANCE_VALUE
            );

            // this is a hard code for now and  will remove later
            // We will implement our webhook logic later
            log.info("=== PARSED EVENT DATA ===");
            log.info("Event ID: {}", event.getId());
            log.info("Event Type: {}", event.getType());
            log.info("Event Created: {}", event.getCreated());
            log.info("Event Created: {}", event.getAccount());
            log.info("========================");


        }
        catch (SignatureVerificationException e)
        {

            return ResponseEntity.badRequest().body(Constants.StripeWebhookConstant.INVALID_SIGNATURE_MESSAGE);
        }



        return ResponseEntity.ok().body(Constants.StripeWebhookConstant.STRIPE_WEBHOOK_SUCCESS);
    }
}