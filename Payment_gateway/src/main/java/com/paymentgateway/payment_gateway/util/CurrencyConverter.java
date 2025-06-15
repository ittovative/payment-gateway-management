package com.paymentgateway.payment_gateway.util;

import com.paymentgateway.payment_gateway.exception.TransactionValueException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public final class CurrencyConverter {


    private CurrencyConverter() {
    }

    private static final Map<String, Integer> CURRENCY_DECIMAL_PLACES = new HashMap<>();

    static {
        // Currencies with 3 decimal places
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.KWD, Constants.CurrencyDecimal.THREE_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.BHD, Constants.CurrencyDecimal.THREE_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.OMR, Constants.CurrencyDecimal.THREE_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.JOD, Constants.CurrencyDecimal.THREE_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.IQD, Constants.CurrencyDecimal.THREE_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.LYD, Constants.CurrencyDecimal.THREE_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.TND, Constants.CurrencyDecimal.THREE_DECIMALS);

        // Currencies with 0 decimal places
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.JPY, Constants.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.KRW, Constants.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.VND, Constants.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.CLP, Constants.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.ISK, Constants.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.TWD, Constants.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.PYG, Constants.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.UGX, Constants.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.RWF, Constants.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.XAF, Constants.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.XOF, Constants.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.XPF, Constants.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.BIF, Constants.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.DJF, Constants.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.GNF, Constants.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.KMF, Constants.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.MGA, Constants.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constants.Currency.VUV, Constants.CurrencyDecimal.ZERO_DECIMALS);
    }


    public static long convertAmount(Long  amount , String currency ) {



        if (amount <= Constants.CurrencyDecimal.ZERO_DECIMALS ) {
            throw new TransactionValueException( Constants.CommonExeption.TRANSACTION_VALUE) ;
        }


        BigDecimal baseAmount = BigDecimal.valueOf(amount);

        int decimalPlaces = CURRENCY_DECIMAL_PLACES.getOrDefault(currency.toUpperCase(), Constants.CurrencyDecimal.TWO_DECIMALS);
        BigDecimal multiplier = BigDecimal.TEN.pow(decimalPlaces);


        return baseAmount.multiply(multiplier).longValue();

    }
}