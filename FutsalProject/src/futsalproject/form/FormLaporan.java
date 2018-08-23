package futsalproject.form;

import futsalproject.FutsalProject;
import futsalproject.controller.JurnalController;
import futsalproject.controller.LaporanController;
import futsalproject.controller.PenyewaanController;
import futsalproject.data.Jurnal;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class FormLaporan extends javax.swing.JInternalFrame {

    PenyewaanController pCont = new PenyewaanController(FutsalProject.emf);
    JurnalController jCont = new JurnalController(FutsalProject.emf);
    LaporanController lapCont = new LaporanController(FutsalProject.emf);
    DefaultTableModel pModel, kasModel;
    String tampil;
    DecimalFormat myFormatter = new DecimalFormat("###,###.##");
    SimpleDateFormat sdfTgl = new SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("id-ID"));
    SimpleDateFormat sdfJam = new SimpleDateFormat("HH:mm");
    
    /**
     * Creates new form FormLaporan
     */
    public FormLaporan(String tampil) {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
        this.setBorder(null);
        this.tampil = tampil;
        tampilLaporan();
    }
    
    private void tampilLaporan(){
        if(tampil.equalsIgnoreCase("Laporan Penyewaan")){
            panelPenyewaan.setVisible(true);
            panelKas.setVisible(false);
            tablePenyewaan();
            formatTglPenyewaan();
            showTablePenyewaan();
            renderTableTglPenyewaan();
            renderTableHargaPenyewaan();
            renderTableJamPenyewaan();
        }else if(tampil.equalsIgnoreCase("Laporan Kas")){
            panelPenyewaan.setVisible(false);
            panelKas.setVisible(true);
            tableKas();
            formatTglKas();
            showTableKas();
            renderTableTglKas();
            renderTableHargaKas();
        }
    }
    
    /*
        Panel Penyewaan
    */
    private void tablePenyewaan(){
        pModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        pModel.addColumn("No. Transaksi");
        pModel.addColumn("Tgl. Sewa");
        pModel.addColumn("Team");
        pModel.addColumn("Kd. Lap");
        pModel.addColumn("Jenis");
        pModel.addColumn("Masuk");
        pModel.addColumn("Keluar");
        pModel.addColumn("Lama");
        pModel.addColumn("Libur");
        pModel.addColumn("Diskon");
        pModel.addColumn("Total");
        pModel.addColumn("User");
        tablePenyewaan.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
    }
    
    private void showTablePenyewaan(){
        pModel.getDataVector().removeAllElements();
        pModel.fireTableDataChanged();
        List<Object[]> listPO = pCont.findAllPenyewaanFormLaporan(jdcTglAwalSewa.getDate(), jdcTglAkhirSewa.getDate());
        for(Object[] po : listPO){
            pModel.addRow(po);
        }
        tablePenyewaan.setModel(pModel);
    }
    
    private void firstDateLastDatePb(){
        Object[] obj=pCont.firstDateLastDate();
        if(obj[0]==null && obj[1]==null){
            jdcTglAwalSewa.setDate(new Date());
            jdcTglAkhirSewa.setDate(new Date());
        }else{
            jdcTglAwalSewa.setDate((Date) obj[0]);
            jdcTglAkhirSewa.setDate((Date) obj[1]);
        }
    }
    
    private void renderTableHargaPenyewaan(){
        TableCellRenderer tbr = new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column){
                if(value instanceof Number){
                    value = myFormatter.format(value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        tablePenyewaan.getColumnModel().getColumn(7).setCellRenderer(tbr);
        tablePenyewaan.getColumnModel().getColumn(8).setCellRenderer(tbr);
        tablePenyewaan.getColumnModel().getColumn(9).setCellRenderer(tbr);
        tablePenyewaan.getColumnModel().getColumn(10).setCellRenderer(tbr);
    }
    
    private void renderTableTglPenyewaan(){
        TableCellRenderer tbr = new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column){
                if(value instanceof Date){
                    value = sdfTgl.format(value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        tablePenyewaan.getColumnModel().getColumn(1).setCellRenderer(tbr);
    }
    
    private void renderTableJamPenyewaan(){
        TableCellRenderer tbr = new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column){
                if(value instanceof Date){
                    value = sdfJam.format(value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        tablePenyewaan.getColumnModel().getColumn(5).setCellRenderer(tbr);
        tablePenyewaan.getColumnModel().getColumn(6).setCellRenderer(tbr);
    }
    
    private void formatTglPenyewaan(){
        jdcTglAwalSewa.setMaxSelectableDate(new Date());
        jdcTglAkhirSewa.setMaxSelectableDate(new Date());
        jdcTglAwalSewa.setLocale(Locale.forLanguageTag("id-ID"));
        jdcTglAkhirSewa.setLocale(Locale.forLanguageTag("id-ID"));
        firstDateLastDatePb();
        jdcTglAwalSewa.setDateFormatString("dd MMMM yyyy");
        jdcTglAkhirSewa.setDateFormatString("dd MMMM yyyy");
    }
    
    /*
        Panel Kas
    */
    
    private void tableKas(){
        kasModel=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        kasModel.addColumn("No.Jurnal");
        kasModel.addColumn("Tgl. Jurnal");
        kasModel.addColumn("No. Transaksi");
        kasModel.addColumn("Total Transaksi");
        tableKas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
    }
    
    private void showTableKas(){
        kasModel.getDataVector().removeAllElements();
        kasModel.fireTableDataChanged();
        List<Object[]> listKas = jCont.findAllJurnalAndTotalSewaByDate(jdcTglAwalKas.getDate(), jdcTglAkhirKas.getDate());
        for(Object[] item : listKas){
            Object[] obj = new Object[4];
            Jurnal j = (Jurnal) item[0];
            obj[0] = j.getNoJurnal();
            obj[1] = (Date) j.getTglJurnal();
            obj[2] =  j.getNoTrans();
            obj[3] = item[1];
            kasModel.addRow(obj);
        }
        tableKas.setModel(kasModel);
    }
    
    private void firstDateLastDateKas(){
        Object[] obj=jCont.firstDateLastDate();
        if(obj[0]==null && obj[1]==null){
            jdcTglAwalKas.setDate(new Date());
            jdcTglAkhirKas.setDate(new Date());
        }else{
            jdcTglAwalKas.setDate((Date) obj[0]);
            jdcTglAkhirKas.setDate((Date) obj[1]);
        }
    }
    
    private void renderTableHargaKas(){
        TableCellRenderer tbr = new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column){
                if(value instanceof Number){
                    value = myFormatter.format(value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        tableKas.getColumnModel().getColumn(3).setCellRenderer(tbr);
    }
    
    private void renderTableTglKas(){
        TableCellRenderer tbr = new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column){
                if(value instanceof Date){
                    value = sdfTgl.format(value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        tableKas.getColumnModel().getColumn(1).setCellRenderer(tbr);
    }
    
    private void formatTglKas(){
        jdcTglAwalKas.setMaxSelectableDate(new Date());
        jdcTglAkhirKas.setMaxSelectableDate(new Date());
        jdcTglAwalKas.setLocale(Locale.forLanguageTag("id-ID"));
        jdcTglAkhirKas.setLocale(Locale.forLanguageTag("id-ID"));
        firstDateLastDateKas();
        jdcTglAwalKas.setDateFormatString("dd MMMM yyyy");
        jdcTglAkhirKas.setDateFormatString("dd MMMM yyyy");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPenyewaan = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePenyewaan = new javax.swing.JTable();
        jdcTglAwalSewa = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jdcTglAkhirSewa = new com.toedter.calendar.JDateChooser();
        btnTampilSewa = new javax.swing.JButton();
        btnCetakSewa = new javax.swing.JButton();
        panelKas = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableKas = new javax.swing.JTable();
        jdcTglAwalKas = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jdcTglAkhirKas = new com.toedter.calendar.JDateChooser();
        btnTampilKas = new javax.swing.JButton();
        btnCetakKas = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.CardLayout());

        panelPenyewaan.setBackground(new java.awt.Color(0, 184, 148));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Form Laporan Penyewaan");

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        tablePenyewaan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Lapangan", "Jenis Lapangan", "Tarif"
            }
        ));
        jScrollPane1.setViewportView(tablePenyewaan);
        if (tablePenyewaan.getColumnModel().getColumnCount() > 0) {
            tablePenyewaan.getColumnModel().getColumn(0).setHeaderValue("Kode Lapangan");
            tablePenyewaan.getColumnModel().getColumn(1).setHeaderValue("Jenis Lapangan");
            tablePenyewaan.getColumnModel().getColumn(2).setHeaderValue("Tarif");
        }

        jdcTglAwalSewa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tanggal Awal");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tanggal Akhir");

        jdcTglAkhirSewa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnTampilSewa.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnTampilSewa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search_20px.png"))); // NOI18N
        btnTampilSewa.setText("Tampil");
        btnTampilSewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTampilSewaActionPerformed(evt);
            }
        });

        btnCetakSewa.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnCetakSewa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-print-20.png"))); // NOI18N
        btnCetakSewa.setText("Cetak Laporan");
        btnCetakSewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakSewaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPenyewaanLayout = new javax.swing.GroupLayout(panelPenyewaan);
        panelPenyewaan.setLayout(panelPenyewaanLayout);
        panelPenyewaanLayout.setHorizontalGroup(
            panelPenyewaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPenyewaanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPenyewaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPenyewaanLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdcTglAwalSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdcTglAkhirSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTampilSewa)
                        .addGap(0, 0, 0)
                        .addComponent(btnCetakSewa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelPenyewaanLayout.setVerticalGroup(
            panelPenyewaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPenyewaanLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPenyewaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jdcTglAwalSewa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jdcTglAkhirSewa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelPenyewaanLayout.createSequentialGroup()
                        .addGroup(panelPenyewaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTampilSewa)
                            .addComponent(btnCetakSewa))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(panelPenyewaan, "card2");

        panelKas.setBackground(new java.awt.Color(0, 184, 148));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Form Laporan Penerimaan Kas");

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));

        tableKas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tableKas);

        jdcTglAwalKas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tanggal Awal");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tanggal Akhir");

        jdcTglAkhirKas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnTampilKas.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnTampilKas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search_20px.png"))); // NOI18N
        btnTampilKas.setText("Tampil");
        btnTampilKas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTampilKasActionPerformed(evt);
            }
        });

        btnCetakKas.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnCetakKas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-print-20.png"))); // NOI18N
        btnCetakKas.setText("Cetak Laporan");
        btnCetakKas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakKasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelKasLayout = new javax.swing.GroupLayout(panelKas);
        panelKas.setLayout(panelKasLayout);
        panelKasLayout.setHorizontalGroup(
            panelKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelKasLayout.createSequentialGroup()
                        .addGroup(panelKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelKasLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jdcTglAwalKas, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jdcTglAkhirKas, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTampilKas)
                                .addGap(0, 0, 0)
                                .addComponent(btnCetakKas)
                                .addGap(0, 129, Short.MAX_VALUE))
                            .addComponent(jScrollPane2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelKasLayout.setVerticalGroup(
            panelKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelKasLayout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelKasLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                        .addGap(467, 467, 467))
                    .addGroup(panelKasLayout.createSequentialGroup()
                        .addGroup(panelKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jdcTglAwalKas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jdcTglAkhirKas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTampilKas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCetakKas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
        );

        getContentPane().add(panelKas, "card3");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTampilSewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTampilSewaActionPerformed
        if(jdcTglAkhirSewa.getCalendar().before(jdcTglAwalSewa.getCalendar())){
            JOptionPane.showMessageDialog(null, "Tanggal Tidak Valid!");
        }else{
            showTablePenyewaan();
        }
    }//GEN-LAST:event_btnTampilSewaActionPerformed

    private void btnCetakSewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakSewaActionPerformed
        int baris = tablePenyewaan.getRowCount();
        if(jdcTglAkhirSewa.getCalendar().before(jdcTglAwalSewa.getCalendar())){
            JOptionPane.showMessageDialog(null, "Tanggal Tidak Valid!");
        }else{
            if(baris == -1){
                JOptionPane.showMessageDialog(null, "Tidak ada data yang tampil!");
            }else{
                lapCont.cetakLaporanPenyewaan(jdcTglAwalSewa.getDate(), jdcTglAkhirSewa.getDate());
            }
        }
    }//GEN-LAST:event_btnCetakSewaActionPerformed

    private void btnTampilKasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTampilKasActionPerformed
        if(jdcTglAkhirKas.getCalendar().before(jdcTglAwalKas.getCalendar())){
            JOptionPane.showMessageDialog(null, "Tanggal Tidak Valid!");
        }else{
            showTableKas();
        }
    }//GEN-LAST:event_btnTampilKasActionPerformed

    private void btnCetakKasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakKasActionPerformed
        int baris = tableKas.getRowCount();
        if(jdcTglAkhirKas.getCalendar().before(jdcTglAwalKas.getCalendar())){
            JOptionPane.showMessageDialog(null, "Tanggal Tidak Valid!");
        }else{
            if(baris == -1){
                JOptionPane.showMessageDialog(null, "Tidak ada data yang tampil!");
            }else{
                lapCont.cetakLaporanKas(jdcTglAwalKas.getDate(), jdcTglAkhirKas.getDate());
            }
        }
    }//GEN-LAST:event_btnCetakKasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCetakKas;
    private javax.swing.JButton btnCetakSewa;
    private javax.swing.JButton btnTampilKas;
    private javax.swing.JButton btnTampilSewa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private com.toedter.calendar.JDateChooser jdcTglAkhirKas;
    private com.toedter.calendar.JDateChooser jdcTglAkhirSewa;
    private com.toedter.calendar.JDateChooser jdcTglAwalKas;
    private com.toedter.calendar.JDateChooser jdcTglAwalSewa;
    private javax.swing.JPanel panelKas;
    private javax.swing.JPanel panelPenyewaan;
    private javax.swing.JTable tableKas;
    private javax.swing.JTable tablePenyewaan;
    // End of variables declaration//GEN-END:variables
}
