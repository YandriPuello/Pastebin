/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pucmm.pw.parcial2.servlets;

import edu.pucmm.pw.parcial2.ejb.JpaServiceEJB;
import edu.pucmm.pw.parcial2.entities.Codigo;
import edu.pucmm.pw.parcial2.entities.Expiracion;
import edu.pucmm.pw.parcial2.entities.Sintaxis;
import edu.pucmm.pw.parcial2.entities.Usuario;
import edu.pucmm.pw.parcial2.entities.Visibilidad;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
@WebServlet(name = "UpdatePaste", urlPatterns = {"/UpdatePaste"})
public class UpdatePaste extends HttpServlet {

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
        String url =request.getParameter("id");
        
        
        Codigo codigo = ((List<Codigo>)jpaService.findObject("Codigo.findByUrl", Codigo.class, "url",url )).get(0);
        
        int idSintaxis = Integer.parseInt(request.getParameter("paste_format"));
        int idVisibilidad = Integer.parseInt(request.getParameter("paste_visibility"));
        int idExpiracion = Integer.parseInt(request.getParameter("paste_expire_date"));
        
        List<Sintaxis> sintaxis = (List<Sintaxis>)request.getServletContext().getAttribute("sintaxis");
        List<Visibilidad> visibilidad = (List<Visibilidad>)request.getServletContext().getAttribute("visibilidad");
        List<Expiracion> expiracion = (List<Expiracion>)request.getServletContext().getAttribute("expiracion");
        
        
        for(Sintaxis s : sintaxis){
            if(s.getIdsintaxis() == idSintaxis){
                codigo.setIdsintaxis(s);
                break;
            }
        }
        for(Visibilidad v : visibilidad){
            if(v.getIdvisibilidad() == idVisibilidad){
                codigo.setIdvisibilidad(v);
                break;
            }
        }
        if(idExpiracion != -1){
           for(Expiracion e : expiracion){
               if(e.getTiempo() == idExpiracion){
                   codigo.setIdexpiracion(e);
                   break;
               }
           }
           codigo.setFechaactualizacion(new Date());
        }
        
        codigo.setTitulo(request.getParameter("paste_name"));
        codigo.setTexto(request.getParameter("code"));
        jpaService.updateCodigo(codigo);
        response.sendRedirect("./codigo.jsp?id=" + codigo.getUrl());
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
