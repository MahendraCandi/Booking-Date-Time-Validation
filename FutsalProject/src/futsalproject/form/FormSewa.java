 package futsalproject.form;

import futsalproject.FutsalProject;
import futsalproject.controller.BookingController;
import futsalproject.controller.LapanganController;
import futsalproject.controller.PelangganController;
import futsalproject.controller.PenyewaanController;
import futsalproject.data.Booking;
import futsalproject.data.Lapangan;
import futsalproject.data.Pelanggan;
import futsalproject.data.Penyewaan;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

public class FormSewa extends javax.swing.JInternalFrame {

    Penyewaan penyewaan = new Penyewaan();
    Booking booking = new Booking();
    Pelanggan pelanggan = new Pelanggan();
    Lapangan lapangan = new Lapangan();
    BookingController bCont = new BookingController(FutsalProject.emf);
    PenyewaanController sewaCont = new PenyewaanController(FutsalProject.emf);
    PelangganController pCont = new PelangganController(FutsalProject.emf);
    LapanganController lCont = new LapanganController(FutsalProject.emf);
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("id-ID"));
    DateFormat df = new SimpleDateFormat("HH:mm");
    
    double totalTarif, totalJam;
    long hargaMalam;
    long hargaSore;
    long jamMalam;
    long jamSore;
    double diskon;
    /**
     * Creates new form FormSewa
     */
    public FormSewa(Penyewaan penyewaan) {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
        this.setBorder(null);
        this.penyewaan = penyewaan;
        tidakAktif();
        comboBoking();
        validasiSewa();
        
    }
    
    private void tidakAktif(){
        txtNoTrans.setEnabled(false);
        cmbKodeBooking.setEnabled(false);
        txtTglBooking.setEnabled(false);
        txtTglSewa.setEnabled(false);
        txtKodeLapangan.setEnabled(false);
        txtKodePelanggan.setEnabled(false);
        txtNamaPelanggan.setEnabled(false);
        txtJenisLapangan.setEnabled(false);
        txtJamMasuk.setEnabled(false);
        txtJamKeluar.setEnabled(false);
        txtLamaSewa.setEnabled(false);
        txtDiskonSewa.setEnabled(false);
        txtTotalSewa.setEnabled(false);
        txtUangDp.setEnabled(false);
        txtSisaSewa.setEnabled(false);
        txtUangBayar.setEnabled(false);
        
    }
    
    private void validasiSewa(){
        if(penyewaan == null){
            txtUangBayar.setEnabled(true);
            txtNoTrans.setText(sewaCont.kodeOtomatis());
            txtTglSewa.setText(sdf.format(new Date()));
            cmbKodeBooking.setEnabled(true);
        }else{
            txtNoTrans.setText(penyewaan.getNoTrans());
            txtTglSewa.setText(sdf.format(penyewaan.getTglSewa()));
            dataBooking();
            validasiTarifLapangan();

            txtLamaSewa.setText(String.valueOf(totalJam));
            txtTotalSewa.setText(String.valueOf(totalTarif - diskonMember()));
            double dp = Double.parseDouble(txtUangDp.getText());
            txtSisaSewa.setText(String.valueOf(totalTarif - dp));
            txtUangBayar.setText(String.valueOf(penyewaan.getUangByr()));
        }
    }
    
    private void comboBoking(){
        if(penyewaan == null){
            List<Object[]> listBooking = bCont.findAllBooking();
            List<Object> listKode = new ArrayList<>();
            for(Object[] obj : listBooking){
                listKode.add(obj[0]);
            }
            cmbKodeBooking.setModel(new DefaultComboBoxModel(listKode.toArray()));
        }else{
            Object[] obj = {penyewaan.getKdBooking()};
            cmbKodeBooking.setModel(new DefaultComboBoxModel(obj));
        }
    }
    
    private void dataBooking(){
        if(penyewaan == null){
            booking = bCont.findOneBooking(cmbKodeBooking.getSelectedItem().toString());
        }else{
            booking = bCont.findOneBooking(penyewaan.getKdBooking());
        }
        pelanggan = pCont.findOnePelanggan(booking.getKdPelanggan());
        lapangan = lCont.findOneLapangan(booking.getKdLap());
        txtTglBooking.setText(sdf.format(booking.getTglBooking()));
        txtKodePelanggan.setText(booking.getKdPelanggan());
        txtNamaPelanggan.setText(pelanggan.getNmPelanggan());
        txtKodeLapangan.setText(booking.getKdLap());
        txtJenisLapangan.setText(lapangan.getJenisLap());
        txtJamMasuk.setText(df.format(booking.getJamMasuk()));
        txtJamKeluar.setText(df.format(booking.getJamKeluar()));
        txtUangDp.setText(String.valueOf(booking.getUangDp()));
    }
    
    private void validasiTarifLapangan(){
        try{
            SimpleDateFormat hr = new SimpleDateFormat("HH:mm");
            DateFormat df = new SimpleDateFormat("HH:mm");
            Date jamMasuk = df.parse(txtJamMasuk.getText());
            Date jamKeluar = df.parse(txtJamKeluar.getText());
            Date jam6=(hr.parse("06:00"));
            Date jam15=(hr.parse("15:00"));
            Date jam18=(hr.parse("18:00"));
            Date jam24=(hr.parse("24:00"));
            
            hargaMalam = 80000;
            hargaSore = 25000;
            jamMalam = 0;
            jamSore = 0;
                        
            // jam malam 18:00 - 24:00
            if(jamMasuk.compareTo(jam18) == 0 || jamMasuk.compareTo(jam18) == 1){
                if(jamKeluar.compareTo(jam24) == -1 || jamKeluar.compareTo(jam24) == 0){
                    jamMalam = (jamKeluar.getTime() - jamMasuk.getTime()) / (60*60*1000);
                    hargaMalam += lapangan.getTarif();
                    totalTarif = hargaMalam * jamMalam;
                    totalJam = jamMalam;
                }
                return;

                // jam sore 15:00 - 18:00
            }else if(jamMasuk.compareTo(jam15) == 0 || jamMasuk.compareTo(jam15) == 1){
                if(jamKeluar.compareTo(jam18) == -1 || jamKeluar.compareTo(jam18) == 0){
                    jamSore = (jamKeluar.getTime() - jamMasuk.getTime()) / (60*60*1000);
                    hargaSore += lapangan.getTarif();
                    totalTarif = hargaSore * jamSore;
                    totalJam = jamSore;
                }
                else if(jamKeluar.compareTo(jam24) == -1 || jamKeluar.compareTo(jam24) == 0){
                    jamSore = (jam18.getTime() - jamMasuk.getTime()) / (60*60*1000);
                    jamMalam = (jamKeluar.getTime() - jam18.getTime()) / (60*60*1000);
                    hargaSore += lapangan.getTarif() * jamSore;
                    hargaMalam += lapangan.getTarif() * jamMalam;
                    totalTarif = hargaSore + hargaMalam;
                    totalJam = jamSore + jamMalam ;
                }
                return;

                // jam pagi 06:00 - 15:00
            }if(jamMasuk.compareTo(jam6) == 0 || jamMasuk.compareTo(jam6) == 1){
                if(jamKeluar.compareTo(jam15) == -1 || jamKeluar.compareTo(jam15) == 0){
                    long x = (jamKeluar.getTime() - jamMasuk.getTime()) / (60*60*1000);
                    totalJam = x;
                    totalTarif = lapangan.getTarif() * x;
                }
                else if(jamKeluar.compareTo(jam18) == -1 || jamKeluar.compareTo(jam18) == 0){
                    long x = (jam15.getTime() - jamMasuk.getTime()) / (60*60*1000);
                    jamSore = (jamKeluar.getTime() - jam15.getTime()) / (60*60*1000);
                    totalJam = x + jamSore;
                    x *= lapangan.getTarif();
                    hargaSore += lapangan.getTarif() * jamSore;
                    totalTarif = x + hargaSore;

                }
                else if(jamKeluar.compareTo(jam24) == -1 || jamKeluar.compareTo(jam24) == 0){
                    long x = (jam15.getTime() - jamMasuk.getTime()) / (60*60*1000);
                    jamSore = (jam18.getTime() - jam15.getTime()) / (60*60*1000);
                    jamMalam = (jamKeluar.getTime() - jam18.getTime()) / (60*60*1000);
                    totalJam = x + jamSore + jamMalam;
                    x *= lapangan.getTarif();
                    hargaSore += lapangan.getTarif() * jamSore;
                    hargaMalam += lapangan.getTarif() * jamMalam;
                    totalTarif = x + hargaSore + hargaMalam;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private double diskonMember(){
        if(txtKodePelanggan.getText().isEmpty()){
            diskon = 0;
        }else{
            try {
                List<Penyewaan> list = sewaCont.searchMemberEksis(txtKodePelanggan.getText(), sdf.parse(txtTglBooking.getText()));
                if(!list.isEmpty()){
                    diskon = totalTarif * (10.0/100);
                }else{
                    diskon = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        txtDiskonSewa.setText(String.valueOf(diskon));
        return diskon;
    }
    
    private void simpan(){
        if(txtUangBayar.getText().isEmpty() || txtTotalSewa.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Data belum lengkap!");
        }else{
            double harga = Double.parseDouble(txtSisaSewa.getText());
            double ubay = Double.parseDouble(txtUangBayar.getText());
            if(ubay < (harga)){
                JOptionPane.showMessageDialog(null, "Uang bayar kurang!");
            }else{
                try {
                    penyewaan = new Penyewaan();
                    penyewaan.setDiskonSewa(Double.parseDouble(txtDiskonSewa.getText()));
                    penyewaan.setJamSewaKeluar(df.parse(txtJamKeluar.getText()));
                    penyewaan.setJamSewaMasuk(df.parse(txtJamMasuk.getText()));
                    penyewaan.setKdBooking(cmbKodeBooking.getSelectedItem().toString());
                    penyewaan.setKdLap(txtKodeLapangan.getText());
                    penyewaan.setKdPelanggan(txtKodePelanggan.getText());
                    penyewaan.setKdUser(booking.getKdUser());
                    penyewaan.setLamaSewa(Double.parseDouble(txtLamaSewa.getText()));
                    penyewaan.setNoTrans(txtNoTrans.getText());
                    penyewaan.setTglSewa(sdf.parse(txtTglSewa.getText()));
                    penyewaan.setTotalSewa(Double.parseDouble(txtTotalSewa.getText()));
                    penyewaan.setUangByr(Double.parseDouble(txtUangBayar.getText()));
                    sewaCont.save(penyewaan);
                    JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
                    btnKembaliActionPerformed(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnSimpan = new javax.swing.JButton();
        btnKembali = new javax.swing.JButton();
        txtNoTrans = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        cmbKodeBooking = new javax.swing.JComboBox<>();
        txtTglBooking = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTglSewa = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtKodePelanggan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNamaPelanggan = new javax.swing.JTextField();
        txtKodeLapangan = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtJenisLapangan = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtJamMasuk = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtJamKeluar = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtLamaSewa = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtDiskonSewa = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtTotalSewa = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtUangDp = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtUangBayar = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtSisaSewa = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(0, 184, 148));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("No. Transaksi");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        btnSimpan.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Check File_20px_5.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        jPanel2.add(btnSimpan);

        btnKembali.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnKembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Undo_20px.png"))); // NOI18N
        btnKembali.setText("Kembali");
        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });
        jPanel2.add(btnKembali);

        txtNoTrans.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Form Penyewaan");

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Kode Booking");

        cmbKodeBooking.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbKodeBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKodeBookingActionPerformed(evt);
            }
        });

        txtTglBooking.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Tgl. Booking");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Tgl. Sewa");

        txtTglSewa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Kode Pelanggan");

        txtKodePelanggan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Nama Pelanggan");

        txtNamaPelanggan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtKodeLapangan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Kode Lapangan");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Jenis Lapangan");

        txtJenisLapangan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Jam Sewa Masuk");

        txtJamMasuk.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Jam Sewa Keluar");

        txtJamKeluar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Diskon Sewa");

        txtLamaSewa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Lama Sewa");

        txtDiskonSewa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Total Sewa");

        txtTotalSewa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Uang DP");

        txtUangDp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Uang Bayar");

        txtUangBayar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Sisa Sewa");

        txtSisaSewa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtNoTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtTglSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtTglBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cmbKodeBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtJamKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtJamMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtKodePelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtJenisLapangan, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtKodeLapangan, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtLamaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtUangDp, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtTotalSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtDiskonSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtSisaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtUangBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 135, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNoTrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtTglSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(cmbKodeBooking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtTglBooking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(txtJamMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(txtJamKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLamaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDiskonSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotalSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUangDp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtKodePelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtKodeLapangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtJenisLapangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSisaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUangBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addContainerGap(190, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        FormSewaDaftar fsd = new FormSewaDaftar();
        JDesktopPane jd = getDesktopPane();
        jd.add(fsd);
        fsd.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        simpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void cmbKodeBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKodeBookingActionPerformed
        dataBooking();
        validasiSewa();
        validasiTarifLapangan();
        
        txtLamaSewa.setText(String.valueOf(totalJam));
        txtTotalSewa.setText(String.valueOf(totalTarif - diskonMember()));
        double dp = Double.parseDouble(txtUangDp.getText());
        txtSisaSewa.setText(String.valueOf(totalTarif - dp));
    }//GEN-LAST:event_cmbKodeBookingActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cmbKodeBooking;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtDiskonSewa;
    private javax.swing.JTextField txtJamKeluar;
    private javax.swing.JTextField txtJamMasuk;
    private javax.swing.JTextField txtJenisLapangan;
    private javax.swing.JTextField txtKodeLapangan;
    private javax.swing.JTextField txtKodePelanggan;
    private javax.swing.JTextField txtLamaSewa;
    private javax.swing.JTextField txtNamaPelanggan;
    private javax.swing.JTextField txtNoTrans;
    private javax.swing.JTextField txtSisaSewa;
    private javax.swing.JTextField txtTglBooking;
    private javax.swing.JTextField txtTglSewa;
    private javax.swing.JTextField txtTotalSewa;
    private javax.swing.JTextField txtUangBayar;
    private javax.swing.JTextField txtUangDp;
    // End of variables declaration//GEN-END:variables
}
