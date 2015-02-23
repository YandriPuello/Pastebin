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
@Table(name = "sintaxis")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sintaxis.findAll", query = "SELECT s FROM Sintaxis s"),
    @NamedQuery(name = "Sintaxis.findByIdsintaxis", query = "SELECT s FROM Sintaxis s WHERE s.idsintaxis = :idsintaxis"),
    @NamedQuery(name = "Sintaxis.findByNombre", query = "SELECT s FROM Sintaxis s WHERE s.nombre = :nombre")})
public class Sintaxis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsintaxis")
    private Integer idsintaxis;
    @Size(max = 20)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idsintaxis")
    private List<Preferencias> preferenciasList;
    @OneToMany(mappedBy = "idsintaxis")
    private List<Codigo> codigoList;

    public Sintaxis() {
    }

    public Sintaxis(Integer idsintaxis) {
        this.idsintaxis = idsintaxis;
    }

    public Integer getIdsintaxis() {
        return idsintaxis;
    }

    public void setIdsintaxis(Integer idsintaxis) {
        this.idsintaxis = idsintaxis;
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
        hash += (idsintaxis != null ? idsintaxis.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sintaxis)) {
            return false;
        }
        Sintaxis other = (Sintaxis) object;
        if ((this.idsintaxis == null && other.idsintaxis != null) || (this.idsintaxis != null && !this.idsintaxis.equals(other.idsintaxis))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.pucmm.pw.parcial2.entities.Sintaxis[ idsintaxis=" + idsintaxis + " ]";
    }
    
}
