package com.paymentgateway.payment_gateway.config;

import com.paymentgateway.payment_gateway.util.Constants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = Constants.AdyenConfig.ADYEN)
public class AdyenConfigProperties {

    private String apiKey;
    private String clientKey;
    private String environment;
    private String merchantAccount;
    private String returnUrl;
    private String hmacKey;
}
