package com.example.my.task;

import com.rabbitmq.client.AMQP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.channels.Channel;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class RabbitMqListener {

    private static final Logger log = LoggerFactory.getLogger(RabbitMqListener.class);

    @RabbitListener(queues = "hello")
    public void listen0(Message message) {
        log.info("##########开始接受mq-----hello###########");
        log.info(new String(message.getBody()));
    }

    @RabbitListener(queues = "q-1")
    public void listen(Message message) {
        log.info("##########开始接受mq-----q-1 ###########");
        log.info(new String(message.getBody()));
        log.info("Receiver  : " + message);
        log.info("##########处理完成mq-----q-1 ###########");
    }

    @RabbitListener(queues ="topic.message" )
    public void receiveMessage1(String str){
        log.info("我是监听topic.message的,仅满足topic.message的过来 ， "+str);
    }


    @RabbitListener(queues ="topic.messages" )
    public void receiveMessage2(String str){
        log.info("我是监听topic.# 的,满足 topic.# 的都过来 ， "+str);
    }

    // fanout
    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(value = "fanoutQueue1"),
                    exchange = @Exchange(name = "fanout_to_exchange",type = "fanout"))
    })
    public void getMessage1(String body){
        System.out.println("消费者[1]接收到消息:" + body);
    }


    @RabbitHandler
    @RabbitListener(queues ="fanoutQueue1" )
    public void fanoutMessageConsumer(String msg, Channel channel, Message message){

        for (int i = 0 ; i < 10 ; i++) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Thread.sleep(2 * 1000);
                System.out.println(sdf.format(new Date()) + "--循环执行第" + (i + 1) + "次");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            //正确执行  手动ack
            //假设收到消息失败呢？  假定收到消息是 message 表示失败
            if("message".equalsIgnoreCase(msg)){
//                channel.basicReject(message.getMessageProperties().getDeliveryTag(),
//                        true);
                System.err.println("get fanout msg1 failed msg = "+msg);
            }else{
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
                System.out.println("get fanout msg1 success msg = "+msg);
            }

        } catch (Exception e) {
            //消费者处理出了问题，需要告诉队列信息消费失败
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(),
//                    false, false);
            System.err.println("get fanout msg1 failed msg = "+msg);
        }

    }


}
