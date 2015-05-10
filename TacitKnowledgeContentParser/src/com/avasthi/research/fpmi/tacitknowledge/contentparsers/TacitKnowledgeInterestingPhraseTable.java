/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.InterestingPhrase;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.MinMaxDatePair;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostWebService;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostWebService_Service;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class TacitKnowledgeInterestingPhraseTable {

    private String topic;
    private File outFile;
    private Double limit;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TacitKnowledgeInterestingPhraseTable parser;
        
        if (args.length > 2) {
            parser = new TacitKnowledgeInterestingPhraseTable(args[0], args[1], Double.parseDouble(args[2]));
        }
        else if (args.length > 1) {
            parser = new TacitKnowledgeInterestingPhraseTable(args[0], args[1], 0.5);
        }
        else if (args.length > 0) {
            parser = new TacitKnowledgeInterestingPhraseTable(args[0], "outfile.csv", 0.5);
        } else {

            parser = new TacitKnowledgeInterestingPhraseTable("sci.physics", "outfile.csv", 0.5);
        }
        parser.parse();
    }

    private TacitKnowledgeInterestingPhraseTable(String topic, String outputPath, double limit) {
        this.topic = topic;
        this.outFile = new File(outputPath);
        this.limit = limit;
    }

    private List<Date> getFromDates(XMLGregorianCalendar min, XMLGregorianCalendar max) {
        
        List<Date> dates = new ArrayList<Date>();
        XMLGregorianCalendar xgc = min;
        xgc.setDay(1);
        do {
            try {
                dates.add(xgc.toGregorianCalendar().getTime());
                xgc.add(DatatypeFactory.newInstance().newDuration("P1M"));
            } catch (DatatypeConfigurationException ex) {
                Logger.getLogger(TacitKnowledgeInterestingPhraseTable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        while (xgc.compare(max) == DatatypeConstants.LESSER);
        return dates;
    }
    void parse() {

        PrintWriter pw = null;
        int kount = 1;
        try {
            pw = new PrintWriter(new FileWriter(outFile));
        } catch (IOException ex) {
            Logger.getLogger(TacitKnowledgeInterestingPhraseTable.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
        UsenetPostWebService_Service service = new UsenetPostWebService_Service();
        UsenetPostWebService ws = service.getUsenetPostWebServicePort();
        MinMaxDatePair mmdp = ws.getMinMaxDates(topic);
        List<Date> dates = getFromDates(mmdp.getMinDate(), mmdp.getMaxDate());
        Date[] dateArray = new Date[dates.size()];
        dateArray = dates.toArray(dateArray);
        Map<String, Double[]> topicMap = new HashMap<String, Double[]>();
        XMLGregorianCalendar xgc = mmdp.getMinDate();
        xgc.setDay(1);
        for (int i = 0; i < dateArray.length; ++i) {
            
            Calendar c = new GregorianCalendar();
            c.setTime(dateArray[i]);
            List<InterestingPhrase> phraseList 
                = ws.getInterestingPhrasesForNewsgroupForYear(topic, c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
            for (InterestingPhrase ip : phraseList) {
                Double[] values = topicMap.get(ip.getWord());
                if (values == null) {
                    values = new Double[dateArray.length];
                }
                values[i] = ip.getSentiment();
                topicMap.put(ip.getWord(), values);
            }
        }
        Map<String, Boolean> shouldKeep = new HashMap<String, Boolean>();
        {
            
            Set<Map.Entry<String,Double[]>> entries = topicMap.entrySet();
            for (Map.Entry<String,Double[]> entry : entries) {
                double m = 0.0;
                for (int i = 0; i < entry.getValue().length; ++i) {
                    double tmp = 0.0;
                    if (entry.getValue()[i] != null) {
                        tmp = entry.getValue()[i].doubleValue();
                    }
                    m = Math.max(m, tmp);
                }
                System.out.println("Maximum for " + entry.getKey() + " = " + m);
                if (m >= limit) {
                    shouldKeep.put(entry.getKey(), Boolean.TRUE);
                }
                else {
                    shouldKeep.put(entry.getKey(), Boolean.FALSE);
                }
            }
        }
        Set<String> keySet = topicMap.keySet();
        for (String k : keySet) {
            if (shouldKeep.get(k).booleanValue()) {
                
                pw.print(","+k);
            }
        }
        pw.println();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Set<Map.Entry<String,Double[]>> entries = topicMap.entrySet();
        for (int i = 0; i < dateArray.length; ++i) {
            pw.print(dateFormat.format(dateArray[i]));
            for (Map.Entry<String,Double[]> entry : entries) {
                if (shouldKeep.get(entry.getKey()).booleanValue()) {
                    Double v = entry.getValue()[i];
                    if (v == null) {
                        v = 0.0;
                    }
                    pw.print("," + v);
                }
            }
            pw.println();
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
