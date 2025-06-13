package com.paymentgateway.payment_gateway.controller;

import com.paymentgateway.payment_gateway.util.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.RedirectURL.STRIPE_CONTROLLER)
public class RedirectURL {


    @GetMapping(Constants.RedirectURL.SUCCESS_ENDPOINT)
    public String success(){
        return Constants.CommonRsponseData.ACCEPT;
    }

    @GetMapping(Constants.RedirectURL.FAILURE_ENDPOINT)
    public String failure(){
        return Constants.CommonRsponseData.FAILURE;
    }


    @GetMapping(Constants.AdyenURL.ADYEN_REDIRECT)
    public String adyenRedirect(){
        return Constants.CommonRsponseData.ACCEPT;
    }




}
