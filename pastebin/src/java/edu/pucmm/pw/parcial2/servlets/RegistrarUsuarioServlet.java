/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pucmm.pw.parcial2.servlets;

import edu.pucmm.pw.parcial2.ejb.JpaServiceEJB;
import edu.pucmm.pw.parcial2.entities.Alerta;
import edu.pucmm.pw.parcial2.entities.Tipousuario;
import edu.pucmm.pw.parcial2.entities.Usuario;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "RegistrarUsuarioServlet", urlPatterns = {"/RegistrarUsuario"})
public class RegistrarUsuarioServlet extends HttpServlet {
    
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
        boolean wasError = false;
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        
        Map<String, String> errors = (HashMap<String, String>) request.getSession().getAttribute("errors");
        
        if(errors == null)
            errors = new HashMap<>();
        else
            errors.clear();
        
        try {
            
            List<Usuario> resultUser = (List<Usuario>)JPAService.findObject(
                "Usuario.findByUsuario", Usuario.class,"usuario", username
            );
                        
            if(!resultUser.isEmpty()){
                errors.put("useraname", "Hay un usuario que está registrado con ese nombre");
                wasError = true;
            }
            
            if(!password.equals(password2)){
                errors.put("password", "Las contraseñas no coinciden");
                wasError = true;
            }
            
            if(!email.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+"
                    + "/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+"
                    + "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")){
                errors.put("email", "No es un email válido");
                wasError = true;
            }
            
            if(wasError){
                request.getSession().setAttribute("errors", errors);
                response.sendRedirect(request.getContextPath() + "/signup.jsp");
            }
            else {
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setUsuario(username);
                nuevoUsuario.setContrasena(password);
                nuevoUsuario.setEmail(email);
                
                Tipousuario resultTipoUsuario = ((List<Tipousuario>)JPAService.findObject(
                    "Tipousuario.findByNombre", Tipousuario.class,"nombre", "normal"
                )).get(0);
                
                nuevoUsuario.setIdtipousuario(resultTipoUsuario);
                
                JPAService.persist(nuevoUsuario);
                
                Alerta alerta = new Alerta();
                alerta.setIdusuario(nuevoUsuario);
                alerta.setPrimerkeyword("");
                alerta.setSegundokeyword("");
                JPAService.persist(alerta);
                
                List<Alerta> alertaList = (List<Alerta>) request.getServletContext().getAttribute("alertaList");
                alertaList.add(alerta);
                request.getServletContext().setAttribute("alertaList", alertaList);
                
                request.getSession().setAttribute("usuario", nuevoUsuario);
                request.getSession().setAttribute("errors", null);
                
                List<Usuario> usuarioList = (List<Usuario>) 
                        request.getServletContext().getAttribute("usuarioList");
                
                usuarioList.add(nuevoUsuario);
                request.getServletContext().setAttribute("usuarioList", usuarioList);
                
                response.sendRedirect(request.getContextPath() + "/index.jsp");
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
        return "Servlet para Registrar Usuarios al Pastebin";
    }// </editor-fold>
    
}
