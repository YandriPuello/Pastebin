/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pucmm.pw.parcial2.servlets;

import edu.pucmm.pw.parcial2.ejb.JpaServiceEJB;
import edu.pucmm.pw.parcial2.entities.Expiracion;
import edu.pucmm.pw.parcial2.entities.Preferencias;
import edu.pucmm.pw.parcial2.entities.Sintaxis;
import edu.pucmm.pw.parcial2.entities.Usuario;
import edu.pucmm.pw.parcial2.entities.Visibilidad;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 *
 * @author fmesa
 */
@WebServlet(name = "PreferenciaUsuarioServlet", urlPatterns = {"/PreferenciaUsuarioServlet", "/preferencia"})
public class PreferenciaUsuarioServlet extends HttpServlet {

    @EJB
    JpaServiceEJB JPAService;

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

        int idSintaxis = Integer.parseInt(request.getParameter("paste_format"));
        int idVisibilidad = Integer.parseInt(request.getParameter("paste_visibility"));
        int idExpiracion = Integer.parseInt(request.getParameter("paste_expire_date"));

        Usuario user = (Usuario) request.getSession().getAttribute("usuario");

        try {                    
            this.JPAService.updatePreferencias(user.getIdusuario(), idSintaxis, idVisibilidad, idExpiracion);
            user = (Usuario) JPAService.findObject(Usuario.class, user.getIdusuario());
            request.getSession().setAttribute("usuario", user);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }

        response.sendRedirect(request.getContextPath() + "/");
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
