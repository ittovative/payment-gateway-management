package com.paymentgateway.payment_gateway.util;

public final class Constant {

    private Constant() {
    }

  public final class PaymentController {
        private PaymentController() {}

      public static final String PAYMENT = "/api/v1/payments";
      public static final String PAYMENT_CAPTURE = "/capture";
      public static final String PAYMENT_AUTHORIZATION = "/authorization" ;
      public static final String PAYMENT_DIRECT = "/direct";
      public static final String PAYMENT_CANCEL = "/cancel";
      public static final String PAYMENT_REFUND = "/refund";

  }

  public final class CommonExeption {
        private CommonExeption() {}

      public static final String PAYMENT_CAPTURE = "Failed to capture payment: ";
      public static final String PAYMENT_AUTHORIZATION = "Failed to create authorization session or get redirect URL" ;
      public static final String PAYMENT_DIRECT = "Failed to create payment session or get redirect URL";
      public static final String PAYMENT_CANCEL = "Failed to cancel authorization: ";
      public static final String PAYMENT_REFUND = "Failed to refund payment: ";
      public static final String WEBHOOK_FAILED = "Failed to process webhook notification: " ;
      public static final String UNSUPPORTED_PROVIDER = "Unsupported payment provider: "  ;
  }

  public final class PayPalMessage{
        private PayPalMessage() {}
      public static final String MODE = "mode" ;
  }

  public final class CommonSuccessMessage {

        private CommonSuccessMessage() {}
      public static final String PAYMENT_CAPTURE = "Payment captured successfully" ;
      public static final String PAYMENT_AUTHORIZATION ="Authorization session created successfully";
      public static final String PAYMENT_DIRECT ="Payment session created successfully";
      public static final String PAYMENT_CANCEL ="Payment cancelled successfully";
      public static final String PAYMENT_REFUND ="Payment refunded successfully" ;
      public static final String WEBHOOK = "Webhook processed successfully";
      public static final String OK = "OK" ;

  }

  public final class Provider {
        private Provider() {}

        public static final String ADYEN_PROVIDER = "ADYEN";
        public static final String PAYPAL_PROVIDER  = "PAYPAL";
        public static final String STRIPE_PROVIDER  = "STRIPE";
  }



  public final class CommonRsponseData {
        private CommonRsponseData() {}

      public static final String  ACCEPT = "SUCCESS" ;
      public static final String  FAILURE = "Failure" ;
  }

  public final class StripeStrategy{
        private StripeStrategy() {}

      public static final int VALUE = 100 ;
        public static final String DESCRIPTION = "description" ;

  }


  public final class RedirectURL{
        private RedirectURL() {}
      public static final String  SUCCESS_ENDPOINT  = "/success" ;
      public static final String  FAILURE_ENDPOINT  = "/failure" ;
      public static final String  STRIPE_CONTROLLER  = "api/v1";
  }


  public final class StripeEndpoint {
         private StripeEndpoint() {}


      public static final String WEBHOOK_CONTROLLER = "/stripe" ;
      public static final String WEBHOOK_ENDPOINT= "/webhook" ;
  }

  public final class StripeURL {
        private StripeURL() {}

      public static final String SUCCESS_URL = "http://localhost:8080/api/v1/success";
      public static final String FAILED_URL = "http://localhost:8080/api/v1/cancel";
  }
    public final class AdyenURL{
        private AdyenURL() {}
        public static final String Redirect = "http://localhost:8080/api/v1/handle-redirect";
    }

  public final class StripeWebhookConstant{
        private StripeWebhookConstant() {}

    public static final String STRIPE_SIGNATURE = "Stripe-Signature" ;
    public static final String INVALID_SIGNATURE_MESSAGE = "Invalid signature" ;
    public static final String STRIPE_WEBHOOK_SUCCESS = "Webhook processed successfully" ;
  }


  public final class StripeWebhookEvent {

        private StripeWebhookEvent() {}

      public static final String CHECKOUT_SESSION_COMPLETED = "checkout.session.completed";
      public static final String CHARGE_SUCCEEDED = "charge.succeeded";
      public static final String PAYMENT_INTENT_CREATED = "payment_intent.created";
      public static final String PAYMENT_INTENT_AMOUNT_CAPTURABLE_UPDATED = "payment_intent.amount_capturable_updated";
      public static final String UNHANDLED_EVENT = "Unhandled event type: " ;
  }



  public final class AdyenController{
        private AdyenController() {}

      public static final String ADYEN_REDIRECT = "/handle-redirect";
      public static final String WEBHOOK_CONTROLLER = "/api/v1/webhooks" ;
      public static final String WEBHOOK_ENDPOINT= "/notifications";
  }

  public final class AdyenConfig {
        private AdyenConfig() {}
      public static final String ADYEN = "adyen" ;
        public static final String ADYEN_ENVIRONMENT= "live";
  }

  public final class Adyenwebhook {
        private Adyenwebhook() {}
      public static final String HMAC_VALIDATION_FAILED = "HMAC validation failed for notification" ;
      public static final String LOG_MESSAGE= "HMAC validation passed and the PSPReference is : {}";
  }

  public final class AdyenService{
        private AdyenService() {}

      public static final int VALUE = 100 ;
      public static final String PREFIX_UUID= "EasyOrder" ;
      public static final int CAPTURE_DELAY_HOURS = 0 ;

  }

  public final class PayPalConstant{
        private PayPalConstant() {}

      public static final String DESCRIPTION = "Payment" ;
      public static final String  FORMAT = "%.2f" ;
      public static final String  PAYMENT_METHOD = "PayPal" ;
      public static final String  APPROVAL_URL = "approval_url" ;
      public static final String  EXCEPTION_MESSAGE ="No approval URL found" ;
      public static final int VALUE = 100 ;
      public static final String AUTHORIZED = "authorize";
      public static final String SALE = "sale";

  }

}
