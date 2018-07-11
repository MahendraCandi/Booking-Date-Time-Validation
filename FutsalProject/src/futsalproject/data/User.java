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
 * @author 0085
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByKdUser", query = "SELECT u FROM User u WHERE u.kdUser = :kdUser")
    , @NamedQuery(name = "User.findByNmUser", query = "SELECT u FROM User u WHERE u.nmUser = :nmUser")
    , @NamedQuery(name = "User.findByHakAkses", query = "SELECT u FROM User u WHERE u.hakAkses = :hakAkses")
    , @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")})
public class User implements Serializable {

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

    public User() {
    }

    public User(String kdUser) {
        this.kdUser = kdUser;
    }

    public User(String kdUser, String nmUser, String hakAkses, String password) {
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.kdUser == null && other.kdUser != null) || (this.kdUser != null && !this.kdUser.equals(other.kdUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "futsalproject.data.User[ kdUser=" + kdUser + " ]";
    }
    
}
