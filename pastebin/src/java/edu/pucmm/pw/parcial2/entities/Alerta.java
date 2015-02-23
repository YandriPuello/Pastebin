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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmesa
 */
@Entity
@Table(name = "alerta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alerta.findAll", query = "SELECT a FROM Alerta a"),
    @NamedQuery(name = "Alerta.findByIdalerta", query = "SELECT a FROM Alerta a WHERE a.idalerta = :idalerta"),
    @NamedQuery(name = "Alerta.findByPrimerkeyword", query = "SELECT a FROM Alerta a WHERE a.primerkeyword = :primerkeyword"),
    @NamedQuery(name = "Alerta.findBySegundokeyword", query = "SELECT a FROM Alerta a WHERE a.segundokeyword = :segundokeyword"),
    @NamedQuery(name = "Alerta.findByUsuario", query = "SELECT a FROM Alerta a WHERE a.idusuario = :usuario")})
public class Alerta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idalerta")
    private Integer idalerta;
    @Size(max = 40)
    @Column(name = "primerkeyword")
    private String primerkeyword;
    @Size(max = 40)
    @Column(name = "segundokeyword")
    private String segundokeyword;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne
    private Usuario idusuario;

    public Alerta() {
    }

    public Alerta(Integer idalerta) {
        this.idalerta = idalerta;
    }

    public Integer getIdalerta() {
        return idalerta;
    }

    public void setIdalerta(Integer idalerta) {
        this.idalerta = idalerta;
    }

    public String getPrimerkeyword() {
        return primerkeyword;
    }

    public void setPrimerkeyword(String primerkeyword) {
        this.primerkeyword = primerkeyword;
    }

    public String getSegundokeyword() {
        return segundokeyword;
    }

    public void setSegundokeyword(String segundokeyword) {
        this.segundokeyword = segundokeyword;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idalerta != null ? idalerta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alerta)) {
            return false;
        }
        Alerta other = (Alerta) object;
        if ((this.idalerta == null && other.idalerta != null) || (this.idalerta != null && !this.idalerta.equals(other.idalerta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.pucmm.pw.parcial2.entities.Alerta[ idalerta=" + idalerta + " ]";
    }
    
}
