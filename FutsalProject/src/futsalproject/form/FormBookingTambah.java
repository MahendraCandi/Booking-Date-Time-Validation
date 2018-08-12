package futsalproject.form;

import futsalproject.FutsalProject;
import futsalproject.controller.BookingController;
import futsalproject.controller.DataLapanganController;
import futsalproject.controller.DataPelangganController;
import futsalproject.controller.PenyewaanController;
import futsalproject.data.Booking;
import futsalproject.data.DataLapangan;
import futsalproject.data.DataPelanggan;
import futsalproject.data.Penyewaan;
import futsalproject.data.DataUser;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class FormBookingTambah extends javax.swing.JInternalFrame {

    // TAMPIL BOOKING DETAIL DENGAN CHECK HARI LIBUR
    
    Booking booking = new Booking();
    DataUser userLogin = new DataUser();
    DataPelanggan pelanggan = new DataPelanggan();
    DataLapangan lapangan = new DataLapangan();
    Penyewaan penyewaan = new Penyewaan();
    BookingController bCont = new BookingController(FutsalProject.emf);
    DataPelangganController pCont = new DataPelangganController(FutsalProject.emf);
    DataLapanganController lCont = new DataLapanganController(FutsalProject.emf);
    PenyewaanController sewaController = new PenyewaanController(FutsalProject.emf);
    DefaultTableModel model;
    
    SimpleDateFormat sdf=new SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("in-ID"));
    double hariLibur, totalTarif, totalJam;
    long hargaMalam;
    long hargaSore;
    long jamMalam;
    long jamSore;
    double diskon;
    String vKodeBook, vNama;
    /**
     * Creates new form FormBookingTambah
     */
    public FormBookingTambah(DataUser user, Booking booking) {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
        this.setBorder(null);
        model=new DefaultTableModel();
        model.addColumn("Kode Pelanggan");
        model.addColumn("Nama Pelanggan");
        model.addColumn("No. Handphone");
        model.addColumn("Alamat");
        tablePelanggan.getTableHeader().setFont(new Font("Tahoma Plain", Font.BOLD, 11));
        userLogin = user;
        this.booking = booking;
        DialogPelanggan.setLocationRelativeTo(null);
        tidakAktif();
        validasiBooking();
        showTable();
        seleksiBaris();
        txtUser.setText(userLogin.getNmUser());
        
    }
    
    private void validasiBooking(){
        if(booking == null){
            txtKodeBooking.setText(bCont.kodeOtomatis());
            txtTglBooking.setText(sdf.format(new Date()));
            jdcTanggalPakai.setLocale(Locale.forLanguageTag("id-ID"));
            jdcTanggalPakai.setDateFormatString("dd MMMM yyyy");
            jdcTanggalPakai.setDate(new Date());
            jdcTanggalPakai.setMinSelectableDate(new Date());
            jamSpinner(spinJamMasuk);
            jamSpinner(spinJamKeluar);
            comboBoxJenisLapangan();
            comboBoxKodeLapangan();
            aktif();
        }else{
            pelanggan = pCont.findOneDataPelanggan(booking.getKdPelanggan());
            lapangan = lCont.findOneDataLapangan(booking.getKdLap());
            jamSpinner(spinJamMasuk);
            jamSpinner(spinJamKeluar);
            comboBoxJenisLapangan();
            comboBoxKodeLapangan();
            txtKodeBooking.setText(booking.getKdBooking());
            txtKodePelanggan.setText(booking.getKdPelanggan());
            txtNamaPelanggan.setText(pelanggan.getNmPelanggan());
            txtNoHandphone.setText(pelanggan.getNoHp());
            txtAlamat.setText(pelanggan.getAlamat());
            txtUser.setText(booking.getKdUser());
            txtTglBooking.setText(sdf.format(booking.getTglBooking()));
            jdcTanggalPakai.setDate(booking.getTglPakai());
            cmbJenisLapangan.setSelectedItem(lapangan.getJenisLap());
            cmbKodeLapangan.setSelectedItem(lapangan.getKdLap());
            txtTarif.setText(String.valueOf(lapangan.getTarif()));
            spinJamMasuk.setValue((booking.getJamMasuk()));
            spinJamKeluar.setValue((booking.getJamKeluar()));
            txtUangDP.setText(String.valueOf(booking.getUangDp()));
            hitung();
            
        }
    }

    private boolean validasiJam2(){
        boolean valid=false;
        try {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            String masuk = df.format(spinJamMasuk.getValue());
            String keluar = df.format(spinJamKeluar.getValue());
            
            DateFormat dff = new SimpleDateFormat("HH:mm");
            Date jamMasuk = dff.parse(masuk);
            Date jamKeluar = dff.parse(keluar);
            if(booking == null){
                List<Booking> list = bCont.findValidasiLapangan2(cmbKodeLapangan.getSelectedItem().toString(), jdcTanggalPakai.getDate());
                for(Booking b : list){
                    if(jamMasuk.compareTo(b.getJamMasuk()) == -1){
                        if(jamKeluar.compareTo(b.getJamMasuk()) == 1 && jamKeluar.compareTo(b.getJamKeluar()) == -1){
                            valid = true;
                        }else if(jamKeluar.compareTo(b.getJamMasuk()) == 1 && jamKeluar.compareTo(b.getJamKeluar()) == 0){
                            valid =true;
                        }else if(jamKeluar.compareTo(b.getJamMasuk()) == 1 && jamKeluar.compareTo(b.getJamKeluar()) == 1){
                            valid =true;
                        }
                    }else if(jamMasuk.compareTo(b.getJamMasuk()) == 0){
                        valid = true;
                    }else if(jamMasuk.compareTo(b.getJamMasuk()) == 1 && jamMasuk.compareTo(b.getJamKeluar()) == -1){
                        valid = true;
                    }
                    vKodeBook = b.getKdBooking();
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valid;
    }
    
    private void validasiTarifLapangan(){
        try{
            SimpleDateFormat hr = new SimpleDateFormat("HH:mm");
            String masuk=(hr.format(spinJamMasuk.getValue()));
            String keluar=(hr.format(spinJamKeluar.getValue()));
            
            DateFormat df = new SimpleDateFormat("HH:mm");
            Date jamMasuk = df.parse(masuk);
            Date jamKeluar = df.parse(keluar);
            Date jam6=(hr.parse("06:00"));
            Date jam15=(hr.parse("15:00"));
            Date jam18=(hr.parse("18:00"));
            Date jam24=(hr.parse("24:00"));
            
            hargaMalam = 80000;
            hargaSore = 25000;
            jamMalam = 0;
            jamSore = 0;
            
            if(jamKeluar.before(jamMasuk)){
                JOptionPane.showMessageDialog(null, "Jam tidak valid");
                spinJamKeluar.setValue(spinJamMasuk.getValue());
            }else{
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
                List<Penyewaan> list = sewaController.searchMemberEksis(txtKodePelanggan.getText(), sdf.parse(txtTglBooking.getText()));
                if(!list.isEmpty()){
                    diskon = totalTarif * (10.0/100);
                }else{
                    diskon = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        txtDiskon.setText(String.valueOf(diskon));
        return diskon;
    }
    
    private double hariLibur(){
        hariLibur = 0;
        if(cekHariLibur.isSelected()){
            hariLibur = 100000;
        }
        txtHariLibur.setText(String.valueOf(hariLibur));
        return hariLibur;
    }
    
    private void hitung(){
        validasiTarifLapangan();
        txtTarif.setText(String.valueOf(lapangan.getTarif()));
        txtTarifSore.setText( String.valueOf(lapangan.getTarif() + 25000));
        txtTarifSoreJam.setText(String.valueOf(jamSore));
        txtTarifMalam.setText( String.valueOf(lapangan.getTarif() + 80000));
        txtTarifMalamJam.setText(String.valueOf(jamMalam));
        totalTarif = (totalTarif + hariLibur()) - diskonMember() ;
        txtTotalJam.setText(String.valueOf(totalJam));
        txtTotalTarif.setText(String.valueOf(totalTarif));
    }
    
    private void comboBoxJenisLapangan(){
        String[] jenisLapangan = {"Vinyl", "Rumput"};
        cmbJenisLapangan.setModel(new DefaultComboBoxModel(jenisLapangan));
    }
    
    private void comboBoxKodeLapangan(){
        List<DataLapangan> listLapangan = lCont.searchDataLapangan(cmbJenisLapangan.getSelectedItem().toString());
        List<String> listKdLapangan = new ArrayList<>();
        for(DataLapangan l : listLapangan){
            listKdLapangan.add(l.getKdLap());
        }
        cmbKodeLapangan.setModel(new DefaultComboBoxModel(listKdLapangan.toArray()));
    }
    
    private void jamSpinner(JSpinner spin){
        try{
            Date date=new Date();
            SpinnerDateModel sm=new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
            spin.setModel(sm);
            JSpinner.DateEditor de=new JSpinner.DateEditor(spin, "kk:00");
            spin.setEditor(de);
            spin.setValue(date);
        }catch(Exception ex){}
    }
    
    // Dialog Tambah Pelanggan
    private void showTable(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        List<DataPelanggan> list = pCont.findAllDataPelanggan();
        for(DataPelanggan p : list){
            Object[] obj = new Object[4];
            obj[0] = p.getKdPelanggan();
            obj[1] = p.getNmPelanggan();
            obj[2] = p.getNoHp();
            obj[3] = p.getAlamat();
            model.addRow(obj);
        }
        tablePelanggan.setModel(model);
    }
    
    // dialog Tambah pelanggan
    private void seleksiBaris(){
        tablePelanggan.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int baris=tablePelanggan.getSelectedRow(); 
                if(baris != -1){                        
                    txtKodePelanggan1.setText(tablePelanggan.getValueAt(baris, 0).toString());
                    txtNamaPelanggan1.setText(tablePelanggan.getValueAt(baris, 1).toString());
                    txtNoHP.setText(tablePelanggan.getValueAt(baris, 2).toString());
                    txtAlamat1.setText(tablePelanggan.getValueAt(baris, 3 ).toString());
                }
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DialogPelanggan = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePelanggan = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAlamat1 = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        txtKodePelanggan1 = new javax.swing.JTextField();
        txtNamaPelanggan1 = new javax.swing.JTextField();
        txtNoHP = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnPilihPelanggan = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnSimpan = new javax.swing.JButton();
        btnKembali = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        jLabel17 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTarifSoreJam = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        spinJamMasuk = new javax.swing.JSpinner();
        btnCariPelanggan = new javax.swing.JButton();
        txtTarifMalam = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTglBooking = new javax.swing.JTextField();
        txtUser = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtTarifMalamJam = new javax.swing.JTextField();
        jdcTanggalPakai = new com.toedter.calendar.JDateChooser();
        jLabel27 = new javax.swing.JLabel();
        txtNoHandphone = new javax.swing.JTextField();
        txtTarif = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTarifSore = new javax.swing.JTextField();
        txtUangDP = new javax.swing.JTextField();
        txtDiskon = new javax.swing.JTextField();
        cmbKodeLapangan = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtTotalJam = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        spinJamKeluar = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        cmbJenisLapangan = new javax.swing.JComboBox<>();
        txtNamaPelanggan = new javax.swing.JTextField();
        txtKodeBooking = new javax.swing.JTextField();
        txtTotalTarif = new javax.swing.JTextField();
        txtKodePelanggan = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        cekHariLibur = new javax.swing.JCheckBox();
        txtHariLibur = new javax.swing.JTextField();

        DialogPelanggan.setTitle("Cari Pelanggan");
        DialogPelanggan.setAlwaysOnTop(true);
        DialogPelanggan.setBackground(new java.awt.Color(85, 239, 196));
        DialogPelanggan.setMinimumSize(new java.awt.Dimension(700, 460));
        DialogPelanggan.setModal(true);
        DialogPelanggan.getContentPane().setLayout(new java.awt.CardLayout());

        jPanel3.setBackground(new java.awt.Color(85, 239, 196));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablePelanggan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Pelanggan", "Nama Pelanggan", "No. Handphone", "Alamat"
            }
        ));
        jScrollPane2.setViewportView(tablePelanggan);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 640, 180));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Cari Pelanggan");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 660, -1));

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 660, 10));

        txtAlamat1.setColumns(20);
        txtAlamat1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtAlamat1.setLineWrap(true);
        txtAlamat1.setRows(5);
        txtAlamat1.setWrapStyleWord(true);
        jScrollPane3.setViewportView(txtAlamat1);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Alamat");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, 47, -1));

        txtKodePelanggan1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jPanel3.add(txtKodePelanggan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 180, -1));

        txtNamaPelanggan1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jPanel3.add(txtNamaPelanggan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 180, -1));

        txtNoHP.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jPanel3.add(txtNoHP, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 180, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("No. Handphone");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 110, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Nama Pelanggan");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 110, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Kode Pelanggan");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 110, -1));

        btnPilihPelanggan.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnPilihPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Plus Math_20px.png"))); // NOI18N
        btnPilihPelanggan.setText("Pilih Pelanggan");
        btnPilihPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilihPelangganActionPerformed(evt);
            }
        });
        jPanel3.add(btnPilihPelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        DialogPelanggan.getContentPane().add(jPanel3, "card2");

        jPanel1.setBackground(new java.awt.Color(0, 184, 148));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        btnSimpan.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Check File_20px_5.png"))); // NOI18N
        btnSimpan.setText("Simpan Booking");
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

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Form Tambah Booking");

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(0, 184, 148));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("Tarif Malam");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Diskon 10%");

        txtAlamat.setColumns(20);
        txtAlamat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtAlamat.setLineWrap(true);
        txtAlamat.setRows(5);
        txtAlamat.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtAlamat);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Total Jam Sewa");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Jam Masuk");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Uang DP");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Kode Pelanggan");

        txtTarifSoreJam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTarifSoreJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTarifSoreJamKeyPressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Tanggal Pakai");

        spinJamMasuk.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        spinJamMasuk.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), new java.util.Date(), null, java.util.Calendar.HOUR_OF_DAY));

        btnCariPelanggan.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnCariPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search_20px.png"))); // NOI18N
        btnCariPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariPelangganActionPerformed(evt);
            }
        });

        txtTarifMalam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTarifMalam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTarifMalamKeyPressed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Total Tarif Sewa");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Kode Booking");

        txtTglBooking.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTglBooking.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTglBookingKeyPressed(evt);
            }
        });

        txtUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUserKeyPressed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Kode Lapangan");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Tanggal Booking");

        txtTarifMalamJam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTarifMalamJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTarifMalamJamKeyPressed(evt);
            }
        });

        jdcTanggalPakai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("User");

        txtNoHandphone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNoHandphone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNoHandphoneKeyPressed(evt);
            }
        });

        txtTarif.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTarif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTarifKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Nama Pelanggan");

        txtTarifSore.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTarifSore.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTarifSoreKeyPressed(evt);
            }
        });

        txtUangDP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtUangDP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUangDPKeyPressed(evt);
            }
        });

        txtDiskon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDiskon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDiskonKeyPressed(evt);
            }
        });

        cmbKodeLapangan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbKodeLapangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKodeLapanganActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Alamat");

        txtTotalJam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTotalJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTotalJamKeyPressed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Jenis Lapangan");

        spinJamKeluar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        spinJamKeluar.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, new java.util.Date(), java.util.Calendar.HOUR));
        spinJamKeluar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinJamKeluarStateChanged(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Jam Keluar");

        cmbJenisLapangan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbJenisLapangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbJenisLapanganActionPerformed(evt);
            }
        });

        txtNamaPelanggan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNamaPelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaPelangganKeyPressed(evt);
            }
        });

        txtKodeBooking.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtKodeBooking.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeBookingKeyPressed(evt);
            }
        });

        txtTotalTarif.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTotalTarif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTotalTarifKeyPressed(evt);
            }
        });

        txtKodePelanggan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtKodePelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodePelangganKeyPressed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("Tarif Sore");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("No. Handphone");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Tarif Lapangan");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Jam");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("Jam");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Jam");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("Hari Libur");

        cekHariLibur.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cekHariLibur.setForeground(new java.awt.Color(255, 255, 255));
        cekHariLibur.setText("Hari Libur");
        cekHariLibur.setOpaque(false);
        cekHariLibur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cekHariLiburActionPerformed(evt);
            }
        });

        txtHariLibur.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtHariLibur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHariLiburKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtNoHandphone))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtNamaPelanggan))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtTarif))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(cmbKodeLapangan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cmbJenisLapangan, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(spinJamKeluar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                        .addComponent(spinJamMasuk, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jdcTanggalPakai, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtTarifSore)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtTarifSoreJam, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel24))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtTglBooking))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtUser))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtTotalJam, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel18))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18))
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)))
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                    .addComponent(cekHariLibur)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(txtHariLibur))
                                                .addComponent(txtTarifMalam, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtTarifMalamJam, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel26)))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtKodePelanggan)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCariPelanggan))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtKodeBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtUangDP, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtTotalTarif))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(407, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtKodeBooking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKodePelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCariPelanggan)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtNoHandphone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtTglBooking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jdcTanggalPakai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(cmbJenisLapangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(spinJamMasuk)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinJamKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cmbKodeLapangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtTarif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtTarifSore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(txtTarifSoreJam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtTarifMalam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(txtTarifMalamJam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(cekHariLibur)
                    .addComponent(txtHariLibur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtTotalJam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtTotalTarif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtUangDP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane4.setViewportView(jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jScrollPane4)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE))
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

    private void txtTglBookingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTglBookingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTglBookingKeyPressed

    private void btnCariPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariPelangganActionPerformed
        DialogPelanggan.setVisible(true);
    }//GEN-LAST:event_btnCariPelangganActionPerformed

    private void txtNoHandphoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoHandphoneKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoHandphoneKeyPressed

    private void txtNamaPelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaPelangganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaPelangganKeyPressed

    private void txtKodePelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodePelangganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodePelangganKeyPressed

    private void txtKodeBookingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeBookingKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
//            cariTable(txtCari.getText());
        }
    }//GEN-LAST:event_txtKodeBookingKeyPressed

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        FormBooking fb = new FormBooking(userLogin);
        JDesktopPane desktopPane = getDesktopPane();
        desktopPane.add(fb);
        fb.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        simpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtTotalJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalJamKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalJamKeyPressed

    private void btnPilihPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihPelangganActionPerformed
        if(txtKodePelanggan1.getText().isEmpty()){
            DialogPelanggan.dispose();
        }else{
            pelanggan = pCont.findOneDataPelanggan(txtKodePelanggan1.getText());
            txtKodePelanggan.setText(pelanggan.getKdPelanggan());
            txtNamaPelanggan.setText(pelanggan.getNmPelanggan());
            txtNoHandphone.setText(pelanggan.getNoHp());
            txtAlamat.setText(pelanggan.getAlamat());
            DialogPelanggan.dispose();
        }
        
    }//GEN-LAST:event_btnPilihPelangganActionPerformed

    private void cmbJenisLapanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbJenisLapanganActionPerformed
        comboBoxKodeLapangan();
        cmbKodeLapanganActionPerformed(null);
    }//GEN-LAST:event_cmbJenisLapanganActionPerformed

    private void txtTotalTarifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalTarifKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalTarifKeyPressed

    private void cmbKodeLapanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKodeLapanganActionPerformed
        if(validasiJam2() == true){
            JOptionPane.showMessageDialog(null, "Jam ini telah diboking dengan nomor " + vKodeBook);
        }else{
            lapangan = lCont.findOneDataLapangan(cmbKodeLapangan.getSelectedItem().toString());
            hitung();
//            validasiTarifLapangan();
//            txtTarif.setText(String.valueOf(lapangan.getTarif()));
//            txtTarifSore.setText( String.valueOf(lapangan.getTarif() + 25000));
//            txtTarifSoreJam.setText(String.valueOf(jamSore));
//            txtTarifMalam.setText( String.valueOf(lapangan.getTarif() + 80000));
//            txtTarifMalamJam.setText(String.valueOf(jamMalam));
//            totalTarif = totalTarif - diskonMember();
//            txtTotalJam.setText(String.valueOf(totalJam));
//            txtTotalTarif.setText(String.valueOf(totalTarif));
        }
    }//GEN-LAST:event_cmbKodeLapanganActionPerformed

    private void txtUangDPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUangDPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUangDPKeyPressed

    private void txtTarifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTarifKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTarifKeyPressed

    private void txtTarifSoreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTarifSoreKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTarifSoreKeyPressed

    private void txtTarifSoreJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTarifSoreJamKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTarifSoreJamKeyPressed

    private void txtTarifMalamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTarifMalamKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTarifMalamKeyPressed

    private void txtTarifMalamJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTarifMalamJamKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTarifMalamJamKeyPressed

    private void spinJamKeluarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinJamKeluarStateChanged
        cmbKodeLapanganActionPerformed(null);
    }//GEN-LAST:event_spinJamKeluarStateChanged

    private void txtUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserKeyPressed

    private void txtDiskonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiskonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiskonKeyPressed

    private void txtHariLiburKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHariLiburKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHariLiburKeyPressed

    private void cekHariLiburActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cekHariLiburActionPerformed
        hitung();
    }//GEN-LAST:event_cekHariLiburActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog DialogPelanggan;
    private javax.swing.JButton btnCariPelanggan;
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnPilihPelanggan;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JCheckBox cekHariLibur;
    private javax.swing.JComboBox<String> cmbJenisLapangan;
    private javax.swing.JComboBox<String> cmbKodeLapangan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private com.toedter.calendar.JDateChooser jdcTanggalPakai;
    private javax.swing.JSpinner spinJamKeluar;
    private javax.swing.JSpinner spinJamMasuk;
    private javax.swing.JTable tablePelanggan;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextArea txtAlamat1;
    private javax.swing.JTextField txtDiskon;
    private javax.swing.JTextField txtHariLibur;
    private javax.swing.JTextField txtKodeBooking;
    private javax.swing.JTextField txtKodePelanggan;
    private javax.swing.JTextField txtKodePelanggan1;
    private javax.swing.JTextField txtNamaPelanggan;
    private javax.swing.JTextField txtNamaPelanggan1;
    private javax.swing.JTextField txtNoHP;
    private javax.swing.JTextField txtNoHandphone;
    private javax.swing.JTextField txtTarif;
    private javax.swing.JTextField txtTarifMalam;
    private javax.swing.JTextField txtTarifMalamJam;
    private javax.swing.JTextField txtTarifSore;
    private javax.swing.JTextField txtTarifSoreJam;
    private javax.swing.JTextField txtTglBooking;
    private javax.swing.JTextField txtTotalJam;
    private javax.swing.JTextField txtTotalTarif;
    private javax.swing.JTextField txtUangDP;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables

    private void tidakAktif(){
        txtKodeBooking.setEditable(false);
        txtKodePelanggan.setEditable(false);
        txtNamaPelanggan.setEditable(false);
        txtNoHandphone.setEditable(false);
        txtAlamat.setEditable(false);
        txtUser.setEditable(false);
        txtTglBooking.setEditable(false);
        jdcTanggalPakai.setEnabled(false);
        spinJamMasuk.setEnabled(false);
        spinJamKeluar.setEnabled(false);
        cmbJenisLapangan.setEnabled(false);
        cmbKodeLapangan.setEnabled(false);
        txtTarif.setEditable(false);
        txtTarifMalam.setEditable(false);
        txtTarifMalamJam.setEditable(false);
        txtTarifSore.setEditable(false);
        txtTarifSoreJam.setEditable(false);
        txtTotalJam.setEditable(false);
        txtDiskon.setEditable(false);
        cekHariLibur.setEnabled(false);
        txtHariLibur.setEditable(false);
        txtTotalTarif.setEditable(false);
        btnSimpan.setEnabled(false);
        btnCariPelanggan.setEnabled(false);
        
        
        //pelanggan dialog
        txtKodePelanggan1.setEnabled(false);
        txtNamaPelanggan1.setEnabled(false);
        txtNoHP.setEnabled(false);
        txtAlamat1.setEnabled(false);
        
    }
    
    private void aktif(){
        btnSimpan.setEnabled(true);
        btnCariPelanggan.setEnabled(true);
        spinJamMasuk.setEnabled(true);
        spinJamKeluar.setEnabled(true);
        jdcTanggalPakai.setEnabled(true);
        cmbJenisLapangan.setEnabled(true);
        cmbKodeLapangan.setEnabled(true);
        cekHariLibur.setEnabled(true);
    }
    
    private void simpan(){
        if(txtKodePelanggan.getText().isEmpty() || txtTotalTarif.getText().isEmpty() || txtUangDP.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Data belum lengkap!");
        }else if(validasiJam2() == true){
            JOptionPane.showMessageDialog(null, "Jam ini telah diboking dengan nomor " + vKodeBook);
        }else{
            try {
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                String masuk = df.format(spinJamMasuk.getValue());
                String keluar = df.format(spinJamKeluar.getValue());
                booking = new Booking();
                booking.setDiskon(Double.parseDouble(txtDiskon.getText()));
                booking.setJamMasuk(df.parse(masuk));
                booking.setJamKeluar(df.parse(keluar));
                booking.setKdBooking(txtKodeBooking.getText());
                booking.setKdLap(cmbKodeLapangan.getSelectedItem().toString());
                booking.setKdPelanggan(txtKodePelanggan.getText());
                booking.setKdUser(userLogin.getKdUser());
                booking.setTglBooking(sdf.parse(txtTglBooking.getText()));
                booking.setTglPakai(jdcTanggalPakai.getDate());
                booking.setUangDp(Double.parseDouble(txtUangDP.getText()));
                booking.setHariLibur(Double.parseDouble(txtHariLibur.getText()));
                bCont.save(booking);
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
                
                FormBooking fb = new FormBooking(userLogin);
                JDesktopPane desktopPane = getDesktopPane();
                desktopPane.add(fb);
                fb.setVisible(true);
                this.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
           
        }
    }
}
