package com.paymentgateway.payment_gateway.controller;

import com.paymentgateway.payment_gateway.util.Constant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.RedirectURL.STRIPE_CONTROLLER)
public class RedirectURL {


    @GetMapping(Constant.RedirectURL.SUCCESS_ENDPOINT)
    public String Success(){
        return Constant.CommonRsponseData.ACCEPT;
    }

    @GetMapping(Constant.RedirectURL.FAILURE_ENDPOINT)
    public String Failure(){
        return Constant.CommonRsponseData.FAILURE;
    }


    @GetMapping(Constant.AdyenController.ADYEN_REDIRECT)
    public String AdyenRedirect(){
        return Constant.CommonRsponseData.ACCEPT;
    }




}
