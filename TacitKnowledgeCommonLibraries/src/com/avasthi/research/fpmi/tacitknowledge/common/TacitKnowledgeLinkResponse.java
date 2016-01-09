/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.common;

/**
 *
 * @author vavasthi
 */
public class TacitKnowledgeLinkResponse {

    public TacitKnowledgeLinkResponse() {
    }

    public TacitKnowledgeLinkResponse(Integer source, Integer target, Integer bond) {
        this.source = source;
        this.target = target;
        this.bond = bond;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Integer getBond() {
        return bond;
    }

    public void setBond(Integer bond) {
        this.bond = bond;
    }
    
    
    private Integer source;
    private Integer target;
    private Integer bond;
}
