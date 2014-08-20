/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.avasthi.research.fpmi.tacitknowledge;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author vavasthi
 */
@Stateless
public class SessionTester implements SessionTesterLocal {
    @PersistenceContext(unitName = "TacitKnowledge-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public Individual addIndividual(Long id) {
       Individual i = new Individual();
       i.setId(id);
       em.persist(i);
       return i;
    }
}
