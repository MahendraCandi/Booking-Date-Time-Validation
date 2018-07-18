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
 * @author Candi-PC
 */
@Entity
@Table(name = "booking")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Booking.findAll", query = "SELECT b FROM Booking b"),
    @NamedQuery(name = "Booking.findByKdBooking", query = "SELECT b FROM Booking b WHERE b.kdBooking = :kdBooking"),
    @NamedQuery(name = "Booking.findByTglBooking", query = "SELECT b FROM Booking b WHERE b.tglBooking = :tglBooking"),
    @NamedQuery(name = "Booking.findByKdPelanggan", query = "SELECT b FROM Booking b WHERE b.kdPelanggan = :kdPelanggan"),
    @NamedQuery(name = "Booking.findByTglPakai", query = "SELECT b FROM Booking b WHERE b.tglPakai = :tglPakai"),
    @NamedQuery(name = "Booking.findByJamMasuk", query = "SELECT b FROM Booking b WHERE b.jamMasuk = :jamMasuk"),
    @NamedQuery(name = "Booking.findByJamKeluar", query = "SELECT b FROM Booking b WHERE b.jamKeluar = :jamKeluar"),
    @NamedQuery(name = "Booking.findByDiskon", query = "SELECT b FROM Booking b WHERE b.diskon = :diskon"),
    @NamedQuery(name = "Booking.findByHariLibur", query = "SELECT b FROM Booking b WHERE b.hariLibur = :hariLibur"),
    @NamedQuery(name = "Booking.findByKdLap", query = "SELECT b FROM Booking b WHERE b.kdLap = :kdLap"),
    @NamedQuery(name = "Booking.findByUangDp", query = "SELECT b FROM Booking b WHERE b.uangDp = :uangDp"),
    @NamedQuery(name = "Booking.findByKdUser", query = "SELECT b FROM Booking b WHERE b.kdUser = :kdUser")})
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kd_booking")
    private String kdBooking;
    @Basic(optional = false)
    @Column(name = "tgl_booking")
    @Temporal(TemporalType.DATE)
    private Date tglBooking;
    @Basic(optional = false)
    @Column(name = "kd_pelanggan")
    private String kdPelanggan;
    @Basic(optional = false)
    @Column(name = "tgl_pakai")
    @Temporal(TemporalType.DATE)
    private Date tglPakai;
    @Basic(optional = false)
    @Column(name = "jam_masuk")
    @Temporal(TemporalType.TIME)
    private Date jamMasuk;
    @Basic(optional = false)
    @Column(name = "jam_keluar")
    @Temporal(TemporalType.TIME)
    private Date jamKeluar;
    @Basic(optional = false)
    @Column(name = "diskon")
    private double diskon;
    @Basic(optional = false)
    @Column(name = "hari_libur")
    private double hariLibur;
    @Basic(optional = false)
    @Column(name = "kd_lap")
    private String kdLap;
    @Basic(optional = false)
    @Column(name = "uang_dp")
    private double uangDp;
    @Basic(optional = false)
    @Column(name = "kd_user")
    private String kdUser;

    public Booking() {
    }

    public Booking(String kdBooking) {
        this.kdBooking = kdBooking;
    }

    public Booking(String kdBooking, Date tglBooking, String kdPelanggan, Date tglPakai, Date jamMasuk, Date jamKeluar, double diskon, double hariLibur, String kdLap, double uangDp, String kdUser) {
        this.kdBooking = kdBooking;
        this.tglBooking = tglBooking;
        this.kdPelanggan = kdPelanggan;
        this.tglPakai = tglPakai;
        this.jamMasuk = jamMasuk;
        this.jamKeluar = jamKeluar;
        this.diskon = diskon;
        this.hariLibur = hariLibur;
        this.kdLap = kdLap;
        this.uangDp = uangDp;
        this.kdUser = kdUser;
    }

    public String getKdBooking() {
        return kdBooking;
    }

    public void setKdBooking(String kdBooking) {
        this.kdBooking = kdBooking;
    }

    public Date getTglBooking() {
        return tglBooking;
    }

    public void setTglBooking(Date tglBooking) {
        this.tglBooking = tglBooking;
    }

    public String getKdPelanggan() {
        return kdPelanggan;
    }

    public void setKdPelanggan(String kdPelanggan) {
        this.kdPelanggan = kdPelanggan;
    }

    public Date getTglPakai() {
        return tglPakai;
    }

    public void setTglPakai(Date tglPakai) {
        this.tglPakai = tglPakai;
    }

    public Date getJamMasuk() {
        return jamMasuk;
    }

    public void setJamMasuk(Date jamMasuk) {
        this.jamMasuk = jamMasuk;
    }

    public Date getJamKeluar() {
        return jamKeluar;
    }

    public void setJamKeluar(Date jamKeluar) {
        this.jamKeluar = jamKeluar;
    }

    public double getDiskon() {
        return diskon;
    }

    public void setDiskon(double diskon) {
        this.diskon = diskon;
    }

    public double getHariLibur() {
        return hariLibur;
    }

    public void setHariLibur(double hariLibur) {
        this.hariLibur = hariLibur;
    }

    public String getKdLap() {
        return kdLap;
    }

    public void setKdLap(String kdLap) {
        this.kdLap = kdLap;
    }

    public double getUangDp() {
        return uangDp;
    }

    public void setUangDp(double uangDp) {
        this.uangDp = uangDp;
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
        hash += (kdBooking != null ? kdBooking.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) object;
        if ((this.kdBooking == null && other.kdBooking != null) || (this.kdBooking != null && !this.kdBooking.equals(other.kdBooking))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "futsalproject.data.Booking[ kdBooking=" + kdBooking + " ]";
    }
    
}
