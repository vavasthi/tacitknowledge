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
    private Long weight;
    private String phrase;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.weight);
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
        if (!Objects.equals(this.weight, other.weight)) {
            return false;
        }
        return true;
    }
    public InterestingPhrase() {
        
    }

    public InterestingPhrase(Long id, String name) {
        this.weight = id;
        this.phrase = name;
    }
    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getName() {
        return phrase;
    }

    public void setName(String name) {
        this.phrase = name;
    }
}
