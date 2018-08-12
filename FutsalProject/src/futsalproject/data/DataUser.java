/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futsalproject.data;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Candi-PC
 */
@Entity
@Table(name = "data_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DataUser.findAll", query = "SELECT d FROM DataUser d"),
    @NamedQuery(name = "DataUser.findByKdUser", query = "SELECT d FROM DataUser d WHERE d.kdUser = :kdUser"),
    @NamedQuery(name = "DataUser.findByNmUser", query = "SELECT d FROM DataUser d WHERE d.nmUser = :nmUser"),
    @NamedQuery(name = "DataUser.findByHakAkses", query = "SELECT d FROM DataUser d WHERE d.hakAkses = :hakAkses"),
    @NamedQuery(name = "DataUser.findByPassword", query = "SELECT d FROM DataUser d WHERE d.password = :password")})
public class DataUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kd_user")
    private String kdUser;
    @Basic(optional = false)
    @Column(name = "nm_user")
    private String nmUser;
    @Basic(optional = false)
    @Column(name = "hak_akses")
    private String hakAkses;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    public DataUser() {
    }

    public DataUser(String kdUser) {
        this.kdUser = kdUser;
    }

    public DataUser(String kdUser, String nmUser, String hakAkses, String password) {
        this.kdUser = kdUser;
        this.nmUser = nmUser;
        this.hakAkses = hakAkses;
        this.password = password;
    }

    public String getKdUser() {
        return kdUser;
    }

    public void setKdUser(String kdUser) {
        this.kdUser = kdUser;
    }

    public String getNmUser() {
        return nmUser;
    }

    public void setNmUser(String nmUser) {
        this.nmUser = nmUser;
    }

    public String getHakAkses() {
        return hakAkses;
    }

    public void setHakAkses(String hakAkses) {
        this.hakAkses = hakAkses;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kdUser != null ? kdUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataUser)) {
            return false;
        }
        DataUser other = (DataUser) object;
        if ((this.kdUser == null && other.kdUser != null) || (this.kdUser != null && !this.kdUser.equals(other.kdUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "futsalproject.data.DataUser[ kdUser=" + kdUser + " ]";
    }
    
}
