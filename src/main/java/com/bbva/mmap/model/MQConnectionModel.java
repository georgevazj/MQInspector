package com.bbva.mmap.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by jorge on 17/03/2016.
 */

@XmlRootElement(name="Connection")
@XmlAccessorType(XmlAccessType.FIELD)
public class MQConnectionModel {

    private String MQQueueName;
    private String MQClientName;
    private String MQChannelName;
    private String MQConnectionName;

    public String getMQQueueName() {
        return MQQueueName;
    }

    public void setMQQueueName(String MQQueueName) {
        this.MQQueueName = MQQueueName;
    }

    public String getMQClientName() {
        return MQClientName;
    }

    public void setMQClientName(String MQClientName) {
        this.MQClientName = MQClientName;
    }

    public String getMQChannelName() {
        return MQChannelName;
    }

    public void setMQChannelName(String MQChannelName) {
        this.MQChannelName = MQChannelName;
    }

    public String getMQConnectionName() {
        return MQConnectionName;
    }

    public void setMQConnectionName(String MQConnectionName) {
        this.MQConnectionName = MQConnectionName;
    }

}
