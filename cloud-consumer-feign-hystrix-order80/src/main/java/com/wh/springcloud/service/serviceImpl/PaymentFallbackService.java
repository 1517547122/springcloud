package com.wh.springcloud.service.serviceImpl;

import com.wh.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {

    @Override
    public String paymentInfo_Ok(Integer id) {
        return "----PaymentFallbackService fall back-payment_ok,o(╥﹏╥)o";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "----PaymentFallbackService fall paymentInfo_TimeOut,o(╥﹏╥)o";
    }


}
