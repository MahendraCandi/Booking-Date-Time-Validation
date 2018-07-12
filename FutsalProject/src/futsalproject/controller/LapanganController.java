package futsalproject.controller;

import futsalproject.data.Lapangan;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class LapanganController implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private EntityManagerFactory emf=null;
    
    public LapanganController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void save(Lapangan lapangan){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(lapangan);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void update(Lapangan lapangan){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(lapangan);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public void delete(String pk){
        EntityManager em = getEntityManager();
        Lapangan lap;
        try{
            lap=em.getReference(Lapangan.class, pk);
            lap.getKdLap();
            em.getTransaction().begin();
            em.remove(lap);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public Lapangan findOneLapangan(String kode){
        EntityManager em=getEntityManager();
        try{
            return em.find(Lapangan.class, kode);
        }finally{}
    }
    
    public List<Lapangan> findAllLapangan(){
        EntityManager em = getEntityManager();
        List<Lapangan> listLapangan = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT l FROM Lapangan l");
            listLapangan = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLapangan;
    }
    
    public List<Lapangan> searchLapangan(String cari){
        EntityManager em = getEntityManager();
        List<Lapangan> listLapangan = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT l FROM Lapangan l WHERE l.kdLap LIKE :cari OR l.jenisLap LIKE :cari OR l.tarif LIKE :cari");
            q.setParameter("cari", "%"+cari+"%");
            listLapangan = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLapangan;
    }
    
    public String kodeOtomatis(){
        EntityManager em=null;
        String kode="Lap-001";
        try{
            em=getEntityManager();
            Query q=em.createQuery("SELECT l FROM Lapangan l ORDER BY l.kdLap DESC");
            q.setMaxResults(1);
            Lapangan lapangan=(Lapangan) q.getSingleResult();
            if(q!=null){
                DecimalFormat formatnomor = new DecimalFormat("Lap-000");
                String nomorurut = lapangan.getKdLap().substring(4);
                kode=formatnomor.format(Double.parseDouble(nomorurut)+1);
            }
        }catch(NoResultException ex){
            return kode;
        }
        return kode;
    }
}
