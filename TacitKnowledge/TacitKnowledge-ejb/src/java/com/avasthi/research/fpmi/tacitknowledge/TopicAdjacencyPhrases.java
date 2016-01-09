/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vavasthi
 */
@Entity
@Table(name = "TopicAdjacencyPhrases")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TopicAdjacencyPhrases.findAll", query = "SELECT t FROM TopicAdjacencyPhrases t"),
    @NamedQuery(name = "TopicAdjacencyPhrases.findById", query = "SELECT t FROM TopicAdjacencyPhrases t WHERE t.id = :id"),
    @NamedQuery(name = "TopicAdjacencyPhrases.findByPhrase", query = "SELECT t FROM TopicAdjacencyPhrases t WHERE t.phrase = :phrase"),
    @NamedQuery(name = "TopicAdjacencyPhrases.findByCount", query = "SELECT t FROM TopicAdjacencyPhrases t WHERE t.count = :count")})
public class TopicAdjacencyPhrases implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "topic")
    private String topic;
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "phrase")
    private String phrase;
    @Basic(optional = false)
    @NotNull
    @Column(name = "count")
    private Double count;

    public TopicAdjacencyPhrases() {
    }

    public TopicAdjacencyPhrases(Long id) {
        this.id = id;
    }

    public TopicAdjacencyPhrases(Long id, String topic, String phrase, double phraseCount) {
        this.id = id;
        this.topic = topic;
        this.phrase = phrase;
        this.count = phraseCount;
    }

    public TopicAdjacencyPhrases(String phrase, double phraseCount) {
        this.phrase = phrase;
        this.count = phraseCount;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
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
        if (!(object instanceof TopicAdjacencyPhrases)) {
            return false;
        }
        TopicAdjacencyPhrases other = (TopicAdjacencyPhrases) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.avasthi.research.fpmi.tacitknowledge.TopicAdjacencyPhrases[ id=" + id + " ]";
    }
    
}
