package com.haiot.springcloud.service;

import com.haiot.springcloud.entities.CommonResult;
import com.haiot.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

@Component
@FeignClient(value="CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {


    @GetMapping(value="/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id")Long id);

}
