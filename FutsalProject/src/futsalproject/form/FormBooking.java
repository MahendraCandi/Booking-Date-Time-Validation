package futsalproject.form;

import futsalproject.FutsalProject;
import futsalproject.controller.BookingController;
import futsalproject.data.Booking;
import futsalproject.data.DataUser;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class FormBooking extends javax.swing.JInternalFrame {

    DataUser userLogin = new DataUser();
    Booking booking = new Booking();
    BookingController bCont = new BookingController(FutsalProject.emf);
    DefaultTableModel model;
    boolean update;
    /**
     * Creates new form FormBooking
     */
    public FormBooking(DataUser user) {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
        this.setBorder(null);
        model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Kode Booking");
        model.addColumn("Tgl. Booking");
        model.addColumn("Kode Pelanggan");
        model.addColumn("Nama Pelanggan");
        model.addColumn("Kode Lapangan");
        model.addColumn("Tgl. Pakai");
        model.addColumn("Jam Masuk");
        model.addColumn("Jam Keluar");
        tableBooking.getTableHeader().setFont(new Font("Tahoma Plain", Font.BOLD, 11));
        showTableNotExistInPenyewaan(null);
        renderTableTgl();
        renderTableJam();
        userLogin = user;
        
    }
    
    private void showTableNotExistInPenyewaan(Date tgl){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        List<Object[]> list = bCont.findAllBookingNotExistsInPenyewaan(tgl);
        for(Object[] obj : list){
            model.addRow(obj);
        }
        tableBooking.setModel(model);
    }
    
    private void showTableExistInPenyewaan(Date tgl){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        List<Object[]> list = bCont.findAllBookingIsExistInPenyewaan(tgl);
        for(Object[] obj : list){
            model.addRow(obj);
        }
        tableBooking.setModel(model);
    }
    
    private void showTableAllBooking(Date tgl){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        List<Object[]> list = bCont.findAllBooking(tgl);
        for(Object[] obj : list){
            model.addRow(obj);
        }
        tableBooking.setModel(model);
    }
    
//    private void cariTable(String cari){
//        List<Object[]> listUser = bCont.searchBooking(cari);
//        if(listUser.size() == 0){
//            JOptionPane.showMessageDialog(null, "Data tidak ditemukan!");
//        }else{
//            if(cari.isEmpty()){
//                showTableNotExistInPenyewaan(null);
//            }else{
//                model.getDataVector().removeAllElements();
//                model.fireTableDataChanged();
//                for(Object[] obj : listUser){
//                model.addRow(obj);
//            }
//            tableBooking.setModel(model);
//            }
//        }
//    }
    
    private void renderTableTgl(){
        TableCellRenderer tbr = new DefaultTableCellRenderer(){
            SimpleDateFormat sdf=new SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("in-ID"));
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column){
                if(value instanceof Date){
                    value = sdf.format(value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        tableBooking.getColumnModel().getColumn(1).setCellRenderer(tbr);
        tableBooking.getColumnModel().getColumn(5).setCellRenderer(tbr);
    }
    
    private void renderTableJam(){
        TableCellRenderer tbr = new DefaultTableCellRenderer(){
            SimpleDateFormat sdf=new SimpleDateFormat("kk:mm", Locale.forLanguageTag("in-ID"));
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column){
                if(value instanceof Date){
                    value = sdf.format(value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        tableBooking.getColumnModel().getColumn(6).setCellRenderer(tbr);
        tableBooking.getColumnModel().getColumn(7).setCellRenderer(tbr);
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
        btnTambah = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDetail = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBooking = new javax.swing.JTable();
        cmbTampil = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jdcTanggal = new com.toedter.calendar.JDateChooser();
        btnCari = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(0, 184, 148));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        btnTambah.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Plus Math_20px.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });
        jPanel2.add(btnTambah);

        btnUpdate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit File_20px.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel2.add(btnUpdate);

        btnDetail.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Check File_20px_5.png"))); // NOI18N
        btnDetail.setText("Lihat Detail");
        btnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailActionPerformed(evt);
            }
        });
        jPanel2.add(btnDetail);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Form Booking");

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Tampilkan:");

        tableBooking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Booking", "Tgl. Bookingr", "Kode Pelanggan", "Nama Pelanggan", "Kode Lapangan", "tgl. Pakai", "Jam Masuk", "Jam Keluar"
            }
        ));
        jScrollPane1.setViewportView(tableBooking);

        cmbTampil.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbTampil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DP belum lunas", "Sudah lunas", "Semua booking" }));
        cmbTampil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTampilActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Tanggal Pakai:");

        jdcTanggal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnCari.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search_20px.png"))); // NOI18N
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTampil, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdcTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdcTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(cmbTampil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCari)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
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

    private void btnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailActionPerformed
        int baris = tableBooking.getSelectedRow();
        if(baris == -1){
            JOptionPane.showMessageDialog(null, "Pilih data yang mau dilihat!");
        }else{
            booking = bCont.findOneBooking(tableBooking.getValueAt(baris, 0).toString());
            update = false;
            FormBookingTambah fbt = new FormBookingTambah(userLogin, booking, update);
            JDesktopPane desktopPane = getDesktopPane();
            desktopPane.add(fbt);
            fbt.setVisible(true);
            this.dispose();
        }
        
    }//GEN-LAST:event_btnDetailActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        booking = null;
        update = false;
        FormBookingTambah fbt = new FormBookingTambah(userLogin, booking, update);
        JDesktopPane desktopPane = getDesktopPane();
        desktopPane.add(fbt);
        fbt.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        String filter = cmbTampil.getSelectedItem().toString();
        if(filter.equalsIgnoreCase("DP belum lunas")){
            showTableNotExistInPenyewaan(jdcTanggal.getDate());
        }else if(filter.equalsIgnoreCase("Sudah lunas")){
            showTableExistInPenyewaan(jdcTanggal.getDate());
        }else if(filter.equalsIgnoreCase("Semua booking")){
            showTableAllBooking(jdcTanggal.getDate());
        }
    }//GEN-LAST:event_btnCariActionPerformed

    private void cmbTampilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTampilActionPerformed
        String filter = cmbTampil.getSelectedItem().toString();
        if(filter.equalsIgnoreCase("DP belum lunas")){
            showTableNotExistInPenyewaan(null);
        }else if(filter.equalsIgnoreCase("Sudah lunas")){
            showTableExistInPenyewaan(null);
        }else if(filter.equalsIgnoreCase("Semua booking")){
            showTableAllBooking(null);
        }
    }//GEN-LAST:event_cmbTampilActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int baris = tableBooking.getSelectedRow();
        if(baris == -1){
            JOptionPane.showMessageDialog(null, "Pilih data yang mau dilihat!");
        }else{
            booking = bCont.findOneBookingIsExistInPenyewaan(tableBooking.getValueAt(baris, 0).toString());
            if(booking == null){
                booking = bCont.findOneBooking(tableBooking.getValueAt(baris, 0).toString());
                update = true;
                FormBookingTambah fbt = new FormBookingTambah(userLogin, booking, update);
                JDesktopPane desktopPane = getDesktopPane();
                desktopPane.add(fbt);
                fbt.setVisible(true);
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Update tidak berlaku, booking ini telah lunas!");
            }
            
        }
    }//GEN-LAST:event_btnUpdateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbTampil;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private com.toedter.calendar.JDateChooser jdcTanggal;
    private javax.swing.JTable tableBooking;
    // End of variables declaration//GEN-END:variables
}
