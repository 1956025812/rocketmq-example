package com.yss.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @author DuXueBo
 * @since 2020-07-28 09:49
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProducerApplication.class)
@Slf4j
public class ProducerTest {

    @Resource
    private DefaultMQProducer producer;


    @Test
    public void testSendOneWay() throws RemotingException, MQClientException, InterruptedException {

        Message message = new Message("producer", "我是测试".getBytes());
        this.producer.sendOneway(message);

    }





}
