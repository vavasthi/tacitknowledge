/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.rest;

import com.avasthi.research.fpmi.tacitknowledge.common.InterestingPhrase;
import com.avasthi.research.fpmi.tacitknowledge.ws.UsenetPostWebService;
import com.avasthi.research.fpmi.tacitknowledge.ws.UsenetPostWebService_Service;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author vavasthi
 */
@Stateless
@Path("/phrases")
public class IndividualInterestingPhrasesFacadeREST {
    
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/web/UsenetPostWebService.wsdl")
    private UsenetPostWebService_Service service;
    
    public IndividualInterestingPhrasesFacadeREST() {
    }

    @GET
    @Path("{topic}/{year}/{month}")
    @Produces({"application/xml", "application/json"})
    public InterestingPhrase[] find(@PathParam("topic") String topic, @PathParam("year") int year, @PathParam("month") int month) {

        LOG.info("The topic is " + topic + " for the year " + year + " and month " + month);
        UsenetPostWebService port = service.getUsenetPostWebServicePort();
        List<com.avasthi.research.fpmi.tacitknowledge.ws.InterestingPhrase> lwsip
                = port.getInterestingPhrasesForNewsgroupForYear(topic, year, month);
        Vector<InterestingPhrase> lip = new Vector<InterestingPhrase>(lwsip.size());
        int i = 0;
        for (com.avasthi.research.fpmi.tacitknowledge.ws.InterestingPhrase wsip : lwsip) {
            lip.add(new InterestingPhrase(wsip.getSentiment(), wsip.getWord()));
        }
        LOG.info("Total words found =" + lip.size());
        return lip.toArray(new InterestingPhrase[lwsip.size()]);
    }
    private static final Logger LOG = Logger.getLogger(IndividualInterestingPhrasesFacadeREST.class.getName());
    
}
