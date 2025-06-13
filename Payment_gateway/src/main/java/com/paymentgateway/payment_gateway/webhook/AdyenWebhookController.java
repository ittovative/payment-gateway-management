package com.paymentgateway.payment_gateway.webhook;

import com.adyen.model.notification.NotificationRequest;
import com.adyen.util.HMACValidator;
import com.paymentgateway.payment_gateway.config.AdyenConfigProperties;
import com.paymentgateway.payment_gateway.exception.HMACValidationException;
import com.paymentgateway.payment_gateway.exception.WebhookException;
import com.paymentgateway.payment_gateway.util.APIResponse;
import com.paymentgateway.payment_gateway.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.AdyenWebhook.WEBHOOK_CONTROLLER)
@Slf4j
public class AdyenWebhookController {


    private AdyenConfigProperties adyenConfigProperties;

    private final HMACValidator hmacValidator = new HMACValidator();

    public AdyenWebhookController(AdyenConfigProperties adyenConfigProperties) {
        this.adyenConfigProperties = adyenConfigProperties;
    }

    @PostMapping(Constants.AdyenWebhook.WEBHOOK_ENDPOINT)
    public ResponseEntity<APIResponse<String>> webhooks(@RequestBody String json)  {

        try {
            var notificationRequest = NotificationRequest.fromJson(json);
            var notificationRequestItem = notificationRequest.getNotificationItems().stream().findFirst();

            if (notificationRequestItem.isPresent()) {

                var item = notificationRequestItem.get();

                if (!hmacValidator.validateHMAC(item, adyenConfigProperties.getHmacKey())) {
                    throw new HMACValidationException(Constants.Adyenwebhook.HMAC_VALIDATION_FAILED);
                }
                log.info(Constants.Adyenwebhook.LOG_MESSAGE, item.getPspReference());

            }

            return ResponseEntity.ok(APIResponse.ok(Constants.CommonSuccessMessage.OK, Constants.CommonSuccessMessage.WEBHOOK));
        }
        catch (Exception e){
            throw new WebhookException(Constants.CommonExeption.WEBHOOK_FAILED + e.getMessage() );
        }


    }
}
