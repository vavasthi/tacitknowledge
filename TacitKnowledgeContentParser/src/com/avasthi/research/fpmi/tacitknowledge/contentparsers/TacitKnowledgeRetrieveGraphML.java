/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import com.avasthi.research.fpmi.tacitknowledge.common.UsenetNetworkEdgeMessage;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.NetworkEdge;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.NetworkNode;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostWebService;
import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostWebService_Service;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
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
public class TacitKnowledgeRetrieveGraphML {

    private String startTopic_;
    private String baseDir_;

    private TacitKnowledgeRetrieveGraphML() {
        startTopic_ = null;
        baseDir_ = new File(".").getAbsolutePath();
    }

    private TacitKnowledgeRetrieveGraphML(String baseDir) {
        baseDir_ = baseDir;
        startTopic_ = null;
    }

    private TacitKnowledgeRetrieveGraphML(String baseDir, String startTopic) {
        startTopic_ = startTopic;
        baseDir_ = baseDir;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TacitKnowledgeRetrieveGraphML builder = null;
        if (args.length > 1) {
            builder = new TacitKnowledgeRetrieveGraphML(args[0], args[1]);
        } else if (args.length > 0) {

            builder = new TacitKnowledgeRetrieveGraphML(args[0]);
        } else {
            builder = new TacitKnowledgeRetrieveGraphML();
        }
        builder.getGraphML();
    }

    void getGraphML() {

        UsenetPostWebService_Service service = new UsenetPostWebService_Service();
        UsenetPostWebService ws = service.getUsenetPostWebServicePort();
        try {

            List<String> topics = ws.getTopics();
            boolean skip = true;
            for (String topic : topics) {
                if (startTopic_ == null || topic.equals(startTopic_)) {
                    skip = false;
                }
                if (!skip) {

                    int noMsgs = 0;
                    XMLGregorianCalendar fromMin = ws.getMinDate();
                    XMLGregorianCalendar toMax = ws.getMaxDate();
                    XMLGregorianCalendar from = DatatypeFactory.newInstance().newXMLGregorianCalendar(fromMin.toGregorianCalendar());
                    from.setHour(0);
                    from.setMinute(0);
                    from.setSecond(0);
                    from.setTimezone(0);
                    XMLGregorianCalendar to = DatatypeFactory.newInstance().newXMLGregorianCalendar(fromMin.toGregorianCalendar());
                    to.setHour(0);
                    to.setMinute(0);
                    to.setSecond(0);
                    to.setTimezone(0);
                    to.add(DatatypeFactory.newInstance().newDuration("P6M"));
                    toMax.add(DatatypeFactory.newInstance().newDuration("P6M"));
                    do {

                        System.out.println("Building the graph between " + from.toString() + " and " + to.toString() + " for " + topic);
                        File destDir = new File(baseDir_ + "/monthly/" + topic);
                        if (!destDir.exists()) {
                            destDir.mkdirs();
                        }
                        File targetFile = new File(destDir, "knowledge-network-monthly-" + topic + "-from-" + from.toString() + "-to-" + to.toString() + ".graphml");

                        List<NetworkNode> nodes = ws.getNetworkNodes(from, to, topic);
                        if (nodes.size() > 0) {

                            FileWriter fw = new FileWriter(targetFile);
                            fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                            fw.write("<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\" ");
                            fw.write("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
                            fw.write("xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns ");
                            fw.write("http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">\n");
                            fw.write("<key id=\"topic\" for=\"edge\" attr.name=\"topic\" attr.type=\"string\"/>\n");
                            fw.write("<key id=\"weight\" for=\"edge\" attr.name=\"weight\" attr.type=\"long\"/>\n");
                            fw.write("<graph id=\"KnowledgeNetwork\" edgedefault=\"directed\">\n");
                            for (NetworkNode node : nodes) {
                                fw.write("<node id=\"" + node.getId() + "\"/>\n");
                            }
                            for (NetworkNode source : nodes) {
                                for (NetworkNode target : nodes) {
                                    if (source != target && !source.equals(target)) {

                                        List<NetworkEdge> edges = ws.getNetworkEdges(source.getId(), target.getId(), from, to, topic);
                                        for (NetworkEdge edge : edges) {
                                            fw.write("<edge id=\"" + edge.getId() + "\" source=\"" + edge.getIndividualFrom() + "\" target=\"" + edge.getIndividualTo() + "\">\n");
                                            fw.write("\t<data key=\"topic\">" + edge.getTopic() + "</data>\n");
                                            fw.write("\t<data key=\"weight\">" + edge.getCount() + "</data>\n");
                                            fw.write("</edge>\n");
                                        }
                                    }
                                }
                            }
                            fw.write("</graph>\n");
                            fw.write("</graphml>\n");
                            fw.close();
                        }
                        from.add(DatatypeFactory.newInstance().newDuration("P3M"));
                        to.add(DatatypeFactory.newInstance().newDuration("P3M"));
                    } while (to.compare(toMax) == DatatypeConstants.LESSER);
                }
            }

        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(TacitKnowledgeContentParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TacitKnowledgeRetrieveGraphML.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }
}
