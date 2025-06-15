package com.paymentgateway.payment_gateway.config;

import com.adyen.Client;
import com.adyen.enums.Environment;
import com.adyen.service.checkout.ModificationsApi;
import com.adyen.service.checkout.PaymentLinksApi;
import com.adyen.service.checkout.PaymentsApi;

import com.paymentgateway.payment_gateway.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Configuration class for setting up Adyen API clients.
 * <p>
 * This class creates and provides beans for Adyen services such as:
 * - Payments API
 * - Payment Links API
 * - Modifications API
 * </p>
 */
@Configuration
public class AdyenConfig {

    /**
     * Creates an Adyen {@link Client} using the API key and environment (LIVE or TEST)
     * provided in the {@link AdyenConfigProperties}.
     *
     * @param config the Adyen configuration properties containing the API key and environment
     * @return a configured Adyen {@code Client} instance
     */
    @Bean
    public Client adyenClient(AdyenConfigProperties config) {
        Environment env = Constants.ConstantConfig.ADYEN_ENVIRONMENT.equalsIgnoreCase(config.getEnvironment())
                ? Environment.LIVE
                : Environment.TEST;

        return new Client(config.getApiKey(), env);
    }

    /**
     * Provides the {@link PaymentsApi} bean used to initiate and manage payments via Adyen.
     *
     * @param client the Adyen client to use for API communication
     * @return a {@code PaymentsApi} instance
     */
    @Bean
    public PaymentsApi paymentsApi(Client client) {
        return new PaymentsApi(client);
    }

    /**
     * Provides the {@link PaymentLinksApi} bean used to create payment links through Adyen.
     *
     * @param client the Adyen client to use for API communication
     * @return a {@code PaymentLinksApi} instance
     */
    @Bean
    public PaymentLinksApi paymentLinksApi(Client client) {
        return new PaymentLinksApi(client);
    }

    /**
     * Provides the {@link ModificationsApi} bean used for operations like refunds or cancellations.
     *
     * @param client the Adyen client to use for API communication
     * @return a {@code ModificationsApi} instance
     */
    @Bean
    public ModificationsApi modificationsApi(Client client) {
        return new ModificationsApi(client);
    }
}
