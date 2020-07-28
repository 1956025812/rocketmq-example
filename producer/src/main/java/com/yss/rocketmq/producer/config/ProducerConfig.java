package com.yss.rocketmq.producer.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 *
 * </p>
 *
 * @author DuXueBo
 * @since 2020-07-27 21:20
 */
@SpringBootConfiguration
@Slf4j
public class ProducerConfig {

    @Value("${mq.producer.open}")
    private Boolean mqProducerOpen;

    @Value("${mq.producer.groupName}")
    private String mqProducerGroupName;

    @Value("${mq.producer.namesrvAddr}")
    private String mqProducerNamesrvAddr;

    @Value("${mq.producer.topic}")
    private String mqProducerTopic;


    @Bean
    public DefaultMQProducer getRocketMqProducer() {
        if (!this.mqProducerOpen) {
            log.info("项目配置启动的时候不启动mq");
            return null;
        }

        DefaultMQProducer producer = new DefaultMQProducer(this.mqProducerGroupName);
        producer.setNamesrvAddr(this.mqProducerNamesrvAddr);

        try {
            producer.start();
            log.info("启动rocketmq成功，groupName: {}, namesvrAddr: {}", this.mqProducerGroupName, this.mqProducerNamesrvAddr);
        } catch (MQClientException e) {
            log.error("启动rocketmq失败，失败原因：{}", e.getErrorMessage(), e);
        }

        return producer;
    }


}
