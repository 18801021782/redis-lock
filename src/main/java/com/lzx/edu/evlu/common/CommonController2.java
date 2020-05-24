package com.lzx.edu.evlu.common;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class CommonController2 {

    @Autowired
    private Redisson redisson;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/stock1")
    public String getStock() {
        String loadkey = "loadkey";
        String clentId = UUID.randomUUID().toString();
        //RLock lock = redisson.getLock(loadkey);
        try {
            //Boolean result = stringRedisTemplate.opsForValue().setIfAbsent("loadkey","mengzq");
            //stringRedisTemplate.expire("loadkey",10,TimeUnit.SECONDS);

            Boolean result = stringRedisTemplate.opsForValue().setIfAbsent("loadkey", "mengzq", 10, TimeUnit.SECONDS);//jekins.setnx("","")
            if (!result) {
                return "error";
            }

            //lock.lock(30,TimeUnit.SECONDS);

            Integer stock = Integer.valueOf(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减成功，剩余库存：" + realStock);
            } else {
                System.out.println("扣减失败，库存不足");
            }
        } finally {
            //lock.unlock();
            if (clentId.equals(stringRedisTemplate.opsForValue().get(loadkey))) {
                stringRedisTemplate.delete("loadkey");
            }
        }

        return "end";
    }

}
