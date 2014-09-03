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
public class UsenetPostMessage implements Serializable {

    String senderName;
    String senderEmail;
    String id;
    String subject;
    Date date;
    String contentType;
    int bytes;
    int lines;
    String newsgroup;

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    String inReplyTo;
    List<String> references;
    String body;

    public UsenetPostMessage(String senderName,
            String senderEmail,
            String id,
            String subject,
            Date date,
            String contentType,
            int bytes,
            int lines,
            String newsgroup,
            String inReplyTo,
            List<String> references,
            String body) {

        this.senderEmail = senderEmail;
        this.senderName = senderName;
        this.id = id;
        this.subject = subject;
        this.date = date;
        this.contentType = contentType;
        this.bytes = bytes;
        this.lines = lines;
        this.newsgroup = newsgroup;
        this.inReplyTo = inReplyTo;
        this.references = references;
        this.body = body;
    }
}
