package com.bbva.mmap.model;

import org.springframework.stereotype.Service;

import javax.xml.bind.annotation.*;

/**
 * Created by jorge on 17/03/2016.
 */

@XmlRootElement(name="Connection")
@XmlType(propOrder = {"MQConnectionName","MQClientName","MQChannelName","MQQueueName"})
public class MQConnectionModel {

    private String MQQueueName;
    private String MQClientName;
    private String MQChannelName;
    private String MQConnectionName;
    private String MQUSerName;

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

    @XmlAttribute(name = "MQUserIdentifier")
    public String getMQUSerName() {
        return MQUSerName;
    }

    public void setMQUSerName(String MQUSerName) {
        this.MQUSerName = MQUSerName;
    }
}
