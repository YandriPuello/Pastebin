/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pucmm.pw.parcial2.filters;

import edu.pucmm.pw.parcial2.ejb.JpaServiceEJB;
import edu.pucmm.pw.parcial2.entities.Codigo;
import edu.pucmm.pw.parcial2.entities.Usuario;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 *
 * @author fmesa
 */

@WebFilter(filterName = "UsuarioFilter", urlPatterns = {"/u/*"})
public class UsuarioFilter implements Filter {
    
    @EJB
    JpaServiceEJB JPAService;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest requestHttpServlet = (HttpServletRequest) request;

        String userNameParameter = requestHttpServlet.getParameter("user");

        try {            
            List<Usuario> user = (List<Usuario>) JPAService.findObject(
                "Usuario.findByUsuario", Usuario.class, "usuario", userNameParameter
            );
            
            if (user.isEmpty()) {
                requestHttpServlet.getSession().setAttribute("errors", "No se encuentra usuario");
            } else {
                List<Codigo> codigoList = (List<Codigo>) JPAService.findObject(
                    "Codigo.findByUsuario", Codigo.class, "usuario", user.get(0)
                );
                
                Usuario usuario = (Usuario) requestHttpServlet.getSession().getAttribute("usuario");
                
                if(!usuario.getUsuario().equals("guest") && !usuario.getUsuario().equals(userNameParameter)){
                    Integer profileview = user.get(0).getProfileview();
                    if(profileview == null)
                        profileview = 0;
                    profileview++;
                    user.get(0).setProfileview(profileview);
                    JPAService.updateUsuario(user.get(0));
                    Usuario userProfile = (Usuario)JPAService.findObject(Usuario.class, user.get(0).getIdusuario());
                    requestHttpServlet.getSession().setAttribute("usuario", userProfile);
                }
                
                usuario = (Usuario) JPAService.findObject(Usuario.class, user.get(0).getIdusuario());
                requestHttpServlet.getSession().setAttribute("usuarioVisitado", usuario);
                requestHttpServlet.getSession().setAttribute("codigos", codigoList);
            }
            
            chain.doFilter(request, response);

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {

    }    
}
