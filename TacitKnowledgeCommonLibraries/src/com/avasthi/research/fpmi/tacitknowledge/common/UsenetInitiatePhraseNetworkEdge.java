/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author vavasthi
 */
public class UsenetInitiatePhraseNetworkEdge implements Serializable {
    private String topic;
    private int limit;
    private int offset;

    public String getTopic() {
        return topic;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
    public UsenetInitiatePhraseNetworkEdge(String topic, int limit, int offset) {
        this.topic = topic;
        this.limit = limit;
        this.offset = offset;
    }


    public UsenetInitiatePhraseNetworkEdge() {
    }

}
