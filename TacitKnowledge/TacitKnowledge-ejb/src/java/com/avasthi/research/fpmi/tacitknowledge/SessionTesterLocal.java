/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.avasthi.research.fpmi.tacitknowledge;

import javax.ejb.Local;

/**
 *
 * @author vavasthi
 */
@Local
public interface SessionTesterLocal {

    Individual addIndividual(Long id);
    
}
