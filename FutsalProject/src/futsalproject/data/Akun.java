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
@Table(name = "akun")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Akun.findAll", query = "SELECT a FROM Akun a")
    , @NamedQuery(name = "Akun.findByKdAkun", query = "SELECT a FROM Akun a WHERE a.kdAkun = :kdAkun")
    , @NamedQuery(name = "Akun.findByNmAkun", query = "SELECT a FROM Akun a WHERE a.nmAkun = :nmAkun")
    , @NamedQuery(name = "Akun.findByJenisAkun", query = "SELECT a FROM Akun a WHERE a.jenisAkun = :jenisAkun")})
public class Akun implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kd_akun")
    private String kdAkun;
    @Basic(optional = false)
    @Column(name = "nm_akun")
    private String nmAkun;
    @Basic(optional = false)
    @Column(name = "jenis_akun")
    private String jenisAkun;

    public Akun() {
    }

    public Akun(String kdAkun) {
        this.kdAkun = kdAkun;
    }

    public Akun(String kdAkun, String nmAkun, String jenisAkun) {
        this.kdAkun = kdAkun;
        this.nmAkun = nmAkun;
        this.jenisAkun = jenisAkun;
    }

    public String getKdAkun() {
        return kdAkun;
    }

    public void setKdAkun(String kdAkun) {
        this.kdAkun = kdAkun;
    }

    public String getNmAkun() {
        return nmAkun;
    }

    public void setNmAkun(String nmAkun) {
        this.nmAkun = nmAkun;
    }

    public String getJenisAkun() {
        return jenisAkun;
    }

    public void setJenisAkun(String jenisAkun) {
        this.jenisAkun = jenisAkun;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kdAkun != null ? kdAkun.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Akun)) {
            return false;
        }
        Akun other = (Akun) object;
        if ((this.kdAkun == null && other.kdAkun != null) || (this.kdAkun != null && !this.kdAkun.equals(other.kdAkun))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "futsalproject.data.Akun[ kdAkun=" + kdAkun + " ]";
    }
    
}
