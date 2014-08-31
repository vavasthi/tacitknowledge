/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.MessageIngestService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 *
 * @author vavasthi
 */
public class TacitKnowledgeContentParserHandRolled {

    private final File base_;

    private final List<String> headerLines_;
    private final List<UsenetPostHeader> uphDictionary_;
    int totalPosts_ = 0;
    FileWriter fw;
    //TacitKnowledgeInterestingPhaseDetector detector;

    private TacitKnowledgeContentParserHandRolled(File base, File outfile) {
        base_ = base;
        try {

            fw = new FileWriter(outfile);
        } catch (IOException ioex) {
            System.out.println("Can't open file :" + outfile.getAbsolutePath());
            ioex.printStackTrace();
            System.exit(0);
        }
        headerLines_ = new ArrayList<String>();
        uphDictionary_ = new ArrayList<UsenetPostHeader>();
        //detector = new TacitKnowledgeInterestingPhaseDetector();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws DatatypeConfigurationException {
        // TODO code application logic here
        if (args.length < 2) {
            System.out.printf("java  %s crawlDirectory outfile\n", TacitKnowledgeContentParserHandRolled.class.getName());
            System.exit(0);
        }
        TacitKnowledgeContentParserHandRolled parser = new TacitKnowledgeContentParserHandRolled(new File(args[0]), new File(args[1]));
        parser.parse();
        /*try {
         parser.detector.report();
         } catch (IOException ex) {
         Logger.getLogger(TacitKnowledgeContentParserHandRolled.class.getName()).log(Level.SEVERE, null, ex);
         }*/
        System.out.println("Total messages parsed :" + parser.totalPosts_);
    }

    void parse() throws DatatypeConfigurationException {

        if (base_.isDirectory()) {
            Iterator<File> fi
                    = FileUtils.iterateFiles(base_, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
            while (fi.hasNext()) {
                File f = fi.next();
                System.out.println("File =" + f.getAbsolutePath());
                parseMailBox(f);
            }
        } else if (base_.canRead()) {
            parse(base_);
        }
    }

    void parse(File f) throws DatatypeConfigurationException {
        if (f.isDirectory()) {
            System.out.println("Directory " + f.getAbsolutePath());
        } else if (f.canRead()) {
            System.out.println("File =" + f.getAbsolutePath());
            parseMailBox(f);
        }
    }

    void processHeaderLine(String line, UsenetPostHeader uph) {
        try {
            StringTokenizer st = new StringTokenizer(line);
            String headerName = st.nextToken(":").toLowerCase();
            String headerValue = line.substring(headerName.length() + 1).trim();
            processHeaderNameAndValue(headerName, headerValue, uph);
        } catch (StringIndexOutOfBoundsException siex) {
            System.out.println("Error in line " + line);
            siex.printStackTrace();
            System.exit(0);
        }
    }

    void processHeaderNameAndValue(String name, String value, UsenetPostHeader uph) {
        if (name.equals("from")) {
            uph.setSender(value);
        } else if (name.equals("newsgroups")) {
            uph.setNewsgroup(value);
        } else if (name.equals("subject")) {

            uph.setSubject(value);
        } else if (name.equals("date")) {
            uph.setDate(value);
        } else if (name.equals("lines")) {
            try {

                uph.setLines(Integer.parseInt(value));
            } catch (NumberFormatException nfe) {
                uph.setLines(-1);
            }
        } else if (name.equals("message-id")) {
            uph.setId(value);
        } else if (name.equals("references")) {
            uph.addReferences(value);
        } else if (name.equals("content-type")) {
            uph.setContentType(value);
        } else if (name.equals("in-reply-to")) {
            uph.setInReplyTo(value);
        } else if (name.equals("bytes")) {
            try {

                uph.setBytes(Integer.parseInt(value));
            } catch (NumberFormatException nfe) {
                uph.setBytes(-1);
            }
        } else {
//            System.out.println("Ignored header " + name);
        }
    }

    boolean populateHeaderLines(BufferedReader br) {
        try {
            headerLines_.clear();
            String line;
            do {

                line = br.readLine();
                if (line == null) {
                    return false;
                }
                if (line.matches("^\\s+.*")) {
                    String oldLine = headerLines_.remove(headerLines_.size() - 1);
                    oldLine += line;
                    headerLines_.add(oldLine);
                } else {
                    if (!line.isEmpty()) {

                        headerLines_.add(line);
                    }
                }
            } while (!line.isEmpty());
        } catch (IOException ex) {
            Logger.getLogger(TacitKnowledgeContentParserHandRolled.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ArrayIndexOutOfBoundsException aibe) {

            Logger.getLogger(TacitKnowledgeContentParserHandRolled.class.getName()).log(Level.SEVERE, null, aibe);
            System.exit(0);
        }

        return true;
    }

    void populateUsenetPostHeader(UsenetPostHeader uph) {
        for (String line : headerLines_) {
            processHeaderLine(line, uph);
        }
    }

    String populateBodyLines(UsenetPostHeader uph, BufferedReader br, boolean multi) {
        try {
            if (multi) {

                String body = new String();
                String l = br.readLine();
                while (l != null && !l.matches("^From .*\\d\\d")) {
                    body += l + "\n";
                    l = br.readLine();
                }
                uph.setBody(body);
                return l;
            } else {

                String body = new String();
                String l = br.readLine();
                while (l != null) {
                    body += l + "\n";
                    l = br.readLine();
                }
                uph.setBody(body);
                return null;
            }
        } catch (IOException ex) {
            Logger.getLogger(TacitKnowledgeContentParserHandRolled.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    void parseMailBox(File f) throws DatatypeConfigurationException {
        try {
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TacitKnowledgeContentParserHandRolled.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            String line = null;
            boolean multi = false;
            if (f.getAbsolutePath().matches(".*\\.multi$")) {
                multi = true;
            }
            System.out.println("The file contains multiple posts =" + multi);
            try {
                int i = 0;
                do {

                    UsenetPostHeader uph = new UsenetPostHeader();
                    if (populateHeaderLines(br)) {

                        populateUsenetPostHeader(uph);
                        line = populateBodyLines(uph, br, multi);
                        if (line == null) {
                            // In case the header doesn't contain lines or bytes, then
                            // we read until we find next From. In that case we end up
                            // reading one extra line. That line is returned from the 
                            // populateBodyLines function. We only need to read the 
                            // next line if we don't have a non-null line here.
                            line = br.readLine();
                        }
                        //uphDictionary_.add(uph);
                        //detector.incrementalTrain(uph.getBody());
                        addPost(uph);
                    } else {
                        line = null;
                    }
                    ++totalPosts_;
                } while (line != null);
            } catch (IOException ex) {
                Logger.getLogger(TacitKnowledgeContentParserHandRolled.class.getName()).log(Level.SEVERE, null, ex);
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(TacitKnowledgeContentParserHandRolled.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void addPost(UsenetPostHeader uph) throws DatatypeConfigurationException {
        com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.MessageIngestService_Service mis
                = new com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.MessageIngestService_Service();
        MessageIngestService port = mis.getMessageIngestServicePort();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(uph.date_);
        try {

            port.ingestMessage(uph.sender_.getName(),
                    uph.sender_.getAddress(),
                    uph.id_.toLowerCase(),
                    uph.subject_,
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(gc),
                    uph.contentType_,
                    uph.bytes_,
                    uph.lines_,
                    uph.newsgroup_,
                    uph.inReplyTo_,
                    uph.references_,
                    uph.body_);
        } catch (NullPointerException nex) {
            port.ingestMessage(uph.sender_.getName(),
                    uph.sender_.getAddress(),
                    uph.id_.toLowerCase(),
                    uph.subject_,
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(gc),
                    uph.contentType_,
                    uph.bytes_,
                    uph.lines_,
                    uph.newsgroup_,
                    uph.inReplyTo_,
                    uph.references_,
                    uph.body_);

        }
    }
}
