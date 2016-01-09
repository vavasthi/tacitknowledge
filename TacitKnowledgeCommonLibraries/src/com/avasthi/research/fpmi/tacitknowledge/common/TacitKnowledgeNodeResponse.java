/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.common;

import java.io.Serializable;

/**
 *
 * @author vavasthi
 */
public class TacitKnowledgeNodeResponse implements Serializable {

    public TacitKnowledgeNodeResponse() {
    }

    public TacitKnowledgeNodeResponse(String name, Integer size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
    
    private String name;
    private Integer size;
}
