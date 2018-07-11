package futsalproject.controller;

import futsalproject.data.JurnalDetail;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class JurnalDetailController implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private EntityManagerFactory emf=null;
    
    public JurnalDetailController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void save(JurnalDetail jurnal){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(jurnal);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void update(JurnalDetail jurnal){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(jurnal);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public void delete(String kode){
        EntityManager em = getEntityManager();
        JurnalDetail jr;
        try{
            jr=em.getReference(JurnalDetail.class, kode);
            jr.getId();
            em.getTransaction().begin();
            em.remove(jr);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public JurnalDetail findJurnalDetail(int kode){
        EntityManager em=getEntityManager();
        try{
            return em.find(JurnalDetail.class, kode);
        }finally{}
    }
}
