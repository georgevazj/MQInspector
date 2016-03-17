package com.bbva.mmap.service;

import com.bbva.mmap.model.MQConnectionModel;
import com.bbva.mmap.model.MQInfoModel;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.headers.MQDataException;
import com.ibm.mq.headers.pcf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * Created by jorge on 16/03/2016.
 */

public class MQInfo {
    private static final Logger logger = LoggerFactory.getLogger(MQInfo.class);

    @Autowired
    public MQInfo(MQQueueManager mqQueueManager,MQInfoModel mqInfoModel,MQConnectionModel mqConnectionModel) throws MQDataException, IOException {
        PCFMessageAgent pcfMessageAgent = new PCFMessageAgent(mqQueueManager);
        PCFMessage pcfMessage = new PCFMessage(CMQCFC.MQCMD_INQUIRE_Q_STATUS);
        pcfMessage.addParameter(CMQC.MQCA_Q_NAME, "*");
        pcfMessage.addParameter(CMQCFC.MQIACF_Q_STATUS_TYPE, CMQCFC.MQCACF_APPL_NAME);
        PCFMessage[] pcfResp = pcfMessageAgent.send(pcfMessage);
        for (int i = 0;i < pcfResp.length;i++){
            mqConnectionModel.setMQQueueName(pcfResp[i].getParameter(2016).getStringValue());
            mqConnectionModel.setMQClientName(pcfResp[i].getParameter(3058).getStringValue());
            mqConnectionModel.setMQChannelName(pcfResp[i].getParameter(3501).getStringValue());
            mqConnectionModel.setMQConnectionName(pcfResp[i].getParameter(3506).getStringValue());
            mqInfoModel.getMqConnections().add(mqConnectionModel);
        }
    }
}