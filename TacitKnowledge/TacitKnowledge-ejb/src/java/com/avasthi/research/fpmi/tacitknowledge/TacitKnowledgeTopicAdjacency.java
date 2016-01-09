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
@Table(name = "TacitKnowledgeTopicAdjacency")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = TacitKnowledgeTopicAdjacency.findAll, query = "SELECT t FROM TacitKnowledgeTopicAdjacency t"),
    @NamedQuery(name = TacitKnowledgeTopicAdjacency.findById, query = "SELECT t FROM TacitKnowledgeTopicAdjacency t WHERE t.id = :id"),
    @NamedQuery(name = TacitKnowledgeTopicAdjacency.findByTopic, query = "SELECT t FROM TacitKnowledgeTopicAdjacency t WHERE t.topic = :topic"),
    @NamedQuery(name = TacitKnowledgeTopicAdjacency.findByFirstPhraseId, query = "SELECT t FROM TacitKnowledgeTopicAdjacency t WHERE t.firstPhraseId = :firstPhraseId"),
    @NamedQuery(name = TacitKnowledgeTopicAdjacency.findBySecondPhraseId, query = "SELECT t FROM TacitKnowledgeTopicAdjacency t WHERE t.secondPhraseId = :secondPhraseId"),
    @NamedQuery(name = TacitKnowledgeTopicAdjacency.findByCount, query = "SELECT t FROM TacitKnowledgeTopicAdjacency t WHERE t.count = :count"),
    @NamedQuery(name = TacitKnowledgeTopicAdjacency.findByTopicFirstPhraseId, query = "SELECT t FROM TacitKnowledgeTopicAdjacency t WHERE t.topic = :topic and t.firstPhraseId = :firstPhraseId order by t.count DESC"),
    @NamedQuery(name = TacitKnowledgeTopicAdjacency.findByTopicSecondPhraseId, query = "SELECT t FROM TacitKnowledgeTopicAdjacency t WHERE t.topic = :topic and t.secondPhraseId = :secondPhraseId order by t.count DESC"),
    @NamedQuery(name = TacitKnowledgeTopicAdjacency.findByTopicFirstOrSecondPhraseId, query = "SELECT t FROM TacitKnowledgeTopicAdjacency t WHERE t.topic = :topic and ( t.firstPhraseId = :phraseId or t.secondPhraseId = :phraseId) order by t.count DESC")
})
public class TacitKnowledgeTopicAdjacency implements Serializable {
    
    public final static String findAll = "TacitKnowledgeTopicAdjacency.findAll";
    public final static String findById = "TacitKnowledgeTopicAdjacency.findById";
    public final static String findByTopic = "TacitKnowledgeTopicAdjacency.findByTopic";
    public final static String findByFirstPhraseId = "TacitKnowledgeTopicAdjacency.findByFirstPhraseId";
    public final static String findBySecondPhraseId = "TacitKnowledgeTopicAdjacency.findBySecondPhraseId";
    public final static String findByCount = "TacitKnowledgeTopicAdjacency.findByCount";
    public final static String findByTopicFirstPhraseId = "TacitKnowledgeTopicAdjacency.findByTopicFirstPhraseId";
    public final static String findByTopicSecondPhraseId = "TacitKnowledgeTopicAdjacency.findByTopicSecondPhraseId";
    public final static String findByTopicFirstOrSecondPhraseId = "TacitKnowledgeTopicAdjacency.findByTopicFirstOrSecondPhraseId";
    
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
    @Column(name = "firstPhraseId")
    private long firstPhraseId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "secondPhraseId")
    private long secondPhraseId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "count")
    private long count;

    public TacitKnowledgeTopicAdjacency() {
    }

    public TacitKnowledgeTopicAdjacency(Long id) {
        this.id = id;
    }

    public TacitKnowledgeTopicAdjacency(Long id, String topic, long firstPhraseId, long secondPhraseId, long count) {
        this.id = id;
        this.topic = topic;
        this.firstPhraseId = firstPhraseId;
        this.secondPhraseId = secondPhraseId;
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

    public long getFirstPhraseId() {
        return firstPhraseId;
    }

    public void setFirstPhraseId(long firstPhraseId) {
        this.firstPhraseId = firstPhraseId;
    }

    public long getSecondPhraseId() {
        return secondPhraseId;
    }

    public void setSecondPhraseId(long secondPhraseId) {
        this.secondPhraseId = secondPhraseId;
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
        if (!(object instanceof TacitKnowledgeTopicAdjacency)) {
            return false;
        }
        TacitKnowledgeTopicAdjacency other = (TacitKnowledgeTopicAdjacency) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.avasthi.research.fpmi.tacitknowledge.TacitKnowledgeTopicAdjacency[ id=" + id + " ]";
    }
    
}
