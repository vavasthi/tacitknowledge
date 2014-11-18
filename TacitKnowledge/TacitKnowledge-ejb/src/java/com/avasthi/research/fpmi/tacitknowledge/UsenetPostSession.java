/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge;

import com.avasthi.research.fpmi.tacitknowledge.common.InterestingPhrase;
import com.avasthi.research.fpmi.tacitknowledge.common.NetworkEdge;
import com.avasthi.research.fpmi.tacitknowledge.common.NetworkNode;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostHeaders;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostPhraseScore;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
            upl.add((UsenetPost) o);
        }
        return upl;

    }

    @Override
    public List<UsenetPost> listUsenetPosts() {
        Query q = em.createQuery("select p from UsenetPost p");
        List l = q.getResultList();
        List<UsenetPost> upl = new ArrayList<UsenetPost>();
        for (Object o : l) {
            upl.add((UsenetPost) o);
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
        Query q = em.createQuery("select DISTINCT i.id from Individual i, UsenetPost p where p.sender = i");
        List l = q.getResultList();
        List<Long> idl = new ArrayList<Long>();
        for (Object o : l) {
            idl.add((Long) o);
        }
        return idl;
    }

    @Override
    public List<String> listMessageIds(long id, Date from, Date to) {
        Individual i = em.find(Individual.class, id);
        Query q = em.createQuery("select p.id from Individual i, UsenetPost p where p.sender = :sender and p.sender = i and p.date >= :from and p.date < :to");
        q.setParameter("from", from);
        q.setParameter("to", to);
        q.setParameter("sender", i);
        List l = q.getResultList();
        List<String> idl = new ArrayList<String>();
        for (Object o : l) {

            try {
                idl.add(URLEncoder.encode((String) o, "UTF-8"));
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

    @Override
    public Set<NetworkNode> getNetworkNodes(Date from, Date to, String topic) {
        Set<NetworkNode> nodes = new HashSet<NetworkNode>();
        Query q = em.createQuery("select une from UsenetNetworkEdge une where une.dateFrom >= :from and une.dateTo < :to and une.topic = :topic");
        q.setParameter("from", from);
        q.setParameter("to", to);
        q.setParameter("topic", topic);
        for (Object o : q.getResultList()) {
            UsenetNetworkEdge une = (UsenetNetworkEdge) o;
            nodes.add(new NetworkNode(une.getFrom().getId(), une.getFrom().getEmail()));
            nodes.add(new NetworkNode(une.getTo().getId(), une.getTo().getEmail()));
        }
        return nodes;
    }

    @Override
    public List<InterestingPhrase> getInterestingPhrasesForNewsgroupForYear(String topic, int year) {

        List<InterestingPhrase> lip = new ArrayList<InterestingPhrase>();
        Double maxScore = 256.0;
        {
            Query q = em.createQuery("select max(iip.score) from IndividualInterestingPhrases iip where year(iip.fromDate) = :year and iip.topic = :topic");
            q.setParameter("year", year);
            q.setParameter("topic", topic);
            maxScore = (Double) q.getSingleResult();

        }
        {

            Query q = em.createQuery("select iip.phrase, sum(iip.score) from IndividualInterestingPhrases iip where year(iip.fromDate) = :year and iip.topic = :topic group by iip.phrase");
            q.setParameter("year", year);
            q.setParameter("topic", topic);
            for (Object[] oa : (List<Object[]>) q.getResultList()) {
                String phrase = (String) oa[0];
                Double ns = ((Double) oa[1]) * 40 / maxScore;
                Long score = ns.longValue();
                InterestingPhrase ip = new InterestingPhrase(score, phrase);
                lip.add(ip);
            }
        }
        return lip;
    }

    @Override
    public List<NetworkEdge> getNetworkEdges(Long src, Long tgt, Date from, Date to, String topic) {
        Individual source = em.find(Individual.class, src);
        Individual target = em.find(Individual.class, tgt);
        Map<String, NetworkEdge> topicHash = new HashMap<String, NetworkEdge>();
        List<NetworkEdge> edges = new ArrayList<NetworkEdge>();
        Query q = em.createQuery("select une from UsenetNetworkEdge une where une.dateFrom >= :dateFrom and une.dateTo < :dateTo and une.from = :from and une.to = :to and une.topic = :topic");
        q.setParameter("dateFrom", from);
        q.setParameter("dateTo", to);
        q.setParameter("from", source);
        q.setParameter("to", target);
        q.setParameter("topic", topic);
        for (Object o : q.getResultList()) {
            UsenetNetworkEdge une = (UsenetNetworkEdge) o;
            NetworkEdge edge = topicHash.get(une.getTopic());
            if (edge != null) {
                edge.setCount(edge.getCount() + une.getCount());
            } else {

                edge = new NetworkEdge(une.getFrom().getId() + "-" + une.getTo().getId() + "-" + une.getTopic(),
                        une.getFrom().getId(),
                        une.getTo().getId(),
                        une.getTopic(),
                        une.getCount());
            }
            topicHash.put(une.getTopic(), edge);
        }
        for (Entry<String, NetworkEdge> e : topicHash.entrySet()) {
            edges.add(e.getValue());
        }
        return edges;
    }

    @Override
    public UsenetPostHeaders getPost(String id) {
        try {
            UsenetPost post = em.find(UsenetPost.class, URLDecoder.decode(id, "UTF-8"));
            if (post == null) {
                return null;
            } else {

                Logger.getLogger(UsenetPostSession.class.getName()).log(Level.SEVERE, "Searching message with Id " + id);
                List<String> references = new ArrayList<String>();
                if (post.getReferencedPosts() != null) {

                    for (UsenetPostReference upr : post.getReferencedPosts()) {
                        references.add(URLEncoder.encode(upr.getReferenceId(), "UTF-8"));
                    }
                }
                UsenetPostHeaders uph
                        = new UsenetPostHeaders(post.getSender().getId(),
                                URLEncoder.encode(post.getId(), "UTF-8"),
                                post.getDate(),
                                URLEncoder.encode(post.getNewsGroup(), "UTF-8"),
                                URLEncoder.encode(post.getInReplyTo(), "UTF-8"),
                                references);
                return uph;
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UsenetPostSession.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(UsenetPostSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<String> getTopics() {

        Query q = em.createQuery("select distinct une.topic from UsenetNetworkEdge une");
        List l = q.getResultList();
        List<String> result = new ArrayList<String>();
        for (Object o : l) {
            result.add((String) o);
        }
        return result;
    }

    @Override
    public Date getMinDateForUser(Long uid) {
        Individual i = em.find(Individual.class, uid);
        Query q = em.createQuery("select min(p.date) from Individual i, UsenetPost p where p.sender = :sender and p.sender = i");
        q.setParameter("sender", i);
        Date minDate = (Date) q.getSingleResult();
        return minDate;
    }

    @Override
    public Date getMaxDateForUser(Long uid) {
        Individual i = em.find(Individual.class, uid);
        Query q = em.createQuery("select max(p.date) from Individual i, UsenetPost p where p.sender = :sender and p.sender = i");
        q.setParameter("sender", i);
        Date minDate = (Date) q.getSingleResult();
        return minDate;
    }

    @Override
    public Date getMinDate() {
        Query q = em.createQuery("select min(p.date) from UsenetPost p");
        Date minDate = (Date) q.getSingleResult();
        return minDate;
    }

    @Override
    public Date getMaxDate() {
        Query q = em.createQuery("select max(p.date) from UsenetPost p");
        Date minDate = (Date) q.getSingleResult();
        return minDate;
    }

    @Oneway
    @Override
    public void updateMessageId() {

        Query q = em.createQuery("select p from UsenetPost p");
        for (Object o : q.getResultList()) {
            UsenetPost p = (UsenetPost) o;
            p.setId(p.getId().replaceAll("[<>]", ""));
            LOG.info("Message Id is " + p.getId());
        }
    }

    @Oneway
    @Override
    public void insertPhrases(long userid, Date from, Date to, List<UsenetPostPhraseScore> ppsList) {

        if (ppsList != null) {

            LOG.info(ppsList.size() + " elements received for date range " + from.toString() + " to " + to.toString() + " for uid " + userid);
            for (UsenetPostPhraseScore pps : ppsList) {

                IndividualInterestingPhrases iip
                        = new IndividualInterestingPhrases();
                iip.setFromDate(from);
                iip.setToDate(to);
                iip.setUserid(userid);
                iip.setPhraseLength(pps.getPhraseLength());
                iip.setPhrase(pps.getPhrase());
                iip.setScore(pps.getScore());
                em.persist(iip);
            }
        } else {
            LOG.info("No element received for date range " + from.toString() + " to " + to.toString() + " for uid " + userid);
        }
    }

}
