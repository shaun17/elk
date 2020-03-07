package sw.java.elk;


import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import sw.java.elk.rabbit.IMessageService;
import sw.java.elk.rabbit.MQConstant;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@MapperScan("sw.java.elk.dao")
@ComponentScan(basePackages = {"sw.java.elk.*"})
public class EklApplication implements CommandLineRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(EklApplication.class, args);

    }
    @Autowired
    IMessageService messageService;
    @Autowired
    RedisTemplate redisTemplate;

//    @Override
    public void run(String... args) throws Exception {
        Logger logger = LoggerFactory.getLogger(EklApplication.class);
        logger.info("开始模拟提交订单");
        redisTemplate.opsForValue().set("order","1234567",20, TimeUnit.SECONDS);

        logger.info("订单完成了，只有20s的时间去支付");
        messageService.send(MQConstant.ORDER_QUEUE_NAME,"order",20000);
        messageService.send(MQConstant.ORDER_QUEUE_NAME,"order");
    }
}
