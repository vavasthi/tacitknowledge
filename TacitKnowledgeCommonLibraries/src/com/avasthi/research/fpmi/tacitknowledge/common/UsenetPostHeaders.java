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
public class UsenetPostHeaders implements Serializable {

    String id;
    Long senderId;
    Date date;
    String newsgroup;
    String inReplyTo;
    List<String> references;
    List<String> topics;


    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNewsgroup() {
        return newsgroup;
    }

    public void setNewsgroup(String newsgroup) {
        this.newsgroup = newsgroup;
    }

    public String getInReplyTo() {
        return inReplyTo;
    }

    public void setInReplyTo(String inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    public List<String> getReferences() {
        return references;
    }

    public void setReferences(List<String> references) {
        this.references = references;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
    public UsenetPostHeaders() {
        
    }
    public UsenetPostHeaders(Long senderId,
            String id,
            Date date,
            String newsgroup,
            String inReplyTo,
            List<String> references,
            List<String> topics) {

        this.senderId = senderId;
        this.id = id;
        this.date = date;
        this.newsgroup = newsgroup;
        this.inReplyTo = inReplyTo;
        this.references = references;
        this.topics = topics;
    }
}
