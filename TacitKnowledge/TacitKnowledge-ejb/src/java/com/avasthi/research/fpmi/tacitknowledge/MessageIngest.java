/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.avasthi.research.fpmi.tacitknowledge;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author vavasthi
 */
@Stateless
public class MessageIngest implements MessageIngestLocal {
    @PersistenceContext(unitName = "TacitKnowledge-ejbPU")
    private EntityManager em;

    @Override
    public void ingestMessage(String senderName, 
            String senderEmail, 
            String id, 
            String subject, 
            Date date, 
            String contentType, 
            int bytes, 
            int lines, 
            String newsgroup, 
            String inReplyTo, 
            List<String> references, 
            String body) {
        id = id.toLowerCase();
        UsenetPost up = em.find(UsenetPost.class, id);
        if (up == null) {
            
            up = new UsenetPost();
            up.setId(id);
            em.persist(up);
        }
        Query iq = 
                em.createQuery("select i FROM Individual i where i.email = :email");
        iq.setParameter("email", senderEmail);
        Individual i;
        try {
            
            i = (Individual)iq.getSingleResult();
        }
        catch (NoResultException nre) {
            i = new Individual();
            i.setEmail(senderEmail);
            i.setName(senderName);
            em.persist(i);
        }
        up.setSender(i);
        up.setSubject(subject);
        
        up.setBody(body);
        up.setBytes(bytes);
        up.setContentType(contentType);
        up.setDate(date);
        up.setInReplyTo(inReplyTo);
        up.setNoLines(lines);
        up.setNewsGroup(newsgroup);
        List<UsenetPostReference> referenceList = new ArrayList<UsenetPostReference>();
        for (String rid : references) {
            String lrid = rid.toLowerCase();
            UsenetPost rup = em.find(UsenetPost.class, lrid);
            if (rup == null) {
                rup = new UsenetPost();
                rup.setId(lrid);
                em.persist(rup);
            }
            UsenetPostReference upr = new UsenetPostReference();
            upr.setReferencedPost(rup);
            referenceList.add(upr);
        }
        up.setReferencedPosts(referenceList);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void persist(Object object) {
        em.persist(object);
    }
}
