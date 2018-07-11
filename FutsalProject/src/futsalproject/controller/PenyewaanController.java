package futsalproject.controller;

import futsalproject.data.Penyewaan;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
    
    public List<Penyewaan> findAllPenyewaan(){
        EntityManager em = getEntityManager();
        List<Penyewaan> listPenyewaan = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT u FROM Penyewaan u");
            listPenyewaan = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPenyewaan;
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
            ex.printStackTrace();
            return null;
        }
        return kode;
    }
}
