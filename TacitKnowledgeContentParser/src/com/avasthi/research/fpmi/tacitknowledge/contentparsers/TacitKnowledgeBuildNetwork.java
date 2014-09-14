/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import com.aliasi.util.ScoredObject;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetInterestingPhraseMessage;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetNetworkEdgeMessage;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostPhraseScore;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostHeaders;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostWebService;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostWebService_Service;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author vavasthi
 */
public class TacitKnowledgeBuildNetwork {

    Long startUserId;

    private TacitKnowledgeBuildNetwork(Long startUserId) {
        this.startUserId = startUserId;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Long startUserId = null;
        if (args.length > 0) {
            startUserId = Long.parseLong(args[0]);
        }
        TacitKnowledgeBuildNetwork builder = new TacitKnowledgeBuildNetwork(startUserId);
        builder.buildNetwork();
    }

    void buildNetwork() {

        UsenetPostWebService_Service service = new UsenetPostWebService_Service();
        UsenetPostWebService ws = service.getUsenetPostWebServicePort();
        List<Long> userIdList = ws.listIndividualIds();
        System.out.println("Found user ids :" + userIdList.size());
        System.out.println("Skipping till user id :" + startUserId);
        boolean skipping = false;
        if (startUserId != null) {
            skipping = true;
        }
        for (Long uid : userIdList) {
            if (uid.equals(startUserId)) {
                skipping = false;
            }
            if (!skipping) {

                try {
                    int noMsgs = 0;
                    System.out.println("Searching " + " for user id " + uid);
                    XMLGregorianCalendar fromMin = ws.getMinDateForUser(uid);
                    XMLGregorianCalendar toMax = ws.getMaxDateForUser(uid);
                    XMLGregorianCalendar from = DatatypeFactory.newInstance().newXMLGregorianCalendar(fromMin.toGregorianCalendar());
                    from.setHour(0);
                    from.setMinute(0);
                    from.setSecond(0);
                    from.setTimezone(0);
                    XMLGregorianCalendar to = DatatypeFactory.newInstance().newXMLGregorianCalendar(fromMin.toGregorianCalendar());;
                    to.setHour(0);
                    to.setMinute(0);
                    to.setSecond(0);
                    to.setTimezone(0);
                    System.out.println("Before " + from.toString() + " " + to.toString());
                    to.add(DatatypeFactory.newInstance().newDuration("P1M"));
                    System.out.println("After " + from.toString() + " " + to.toString());
                    while (from.compare(toMax) == DatatypeConstants.LESSER) {

                        TacitKnowledgeInterestingPhraseDetector ipd
                                = new TacitKnowledgeInterestingPhraseDetector();
                        List<String> messageIdList = ws.listMessageIds(uid, from, to);
                        System.out.println("Found " + messageIdList.size() + " message Ids" + " for user id " + uid + " between dates " + from.toString() + " and " + to.toString());
                        for (String mid : messageIdList) {
                            System.out.println("Message Id is " + mid);
                            UsenetPostHeaders uphFrom = ws.getPost(mid);
                            if (uphFrom.getReferences() != null) {

                                for (String rmid : uphFrom.getReferences()) {
                                    UsenetPostHeaders uphTo = ws.getPost(rmid);
                                    if (uphTo != null) {

                                        UsenetNetworkEdgeMessage une = new UsenetNetworkEdgeMessage();
                                        une.setDateFrom(from.toGregorianCalendar().getTime());
                                        une.setDateTo(to.toGregorianCalendar().getTime());
                                        une.setIndividualFrom(uphFrom.getSenderId());
                                        une.setIndividualTo(uphTo.getSenderId());
                                        une.setTopic(uphFrom.getNewsgroup());
                                        UsenetPostMessageQueueSender.instance().send(une);
                                    }
                                }
                            }
                        }
                        from.add(DatatypeFactory.newInstance().newDuration("P1M"));
                        to.add(DatatypeFactory.newInstance().newDuration("P1M"));

                    }
                } catch (DatatypeConfigurationException ex) {
                    Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
