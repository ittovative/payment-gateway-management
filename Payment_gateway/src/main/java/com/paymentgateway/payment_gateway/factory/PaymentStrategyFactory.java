package com.paymentgateway.payment_gateway.factory;

import com.paymentgateway.payment_gateway.exception.UnsupportedPaymentProvider;
import com.paymentgateway.payment_gateway.strategy.PaymentStrategy;
import com.paymentgateway.payment_gateway.util.Constants;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Factory class to manage and provide PaymentStrategy instances based on payment providers.
 * <p>
 * This class keeps a map of provider names to their corresponding payment strategy implementations.
 * It allows retrieving a strategy by provider name and listing all supported providers.
 * </p>
 */
@Component
public class PaymentStrategyFactory {


    private final Map<String, PaymentStrategy> strategies;

    /**
     * Constructs the factory by collecting available payment strategies into a map,
     * keyed by the uppercase provider name.
     *
     * @param paymentStrategies a list of all available PaymentStrategy implementations.
     */
    public PaymentStrategyFactory(List<PaymentStrategy> paymentStrategies) {
        this.strategies = paymentStrategies.stream()
                .collect(Collectors.toMap(
                        PaymentStrategy::getProviderName,
                        Function.identity()
                ));

    }

    /**
     * Retrieves the PaymentStrategy for the given provider name.
     * <p>
     * The provider name is case-insensitive and will be converted to uppercase internally.
     * If the provider is not supported, an UnsupportedPaymentProvider exception is thrown.
     * </p>
     *
     * @param provider the payment provider name (e.g., "STRIPE", "PAYPAL").
     * @return the PaymentStrategy associated with the provider.
     * @throws UnsupportedPaymentProvider if no strategy exists for the given provider.
     */
    public PaymentStrategy getStrategy(String provider) {
        PaymentStrategy strategy = strategies.get(provider.toUpperCase());
        if (Objects.isNull(strategy) ) {
            throw new UnsupportedPaymentProvider( Constants.CommonExeption.UNSUPPORTED_PROVIDER + provider);
        }
        return strategy;
    }


}
