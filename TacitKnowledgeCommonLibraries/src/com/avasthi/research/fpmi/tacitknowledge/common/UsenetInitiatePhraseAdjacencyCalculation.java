/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author vavasthi
 */
public class UsenetInitiatePhraseAdjacencyCalculation implements Serializable {
    private long uid;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public UsenetInitiatePhraseAdjacencyCalculation(long uid) {
        this.uid = uid;
    }

    public UsenetInitiatePhraseAdjacencyCalculation() {
    }

}
