package com.bbva.mmap.conf;

import com.bbva.mmap.model.MQConnectionModel;
import com.bbva.mmap.model.MQInfoModel;
import com.bbva.mmap.service.MQInfo;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.headers.MQDataException;
import com.ibm.mq.headers.pcf.PCFAgent;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jms.connection.SingleConnectionFactory;
import javax.xml.bind.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.jms.JMSException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * Created by jorge on 15/03/2016.
 */

@Configuration
@EnableScheduling
@PropertySource("file:config/MQServer.properties")
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
    @Value("${writer.output}")
    private String outputXML;

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

    //CONNECTION FACTORY A PARTIR DE LOS PARAMETROS PARA MQ
    @Bean
    public SingleConnectionFactory singleConnectionFactory() throws JMSException {
        SingleConnectionFactory singleConnectionFactory = new SingleConnectionFactory();
        singleConnectionFactory.setTargetConnectionFactory(mqQueueConnectionFactory());
        return singleConnectionFactory;
    }

    //BEANS INDEPENDIENTES PARA MQ
    @Bean
    public MQQueueManager mqQueueManagerBean() throws MQException {
        return new MQQueueManager(mqQueueManager);
    }

    @Bean
    public PCFAgent pcfAgent() throws MQException, MQDataException {
        return new PCFAgent(mqHostName,mqPort,mqChannel);
    }

    //Modelos de datos
    @Bean
    public MQConnectionModel mqConnectionModel(){
        return new MQConnectionModel();
    }

    @Bean
    public MQInfoModel mqInfoModel(){
        return new MQInfoModel();
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller(){
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(com.bbva.mmap.model.MQInfoModel.class);
        return jaxb2Marshaller;
    }

    @Bean
    public Marshaller jaxbMarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(com.bbva.mmap.model.MQInfoModel.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        return jaxbMarshaller;
    }

    //Se inyecta la ruta en mqInfo()
    @Bean
    public String getOutputXML(){
        return outputXML;
    }

    //Se obtienen las conexiones desde MQ
    @Bean
    public MQInfo mqInfo() throws MQException, MQDataException, IOException, JAXBException {
        return new MQInfo(getOutputXML(), mqQueueManagerBean(),mqInfoModel(),mqConnectionModel(),jaxbMarshaller());
    }

}
