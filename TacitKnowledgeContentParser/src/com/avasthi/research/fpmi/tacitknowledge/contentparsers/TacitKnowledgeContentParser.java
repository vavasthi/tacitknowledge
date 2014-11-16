/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import com.aliasi.util.ScoredObject;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetInterestingPhraseMessage;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostPhraseScore;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostWebService;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostWebService_Service;
import java.io.File;
import java.io.FileWriter;
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
public class TacitKnowledgeContentParser {

    private final String email_;
    private final String base_;
    private final static String exceptionWordPattern_ = "("
            + "all|"
            + "another|"
            + "a|"
            + "am|"
            + "an|"
            + "and|"
            + "any|"
            + "anybody|"
            + "anyone|"
            + "anything|"
            + "both|"
            + "each|"
            + "either|"
            + "everybody|"
            + "everyone|"
            + "everything|"
            + "few|"
            + "for|"
            + "from|"
            + "has|"
            + "have|"
            + "he|"
            + "her|"
            + "hers|"
            + "herself|"
            + "him|"
            + "himself|"
            + "his|"
            + "i|"
            + "if|"
            + "is|"
            + "it|"
            + "its|"
            + "itself|"
            + "many|"
            + "me|"
            + "mine|"
            + "more|"
            + "most|"
            + "much|"
            + "my|"
            + "myself|"
            + "neither|"
            + "no one|"
            + "nobody|"
            + "none|"
            + "not|"
            + "nothing|"
            + "of|"
            + "one|"
            + "or|"
            + "other|"
            + "others|"
            + "our|"
            + "ours|"
            + "ourselves|"
            + "please|"
            + "several|"
            + "she|"
            + "some|"
            + "somebody|"
            + "someone|"
            + "something|"
            + "that|"
            + "the|"
            + "their|"
            + "theirs|"
            + "them|"
            + "themselves|"
            + "these|"
            + "they|"
            + "this|"
            + "those|"
            + "to|"
            + "us|"
            + "we|"
            + "what|"
            + "whatever|"
            + "which|"
            + "whichever|"
            + "who|"
            + "whoever|"
            + "whom|"
            + "whomever|"
            + "whose|"
            + "with|"
            + "you|"
            + "your|"
            + "yours|"
            + "yourself|"
            + "yourselves|"
            + "newsgroup|"
            + "subscribe|"
            + "list|)" + "\\b\\s*";

    private TacitKnowledgeContentParser(String base, String email) {
        base_ = base;
        email_ = email;
    }

    private TacitKnowledgeContentParser() {
        base_ = System.getenv("HOME") + "/fpmi/research/datasets/usenet-msgbody";
        System.out.println("Base directory is set to " + base_);
        email_ = null;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TacitKnowledgeContentParser parser;
        if (args.length < 1) {

            parser = new TacitKnowledgeContentParser();
        } else if (args.length < 2) {

            parser = new TacitKnowledgeContentParser(args[0], null);
        } else {

            parser = new TacitKnowledgeContentParser(args[0], args[1]);
        }
        parser.parse();
    }

    void parse() {

        UsenetPostWebService_Service service = new UsenetPostWebService_Service();
        UsenetPostWebService ws = service.getUsenetPostWebServicePort();
        List<Long> userIdList = ws.listIndividualIds();
        System.out.println("Found user ids :" + userIdList.size());
        for (Long uid : userIdList) {
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
                        try {
                            String body = URLDecoder.decode(ws.getMessageBody(mid), "UTF-8");
                            body = body.toLowerCase();
                            body = body.replaceAll("[^a-z0-9]+", " ");
                            body = body.replaceAll("[\\s*\\p{Punct}]+\\s*", " ");
                            body = body.replaceAll("[\\s*\\p{Cntrl}]+\\s*", " ");
                            body = body.replaceAll("\\s*\\b[a-z]\\b\\s*", " ");
                            body = body.replaceAll("\\s*\\b[0-9]+\\b\\s*", " ");
                            body = body.replaceAll(exceptionWordPattern_, " ");
                            body = body.replaceAll("\\s+", " ");
                            body = body.trim();
                            ipd.incrementalTrain(body);
                            ++noMsgs;
                            if (noMsgs == 200) {
                                noMsgs = 0;
                                ipd.model.sequenceCounter().prune(3);
                            }
  //                          fw.write(body);
  //                          fw.close();
                        } catch (IOException ex) {
                            Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
//                            try {
 //                               fw.close();
 //                           } catch (IOException ex) {
 //                               Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
 //                           }
                        }
                    }
                    List<UsenetPostPhraseScore> ppsList = new ArrayList<UsenetPostPhraseScore>();
                    ipd.model.sequenceCounter().prune(3);
                    for (int i = 1; i < 3; ++i) {
                        SortedSet< ScoredObject<String[]>> sso;
                        sso = ipd.model.frequentTermSet(i, 100);
                        String sep1 = " ";
                        String sep2 = "";
                        String sep = sep2;
                        for (ScoredObject<String[]> so : sso) {
                            String[] sa = so.getObject();
                            String phrase = "";
                            for (int j = 0; j < sa.length; ++j) {
                                phrase += sep + sa[j];
                                sep = sep1;
                            }
                            UsenetPostPhraseScore pps = new UsenetPostPhraseScore();
                            pps.setPhrase(phrase.trim());
                            pps.setScore(so.score());
                            pps.setPhraseLength(i);
                            ppsList.add(pps);
                        }
                    }
                    UsenetInterestingPhraseMessage uipm 
                            = new UsenetInterestingPhraseMessage();
                    uipm.setUid(uid);
                    uipm.setFrom(from.toGregorianCalendar().getTime());
                    uipm.setTo(to.toGregorianCalendar().getTime());
                    uipm.setPpsList(ppsList);
                    UsenetPostMessageQueueSender.instance().send(uipm);
//                    ipd.report();
                    from.add(DatatypeFactory.newInstance().newDuration("P1M"));
                    to.add(DatatypeFactory.newInstance().newDuration("P1M"));
                }
//            } catch (IOException ex) {
//                Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DatatypeConfigurationException ex) {
                Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
