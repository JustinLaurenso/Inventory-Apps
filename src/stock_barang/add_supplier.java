/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock_barang;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author cent91
 */
public class add_supplier extends javax.swing.JFrame {
    public static Connection conn;
    public static Statement st;
    String primary;
    String status;
    /**
     * Creates new form add_user
     */
    public add_supplier(String primary,String status) {
        this.primary = primary;
        this.status = status;
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setBackground(new Color (0,0,0,0));
        jPanel1.setBackground(new Color (0,0,0,0));
        txusername.setBackground(new Color (0,0,0,0));
        txaddress.setBackground(new Color (0,0,0,0));
        txemail.setBackground(new Color (0,0,0,0));
        koneksi();
        get_status();
    }

    private add_supplier() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
    void get_status(){
        if(this.status.equals("Edit")){
            save.setText("EDIT");
            Header.setText("EDIT SUPPLIER");
            txsupplierid.setEnabled(false);
            get_supp_data();
        }
        else if(this.status.equals("Save")){
            save.setText("SAVE");
        }
    }
    
    void validation(){
        if(txsupplierid.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Customer ID must be filled !", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(txusername.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Customer name must be filled !", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(txemail.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Email must be filled !", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(txaddress.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Address must be filled !", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(txphonenumber.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Phone Number must be filled !", "Error", JOptionPane.ERROR_MESSAGE);
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
    
    void get_supp_data(){
       try{
            String sql = "select * from supplier where supp_id='"+primary+"'";
            ResultSet rs = st.executeQuery(sql);
            
            if(rs.next()){
                txsupplierid.setText(rs.getString("supp_id"));
                txusername.setText(rs.getString("supp_name"));
                txemail.setText(rs.getString("supp_email"));
                txaddress.setText(rs.getString("supp_address"));
                txphonenumber.setText(rs.getString("supp_nohp"));
            }
        }
        catch(SQLException e){
            System.out.println("Koneksi Gagal" + e.toString());
        }
    }

    void save(){
         try{
             String sql = "insert into supplier values('"+txsupplierid.getText()+"','"+txusername.getText()+"','"+txemail.getText()+"','"+txaddress.getText()+"','"+txphonenumber.getText()+"')";
            st.executeUpdate(sql);
            Session_Update.update_status("Supplier");
            JOptionPane.showMessageDialog(this,"Data added succesully","info",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException e){
            System.out.println("Koneksi gagal"+ e.toString());
        }
    }
    
    void update(){
        try{
            String sql = "update supplier set supp_name='"+txusername.getText()+"',supp_email='"+txemail.getText()+"',supp_address='"+txaddress.getText()+"',supp_nohp='"+txphonenumber.getText()+"' where supp_id='"+primary+"'";
            st.executeUpdate(sql);
            
            Session_Update.update_status("Supplier");
            JOptionPane.showMessageDialog(this,"Data updated succesfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException e){
            System.out.println("Koneksi gagal"+ e.toString());
        }
    }
    
    void cust_id_otomatis(){
        try{
             String sql = "select right(supp_id,3) from supplier";
             ResultSet rs = st.executeQuery(sql);
             if(rs.next()){
                 rs.last();
                 String supp_id = ""+(Integer.parseInt(rs.getString(1))+1);
                
                while(supp_id.length() < 3){
                    supp_id = "0" +supp_id;
                    txsupplierid.setText("SP-"+supp_id);
                }
                 
             }
             else{
                 txsupplierid.setText("SP-001");
             }
        }
        catch(SQLException e){
            System.out.println("Koneksi Gagal " + e.toString());
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
        txphonenumber = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        username1 = new javax.swing.JLabel();
        txsupplierid = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        username = new javax.swing.JLabel();
        txusername = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        txaddress = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        txemail = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        Header = new javax.swing.JLabel();
        save = new javax.swing.JLabel();
        lblsave = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jLabel13.setText("jLabel13");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
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

        txphonenumber.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txphonenumber.setForeground(new java.awt.Color(255, 255, 255));
        txphonenumber.setBorder(null);
        txphonenumber.setCaretColor(new java.awt.Color(255, 255, 255));
        txphonenumber.setOpaque(false);
        txphonenumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txphonenumberActionPerformed(evt);
            }
        });
        txphonenumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txphonenumberKeyPressed(evt);
            }
        });
        jPanel1.add(txphonenumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 340, 20));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 390, 340, -1));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Phone Number");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, -1, -1));

        username1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        username1.setForeground(new java.awt.Color(255, 255, 255));
        username1.setText("Supplier ID");
        jPanel1.add(username1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, -1, -1));

        txsupplierid.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txsupplierid.setForeground(new java.awt.Color(255, 255, 255));
        txsupplierid.setBorder(null);
        txsupplierid.setCaretColor(new java.awt.Color(255, 255, 255));
        txsupplierid.setOpaque(false);
        txsupplierid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txsupplieridActionPerformed(evt);
            }
        });
        txsupplierid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txsupplieridKeyReleased(evt);
            }
        });
        jPanel1.add(txsupplierid, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 250, 20));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 340, -1));

        username.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        username.setForeground(new java.awt.Color(255, 255, 255));
        username.setText("Supplier Name");
        jPanel1.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, -1, -1));

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
        jPanel1.add(txusername, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 250, 20));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 210, 340, -1));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Address");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 290, -1, -1));

        txaddress.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txaddress.setForeground(new java.awt.Color(255, 255, 255));
        txaddress.setBorder(null);
        txaddress.setCaretColor(new java.awt.Color(255, 255, 255));
        txaddress.setOpaque(false);
        txaddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txaddressActionPerformed(evt);
            }
        });
        jPanel1.add(txaddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, 340, 20));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 330, 340, -1));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Email");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 40, -1));

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
        jPanel1.add(txemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, 340, 20));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, 340, -1));

        Header.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        Header.setForeground(new java.awt.Color(255, 255, 255));
        Header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Header.setText("ADD SUPPLIER");
        Header.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel8MousePressed(evt);
            }
        });
        jPanel1.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 220, 40));

        save.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        save.setForeground(new java.awt.Color(255, 255, 255));
        save.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        save.setText("SAVE");
        save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                saveMousePressed(evt);
            }
        });
        jPanel1.add(save, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 430, 130, 30));

        lblsave.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblsave.setForeground(new java.awt.Color(255, 255, 255));
        lblsave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/btn-login.png"))); // NOI18N
        lblsave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblsaveMousePressed(evt);
            }
        });
        jPanel1.add(lblsave, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 430, 240, 30));

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

    private void txaddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txaddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txaddressActionPerformed

    private void txusernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txusernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txusernameActionPerformed

    private void exitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMousePressed
        dispose();
        Session_Update.update_status("Supplier");
    }//GEN-LAST:event_exitMousePressed


    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MousePressed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_jLabel8MousePressed

    private void txusernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txusernameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txusernameKeyReleased

    private void show_passMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_show_pass1MousePressed
        
    }//GEN-LAST:event_show_pass1MousePressed

    private void lblsaveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblsaveMousePressed
        // TODO add your handling code here:
       validation();
    }//GEN-LAST:event_lblsaveMousePressed

    private void saveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveMousePressed
        // TODO add your handling code here:
        validation();
    }//GEN-LAST:event_saveMousePressed

    private void txsupplieridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txsupplieridActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txsupplieridActionPerformed

    private void txsupplieridKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txsupplieridKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txsupplieridKeyReleased

    private void txphonenumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txphonenumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txphonenumberActionPerformed

    private void txphonenumberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txphonenumberKeyPressed
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(Character.isLetter(c)){
            txphonenumber.setEditable(false);
        }
        else{
            txphonenumber.setEditable(true);
        }
    }//GEN-LAST:event_txphonenumberKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        txsupplierid.setEditable(false);
        txusername.grabFocus();
        if(this.status.equals("Edit")){
            txsupplierid.setText(this.primary);
        }
        else{
            cust_id_otomatis();
        }
    }//GEN-LAST:event_formWindowActivated
        

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
            java.util.logging.Logger.getLogger(add_supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(add_supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(add_supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(add_supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new add_supplier().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Header;
    private javax.swing.JLabel exit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel lblsave;
    private javax.swing.JLabel save;
    private javax.swing.JTextField txaddress;
    private javax.swing.JTextField txemail;
    private javax.swing.JTextField txphonenumber;
    private javax.swing.JTextField txsupplierid;
    private javax.swing.JTextField txusername;
    private javax.swing.JLabel username;
    private javax.swing.JLabel username1;
    // End of variables declaration//GEN-END:variables

    private void formWindowActivated(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
