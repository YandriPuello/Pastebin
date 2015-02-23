/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pucmm.pw.parcial2.servlets;

import edu.pucmm.pw.parcial2.ejb.JpaServiceEJB;
import edu.pucmm.pw.parcial2.entities.Codigo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Yandri
 */
@WebServlet(name = "RawPaste", urlPatterns = {"/RawPaste"})
public class RawPaste extends HttpServlet {

    @EJB
    JpaServiceEJB jpaService;
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
       
        Codigo code = ((List<Codigo>)jpaService.findObject("Codigo.findByUrl", Codigo.class, "url", request.getParameter("id"))).get(0);
        validate(code);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>" + code.getTitulo() + " - RawPaste</title>");            
            out.println("</head>");
            out.println("<body>");
            String output = "";
            String text = code.getTexto();
            for(int i = 0; i < text.length(); i++){
                if(text.charAt(i) == '\n'){
                    out.println(output + "</br>");
                    output = "";
                }else{
                    output += text.charAt(i);
                }
            }
            out.println(output);
            ///out.println(code.getTexto());
            out.println("</body>");
            out.println("</html>");
        }
    }
    public void validate(Codigo codigo){
        String code = codigo.getTexto();
        String newCode = "";
        for(int i = 0; i < code.length(); i++){
            if(code.charAt(i) == '<'){
                newCode += "&lt";
            }else if (code.charAt(i) == '>'){
                newCode += "&gt";
            }else{
                newCode += code.charAt(i);
            }
        }
        codigo.setTexto(newCode);
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
