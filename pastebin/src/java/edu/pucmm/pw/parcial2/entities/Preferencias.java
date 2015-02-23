/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pucmm.pw.parcial2.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmesa
 */
@Entity
@Table(name = "preferencias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preferencias.findAll", query = "SELECT p FROM Preferencias p"),
    @NamedQuery(name = "Preferencias.findByIdusuario", query = "SELECT p FROM Preferencias p WHERE p.idusuario = :idusuario")})
public class Preferencias implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idusuario")
    private Integer idusuario;
    @JoinColumn(name = "idexpiracion", referencedColumnName = "idexpiracion")
    @ManyToOne
    private Expiracion idexpiracion;
    @JoinColumn(name = "idsintaxis", referencedColumnName = "idsintaxis")
    @ManyToOne
    private Sintaxis idsintaxis;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "idvisibilidad", referencedColumnName = "idvisibilidad")
    @ManyToOne
    private Visibilidad idvisibilidad;

    public Preferencias() {
    }

    public Preferencias(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        hash += (idusuario != null ? idusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preferencias)) {
            return false;
        }
        Preferencias other = (Preferencias) object;
        if ((this.idusuario == null && other.idusuario != null) || (this.idusuario != null && !this.idusuario.equals(other.idusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.pucmm.pw.parcial2.entities.Preferencias[ idusuario=" + idusuario + " ]";
    }
    
}
