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
@Table(name = "visibilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visibilidad.findAll", query = "SELECT v FROM Visibilidad v"),
    @NamedQuery(name = "Visibilidad.findByIdvisibilidad", query = "SELECT v FROM Visibilidad v WHERE v.idvisibilidad = :idvisibilidad"),
    @NamedQuery(name = "Visibilidad.findByNombre", query = "SELECT v FROM Visibilidad v WHERE v.nombre = :nombre")})
public class Visibilidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvisibilidad")
    private Integer idvisibilidad;
    @Size(max = 20)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idvisibilidad")
    private List<Preferencias> preferenciasList;
    @OneToMany(mappedBy = "idvisibilidad")
    private List<Codigo> codigoList;

    public Visibilidad() {
    }

    public Visibilidad(Integer idvisibilidad) {
        this.idvisibilidad = idvisibilidad;
    }

    public Integer getIdvisibilidad() {
        return idvisibilidad;
    }

    public void setIdvisibilidad(Integer idvisibilidad) {
        this.idvisibilidad = idvisibilidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        hash += (idvisibilidad != null ? idvisibilidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visibilidad)) {
            return false;
        }
        Visibilidad other = (Visibilidad) object;
        if ((this.idvisibilidad == null && other.idvisibilidad != null) || (this.idvisibilidad != null && !this.idvisibilidad.equals(other.idvisibilidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.pucmm.pw.parcial2.entities.Visibilidad[ idvisibilidad=" + idvisibilidad + " ]";
    }
    
}
