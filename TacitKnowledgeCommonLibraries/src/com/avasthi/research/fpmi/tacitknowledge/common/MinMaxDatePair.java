/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.avasthi.research.fpmi.tacitknowledge.common;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author vavasthi
 */
public class MinMaxDatePair implements Serializable {
    private Date minDate;
    private Date maxDate;
    
    public MinMaxDatePair() {
        
    }

    public MinMaxDatePair(Date minDate, Date maxDate) {
        this.minDate = minDate;
        this.maxDate = maxDate;
    }
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.minDate);
        hash = 53 * hash + Objects.hashCode(this.maxDate);
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
        final MinMaxDatePair other = (MinMaxDatePair) obj;
        if (!Objects.equals(this.minDate, other.minDate)) {
            return false;
        }
        if (!Objects.equals(this.maxDate, other.maxDate)) {
            return false;
        }
        return true;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

}
