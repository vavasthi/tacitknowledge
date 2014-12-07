/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws;

import com.avasthi.research.fpmi.tacitknowledge.UsenetPost;
import com.avasthi.research.fpmi.tacitknowledge.UsenetPostSessionLocal;
import com.avasthi.research.fpmi.tacitknowledge.common.InterestingPhrase;
import com.avasthi.research.fpmi.tacitknowledge.common.NetworkEdge;
import com.avasthi.research.fpmi.tacitknowledge.common.NetworkNode;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostHeaders;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostPhraseScore;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 *
 * @author vavasthi
 */
@WebService(serviceName = "UsenetPostWebService")
public class UsenetPostWebService {
    @EJB
    private UsenetPostSessionLocal ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "listUsenetPosts")
    public List<UsenetPost> listUsenetPosts(@WebParam(name = "email") String email) {
        return ejbRef.listUsenetPosts(email);
    }

    @WebMethod(operationName = "listUsenetPosts_1")
    @RequestWrapper(className = "listUsenetPosts_1")
    @ResponseWrapper(className = "listUsenetPosts_1Response")
    public List<UsenetPost> listUsenetPosts() {
        return ejbRef.listUsenetPosts();
    }

    @WebMethod(operationName = "countUsenetPosts")
    public int countUsenetPosts(@WebParam(name = "email") String email) {
        return ejbRef.countUsenetPosts(email);
    }

    @WebMethod(operationName = "countUsenetPosts_1")
    @RequestWrapper(className = "countUsenetPosts_1")
    @ResponseWrapper(className = "countUsenetPosts_1Response")
    public int countUsenetPosts() {
        return ejbRef.countUsenetPosts();
    }

    @WebMethod(operationName = "listIndividualIds")
    public List<Long> listIndividualIds() {
        return ejbRef.listIndividualIds();
    }

    @WebMethod(operationName = "listMessageIds")
    public List<String> listMessageIds(@WebParam(name = "id") long id, @WebParam(name = "from") Date from, @WebParam(name = "to") Date to) {
        return ejbRef.listMessageIds(id, from, to);
    }

    @WebMethod(operationName = "getMessageBody")
    public String getMessageBody(@WebParam(name = "id") String id) {
        return ejbRef.getMessageBody(id);
    }

    @WebMethod(operationName = "getMinDateForUser")
    public Date getMinDateForUser(@WebParam(name = "uid") Long uid) {
        return ejbRef.getMinDateForUser(uid);
    }

    @WebMethod(operationName = "getMaxDateForUser")
    public Date getMaxDateForUser(@WebParam(name = "uid") Long uid) {
        return ejbRef.getMaxDateForUser(uid);
    }

    @WebMethod(operationName = "getMinDate")
    public Date getMinDate() {
        return ejbRef.getMinDate();
    }

    @WebMethod(operationName = "getMaxDate")
    public Date getMaxDate() {
        return ejbRef.getMaxDate();
    }

    @WebMethod(operationName = "updateMessageId")
    @Oneway
    public void updateMessageId() {
        ejbRef.updateMessageId();
    }

    @WebMethod(operationName = "insertPhrases")
    @Oneway
    public void insertPhrases(@WebParam(name = "userid") long userid, @WebParam(name = "from") Date from, @WebParam(name = "to") Date to, @WebParam(name = "ppsList") List<UsenetPostPhraseScore> ppsList) {
        ejbRef.insertPhrases(userid, from, to, ppsList);
    }

    @WebMethod(operationName = "getPost")
    public UsenetPostHeaders getPost(@WebParam(name = "id") String id) {
        return ejbRef.getPost(id);
    }

    @WebMethod(operationName = "getNetworkNodes")
    public Set<NetworkNode> getNetworkNodes(@WebParam(name = "from") Date from, @WebParam(name = "to") Date to, @WebParam(name = "topic") String topic) {
        return ejbRef.getNetworkNodes(from, to, topic);
    }

    @WebMethod(operationName = "getNetworkEdges")
    public List<NetworkEdge> getNetworkEdges(@WebParam(name = "src") Long src, @WebParam(name = "tgt") Long tgt, @WebParam(name = "from") Date from, @WebParam(name = "to") Date to, @WebParam(name = "topic") String topic) {
        return ejbRef.getNetworkEdges(src, tgt, from, to, topic);
    }

    @WebMethod(operationName = "getTopics")
    public List<String> getTopics() {
        return ejbRef.getTopics();
    }

    @WebMethod(operationName = "getRelevantTopics")
    public List<String> getRelevantTopics(@WebParam(name = "uid") long uid, @WebParam(name = "dateFrom") Date dateFrom, @WebParam(name = "dateTo") Date dateTo) {
        return ejbRef.getRelevantTopics(uid, dateFrom, dateTo);
    }

    @WebMethod(operationName = "getInterestingPhrasesForNewsgroupForYear")
    public List<InterestingPhrase> getInterestingPhrasesForNewsgroupForYear(@WebParam(name = "topic") String topic, @WebParam(name = "year") int year, @WebParam(name = "month") int month) {
        return ejbRef.getInterestingPhrasesForNewsgroupForYear(topic, year, month);
    }

    @WebMethod(operationName = "upgradeTable")
    @Oneway
    public void upgradeTable(@WebParam(name = "uid") long uid) {
        ejbRef.upgradeTable(uid);
    }
    
}
