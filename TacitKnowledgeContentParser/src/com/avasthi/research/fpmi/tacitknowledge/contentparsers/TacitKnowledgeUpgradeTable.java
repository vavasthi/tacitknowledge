/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import com.avasthi.research.fpmi.tacitknowledge.common.UsenetUpgradeTableMessage;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostWebService;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostWebService_Service;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vavasthi
 */
public class TacitKnowledgeUpgradeTable {

    private TacitKnowledgeUpgradeTable() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TacitKnowledgeUpgradeTable parser;
        parser = new TacitKnowledgeUpgradeTable();
        parser.parse();
    }

    void parse() {

        UsenetPostWebService_Service service = new UsenetPostWebService_Service();
        UsenetPostWebService ws = service.getUsenetPostWebServicePort();
        List<Long> userIdList = ws.listIndividualIds();
        
        System.out.println("Found user ids :" + userIdList.size());
        for (Long uid : userIdList) {
            System.out.println("Upgrading tables for useid :" + uid);
     
//            ws.upgradeTable(uid);
            UsenetUpgradeTableMessage uutm = new UsenetUpgradeTableMessage();
            uutm.setId(uid);
            UsenetPostMessageQueueSender.instance().send(uutm);
            try {
                Thread.sleep(50);
                
                //          try {
//            } catch (DatatypeConfigurationException ex) {
                //              Logger.getLogger(TacitKnowledgeUpgradeTable.class.getName()).log(Level.SEVERE, null, ex);
                //        }
            } catch (InterruptedException ex) {
                Logger.getLogger(TacitKnowledgeUpgradeTable.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
