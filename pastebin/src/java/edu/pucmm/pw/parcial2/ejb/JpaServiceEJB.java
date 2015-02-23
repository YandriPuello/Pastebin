/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pucmm.pw.parcial2.ejb;

import edu.pucmm.pw.parcial2.entities.Alerta;
import edu.pucmm.pw.parcial2.entities.Codigo;
import edu.pucmm.pw.parcial2.entities.Expiracion;
import edu.pucmm.pw.parcial2.entities.Preferencias;
import edu.pucmm.pw.parcial2.entities.Sintaxis;
import edu.pucmm.pw.parcial2.entities.Tipousuario;
import edu.pucmm.pw.parcial2.entities.Usuario;
import edu.pucmm.pw.parcial2.entities.Visibilidad;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author fmesa
 */
@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class JpaServiceEJB {

    @PersistenceContext(unitName = "parcial2_grupo5_PU")
    private EntityManager em;

    @Resource
    private EJBContext context;

    public EntityManager getEntityManager() {
        return this.em;
    }

    @Lock(LockType.WRITE)
    public void persist(Object object) {
        UserTransaction utx = context.getUserTransaction();

        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    @Lock(LockType.READ)
    public Object findObject(Class clase, Object primaryKey) {
        try {
            return em.find(clase, primaryKey);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    @Lock(LockType.READ)
    public Object findObject(String query, Class clase, String column, Object value) {
        try {
            return em.createNamedQuery(query, clase).setParameter(column, value).getResultList();

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    @Lock(LockType.READ)
    public Object findObject(String query, Class clase) {
        try {
            return em.createNamedQuery(query, clase).getResultList();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    @Lock(LockType.WRITE)
    public void deleteObject(Class clase, Object primaryKey) {
        UserTransaction utx = context.getUserTransaction();
        try {
            utx.begin();
            Object find = em.find(clase, primaryKey);
            em.remove(find);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    
    @Lock(LockType.WRITE)
    public void updatePreferencias(
            Integer idUsuario,
            Integer idSintaxis,
            Integer idVisibilidad,
            Integer idExpiracion) throws Exception {
        
        Usuario user = (Usuario) this.findObject(Usuario.class, idUsuario);
        Sintaxis sintaxis = (Sintaxis) this.findObject(Sintaxis.class, idSintaxis);
        Visibilidad visibilidad = (Visibilidad) this.findObject(Visibilidad.class, idVisibilidad);
        Expiracion expiracion = (Expiracion) this.findObject(Expiracion.class, idExpiracion);

        Preferencias settings;
        boolean isCreated = user.getPreferencias() != null;

        if (isCreated) {
            UserTransaction utx = context.getUserTransaction();
            utx.begin();
            settings = (Preferencias) this.findObject(Preferencias.class, user.getIdusuario());
            settings.setIdsintaxis(sintaxis);
            settings.setIdvisibilidad(visibilidad);
            settings.setIdexpiracion(expiracion);
            utx.commit();

        } else {
            settings = new Preferencias();
            settings.setIdusuario(user.getIdusuario());
            settings.setUsuario(user);
            settings.setIdsintaxis(sintaxis);
            settings.setIdvisibilidad(visibilidad);
            settings.setIdexpiracion(expiracion);
            this.persist(settings);
        }
    }
    
    @Lock(LockType.WRITE)
    public void updateUsuario (Usuario usuario) throws Exception {
        UserTransaction utx = context.getUserTransaction();
        utx.begin();
        Usuario user = (Usuario) this.findObject(Usuario.class, usuario.getIdusuario());
        user.setNombre(usuario.getNombre());
        user.setApellidos(usuario.getApellidos());
        user.setEmail(usuario.getEmail());
        user.setContrasena(usuario.getContrasena());
        user.setImagenurl(usuario.getImagenurl());
        user.setProfileview(usuario.getProfileview());
        user.setIdtipousuario((Tipousuario)this.findObject(Tipousuario.class, usuario.getIdtipousuario().getIdtipousuario()));
        
        List<Codigo> codigoList = (List<Codigo>)this.findObject("Codigo.findByUsuario",Codigo.class, "usuario", usuario );
        if(!codigoList.isEmpty()){
            user.setCodigoList(codigoList);
        }
        
        List<Alerta> alertaList = (List<Alerta>)this.findObject("Alerta.findByUsuario",Alerta.class, "usuario", usuario);
        if(!alertaList.isEmpty())
            user.setAlertaList(alertaList);
        
        utx.commit();
    }

    @Lock(LockType.WRITE)
    public void updateCodigo(Codigo codigo){
        UserTransaction utx = context.getUserTransaction();
        try {
           utx.begin();
           Codigo code = em.createNamedQuery("Codigo.findByIdcodigo", Codigo.class).setParameter("idcodigo", codigo.getIdcodigo()).getResultList().get(0);
           code.setCantidadviews(codigo.getCantidadviews() + 1);
           code.setTexto(codigo.getTexto());
           code.setTamanoarchivo((float)codigo.getTexto().getBytes().length);
           code.setTitulo(codigo.getTitulo());
           code.setIdexpiracion(codigo.getIdexpiracion());
           code.setIdsintaxis(codigo.getIdsintaxis());
           code.setIdvisibilidad(codigo.getIdvisibilidad());
           utx.commit();
       } catch (Exception ex) {
           Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
           throw new RuntimeException(ex);
       }
    }
    
    @Lock(LockType.WRITE)
    public void updateAlerta(Alerta alerta){
        UserTransaction utx = context.getUserTransaction();
        try {
           utx.begin();
           Alerta alert = em.createNamedQuery("Alerta.findByIdalerta", Alerta.class).setParameter("idalerta", alerta.getIdalerta()).getResultList().get(0);
           alert.setPrimerkeyword(alerta.getPrimerkeyword());
           alert.setSegundokeyword(alerta.getSegundokeyword());
           utx.commit();
       } catch (Exception ex) {
           Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
           throw new RuntimeException(ex);
       }
    }
    
    @Schedule(second = "5", hour = "*", minute = "*")    
    private void scheduleCodigo(){
        UserTransaction utx = context.getUserTransaction();
        try{
            List<Codigo> codigoList = (List<Codigo>) this.findObject("Codigo.findAll", Codigo.class);
            
            for(Codigo codigo : codigoList){
                long diff = Math.abs(System.currentTimeMillis() - codigo.getFechaactualizacion().getTime());
                long diffMin = diff / (60 * 1000);
                if(!codigo.getIdexpiracion().getDescripcion().equals("Nunca") && diffMin >= codigo.getIdexpiracion().getTiempo()){
                    this.deleteObject(Codigo.class, codigo.getIdcodigo());
                }
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
}
