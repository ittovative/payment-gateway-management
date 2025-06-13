package com.paymentgateway.payment_gateway.util;

import com.paymentgateway.payment_gateway.exception.TransactionValueException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class CurrencyConverter {


    private CurrencyConverter() {
    }

    private static final Map<String, Integer> CURRENCY_DECIMAL_PLACES = new HashMap<>();

    static {
        // Currencies with 3 decimal places
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.KWD, Constant.CurrencyDecimal.THREE_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.BHD, Constant.CurrencyDecimal.THREE_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.OMR, Constant.CurrencyDecimal.THREE_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.JOD, Constant.CurrencyDecimal.THREE_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.IQD, Constant.CurrencyDecimal.THREE_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.LYD, Constant.CurrencyDecimal.THREE_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.TND, Constant.CurrencyDecimal.THREE_DECIMALS);

        // Currencies with 0 decimal places
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.JPY, Constant.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.KRW, Constant.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.VND, Constant.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.CLP, Constant.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.ISK, Constant.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.TWD, Constant.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.PYG, Constant.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.UGX, Constant.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.RWF, Constant.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.XAF, Constant.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.XOF, Constant.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.XPF, Constant.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.BIF, Constant.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.DJF, Constant.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.GNF, Constant.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.KMF, Constant.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.MGA, Constant.CurrencyDecimal.ZERO_DECIMALS);
        CURRENCY_DECIMAL_PLACES.put(Constant.Currency.VUV, Constant.CurrencyDecimal.ZERO_DECIMALS);
    }


    public static long convertAmount(Long  amount , String currency ) {



        if (amount <= Constant.CurrencyDecimal.ZERO_DECIMALS ) {
            throw new TransactionValueException( Constant.CommonExeption.TRANSACTION_VALUE) ;
        }


        BigDecimal baseAmount = BigDecimal.valueOf(amount);

        int decimalPlaces = CURRENCY_DECIMAL_PLACES.getOrDefault(currency.toUpperCase(), Constant.CurrencyDecimal.TWO_DECIMALS);
        BigDecimal multiplier = BigDecimal.TEN.pow(decimalPlaces);


        return baseAmount.multiply(multiplier).longValue();

    }
}