/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import com.aliasi.util.ScoredObject;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetInitiatePeriodicPhraseAdjacencyCalculation;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetInitiatePhraseAdjacencyCalculation;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetInitiatePhraseNetworkEdge;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetInterestingPhraseMessages;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostPhraseScore;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPost;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostHeaders;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostWebService;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostWebService_Service;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetTopic;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author vavasthi
 */
public class TacitKnowledgeGeneratePhraseEdgeNetwork {

    String[] topics;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        TacitKnowledgeGeneratePhraseEdgeNetwork parser;
        if (args.length > 0) {
            parser = new TacitKnowledgeGeneratePhraseEdgeNetwork(args);
        } else {

            parser = new TacitKnowledgeGeneratePhraseEdgeNetwork(new String[0]);
        }
        parser.parse();
    }

    private TacitKnowledgeGeneratePhraseEdgeNetwork(String[] topics) {
        this.topics = topics;
    }

    void parse() throws InterruptedException {

        int kount = 1;
        int limit = 50;
        for (String topic : topics) {
            UsenetPostWebService_Service service = new UsenetPostWebService_Service();
            UsenetPostWebService ws = service.getUsenetPostWebServicePort();
            int count = ws.countUsenetPostsForTopic(topic);
            int noOfMessages = count / limit;
            ++noOfMessages;

            for (int i = 0; i < noOfMessages; ++i) {
                UsenetInitiatePhraseNetworkEdge msg
                        = new UsenetInitiatePhraseNetworkEdge(topic, limit, i * limit);
                UsenetPostMessageQueueSender.instance().send(msg);
                System.out.println("Sending messages for topic = " + topic + " limit = " + limit + " offset = " + i * limit);
            }
        }
    }
}
/*                            UsenetInterestingPhraseMessage uipm
 = new UsenetInterestingPhraseMessage();
 uipm.setUid(uid);
 uipm.setFrom(from.toGregorianCalendar().getTime());
 uipm.setTo(to.toGregorianCalendar().getTime());
 uipm.setPpsList(ppsList);
 for (String etopic : topicList) {
 String topic = etopic;
 try {
 topic = URLDecoder.decode(etopic, "UTF-8");
 } catch (UnsupportedEncodingException ex) {
 Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
 }
 uipm.setTopic(topic);
 uipms.addMessage(uipm);
 System.out.printf("%d,%d,%d,\"%d\",%d,%s,%s,%s\n", kount, uid, 1, )
 if (uipms.getMessages().size() > 250) {
                                    
 UsenetPostMessageQueueSender.instance().send(uipms);
 uipms = new UsenetInterestingPhraseMessages();
 }*/

//                    ipd.report();
//                 }
                    /*                        if (uipms.getMessages().size() > 0) {
                            
 UsenetPostMessageQueueSender.instance().send(uipms);
 }*/
//            } catch (IOException ex) {
//                Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
