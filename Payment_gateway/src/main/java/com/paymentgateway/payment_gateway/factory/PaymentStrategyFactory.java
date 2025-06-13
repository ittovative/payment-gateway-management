package com.paymentgateway.payment_gateway.factory;

import com.paymentgateway.payment_gateway.exception.UnsupportPaymentProvider;
import com.paymentgateway.payment_gateway.strategy.PaymentStrategy;
import com.paymentgateway.payment_gateway.util.Constants;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PaymentStrategyFactory {


    private final Map<String, PaymentStrategy> strategies;

    public PaymentStrategyFactory(List<PaymentStrategy> paymentStrategies) {
        this.strategies = paymentStrategies.stream()
                .collect(Collectors.toMap(
                        PaymentStrategy::getProviderName,
                        Function.identity()
                ));

    }

    public PaymentStrategy getStrategy(String provider) {
        PaymentStrategy strategy = strategies.get(provider.toUpperCase());
        if (Objects.isNull(strategy) ) {
            throw new UnsupportPaymentProvider( Constants.CommonExeption.UNSUPPORTED_PROVIDER + provider);
        }
        return strategy;
    }

    public List<String> getSupportedProviders() {
        return List.copyOf(strategies.keySet());
    }
}
