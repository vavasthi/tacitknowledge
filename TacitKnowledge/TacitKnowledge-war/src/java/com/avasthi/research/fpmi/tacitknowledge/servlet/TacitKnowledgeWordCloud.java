/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.servlet;

import com.avasthi.research.fpmi.tacitknowledge.contentparsers.ws.client.UsenetPostWebService_Service;
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
@WebServlet(name = "TacitKnowledgeWordCloud", urlPatterns = {"/WordCloud"})
public class TacitKnowledgeWordCloud extends HttpServlet {

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
            String syear = request.getParameter("year");
            int year = 2003;
            if (syear != null) {
                year = Integer.parseInt(syear);
            }
            String smonth = request.getParameter("month");
            int month = 1;
            if (smonth != null) {
                month = Integer.parseInt(smonth);
            }
            String url = "/rest/phrases/" + topic + "/" + year + "/" + month;
            out.println("<!DOCTYPE html>");
            out.println("<meta charset=\"utf-8\">");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TacitKnowledgeWordCloud</title>");
            out.println("</head>");
            out.println("<body style=\"margin:100px\">");
            out.println("<script src=\"d3-cloud/lib/d3/d3.js\"></script>");
            out.println("<script src=\"d3-cloud/d3.layout.cloud.js\"></script>");
            out.println("<script src=\"createWordMap.js\"></script>");
            out.println("<script>");
            out.println("createWordMap(\"" + url + "\");");
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
