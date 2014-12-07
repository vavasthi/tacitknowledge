/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import com.aliasi.util.ScoredObject;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetInterestingPhraseMessages;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostPhraseScore;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostHeaders;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostWebService;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostWebService_Service;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
public class TacitKnowledgeContentParser {

    private Long startUserId;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TacitKnowledgeContentParser parser;
        if (args.length > 0) {
            parser = new TacitKnowledgeContentParser(Long.parseLong(args[0]));
        } else {

            parser = new TacitKnowledgeContentParser(null);
        }
        parser.parse();
    }

    private TacitKnowledgeContentParser(Long startUserId) {
        this.startUserId = startUserId;
    }

    void parse() {

        File outFile = new File("output.csv");
        PrintWriter pw = null;
        int kount = 1;
        try {
            pw = new PrintWriter(new FileWriter(outFile));
        } catch (IOException ex) {
            Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
        UsenetPostWebService_Service service = new UsenetPostWebService_Service();
        UsenetPostWebService ws = service.getUsenetPostWebServicePort();
        List<Long> userIdList = ws.listIndividualIds();
        System.out.println("Found user ids :" + userIdList.size());
        boolean skip = false;
        if (startUserId != null) {
            skip = true;
        }
        for (Long uid : userIdList) {
            if (skip && !uid.equals(startUserId)) {
                continue;
            }
            skip = false;
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
                String ng;

                while (from.compare(toMax) == DatatypeConstants.LESSER) {

                    UsenetInterestingPhraseMessages uipms
                            = new UsenetInterestingPhraseMessages();
                    TacitKnowledgeInterestingPhraseDetector ipd
                            = new TacitKnowledgeInterestingPhraseDetector();
                    List<String> messageIdList = ws.listMessageIds(uid, from, to);
                    List<String> topicList = new ArrayList<String>();
                    System.out.println("Found " + messageIdList.size() + " message Ids" + " for user id " + uid + " between dates " + from.toString() + " and " + to.toString());
                    for (String mid : messageIdList) {
                        try {
                            String body = URLDecoder.decode(ws.getMessageBody(mid), "UTF-8");
                            body = body.trim();
                            ipd.incrementalTrain(body);
                            ++noMsgs;
                            if (noMsgs == 200) {
                                noMsgs = 0;
                                ipd.model.sequenceCounter().prune(3);
                            }
                            UsenetPostHeaders uph = ws.getPost(mid);
                            topicList = uph.getTopics();
                        } catch (Exception ex) {
                            Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                        }
                        List<UsenetPostPhraseScore> ppsList = new ArrayList<UsenetPostPhraseScore>();
                        ipd.model.sequenceCounter().prune(3);
                        for (int i = 1; i < 2; ++i) {
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

                        for (String etopic : topicList) {
                            for (UsenetPostPhraseScore upps : ppsList) {
                                String punctMatch = "\\p{Punct}";
                                Pattern r = Pattern.compile(punctMatch, Pattern.CASE_INSENSITIVE);
                                Matcher m = r.matcher(upps.getPhrase());
                                if (!m.find()) {

                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    pw.printf("%d,%d,%d,%s,%f,%s,%s,%s\n", kount, uid, upps.getPhraseLength(), upps.getPhrase(), upps.getScore(), dateFormat.format(from.toGregorianCalendar().getTime()), dateFormat.format(to.toGregorianCalendar().getTime()), etopic);
                                    ++kount;
                                }
                            }
                        }
                    }
                    pw.flush();
                    from.add(DatatypeFactory.newInstance().newDuration("P1M"));
                    to.add(DatatypeFactory.newInstance().newDuration("P1M"));
                }

            } catch (DatatypeConfigurationException ex) {
                Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
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
