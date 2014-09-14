/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.avasthi.research.fpmi.tacitknowledge;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "IndividualInterestingPhrases")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IndividualInterestingPhrases.findAll", query = "SELECT i FROM IndividualInterestingPhrases i"),
    @NamedQuery(name = "IndividualInterestingPhrases.findById", query = "SELECT i FROM IndividualInterestingPhrases i WHERE i.id = :id"),
    @NamedQuery(name = "IndividualInterestingPhrases.findByUserid", query = "SELECT i FROM IndividualInterestingPhrases i WHERE i.userid = :userid"),
    @NamedQuery(name = "IndividualInterestingPhrases.findByPhraseLength", query = "SELECT i FROM IndividualInterestingPhrases i WHERE i.phraseLength = :phraseLength"),
    @NamedQuery(name = "IndividualInterestingPhrases.findByPhrase", query = "SELECT i FROM IndividualInterestingPhrases i WHERE i.phrase = :phrase"),
    @NamedQuery(name = "IndividualInterestingPhrases.findByScore", query = "SELECT i FROM IndividualInterestingPhrases i WHERE i.score = :score")})
public class IndividualInterestingPhrases implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Column(name = "userid")
    private Long userid;
    @Column(name = "fromDate")
    private Date fromDate;
    @Column(name = "toDate")
    private Date toDate;
    @Column(name = "phraseLength")
    private Integer phraseLength;
    @Size(max = 255)
    @Column(name = "phrase")
    private String phrase;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "score")
    private Double score;

    public IndividualInterestingPhrases() {
    }

    public IndividualInterestingPhrases(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
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
    public Integer getPhraseLength() {
        return phraseLength;
    }

    public void setPhraseLength(Integer phraseLength) {
        this.phraseLength = phraseLength;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
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
        if (!(object instanceof IndividualInterestingPhrases)) {
            return false;
        }
        IndividualInterestingPhrases other = (IndividualInterestingPhrases) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.avasthi.research.fpmi.tacitknowledge.IndividualInterestingPhrases[ id=" + id + " ]";
    }
    
}
