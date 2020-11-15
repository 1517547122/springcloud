package com.wh.springcloud.controller;

import com.wh.springcloud.entities.CommonResult;
import com.wh.springcloud.entities.Payment;
import com.wh.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){

        int result = paymentService.create(payment);
        log.info("*******插入数据："+result+"\t");
        if(result > 0 ){
            return new CommonResult(200,"插入数据库成功"+serverPort,result);
        }else{
            return new CommonResult(444,"插入失败"+serverPort,null);
        }

    }

    @GetMapping(value="/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        log.info("*******开始查询：");
        Payment payment = paymentService.getPaymentById(id);
        log.info("*******查询数据："+payment);
        if(payment !=null ){
            return new CommonResult(200,"查询成功"+serverPort,payment);
        }else{
            return new CommonResult(444,"没有对应的记录，查询ID："+id,null);
        }
    }
    @GetMapping(value = "/payment/lb")
    public String getPayment(){
        return serverPort;
    }
}

