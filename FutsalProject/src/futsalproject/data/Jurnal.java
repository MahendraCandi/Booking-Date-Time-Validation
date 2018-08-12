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
@Table(name = "jurnal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jurnal.findAll", query = "SELECT j FROM Jurnal j"),
    @NamedQuery(name = "Jurnal.findByNoJurnal", query = "SELECT j FROM Jurnal j WHERE j.noJurnal = :noJurnal"),
    @NamedQuery(name = "Jurnal.findByTglJurnal", query = "SELECT j FROM Jurnal j WHERE j.tglJurnal = :tglJurnal"),
    @NamedQuery(name = "Jurnal.findByNoTrans", query = "SELECT j FROM Jurnal j WHERE j.noTrans = :noTrans")})
public class Jurnal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "no_jurnal")
    private String noJurnal;
    @Basic(optional = false)
    @Column(name = "tgl_jurnal")
    @Temporal(TemporalType.DATE)
    private Date tglJurnal;
    @Basic(optional = false)
    @Column(name = "no_trans")
    private String noTrans;

    public Jurnal() {
    }

    public Jurnal(String noJurnal) {
        this.noJurnal = noJurnal;
    }

    public Jurnal(String noJurnal, Date tglJurnal, String noTrans) {
        this.noJurnal = noJurnal;
        this.tglJurnal = tglJurnal;
        this.noTrans = noTrans;
    }

    public String getNoJurnal() {
        return noJurnal;
    }

    public void setNoJurnal(String noJurnal) {
        this.noJurnal = noJurnal;
    }

    public Date getTglJurnal() {
        return tglJurnal;
    }

    public void setTglJurnal(Date tglJurnal) {
        this.tglJurnal = tglJurnal;
    }

    public String getNoTrans() {
        return noTrans;
    }

    public void setNoTrans(String noTrans) {
        this.noTrans = noTrans;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noJurnal != null ? noJurnal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jurnal)) {
            return false;
        }
        Jurnal other = (Jurnal) object;
        if ((this.noJurnal == null && other.noJurnal != null) || (this.noJurnal != null && !this.noJurnal.equals(other.noJurnal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "futsalproject.data.Jurnal[ noJurnal=" + noJurnal + " ]";
    }
    
}
