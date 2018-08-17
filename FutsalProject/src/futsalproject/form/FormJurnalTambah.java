package futsalproject.form;

import futsalproject.FutsalProject;
import futsalproject.controller.DataPerkiraanController;
import futsalproject.controller.JurnalController;
import futsalproject.controller.JurnalDetailController;
import futsalproject.controller.PenyewaanController;
import futsalproject.data.DataPerkiraan;
import futsalproject.data.Jurnal;
import futsalproject.data.JurnalDetail;
import futsalproject.data.Penyewaan;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormJurnalTambah extends javax.swing.JInternalFrame {

    Jurnal jurnal = new Jurnal();
    JurnalController jCont = new JurnalController(FutsalProject.emf);
    JurnalDetail detail = new JurnalDetail();
    JurnalDetailController jdCont = new JurnalDetailController(FutsalProject.emf);
    Penyewaan penyewaan = new Penyewaan();
    PenyewaanController pCont = new PenyewaanController(FutsalProject.emf);
    DataPerkiraan perkiraan = new DataPerkiraan();
    DataPerkiraanController pkCont = new DataPerkiraanController(FutsalProject.emf);
    DefaultTableModel model;
    String kodePerkiraan1, kodePerkiraan2;
    double debit1, debit2, kredit1, kredit2;
    DecimalFormat myFormatter = new DecimalFormat("###,###.##");
    Number number;
    /**
     * Creates new form FormJurnalTambah
     */
    public FormJurnalTambah(Jurnal jurnal) {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
        this.setBorder(null);
        this.jurnal = jurnal;
        model = (DefaultTableModel) tableJurnal.getModel();
        tableJurnal.getTableHeader().setFont(new Font("Tahoma Plain", Font.BOLD, 11));
        tidakAktif();
        validasiJurnal();
        
    }
    
    private void validasiJurnal(){
        if(jurnal != null){
            txtNoJurnal.setText(jurnal.getNoJurnal());
            jdcTanggal.setDate(jurnal.getTglJurnal());
            jdcTanggal.setLocale(Locale.forLanguageTag("id-ID"));
            jdcTanggal.setDateFormatString("dd MMMM yyyy");
            Object[] objNo = {jurnal.getNoTrans()};
            cmbTrans.setModel(new DefaultComboBoxModel(objNo));
            panelJurnal.setVisible(false);
            btnSimpan.setVisible(false);
            btnTambah.setVisible(false);
            btnHapus.setVisible(false);
            List<Object[]> list = jdCont.findDetailToListByNoJurnal(jurnal.getNoJurnal());
            int i = 1;
            for(Object[] item : list){
                detail = (JurnalDetail) item[0];
                Object[] obj = new Object[6];
                obj[0] = i;
                obj[1] = detail.getKdPerkiraan();
                obj[2] = (String) item[1];
                obj[3] = (String) item[2];
                obj[4] = myFormatter.format(detail.getDebet());
                obj[5] = myFormatter.format(detail.getKredit());
                model.addRow(obj);
                i++;
            }
        }else{
            aktif();
            bersih();
        }
    }
    
    private void tidakAktif(){
        txtNoJurnal.setEnabled(false);
        jdcTanggal.setEnabled(false);
        cmbTrans.setEnabled(false);
        txtKdPerkiraan1.setEnabled(false);
        txtKdPerkiraan2.setEnabled(false);
        cmbNmPerkiraan1.setEnabled(false);
        cmbNmPerkiraan2.setEnabled(false);
        txtJenisPerkiraan1.setEnabled(false);
        txtJenisPerkiraan2.setEnabled(false);
        txtDebit1.setEnabled(false);
        txtDebit2.setEnabled(false);
        txtKredit1.setEnabled(false);
        txtKredit2.setEnabled(false);
        btnTambah.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    private void aktif(){
        jdcTanggal.setEnabled(true);
        cmbTrans.setEnabled(true);
        cmbNmPerkiraan1.setEnabled(true);
        cmbNmPerkiraan2.setEnabled(true);
        btnTambah.setEnabled(true);
        btnHapus.setEnabled(true);
        jdcTanggal.setMaxSelectableDate(new Date());
        jdcTanggal.setLocale(Locale.forLanguageTag("id-ID"));
        jdcTanggal.setDateFormatString("dd MMMM yyyy");
    }
    
    private void bersih(){
        txtNoJurnal.setText(jCont.nomorOtomatis());
        jdcTanggal.setDate(null);
        comboBoxTransaksi();
        comboBoxPerkiraan();
        clearJurnal();
        clearList();
        cmbTrans.setSelectedIndex(0);
    }
    
    private void simpan(){
        int row = tableJurnal.getRowCount();
        if(cmbTrans.getSelectedItem().equals("Pilih")){
            JOptionPane.showMessageDialog(null, "Pilih nomor Transaksi!");
        }else if(jdcTanggal.getDate() == null){
            JOptionPane.showMessageDialog(null, "Tanggal Jurnal tidak boleh kosong!");
        }else if(cmbNmPerkiraan1.getSelectedItem().equals("Pilih") || cmbNmPerkiraan2.getSelectedItem().equals("Pilih") ){
            JOptionPane.showMessageDialog(null, "Nama Perkiraan tidak valid!");
        }else if(txtKdPerkiraan1.getText().isEmpty() || txtKdPerkiraan2.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Kode perkiraan tidak lengkap!");
        }else if(txtDebit1.getText().isEmpty() || txtDebit2.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Debit Kredit tidak lengkap!");
        }else if(row == 0){
            JOptionPane.showMessageDialog(null, "Data table masih kosong!");
        }else{
            try {
                jurnal = new Jurnal();
                jurnal.setNoJurnal(txtNoJurnal.getText());
                jurnal.setNoTrans(cmbTrans.getSelectedItem().toString());
                jurnal.setTglJurnal(jdcTanggal.getDate());
                jCont.save(jurnal);

                detail = new JurnalDetail();
                for(int i = 0; i < row; i++){
                    detail.setNoJurnal(txtNoJurnal.getText());
                    detail.setKdPerkiraan(tableJurnal.getValueAt(i, 1).toString());
                    number = myFormatter.parse(tableJurnal.getValueAt(i, 4).toString());
                    
                    detail.setDebet(number.doubleValue());
                    number = myFormatter.parse(tableJurnal.getValueAt(i, 5).toString());
                    detail.setKredit(number.doubleValue());
                    jdCont.save(detail);
                }
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
                btnKembaliActionPerformed(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }
    
    private void comboBoxTransaksi(){
        List<Penyewaan> listPenyewaan = pCont.findAllPenyewaanNotExistInJurnal();
        List<String> listNoTransaksi= new ArrayList<>();
        listNoTransaksi.add("Pilih");
        for(Penyewaan pp : listPenyewaan){
            listNoTransaksi.add(pp.getNoTrans());
        }
        cmbTrans.setModel(new DefaultComboBoxModel(listNoTransaksi.toArray()));
    }
    
    private void comboBoxPerkiraan(){
        List<DataPerkiraan> listPerkiraan = pkCont.findAllDataPerkiraan();
        List<String> listNmPerkiraan = new ArrayList<>();
        listNmPerkiraan.add("Pilih");
        for (DataPerkiraan dp  : listPerkiraan) {
            listNmPerkiraan.add(dp.getNmPerkiraan());
        }
        cmbNmPerkiraan1.setModel(new DefaultComboBoxModel(listNmPerkiraan.toArray()));
        cmbNmPerkiraan2.setModel(new DefaultComboBoxModel(listNmPerkiraan.toArray()));
        
    }
    
    private void findKodePerkiraan1(){
        perkiraan = new DataPerkiraan();
        if(cmbNmPerkiraan1.getSelectedItem().equals("Pilih")){
            kodePerkiraan1 = "";
            txtKdPerkiraan1.setText("");
            txtJenisPerkiraan1.setText("");
            txtDebit1.setText("");
            txtKredit1.setText("");
        }else{
            kodePerkiraan1 = cmbNmPerkiraan1.getSelectedItem().toString();
            perkiraan = pkCont.findOneDataPerkiraanByNama(kodePerkiraan1);
            txtKdPerkiraan1.setText(perkiraan.getKdPerkiraan());
            txtJenisPerkiraan1.setText(perkiraan.getJenisPerkiraan());
        }
    }
    
    private void findKodePerkiraan2(){
        perkiraan = new DataPerkiraan();
        if(cmbNmPerkiraan2.getSelectedItem().equals("Pilih")){
            kodePerkiraan2 = "";
            txtKdPerkiraan2.setText("");
            txtJenisPerkiraan2.setText("");
            txtDebit2.setText("");
            txtKredit2.setText("");
        }else{
            kodePerkiraan2 = cmbNmPerkiraan2.getSelectedItem().toString();
            perkiraan = pkCont.findOneDataPerkiraanByNama(kodePerkiraan2);
            txtKdPerkiraan2.setText(perkiraan.getKdPerkiraan());
            txtJenisPerkiraan2.setText(perkiraan.getJenisPerkiraan());
        }
    }
    
    private void findDebitKredit1(){
        String kode = cmbTrans.getSelectedItem().toString();
        penyewaan = pCont.findOnePenyewaan(kode);
        debit1 = penyewaan.getTotalSewa();
        kredit1 = 0;
        txtDebit1.setText(myFormatter.format(debit1));
        txtKredit1.setText(myFormatter.format(kredit1));
    }
    
    private void findDebitKredit2(){
        String kode = cmbTrans.getSelectedItem().toString();
        penyewaan = pCont.findOnePenyewaan(kode);
        debit2 = 0;
        kredit2 = penyewaan.getTotalSewa();
        txtDebit2.setText(myFormatter.format(debit2));
        txtKredit2.setText(myFormatter.format(kredit2));
    }
    
    private void clearJurnal(){
        txtKdPerkiraan1.setText("");
        txtKdPerkiraan2.setText("");
        txtDebit1.setText("");
        txtDebit2.setText("");
        txtKredit1.setText("");
        txtKredit2.setText("");
        cmbNmPerkiraan1.setSelectedIndex(0);
        cmbNmPerkiraan2.setSelectedIndex(0);
    }
    
    private void addData1ToTable(){
        Object[] obj = new Object[6];
        obj[1] = txtKdPerkiraan1.getText();
        obj[2] = cmbNmPerkiraan1.getSelectedItem();
        obj[3] = txtJenisPerkiraan1.getText();
        obj[4] = txtDebit1.getText();
        obj[5] = txtKredit1.getText();
        model.addRow(obj);
        noTable();
    }
    
    private void addData2ToTable(){
        Object[] obj = new Object[6];
        obj[1] = txtKdPerkiraan2.getText();
        obj[2] = cmbNmPerkiraan2.getSelectedItem();
        obj[3] = txtJenisPerkiraan2.getText();
        obj[4] = txtDebit2.getText();
        obj[5] = txtKredit2.getText();
        model.addRow(obj);
        noTable();
    }
    
    private void noTable(){
        int row=model.getRowCount(); 
        for(int a=0; a<row ;a++){
            String no=String.valueOf(a+1);
            tableJurnal.setValueAt(no, a, 0); 
        }
    }
    
    private void clearList(){
        int row=tableJurnal.getRowCount();
        while(row>0){
            row--;
            model.removeRow(row);
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
        jPanel2 = new javax.swing.JPanel();
        btnSimpan = new javax.swing.JButton();
        btnKembali = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        panelJurnal = new javax.swing.JPanel();
        txtDebit1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtKredit1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtKdPerkiraan2 = new javax.swing.JTextField();
        cmbNmPerkiraan2 = new javax.swing.JComboBox<>();
        txtDebit2 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtKredit2 = new javax.swing.JTextField();
        txtKdPerkiraan1 = new javax.swing.JTextField();
        cmbNmPerkiraan1 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtJenisPerkiraan1 = new javax.swing.JTextField();
        txtJenisPerkiraan2 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        txtNoJurnal = new javax.swing.JTextField();
        cmbTrans = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jdcTanggal = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableJurnal = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(0, 184, 148));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        btnSimpan.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Check File_20px_5.png"))); // NOI18N
        btnSimpan.setText("Simpan Jurnal");
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
        jLabel5.setText("Form Tambah Jurnal");

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        panelJurnal.setBackground(new java.awt.Color(0, 184, 148));
        panelJurnal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Jurnal", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        txtDebit1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDebit1.setForeground(new java.awt.Color(51, 51, 51));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Debit");

        txtKredit1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtKredit1.setForeground(new java.awt.Color(51, 51, 51));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Kredit");

        txtKdPerkiraan2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtKdPerkiraan2.setForeground(new java.awt.Color(51, 51, 51));
        txtKdPerkiraan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKdPerkiraan2ActionPerformed(evt);
            }
        });

        cmbNmPerkiraan2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbNmPerkiraan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNmPerkiraan2ActionPerformed(evt);
            }
        });

        txtDebit2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDebit2.setForeground(new java.awt.Color(51, 51, 51));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Kode Perkiraan");

        txtKredit2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtKredit2.setForeground(new java.awt.Color(51, 51, 51));

        txtKdPerkiraan1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtKdPerkiraan1.setForeground(new java.awt.Color(51, 51, 51));

        cmbNmPerkiraan1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbNmPerkiraan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNmPerkiraan1ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Nama Perkiraan");

        txtJenisPerkiraan1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtJenisPerkiraan1.setForeground(new java.awt.Color(51, 51, 51));

        txtJenisPerkiraan2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtJenisPerkiraan2.setForeground(new java.awt.Color(51, 51, 51));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Jenis Perkiraan");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        btnTambah.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Plus Math_20px.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });
        jPanel5.add(btnTambah);

        btnHapus.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Trash_20px.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        jPanel5.add(btnHapus);

        javax.swing.GroupLayout panelJurnalLayout = new javax.swing.GroupLayout(panelJurnal);
        panelJurnal.setLayout(panelJurnalLayout);
        panelJurnalLayout.setHorizontalGroup(
            panelJurnalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJurnalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelJurnalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelJurnalLayout.createSequentialGroup()
                        .addGroup(panelJurnalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtKdPerkiraan1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtKdPerkiraan2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelJurnalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelJurnalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cmbNmPerkiraan1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                            .addComponent(cmbNmPerkiraan2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelJurnalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtJenisPerkiraan2, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                            .addComponent(txtJenisPerkiraan1, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelJurnalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDebit2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelJurnalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtDebit1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelJurnalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKredit2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelJurnalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)
                                .addComponent(txtKredit1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelJurnalLayout.setVerticalGroup(
            panelJurnalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJurnalLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panelJurnalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelJurnalLayout.createSequentialGroup()
                        .addComponent(txtJenisPerkiraan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtJenisPerkiraan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelJurnalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelJurnalLayout.createSequentialGroup()
                            .addGroup(panelJurnalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtDebit1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelJurnalLayout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addGap(32, 32, 32)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtDebit2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelJurnalLayout.createSequentialGroup()
                            .addGroup(panelJurnalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(panelJurnalLayout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtKredit1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelJurnalLayout.createSequentialGroup()
                                    .addGroup(panelJurnalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel12)
                                        .addComponent(jLabel13))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panelJurnalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtKdPerkiraan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmbNmPerkiraan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelJurnalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtKdPerkiraan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbNmPerkiraan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtKredit2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 184, 148));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "No. Jurnal", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        txtNoJurnal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNoJurnal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNoJurnalKeyPressed(evt);
            }
        });

        cmbTrans.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbTrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTransActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("No. Transaksi");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("No. Jurnal");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Tanggal Jurnal");

        jdcTanggal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(44, 44, 44)
                        .addComponent(txtNoJurnal, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(cmbTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jdcTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(127, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNoJurnal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jdcTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cmbTrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableJurnal.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tableJurnal.setForeground(new java.awt.Color(51, 51, 51));
        tableJurnal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Kode Perkiraan", "Nama Perkiraan", "Jenis Perkiraan", "Debit", "Kredit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableJurnal);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE)
                            .addComponent(jSeparator1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelJurnal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(51, 51, 51)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelJurnal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addContainerGap())
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

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        simpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        FormJurnal fj = new FormJurnal();
        JDesktopPane desktopPane = getDesktopPane();
        desktopPane.add(fj);
        fj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void txtNoJurnalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoJurnalKeyPressed

    }//GEN-LAST:event_txtNoJurnalKeyPressed

    private void txtKdPerkiraan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKdPerkiraan2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKdPerkiraan2ActionPerformed

    private void cmbNmPerkiraan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNmPerkiraan2ActionPerformed
        findKodePerkiraan2();
        if(!cmbTrans.getSelectedItem().equals("Pilih")){
            findDebitKredit2();
        }
    }//GEN-LAST:event_cmbNmPerkiraan2ActionPerformed

    private void cmbNmPerkiraan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNmPerkiraan1ActionPerformed
        findKodePerkiraan1();
        if(!cmbTrans.getSelectedItem().equals("Pilih")){
            findDebitKredit1();
        }

    }//GEN-LAST:event_cmbNmPerkiraan1ActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        double debit = 0 , kredit = 1;
        if(!txtDebit1.getText().isEmpty() || !txtDebit2.getText().isEmpty()){
            try {
                number = myFormatter.parse(txtDebit1.getText());
                debit = number.doubleValue();
                number = myFormatter.parse(txtKredit2.getText());
                kredit = number.doubleValue();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(txtKdPerkiraan1.getText().isEmpty() || txtKdPerkiraan2.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Kode perkiraan kosong!");
            }else if(txtDebit1.getText().isEmpty() || txtDebit2.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Nominal debit kredit kosong! Pilih nomor transaksi lebih dahulu!");
            }else if(debit != kredit){
                JOptionPane.showMessageDialog(null, "Nominal debit kredit tidak balance!");
            }else{
                addData1ToTable();
                addData2ToTable();
            }
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed

        clearList();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void cmbTransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTransActionPerformed
        if(tableJurnal.getRowCount() > 0){
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Nomor transaksi berubah!");
            clearJurnal();
        }
    }//GEN-LAST:event_cmbTransActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbNmPerkiraan1;
    private javax.swing.JComboBox<String> cmbNmPerkiraan2;
    private javax.swing.JComboBox<String> cmbTrans;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private com.toedter.calendar.JDateChooser jdcTanggal;
    private javax.swing.JPanel panelJurnal;
    private javax.swing.JTable tableJurnal;
    private javax.swing.JTextField txtDebit1;
    private javax.swing.JTextField txtDebit2;
    private javax.swing.JTextField txtJenisPerkiraan1;
    private javax.swing.JTextField txtJenisPerkiraan2;
    private javax.swing.JTextField txtKdPerkiraan1;
    private javax.swing.JTextField txtKdPerkiraan2;
    private javax.swing.JTextField txtKredit1;
    private javax.swing.JTextField txtKredit2;
    private javax.swing.JTextField txtNoJurnal;
    // End of variables declaration//GEN-END:variables
}
