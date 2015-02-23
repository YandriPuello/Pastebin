/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pucmm.pw.parcial2.servlets;

import edu.pucmm.pw.parcial2.ejb.JpaServiceEJB;
import edu.pucmm.pw.parcial2.entities.Tipousuario;
import edu.pucmm.pw.parcial2.entities.Usuario;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fmesa
 */
@WebServlet(name = "PermisosUsuarioServlet", urlPatterns = {"/PermisosUsuarioServlet", "/PermisosUsuarios"})
public class PermisosUsuarioServlet extends HttpServlet {
    
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
        int userCount = ((List<Usuario>) request.getServletContext().getAttribute("usuarioList")).size();
        for(int i = 1; i < userCount; i++){
            String username = request.getParameter("userName"+i);
            String tipoUsurio = request.getParameter("tipoUsuarioList"+i);
            
            try {
                Usuario user = ((List<Usuario>) JPAService.findObject(
                    "Usuario.findByUsuario", Usuario.class, "usuario", username)
                ).get(0);
                
                Tipousuario tipoUsuario = ((List<Tipousuario>) JPAService.findObject(
                    "Tipousuario.findByNombre", Tipousuario.class, "nombre", tipoUsurio)
                ).get(0);
                
                user.setIdtipousuario(tipoUsuario);
                
                JPAService.updateUsuario(user);
                
                List<Usuario> usuarioList = (List<Usuario>) JPAService.findObject(
                    "Usuario.findAll", Usuario.class
                );
                
                getServletContext().setAttribute("usuarioList", usuarioList);
                
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
                throw new RuntimeException(e);
            }  
        }
        
        response.sendRedirect(getServletContext().getContextPath() + "/manage.jsp");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
