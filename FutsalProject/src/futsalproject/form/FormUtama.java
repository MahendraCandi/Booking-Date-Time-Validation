package futsalproject.form;

import futsalproject.data.DataUser;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.plaf.basic.BasicMenuBarUI;

public class FormUtama extends javax.swing.JFrame {

    private String nama;
    private String hakAkses;
    private String kodeUser;
    private DataUser userLogin = new DataUser();
    
    int xx;
    int xy;
    /**
     * Creates new form FormUtama
     */
    public FormUtama(DataUser user) {
        initComponents();
        setLocationRelativeTo(null);
        jMenuBar1.setUI(new BasicMenuBarUI(){
            public void paint(Graphics g, JComponent c){
                g.setColor(new Color(255, 255, 255));
                g.fillRect(0, 0, c.getWidth(), c.getHeight());
            }
        });
        txtKodeUser.setText("Kode User : " + user.getKdUser());
        txtNamaUser.setText("Nama : " + user.getNmUser());
        txtHakAkses.setText("Akses : " + user.getHakAkses());
        userLogin = user;
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
        desktopPane = new javax.swing.JDesktopPane();
        txtNamaUser = new javax.swing.JLabel();
        txtKodeUser = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtHakAkses = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuMaster = new javax.swing.JMenu();
        MenuPelanggan = new javax.swing.JMenuItem();
        MenuLapangan = new javax.swing.JMenuItem();
        MenuAkun = new javax.swing.JMenuItem();
        MenuUser = new javax.swing.JMenuItem();
        MenuTransaksi = new javax.swing.JMenu();
        MenuBooking = new javax.swing.JMenuItem();
        MenuSewa = new javax.swing.JMenuItem();
        MenuJurnal = new javax.swing.JMenuItem();
        MenuLaporan = new javax.swing.JMenu();
        MenuLapPenyewaan = new javax.swing.JMenuItem();
        MenuLapKas = new javax.swing.JMenuItem();
        MenuLogout = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 184, 148));
        setMinimumSize(new java.awt.Dimension(900, 600));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(900, 600));
        getContentPane().setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(0, 184, 148));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });

        desktopPane.setBackground(new java.awt.Color(0, 184, 148));
        desktopPane.setLayout(new java.awt.CardLayout());

        txtNamaUser.setForeground(new java.awt.Color(255, 255, 255));
        txtNamaUser.setText("txtNamaUser");

        txtKodeUser.setForeground(new java.awt.Color(255, 255, 255));
        txtKodeUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Circled User Male_17px_2.png"))); // NOI18N
        txtKodeUser.setText("txtKodeUser");

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        txtHakAkses.setForeground(new java.awt.Color(255, 255, 255));
        txtHakAkses.setText("txtHakAkses");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtKodeUser)
                        .addGap(18, 18, 18)
                        .addComponent(txtNamaUser)
                        .addGap(18, 18, 18)
                        .addComponent(txtHakAkses)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKodeUser)
                    .addComponent(txtNamaUser)
                    .addComponent(txtHakAkses))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, "card2");

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setBorderPainted(false);

        menuMaster.setText("Master");

        MenuPelanggan.setBackground(new java.awt.Color(255, 255, 255));
        MenuPelanggan.setText("Pelanggan");
        MenuPelanggan.setOpaque(true);
        MenuPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuPelangganActionPerformed(evt);
            }
        });
        menuMaster.add(MenuPelanggan);

        MenuLapangan.setBackground(new java.awt.Color(255, 255, 255));
        MenuLapangan.setText("Lapangan");
        MenuLapangan.setOpaque(true);
        MenuLapangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuLapanganActionPerformed(evt);
            }
        });
        menuMaster.add(MenuLapangan);

        MenuAkun.setBackground(new java.awt.Color(255, 255, 255));
        MenuAkun.setText("Perkiraan");
        MenuAkun.setOpaque(true);
        MenuAkun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAkunActionPerformed(evt);
            }
        });
        menuMaster.add(MenuAkun);

        MenuUser.setBackground(new java.awt.Color(255, 255, 255));
        MenuUser.setText("User");
        MenuUser.setOpaque(true);
        MenuUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuUserActionPerformed(evt);
            }
        });
        menuMaster.add(MenuUser);

        jMenuBar1.add(menuMaster);

        MenuTransaksi.setText("Transaksi");

        MenuBooking.setBackground(new java.awt.Color(255, 255, 255));
        MenuBooking.setText("Booking");
        MenuBooking.setOpaque(true);
        MenuBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuBookingActionPerformed(evt);
            }
        });
        MenuTransaksi.add(MenuBooking);

        MenuSewa.setBackground(new java.awt.Color(255, 255, 255));
        MenuSewa.setText("Penyewaan");
        MenuSewa.setOpaque(true);
        MenuSewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuSewaActionPerformed(evt);
            }
        });
        MenuTransaksi.add(MenuSewa);

        MenuJurnal.setBackground(new java.awt.Color(255, 255, 255));
        MenuJurnal.setText("Jurnal");
        MenuJurnal.setOpaque(true);
        MenuJurnal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuJurnalActionPerformed(evt);
            }
        });
        MenuTransaksi.add(MenuJurnal);

        jMenuBar1.add(MenuTransaksi);

        MenuLaporan.setText("Laporan");

        MenuLapPenyewaan.setBackground(new java.awt.Color(255, 255, 255));
        MenuLapPenyewaan.setText("Penyewaan");
        MenuLapPenyewaan.setOpaque(true);
        MenuLapPenyewaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuLapPenyewaanActionPerformed(evt);
            }
        });
        MenuLaporan.add(MenuLapPenyewaan);

        MenuLapKas.setBackground(new java.awt.Color(255, 255, 255));
        MenuLapKas.setText("Penerimaan Kas");
        MenuLapKas.setOpaque(true);
        MenuLapKas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuLapKasActionPerformed(evt);
            }
        });
        MenuLaporan.add(MenuLapKas);

        jMenuBar1.add(MenuLaporan);

        MenuLogout.setText("Logout");
        MenuLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuLogoutMouseClicked(evt);
            }
        });
        jMenuBar1.add(MenuLogout);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x -xx, y -xy);
    }//GEN-LAST:event_jPanel1MouseDragged

    private void MenuUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuUserActionPerformed
        FormUser fu = new FormUser();
        showForm(fu);
    }//GEN-LAST:event_MenuUserActionPerformed

    private void MenuLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuLogoutMouseClicked
        FormLogin FL=new FormLogin();
        this.dispose();
        FL.setVisible(true);
        FL.setAutoRequestFocus(true);
    }//GEN-LAST:event_MenuLogoutMouseClicked

    private void MenuLapanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuLapanganActionPerformed
        FormLapangan fl = new FormLapangan();
        showForm(fl);
    }//GEN-LAST:event_MenuLapanganActionPerformed

    private void MenuAkunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAkunActionPerformed
        FormPerkiraan fa = new FormPerkiraan();
        showForm(fa);
    }//GEN-LAST:event_MenuAkunActionPerformed

    private void MenuPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuPelangganActionPerformed
        FormPelanggan fp = new FormPelanggan();
        showForm(fp);
    }//GEN-LAST:event_MenuPelangganActionPerformed

    private void MenuBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuBookingActionPerformed
        FormBooking fb = new FormBooking(userLogin);
        showForm(fb);
    }//GEN-LAST:event_MenuBookingActionPerformed

    private void MenuSewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuSewaActionPerformed
        FormSewaDaftar fs = new FormSewaDaftar();
        showForm(fs);
    }//GEN-LAST:event_MenuSewaActionPerformed

    private void MenuJurnalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuJurnalActionPerformed
        FormJurnal fj = new FormJurnal();
        showForm(fj);
    }//GEN-LAST:event_MenuJurnalActionPerformed

    private void MenuLapPenyewaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuLapPenyewaanActionPerformed
        FormLaporan fl = new FormLaporan("Laporan Penyewaan");
        showForm(fl);
    }//GEN-LAST:event_MenuLapPenyewaanActionPerformed

    private void MenuLapKasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuLapKasActionPerformed
        FormLaporan fl = new FormLaporan("Laporan Kas");
        showForm(fl);
    }//GEN-LAST:event_MenuLapKasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new FormUtama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MenuAkun;
    private javax.swing.JMenuItem MenuBooking;
    private javax.swing.JMenuItem MenuJurnal;
    private javax.swing.JMenuItem MenuLapKas;
    private javax.swing.JMenuItem MenuLapPenyewaan;
    private javax.swing.JMenuItem MenuLapangan;
    private javax.swing.JMenu MenuLaporan;
    private javax.swing.JMenu MenuLogout;
    private javax.swing.JMenuItem MenuPelanggan;
    private javax.swing.JMenuItem MenuSewa;
    private javax.swing.JMenu MenuTransaksi;
    private javax.swing.JMenuItem MenuUser;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenu menuMaster;
    private javax.swing.JLabel txtHakAkses;
    private javax.swing.JLabel txtKodeUser;
    private javax.swing.JLabel txtNamaUser;
    // End of variables declaration//GEN-END:variables

    private void showForm(Object obj){
        JInternalFrame jf=null;
        jf=(JInternalFrame) obj;
        desktopPane.add(jf);
        jf.setVisible(true);
        try {
            jf.setMaximizable(true);
            jf.setSelected(true);
        } catch (Exception e) {
        }
    }

    public javax.swing.JMenuItem getMenuAkun() {
        return MenuAkun;
    }

    public void setMenuAkun(javax.swing.JMenuItem MenuAkun) {
        this.MenuAkun = MenuAkun;
    }

    public javax.swing.JMenuItem getMenuJurnal() {
        return MenuJurnal;
    }

    public JMenuItem getMenuPelanggan() {
        return MenuPelanggan;
    }

    public void setMenuPelanggan(JMenuItem MenuPelanggan) {
        this.MenuPelanggan = MenuPelanggan;
    }

    public void setMenuJurnal(javax.swing.JMenuItem MenuJurnal) {
        this.MenuJurnal = MenuJurnal;
    }

    public javax.swing.JMenuItem getMenuLapangan() {
        return MenuLapangan;
    }

    public void setMenuLapangan(javax.swing.JMenuItem MenuLapangan) {
        this.MenuLapangan = MenuLapangan;
    }

    public javax.swing.JMenu getMenuTransaksi() {
        return MenuTransaksi;
    }

    public void setMenuTransaksi(javax.swing.JMenu MenuTransaksi) {
        this.MenuTransaksi = MenuTransaksi;
    }

    public javax.swing.JMenuItem getMenuUser() {
        return MenuUser;
    }

    public void setMenuUser(javax.swing.JMenuItem MenuUser) {
        this.MenuUser = MenuUser;
    }
}
