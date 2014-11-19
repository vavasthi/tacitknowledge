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
public class InterestingPhrase implements Serializable {
    private Double sentiment;
    private String word;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.sentiment);
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
        final InterestingPhrase other = (InterestingPhrase) obj;
        if (!Objects.equals(this.sentiment, other.sentiment)) {
            return false;
        }
        return true;
    }
    public InterestingPhrase() {
        
    }

    public InterestingPhrase(Double sentiment, String word) {
        this.sentiment = sentiment;
        this.word = word;
    }
    public Double getSentiment() {
        return sentiment;
    }

    public void setSentiment(Double sentiment) {
        this.sentiment = sentiment;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
