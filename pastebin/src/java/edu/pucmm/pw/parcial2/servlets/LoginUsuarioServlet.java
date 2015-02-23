/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pucmm.pw.parcial2.servlets;

import edu.pucmm.pw.parcial2.ejb.JpaServiceEJB;
import edu.pucmm.pw.parcial2.entities.Usuario;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fmesa
 */
@WebServlet(name = "LoginUsuarioServlet", urlPatterns = {"/LoginUsuarioServlet", "/LoginUsuario"})
public class LoginUsuarioServlet extends HttpServlet {
    
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
        boolean isInvalid = false;

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Map<String, String> errors = new HashMap<>();
        errors.put("username", "");
        errors.put("password", "");
        
        try {

            List<Usuario> userList = (List<Usuario>)JPAService.findObject(
                "Usuario.findByUsuario", Usuario.class, "usuario", username
            );
            
            if(!userList.isEmpty()){
                Usuario user = userList.get(0);
                if(!user.getContrasena().equals(password)) {
                    errors.put("password", "La contraseña no es válida, intente de nuevo");
                    isInvalid = true;
                }
            } else {
                errors.put("username", "No hay un usuario que esté registrado con ese nombre");
                isInvalid = true;
            }

            if (isInvalid) {
                request.getSession().setAttribute("errors", errors);
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            } else {
                
                request.getSession().setAttribute("usuario", userList.get(0));
                request.getSession().setAttribute("errors", errors);
                response.sendRedirect(request.getContextPath() + "/u/user.jsp?user="+userList.get(0).getUsuario());
            }
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
        return "Servlet que Logea a un Usuario en Pastebin";
    }
}
