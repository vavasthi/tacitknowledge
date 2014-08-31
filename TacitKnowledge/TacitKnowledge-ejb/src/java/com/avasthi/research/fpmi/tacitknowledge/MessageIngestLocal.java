/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.avasthi.research.fpmi.tacitknowledge;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vavasthi
 */
@Local
public interface MessageIngestLocal {

    public void ingestMessage(String senderName, 
            String senderEmail, 
            String id, 
            String subject, 
            Date date, 
            String contentType, 
            int bytes, 
            int lines, 
            String newsgroup, 
            String inReplyTo, 
            List<String> references, 
            String body);
}
