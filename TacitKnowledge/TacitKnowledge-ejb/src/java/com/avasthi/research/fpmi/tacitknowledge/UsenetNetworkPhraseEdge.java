/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.avasthi.research.fpmi.tacitknowledge;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 *
 * @author vavasthi
 */
@NamedQueries({
    @NamedQuery(name="UsenetNetworkPhraseEdge.findBySenderReceiverDateFromDateToTopicPhrase",
                query="SELECT p FROM com.avasthi.research.fpmi.tacitknowledge.UsenetNetworkPhraseEdge p where p.from = :from and p.to = :to and p.topic = :topic and p.phrase = :phrase and dateFrom = :dateFrom and dateTo = :dateTo")
}) 
@Entity
public class UsenetNetworkPhraseEdge implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Individual from;
    @OneToOne
    private Individual to;
    private String topic;
    private String phrase;
    private Date dateFrom;
    private Date dateTo;
    private Long weight;

    public Individual getFrom() {
        return from;
    }

    public void setFrom(Individual from) {
        this.from = from;
    }

    public Individual getTo() {
        return to;
    }

    public void setTo(Individual to) {
        this.to = to;
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

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
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
        if (!(object instanceof UsenetNetworkPhraseEdge)) {
            return false;
        }
        UsenetNetworkPhraseEdge other = (UsenetNetworkPhraseEdge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.avasthi.research.fpmi.tacitknowledge.UsenetNetworkEdge[ id=" + id + " ]";
    }
    
}
