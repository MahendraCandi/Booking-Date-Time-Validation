package futsalproject.form;

import futsalproject.FutsalProject;
import futsalproject.controller.LaporanController;
import futsalproject.controller.PenyewaanController;
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
            tablePenyewaan();
            formatTglPenyewaan();
            showTablePenyewaan();
            renderTableTglPenyewaan();
            renderTableHargaPenyewaan();
            renderTableJamPenyewaan();
        }
    }
    
    /*
        Panel Penyewaan
    */
    private void tablePenyewaan(){
        pModel = new DefaultTableModel();
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPenyewaan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPenyewaan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCetakSewa;
    private javax.swing.JButton btnTampilSewa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private com.toedter.calendar.JDateChooser jdcTglAkhirSewa;
    private com.toedter.calendar.JDateChooser jdcTglAwalSewa;
    private javax.swing.JPanel panelPenyewaan;
    private javax.swing.JTable tablePenyewaan;
    // End of variables declaration//GEN-END:variables
}
