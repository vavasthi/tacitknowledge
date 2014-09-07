/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.avasthi.research.fpmi.tacitknowledge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vavasthi
 */
@Entity
@XmlRootElement
public class UsenetPost implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @OneToOne
    private Individual sender;
    private String subject;
    private Date date;
    private String contentType;
    private int bytes;
    private int noLines;
    private String newsGroup;
    private String inReplyTo;
    
    @OneToMany
    private List<UsenetPostReference> referencedPostIds = new ArrayList<UsenetPostReference>();
    
    @Lob @Basic(fetch = FetchType.EAGER)
    String body;

    public Individual getSender() {
        return sender;
    }

    public void setSender(Individual sender) {
        this.sender = sender;
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

    public int getNoLines() {
        return noLines;
    }

    public void setNoLines(int noLines) {
        this.noLines = noLines;
    }

    public String getNewsGroup() {
        return newsGroup;
    }

    public void setNewsGroup(String newsGroup) {
        this.newsGroup = newsGroup;
    }

    public String getInReplyTo() {
        return inReplyTo;
    }

    public void setInReplyTo(String inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    public List<UsenetPostReference> getReferencedPosts() {
        return referencedPostIds;
    }

    public void setReferencedPosts(List<UsenetPostReference> referencedPosts) {
        this.referencedPostIds = referencedPosts;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsenetPost)) {
            return false;
        }
        UsenetPost other = (UsenetPost) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.avasthi.research.fpmi.tacitknowledge.UsenetPost[ id=" + id + " ]";
    }
    
}
