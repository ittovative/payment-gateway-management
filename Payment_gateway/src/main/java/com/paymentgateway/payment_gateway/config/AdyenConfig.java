package com.paymentgateway.payment_gateway.config;

import com.adyen.Client;
import com.adyen.enums.Environment;
import com.adyen.service.checkout.ModificationsApi;
import com.adyen.service.checkout.PaymentLinksApi;
import com.adyen.service.checkout.PaymentsApi;

import com.paymentgateway.payment_gateway.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdyenConfig {

    @Bean
    public Client adyenClient(AdyenConfigProperties config) {
        Environment env = Constants.ConstantConfig.ADYEN_ENVIRONMENT.equalsIgnoreCase(config.getEnvironment())
                ? Environment.LIVE
                : Environment.TEST;

        return new Client(config.getApiKey(), env);
    }

    @Bean
    public PaymentsApi paymentsApi(Client client) {
        return new PaymentsApi(client);
    }

    @Bean
    public PaymentLinksApi paymentLinksApi(Client client) {
        return new PaymentLinksApi(client);
    }

    @Bean
    public ModificationsApi modificationsApi(Client client) {
        return new ModificationsApi(client);
    }
}
