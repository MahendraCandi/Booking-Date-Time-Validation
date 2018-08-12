package futsalproject.controller;

import java.io.Serializable;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import futsalproject.data.DataPerkiraan;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
public class DataPerkiraanController implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private EntityManagerFactory emf=null;
    
    public DataPerkiraanController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void save(DataPerkiraan perkiraan){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(perkiraan);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void update(DataPerkiraan perkiraan){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(perkiraan);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public void delete(String pk){
        EntityManager em = getEntityManager();
        DataPerkiraan us;
        try{
            us=em.getReference(DataPerkiraan.class, pk);
            us.getKdPerkiraan();
            em.getTransaction().begin();
            em.remove(us);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public DataPerkiraan findOneDataPerkiraan(String kode){
        EntityManager em=getEntityManager();
        try{
            return em.find(DataPerkiraan.class, kode);
        }finally{}
    }
    
    public DataPerkiraan findOneDataPerkiraanByNama(String nama){
        EntityManager em = getEntityManager();
        DataPerkiraan dp = new DataPerkiraan();
        try {
            Query q = em.createQuery("SELECT da FROM DataPerkiraan da WHERE da.nmPerkiraan = :nama");
            q.setParameter("nama", nama);
            dp = (DataPerkiraan) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return dp;
    }
    
    public List<DataPerkiraan> findAllDataPerkiraan(){
        EntityManager em = getEntityManager();
        List<DataPerkiraan> listDataPerkiraan = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT u FROM DataPerkiraan u");
            listDataPerkiraan = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDataPerkiraan;
    }
    
    public List<DataPerkiraan> searchDataPerkiraan(String cari){
        EntityManager em = getEntityManager();
        List<DataPerkiraan> listDataPerkiraan = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT a FROM DataPerkiraan a WHERE a.kdPerkiraan LIKE :cari OR a.nmPerkiraan LIKE :cari OR a.jenisPerkiraan LIKE :cari");
            q.setParameter("cari", "%"+cari+"%");
            listDataPerkiraan = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDataPerkiraan;
    }
    
//    public String kodeOtomatis(){
//        EntityManager em=null;
//        String kode="DataPerkiraan-001";
//        try{
//            em=getEntityManager();
//            Query q=em.createQuery("SELECT a FROM DataPerkiraan a ORDER BY a.kdDataPerkiraan DESC");
//            q.setMaxResults(1);
//            DataPerkiraan perkiraan=(DataPerkiraan) q.getSingleResult();
//            if(q!=null){
//                DecimalFormat formatnomor = new DecimalFormat("DataPerkiraan-000");
//                String nomorurut = perkiraan.getKdDataPerkiraan().substring(5);
//                kode=formatnomor.format(Double.parseDouble(nomorurut)+1);
//            }
//        }catch(NoResultException ex){
//            return kode;
//        }
//        return kode;
//    }
}
