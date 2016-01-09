/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
@Table(name = "TacitKnowledgePeriodicTopicAdjacency")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = TacitKnowledgePeriodicTopicAdjacency.findAll, query = "SELECT t FROM TacitKnowledgePeriodicTopicAdjacency t"),
    @NamedQuery(name = TacitKnowledgePeriodicTopicAdjacency.findById, query = "SELECT t FROM TacitKnowledgePeriodicTopicAdjacency t WHERE t.id = :id"),
    @NamedQuery(name = TacitKnowledgePeriodicTopicAdjacency.findByTopic, query = "SELECT t FROM TacitKnowledgePeriodicTopicAdjacency t WHERE t.topic = :topic"),
    @NamedQuery(name = TacitKnowledgePeriodicTopicAdjacency.findByFirstPhraseId, query = "SELECT t FROM TacitKnowledgePeriodicTopicAdjacency t WHERE t.firstPhraseId = :firstPhraseId"),
    @NamedQuery(name = TacitKnowledgePeriodicTopicAdjacency.findBySecondPhraseId, query = "SELECT t FROM TacitKnowledgePeriodicTopicAdjacency t WHERE t.secondPhraseId = :secondPhraseId"),
    @NamedQuery(name = TacitKnowledgePeriodicTopicAdjacency.findByCount, query = "SELECT t FROM TacitKnowledgePeriodicTopicAdjacency t WHERE t.count = :count"),
    @NamedQuery(name = TacitKnowledgePeriodicTopicAdjacency.findByTopicFirstPhraseId, query = "SELECT t FROM TacitKnowledgePeriodicTopicAdjacency t WHERE t.topic = :topic and t.firstPhraseId = :firstPhraseId order by t.count DESC"),
    @NamedQuery(name = TacitKnowledgePeriodicTopicAdjacency.findByTopicSecondPhraseId, query = "SELECT t FROM TacitKnowledgePeriodicTopicAdjacency t WHERE t.topic = :topic and t.secondPhraseId = :secondPhraseId order by t.count DESC"),
    @NamedQuery(name = TacitKnowledgePeriodicTopicAdjacency.findByTopicFirstOrSecondPhraseId, query = "SELECT t FROM TacitKnowledgePeriodicTopicAdjacency t WHERE t.topic = :topic and ( t.firstPhraseId = :phraseId or t.secondPhraseId = :phraseId) order by t.count DESC")
})
public class TacitKnowledgePeriodicTopicAdjacency implements Serializable {
    
    public final static String findAll = "TacitKnowledgePeriodicTopicAdjacency.findAll";
    public final static String findById = "TacitKnowledgePeriodicTopicAdjacency.findById";
    public final static String findByTopic = "TacitKnowledgePeriodicTopicAdjacency.findByTopic";
    public final static String findByFirstPhraseId = "TacitKnowledgePeriodicTopicAdjacency.findByFirstPhraseId";
    public final static String findBySecondPhraseId = "TacitKnowledgePeriodicTopicAdjacency.findBySecondPhraseId";
    public final static String findByCount = "TacitKnowledgePeriodicTopicAdjacency.findByCount";
    public final static String findByTopicFirstPhraseId = "TacitKnowledgePeriodicTopicAdjacency.findByTopicFirstPhraseId";
    public final static String findByTopicSecondPhraseId = "TacitKnowledgePeriodicTopicAdjacency.findByTopicSecondPhraseId";
    public final static String findByTopicFirstOrSecondPhraseId = "TacitKnowledgePeriodicTopicAdjacency.findByTopicFirstOrSecondPhraseId";
    
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
    private Date fromDate;
    private Date toDate;

    public TacitKnowledgePeriodicTopicAdjacency() {
    }

    public TacitKnowledgePeriodicTopicAdjacency(Long id) {
        this.id = id;
    }

    public TacitKnowledgePeriodicTopicAdjacency(Long id, String topic, long firstPhraseId, long secondPhraseId, long count) {
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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TacitKnowledgePeriodicTopicAdjacency other = (TacitKnowledgePeriodicTopicAdjacency) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TacitKnowledgePeriodicTopicAdjacency{" + "id=" + id + ", topic=" + topic + ", firstPhraseId=" + firstPhraseId + ", secondPhraseId=" + secondPhraseId + ", count=" + count + ", fromDate=" + fromDate + ", toDate=" + toDate + '}';
    }

    
}
