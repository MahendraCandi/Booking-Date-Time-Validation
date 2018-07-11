package futsalproject.controller;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import futsalproject.data.User;
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
    
    public void save(User user){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void update(User user){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public void delete(int pk){
        EntityManager em = getEntityManager();
        User us;
        try{
            us=em.getReference(User.class, pk);
            us.getKdUser();
            em.getTransaction().begin();
            em.remove(us);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public User findOneUser(String kode){
        EntityManager em=getEntityManager();
        try{
            return em.find(User.class, kode);
        }finally{}
    }
    
    public List<User> findAllUser(){
        EntityManager em = getEntityManager();
        List<User> listUser = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT u FROM User u");
            listUser = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listUser;
    }
    
    public String kodeOtomatis(){
        EntityManager em=null;
        String kode="User-001";
        try{
            em=getEntityManager();
            Query q=em.createQuery("SELECT u FROM User u ORDER BY u.kdUser DESC");
            q.setMaxResults(1);
            User user=(User) q.getSingleResult();
            if(q!=null){
                DecimalFormat formatnomor = new DecimalFormat("User-000");
                String nomorurut = user.getKdUser().substring(5);
                kode=formatnomor.format(Double.parseDouble(nomorurut)+1);
            }
        }catch(NoResultException ex){
            ex.printStackTrace();
        }
        return kode;
    }
}
