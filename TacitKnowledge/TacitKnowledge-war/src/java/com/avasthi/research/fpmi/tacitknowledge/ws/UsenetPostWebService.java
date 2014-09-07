/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.avasthi.research.fpmi.tacitknowledge.ws;

import com.avasthi.research.fpmi.tacitknowledge.UsenetPost;
import com.avasthi.research.fpmi.tacitknowledge.UsenetPostSessionLocal;
import java.util.List;
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
    public List<String> listMessageIds(@WebParam(name = "id") long id) {
        return ejbRef.listMessageIds(id);
    }

    @WebMethod(operationName = "getMessageBody")
    public String getMessageBody(@WebParam(name = "id") String id) {
        return ejbRef.getMessageBody(id);
    }

    @WebMethod(operationName = "updateMessageId")
    @Oneway
    public void updateMessageId() {
        ejbRef.updateMessageId();
    }

    @WebMethod(operationName = "insertPhrases")
    @Oneway
    public void insertPhrases(@WebParam(name = "userid") long userid, @WebParam(name = "ppsList") List<com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostPhraseScore> ppsList) {
        ejbRef.insertPhrases(userid, ppsList);
    }
    
}
