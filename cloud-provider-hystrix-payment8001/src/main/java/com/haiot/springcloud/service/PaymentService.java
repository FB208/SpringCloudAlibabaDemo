package com.haiot.springcloud.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id) {
        return "线程池： " + Thread.currentThread().getName() + " paymentInfo_OK.id: " + id;
    }

    public String paymentInfo_Timeout(Integer id) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "线程池： " + Thread.currentThread().getName() + " paymentInfo_Timeout.id: " + id;
    }
}
