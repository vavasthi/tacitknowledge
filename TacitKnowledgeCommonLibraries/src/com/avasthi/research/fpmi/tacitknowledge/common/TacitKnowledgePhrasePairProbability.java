/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.common;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author vavasthi
 */
public class TacitKnowledgePhrasePairProbability implements Serializable {

    public TacitKnowledgePhrasePairProbability(String topic, String phrase1, String phrase2, Double probability) {
        this.topic = topic;
        this.phrase1 = phrase1;
        this.phrase2 = phrase2;
        this.probability = probability;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPhrase1() {
        return phrase1;
    }

    public void setPhrase1(String phrase1) {
        this.phrase1 = phrase1;
    }

    public String getPhrase2() {
        return phrase2;
    }

    public void setPhrase2(String phrase2) {
        this.phrase2 = phrase2;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.phrase1);
        hash = 37 * hash + Objects.hashCode(this.phrase2);
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
        final TacitKnowledgePhrasePairProbability other = (TacitKnowledgePhrasePairProbability) obj;
        if (!Objects.equals(this.phrase1, other.phrase1)) {
            return false;
        }
        if (!Objects.equals(this.phrase2, other.phrase2)) {
            return false;
        }
        return true;
    }
    
    private String topic;
    private String phrase1;
    private String phrase2;
    private Double probability; 
    
}
