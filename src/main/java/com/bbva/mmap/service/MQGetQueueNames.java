package com.bbva.mmap.service;

import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.constants.MQConstants;
import com.ibm.mq.headers.MQDataException;
import com.ibm.mq.headers.pcf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by jorge on 16/03/2016.
 */
public class MQGetQueueNames {

    private static final Logger logger = LoggerFactory.getLogger(MQGetQueueNames.class);
    private MQQueueManager mqQueueManager;
    private PCFAgent pcfAgent;

    public void getQueueNames() throws MQException, IOException, MQDataException {
        MQMessage[] responses;
        PCFParameter[] parameters = {
                new MQCFST(CMQC.MQCA_Q_NAME,"*"),
                new MQCFIN(CMQC.MQIA_Q_TYPE, MQConstants.MQQT_LOCAL)
        };
        MQCFH mqcfh;
        MQCFSL mqcfsl;
        responses = pcfAgent.send(CMQCFC.MQCMD_INQUIRE_Q_NAMES,parameters);
        mqcfh = new MQCFH(responses[0]);
        if(mqcfh.getReason()==0){
            mqcfsl = new MQCFSL(responses[0]);
            for (int i=0;i < mqcfsl.getStrings().length;i++){
                if (logger.isDebugEnabled()){
                    logger.debug("Queue: " + mqcfsl.getStrings()[i]);
                }
            }
        }
    }

    public void getQueueStatus() throws MQDataException, IOException {
        PCFMessageAgent pcfMessageAgent = new PCFMessageAgent(mqQueueManager);
        PCFMessage pcfMessage = new PCFMessage(CMQCFC.MQCMD_INQUIRE_Q_STATUS);
        pcfMessage.addParameter(CMQC.MQCA_Q_NAME, "*");
        pcfMessage.addParameter(CMQCFC.MQIACF_Q_STATUS_TYPE, CMQCFC.MQCACF_APPL_NAME);
        PCFMessage[] pcfResp = pcfMessageAgent.send(pcfMessage);
        for (int i = 0;i < pcfResp.length;i++){
            System.out.println(pcfResp[i].getParameter(2016).getStringValue());
            System.out.println(pcfResp[i].getParameter(3058).getStringValue());
        }
    }

    public void setMqQueueManager(MQQueueManager mqQueueManager) {
        this.mqQueueManager = mqQueueManager;
    }

    public void setPcfAgent(PCFAgent pcfAgent) {
        this.pcfAgent = pcfAgent;
    }
}
