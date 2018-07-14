package futsalproject.form;

import futsalproject.FutsalProject;
import futsalproject.controller.BookingController;
import futsalproject.controller.LapanganController;
import futsalproject.controller.PelangganController;
import futsalproject.data.Booking;
import futsalproject.data.Lapangan;
import futsalproject.data.Pelanggan;
import futsalproject.data.User;
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

    Booking booking = new Booking();
    User userLogin = new User();
    Pelanggan pelanggan = new Pelanggan();
    Lapangan lapangan = new Lapangan();
    BookingController bCont = new BookingController(FutsalProject.emf);
    PelangganController pCont = new PelangganController(FutsalProject.emf);
    LapanganController lCont = new LapanganController(FutsalProject.emf);
    DefaultTableModel model;
    
    SimpleDateFormat sdf=new SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("in-ID"));
        
    /**
     * Creates new form FormBookingTambah
     */
    public FormBookingTambah(User user, Booking booking) {
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
        validasiBooking();
        showTable();
        seleksiBaris();
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
            
        }
    }
    
    private boolean validasiJam(){
        boolean valid=false;
        try{
            System.out.println("1");
            Date masuk=(Date) spinJamMasuk.getValue();
            System.out.println("2");
            Date keluar=(Date) spinJamKeluar.getValue();
            System.out.println("3");
            Date tglPakai=(jdcTanggalPakai.getDate());
            System.out.println("4");
            String kodeLap = cmbKodeLapangan.getSelectedItem().toString();
            System.out.println("5");
            List<Object[]> hasil=bCont.findValidasiLapangan(kodeLap, tglPakai, masuk, keluar);
            System.out.println("6");
            for(Object[] b : hasil){
                System.out.println("1");
                System.out.println( "TESS "+b[0]);
                System.out.println(b[1]);
                System.out.println(b[2]);
                System.out.println(b[3]);
            }
            System.out.println("7");
            if(!hasil.isEmpty()){
                System.out.println("8");
                valid=true;
            }
        }catch(Exception ex){}
        return valid;
    }
    
    private void validasiTarifLapangan(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String masuk=(sdf.format(spinJamMasuk.getValue()));
            String keluar=(sdf.format(spinJamKeluar.getValue()));
            
            DateFormat df = new SimpleDateFormat("HH:mm");
            Date jamMasuk = df.parse(masuk);
            Date jamKeluar = df.parse(keluar);
            Date jam6=(sdf.parse("06:00"));
            Date jam15=(sdf.parse("15:00"));
            Date jam18=(sdf.parse("18:00"));
            Date jam24=(sdf.parse("24:00"));
            if(jamKeluar.before(jamMasuk)){
                JOptionPane.showMessageDialog(null, "Jam tidak valid");
            }else{
                if(jamMasuk.compareTo(jam18) == 0 || jamMasuk.compareTo(jam18) == 1){
                    System.out.println("Malam");
                    if(jamKeluar.compareTo(jam24) == -1 || jamKeluar.compareTo(jam24) == 0){
                        long y = (jamKeluar.getTime() - jamMasuk.getTime()) / (60*60*1000);
                        System.out.println("selisih 18 " + (y));
                    }
                    
                }else if(jamMasuk.compareTo(jam15) == 0 || jamMasuk.compareTo(jam15) == 1){
                    System.out.println("Sore");
                    if(jamKeluar.compareTo(jam18) == -1 || jamKeluar.compareTo(jam18) == 0){
                        long y = (jamKeluar.getTime() - jamMasuk.getTime()) / (60*60*1000);
                        System.out.println("selisih 15 " + (y));
                    }
                    else if(jamKeluar.compareTo(jam24) == -1 || jamKeluar.compareTo(jam24) == 0){
                        long y = (jam18.getTime() - jamMasuk.getTime()) / (60*60*1000);
                        long z = (jamKeluar.getTime() - jam18.getTime()) / (60*60*1000);
                        System.out.println("selisih 15 " + (y));
                        System.out.println("selisih 18 " + (z));
                    }
                }if(jamMasuk.compareTo(jam6) == 0 || jamMasuk.compareTo(jam6) == 1){
                    System.out.println("pagi");
                    if(jamKeluar.compareTo(jam15) == -1 || jamKeluar.compareTo(jam15) == 0){
                        long x = (jamKeluar.getTime() - jamMasuk.getTime()) / (60*60*1000);
                        System.out.println("selisih " + (x));
                    }
                    else if(jamKeluar.compareTo(jam18) == -1 || jamKeluar.compareTo(jam18) == 0){
                        long x = (jam15.getTime() - jamMasuk.getTime()) / (60*60*1000);
                        long y = (jamKeluar.getTime() - jam15.getTime()) / (60*60*1000);
                        System.out.println("selisih 6 " + (x));
                        System.out.println("selisih 15 " + (y));
                    }
                    else if(jamKeluar.compareTo(jam24) == -1 || jamKeluar.compareTo(jam24) == 0){
                        long x = (jam15.getTime() - jamMasuk.getTime()) / (60*60*1000);
                        long y = (jam18.getTime() - jam15.getTime()) / (60*60*1000);
                        long z = (jamKeluar.getTime() - jam18.getTime()) / (60*60*1000);
                        System.out.println("selisih 6 " + (x));
                        System.out.println("selisih 15 " + (y));
                        System.out.println("selisih 18 " + (z));
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    private void comboBoxJenisLapangan(){
        String[] jenisLapangan = {"Vinyl", "Rumput"};
        cmbJenisLapangan.setModel(new DefaultComboBoxModel(jenisLapangan));
    }
    
    private void comboBoxKodeLapangan(){
        List<Lapangan> listLapangan = lCont.searchLapangan(cmbJenisLapangan.getSelectedItem().toString());
        List<String> listKdLapangan = new ArrayList<>();
        for(Lapangan l : listLapangan){
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
        }catch(Exception ex){}
    }
    
    // Dialog Tambah Pelanggan
    private void showTable(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        List<Pelanggan> list = pCont.findAllPelanggan();
        for(Pelanggan p : list){
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
        txtKodeBooking = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtKodePelanggan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNamaPelanggan = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNoHandphone = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnCariPelanggan = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        txtTglBooking = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jdcTanggalPakai = new com.toedter.calendar.JDateChooser();
        spinJamMasuk = new javax.swing.JSpinner();
        spinJamKeluar = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cmbJenisLapangan = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        cmbKodeLapangan = new javax.swing.JComboBox<>();
        txtLamaSewa = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtLamaSewa1 = new javax.swing.JTextField();

        DialogPelanggan.setTitle("Cari Pelanggan");
        DialogPelanggan.setAlwaysOnTop(true);
        DialogPelanggan.setBackground(new java.awt.Color(85, 239, 196));
        DialogPelanggan.setMinimumSize(new java.awt.Dimension(700, 460));
        DialogPelanggan.setModal(true);
        DialogPelanggan.setPreferredSize(new java.awt.Dimension(700, 460));
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

        txtKodeBooking.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtKodeBooking.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeBookingKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Kode Booking");

        txtKodePelanggan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtKodePelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodePelangganKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Kode Pelanggan");

        txtNamaPelanggan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNamaPelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaPelangganKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Nama Pelanggan");

        txtNoHandphone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNoHandphone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNoHandphoneKeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("No. Handphone");

        btnCariPelanggan.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnCariPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search_20px.png"))); // NOI18N
        btnCariPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariPelangganActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Alamat");

        txtAlamat.setColumns(20);
        txtAlamat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtAlamat.setLineWrap(true);
        txtAlamat.setRows(5);
        txtAlamat.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtAlamat);

        txtTglBooking.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTglBooking.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTglBookingKeyPressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Tanggal Booking");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Tanggal Pakai");

        jdcTanggalPakai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        spinJamMasuk.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        spinJamMasuk.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), new java.util.Date(), null, java.util.Calendar.HOUR_OF_DAY));

        spinJamKeluar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        spinJamKeluar.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, new java.util.Date(), java.util.Calendar.HOUR));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Jam Masuk");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Jam Keluar");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Jenis Lapangan");

        cmbJenisLapangan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbJenisLapangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbJenisLapanganActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Kode Lapangan");

        cmbKodeLapangan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtLamaSewa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtLamaSewa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLamaSewaKeyPressed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Lama Sewa");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Jam");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Jumlah DP");

        txtLamaSewa1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtLamaSewa1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLamaSewa1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtKodeBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtKodePelanggan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCariPelanggan))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtNamaPelanggan))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtNoHandphone, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(54, 54, 54)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtTglBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spinJamKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(spinJamMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jdcTanggalPakai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbJenisLapangan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbKodeLapangan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtLamaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtLamaSewa1)))
                        .addGap(0, 94, Short.MAX_VALUE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtKodeBooking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtTglBooking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCariPelanggan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtKodePelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addComponent(jdcTanggalPakai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtNoHandphone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(cmbJenisLapangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(cmbKodeLapangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(txtLamaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel20)
                                    .addComponent(txtLamaSewa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(spinJamMasuk)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinJamKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(115, 234, Short.MAX_VALUE))
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
//        if(validasiJam() == true){
//            JOptionPane.showMessageDialog(null, "Jam ini telah diboking");
//        }
    
        validasiTarifLapangan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtLamaSewaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLamaSewaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLamaSewaKeyPressed

    private void btnPilihPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihPelangganActionPerformed
        if(txtKodePelanggan1.getText().isEmpty()){
            DialogPelanggan.dispose();
        }else{
            txtKodePelanggan.setText(txtKodePelanggan1.getText());
            txtNamaPelanggan.setText(txtNamaPelanggan1.getText());
            txtNoHandphone.setText(txtNoHP.getText());
            txtAlamat.setText(txtAlamat1.getText());
            DialogPelanggan.dispose();
        }
        
    }//GEN-LAST:event_btnPilihPelangganActionPerformed

    private void cmbJenisLapanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbJenisLapanganActionPerformed
        comboBoxKodeLapangan();
    }//GEN-LAST:event_cmbJenisLapanganActionPerformed

    private void txtLamaSewa1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLamaSewa1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLamaSewa1KeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog DialogPelanggan;
    private javax.swing.JButton btnCariPelanggan;
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnPilihPelanggan;
    private javax.swing.JButton btnSimpan;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private com.toedter.calendar.JDateChooser jdcTanggalPakai;
    private javax.swing.JSpinner spinJamKeluar;
    private javax.swing.JSpinner spinJamMasuk;
    private javax.swing.JTable tablePelanggan;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextArea txtAlamat1;
    private javax.swing.JTextField txtKodeBooking;
    private javax.swing.JTextField txtKodePelanggan;
    private javax.swing.JTextField txtKodePelanggan1;
    private javax.swing.JTextField txtLamaSewa;
    private javax.swing.JTextField txtLamaSewa1;
    private javax.swing.JTextField txtNamaPelanggan;
    private javax.swing.JTextField txtNamaPelanggan1;
    private javax.swing.JTextField txtNoHP;
    private javax.swing.JTextField txtNoHandphone;
    private javax.swing.JTextField txtTglBooking;
    // End of variables declaration//GEN-END:variables
}
