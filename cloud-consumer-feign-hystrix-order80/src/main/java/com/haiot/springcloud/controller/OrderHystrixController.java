package com.haiot.springcloud.controller;


import com.haiot.springcloud.service.PaymentHystrixService;
import com.haiot.springcloud.utils.StringHelper;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod",commandProperties =
      {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000")})
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;

    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    //@HystrixCommand(fallbackMethod = "paymentTimeoutFallbackMethod",commandProperties =
    //       {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000")})
    @HystrixCommand
    public String paymentInfo_Timeout(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfo_Timeout(id);
        return result;
    }

    public String paymentTimeoutFallbackMethod(@PathVariable("id")Integer id)
    {
        return "我是消费者80，提供者8001系统繁忙";
    }

    //全局fallback
    public String payment_Global_FallbackMethod()
    {
        return "Global异常处理信息，请稍后再试。";
    }
}
