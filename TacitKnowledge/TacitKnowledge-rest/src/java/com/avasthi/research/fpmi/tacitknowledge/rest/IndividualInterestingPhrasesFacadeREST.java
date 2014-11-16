/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.rest;

import com.avasthi.research.fpmi.tacitknowledge.ws.InterestingPhrase;
import com.avasthi.research.fpmi.tacitknowledge.ws.UsenetPostWebService;
import com.avasthi.research.fpmi.tacitknowledge.ws.UsenetPostWebService_Service;
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
    @Path("{topic}/{year}")
    @Produces({"application/xml", "application/json"})
    public java.util.List<InterestingPhrase> find(@PathParam("topic") String topic, @PathParam("year") int year) {

        LOG.info("The topic is " + topic + " for the year " + year);
        UsenetPostWebService port = service.getUsenetPostWebServicePort();
        return port.getInterestingPhrasesForNewsgroupForYear(topic, year);
    }
    private static final Logger LOG = Logger.getLogger(IndividualInterestingPhrasesFacadeREST.class.getName());
    
}
