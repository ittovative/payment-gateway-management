package com.paymentgateway.payment_gateway.config;


import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    private final StripeConfigProperties stripeConfigProperties;

    public StripeConfig(StripeConfigProperties stripeConfigProperties) {
        this.stripeConfigProperties = stripeConfigProperties;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeConfigProperties.getApiKey();
    }
}
