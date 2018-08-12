package futsalproject.controller;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import futsalproject.data.DataUser;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;

public class UserController implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private EntityManagerFactory emf=null;
    
    public UserController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void save(DataUser user){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void update(DataUser user){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public void delete(String pk){
        EntityManager em = getEntityManager();
        DataUser us;
        try{
            us=em.getReference(DataUser.class, pk);
            us.getKdUser();
            em.getTransaction().begin();
            em.remove(us);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public DataUser findOneDataUser(String kode){
        EntityManager em=getEntityManager();
        try{
            return em.find(DataUser.class, kode);
        }finally{}
    }
    
    public List<DataUser> findAllDataUser(){
        EntityManager em = getEntityManager();
        List<DataUser> listDataUser = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT u FROM DataUser u");
            listDataUser = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDataUser;
    }
    
    public List<DataUser> searchDataUser(String cari){
        EntityManager em = getEntityManager();
        List<DataUser> listDataUser = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT u FROM DataUser u WHERE u.kdUser LIKE :cari OR u.nmUser LIKE :cari OR u.hakAkses LIKE :cari");
            q.setParameter("cari", "%"+cari+"%");
            listDataUser = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDataUser;
    }
    
    public String kodeOtomatis(){
        EntityManager em=null;
        String kode="DataUser-001";
        try{
            em=getEntityManager();
            Query q=em.createQuery("SELECT u FROM DataUser u ORDER BY u.kdUser DESC");
            q.setMaxResults(1);
            DataUser user=(DataUser) q.getSingleResult();
            if(q!=null){
                DecimalFormat formatnomor = new DecimalFormat("DataUser-000");
                String nomorurut = user.getKdUser().substring(5);
                kode=formatnomor.format(Double.parseDouble(nomorurut)+1);
            }
        }catch(NoResultException ex){
            return kode;
        }
        return kode;
    }
}
