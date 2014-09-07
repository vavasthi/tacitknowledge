/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import com.aliasi.util.ScoredObject;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostPhraseScore;
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
                    + "yourselves)" + "\\b";

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

        File base = new File(base_);
        UsenetPostWebService_Service service = new UsenetPostWebService_Service();
        UsenetPostWebService ws = service.getUsenetPostWebServicePort();
        List<Long> userIdList = ws.listIndividualIds();
        System.out.println("Found user ids :" + userIdList.size());
        for (Long uid : userIdList) {
            try {
                File userDir = new File(base, "" + uid);
                if (!userDir.isDirectory()) {
                    userDir.mkdirs();
                }
                TacitKnowledgeInterestingPhraseDetector ipd
                        = new TacitKnowledgeInterestingPhraseDetector();
                System.out.println("Searching " + " for user id " + uid);
                List<String> messageIdList = ws.listMessageIds(uid);
                for (String mid : messageIdList) {
                    System.out.println("Found " + mid + " message Ids" + " for user id " + uid);
                    FileWriter fw = null;
                    try {
                        File msgFile = new File(userDir, mid);
                        fw = new FileWriter(msgFile);
                        String body = URLDecoder.decode(ws.getMessageBody(mid), "UTF-8");
                        body = body.toLowerCase();
                        body = body.replaceAll("[^a-z0-9@ ]", "");
                        body = body.replaceAll(exceptionWordPattern_, "");
                        ipd.incrementalTrain(body);
                        fw.write(body);
                        fw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            fw.close();
                        } catch (IOException ex) {
                            Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                List<UsenetPostPhraseScore> ppsList = new ArrayList<UsenetPostPhraseScore>();
                for (int i = 1; i < 4; ++i) {
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
                        pps.setPhrase(phrase);
                        pps.setScore(so.score());
                        pps.setPhraseLength(i);
                        ppsList.add(pps);
                    }
                }
                ws.insertPhrases(uid, ppsList);
                ipd.report();
            } catch (IOException ex) {
                Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
