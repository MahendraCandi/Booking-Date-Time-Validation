/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futsalproject.data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 0085
 */
@Entity
@Table(name = "penyewaan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Penyewaan.findAll", query = "SELECT p FROM Penyewaan p")
    , @NamedQuery(name = "Penyewaan.findByNoTrans", query = "SELECT p FROM Penyewaan p WHERE p.noTrans = :noTrans")
    , @NamedQuery(name = "Penyewaan.findByTglSewa", query = "SELECT p FROM Penyewaan p WHERE p.tglSewa = :tglSewa")
    , @NamedQuery(name = "Penyewaan.findByKdPelanggan", query = "SELECT p FROM Penyewaan p WHERE p.kdPelanggan = :kdPelanggan")
    , @NamedQuery(name = "Penyewaan.findByKdBooking", query = "SELECT p FROM Penyewaan p WHERE p.kdBooking = :kdBooking")
    , @NamedQuery(name = "Penyewaan.findByKdLap", query = "SELECT p FROM Penyewaan p WHERE p.kdLap = :kdLap")
    , @NamedQuery(name = "Penyewaan.findByJamSewaMasuk", query = "SELECT p FROM Penyewaan p WHERE p.jamSewaMasuk = :jamSewaMasuk")
    , @NamedQuery(name = "Penyewaan.findByJamSewaKeluar", query = "SELECT p FROM Penyewaan p WHERE p.jamSewaKeluar = :jamSewaKeluar")
    , @NamedQuery(name = "Penyewaan.findByLamaSewa", query = "SELECT p FROM Penyewaan p WHERE p.lamaSewa = :lamaSewa")
    , @NamedQuery(name = "Penyewaan.findByDiskonSewa", query = "SELECT p FROM Penyewaan p WHERE p.diskonSewa = :diskonSewa")
    , @NamedQuery(name = "Penyewaan.findByTotalSewa", query = "SELECT p FROM Penyewaan p WHERE p.totalSewa = :totalSewa")
    , @NamedQuery(name = "Penyewaan.findByUangByr", query = "SELECT p FROM Penyewaan p WHERE p.uangByr = :uangByr")
    , @NamedQuery(name = "Penyewaan.findByKdUser", query = "SELECT p FROM Penyewaan p WHERE p.kdUser = :kdUser")})
public class Penyewaan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "no_trans")
    private String noTrans;
    @Basic(optional = false)
    @Column(name = "tgl_sewa")
    @Temporal(TemporalType.DATE)
    private Date tglSewa;
    @Basic(optional = false)
    @Column(name = "kd_pelanggan")
    private String kdPelanggan;
    @Basic(optional = false)
    @Column(name = "kd_booking")
    private String kdBooking;
    @Basic(optional = false)
    @Column(name = "kd_lap")
    private String kdLap;
    @Basic(optional = false)
    @Column(name = "jam_sewa_masuk")
    @Temporal(TemporalType.TIME)
    private Date jamSewaMasuk;
    @Basic(optional = false)
    @Column(name = "jam_sewa_keluar")
    @Temporal(TemporalType.TIME)
    private Date jamSewaKeluar;
    @Basic(optional = false)
    @Column(name = "lama_sewa")
    private double lamaSewa;
    @Basic(optional = false)
    @Column(name = "diskon_sewa")
    private double diskonSewa;
    @Basic(optional = false)
    @Column(name = "total_sewa")
    private double totalSewa;
    @Basic(optional = false)
    @Column(name = "uang_byr")
    private double uangByr;
    @Basic(optional = false)
    @Column(name = "kd_user")
    private String kdUser;

    public Penyewaan() {
    }

    public Penyewaan(String noTrans) {
        this.noTrans = noTrans;
    }

    public Penyewaan(String noTrans, Date tglSewa, String kdPelanggan, String kdBooking, String kdLap, Date jamSewaMasuk, Date jamSewaKeluar, double lamaSewa, double diskonSewa, double totalSewa, double uangByr, String kdUser) {
        this.noTrans = noTrans;
        this.tglSewa = tglSewa;
        this.kdPelanggan = kdPelanggan;
        this.kdBooking = kdBooking;
        this.kdLap = kdLap;
        this.jamSewaMasuk = jamSewaMasuk;
        this.jamSewaKeluar = jamSewaKeluar;
        this.lamaSewa = lamaSewa;
        this.diskonSewa = diskonSewa;
        this.totalSewa = totalSewa;
        this.uangByr = uangByr;
        this.kdUser = kdUser;
    }

    public String getNoTrans() {
        return noTrans;
    }

    public void setNoTrans(String noTrans) {
        this.noTrans = noTrans;
    }

    public Date getTglSewa() {
        return tglSewa;
    }

    public void setTglSewa(Date tglSewa) {
        this.tglSewa = tglSewa;
    }

    public String getKdPelanggan() {
        return kdPelanggan;
    }

    public void setKdPelanggan(String kdPelanggan) {
        this.kdPelanggan = kdPelanggan;
    }

    public String getKdBooking() {
        return kdBooking;
    }

    public void setKdBooking(String kdBooking) {
        this.kdBooking = kdBooking;
    }

    public String getKdLap() {
        return kdLap;
    }

    public void setKdLap(String kdLap) {
        this.kdLap = kdLap;
    }

    public Date getJamSewaMasuk() {
        return jamSewaMasuk;
    }

    public void setJamSewaMasuk(Date jamSewaMasuk) {
        this.jamSewaMasuk = jamSewaMasuk;
    }

    public Date getJamSewaKeluar() {
        return jamSewaKeluar;
    }

    public void setJamSewaKeluar(Date jamSewaKeluar) {
        this.jamSewaKeluar = jamSewaKeluar;
    }

    public double getLamaSewa() {
        return lamaSewa;
    }

    public void setLamaSewa(double lamaSewa) {
        this.lamaSewa = lamaSewa;
    }

    public double getDiskonSewa() {
        return diskonSewa;
    }

    public void setDiskonSewa(double diskonSewa) {
        this.diskonSewa = diskonSewa;
    }

    public double getTotalSewa() {
        return totalSewa;
    }

    public void setTotalSewa(double totalSewa) {
        this.totalSewa = totalSewa;
    }

    public double getUangByr() {
        return uangByr;
    }

    public void setUangByr(double uangByr) {
        this.uangByr = uangByr;
    }

    public String getKdUser() {
        return kdUser;
    }

    public void setKdUser(String kdUser) {
        this.kdUser = kdUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noTrans != null ? noTrans.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Penyewaan)) {
            return false;
        }
        Penyewaan other = (Penyewaan) object;
        if ((this.noTrans == null && other.noTrans != null) || (this.noTrans != null && !this.noTrans.equals(other.noTrans))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "futsalproject.data.Penyewaan[ noTrans=" + noTrans + " ]";
    }
    
}
