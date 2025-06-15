package com.paymentgateway.payment_gateway.controller;

import com.paymentgateway.payment_gateway.util.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller class for handling redirect URLs after payment completion.
 * <p>
 * This controller handles redirect requests from payment providers like Stripe and Adyen.
 * Based on the result (success or failure), it returns a simple message.
 * </p>
 */
@RestController
@RequestMapping(Constants.RedirectURL.STRIPE_CONTROLLER)
public class RedirectURL {


    /**
     * Handles the success redirect from Stripe.
     *
     * @return a success message (e.g., "ACCEPT")
     */
    @GetMapping(Constants.RedirectURL.SUCCESS_ENDPOINT)
    public String success(){
        return Constants.CommonRsponseData.ACCEPT;
    }

    /**
     * Handles the failure redirect from Stripe.
     *
     * @return a failure message (e.g., "FAILURE")
     */
    @GetMapping(Constants.RedirectURL.FAILURE_ENDPOINT)
    public String failure(){
        return Constants.CommonRsponseData.FAILURE;
    }

    /**
     * Handles the redirect from Adyen after a payment is processed.
     *
     * @return a success message (e.g., "ACCEPT")
     */
    @GetMapping(Constants.AdyenURL.ADYEN_REDIRECT)
    public String adyenRedirect(){
        return Constants.CommonRsponseData.ACCEPT;
    }




}
