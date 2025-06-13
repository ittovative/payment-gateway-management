package com.paymentgateway.payment_gateway.webhook;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.paymentgateway.payment_gateway.util.Constant;
import com.stripe.model.Event;
import org.springframework.stereotype.Service;

@Service
public class StripeWebhookService {

    public void processEvent(Event event) {
      //  System.out.println("Processing event type: " + event.getType());

        switch (event.getType()) {
            case Constant.StripeWebhookEvent.CHECKOUT_SESSION_COMPLETED :
                handleCheckoutSessionCompleted(event);
                break;
            case Constant.StripeWebhookEvent.CHARGE_SUCCEEDED:
                handleChargeSucceeded(event);
                break;
            case Constant.StripeWebhookEvent.PAYMENT_INTENT_CREATED:
                handlePaymentIntentCreated(event);
                break;
            case Constant.StripeWebhookEvent.PAYMENT_INTENT_AMOUNT_CAPTURABLE_UPDATED:
                handlePaymentIntentAmountCapturableUpdated(event);
                break;
            default:
                System.out.println(Constant.StripeWebhookEvent.UNHANDLED_EVENT+ event.getType());
        }
    }

    private void handleCheckoutSessionCompleted(Event event) {
        JsonObject sessionObject = JsonParser.parseString(event.getData().getObject().toJson()).getAsJsonObject();
        String sessionId = sessionObject.get("id").getAsString();
      //  System.out.println("✅ Checkout Session Completed with ID: " + sessionId);
    }

    private void handleChargeSucceeded(Event event) {
        JsonObject chargeObject = JsonParser.parseString(event.getData().getObject().toJson()).getAsJsonObject();
        String chargeId = chargeObject.get("id").getAsString();
        long amount = chargeObject.get("amount").getAsLong();
    //    System.out.println("💰 Charge Succeeded - ID: " + chargeId + ", Amount: " + amount);
    }

    private void handlePaymentIntentCreated(Event event) {
        JsonObject paymentIntentObject = JsonParser.parseString(event.getData().getObject().toJson()).getAsJsonObject();
        String paymentIntentId = paymentIntentObject.get("id").getAsString();
      //  System.out.println("🎯 Payment Intent Created - ID: " + paymentIntentId);
    }

    private void handlePaymentIntentAmountCapturableUpdated(Event event) {
        JsonObject paymentIntentObject = JsonParser.parseString(event.getData().getObject().toJson()).getAsJsonObject();
        String paymentIntentId = paymentIntentObject.get("id").getAsString();
      //  System.out.println("📊 Payment Intent Amount Capturable Updated - ID: " + paymentIntentId);
    }
}