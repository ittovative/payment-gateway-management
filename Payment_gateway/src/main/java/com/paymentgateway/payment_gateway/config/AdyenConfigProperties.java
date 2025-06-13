package com.paymentgateway.payment_gateway.config;

import com.paymentgateway.payment_gateway.util.Constant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = Constant.AdyenConfig.ADYEN)
public class AdyenConfigProperties {

    private String apiKey;
    private String clientKey;
    private String environment;
    private String merchantAccount;
    private String returnUrl;
    private String hmacKey;
}
