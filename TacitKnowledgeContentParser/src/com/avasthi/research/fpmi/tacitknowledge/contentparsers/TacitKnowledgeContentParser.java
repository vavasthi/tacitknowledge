/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

/**
 *
 * @author vavasthi
 */
public class TacitKnowledgeContentParser {

    private final File base_;

    private TacitKnowledgeContentParser(File base) {
        base_ = base;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if (args.length < 1) {
            System.out.printf("java  %s crawlDirectory", TacitKnowledgeContentParser.class.getName());
            System.exit(0);
        }
        TacitKnowledgeContentParser parser = new TacitKnowledgeContentParser(new File(args[0]));
        parser.parse();
    }

    void parse() {

        if (base_.isDirectory()) {
            Iterator<File> fi
                    = FileUtils.iterateFiles(base_, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
            while (fi.hasNext()) {
                File f = fi.next();
                parseMailBox(f);
            }
        } else if (base_.canRead()) {
            parse(base_);
        }
    }

    void parse(File f) {
        if (f.isDirectory()) {
            System.out.println("Directory " + f.getAbsolutePath());
        } else if (f.canRead()) {
            System.out.println("File =" + f.getAbsolutePath());
            parseMailBox(f);
        }
    }

    void parseMailBox(File f) {
        try {
            Properties props = new Properties();
            props.setProperty("mstor.mbox.metadataStrategy", "xml");
            Session session = Session.getDefaultInstance(props);
            Store store = session.getStore(new URLName("mstor:" + f.getAbsolutePath()));
            store.connect();
            Folder inbox = store.getDefaultFolder();
            inbox.open(Folder.READ_ONLY);
            int msgCount = inbox.getMessageCount();
            System.out.println("Number of messages = " + msgCount + " " + inbox.getDeletedMessageCount());
            for (int i = 0; i < msgCount; ++i) {
                Message m = inbox.getMessage(i + 1);
                System.out.println(f.getAbsoluteFile() + " " + m.getFrom() + m.getSubject());
            }
            store.close();
        } catch (MessagingException ex) {
            Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*    void parseMailBox(File f) {
     try {
     InputStream is = new FileInputStream(f);
     Metadata md = new Metadata();
     BodyContentHandler ch = new BodyContentHandler();
     AutoDetectParser parser = new AutoDetectParser();

     String mimeType = new Tika().detect(f);
     mimeType = "message/rfc822";
     md.set(Metadata.CONTENT_TYPE, mimeType);

     parser.parse(is, ch, md, new ParseContext());
     for(int i = 0;i< md.names().length;i++) {
     String item = md.names()[i];
     System.out.println(item + " -- " + md.get(item));
     }
     } catch (FileNotFoundException ex) {
     Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
     } catch (IOException ex) {
     Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
     } catch (SAXException ex) {
     Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
     } catch (TikaException ex) {
     Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
     }
     }*/
}
