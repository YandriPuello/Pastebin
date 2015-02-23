/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pucmm.pw.parcial2.servlets;

import edu.pucmm.pw.parcial2.ejb.JpaServiceEJB;
import edu.pucmm.pw.parcial2.entities.Alerta;
import edu.pucmm.pw.parcial2.entities.Codigo;
import edu.pucmm.pw.parcial2.entities.Expiracion;
import edu.pucmm.pw.parcial2.entities.Sintaxis;
import edu.pucmm.pw.parcial2.entities.Usuario;
import edu.pucmm.pw.parcial2.entities.Visibilidad;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Yandri
 */
@WebServlet(name = "CreatePaste", urlPatterns = {"/CreatePaste"})
public class CreatePaste extends HttpServlet {

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
        
        int idSintaxis = Integer.parseInt(request.getParameter("paste_format"));
        int idVisibilidad = Integer.parseInt(request.getParameter("paste_visibility"));
        int idExpiracion = Integer.parseInt(request.getParameter("paste_expire_date"));
        String titulo = request.getParameter("paste_name");
        String pasteContent = request.getParameter("code");
        if(pasteContent == null)
            pasteContent = "";
        
        List<Sintaxis> sintaxis = (List<Sintaxis>)request.getServletContext().getAttribute("sintaxis");
        List<Visibilidad> visibilidad = (List<Visibilidad>)request.getServletContext().getAttribute("visibilidad");
        List<Expiracion> expiracion = (List<Expiracion>)request.getServletContext().getAttribute("expiracion");
        
        Codigo newCodigo = new Codigo();
        for(Sintaxis s : sintaxis){
            if(s.getIdsintaxis() == idSintaxis){
                newCodigo.setIdsintaxis(s);
                break;
            }
        }
        for(Visibilidad v : visibilidad){
            if(v.getIdvisibilidad() == idVisibilidad){
                newCodigo.setIdvisibilidad(v);
                break;
            }
        }
         for(Expiracion e : expiracion){
            if(e.getTiempo() == idExpiracion){
                newCodigo.setIdexpiracion(e);
                break;
            }
        }
        
        newCodigo.setIdusuario(((Usuario)request.getSession().getAttribute("usuario")));
        newCodigo.setUrl(UUID.randomUUID().toString());
        if(titulo != null)
            newCodigo.setTitulo(titulo);
        else
            newCodigo.setTitulo("Sin Titulo");
        newCodigo.setTexto(pasteContent);
        newCodigo.setFechacreacion(new Date());
        newCodigo.setCantidadviews(0);
        newCodigo.setTamanoarchivo((float)newCodigo.getTexto().getBytes().length);
        newCodigo.setFechaactualizacion(new Date());
        jpaService.persist(newCodigo);
        
        //alertarUsuarios(newCodigo, (List<Alerta>)request.getServletContext().getAttribute("alertaList"));
        response.sendRedirect("./codigo.jsp?id=" + newCodigo.getUrl());
    }
    
    public void alertarUsuarios(Codigo codigo, List<Alerta> alertas){
        for(Alerta a : alertas){
            if(codigo.getTitulo().contains(a.getPrimerkeyword()) || codigo.getTitulo().contains(a.getSegundokeyword())){
                Usuario usuario = ((List<Usuario>)jpaService.findObject("Usuario.findByIdusuario", Usuario.class, "idusuario", a.getIdusuario().getIdusuario())).get(0);
                
                String to = usuario.getEmail();
                String from = usuario.getEmail();
                String host = "localhost";
                Properties properties = System.getProperties();
                properties.setProperty("mail.smtp.host", host);
                Session session = Session.getDefaultInstance(properties);
 
                try{
                   MimeMessage message = new MimeMessage(session);
                   message.setFrom(new InternetAddress(from));
                   message.addRecipient(Message.RecipientType.TO,
                                            new InternetAddress(to));

                   message.setSubject("Alerta - Pastebin!");
                   message.setText("localhost:8080/parcial2/codigo.jsp?id=" + codigo.getUrl());
                   Transport.send(message);
                   System.out.println("Sent message successfully....");
                }catch (MessagingException mex) {
                   mex.printStackTrace();
                }
            }
        }
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
