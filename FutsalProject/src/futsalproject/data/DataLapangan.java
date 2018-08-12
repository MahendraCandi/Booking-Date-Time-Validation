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
@Table(name = "data_lapangan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DataLapangan.findAll", query = "SELECT d FROM DataLapangan d"),
    @NamedQuery(name = "DataLapangan.findByKdLap", query = "SELECT d FROM DataLapangan d WHERE d.kdLap = :kdLap"),
    @NamedQuery(name = "DataLapangan.findByJenisLap", query = "SELECT d FROM DataLapangan d WHERE d.jenisLap = :jenisLap"),
    @NamedQuery(name = "DataLapangan.findByTarif", query = "SELECT d FROM DataLapangan d WHERE d.tarif = :tarif")})
public class DataLapangan implements Serializable {

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

    public DataLapangan() {
    }

    public DataLapangan(String kdLap) {
        this.kdLap = kdLap;
    }

    public DataLapangan(String kdLap, String jenisLap, double tarif) {
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
        if (!(object instanceof DataLapangan)) {
            return false;
        }
        DataLapangan other = (DataLapangan) object;
        if ((this.kdLap == null && other.kdLap != null) || (this.kdLap != null && !this.kdLap.equals(other.kdLap))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "futsalproject.data.DataLapangan[ kdLap=" + kdLap + " ]";
    }
    
}
