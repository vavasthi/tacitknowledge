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
public class UsenetInterestingPhraseMessage implements Serializable {

    long uid;
    Date from;
    Date to;
    List<UsenetPostPhraseScore> ppsList;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public List<UsenetPostPhraseScore> getPpsList() {
        return ppsList;
    }

    public void setPpsList(List<UsenetPostPhraseScore> ppsList) {
        this.ppsList = ppsList;
    }
}
