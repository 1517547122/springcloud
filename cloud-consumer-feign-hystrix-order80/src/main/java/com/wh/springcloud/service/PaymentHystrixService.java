package com.wh.springcloud.service;

import com.wh.springcloud.service.serviceImpl.PaymentFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value="CLOUD-PROVIDER-HYSTRIX-PAYMENT",fallback = PaymentFallbackService.class)
public interface PaymentHystrixService {
    @GetMapping(value = "/payment/hystrix/ok/{id}")
    public String paymentInfo_Ok(@PathVariable("id") Integer id);

    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id);
}
