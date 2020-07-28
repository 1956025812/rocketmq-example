package com.yss.rocketmq.consumer.config;

import com.yss.rocketmq.consumer.listener.MqListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author DuXueBo
 * @since 2020-07-27 21:44
 */
@SpringBootConfiguration
@Slf4j
public class ConsumerConfig {

    @Value("${mq.consumer.open}")
    private Boolean mqConsumerOpen;

    @Value("${mq.consumer.groupName}")
    private String mqConsumerGroupName;

    @Value("${mq.consumer.namesrvAddr}")
    private String mqConsumerNamesrvAddr;

    @Value("#{'${mq.consumer.subscribeTopics}'.split(',')}")
    private List<String> mqConsumerSubscribeTopics;

    @Resource
    private MqListener mqListener;


    @Bean
    public DefaultMQPushConsumer getPushConsumer() {

        if (!this.mqConsumerOpen) {
            log.info("配置项目启动的时候不启动mq");
            return null;
        }

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(this.mqConsumerGroupName);
        consumer.setNamesrvAddr(this.mqConsumerNamesrvAddr);

        this.mqConsumerSubscribeTopics.forEach(eachSubscribeTopic -> {
            try {
                consumer.subscribe(eachSubscribeTopic, "*");
                consumer.registerMessageListener(mqListener);
                consumer.start();
                log.info("启动mqConsumer成功，groupName: {}， namesvrAddr: {}", this.mqConsumerGroupName, this.mqConsumerNamesrvAddr);
            } catch (MQClientException e) {
                log.error("启动mqConsumer成功，groupName: {}， namesvrAddr: {}", this.mqConsumerGroupName, this.mqConsumerNamesrvAddr);
                e.printStackTrace();
            }
        });

        return consumer;
    }


}
