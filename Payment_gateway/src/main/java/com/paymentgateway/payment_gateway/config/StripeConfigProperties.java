package com.paymentgateway.payment_gateway.config;

import com.paymentgateway.payment_gateway.util.Constants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = Constants.ConstantConfig.STRIPE)
public class StripeConfigProperties {

    private String apiKey;
    String priceId;

}
