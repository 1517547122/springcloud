package com.wh.springcloud.controller;

import com.wh.lb.LoadBalanced;
import com.wh.springcloud.entities.CommonResult;
import com.wh.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderContoller {
//    public static final String PAYMENT_URL = "http://localhost:8001";
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalanced loadBalanced;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        log.info("开始创建");
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }
    //getForObject返回对象为响应体中数据转换的对象，基本可以理解为json
    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    /**
     * getForEntity返回对象为ResponseEntity对象，包含了响应中的一些重要信息，比如响应头，响应状态码，响应体等
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id")Long id){
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/"+id, CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful()){
            log.info("响应码",entity.getStatusCode()+"\t");
            return entity.getBody();
        }else{
            return new CommonResult<>(444,"操作失败");
        }
    }
    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentLB(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(instances == null || instances.size() <=0){
            return null;
        }
        ServiceInstance serviceInstance = loadBalanced.instance(instances);
        URI uri = serviceInstance.getUri();

        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }
}
