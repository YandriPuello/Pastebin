/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pucmm.pw.parcial2.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fmesa
 */
@Entity
@Table(name = "expiracion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Expiracion.findAll", query = "SELECT e FROM Expiracion e"),
    @NamedQuery(name = "Expiracion.findByIdexpiracion", query = "SELECT e FROM Expiracion e WHERE e.idexpiracion = :idexpiracion"),
    @NamedQuery(name = "Expiracion.findByTiempo", query = "SELECT e FROM Expiracion e WHERE e.tiempo = :tiempo"),
    @NamedQuery(name = "Expiracion.findByDescripcion", query = "SELECT e FROM Expiracion e WHERE e.descripcion = :descripcion")})
public class Expiracion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idexpiracion")
    private Integer idexpiracion;
    @Column(name = "tiempo")
    private Integer tiempo;
    @Size(max = 20)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "idexpiracion")
    private List<Preferencias> preferenciasList;
    @OneToMany(mappedBy = "idexpiracion")
    private List<Codigo> codigoList;

    public Expiracion() {
    }

    public Expiracion(Integer idexpiracion) {
        this.idexpiracion = idexpiracion;
    }

    public Integer getIdexpiracion() {
        return idexpiracion;
    }

    public void setIdexpiracion(Integer idexpiracion) {
        this.idexpiracion = idexpiracion;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Preferencias> getPreferenciasList() {
        return preferenciasList;
    }

    public void setPreferenciasList(List<Preferencias> preferenciasList) {
        this.preferenciasList = preferenciasList;
    }

    @XmlTransient
    public List<Codigo> getCodigoList() {
        return codigoList;
    }

    public void setCodigoList(List<Codigo> codigoList) {
        this.codigoList = codigoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idexpiracion != null ? idexpiracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Expiracion)) {
            return false;
        }
        Expiracion other = (Expiracion) object;
        if ((this.idexpiracion == null && other.idexpiracion != null) || (this.idexpiracion != null && !this.idexpiracion.equals(other.idexpiracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.pucmm.pw.parcial2.entities.Expiracion[ idexpiracion=" + idexpiracion + " ]";
    }
    
}
