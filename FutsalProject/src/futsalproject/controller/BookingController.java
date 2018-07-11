package futsalproject.controller;

import futsalproject.data.Booking;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
    
    public List<Booking> findAllBooking(){
        EntityManager em = getEntityManager();
        List<Booking> listBooking = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT b FROM Booking b");
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
            ex.printStackTrace();
            return null;
        }
        return kode;
    }
}
