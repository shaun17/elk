package sw.java.elk.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = MQConstant.DEFAULT_REPEAT_TRADE_QUEUE_NAME)
public class ReceiverDelayMessage {
    @Autowired
    private IMessageService messageService;
    @Autowired
    private RedisTemplate  redisTemplate;

    @RabbitHandler
    public void process(String content) {

        Object order = redisTemplate.opsForValue().get("order");
        if(order!=null){

        }else{
            messageService.print();

        }

    }
}