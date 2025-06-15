package com.paymentgateway.payment_gateway.config;

import com.paymentgateway.payment_gateway.util.Constants;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for setting up PayPal integration.
 * <p>
 * This class loads PayPal properties and creates beans for:
 * - PayPal SDK configuration map
 * - OAuth token credential
 * - API context used to call PayPal APIs
 * </p>
 */
@Configuration
public class PayPalConfig {


    /**
     * The PayPal client ID, loaded from application properties.
     */
    @Value("${paypal.client-id}")
    private String clientId;

    /**
     * The PayPal client secret, loaded from application properties.
     */
    @Value("${paypal.client-secret}")
    private String clientSecret;

    /**
     * The PayPal mode, usually "sandbox" for testing or "live" for production.
     */
    @Value("${paypal.mode}")
    private String mode;


    /**
     * Creates the PayPal SDK configuration map.
     *
     * @return a map containing PayPal settings (for example, mode)
     */
    @Bean
    public Map<String, String> paypalSdkConfig() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put(Constants.PayPalMessage.MODE, mode);
        return configMap;
    }

    /**
     * Creates an OAuthTokenCredential using client ID, secret, and SDK config.
     *
     * @return an OAuthTokenCredential used to get access tokens
     */
    @Bean
    public OAuthTokenCredential oAuthTokenCredential() {
        return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
    }

    /**
     * Creates and configures the PayPal API context for sending API requests.
     *
     * @return an APIContext object with access token and settings
     * @throws PayPalRESTException if token retrieval fails
     */
    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());
        context.setConfigurationMap(paypalSdkConfig());
        return context;
    }


}
