package com.paymentgateway.payment_gateway.dto;

/**
 * This class is used to send a request for creating a subscription.
 * <p>
 * It includes basic user information like name, email, and an optional customer ID
 * (which may already exist in the payment provider system).
 * </p>
 *
 * Example:
 * <pre>
 * {
 *   "name": "Jane Doe",
 *   "email": "jane@example.com",
 *   "CustomerId": "cus_987654321"
 * }
 * </pre>
 *
 * @param name        The full name of the customer (used for records or invoices)
 * @param email       The email address of the customer (used for receipts and notifications)
 * @param CustomerId  The ID of the customer from Stripe or another payment service (optional)
 */
public record SubscriptionRequest(
        String name ,
        String email ,
        String CustomerId
) {}
