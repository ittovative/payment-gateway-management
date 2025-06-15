package com.paymentgateway.payment_gateway.config;

import com.paymentgateway.payment_gateway.util.Constants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * Configuration class for loading Stripe settings from the application file.
 * <p>
 * This class maps all properties under the prefix defined by
 * {@link Constants.ConstantConfig#STRIPE} (e.g. "stripe") to Java fields.
 * </p>
 *
 * Example in application.yaml or application.properties:
 * <pre>
 * stripe:
 *   apiKey: sk_test_123456789
 *   priceId: price_ABCDEF123456
 * </pre>
 */
@Data
@Configuration
@ConfigurationProperties(prefix = Constants.ConstantConfig.STRIPE)
public class StripeConfigProperties {

    private String apiKey;
    String priceId;

}
