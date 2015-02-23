/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pucmm.pw.parcial2.servlets;

import edu.pucmm.pw.parcial2.ejb.JpaServiceEJB;
import edu.pucmm.pw.parcial2.entities.Codigo;
import edu.pucmm.pw.parcial2.entities.Visibilidad;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fmesa
 */
@WebServlet(name = "TrendPasteServlet", urlPatterns = {"/TrendPasteServlet", "/trends"})
public class TrendPasteServlet extends HttpServlet {
    
    @EJB
    JpaServiceEJB JpaService;

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
        
        try {
            EntityManager em = JpaService.getEntityManager();
            Visibilidad visibilidad = (Visibilidad) JpaService.findObject(Visibilidad.class, 1);
            
            List<Codigo> codigoList = em.createQuery(
                "SELECT c FROM Codigo c WHERE c.idvisibilidad = :visibilidad ORDER BY c.cantidadviews"
            ).setParameter("visibilidad", visibilidad).setMaxResults(20).getResultList();
            
            request.getSession().setAttribute("codigos", codigoList);
            
            response.sendRedirect(request.getContextPath() + "/trends.jsp");
            
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
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
