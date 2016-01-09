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
@Table(name = "TacitKnowledgeTopicPhrase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = TacitKnowledgeTopicPhrase.findAll, query = "SELECT t FROM TacitKnowledgeTopicPhrase t order by t.count DESC"),
    @NamedQuery(name = TacitKnowledgeTopicPhrase.findById, query = "SELECT t FROM TacitKnowledgeTopicPhrase t WHERE t.id = :id order by t.count DESC"),
    @NamedQuery(name = TacitKnowledgeTopicPhrase.findByTopic, query = "SELECT t FROM TacitKnowledgeTopicPhrase t WHERE t.topic = :topic order by t.count DESC"),
    @NamedQuery(name = TacitKnowledgeTopicPhrase.findByPhrase, query = "SELECT t FROM TacitKnowledgeTopicPhrase t WHERE t.phrase = :phrase order by t.count DESC"),
    @NamedQuery(name = TacitKnowledgeTopicPhrase.findByCount, query = "SELECT t FROM TacitKnowledgeTopicPhrase t WHERE t.count = :count order by t.count DESC"),
    @NamedQuery(name = TacitKnowledgeTopicPhrase.findByTopicAndId, query = "SELECT t FROM TacitKnowledgeTopicPhrase t WHERE t.topic = :topic and t.id = :id order by t.count DESC"),
    @NamedQuery(name = TacitKnowledgeTopicPhrase.findCountForTopic, query = "SELECT sum(t.count) FROM TacitKnowledgeTopicPhrase t WHERE t.topic = :topic group by t.topic order by t.count DESC"),
    @NamedQuery(name = TacitKnowledgeTopicPhrase.findByTopicAndPhrase, query = "SELECT t FROM TacitKnowledgeTopicPhrase t WHERE t.topic = :topic and t.phrase = :phrase group by t.topic, phrase order by t.count DESC"),
    @NamedQuery(name = TacitKnowledgeTopicPhrase.findCountForPhrase, query = "SELECT sum(t.count) FROM TacitKnowledgeTopicPhrase t WHERE t.phrase = :phrase group by t.phrase order by t.count DESC")
})
public class TacitKnowledgeTopicPhrase implements Serializable {
    public final static String findAll = "TacitKnowledgeTopicPhrase.findAll";
    public final static String findById = "TacitKnowledgeTopicPhrase.findById";
    public final static String findByTopic = "TacitKnowledgeTopicPhrase.findByTopic";
    public final static String findByPhrase = "TacitKnowledgeTopicPhrase.findByPhrase";
    public final static String findByCount = "TacitKnowledgeTopicPhrase.findByCount";
    public final static String findByTopicAndId = "TacitKnowledgeTopicPhrase.findByTopicAndId";
    public final static String findCountForTopic = "TacitKnowledgeTopicPhrase.findCountForTopic";
    public final static String findByTopicAndPhrase = "TacitKnowledgeTopicPhrase.findByTopicAndPhrase";
    public final static String findCountForPhrase = "TacitKnowledgeTopicPhrase.findCountForPhrase";
    
    
    
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "phrase")
    private String phrase;
    @Basic(optional = false)
    @NotNull
    @Column(name = "count")
    private long count;

    public TacitKnowledgeTopicPhrase() {
    }

    public TacitKnowledgeTopicPhrase(Long id) {
        this.id = id;
    }

    public TacitKnowledgeTopicPhrase(Long id, String topic, String phrase, long count) {
        this.id = id;
        this.topic = topic;
        this.phrase = phrase;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
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
        if (!(object instanceof TacitKnowledgeTopicPhrase)) {
            return false;
        }
        TacitKnowledgeTopicPhrase other = (TacitKnowledgeTopicPhrase) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.avasthi.research.fpmi.tacitknowledge.TacitKnowledgeTopicPhrase[ id=" + id + " ]";
    }
    
}
