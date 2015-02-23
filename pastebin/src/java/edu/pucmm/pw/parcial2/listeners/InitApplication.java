/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pucmm.pw.parcial2.listeners;

import edu.pucmm.pw.parcial2.ejb.JpaServiceEJB;
import edu.pucmm.pw.parcial2.entities.Alerta;
import edu.pucmm.pw.parcial2.entities.Expiracion;
import edu.pucmm.pw.parcial2.entities.Sintaxis;
import edu.pucmm.pw.parcial2.entities.Tipousuario;
import edu.pucmm.pw.parcial2.entities.Usuario;
import edu.pucmm.pw.parcial2.entities.Visibilidad;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author fmesa
 */
public class InitApplication implements ServletContextListener{
    
    @EJB
    JpaServiceEJB jpaService;
    
    @Override
    public void contextInitialized(ServletContextEvent servletContext) {        
        
        List<Usuario> users = (List<Usuario>)jpaService.findObject("Usuario.findByUsuario", Usuario.class, "usuario", "admin");
        
        try {
            if(users.get(0) == null){
                
                Usuario newUser = new Usuario();
                newUser.setUsuario("admin");
                newUser.setContrasena("admin");
                
                List<Tipousuario> resultList = (List<Tipousuario>)jpaService.findObject("Tipousuario.findByNombre", Tipousuario.class, "nombre", "administrador");
                
                newUser.setIdtipousuario(resultList.get(0));
                jpaService.persist(newUser);
            }
            
            List<Usuario> usuarioList = (List<Usuario>)jpaService.findObject("Usuario.findAll", Usuario.class);
            List<Alerta> alertaList = (List<Alerta>)jpaService.findObject("Alerta.findAll", Alerta.class);
            servletContext.getServletContext().setAttribute("usuarioList", usuarioList);
            servletContext.getServletContext().setAttribute("alertaList", alertaList);
            
            
             
            List<Sintaxis> sintaxislist = (List<Sintaxis>)jpaService.findObject("Sintaxis.findAll", Sintaxis.class);
            List<Visibilidad> visibilidadList = (List<Visibilidad>)jpaService.findObject("Visibilidad.findAll", Visibilidad.class);
            List<Expiracion> expiracionList = (List<Expiracion>)jpaService.findObject("Expiracion.findAll", Expiracion.class);
            servletContext.getServletContext().setAttribute("sintaxis", sintaxislist);
            servletContext.getServletContext().setAttribute("visibilidad", visibilidadList);
            servletContext.getServletContext().setAttribute("expiracion", expiracionList);
            
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent app) {
        System.out.println("Destruyendo la aplicación: " + app.getServletContext().getContextPath());
    }
}