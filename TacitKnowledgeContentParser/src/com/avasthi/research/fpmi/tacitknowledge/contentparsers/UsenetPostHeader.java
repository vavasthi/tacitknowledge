/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vavasthi
 */
public class UsenetPostHeader {

    UsenetEmailAddress sender_;
    String id_;
    String subject_;
    Date date_;
    String contentType_;
    int bytes_;
    int lines_;
    String newsgroup_;
    String inReplyTo_;
    List<String> references_;

    private boolean linesSet_;
    private boolean bytesSet_;
    String body_;

    public UsenetPostHeader() {
        references_ = new ArrayList<String>();
        bytesSet_ = false;
        linesSet_ = false;
        contentType_ = new String("text/plain");
        inReplyTo_ = new String("");
        subject_ = new String("");
        newsgroup_ = new String("");
        date_ = new Date();

    }

    void addReferences(String refString) {
        refString = refString.replaceAll(",", " ");
        refString = refString.replaceAll("\\s+", " ");
        StringTokenizer st = new StringTokenizer(refString);
        if (st.countTokens() > 0) {

            while (st.hasMoreTokens()) {
                references_.add(st.nextToken(" ").toLowerCase().replaceAll("[<>]", ""));
            }

        } else {

            references_.add(refString);
        }
    }

    public String getSender() {
        return sender_.getName();
    }

    public void setSender(String sender) {
        sender_ = new UsenetEmailAddress(sender);
    }

    public String getId() {
        return id_;
    }

    public void setId(String id) {
        id_ = id;
    }

    public String getSubject() {
        return subject_;
    }

    public void setSubject(String subject) {
        subject_ = subject;
    }

    public Date getDate() {
        return date_;
    }

    public void setDate(Date date) {
        date_ = date;
    }

    public void setDate(String ds) {

        try {

            if (ds.matches(".*-...-.*:.*:.*")) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d-MMM-y HH:mm:ss z");
                date_ = sdf.parse(ds);
            } else if (ds.matches(".......\\s+\\d+\\s+\\d+:\\d+:\\d+\\s+...\\s+\\d+")) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss z y");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\d+\\s+...\\s+\\d+\\s+\\d+:\\d+:\\d+\\s+\\D{3}?")) {
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM y HH:mm:ss z");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\d+\\s+...\\s+\\d+\\s+\\d+:\\d+:\\d+\\s+.....")) {
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM y HH:mm:ss z");
                date_ = sdf.parse(ds);
            } else if (ds.matches("...,\\s+\\d+\\s+...\\s+\\d+\\s+\\d+:\\d+:\\d+")) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM y HH:mm:ss");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\d+\\s+...\\s+\\d+\\s+\\d+:\\d+:\\d+\\s+\\D{3}?")) {
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM y HH:mm:ss z");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\d+\\s+...\\s+\\d+\\s+\\d+:\\d+\\s+...")) {
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM y HH:mm z");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\D+,\\s+\\d+\\s+...\\s+\\d+\\s+\\d+:\\d+:\\d+\\s+\\D{3,}?")) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM y HH:mm:ss z");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\D+,\\s+\\d+\\s+...\\s+\\d+\\s+\\d+:\\d+:\\d+\\s+.*")) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM y HH:mm:ss X");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\D\\D\\D,\\s+\\d+\\s+\\D\\D\\D\\s+\\d+\\s+\\d+:\\d+\\s+\\D{3}?")) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM y HH:mm z");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\d+\\s+\\D{3}?\\s+\\d+\\s+\\d+:\\d+:\\d+")) {
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM y HH:mm:ss");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\D+\\s+\\D+\\s+\\d+\\s+\\d+:\\d+:\\d+\\s+\\d+\\s+\\D{3,}?")) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss y z");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\d+\\s+\\D{3}?\\s+\\d+\\s+\\d+:\\d+\\s+.{3,5}")) {
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM y HH:mm X");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\d+\\s+\\D{3}?\\s+\\d+\\s+\\d+:\\d+:\\d+\\s+\\D{3,4}?")) {
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM y HH:mm:ss z");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\d+\\s+\\D{3}?\\s+\\d+\\s+\\d+:\\d+:\\d+\\s+[+-]\\d{1,4}?")) {
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM y HH:mm:ss X");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\D{3}?,\\s+\\d+\\s+\\D{3}?\\s+\\d+\\s+\\d+:\\d+")) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM y HH:mm");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\D{3}?,\\s+\\d+\\s+\\D{3}?\\s+\\d+:\\d+:\\d+\\s+\\D{3}?")) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM HH:mm:ss z");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\d+-\\D{3}?-\\d+\\s+\\d+:\\d+\\s+\\D{3}?")) {
                SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-y HH:mm z");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\d+\\s+\\D+\\s+\\d+\\s+\\d+:\\d+\\s+[+-]\\d{4}?")) {
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM y HH:mm X");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\d+\\s+\\D+\\s+\\d+,\\s+\\d+:\\d+:\\d+\\s+\\D{3}?")) {
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM y, HH:mm:ss z");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\d+\\s+\\D+,\\s+\\d+\\s+\\d+:\\d+:\\d+")) {
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM, y HH:mm:ss");
                date_ = sdf.parse(ds);
            } else if (ds.matches("\\d+\\s+\\D+\\s+\\d+\\s+\\d+:\\d+:\\d+\\s+[+-]\\D{2,4}?")) {
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM y HH:mm:ss X");
                date_ = sdf.parse(ds);
            } else if (ds.length() == 14 || ds.length() == 15) {

                SimpleDateFormat sdf = new SimpleDateFormat("d MMM y HH:mm");
                date_ = sdf.parse(ds);
            } else if (ds.length() == 18) {

                SimpleDateFormat sdf = new SimpleDateFormat("d MMM y HH:mm:ss");
                date_ = sdf.parse(ds);
            } else if (ds.length() == 24) {

                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy");
                date_ = sdf.parse(ds);
            } else if (ds.contains(",")) {

                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM y HH:mm:ss Z");
                date_ = sdf.parse(ds);
            } else {

                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");
                date_ = sdf.parse(ds);
            }
        } catch (ParseException ex) {
            Logger.getLogger(UsenetPostHeader.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }

    }

    public String getContentType_() {
        return contentType_;
    }

    public void setContentType(String contentType) {
        contentType = contentType;
    }

    public int getBytes() {
        return bytes_;
    }

    public void setBytes(int bytes) {
        bytesSet_ = true;
        bytes_ = bytes;
    }

    public int getLines() {
        return lines_;
    }

    @Override
    public String toString() {
        return "UsenetPostHeader{" + "sender_=" + sender_ + ", id_=" + id_ + ", subject_=" + subject_ + ", date_=" + date_ + ", contentType_=" + contentType_ + ", bytes_=" + bytes_ + ", lines_=" + lines_ + ", newsgroup_=" + newsgroup_ + ", inReplyTo_=" + inReplyTo_ + ", references_=" + references_ + ", linesSet_=" + linesSet_ + ", bytesSet_=" + bytesSet_ + ", body_=" + body_ + '}';
    }

    public void setLines(int lines) {
        linesSet_ = true;
        lines_ = lines;
    }

    public String getNewsgroup() {
        return newsgroup_;
    }

    public void setNewsgroup(String newsgroup) {
        newsgroup_ = newsgroup;
    }

    public String getInReplyTo() {
        return inReplyTo_;
    }

    public void setInReplyTo(String inReplyTo) {
        inReplyTo_ = inReplyTo;
    }

    public boolean getLinesSet() {
        return linesSet_;
    }

    public boolean getBytesSet() {
        return bytesSet_;
    }

    public String getBody() {
        return body_;
    }

    public void setBody(String body) {
        body_ = body;
    }
}
