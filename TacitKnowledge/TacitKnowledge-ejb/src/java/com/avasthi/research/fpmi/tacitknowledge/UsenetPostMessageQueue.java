/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge;

import com.avasthi.research.fpmi.tacitknowledge.common.UsenetInterestingPhraseMessage;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetMessageIds;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetNetworkEdgeMessage;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostMessage;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostPhraseScore;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
                int messageType = o.getIntProperty(UsenetMessageIds.MESSAGE_TYPE_PROPERTY);
                switch (messageType) {
                    case UsenetMessageIds.POST_MESSAGE:
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
                        break;
                    case UsenetMessageIds.INTERESTING_PHRASE_MESSAGE:
                        UsenetInterestingPhraseMessage uipm = (UsenetInterestingPhraseMessage) o.getObject();
                        processMessage(uipm.getUid(),
                                uipm.getFrom(),
                                uipm.getTo(),
                                uipm.getPpsList());
                        break;
                    case UsenetMessageIds.NETWORK_EDGE_MESSAGE:
                        UsenetNetworkEdgeMessage unem = (UsenetNetworkEdgeMessage) o.getObject();
                        processMessage(unem.getIndividualFrom(),
                                unem.getIndividualTo(),
                                unem.getDateFrom(),
                                unem.getDateTo(),
                                unem.getTopic());
                        break;
                }
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

    private void processMessage(long uid, Date from, Date to, List<UsenetPostPhraseScore> ppsList) {

        if (ppsList != null) {

            LOG.info(ppsList.size() + " elements received for date range " + from.toString() + " to " + to.toString() + " for uid " + uid);
            for (UsenetPostPhraseScore pps : ppsList) {

                IndividualInterestingPhrases iip
                        = new IndividualInterestingPhrases();
                iip.setFromDate(from);
                iip.setToDate(to);
                iip.setUserid(uid);
                iip.setPhraseLength(pps.getPhraseLength());
                iip.setPhrase(pps.getPhrase());
                iip.setScore(pps.getScore());
                em.persist(iip);
            }
        } else {
            LOG.info("No element received for date range " + from.toString() + " to " + to.toString() + " for uid " + uid);
        }

    }

    private void processMessage(long uidFrom,
            long uidTo,
            Date from,
            Date to,
            String topic) {

        Individual indFrom = em.find(Individual.class, uidFrom);
        Individual indTo = em.find(Individual.class, uidTo);
        try {
            String dTopic = URLDecoder.decode(topic, "UTF-8");
            StringTokenizer st = new StringTokenizer(dTopic, ", ");
            String t= null;
            while (st.hasMoreTokens()) {
                try {
                    t = st.nextToken();
                    Query q = em.createQuery("select une from UsenetNetworkEdge une where une.from = :from and une.to = :to and une.dateFrom = :dateFrom and une.dateTo = :dateTo and une.topic = :topic");
                    q.setParameter("from", indFrom);
                    q.setParameter("to", indTo);
                    q.setParameter("dateFrom", from);
                    q.setParameter("dateTo", to);
                    q.setParameter("topic", t);
                    UsenetNetworkEdge une = (UsenetNetworkEdge) (q.getSingleResult());
                    une.setCount(une.getCount() + 1);
                } catch (NoResultException nre) {
                    UsenetNetworkEdge une = new UsenetNetworkEdge();
                    une.setCount(1L);
                    une.setDateFrom(from);
                    une.setDateTo(to);
                    une.setFrom(indFrom);
                    une.setTo(indTo);
                    une.setTopic(t);
                    em.persist(une);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UsenetPostMessageQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static final Logger LOG = Logger.getLogger(UsenetPostMessageQueue.class
            .getName());

}
