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
public class UsenetNetworkEdgeMessage implements Serializable {

    Long id;
    Long individualFrom;
    Long individualTo;
    String topic;
    Date dateFrom;
    Date dateTo;
    
    public UsenetNetworkEdgeMessage(Long id, Long individualFrom, Long individualTo, String topic, Date dateFrom, Date dateTo) {
        this.id = id;
        this.individualFrom = individualFrom;
        this.individualTo = individualTo;
        this.topic = topic;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
    public UsenetNetworkEdgeMessage() {
        
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
 }
