/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge;

import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author vavasthi
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/usenetPostQueue"),
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class UsenetPostMessageQueue implements MessageListener {

    @PersistenceContext(unitName = "TacitKnowledge-ejbPU")
    private EntityManager em;

    public UsenetPostMessageQueue() {
    }

    @Override
    public void onMessage(Message message) {

        LOG.info("Message received ");

        if (message instanceof ObjectMessage) {
            try {
                ObjectMessage o = (ObjectMessage) message;
                UsenetPostMessage upm = (UsenetPostMessage) o.getObject();
                processMessage(upm.getSenderName(),
                        upm.getSenderEmail(),
                        upm.getId(),
                        upm.getSubject(),
                        upm.getDate(),
                        upm.getContentType(),
                        upm.getBytes(),
                        upm.getLines(),
                        upm.getNewsgroup(),
                        upm.getInReplyTo(),
                        upm.getReferences(),
                        upm.getBody());
            } catch (JMSException ex) {
                Logger.getLogger(UsenetPostMessageQueue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }

    private void processMessage(String senderName,
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
            LOG.info("Creating a new post :" + id);
            up.setId(id);
            em.persist(up);
        }
        Query iq
                = em.createQuery("select i FROM Individual i where i.email = :email");
        iq.setParameter("email", senderEmail);
        Individual i;
        List il = iq.getResultList();
        if (il.isEmpty()) {
            i = new Individual();
            i.setEmail(senderEmail);
            i.setName(senderName);
            em.persist(i);
        } else {
            i = (Individual) (il.get(0));
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
        List<UsenetPostReference> referenceList;
        referenceList = new ArrayList<>();
        if (references != null) {
            for (String rid : references) {
                UsenetPostReference upr = new UsenetPostReference();
                upr.setReferenceId(rid.toLowerCase());
                referenceList.add(upr);
                em.persist(upr);
            }
        }
            up.setReferencedPosts(referenceList);
    }
    private static final Logger LOG = Logger.getLogger(UsenetPostMessageQueue.class.getName());

}
