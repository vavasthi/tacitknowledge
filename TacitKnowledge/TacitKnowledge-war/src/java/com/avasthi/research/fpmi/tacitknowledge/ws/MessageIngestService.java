/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.avasthi.research.fpmi.tacitknowledge.ws;

import com.avasthi.research.fpmi.tacitknowledge.MessageIngestLocal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author vavasthi
 */
@WebService(serviceName = "MessageIngestService")
public class MessageIngestService {
    @EJB
    private MessageIngestLocal ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "ingestMessage")
    @Oneway
    public void ingestMessage(@WebParam(name = "senderName") String senderName, @WebParam(name = "senderEmail") String senderEmail, @WebParam(name = "id") String id, @WebParam(name = "subject") String subject, @WebParam(name = "date") Date date, @WebParam(name = "contentType") String contentType, @WebParam(name = "bytes") int bytes, @WebParam(name = "lines") int lines, @WebParam(name = "newsgroup") String newsgroup, @WebParam(name = "inReplyTo") String inReplyTo, @WebParam(name = "references") List<String> references, @WebParam(name = "body") String body) {
        ejbRef.ingestMessage(senderName, senderEmail, id, subject, date, contentType, bytes, lines, newsgroup, inReplyTo, references, body);
    }
    
}
