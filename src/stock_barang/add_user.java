/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock_barang;
import java.awt.Color;
import static java.awt.Color.green;
import static java.awt.Color.red;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.security.MessageDigest;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author cent91
 */
public class add_user extends javax.swing.JFrame {
    public static Connection conn;
    public static Statement st;
    String primary;
    String status;
    /**
     * Creates new form add_user
     * @param primary
     * @param status
     */
    public add_user(String primary, String status) {
        this.primary = primary;
        this.status = status;
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setBackground(new Color (0,0,0,0));
        jPanel1.setBackground(new Color (0,0,0,0));
        txusername.setBackground(new Color (0,0,0,0));
        txpass.setBackground(new Color (0,0,0,0));
        txrepass.setBackground(new Color (0,0,0,0));
        txname.setBackground(new Color (0,0,0,0));
        txemail.setBackground(new Color (0,0,0,0));
        show_repass.setVisible(false);
        show_pass.setVisible(false);
        checkuserok.setVisible(false);
        checkuserno.setVisible(false);
        koneksi();
        get_status();
        //show_repass.setVisible(false);
    }

    private add_user() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    void get_status(){
        if(this.status.equals("Edit")){
            save.setText("EDIT");
            title.setText("EDIT USER");
            txusername.setEnabled(false);
            checkuserok.setVisible(false);
            checkuserno.setVisible(false);
            get_data();
        }
        else if(this.status.equals("Save")){
            save.setText("SAVE");
        }
    }
    
    void get_data(){
        try{
            String sql = "select * from user where username='"+primary+"'";
            ResultSet rs = st.executeQuery(sql);
            
            if(rs.next()){
                txusername.setText(rs.getString("username"));
                txname.setText(rs.getString("name"));
                txemail.setText(rs.getString("email"));
                cbposition.setSelectedItem(rs.getString("position"));
            }
        }
        catch(SQLException e){
            System.out.println("Koneksi Gagal" + e.toString());
        }
    }
    
    void koneksi(){
         try {
            // timezone 
            String timezone = "useLegacyDatetimeCode=false&serverTimezone=UTC";
            
            //untuk mengkoneksikan dengan database penjualan
            String url ="jdbc:mysql://localhost/uas?" + timezone;
            String user="root";
            String pass="";
            
            //membuat koneksi dengan database
            conn = DriverManager.getConnection(url,user,pass);
            st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            System.out.println("koneksi berhasil;");
        } catch (Exception e) {
            System.err.println("koneksi gagal" + e.getMessage());
        }
    }
    
    public static String md5(String msg) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(msg.getBytes());
            byte byteData[] = md.digest();
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return  sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }
    
    void save(){
         try{
             String password = txpass.getText();
             String encryptPass = md5(password);
             String sql = "insert into user values('"+txusername.getText()+"','"+encryptPass+"','"+txname.getText()+"','"+txemail.getText()+"','"+cbposition.getSelectedItem()+"')";
            st.executeUpdate(sql);
            Session_Update.update_status("User");
            JOptionPane.showMessageDialog(this,"Data added succesfully","info",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException e){
            System.out.println("Koneksi gagal"+ e.toString());
        }
    }
    
    void update(){
        String password = txpass.getText();
        String encryptPass = md5(password);
        try{
            String sql = "update user set password='"+encryptPass+"',name='"+txname.getText()+"',email='"+txemail.getText()+"', position='"+cbposition.getSelectedItem()+"' where username='"+primary+"'";
            st.executeUpdate(sql);
            
            Session_Update.update_status("User");
            JOptionPane.showMessageDialog(this,"Data updated succesfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException e){
            System.out.println("Koneksi gagal"+ e.toString());
        }
    }
    
    void validation(){
        if(txusername.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Username must be filled !", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(!txpass.getText().equals(txrepass.getText())){
            JOptionPane.showMessageDialog(this,"Passwords don't match", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(txname.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Name must be filled !", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(txemail.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Email must be filled !", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else{
            if(save.getText().equals("SAVE")){
                save();
            }
            else if(save.getText().equals("EDIT")){
                update();
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

        jLabel13 = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        username = new javax.swing.JLabel();
        checkuserok = new javax.swing.JLabel();
        checkuserno = new javax.swing.JLabel();
        txusername = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        pass = new javax.swing.JLabel();
        hide_pass = new javax.swing.JLabel();
        show_pass = new javax.swing.JLabel();
        txpass = new javax.swing.JPasswordField();
        jSeparator2 = new javax.swing.JSeparator();
        repass = new javax.swing.JLabel();
        hide_repass = new javax.swing.JLabel();
        show_repass = new javax.swing.JLabel();
        txrepass = new javax.swing.JPasswordField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        txname = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        txemail = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        cbposition = new javax.swing.JComboBox<>();
        title = new javax.swing.JLabel();
        save = new javax.swing.JLabel();
        lblsave = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jLabel13.setText("jLabel13");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        exit.setFont(new java.awt.Font("Roboto Black", 1, 11)); // NOI18N
        exit.setForeground(new java.awt.Color(255, 51, 51));
        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_back_arrow_25px.png"))); // NOI18N
        exit.setToolTipText("");
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                exitMousePressed(evt);
            }
        });
        getContentPane().add(exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, 30, 40));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        username.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        username.setForeground(new java.awt.Color(255, 255, 255));
        username.setText("Username");
        jPanel1.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, -1));

        checkuserok.setForeground(new java.awt.Color(255, 255, 255));
        checkuserok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_checked_25px.png"))); // NOI18N
        jPanel1.add(checkuserok, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, 30, 30));

        checkuserno.setForeground(new java.awt.Color(255, 255, 255));
        checkuserno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_xbox_x_25px.png"))); // NOI18N
        jPanel1.add(checkuserno, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, 30, 30));

        txusername.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txusername.setForeground(new java.awt.Color(255, 255, 255));
        txusername.setBorder(null);
        txusername.setCaretColor(new java.awt.Color(255, 255, 255));
        txusername.setOpaque(false);
        txusername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txusernameActionPerformed(evt);
            }
        });
        txusername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txusernameKeyReleased(evt);
            }
        });
        jPanel1.add(txusername, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 250, 20));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 340, -1));

        pass.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        pass.setForeground(new java.awt.Color(255, 255, 255));
        pass.setText("Password");
        jPanel1.add(pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, -1, -1));

        hide_pass.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        hide_pass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_hide_15px.png"))); // NOI18N
        hide_pass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                hide_passMousePressed(evt);
            }
        });
        jPanel1.add(hide_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, 20, 20));

        show_pass.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        show_pass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_show_password_15px.png"))); // NOI18N
        show_pass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                show_passMousePressed(evt);
            }
        });
        jPanel1.add(show_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, 20, 20));

        txpass.setForeground(new java.awt.Color(255, 255, 255));
        txpass.setBorder(null);
        txpass.setCaretColor(new java.awt.Color(255, 255, 255));
        txpass.setOpaque(false);
        jPanel1.add(txpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 310, 20));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 340, -1));

        repass.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        repass.setForeground(new java.awt.Color(255, 255, 255));
        repass.setText("Re-Password");
        jPanel1.add(repass, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, -1, -1));

        hide_repass.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        hide_repass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_hide_15px.png"))); // NOI18N
        hide_repass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                hide_repassMousePressed(evt);
            }
        });
        jPanel1.add(hide_repass, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, 20, 20));

        show_repass.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        show_repass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_show_password_15px.png"))); // NOI18N
        show_repass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                show_repassMousePressed(evt);
            }
        });
        jPanel1.add(show_repass, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, 20, 20));

        txrepass.setForeground(new java.awt.Color(255, 255, 255));
        txrepass.setBorder(null);
        txrepass.setCaretColor(new java.awt.Color(255, 255, 255));
        txrepass.setOpaque(false);
        jPanel1.add(txrepass, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 310, 20));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 340, -1));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Name");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, -1, -1));

        txname.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txname.setForeground(new java.awt.Color(255, 255, 255));
        txname.setBorder(null);
        txname.setCaretColor(new java.awt.Color(255, 255, 255));
        txname.setOpaque(false);
        txname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txnameActionPerformed(evt);
            }
        });
        jPanel1.add(txname, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 340, 20));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, 340, -1));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Email");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, 40, -1));

        txemail.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txemail.setForeground(new java.awt.Color(255, 255, 255));
        txemail.setBorder(null);
        txemail.setCaretColor(new java.awt.Color(255, 255, 255));
        txemail.setOpaque(false);
        txemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txemailActionPerformed(evt);
            }
        });
        jPanel1.add(txemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, 340, 20));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 370, 340, -1));

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Position");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, -1, -1));

        cbposition.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        cbposition.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "User" }));
        cbposition.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbposition.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.add(cbposition, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, 340, -1));

        title.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("ADD USER");
        title.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel8MousePressed(evt);
            }
        });
        jPanel1.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 150, 40));

        save.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        save.setForeground(new java.awt.Color(255, 255, 255));
        save.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        save.setText("SAVE");
        save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                saveMousePressed(evt);
            }
        });
        jPanel1.add(save, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 450, 130, 30));

        lblsave.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblsave.setForeground(new java.awt.Color(255, 255, 255));
        lblsave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/btn-login.png"))); // NOI18N
        lblsave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblsaveMousePressed(evt);
            }
        });
        jPanel1.add(lblsave, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 450, 240, 30));

        jLabel1.setBackground(new java.awt.Color(121, 163, 172));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/add_user_bg.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 510));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 510));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txemailActionPerformed

    private void txnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txnameActionPerformed

    private void txusernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txusernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txusernameActionPerformed

    private void exitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMousePressed
        this.dispose();
        Session_Update.update_status("User");
    }//GEN-LAST:event_exitMousePressed


    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MousePressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jLabel8MousePressed

    private void txusernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txusernameKeyReleased
        // TODO add your handling code here:
        String username = txusername.getText();
        
        try{
             String sql = "select * from user where username='"+username.trim()+"'";
             ResultSet rs = st.executeQuery(sql);
             
             if(rs.next()){
                 checkuserno.setVisible(true);
                 checkuserok.setVisible(false);
             }
             else{
                 checkuserok.setVisible(true);
                 checkuserno.setVisible(false);
             }
        }catch(SQLException e){
                System.out.println("Koneksi Gagal " + e.toString());
        }
    }//GEN-LAST:event_txusernameKeyReleased

    private void hide_repassMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hide_passMousePressed
        txrepass.setEchoChar((char)0);
        show_repass.setVisible(true);
        hide_repass.setVisible(false);
    }//GEN-LAST:event_hide_passMousePressed

    private void show_repassMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_show_passMousePressed
        txrepass.setEchoChar('\u25cf');
        hide_repass.setVisible(true);
        show_repass.setVisible(false);
    }//GEN-LAST:event_show_passMousePressed

    private void show_passMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_show_pass1MousePressed
        txpass.setEchoChar('\u25cf');
        hide_pass.setVisible(true);
        show_pass.setVisible(false);
    }//GEN-LAST:event_show_pass1MousePressed

    private void lblsaveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblsaveMousePressed
        // TODO add your handling code here:
        validation();
        
    }//GEN-LAST:event_lblsaveMousePressed

    private void saveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveMousePressed
        validation();
    }//GEN-LAST:event_saveMousePressed
    private void hide_passMousePressed(java.awt.event.MouseEvent evt) {                                        
        txpass.setEchoChar((char)0);
        show_pass.setVisible(true);
        hide_pass.setVisible(false);
    }             

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
            java.util.logging.Logger.getLogger(add_user.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(add_user.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(add_user.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(add_user.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new add_user().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbposition;
    private javax.swing.JLabel checkuserno;
    private javax.swing.JLabel checkuserok;
    private javax.swing.JLabel exit;
    private javax.swing.JLabel hide_pass;
    private javax.swing.JLabel hide_repass;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblsave;
    private javax.swing.JLabel pass;
    private javax.swing.JLabel repass;
    private javax.swing.JLabel save;
    private javax.swing.JLabel show_pass;
    private javax.swing.JLabel show_repass;
    private javax.swing.JLabel title;
    private javax.swing.JTextField txemail;
    private javax.swing.JTextField txname;
    private javax.swing.JPasswordField txpass;
    private javax.swing.JPasswordField txrepass;
    private javax.swing.JTextField txusername;
    private javax.swing.JLabel username;
    // End of variables declaration//GEN-END:variables

    private void formWindowActivated(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
