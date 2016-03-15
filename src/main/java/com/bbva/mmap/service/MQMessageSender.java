package com.bbva.mmap.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by jorge on 15/03/2016.
 */
public class MQMessageSender {
    private static final Logger logger = LoggerFactory.getLogger(MQMessageSender.class);

    private JmsTemplate outJmsTemplate;
    private String message;

    @Scheduled(fixedRate = 5000)
    public void sendMessage(){
        outJmsTemplate.send(outJmsTemplate.getDefaultDestination(), new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                logger.info("Mensaje enviado -> " + message);
                return session.createTextMessage(message);
            }
        });
    }

    public void setOutJmsTemplate(JmsTemplate outJmsTemplate) {
        this.outJmsTemplate = outJmsTemplate;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
