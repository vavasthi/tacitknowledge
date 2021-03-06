/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.Dispatch;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Service;
import java.io.StringReader;

/**
 *
 * @author vavasthi
 */
@WebServlet(name = "TacitKnowledgePhraseAdjacency", urlPatterns = {"/PhraseAdjacency"})
public class TacitKnowledgePhraseAdjacency extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String topic = request.getParameter("topic");
            if (topic == null) {
                topic = "sci.physics";
            }
            String phrase = request.getParameter("phrase");
            if (phrase == null) {
                phrase = "us";
            }
            String gotoValue = request.getParameter("goto");
            if (gotoValue != null) {
                phrase = gotoValue;
            }
//            String url = "/rest/adjacency/" + topic + "/" + phrase;
            String url = "/rest/adjacency";
            out.println("<!DOCTYPE html>");
            out.println("<meta charset=\"utf-8\">");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Phrase Adjacency</title>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"phraseadjacency.css\"/>");
            out.println("</head>");
            
            out.println("<body>");
            out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js\"></script>");
            out.println("<script src=\"createAdjacencyGraph.js\"></script>");
            out.println("<script>");
            out.println("createAdjacencyGraph(\"" + url + "\",\"" + topic + "\",\"" + phrase + "\");");
            out.println("</script>");
            out.println("</body>");
            out.println("</html>");

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
