package futsalproject.controller;

import java.io.Serializable;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import futsalproject.data.Akun;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
public class AkunController implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private EntityManagerFactory emf=null;
    
    public AkunController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void save(Akun akun){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(akun);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void update(Akun akun){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(akun);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public void delete(String pk){
        EntityManager em = getEntityManager();
        Akun us;
        try{
            us=em.getReference(Akun.class, pk);
            us.getKdAkun();
            em.getTransaction().begin();
            em.remove(us);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public Akun findOneAkun(String kode){
        EntityManager em=getEntityManager();
        try{
            return em.find(Akun.class, kode);
        }finally{}
    }
    
    public List<Akun> findAllAkun(){
        EntityManager em = getEntityManager();
        List<Akun> listAkun = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT u FROM Akun u");
            listAkun = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listAkun;
    }
    
    public List<Akun> searchAkun(String cari){
        EntityManager em = getEntityManager();
        List<Akun> listAkun = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT a FROM Akun a WHERE a.kdAkun LIKE :cari OR a.nmAkun LIKE :cari OR a.jenisAkun LIKE :cari");
            q.setParameter("cari", "%"+cari+"%");
            listAkun = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listAkun;
    }
    
    public String kodeOtomatis(){
        EntityManager em=null;
        String kode="Akun-001";
        try{
            em=getEntityManager();
            Query q=em.createQuery("SELECT a FROM Akun a ORDER BY a.kdAkun DESC");
            q.setMaxResults(1);
            Akun akun=(Akun) q.getSingleResult();
            if(q!=null){
                DecimalFormat formatnomor = new DecimalFormat("Akun-000");
                String nomorurut = akun.getKdAkun().substring(5);
                kode=formatnomor.format(Double.parseDouble(nomorurut)+1);
            }
        }catch(NoResultException ex){
            return kode;
        }
        return kode;
    }
}
