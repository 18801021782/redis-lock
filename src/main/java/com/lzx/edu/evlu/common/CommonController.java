package com.lzx.edu.evlu.common;

import org.redisson.Redisson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class CommonController {

    @Autowired
    private Redisson redisson;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/stock")
    public String getStock() {

        String projectKey = "loadkey";
        String clentId = UUID.randomUUID().toString();
        try {
            Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(projectKey, clentId, 10, TimeUnit.SECONDS);//jedis.setnx()
            if(!result){
                return "error";
            }
            Integer stock = Integer.valueOf(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减成功，剩余库存：" + realStock);
            } else {
                System.out.println("扣减失败，库存不足");
            }
        }finally {
            if(stringRedisTemplate.opsForValue().get(projectKey).equalsIgnoreCase(clentId)){
                stringRedisTemplate.delete(projectKey);
            }
        }

        return "end";
    }

}
