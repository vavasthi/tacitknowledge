/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.avasthi.research.fpmi.tacitknowledge.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author vavasthi
 */
public class UsenetUpgradeTableMessage implements Serializable {

    Long id;
    
    public UsenetUpgradeTableMessage(Long id) {
        this.id = id;
    }
    public UsenetUpgradeTableMessage() {
        
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

 }
