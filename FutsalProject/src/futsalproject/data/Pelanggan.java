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
@Table(name = "pelanggan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pelanggan.findAll", query = "SELECT p FROM Pelanggan p")
    , @NamedQuery(name = "Pelanggan.findByKdPelanggan", query = "SELECT p FROM Pelanggan p WHERE p.kdPelanggan = :kdPelanggan")
    , @NamedQuery(name = "Pelanggan.findByNmPelanggan", query = "SELECT p FROM Pelanggan p WHERE p.nmPelanggan = :nmPelanggan")
    , @NamedQuery(name = "Pelanggan.findByAlamat", query = "SELECT p FROM Pelanggan p WHERE p.alamat = :alamat")
    , @NamedQuery(name = "Pelanggan.findByNoHp", query = "SELECT p FROM Pelanggan p WHERE p.noHp = :noHp")})
public class Pelanggan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kd_pelanggan")
    private String kdPelanggan;
    @Basic(optional = false)
    @Column(name = "nm_pelanggan")
    private String nmPelanggan;
    @Basic(optional = false)
    @Column(name = "alamat")
    private String alamat;
    @Basic(optional = false)
    @Column(name = "no_hp")
    private String noHp;

    public Pelanggan() {
    }

    public Pelanggan(String kdPelanggan) {
        this.kdPelanggan = kdPelanggan;
    }

    public Pelanggan(String kdPelanggan, String nmPelanggan, String alamat, String noHp) {
        this.kdPelanggan = kdPelanggan;
        this.nmPelanggan = nmPelanggan;
        this.alamat = alamat;
        this.noHp = noHp;
    }

    public String getKdPelanggan() {
        return kdPelanggan;
    }

    public void setKdPelanggan(String kdPelanggan) {
        this.kdPelanggan = kdPelanggan;
    }

    public String getNmPelanggan() {
        return nmPelanggan;
    }

    public void setNmPelanggan(String nmPelanggan) {
        this.nmPelanggan = nmPelanggan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kdPelanggan != null ? kdPelanggan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pelanggan)) {
            return false;
        }
        Pelanggan other = (Pelanggan) object;
        if ((this.kdPelanggan == null && other.kdPelanggan != null) || (this.kdPelanggan != null && !this.kdPelanggan.equals(other.kdPelanggan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "futsalproject.data.Pelanggan[ kdPelanggan=" + kdPelanggan + " ]";
    }
    
}
