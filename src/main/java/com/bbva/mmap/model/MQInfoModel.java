package com.bbva.mmap.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorge on 17/03/2016.
 */

@XmlRootElement(name = "MQConnections")
@XmlAccessorType(XmlAccessType.FIELD)
public class MQInfoModel {

    @XmlElement(name="connection")
    private List<MQConnectionModel> mqConnections = new ArrayList<MQConnectionModel>();


    public List<MQConnectionModel> getMqConnections() {
        return mqConnections;
    }

    public void setMqConnections(List<MQConnectionModel> mqConnections) {
        this.mqConnections = mqConnections;
    }
}
