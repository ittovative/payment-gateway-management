package com.paymentgateway.payment_gateway.config;

import com.paymentgateway.payment_gateway.util.Constants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that loads Adyen settings from application YAML file.
 * <p>
 * This class maps the values under the prefix {@code adyen} (defined in {@link Constants.ConstantConfig#ADYEN})
 * to fields like API key, client key, and environment.
 * </p>
 *
 * Example in application.yaml:
 * <pre>
 * adyen:
 *   apiKey: your_api_key
 *   clientKey: your_client_key
 *   environment: TEST
 *   merchantAccount: your_merchant_account
 *   returnUrl: https://your-site.com/return
 *   hmacKey: your_hmac_key
 * </pre>
 */
@Data
@Configuration
@ConfigurationProperties(prefix = Constants.ConstantConfig.ADYEN)
public class AdyenConfigProperties {

    private String apiKey;
    private String clientKey;
    private String environment;
    private String merchantAccount;
    private String returnUrl;
    private String hmacKey;
}
