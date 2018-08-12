package futsalproject.controller;

import futsalproject.data.Penyewaan;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class PenyewaanController implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private EntityManagerFactory emf=null;
    
    public PenyewaanController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void save(Penyewaan sewa){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(sewa);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void update(Penyewaan sewa){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(sewa);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public void delete(String pk){
        EntityManager em = getEntityManager();
        Penyewaan sewa;
        try{
            sewa=em.getReference(Penyewaan.class, pk);
            sewa.getNoTrans();
            em.getTransaction().begin();
            em.remove(sewa);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public Penyewaan findOnePenyewaan(String kode){
        EntityManager em=getEntityManager();
        try{
            return em.find(Penyewaan.class, kode);
        }finally{}
    }
    
    public List<Object[]> findAllPenyewaan(){
        EntityManager em = getEntityManager();
        List<Object[]> listPenyewaan = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT p, pel.nmPelanggan FROM Penyewaan p, Pelanggan pel WHERE p.kdPelanggan = pel.kdPelanggan");
            listPenyewaan = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPenyewaan;
    }
    
    public List<Object[]> searchPenyewaan(String cari){
        EntityManager em = getEntityManager();
        List<Object[]> listBooking = new ArrayList<>();
        try {
            Query q = em.createNativeQuery("SELECT p.no_trans, p.tgl_sewa, p.kd_booking, p.kd_pelanggan, pel.nm_pelanggan, p.jam_sewa_masuk, p.jam_sewa_keluar, p.total_sewa, p.uang_byr FROM penyewaan p \n" +
                "INNER JOIN pelanggan pel ON p.kd_pelanggan = pel.kd_pelanggan\n" +
                "WHERE p.no_trans LIKE ?cari\n" +
                "OR p.kd_booking LIKE ?cari\n" +
                "OR p.kd_pelanggan LIKE ?cari\n" +
                "OR pel.nm_pelanggan LIKE ?cari");
            q.setParameter("cari", "%"+cari+"%");
            listBooking = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBooking;
    }
    
    // form jurnal
    public List<Penyewaan> findAllPenyewaanNotExistInJurnal(){
        EntityManager em = getEntityManager();
        List<Penyewaan> list = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT p FROM Penyewaan p WHERE NOT EXISTS (SELECT j FROM Jurnal j WHERE p.noTrans = j.noTrans)");
            list = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // cari member eksis untuk diskon, di form booking
    public List<Penyewaan> searchMemberEksis(String kdPelanggan, Date tglSewa){
        EntityManager em = getEntityManager();
        List<Penyewaan> listBooking = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT p FROM Penyewaan p WHERE p.kdPelanggan = :kdPelanggan AND p.tglSewa < :tglSewa");
            q.setParameter("kdPelanggan", kdPelanggan);
            q.setParameter("tglSewa", tglSewa);
            listBooking = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBooking;
    }
    
    public String kodeOtomatis(){
        EntityManager em=null;
        String kode="Trans-001";
        try{
            em=getEntityManager();
            Query q=em.createQuery("SELECT p FROM Penyewaan p ORDER BY p.noTrans DESC");
            q.setMaxResults(1);
            Penyewaan sewa=(Penyewaan) q.getSingleResult();
            if(q!=null){
                DecimalFormat formatnomor = new DecimalFormat("Trans-000");
                String nomorurut = sewa.getNoTrans().substring(6);
                kode=formatnomor.format(Double.parseDouble(nomorurut)+1);
            }
        }catch(NoResultException ex){
            return kode;
        }
        return kode;
    }
}
