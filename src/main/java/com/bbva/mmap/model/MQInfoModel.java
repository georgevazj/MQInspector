package com.bbva.mmap.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorge on 17/03/2016.
 */

@XmlRootElement(name = "IntegrationMap")
@XmlType(propOrder = {"mqConnections"})
public class MQInfoModel {

    private List<MQConnectionModel> mqConnections = new ArrayList<MQConnectionModel>();

    @XmlElementWrapper(name = "MQConnections")
    @XmlElement(name="Connection")
    public List<MQConnectionModel> getMqConnections() {
        return mqConnections;
    }

    public void setMqConnections(List<MQConnectionModel> mqConnections) {
        this.mqConnections = mqConnections;
    }
}
