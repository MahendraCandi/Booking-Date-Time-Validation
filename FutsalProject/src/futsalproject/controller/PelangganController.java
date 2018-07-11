package futsalproject.controller;

import futsalproject.data.Pelanggan;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class PelangganController implements Serializable{
        private static final long serialVersionUID = 1L;
    
    private EntityManagerFactory emf=null;
    
    public PelangganController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void save(Pelanggan pelanggan){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(pelanggan);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void update(Pelanggan pelanggan){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(pelanggan);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public void delete(String pk){
        EntityManager em = getEntityManager();
        Pelanggan us;
        try{
            us=em.getReference(Pelanggan.class, pk);
            us.getKdPelanggan();
            em.getTransaction().begin();
            em.remove(us);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public Pelanggan findOnePelanggan(String kode){
        EntityManager em=getEntityManager();
        try{
            return em.find(Pelanggan.class, kode);
        }finally{}
    }
    
    public List<Pelanggan> findAllPelanggan(){
        EntityManager em = getEntityManager();
        List<Pelanggan> listPelanggan = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT p FROM Pelanggan p");
            listPelanggan = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPelanggan;
    }
    
    public String kodeOtomatis(){
        EntityManager em=null;
        String kode="Team-001";
        try{
            em=getEntityManager();
            Query q=em.createQuery("SELECT p FROM Pelanggan p ORDER BY p.kdPelanggan DESC");
            q.setMaxResults(1);
            Pelanggan pelanggan=(Pelanggan) q.getSingleResult();
            if(q!=null){
                DecimalFormat formatnomor = new DecimalFormat("Team-000");
                String nomorurut = pelanggan.getKdPelanggan().substring(5);
                kode=formatnomor.format(Double.parseDouble(nomorurut)+1);
            }
        }catch(NoResultException ex){
            ex.printStackTrace();
            return null;
        }
        return kode;
    }
}
