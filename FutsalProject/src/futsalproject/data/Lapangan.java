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
@Table(name = "lapangan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lapangan.findAll", query = "SELECT l FROM Lapangan l")
    , @NamedQuery(name = "Lapangan.findByKdLap", query = "SELECT l FROM Lapangan l WHERE l.kdLap = :kdLap")
    , @NamedQuery(name = "Lapangan.findByJenisLap", query = "SELECT l FROM Lapangan l WHERE l.jenisLap = :jenisLap")
    , @NamedQuery(name = "Lapangan.findByTarif", query = "SELECT l FROM Lapangan l WHERE l.tarif = :tarif")})
public class Lapangan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kd_lap")
    private String kdLap;
    @Basic(optional = false)
    @Column(name = "jenis_lap")
    private String jenisLap;
    @Basic(optional = false)
    @Column(name = "tarif")
    private double tarif;

    public Lapangan() {
    }

    public Lapangan(String kdLap) {
        this.kdLap = kdLap;
    }

    public Lapangan(String kdLap, String jenisLap, double tarif) {
        this.kdLap = kdLap;
        this.jenisLap = jenisLap;
        this.tarif = tarif;
    }

    public String getKdLap() {
        return kdLap;
    }

    public void setKdLap(String kdLap) {
        this.kdLap = kdLap;
    }

    public String getJenisLap() {
        return jenisLap;
    }

    public void setJenisLap(String jenisLap) {
        this.jenisLap = jenisLap;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kdLap != null ? kdLap.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lapangan)) {
            return false;
        }
        Lapangan other = (Lapangan) object;
        if ((this.kdLap == null && other.kdLap != null) || (this.kdLap != null && !this.kdLap.equals(other.kdLap))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "futsalproject.data.Lapangan[ kdLap=" + kdLap + " ]";
    }
    
}
