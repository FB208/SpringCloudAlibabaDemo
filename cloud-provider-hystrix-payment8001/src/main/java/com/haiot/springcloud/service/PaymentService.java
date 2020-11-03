package com.haiot.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.haiot.springcloud.utils.StringHelper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    int timespan;
    public String paymentInfo_OK(Integer id) {
        return "线程池： " + Thread.currentThread().getName() + " paymentInfo_OK.id: " + id;
    }
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {@HystrixProperty(name=
            "execution.isolation.thread.timeoutInMilliseconds",value
            ="3000")})
    public String paymentInfo_Timeout(Integer id) {
        timespan = 2;//(int)(Math.random()*10+1);
        try {
            TimeUnit.SECONDS.sleep(timespan);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "线程池： " + Thread.currentThread().getName() + " paymentInfo_Timeout.id: " + id+"  timeout:"+timespan;
    }

    public String paymentInfo_TimeOutHandler(Integer id)
    {
        return "线程池： " + Thread.currentThread().getName() + " paymentInfo_TimeOutHandler.id: " + id+"超时  timeout:"+timespan;
    }


    /*
    * 服务熔断
    * */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled",value="true"),//是否开启断路器
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"),//时间范围
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="60")//失败率达到value%后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id")Integer id)
    {
        if (id<0) {
            throw new RuntimeException("***id 不能是负数");
        }
        String serialNumber= IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功，流水号： "+serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id")Integer id)
    {

        Map fMap = new HashMap();
        fMap.put("name", id);
        fMap.put("age", "");
        String fResult = StringHelper.Format("我的名字是${name},年龄${age}", fMap);
        return StringHelper.Format("id 不能是负数，请稍后再试，id: ${id}", id);

    }
}
