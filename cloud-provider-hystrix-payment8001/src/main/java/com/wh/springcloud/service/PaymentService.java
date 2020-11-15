package com.wh.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    /**
     * 正常访问
     * @param id
     * @return
     */
    public String paymetInfo_Ok(Integer id){
        return "线程池： "+Thread.currentThread().getName()+"  PaymentInfo_Ok,id:   "+id+"\t"+"O(∩_∩)O哈哈~";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandle",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="5000")
    })
    public String paymetInfo_TimeOut(Integer id){
        int timeNumber = 5;
 //       int age = 10/0;
        try {
            TimeUnit.MILLISECONDS.sleep(13000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "线程池： "+Thread.currentThread().getName()+"  PaymentInfo_TimeOut,id:   "+id+"\t"+"O(∩_∩)O哈哈~"+"  耗时(秒)"+timeNumber;
    }

    public String paymentInfo_TimeOutHandle(Integer id){
        return "线程池： "+Thread.currentThread().getName()+"  8001系统繁忙或者运行报错，请稍后再试,id:   "+id+"\t"+"o(╥﹏╥)o";
    }

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),// 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if(id < 0){
            throw new RuntimeException("*********id,不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号：" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id")Integer id){
        return "id 不能为负数，请稍后再试，^_^  id" + id;
    }


}
