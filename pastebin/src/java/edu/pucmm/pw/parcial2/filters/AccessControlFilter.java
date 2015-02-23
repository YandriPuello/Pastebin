/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pucmm.pw.parcial2.filters;

import edu.pucmm.pw.parcial2.ejb.JpaServiceEJB;
import edu.pucmm.pw.parcial2.entities.Codigo;
import edu.pucmm.pw.parcial2.entities.Usuario;
import edu.pucmm.pw.parcial2.entities.Visibilidad;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Yandri
 */
public class AccessControlFilter implements Filter{
//    @PersistenceContext(unitName = "parcial2_grupo5_PU")
//    private EntityManager em;
//    
//    @Resource
//    private javax.transaction.UserTransaction utx;
    @EJB
    JpaServiceEJB jpaService;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest requestServlet = (HttpServletRequest) request;
        Usuario usuario = (Usuario) requestServlet.getSession().getAttribute("usuario");
        
        if(usuario == null){
            Usuario guest =  ((List<Usuario>)jpaService.findObject("Usuario.findByUsuario", Usuario.class, "usuario", "guest")).get(0);
            requestServlet.getSession().setAttribute("usuario", guest);
            findCodigosPublicos(request);
            chain.doFilter(request, response);
        } else if(usuario.getUsuario().equals("guest")){
            String requestURI = requestServlet.getRequestURI();
            if (    
                    requestURI.contains("setting.jsp") ||
                    requestURI.contains("manage.jsp") ||
                    requestURI.contains("profile.jsp")
               ) 
            {
                String newURI = "./index.jsp";
                findCodigosPublicos(request);
                request.getRequestDispatcher(newURI).forward(request, response);
            } else {
                findCodigosPublicos(request);
                chain.doFilter(request, response);
            }
        } else {
            usuario = (Usuario) jpaService.findObject(Usuario.class, usuario.getIdusuario());
            requestServlet.getSession().setAttribute("usuario", usuario);
            
            findCodigosPublicos(request);
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
    
    public void findCodigosPublicos(ServletRequest request) 
            throws IOException  , ServletException {
        EntityManager em = jpaService.getEntityManager();
        Visibilidad visibilidad = (Visibilidad) jpaService.findObject(Visibilidad.class, 1);

        List<Codigo> codigoList = em.createQuery(
            "SELECT c FROM Codigo c WHERE c.idvisibilidad = :visibilidad ORDER BY c.fechacreacion"
        ).setParameter("visibilidad", visibilidad).setMaxResults(10).getResultList();

        request.getServletContext().setAttribute("codigosPublicos", codigoList);
    }
}
