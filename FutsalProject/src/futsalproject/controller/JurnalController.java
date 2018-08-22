package futsalproject.controller;

import futsalproject.data.Jurnal;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class JurnalController implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private EntityManagerFactory emf=null;
    
    public JurnalController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void save(Jurnal jurnal){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(jurnal);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void update(Jurnal jurnal){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(jurnal);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public void delete(String kode){
        EntityManager em = getEntityManager();
        Jurnal jr;
        try{
            jr=em.getReference(Jurnal.class, kode);
            jr.getNoJurnal();
            em.getTransaction().begin();
            em.remove(jr);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public Jurnal findJurnal(String kode){
        EntityManager em=getEntityManager();
        try{
            return em.find(Jurnal.class, kode);
        }finally{}
    }
    
    public List<Jurnal> findAllJurnal(){
        EntityManager em = getEntityManager();
        List<Jurnal> list = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT j FROM Jurnal j");
            list = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Jurnal> SearchJurnal(String cari){
        EntityManager em = getEntityManager();
        List<Jurnal> list = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT j FROM Jurnal j WHERE j.noJurnal LIKE :cari OR j.noTrans LIKE :cari");
            q.setParameter("cari", "%" +cari+ "%");
            list = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Object[]> findAllJurnalAndTotalSewaByDate(Date tgl1, Date tgl2){
        EntityManager em = getEntityManager();
        List<Object[]> list = new ArrayList<>();
        try {
            Query q=em.createQuery("SELECT j, p.totalSewa FROM Jurnal j, Penyewaan p WHERE j.noTrans = p.noTrans AND (j.tglJurnal BETWEEN :tgl1 AND :tgl2)");
            q.setParameter("tgl1", tgl1);
            q.setParameter("tgl2", tgl2);
            list = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Object[] firstDateLastDate(){
        EntityManager em = getEntityManager();
        Object[] obj=new Object[2];
        try {
            Query q=em.createQuery("SELECT MIN(j.tglJurnal), MAX(j.tglJurnal) FROM Jurnal j");
            List<Object[]> list = q.getResultList();
            for(Object[] o : list){
                obj[0] = o[0];
                obj[1] = o[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }
    
    public String nomorOtomatis(){
        String kode="Jurnal-001";
        EntityManager em=null;
        try{
            em=getEntityManager();
            Query q=em.createQuery("SELECT j FROM Jurnal j ORDER BY j.noJurnal DESC");
            q.setMaxResults(1);
            Jurnal jurnal=(Jurnal) q.getSingleResult();
            if(q!=null){
                DecimalFormat formatnomor = new DecimalFormat("Jurnal-000");
                String nomorurut = jurnal.getNoJurnal().substring(7);
                kode=formatnomor.format(Double.parseDouble(nomorurut)+1);
            }
        }catch(NoResultException ex){
            return kode;
        }
        return kode;
    }
}
