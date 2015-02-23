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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmesa
 */
@Entity
@Table(name = "follow")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Follow.findAll", query = "SELECT f FROM Follow f"),
    @NamedQuery(name = "Follow.findByIdfollow", query = "SELECT f FROM Follow f WHERE f.idfollow = :idfollow")})
public class Follow implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfollow")
    private Integer idfollow;
    @JoinColumn(name = "idusuariofollower", referencedColumnName = "idusuario")
    @ManyToOne
    private Usuario idusuariofollower;
    @JoinColumn(name = "idusuariofollowed", referencedColumnName = "idusuario")
    @ManyToOne
    private Usuario idusuariofollowed;

    public Follow() {
    }

    public Follow(Integer idfollow) {
        this.idfollow = idfollow;
    }

    public Integer getIdfollow() {
        return idfollow;
    }

    public void setIdfollow(Integer idfollow) {
        this.idfollow = idfollow;
    }

    public Usuario getIdusuariofollower() {
        return idusuariofollower;
    }

    public void setIdusuariofollower(Usuario idusuariofollower) {
        this.idusuariofollower = idusuariofollower;
    }

    public Usuario getIdusuariofollowed() {
        return idusuariofollowed;
    }

    public void setIdusuariofollowed(Usuario idusuariofollowed) {
        this.idusuariofollowed = idusuariofollowed;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfollow != null ? idfollow.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Follow)) {
            return false;
        }
        Follow other = (Follow) object;
        if ((this.idfollow == null && other.idfollow != null) || (this.idfollow != null && !this.idfollow.equals(other.idfollow))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.pucmm.pw.parcial2.entities.Follow[ idfollow=" + idfollow + " ]";
    }
    
}
