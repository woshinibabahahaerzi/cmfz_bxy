package com.baizhi.redis;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.annotation.RedisCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;

/**
 * 类描述信息 ()
 *
 * @author bxy
 * @Date 2019/11/7$ 15:30$
 * @version: V_1.0.0
 */

@Aspect
@Configuration
public class RedisCommonCache {

    @Autowired
    private Jedis jedis;

    @Around("execution(* com.baizhi.service.*.*(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //拿到当前要执行的方法
        //判断当前方法上是否有自定义注解
        //如果有自定义注解
            //判断缓存中是否存在这个key  如果存在直接取数据
            //如果不存在  先去数据库查询返回  在缓存中存一份
        //如果没有自定义注解 直接放行
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //当前执行方法的类名
        String key = proceedingJoinPoint.getTarget().getClass().getName();
        //当前执行的方法的名字
        String methodName = method.getName();

        StringBuilder builder = new StringBuilder();
        builder.append(methodName).append(":");

        //参数
        Object[] args = proceedingJoinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            builder.append(arg);
        }
        String valueKey = builder.toString();
        System.out.println("valueKey：      "+valueKey);
        //判断当前方法上是否含有这个注解
        boolean b = method.isAnnotationPresent(RedisCache.class);
        Object result = null;
        //当前方法有这个注解
        if (b){
            if (jedis.hexists(key,valueKey)){
                String s = jedis.hget(key, valueKey);
                result = JSONObject.parse(s);
                System.out.println("缓存中存在这个key");
            }else {
                result = proceedingJoinPoint.proceed();
                jedis.hset(key,valueKey,JSONObject.toJSONString(result));
                System.out.println("缓存中不存在这个key");

            }
        }else {
            System.out.println("没有这个注解");
            result = proceedingJoinPoint.proceed();
        }
        jedis.close();
        System.out.println();
        System.out.println();
        System.out.println();

        return result;
    }


    @After("execution(* com.baizhi.service.*.*(..)) && !execution(* com.baizhi.service.*.select*(..))")
    public void after(JoinPoint joinPoint){
        String name = joinPoint.getTarget().getClass().getName();
        jedis.del(name);
        jedis.close();
    }



}
