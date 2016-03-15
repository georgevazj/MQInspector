package com.bbva.mmap.conf;

import com.bbva.mmap.listener.MQMessageListener;
import com.bbva.mmap.service.MQMessageSender;
import com.ibm.mq.MQMessage;
import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.jms.JMSException;

/**
 * Created by jorge on 15/03/2016.
 */

@Configuration
@EnableScheduling
@PropertySource("classpath:conf/MQServer.properties")
public class ApplicationContext {

    //PARAMETROS PARA LA CONEXION CON MQ
    @Value("${mq.hostName}")
    private String mqHostName;
    @Value("${mq.port}")
    private int mqPort;
    @Value("${mq.queueManager}")
    private String mqQueueManager;
    @Value("${mq.channel}")
    private String mqChannel;
    @Value("${mq.inQueue}")
    private String mqInQueue;
    @Value("${mq.outQueue}")
    private String mqOutQueue;
    @Value("${mq.message}")
    private String mqMessage;

    //RESUELVE ${} EN @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    //SE DEFINEN LOS DATOS DE CONEXION CON MQ
    @Bean
    public MQQueueConnectionFactory mqQueueConnectionFactory() throws JMSException {
        MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
        mqQueueConnectionFactory.setHostName(mqHostName);
        mqQueueConnectionFactory.setPort(mqPort);
        mqQueueConnectionFactory.setQueueManager(mqQueueManager);
        mqQueueConnectionFactory.setChannel(mqChannel);
        mqQueueConnectionFactory.setTransportType(1);
        return mqQueueConnectionFactory;
    }

    //COLA DE SALIDA PARA MQ
    @Bean
    public MQQueue outQueue() throws JMSException {
        MQQueue mqQueue = new MQQueue(mqOutQueue);
        return mqQueue;
    }

    //COLA DE ENTRADA DESDE MQ
    @Bean
    public MQQueue inQueue() throws JMSException{
        MQQueue mqQueue = new MQQueue(mqInQueue);
        return mqQueue;
    }

    //CONNECTION FACTORY A PARTIR DE LOS PARAMETROS PARA MQ
    @Bean
    public SingleConnectionFactory singleConnectionFactory() throws JMSException {
        SingleConnectionFactory singleConnectionFactory = new SingleConnectionFactory();
        singleConnectionFactory.setTargetConnectionFactory(mqQueueConnectionFactory());
        return singleConnectionFactory;
    }

    //BEAN PARA EL ENVIO DE MENSAJES - MQOUTQUEUE
    @Bean
    public JmsTemplate outJmsTemplate() throws JMSException {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestination(outQueue());
        jmsTemplate.setConnectionFactory(singleConnectionFactory());
        return jmsTemplate;
    }

    //TEMPLATE PARA LA RECEPCION DE MENSAJES - MQINQUEUE
    @Bean
    public JmsTemplate inJmsTemplate() throws JMSException {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestination(inQueue());
        jmsTemplate.setConnectionFactory(singleConnectionFactory());
        return jmsTemplate;
    }

    //BEAN PARA EL MENSAJE DE ENVIO
    @Bean
    public String message(){
        return mqMessage;
    }

    //PRODUCTOR DE MENSAJES
    @Bean
    public MQMessageSender mqMessageSender() throws JMSException {
        MQMessageSender mqMessageSender = new MQMessageSender();
        mqMessageSender.setOutJmsTemplate(outJmsTemplate());
        mqMessageSender.setMessage(message());
        mqMessageSender.sendMessage();
        return mqMessageSender;
    }

    //LISTENER MQ
    @Bean
    public MQMessageListener mqMessageListener(){
        return new MQMessageListener();
    }

    //CONTENEDOR PARA EL LISTENER
    @Bean
    public DefaultMessageListenerContainer jmsListenerContainerFactory() throws JMSException {
        DefaultMessageListenerContainer listenerContainerFactory = new DefaultMessageListenerContainer();
        listenerContainerFactory.setConnectionFactory(singleConnectionFactory());
        listenerContainerFactory.setDestination(inQueue());
        listenerContainerFactory.setMessageListener(mqMessageListener());
        return listenerContainerFactory;
    }
}
