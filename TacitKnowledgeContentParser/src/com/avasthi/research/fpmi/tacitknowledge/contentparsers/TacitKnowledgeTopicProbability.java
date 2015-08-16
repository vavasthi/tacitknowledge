/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import com.aliasi.util.ScoredObject;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.TacitKnowledgePhraseCount;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetInterestingPhraseMessage;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetNetworkEdgeMessage;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostPhraseScore;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.TacitKnowledgePhrasePairProbability;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostHeaders;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostWebService;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostWebService_Service;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
public class TacitKnowledgeTopicProbability {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Long startUserId = null;
        if (args.length == 0) {
            System.out.println("Please provide topic name as argument.");
            System.exit(0);
        }
        TacitKnowledgeTopicProbability builder = new TacitKnowledgeTopicProbability();
        builder.buildNetwork(args[0]);
    }

    void buildNetwork(String topic) {

        try {
            UsenetPostWebService_Service service = new UsenetPostWebService_Service();
            UsenetPostWebService ws = service.getUsenetPostWebServicePort();
            for (TacitKnowledgePhraseCount tkpc : ws.getTopPhraseIds(topic)) {
                
                System.out.println("Phrase " + tkpc.getPhrase());
                List<TacitKnowledgePhrasePairProbability> ppList = ws.getPhrasePairProbability1(URLEncoder.encode(topic, "utf-8"), tkpc.getPhraseId().longValue());
                for (TacitKnowledgePhrasePairProbability tkppp : ppList) {
                    System.out.printf("%s,%s,%s,%f\n", tkppp.getTopic(), tkppp.getPhrase1(), tkppp.getPhrase2(), tkppp.getProbability());
                }
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TacitKnowledgeTopicProbability.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
