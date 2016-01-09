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
public class TacitKnowledgePhraseCount implements Serializable {

    public TacitKnowledgePhraseCount(Long phraseId, String phrase, Long count) {
        this.phraseId = phraseId;
        this.phrase = phrase;
        this.count = count;
    }

    public Long getPhraseId() {
        return phraseId;
    }

    public void setPhraseId(Long phraseId) {
        this.phraseId = phraseId;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.phraseId);
        hash = 97 * hash + Objects.hashCode(this.phrase);
        hash = 97 * hash + Objects.hashCode(this.count);
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
        final TacitKnowledgePhraseCount other = (TacitKnowledgePhraseCount) obj;
        if (!Objects.equals(this.phraseId, other.phraseId)) {
            return false;
        }
        if (!Objects.equals(this.phrase, other.phrase)) {
            return false;
        }
        if (!Objects.equals(this.count, other.count)) {
            return false;
        }
        return true;
    }
    
    private Long phraseId;
    private String phrase;
    private Long count;
}
