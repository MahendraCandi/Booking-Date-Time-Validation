package futsalproject.controller;

import futsalproject.data.JurnalDetail;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

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
    
    public List<Object[]> findDetailToListByNoJurnal(String noJurnal){
        EntityManager em = getEntityManager();
        List<Object[]> list = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT jd, dp.nmPerkiraan, dp.jenisPerkiraan  FROM JurnalDetail jd, DataPerkiraan dp WHERE jd.kdPerkiraan = dp.kdPerkiraan AND jd.noJurnal = :noJurnal ORDER BY jd.id");
            q.setParameter("noJurnal", noJurnal);
            list = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
