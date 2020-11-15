package com.wh.springcloud.service.impl;

import com.wh.springcloud.dao.PaymentDao;
import com.wh.springcloud.entities.Payment;
import com.wh.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private PaymentDao paymentDao;

    public int create(Payment payment){
        return paymentDao.create(payment);
    }

    public Payment getPaymentById(Long id){
        return paymentDao.getPaymentById(id);
    }
}
