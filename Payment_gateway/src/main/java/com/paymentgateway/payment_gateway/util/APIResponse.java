package com.paymentgateway.payment_gateway.util;

import org.springframework.http.HttpStatus;

public record APIResponse<T>(
        int status,
        String message,
        T data,
        Long timestamp
) {


    public static <T> APIResponse<T> ok(T data, String message) {
        return new APIResponse<>(
                HttpStatus.OK.value(),
                message,
                data,
                System.currentTimeMillis()
        );
    }

    public static <T> APIResponse<T> error(int status, String message) {
        return new APIResponse<>(
                status,
                message,
                null,
                System.currentTimeMillis()
        );
    }
}