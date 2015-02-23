/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pucmm.pw.parcial2.ws;

import edu.pucmm.pw.parcial2.ejb.WebServiceEJB;
import edu.pucmm.pw.parcial2.entities.Codigo;
import edu.pucmm.pw.parcial2.entities.Expiracion;
import edu.pucmm.pw.parcial2.entities.Sintaxis;
import edu.pucmm.pw.parcial2.entities.Usuario;
import edu.pucmm.pw.parcial2.entities.Visibilidad;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author fmesa
 */
@WebService(serviceName = "SOAPWebService")
public class PastebinSOAPWS {
    @EJB
    private WebServiceEJB ws;
    
    /**
     * Creación de un {@link Codigo}
     * @param codigo Codigo con la siguientes propiedades incluidas: <br>
     *                  <i>{@link Sintaxis}: {@link Codigo#idsintaxis}</i>,<br>
     *                  <i>{@link Visibilidad}: {@link Codigo#idvisibilidad}</i>  <br>
     *                  <i>{@link Expiracion}: {@link Codigo#idexpiracion}</i> <br>
     *                  <i>{@link Usuario}: {@link Codigo#idusuario}</i> 
     * @return {@link Codigo#url}
     */
    @WebMethod(operationName = "createPaste")
    public String createPaste(@WebParam(name = "codigo") Codigo codigo) {
        return ws.createPaste(codigo);
    }
    
    /**
     * Obtener el {@link Codigo} de un {@link Codigo#url}
     * @param url Identificacion {@link Codigo#url} única del {@link Codigo}
     * @return retorna el {@link Codigo} a buscar
     */
    @WebMethod(operationName = "findCodigo")
    public Codigo findCodigo(@WebParam(name = "url") String url) {
        return ws.findCodigo(url);
    }
    
    /**
     * Actualiza un {@link Codigo}
     * @param codigo vease {@link Codigo} a actualizar
     */
    @WebMethod(operationName = "updateCodigo")
    public void updateCodigo(@WebParam(name = "codigo") Codigo codigo) {
        ws.updateCodigo(codigo);
    }

    /**
     * Obtener lista de {@link Codigo} de un {@link Usuario}
     * @param username Nombre de{@link Usuario}l Usuario {@link Usuario#usuario}
     * @return retorna la Lista de {@link Codigo} de un {@link Usuario}
     */
    @WebMethod(operationName = "getUsuarioCodigos")
    public List<Codigo> getUsuarioCodigos(@WebParam(name = "username") String username) {
        return ws.getUsuarioCodigos(username);
    }
    
    /**
     * Validación de un {@link Usuario}
     * @param user el {@link Usuario} debe tener {@link Usuario#usuario} y {@link Usuario#contrasena} 
     * @return null no es valido, de lo contrario, retorna el {@link Usuario}
     */
    @WebMethod(operationName = "validarUsuario")
    public Usuario validarUsuario(@WebParam(name = "user") Usuario user) {
        return ws.validarUsuario(user);
    }
    
    /**
     * Busqueda del {@link Usuario} por su {@link Usuario#usuario}
     * @param username  el {@link Usuario#usuario} del {@link Usuario} 
     * @return null no existe, de lo contrario, retorna el {@link Usuario}
     */
    @WebMethod(operationName = "findUsuario")
    public Usuario findUsuario(@WebParam(name = "username") String username) {
        return ws.findUsuario(username);
    }
    
    /**
     * Actualiza el {@link Usuario} 
     * @param usuario  el {@link Usuario} 
     */
    @WebMethod(operationName = "updateUsuario")
    public void updateUsuario(@WebParam(name = "usuario") Usuario usuario) {
        ws.updateUsuario(usuario);
    }
    
    /**
     * Obtener lista de codigos públicos
     * @return Lista de codigos
     */
    @WebMethod(operationName = "getAllPublicCode")
    public List<Codigo> getAllPublicCode() {
        return ws.getAllPublicCode();
    }
    
    /**
     * Obtener lista de sintaxis
     * @return Lista de Sintaxis
     */
    @WebMethod(operationName = "getAllSintaxis")
    public List<Sintaxis> getAllSintaxis() {
        return ws.getAllSintaxis();
    }
    
    /**
     * Obtener lista de visibilidad
     * @return Lista de Visibilidad
     */
    @WebMethod(operationName = "getAllVisibilidad")
    public List<Visibilidad> getAllVisibilidad() {
        return ws.getAllVisibilidad();
    }
    
    /**
     * Obtener lista de expiración
     * @return Lista de Expiración
     */
    @WebMethod(operationName = "getAllExpiracion")
    public List<Expiracion> getAllExpiracion() {
        return ws.getAllExpiracion();
    }    
}