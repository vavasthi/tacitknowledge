/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import com.aliasi.util.ScoredObject;
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
public class TacitKnowledgePerMessagePhrases {

    private Long startUserId;
    Set<Pair<String, String> > probabilityExistenceSet = new HashSet<>();
    Map<Pair<String, String>, Double> conditionalProbabilityMap = new HashMap<>();
    
    class Pair<T1, T2> {

        public Pair(T1 value1, T2 value2) {
            this.value1 = value1;
            this.value2 = value2;
        }

        
        public T1 getValue1() {
            return value1;
        }

        public void setValue1(T1 value1) {
            this.value1 = value1;
        }

        public T2 getValue2() {
            return value2;
        }

        public void setValue2(T2 value2) {
            this.value2 = value2;
        }
        @Override
        public int hashCode() {
            int hash = 5;
            hash = 79 * hash + Objects.hashCode(this.value1);
            hash = 79 * hash + Objects.hashCode(this.value2);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Pair<?, ?> other = (Pair<?, ?>) obj;
            if (!Objects.equals(this.value1, other.value1)) {
                return false;
            }
            if (!Objects.equals(this.value2, other.value2)) {
                return false;
            }
            return true;
        }

        
        T1 value1;
        T2 value2;
    }

    void populateProbability(String phrase1, String phrase2, double count) {
        Pair<String, String> key = new Pair<>(phrase1, phrase2);
        Pair<String, String> revKey = new Pair<>(phrase2, phrase1);
        if (!probabilityExistenceSet.contains(key)) {
            if (!probabilityExistenceSet.contains(key)) {
                probabilityExistenceSet.add(key);
                conditionalProbabilityMap.put(key, count);
            }
            else {
                count += conditionalProbabilityMap.get(revKey);
                conditionalProbabilityMap.put(revKey, count);
            }
        }
        else {
            
            count += conditionalProbabilityMap.get(key);
            conditionalProbabilityMap.put(key, count);
        }
    }
    private void printTables() {
        for (Pair<String, String> key : probabilityExistenceSet) {
            double count = conditionalProbabilityMap.get(key);
            System.out.printf("%s,%s,%f\n", key.value1, key.value2, count);
        }
    }
    void increaseCounts(SortedSet< ScoredObject<String[]>> sso) {
        String first = null;
        double firstCount = 0;
        for (ScoredObject<String[]> so : sso) {
            String[] sa = so.getObject();
            String phrase = "";
            String sep1 = " ";
            String sep2 = "";
            String sep = sep2;
            for (int j = 0; j < sa.length; ++j) {
                phrase += sep + sa[j];
                sep = sep1;
            }
            if (first == null) {
                first = phrase;
                firstCount = so.score();
                populateProbability(first, first, firstCount);
            }
            else {
                
                populateProbability(first, phrase, so.score());
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TacitKnowledgePerMessagePhrases parser;
        if (args.length > 0) {
            parser = new TacitKnowledgePerMessagePhrases(Long.parseLong(args[0]));
        } else {

            parser = new TacitKnowledgePerMessagePhrases(null);
        }
        parser.parse();
    }

    private TacitKnowledgePerMessagePhrases(Long startUserId) {
        this.startUserId = startUserId;
    }

    void parse() {

        File outFile = new File("output.csv");
        PrintWriter pw = null;
        int kount = 1;
        try {
            pw = new PrintWriter(new FileWriter(outFile));
        } catch (IOException ex) {
            Logger.getLogger(TacitKnowledgePerMessagePhrases.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
        UsenetPostWebService_Service service = new UsenetPostWebService_Service();
        UsenetPostWebService ws = service.getUsenetPostWebServicePort();
        List<Long> users = ws.listIndividualIds();
        for (long u : users) {

            XMLGregorianCalendar fromMin = ws.getMinDateForUser(u);
            XMLGregorianCalendar toMax = ws.getMaxDateForUser(u);

            List<String> messageIds = ws.listMessageIds(u, fromMin, toMax);
            for (String m : messageIds) {
                UsenetPostHeaders uph = ws.getPost(m);
                for (String topic : uph.getTopics()) {

                    try {
                        int noMsgs = 0;
                        UsenetInterestingPhraseMessages uipms
                                = new UsenetInterestingPhraseMessages();
                        TacitKnowledgeInterestingPhraseDetector ipd
                                = new TacitKnowledgeInterestingPhraseDetector();
                        String body = URLDecoder.decode(ws.getMessageBody(m), "UTF-8");
                        body = body.trim();
                        ipd.incrementalTrain(body);
                        ipd.model.sequenceCounter().prune(3);
                        SortedSet< ScoredObject<String[]>> sso = ipd.model.frequentTermSet(1, 100);
                        increaseCounts(sso);
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(TacitKnowledgePerMessagePhrases.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(TacitKnowledgePerMessagePhrases.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        printTables();
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
