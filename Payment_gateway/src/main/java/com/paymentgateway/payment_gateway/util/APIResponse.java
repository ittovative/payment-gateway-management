package com.paymentgateway.payment_gateway.util;

import org.springframework.http.HttpStatus;

/**
 * A generic response wrapper used to return consistent API responses.
 *
 * @param <T> the type of data returned in the response
 */
public record APIResponse<T>(
        int status,
        String message,
        T data,
        Long timestamp
) {

    /**
     * Creates a successful API response.
     *
     * @param data    the response body (can be any type)
     * @param message a message explaining the result (like "Payment completed successfully")
     * @param <T>     the type of the response data
     * @return an {@code APIResponse} object with status 200 OK and current timestamp
     */
    public static <T> APIResponse<T> ok(T data, String message) {
        return new APIResponse<>(
                HttpStatus.OK.value(),
                message,
                data,
                System.currentTimeMillis()
        );
    }

    /**
     * Creates an error API response.
     *
     * @param status  the HTTP status code (for example, 400 or 500)
     * @param message a message describing the error (like "Invalid request")
     * @param <T>     the type of the response data (set as {@code null} here)
     * @return an {@code APIResponse} object with the given error status and current timestamp
     */
    public static <T> APIResponse<T> error(int status, String message) {
        return new APIResponse<>(
                status,
                message,
                null,
                System.currentTimeMillis()
        );
    }
}