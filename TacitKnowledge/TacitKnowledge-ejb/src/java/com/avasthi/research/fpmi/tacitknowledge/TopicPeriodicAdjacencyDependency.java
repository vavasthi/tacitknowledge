/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vavasthi
 */
@Entity
@Table(name = "TopicPeriodicAdjacencyDependency")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = TopicPeriodicAdjacencyDependency.findAll, query = "SELECT t FROM TopicPeriodicAdjacencyDependency t"),
    @NamedQuery(name = TopicPeriodicAdjacencyDependency.findById, query = "SELECT t FROM TopicPeriodicAdjacencyDependency t WHERE t.id = :id"),
    @NamedQuery(name = TopicPeriodicAdjacencyDependency.findByFirstPhrase, query = "SELECT t FROM TopicPeriodicAdjacencyDependency t WHERE t.firstPhrase = :firstPhrase"),
    @NamedQuery(name = TopicPeriodicAdjacencyDependency.findBySecondPhrase, query = "SELECT t FROM TopicPeriodicAdjacencyDependency t WHERE t.secondPhrase = :secondPhrase"),
    @NamedQuery(name = TopicPeriodicAdjacencyDependency.findByBothPhrases, query = "SELECT t FROM TopicPeriodicAdjacencyDependency t WHERE t.firstPhrase = :firstPhrase and t.secondPhrase = :secondPhrase"),
    @NamedQuery(name = TopicPeriodicAdjacencyDependency.findByCount, query = "SELECT t FROM TopicPeriodicAdjacencyDependency t WHERE t.count = :count")
})
public class TopicPeriodicAdjacencyDependency implements Serializable {
    
    public final static String findAll = "TopicPeriodicAdjacencyDependency.findAll"; 
    public final static String findById = "TopicPeriodicAdjacencyDependency.findById"; 
    public final static String findByFirstPhrase = "TopicPeriodicAdjacencyDependency.findByFirstPhrase"; 
    public final static String findBySecondPhrase = "TopicPeriodicAdjacencyDependency.findBySecondPhrase"; 
    public final static String findByBothPhrases = "TopicPeriodicAdjacencyDependency.findByBothPhrases"; 
    public final static String findByCount = "TopicPeriodicAdjacencyDependency.findByCount"; 

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "topic")
    private String  topic;
    
    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="firstPhraseId",insertable=true,
        updatable=true,nullable=true,unique=true)
    private TopicPeriodicAdjacencyPhrases firstPhrase;
    
    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="secondPhraseId",insertable=true,
        updatable=true,nullable=true,unique=true)
    private TopicPeriodicAdjacencyPhrases secondPhrase;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "count")
    private Double count;

    private Date date;
    
    public TopicPeriodicAdjacencyDependency() {
    }

    public TopicPeriodicAdjacencyDependency(Long id) {
        this.id = id;
    }

    public TopicPeriodicAdjacencyDependency(Long id,
            String topic,
            TopicPeriodicAdjacencyPhrases firstPhrase, 
            TopicPeriodicAdjacencyPhrases secondPhrase, 
            double count) {
        this.id = id;
        this.topic = topic;
        this.firstPhrase = firstPhrase;
        this.secondPhrase = secondPhrase;
        this.count = count;
    }
    public TopicPeriodicAdjacencyDependency(TopicPeriodicAdjacencyPhrases firstPhrase, 
            TopicPeriodicAdjacencyPhrases secondPhrase, 
            double count) {
        this.firstPhrase = firstPhrase;
        this.secondPhrase = secondPhrase;
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

    public TopicPeriodicAdjacencyPhrases getFirstPhrase() {
        return firstPhrase;
    }

    public void setFirstPhrase(TopicPeriodicAdjacencyPhrases firstPhrase) {
        this.firstPhrase = firstPhrase;
    }

    public TopicPeriodicAdjacencyPhrases getSecondPhrase() {
        return secondPhrase;
    }

    public void setSecondPhrase(TopicPeriodicAdjacencyPhrases secondPhrase) {
        this.secondPhrase = secondPhrase;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        if (!(object instanceof TopicPeriodicAdjacencyDependency)) {
            return false;
        }
        TopicPeriodicAdjacencyDependency other = (TopicPeriodicAdjacencyDependency) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.avasthi.research.fpmi.tacitknowledge.TopicAdjacencyDependency[ id=" + id + " ]";
    }
    
}
