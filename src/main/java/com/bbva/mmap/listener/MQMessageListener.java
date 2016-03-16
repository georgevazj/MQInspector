package com.bbva.mmap.listener;


import com.bbva.mmap.service.MQMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by jorge on 15/03/2016.
 */
public class MQMessageListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(MQMessageListener.class);

    public MQMessageListener(){
        logger.info("Listening...");
    }

    @Override
    public void onMessage(Message message) {
        logger.info("Mensaje recibido");
        logger.info(message.toString());
    }
}
