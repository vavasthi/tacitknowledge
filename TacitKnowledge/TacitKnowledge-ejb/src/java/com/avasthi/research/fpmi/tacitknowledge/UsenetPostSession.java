/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.avasthi.research.fpmi.tacitknowledge;

import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostPhraseScore;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author vavasthi
 */
@Stateless
public class UsenetPostSession implements UsenetPostSessionLocal {
    @PersistenceContext(unitName = "TacitKnowledge-ejbPU")
    private EntityManager em;
    
    private static final Logger LOG = Logger.getLogger(UsenetPostSession.class.getName());

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")


    @Override
    public List<UsenetPost> listUsenetPosts(String email) {
        Query q = em.createQuery("select p from UsenetPost p, Individual i where i.email = :email and i = p.sender");
        q.setParameter("email", email);
        List l = q.getResultList();
        List<UsenetPost> upl = new ArrayList<UsenetPost>();
        for (Object o : l) {
            upl.add((UsenetPost)o);
        }
        return upl;
        
    }
    
    @Override
    public List<UsenetPost> listUsenetPosts() {
        Query q = em.createQuery("select p from UsenetPost p");
        List l = q.getResultList();
        List<UsenetPost> upl = new ArrayList<UsenetPost>();
        for (Object o : l) {
            upl.add((UsenetPost)o);
        }
        return upl;
    }
    @Override
    public int countUsenetPosts(String email) {
        Query q = em.createQuery("select p from Individual i, UsenetPost p where i.email = :email and i = p.sender");
        q.setParameter("email", email);
        List l = q.getResultList();
        return l.size();
    }
    
    @Override
    public int countUsenetPosts() {
        Query q = em.createQuery("select p from UsenetPost p");
        List l = q.getResultList();
        return l.size();
    }

    @Override
    public List<Long> listIndividualIds() {
        Query q = em.createQuery("select id from Individual i");
        List l = q.getResultList();
        List<Long> idl = new ArrayList<Long>();
        for (Object o : l) {
            idl.add((Long)o);
        }
        return idl;
    }

    @Override
    public List<String> listMessageIds(long id) {
        Individual i = em.find(Individual.class, id);
        Query q = em.createQuery("select p.id from Individual i, UsenetPost p where p.sender = :sender and p.sender = i");
        q.setParameter("sender", i);
        List l = q.getResultList();
        List<String> idl = new ArrayList<String>();
        for (Object o : l) {
            
            try {
                idl.add(URLEncoder.encode((String)o,"UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(UsenetPostSession.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return idl;
    }

    @Override
    public String getMessageBody(String id) {
        try {
            String mid = URLDecoder.decode(id, "UTF-8");
            UsenetPost p = em.find(UsenetPost.class, mid);
            return URLEncoder.encode(p.getBody(), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UsenetPostSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    

    @Oneway
    @Override
    public void updateMessageId() {
        
        Query q = em.createQuery("select p from UsenetPost p");
        for (Object o : q.getResultList()) {
            UsenetPost p = (UsenetPost)o;
            p.setId(p.getId().replaceAll("[<>]", ""));
            LOG.info("Message Id is " + p.getId());
        }
    }

    @Oneway
    @Override
    public void insertPhrases(long userid, List<UsenetPostPhraseScore> ppsList) {
        for (UsenetPostPhraseScore pps : ppsList) {
            
        IndividualInterestingPhrases iip 
                = new IndividualInterestingPhrases();
        iip.setUserid(userid);
        iip.setPhraseLength(pps.getPhraseLength());
        iip.setPhrase(pps.getPhrase());
        iip.setScore(pps.getScore());
        em.persist(iip);
        }
    }
   
}
