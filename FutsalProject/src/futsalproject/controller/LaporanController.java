package futsalproject.controller;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class LaporanController implements Serializable{
    private EntityManagerFactory emf=null;
    
    public LaporanController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void cetakBooking(String kdBooking, double tarifMalam, double tarifSore, double totalTarif){
        EntityManager em = null;
        try{
            em = emf.createEntityManager();
            em.getTransaction().begin();
            Connection connect=em.unwrap(Connection.class);
            HashMap param = new HashMap();
            Locale local=new Locale("id", "ID");
            param.put(JRParameter.REPORT_LOCALE, local);
            param.put("kode_booking", kdBooking);
            param.put("tarif_malam", tarifMalam);
            param.put("tarif_sore", tarifSore);
            param.put("total_tarif", totalTarif);
            JasperPrint jprint=JasperFillManager.fillReport ( getClass().getResourceAsStream("/report/KwitansiBooking.jasper"), param, connect);
            JasperViewer viewer=new JasperViewer(jprint, false);
            viewer.setFitPageZoomRatio();
            viewer.setVisible(true);
            connect.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Ups ada kesalahan! " + e.getMessage());
            e.printStackTrace();
        }finally{
            em.close();
        }
    }
    
    public void cetakPenyewaan(String noTrans, double tarifMalam, double tarifSore){
        EntityManager em = null;
        try{
            em = emf.createEntityManager();
            em.getTransaction().begin();
            Connection connect=em.unwrap(Connection.class);
            HashMap param = new HashMap();
            Locale local=new Locale("id", "ID");
            param.put(JRParameter.REPORT_LOCALE, local);
            param.put("noTrans", noTrans);
            param.put("tarif_malam", tarifMalam);
            param.put("tarif_sore", tarifSore);
            JasperPrint jprint=JasperFillManager.fillReport ( getClass().getResourceAsStream("/report/KwitansiPenyewaan.jasper"), param, connect);
            JasperViewer viewer=new JasperViewer(jprint, false);
            viewer.setFitPageZoomRatio();
            viewer.setVisible(true);
            connect.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Ups ada kesalahan! " + e.getMessage());
            e.printStackTrace();
        }finally{
            em.close();
        }
    }
}
