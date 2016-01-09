/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.common;

import java.util.List;

/**
 *
 * @author vavasthi
 */
public class TacitKnowledgeGraphResponse {

    public TacitKnowledgeGraphResponse() {
    }

    public TacitKnowledgeGraphResponse(List<TacitKnowledgeNodeResponse> nodes, List<TacitKnowledgeLinkResponse> links) {
        this.nodes = nodes;
        this.links = links;
    }

    public List<TacitKnowledgeNodeResponse> getNodes() {
        return nodes;
    }

    public void setNodes(List<TacitKnowledgeNodeResponse> nodes) {
        this.nodes = nodes;
    }

    public List<TacitKnowledgeLinkResponse> getLinks() {
        return links;
    }

    public void setLinks(List<TacitKnowledgeLinkResponse> links) {
        this.links = links;
    }

    List<TacitKnowledgeNodeResponse> nodes;
    List<TacitKnowledgeLinkResponse> links;
}
