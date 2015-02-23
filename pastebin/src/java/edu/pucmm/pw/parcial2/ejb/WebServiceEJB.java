/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pucmm.pw.parcial2.ejb;

import edu.pucmm.pw.parcial2.entities.Codigo;
import edu.pucmm.pw.parcial2.entities.Expiracion;
import edu.pucmm.pw.parcial2.entities.Sintaxis;
import edu.pucmm.pw.parcial2.entities.Usuario;
import edu.pucmm.pw.parcial2.entities.Visibilidad;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 *
 * @author fmesa
 */
@Stateless
public class WebServiceEJB {
    @EJB
    JpaServiceEJB JPAService;
    
    public String createPaste(Codigo codigo) {
        
        Sintaxis sintaxis = (Sintaxis)JPAService.findObject(Sintaxis.class, codigo.getIdsintaxis().getIdsintaxis());
        Visibilidad visibilidad = (Visibilidad)JPAService.findObject(Visibilidad.class, codigo.getIdvisibilidad().getIdvisibilidad());
        Expiracion expiracion = (Expiracion) JPAService.findObject(Expiracion.class, codigo.getIdexpiracion().getIdexpiracion());
        Usuario usuario = (Usuario) JPAService.findObject(Usuario.class, codigo.getIdusuario().getIdusuario());
        
        Codigo codigoToPersist = new Codigo();
        codigoToPersist.setUrl(UUID.randomUUID().toString());
        codigoToPersist.setTitulo(codigo.getTitulo());
        codigoToPersist.setTexto(codigo.getTexto());
        
        codigoToPersist.setIdusuario(usuario);
        codigoToPersist.setIdsintaxis(sintaxis);
        codigoToPersist.setIdvisibilidad(visibilidad);
        codigoToPersist.setIdexpiracion(expiracion);
        
        codigoToPersist.setFechacreacion(new Date());
        codigoToPersist.setCantidadviews(0);
        codigoToPersist.setTamanoarchivo((float) codigoToPersist.getTexto().getBytes().length);
        codigoToPersist.setFechaactualizacion(new Date());
        JPAService.persist(codigoToPersist);
        
        return codigoToPersist.getUrl();
    }
    
    public String createPaste(Usuario usuario, String password, int idSintaxis, int idExpiracion, int idVisibilidad, String titulo, String code) {
        
        List<Sintaxis> sintaxis = getAllSintaxis();
        List<Visibilidad> visibilidad = getAllVisibilidad();
        List<Expiracion> expiracion = getAllExpiracion();
        
        Codigo codigoToPersist = new Codigo();

        for(Sintaxis s : sintaxis){
            if(s.getIdsintaxis() == idSintaxis){
                codigoToPersist.setIdsintaxis(s);
                break;
            }
        }
        for(Visibilidad v : visibilidad){
            if(v.getIdvisibilidad() == idVisibilidad){
                codigoToPersist.setIdvisibilidad(v);
                break;
            }
        }
         for(Expiracion e : expiracion){
            if(e.getTiempo() == idExpiracion){
                codigoToPersist.setIdexpiracion(e);
                break;
            }
        }
        
        codigoToPersist.setUrl(UUID.randomUUID().toString());
        if(titulo == null)
            titulo = "Sin Titulo";
        codigoToPersist.setTitulo(titulo);
        codigoToPersist.setTexto(code);
        codigoToPersist.setIdusuario(usuario);
        codigoToPersist.setFechacreacion(new Date());
        codigoToPersist.setCantidadviews(0);
        codigoToPersist.setTamanoarchivo((float) codigoToPersist.getTexto().getBytes().length);
        codigoToPersist.setFechaactualizacion(new Date());
        JPAService.persist(codigoToPersist);
        
        return codigoToPersist.getUrl();
    }
    
    public Codigo findCodigo(String url){
        List<Codigo> codigoList = (List<Codigo>)JPAService.findObject("Codigo.findByUrl", Codigo.class, "url", url);
        if(!codigoList.isEmpty()){
            return codigoList.get(0);
        }
        return null;
    }
    
    public void updateCodigo(Codigo codigo){
        JPAService.updateCodigo(codigo);
    }
    
    public List<Codigo> getUsuarioCodigos(String username){
        List<Usuario> userList = (List<Usuario>) JPAService.findObject("Usuario.findByUsuario", Usuario.class, "usuario", username);
        if(!userList.isEmpty())
            return userList.get(0).getCodigoList();
        else
            return null;
    }
    
    public List<Codigo> getUsuarioPublicCodigos(String username){
        List<Usuario> userList = (List<Usuario>) JPAService.findObject("Usuario.findByUsuario", Usuario.class, "usuario", username);
        if(!userList.isEmpty()){
            List<Codigo> codigos = (List<Codigo>) userList.get(0).getCodigoList();
            for(int i = 0; i < codigos.size(); i++){
                if(!codigos.get(i).getIdvisibilidad().getNombre().equals("Público") && !codigos.get(i).getIdvisibilidad().getNombre().equals("Sin Listar"))
                    codigos.remove(i);
            }
            return codigos;
        }
        else
            return null;
    }
    
    public Usuario validarUsuario(Usuario user){
        List<Usuario> userList = (List<Usuario>) JPAService.findObject("Usuario.findByUsuario", Usuario.class, "usuario", user.getUsuario());
        
        if(!userList.isEmpty()){
            if(userList.get(0).getContrasena().equals(user.getContrasena())){
                return userList.get(0);
            } else {
                return null;
            }
        }
        return null;
    }
    
    public Usuario validarUsuario(String user, String password){
        List<Usuario> userList = (List<Usuario>) JPAService.findObject("Usuario.findByUsuario", Usuario.class, "usuario", user);
        
        if(!userList.isEmpty()){
            if(userList.get(0).getContrasena().equals(password)){
                return userList.get(0);
            } else {
                return null;
            }
        }
        
        return null;
    }
    
    public Usuario findUsuario(String username){
        List<Usuario> userList = (List<Usuario>) JPAService.findObject("Usuario.findByUsuario", Usuario.class, "usuario", username);
        
        if(!userList.isEmpty()){
            return userList.get(0);
        }
        
        return null;
    }
    
    public void updateUsuario(Usuario usuario){
        try {
            JPAService.updateUsuario(usuario);
        } catch (Exception ex) {
            Logger.getLogger(WebServiceEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Codigo> getAllPublicCode(){
        EntityManager em = JPAService.getEntityManager();
        Visibilidad visibilidad = (Visibilidad) JPAService.findObject(Visibilidad.class, 1);

        List<Codigo> codigoList = em.createQuery(
            "SELECT c FROM Codigo c WHERE c.idvisibilidad = :visibilidad ORDER BY c.fechacreacion"
        ).setParameter("visibilidad", visibilidad).setMaxResults(10).getResultList();
        
        return codigoList;
    }

    public List<Sintaxis> getAllSintaxis(){
        return (List<Sintaxis>) JPAService.findObject("Sintaxis.findAll", Sintaxis.class);
    }
    
    public List<Visibilidad> getAllVisibilidad(){
        return (List<Visibilidad>) JPAService.findObject("Visibilidad.findAll", Visibilidad.class);
    }
    
    public List<Expiracion> getAllExpiracion(){
        return (List<Expiracion>) JPAService.findObject("Expiracion.findAll", Expiracion.class);
    }
}
