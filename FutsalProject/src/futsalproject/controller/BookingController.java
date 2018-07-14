package futsalproject.controller;

import futsalproject.data.Booking;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class BookingController implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private EntityManagerFactory emf=null;
    
    public BookingController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void save(Booking booking){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(booking);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void update(Booking booking){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(booking);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public void delete(String pk){
        EntityManager em = getEntityManager();
        Booking book;
        try{
            book=em.getReference(Booking.class, pk);
            book.getKdBooking();
            em.getTransaction().begin();
            em.remove(book);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public Booking findOneBooking(String kode){
        EntityManager em=getEntityManager();
        try{
            return em.find(Booking.class, kode);
        }finally{}
    }
    
    public List<Object[]> findAllBooking(){
        EntityManager em = getEntityManager();
        List<Object[]> listBooking = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT b, p.nmPelanggan FROM Booking b, Pelanggan p WHERE b.kdPelanggan = p.kdPelanggan");
            listBooking = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBooking;
    }
    
    public List<Object[]> searchBooking(String cari){
        EntityManager em = getEntityManager();
        List<Object[]> listBooking = new ArrayList<>();
        try {
            Query q = em.createNativeQuery("SELECT b.kd_booking, b.tgl_booking, b.kd_pelanggan, p.nm_pelanggan, b.kd_lap, b.tgl_pakai, b.jam_masuk, b.jam_keluar\n" +
                "FROM booking b\n" +
                "INNER JOIN pelanggan p ON b.kd_pelanggan = p.kd_pelanggan\n" +
                "WHERE b.kd_booking LIKE ?cari\n" +
                "OR b.kd_pelanggan LIKE ?cari\n" +
                "OR p.nm_pelanggan LIKE ?cari\n" +
                "OR b.kd_lap LIKE ?cari\n" +
                "OR b.tgl_booking LIKE ?cari\n" +
                "OR b.tgl_pakai LIKE ?cari\n" +
                "OR b.jam_masuk LIKE ?cari\n" +
                "OR b.jam_keluar LIKE ?cari");
            q.setParameter("cari", "%"+cari+"%");
            listBooking = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBooking;
    }
    
    // validasi kode lap, tanggal, jam
    public List<Object[]> findValidasiLapangan(String kdLap, Date tglPakai, Date jamMasuk, Date jamKeluar){
        EntityManager em = getEntityManager();
        List<Object[]> listBooking = new ArrayList<>();
        try {
            Query q = em.createNativeQuery("SELECT t1.kd_booking, t1.kd_lap, t1.tgl_pakai, t1.jam_masuk, t1.jam_keluar\n" +
                "FROM\n" +
                "(\n" +
                "SELECT b.kd_booking, b.kd_lap, b.tgl_pakai, b.jam_masuk, b.jam_keluar\n" +
                "FROM booking b\n" +
                "WHERE b.tgl_pakai = ?tglPakai AND b.kd_lap = ?kdLap\n" +
                ") t1\n" +
                "WHERE\n" +
                "(?jamMasuk > t1.jam_masuk AND ?jamMasuk < t1.jam_keluar)\n" +
                "OR (?jamKeluar > t1.jam_masuk AND ?jamKeluar < t1.jam_keluar)\n" +
                "OR (?jamMasuk = t1.jam_masuk AND ?jamKeluar = t1.jam_keluar)");
            q.setParameter("kdLap", kdLap);
            q.setParameter("tglPakai", tglPakai);
            q.setParameter("jamMasuk", jamMasuk);
            q.setParameter("jamKeluar", jamKeluar);
            listBooking = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBooking;
    }
    
    public String kodeOtomatis(){
        EntityManager em=null;
        String kode="Book-001";
        try{
            em=getEntityManager();
            Query q=em.createQuery("SELECT b FROM Booking b ORDER BY b.kdBooking DESC");
            q.setMaxResults(1);
            Booking booking=(Booking) q.getSingleResult();
            if(q!=null){
                DecimalFormat formatnomor = new DecimalFormat("Book-000");
                String nomorurut = booking.getKdBooking().substring(5);
                kode=formatnomor.format(Double.parseDouble(nomorurut)+1);
            }
        }catch(NoResultException ex){
            return kode;
        }
        return kode;
    }
}
