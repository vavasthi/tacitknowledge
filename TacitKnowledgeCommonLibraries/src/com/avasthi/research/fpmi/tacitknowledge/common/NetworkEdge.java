/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.avasthi.research.fpmi.tacitknowledge.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author vavasthi
 */
public class NetworkEdge implements Serializable {

    String id;
    Long individualFrom;
    Long individualTo;
    String topic;
    Long count;
    
    public NetworkEdge(String id, Long individualFrom, Long individualTo, String topic, Long count) {
        this.id = id;
        this.individualFrom = individualFrom;
        this.individualTo = individualTo;
        this.topic = topic;
        this.count = count;
    }
    public NetworkEdge() {
        
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getIndividualFrom() {
        return individualFrom;
    }

    public void setIndividualFrom(Long individualFrom) {
        this.individualFrom = individualFrom;
    }

    public Long getIndividualTo() {
        return individualTo;
    }

    public void setIndividualTo(Long individualTo) {
        this.individualTo = individualTo;
    }

    public String getTopic() {
        return topic;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
 }
