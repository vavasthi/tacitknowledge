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
public class NetworkPhraseEdge implements Serializable {

    String id;
    Long individualFrom;
    Long individualTo;
    String topic;
    String phrase;
    Long count;
    
    public NetworkPhraseEdge(String id, Long individualFrom, Long individualTo, String topic, String phrase, Long count) {
        this.id = id;
        this.individualFrom = individualFrom;
        this.individualTo = individualTo;
        this.topic = topic;
        this.phrase = phrase;
        this.count = count;
    }
    public NetworkPhraseEdge() {
        
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

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
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
