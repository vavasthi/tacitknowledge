/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge;

import java.io.Serializable;
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
@Table(name = "TopicAdjacencyDependency")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = TopicAdjacencyDependency.findAll, query = "SELECT t FROM TopicAdjacencyDependency t"),
    @NamedQuery(name = TopicAdjacencyDependency.findById, query = "SELECT t FROM TopicAdjacencyDependency t WHERE t.id = :id"),
    @NamedQuery(name = TopicAdjacencyDependency.findByFirstPhrase, query = "SELECT t FROM TopicAdjacencyDependency t WHERE t.firstPhrase = :firstPhrase"),
    @NamedQuery(name = TopicAdjacencyDependency.findBySecondPhrase, query = "SELECT t FROM TopicAdjacencyDependency t WHERE t.secondPhrase = :secondPhrase"),
    @NamedQuery(name = TopicAdjacencyDependency.findByBothPhrases, query = "SELECT t FROM TopicAdjacencyDependency t WHERE t.firstPhrase = :firstPhrase and t.secondPhrase = :secondPhrase"),
    @NamedQuery(name = TopicAdjacencyDependency.findByCount, query = "SELECT t FROM TopicAdjacencyDependency t WHERE t.count = :count")
})
public class TopicAdjacencyDependency implements Serializable {
    
    public final static String findAll = "TopicAdjacencyDependency.findAll"; 
    public final static String findById = "TopicAdjacencyDependency.findById"; 
    public final static String findByFirstPhrase = "TopicAdjacencyDependency.findByFirstPhrase"; 
    public final static String findBySecondPhrase = "TopicAdjacencyDependency.findBySecondPhrase"; 
    public final static String findByBothPhrases = "TopicAdjacencyDependency.findByBothPhrases"; 
    public final static String findByCount = "TopicAdjacencyDependency.findByCount"; 

    
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
    private TopicAdjacencyPhrases firstPhrase;
    
    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="secondPhraseId",insertable=true,
        updatable=true,nullable=true,unique=true)
    private TopicAdjacencyPhrases secondPhrase;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "count")
    private Double count;

    public TopicAdjacencyDependency() {
    }

    public TopicAdjacencyDependency(Long id) {
        this.id = id;
    }

    public TopicAdjacencyDependency(Long id,
            String topic,
            TopicAdjacencyPhrases firstPhrase, 
            TopicAdjacencyPhrases secondPhrase, 
            double count) {
        this.id = id;
        this.topic = topic;
        this.firstPhrase = firstPhrase;
        this.secondPhrase = secondPhrase;
        this.count = count;
    }
    public TopicAdjacencyDependency(TopicAdjacencyPhrases firstPhrase, 
            TopicAdjacencyPhrases secondPhrase, 
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

    public TopicAdjacencyPhrases getFirstPhrase() {
        return firstPhrase;
    }

    public void setFirstPhrase(TopicAdjacencyPhrases firstPhrase) {
        this.firstPhrase = firstPhrase;
    }

    public TopicAdjacencyPhrases getSecondPhrase() {
        return secondPhrase;
    }

    public void setSecondPhrase(TopicAdjacencyPhrases secondPhrase) {
        this.secondPhrase = secondPhrase;
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
        if (!(object instanceof TopicAdjacencyDependency)) {
            return false;
        }
        TopicAdjacencyDependency other = (TopicAdjacencyDependency) object;
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
