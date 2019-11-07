package com.baizhi.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

/**
 * 类描述信息 ()
 *
 * @author bxy
 * @Date 2019/11/7$ 15:22$
 * @version: V_1.0.0
 */
@Configuration
public class RedisConf {

    @Bean
    public Jedis getJedis(){
        return new Jedis("192.168.150.138",6379);
    }
}
