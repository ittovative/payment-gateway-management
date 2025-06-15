package com.paymentgateway.payment_gateway.config;


import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up Stripe API.
 * <p>
 * This class sets the Stripe secret API key after the application starts.
 * The key is read from {@link StripeConfigProperties}.
 * </p>
 */
@Configuration
public class StripeConfig {



    /**
     * Holds the Stripe configuration values like the secret API key.
     */
    private final StripeConfigProperties stripeConfigProperties;

    /**
     * Constructor to inject Stripe configuration values.
     *
     * @param stripeConfigProperties the Stripe config properties from application file
     */
    public StripeConfig(StripeConfigProperties stripeConfigProperties) {
        this.stripeConfigProperties = stripeConfigProperties;
    }

    /**
     * Initializes the Stripe API by setting the secret key.
     * This method runs automatically after the class is created.
     */
    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeConfigProperties.getApiKey();
    }
}
