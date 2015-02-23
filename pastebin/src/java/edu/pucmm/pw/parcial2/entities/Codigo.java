/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pucmm.pw.parcial2.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmesa
 */
@Entity
@Table(name = "codigo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Codigo.findAll", query = "SELECT c FROM Codigo c"),
    @NamedQuery(name = "Codigo.findByIdcodigo", query = "SELECT c FROM Codigo c WHERE c.idcodigo = :idcodigo"),
    @NamedQuery(name = "Codigo.findByUrl", query = "SELECT c FROM Codigo c WHERE c.url = :url"),
    @NamedQuery(name = "Codigo.findByTitulo", query = "SELECT c FROM Codigo c WHERE c.titulo = :titulo"),
    @NamedQuery(name = "Codigo.findByTexto", query = "SELECT c FROM Codigo c WHERE c.texto = :texto"),
    @NamedQuery(name = "Codigo.findByFechacreacion", query = "SELECT c FROM Codigo c WHERE c.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Codigo.findByCantidadviews", query = "SELECT c FROM Codigo c WHERE c.cantidadviews = :cantidadviews"),
    @NamedQuery(name = "Codigo.findByTamanoarchivo", query = "SELECT c FROM Codigo c WHERE c.tamanoarchivo = :tamanoarchivo"),
    @NamedQuery(name = "Codigo.findByFechaactualizacion", query = "SELECT c FROM Codigo c WHERE c.fechaactualizacion = :fechaactualizacion"),
    @NamedQuery(name = "Codigo.findByUsuario", query = "SELECT c FROM Codigo c WHERE c.idusuario = :usuario")})
public class Codigo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcodigo")
    private Integer idcodigo;
    @Size(max = 50)
    @Column(name = "url")
    private String url;
    @Size(max = 15)
    @Column(name = "titulo")
    private String titulo;
    @Size(max = 2147483647)
    @Column(name = "texto")
    private String texto;
    @Column(name = "fechacreacion")
    @Temporal(TemporalType.DATE)
    private Date fechacreacion;
    @Column(name = "cantidadviews")
    private Integer cantidadviews;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tamanoarchivo")
    private Float tamanoarchivo;
    @Column(name = "fechaactualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaactualizacion;
    @JoinColumn(name = "idexpiracion", referencedColumnName = "idexpiracion")
    @ManyToOne
    private Expiracion idexpiracion;
    @JoinColumn(name = "idsintaxis", referencedColumnName = "idsintaxis")
    @ManyToOne
    private Sintaxis idsintaxis;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne
    private Usuario idusuario;
    @JoinColumn(name = "idvisibilidad", referencedColumnName = "idvisibilidad")
    @ManyToOne
    private Visibilidad idvisibilidad;

    public Codigo() {
    }

    public Codigo(Integer idcodigo) {
        this.idcodigo = idcodigo;
    }

    public Integer getIdcodigo() {
        return idcodigo;
    }

    public void setIdcodigo(Integer idcodigo) {
        this.idcodigo = idcodigo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Integer getCantidadviews() {
        return cantidadviews;
    }

    public void setCantidadviews(Integer cantidadviews) {
        this.cantidadviews = cantidadviews;
    }

    public Float getTamanoarchivo() {
        return tamanoarchivo;
    }

    public void setTamanoarchivo(Float tamanoarchivo) {
        this.tamanoarchivo = tamanoarchivo;
    }

    public Date getFechaactualizacion() {
        return fechaactualizacion;
    }

    public void setFechaactualizacion(Date fechaactualizacion) {
        this.fechaactualizacion = fechaactualizacion;
    }

    public Expiracion getIdexpiracion() {
        return idexpiracion;
    }

    public void setIdexpiracion(Expiracion idexpiracion) {
        this.idexpiracion = idexpiracion;
    }

    public Sintaxis getIdsintaxis() {
        return idsintaxis;
    }

    public void setIdsintaxis(Sintaxis idsintaxis) {
        this.idsintaxis = idsintaxis;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    public Visibilidad getIdvisibilidad() {
        return idvisibilidad;
    }

    public void setIdvisibilidad(Visibilidad idvisibilidad) {
        this.idvisibilidad = idvisibilidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcodigo != null ? idcodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Codigo)) {
            return false;
        }
        Codigo other = (Codigo) object;
        if ((this.idcodigo == null && other.idcodigo != null) || (this.idcodigo != null && !this.idcodigo.equals(other.idcodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.pucmm.pw.parcial2.entities.Codigo[ idcodigo=" + idcodigo + " ]";
    }
    
    public String getTimeLeft(){
        double elapseTime = Math.abs(System.currentTimeMillis() - this.getFechaactualizacion().getTime());
        elapseTime = elapseTime / (60*1000);
        double totalTime = this.getIdexpiracion().getTiempo() - elapseTime;
        
        if(this.getIdexpiracion().getIdexpiracion().equals(7)){
            return this.getIdexpiracion().getDescripcion();
        } else if(totalTime < 1){
            return "Faltan " + (int)Math.ceil(totalTime / 60) + " Segundos";
        } else if( totalTime < 60 ){
            return "Faltan " + (int)Math.ceil(totalTime) + " Minutos";        
        } else if( (totalTime / 60) < 24){
            return "Faltan " + (int)Math.ceil(totalTime / 60) + " Horas";
        } else if( (totalTime / (24*60)) < 1){
            return "Faltan " + (int)Math.ceil(totalTime / (24*60)) + " Días";  
        } else {
            return "Faltan " + (int)Math.ceil(totalTime / (7*24*60)) + " Semanas";
        }
    }
    
    public String getTimeCreatedElapsed(){
        double elapseTime = Math.abs(System.currentTimeMillis() - this.getFechaactualizacion().getTime());
        elapseTime = elapseTime / (60*1000);
        
        if(elapseTime < 1){
            return "Hace " + (int)(elapseTime * 60) + " Segundos";
        } else if( elapseTime < 60 ){
            return "Hace " + (int)elapseTime + " Minutos";        
        } else if( (elapseTime / 60) < 24){
            return "Hace " + (int)(elapseTime / 60) + " Horas";
        } else if( (elapseTime / (24*60)) < 7){
            return "Hace " + (int)Math.ceil(elapseTime / (24*60)) + " Días";  
        } else {
            return "Hace " + (int)(elapseTime / (7*24*60)) + " Semanas";
        }
    }
    
}
