/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge;

import com.aliasi.util.ScoredObject;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetInitiatePhraseAdjacencyCalculation;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetInterestingPhraseMessage;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetInterestingPhraseMessages;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetMessageIds;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetNetworkEdgeMessage;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostMessage;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostPhraseScore;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetUpgradeTableMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
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
import javax.persistence.PersistenceException;
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
                    case UsenetMessageIds.INTERESTING_PHRASE_MESSAGE: {

                        UsenetInterestingPhraseMessage uipm = (UsenetInterestingPhraseMessage) o.getObject();
                        List<UsenetInterestingPhraseMessage> l = new ArrayList<UsenetInterestingPhraseMessage>();
                        l.add(uipm);
                        processMessage(l);
                        break;
                    }
                    case UsenetMessageIds.NETWORK_EDGE_MESSAGE:
                        UsenetNetworkEdgeMessage unem = (UsenetNetworkEdgeMessage) o.getObject();
                        processMessage(unem.getIndividualFrom(),
                                unem.getIndividualTo(),
                                unem.getDateFrom(),
                                unem.getDateTo(),
                                unem.getTopic());
                    case UsenetMessageIds.UPGRADE_TABLE_MESSAGE:
                        UsenetUpgradeTableMessage uutm = (UsenetUpgradeTableMessage) o.getObject();
                        processMessage(uutm.getId());
                        break;
                    case UsenetMessageIds.INITIATE_PHRASE_ADJACENCY:
                        LOG.info("Initiate phrase adjacency message received.");
                        UsenetInitiatePhraseAdjacencyCalculation uipac 
                                = (UsenetInitiatePhraseAdjacencyCalculation)o.getObject();
                        generateTopicAdjacency(uipac.getUid());
                        break;
                    case UsenetMessageIds.INTERESTING_PHRASE_MESSAGES: {

                        UsenetInterestingPhraseMessages uipms = (UsenetInterestingPhraseMessages) o.getObject();

                        if (uipms.getMessages() != null) {

                            processMessage(uipms.getMessages());
                        }
                        break;
                    }
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

    private void processMessage(List<UsenetInterestingPhraseMessage> messages) {

        for (UsenetInterestingPhraseMessage uipm : messages) {
            List<UsenetPostPhraseScore> ppsList = uipm.getPpsList();

            if (ppsList != null) {
                for (UsenetPostPhraseScore pps : ppsList) {

                    IndividualInterestingPhrases iip
                            = new IndividualInterestingPhrases();
                    iip.setTopic(uipm.getTopic());
                    iip.setFromDate(uipm.getFrom());
                    iip.setToDate(uipm.getTo());
                    iip.setUserid(uipm.getUid());
                    iip.setPhraseLength(pps.getPhraseLength());
                    iip.setPhrase(pps.getPhrase());
                    iip.setScore(pps.getScore());
                    em.persist(iip);
                }
            } else {
                LOG.info("No element received for date range " + uipm.getFrom().toString() + " to " + uipm.getTo().toString() + " for uid " + uipm.getUid());
            }
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
            String t = null;
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

    private void processMessage(Long uid) {
        LOG.info("Processing table upgrade for uid =" + uid);

        Individual i = em.find(Individual.class, uid);
        Query q = em.createQuery("select p from Individual i, UsenetPost p where p.sender = :sender and p.sender = i");
        q.setParameter("sender", i);
        List l = q.getResultList();
        for (Object o : l) {
            UsenetPost up = (UsenetPost) o;
            StringTokenizer st = new StringTokenizer(up.getNewsGroup(), ",");
            List<UsenetTopic> list = up.getReferencedTopics();
            while (st.hasMoreTokens()) {
                String topic = st.nextToken().trim();
                UsenetTopic ut = new UsenetTopic();
                ut.setReferenceId(topic);
                em.persist(ut);
                list.add(ut);
            }
            up.setReferencedTopics(list);
        }

    }
    private String createPhrase(String[] sa) {

        String phrase = "";
        String sep1 = " ";
        String sep2 = "";
        String sep = sep2;
        for (int j = 0; j < sa.length; ++j) {
            phrase += sep + sa[j];
            sep = sep1;
        }
        return phrase;
    }

    private TopicAdjacencyPhrases getPhrase(String phrase, String topic) throws PersistenceException {

            TopicAdjacencyPhrases phraseObject = new TopicAdjacencyPhrases();
            phraseObject.setPhrase(phrase);
            phraseObject.setTopic(topic);
            phraseObject.setCount(0);
            em.persist(phraseObject);
            return phraseObject;
    }

    private TopicAdjacencyDependency getDependency(EntityManager em, TopicAdjacencyPhrases phrase1,
            TopicAdjacencyPhrases phrase2,
            double phrase1Count,
            double phrase2Count) throws PersistenceException {
        if (phrase1.getPhrase().compareTo(phrase2.getPhrase()) < 0) {
            
                TopicAdjacencyDependency dependency
                        = new TopicAdjacencyDependency();
                dependency.setFirstPhrase(phrase1);
                dependency.setSecondPhrase(phrase2);
                dependency.setTopic(phrase1.getTopic());
                dependency.setCount(Math.min(phrase1Count, phrase2Count));
                em.persist(dependency);
                return dependency;
        }
        else {
            
                TopicAdjacencyDependency dependency
                        = new TopicAdjacencyDependency();
                dependency.setFirstPhrase(phrase2);
                dependency.setSecondPhrase(phrase1);
                dependency.setTopic(phrase2.getTopic());
                dependency.setCount(Math.min(phrase1Count, phrase2Count));
                em.persist(dependency);
                return dependency;
        }
    }

    private void increaseCounts(EntityManager em, String topic, SortedSet< ScoredObject<String[]>> sso) {
        String first = null;
        for (ScoredObject<String[]> soOuter : sso) {
            String outerPhraseString = createPhrase(soOuter.getObject());
            TopicAdjacencyPhrases outerPhrase = getPhrase(outerPhraseString, topic);
            outerPhrase.setCount(outerPhrase.getCount() + soOuter.score());
            boolean skipToOuter = true;
            for (ScoredObject<String[]> soInner : sso) {
                if (skipToOuter) {
                    if (soInner == soOuter) {
                        skipToOuter = false;
                    }
                } else {

                    if (soInner.equals(soOuter)) {
                        // This is the scenario where both the phrases are identical. 
                        TopicAdjacencyDependency dependency = getDependency(em, outerPhrase, outerPhrase, soOuter.score(), soOuter.score());
                    } else {

                        String innerPhraseString = createPhrase(soInner.getObject());
                        TopicAdjacencyPhrases innerPhrase = getPhrase(innerPhraseString, topic);
                        innerPhrase.setCount(innerPhrase.getCount() + soInner.score());
                        TopicAdjacencyDependency dependency = getDependency(em, outerPhrase, innerPhrase, soOuter.score(), soInner.score());
                    }
                }
            }
        }
    }

    public void  generateTopicAdjacency(long uid) {
        Individual i = em.find(Individual.class, uid);
        Query q = em.createQuery("select p from UsenetPost p where p.sender = :sender");
        q.setParameter("sender", i);
        for (Object o : q.getResultList()) {
            UsenetPost p = (UsenetPost)o;
            int count = generateTopicAdjacency(p);
            Logger.getLogger(UsenetPostMessageQueue.class.getName()).log(Level.INFO, "Processed " + count + " messages for topic " + p.getId());
        }
        em.flush();
    }

    public Integer generateTopicAdjacency(UsenetPost p) {
        
        int noMsgs = 0;
        if (p == null) {
            return 0;
        } else {
            for (UsenetTopic topic : p.getReferencedTopics()) {
                try {

                    TacitKnowledgeInterestingPhraseDetector ipd
                            = new TacitKnowledgeInterestingPhraseDetector();
                    String body = p.body;
                    body = body.trim();
                    ipd.incrementalTrain(body);
                    ipd.model.sequenceCounter().prune(3);
                    SortedSet< ScoredObject<String[]>> sso = ipd.model.frequentTermSet(1, 100);
                    increaseCounts(em, topic.getReferenceId(), sso);
                    ++noMsgs;
                } catch (IOException ex) {
                    Logger.getLogger(UsenetPostSession.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return noMsgs;
    }
    
    public Integer generateTopicAdjacency(String messageId) {

        UsenetPost p;
        try {
            
            p = em.find(UsenetPost.class, URLDecoder.decode(messageId, "UTF-8"));
        }
        catch (UnsupportedEncodingException uex) {
            p = null;
            uex.printStackTrace();
        }
        return generateTopicAdjacency(p);
    }

    private static final Logger LOG = Logger.getLogger(UsenetPostMessageQueue.class
            .getName());

}
