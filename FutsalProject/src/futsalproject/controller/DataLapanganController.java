package futsalproject.controller;

import futsalproject.data.DataLapangan;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class DataLapanganController implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private EntityManagerFactory emf=null;
    
    public DataLapanganController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void save(DataLapangan lapangan){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(lapangan);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void update(DataLapangan lapangan){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(lapangan);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public void delete(String pk){
        EntityManager em = getEntityManager();
        DataLapangan lap;
        try{
            lap=em.getReference(DataLapangan.class, pk);
            lap.getKdLap();
            em.getTransaction().begin();
            em.remove(lap);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public DataLapangan findOneDataLapangan(String kode){
        EntityManager em=getEntityManager();
        try{
            return em.find(DataLapangan.class, kode);
        }finally{}
    }
    
    public List<DataLapangan> findAllDataLapangan(){
        EntityManager em = getEntityManager();
        List<DataLapangan> listDataLapangan = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT l FROM DataLapangan l");
            listDataLapangan = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDataLapangan;
    }
    
    public List<DataLapangan> searchDataLapangan(String cari){
        EntityManager em = getEntityManager();
        List<DataLapangan> listDataLapangan = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT l FROM DataLapangan l WHERE l.kdLap LIKE :cari OR l.jenisLap LIKE :cari");
            q.setParameter("cari", "%"+cari+"%");
            listDataLapangan = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDataLapangan;
    }
    
    public String kodeOtomatis(){
        EntityManager em=null;
        String kode="Lap-001";
        try{
            em=getEntityManager();
            Query q=em.createQuery("SELECT l FROM DataLapangan l ORDER BY l.kdLap DESC");
            q.setMaxResults(1);
            DataLapangan lapangan=(DataLapangan) q.getSingleResult();
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
