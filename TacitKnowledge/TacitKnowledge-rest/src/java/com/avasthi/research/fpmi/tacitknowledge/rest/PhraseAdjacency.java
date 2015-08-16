/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.rest;

import com.avasthi.research.fpmi.tacitknowledge.common.TacitKnowledgeGraphResponse;
import com.avasthi.research.fpmi.tacitknowledge.common.TacitKnowledgeLinkResponse;
import com.avasthi.research.fpmi.tacitknowledge.common.TacitKnowledgeNodeResponse;
import com.avasthi.research.fpmi.tacitknowledge.ws.TacitKnowledgePhrasePairProbability;
import com.avasthi.research.fpmi.tacitknowledge.ws.UsenetPostWebService;
import com.avasthi.research.fpmi.tacitknowledge.ws.UsenetPostWebService_Service;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
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
@Path("/adjacency")
public class PhraseAdjacency {
    
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/web/UsenetPostWebService.wsdl")
    private UsenetPostWebService_Service service;

    Map<String, Integer> phraseMap = new HashMap<>();
    Map<Integer, TacitKnowledgeNodeResponse> reversePhraseMap = new HashMap<>();
    int maxCount = -1;
    
    public PhraseAdjacency() {
    }

    private int getIndex(String phrase, TacitKnowledgeNodeResponse node) {
        Integer count = phraseMap.get(phrase);
        if (count == null) {
            ++maxCount;
            count = maxCount;
            phraseMap.put(phrase, count);
            reversePhraseMap.put(count, node);
        }
        return count;
    }
    private int getSize(double min, double max, double val) {
        Double retval = (32) * ((val - min) / (max - min));
        return retval.intValue();
    }
    @GET
    @Path("{topic}/{phrase}")
    @Produces({"application/xml", "application/json"})
    public TacitKnowledgeGraphResponse find(@PathParam("topic") String topic, @PathParam("phrase") String phrase) {

        try {
            UsenetPostWebService ws = service.getUsenetPostWebServicePort();
            List<TacitKnowledgePhrasePairProbability> ppList 
                    = ws.getPhrasePairProbability2(URLEncoder.encode(topic, "utf-8"), phrase);
            List<TacitKnowledgeLinkResponse> linkList = new ArrayList<>();
            double minProb = Double.MAX_VALUE;
            double maxProb = Double.MIN_VALUE;
            for (TacitKnowledgePhrasePairProbability tkppp : ppList) {
                if (tkppp.getProbability() > maxProb) {
                    maxProb = tkppp.getProbability();
                }
                if (tkppp.getProbability() < minProb) {
                    minProb = tkppp.getProbability();
                }
            }
            getIndex(phrase, new TacitKnowledgeNodeResponse(phrase, getSize(minProb, maxProb, maxProb)));
            List<TacitKnowledgeNodeResponse> nodeList = new ArrayList<>();
            for (TacitKnowledgePhrasePairProbability tkppp : ppList) {
                TacitKnowledgeNodeResponse node = new TacitKnowledgeNodeResponse(tkppp.getPhrase1(), getSize(minProb, maxProb, tkppp.getProbability()));
                int phrase1Index = getIndex(tkppp.getPhrase1(), node);
                node = new TacitKnowledgeNodeResponse(tkppp.getPhrase2(), getSize(minProb, maxProb, tkppp.getProbability()));
                int phrase2Index = getIndex(tkppp.getPhrase2(), node);
                TacitKnowledgeLinkResponse linkResp = new TacitKnowledgeLinkResponse(phrase1Index, phrase2Index, 1);
                linkList.add(linkResp);
            }
            if (maxCount < 0) {
                return null;
            }
            for (int i = 0; i <= maxCount; ++i) {
                TacitKnowledgeNodeResponse n = reversePhraseMap.get(i);
                nodeList.add(n);
            }
            TacitKnowledgeGraphResponse graph = new TacitKnowledgeGraphResponse(nodeList, linkList);            
            return graph;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PhraseAdjacency.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new TacitKnowledgeGraphResponse();
    }
    private static final Logger LOG = Logger.getLogger(PhraseAdjacency.class.getName());
    
}
