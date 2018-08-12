package futsalproject.controller;

import futsalproject.data.DataPelanggan;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class DataPelangganController implements Serializable{
        private static final long serialVersionUID = 1L;
    
    private EntityManagerFactory emf=null;
    
    public DataPelangganController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void save(DataPelanggan pelanggan){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(pelanggan);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void update(DataPelanggan pelanggan){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(pelanggan);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public void delete(String pk){
        EntityManager em = getEntityManager();
        DataPelanggan us;
        try{
            us=em.getReference(DataPelanggan.class, pk);
            us.getKdPelanggan();
            em.getTransaction().begin();
            em.remove(us);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public DataPelanggan findOneDataPelanggan(String kode){
        EntityManager em=getEntityManager();
        try{
            return em.find(DataPelanggan.class, kode);
        }finally{}
    }
    
    public List<DataPelanggan> findAllDataPelanggan(){
        EntityManager em = getEntityManager();
        List<DataPelanggan> listDataPelanggan = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT p FROM DataPelanggan p");
            listDataPelanggan = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDataPelanggan;
    }
    
    public List<DataPelanggan> searchDataPelanggan(String cari){
        EntityManager em = getEntityManager();
        List<DataPelanggan> listDataPelanggan = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT p FROM DataPelanggan p WHERE p.kdPelanggan LIKE :cari OR p.nmPelanggan LIKE :cari OR p.noHp LIKE :cari OR p.alamat LIKE :cari");
            q.setParameter("cari", "%"+cari+"%");
            listDataPelanggan = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDataPelanggan;
    }
    
    public String kodeOtomatis(){
        EntityManager em=null;
        String kode="Team-001";
        try{
            em=getEntityManager();
            Query q=em.createQuery("SELECT p FROM DataPelanggan p ORDER BY p.kdPelanggan DESC");
            q.setMaxResults(1);
            DataPelanggan pelanggan=(DataPelanggan) q.getSingleResult();
            if(q!=null){
                DecimalFormat formatnomor = new DecimalFormat("Team-000");
                String nomorurut = pelanggan.getKdPelanggan().substring(5);
                kode=formatnomor.format(Double.parseDouble(nomorurut)+1);
            }
        }catch(NoResultException ex){
            return kode;
        }
        return kode;
    }
}
