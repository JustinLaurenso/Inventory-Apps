/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock_barang;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import java.sql.*;

/**
 *
 * @author cent91
 */
public class add_product extends javax.swing.JFrame {
            String timezone = "useLegacyDatetimeCode=false&serverTimezone=UTC";
            String user="root";
            String pwd="";
            String url="jdbc:mysql://localhost/uas?"+timezone;
            Boolean isi=true;
            String primary;
            String status;
    /**
     * Creates new form add_user
     */
    public add_product(String primary, String status) {
         this.primary = primary;
        this.status = status;
        initComponents();
        setLocationRelativeTo(null);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setBackground(new Color (0,0,0,0));
        jPanel1.setBackground(new Color (0,0,0,0));
        txproductid.setBackground(new Color (0,0,0,0));
        txsku.setBackground(new Color (0,0,0,0));
        txprice.setBackground(new Color (0,0,0,0));
        txstock.setBackground(new Color (0,0,0,0));
        //txkategori.setBackground(new Color (0,0,0,0));
        get_status();
    }

    private add_product() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    void get_status(){
        if(this.status.equals("Edit")){
            save.setText("EDIT");
            Header.setText("EDIT PRODUCT");
            txproductid.setEnabled(false);
            get_prod_data();
        }
        else if(this.status.equals("Save")){
            save.setText("SAVE");
        }
    }
    
    void get_prod_data(){
       try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String sql = "select * from product where product_id='"+primary+"'";
            ResultSet rs = st.executeQuery(sql);
            
            if(rs.next()){
                txproductid.setText(rs.getString("product_id"));
                txsku.setText(rs.getString("name_product"));
                txprice.setText(rs.getString("price"));
                txstock.setText(rs.getString("stock"));
                cbkategori.setSelectedItem(rs.getString("category"));
                cbsupp.setSelectedItem(rs.getString("supp_id"));
            }
        }
        catch(SQLException e){
            System.out.println("Koneksi Gagal" + e.toString());
        }
    }
    
    void aktif(){
        txproductid.setEnabled(false);
        txsku.setEnabled (true);
        txprice.setEnabled(true);
        txstock.setEnabled(true);
        cbkategori.setEnabled(true);
        cbkategori.setEnabled(true);
        cbsupp.setEnabled(true);
    }
    void isiKode(){
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String sql = "Select * from category";
            ResultSet rs=st.executeQuery(sql);
            while (rs.next()){
                cbkategori.addItem(rs.getString("kategori"));
            }
            
            Statement st1 = conn.createStatement();
            String sql1 = "Select * from supplier";
            ResultSet rs1=st1.executeQuery(sql1);
            while (rs1.next()){
                cbsupp.addItem(rs1.getString("supp_id"));
            }
        }
        catch(SQLException e){
            System.out.println("Koneksi gagal isiKode"+e.toString());
        }
    }
    void otomatis(){
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String sql = "Select right(product_id,3)+1 from product";
            ResultSet rs=st.executeQuery(sql);
            if (rs.next()){
                rs.last();
                String kode =rs.getString(1);
                String[] kde = kode.split("[.]");
                kode = kde[0];
                  while (kode.length()<3){
                    kode = "0"+kode;
                    txproductid.setText("BR-"+kode);
                }
            }else{
                txproductid.setText("BR-001");
            }
        }catch(SQLException e){
                System.out.println("Koneksi gagal cari"+e.toString());
        }
    }
    void simpan(){
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement();
            
            String sql = "insert into product values('"+txproductid.getText()+"','"+txsku.getText()+"','"+txprice.getText()+"','"+txstock.getText()+"','"+cbkategori.getSelectedItem()+"','"+cbsupp.getSelectedItem()+"')";
        
            st.executeUpdate(sql);
            
            JOptionPane.showMessageDialog(this,"Data berhasil disimpan","info",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException e){
            System.out.println("Koneksi gagal simpan"+e.toString());
        }
        formWindowActivated(null);
    }
    void update(){
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement();
            
            String sql = "update product set name_product='"+txsku.getText()+"',price='"+txprice.getText()+"',stock='"+txstock.getText()+"',category='"+cbkategori.getSelectedItem()+"',supp_id='"+cbsupp.getSelectedItem()+"' where product_id='"+primary+"'";
            
            st.executeUpdate(sql);
            
            JOptionPane.showMessageDialog(this,"Data berhasil diupdate","info",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException e){
           System.out.println("Koneksi gagal update"+e.toString());
        }
        formWindowActivated(null);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        exit = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cbkategori = new javax.swing.JComboBox<>();
        name = new javax.swing.JLabel();
        txproductid = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        sku = new javax.swing.JLabel();
        txsku = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        price = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txprice = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        stock = new javax.swing.JLabel();
        txstock = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        kategori = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        save = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        kategori1 = new javax.swing.JLabel();
        cbsupp = new javax.swing.JComboBox<>();
        Header = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

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

        jPanel1.add(cbkategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, 340, -1));

        name.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        name.setForeground(new java.awt.Color(255, 255, 255));
        name.setText("Product ID");
        jPanel1.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, -1, -1));

        txproductid.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txproductid.setForeground(new java.awt.Color(255, 255, 255));
        txproductid.setBorder(null);
        txproductid.setOpaque(false);
        txproductid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txproductidActionPerformed(evt);
            }
        });
        jPanel1.add(txproductid, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 340, 20));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 340, 10));

        sku.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        sku.setForeground(new java.awt.Color(255, 255, 255));
        sku.setText("Name Product");
        jPanel1.add(sku, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, -1, -1));

        txsku.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txsku.setForeground(new java.awt.Color(255, 255, 255));
        txsku.setBorder(null);
        txsku.setOpaque(false);
        txsku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txskuActionPerformed(evt);
            }
        });
        txsku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txskuKeyPressed(evt);
            }
        });
        jPanel1.add(txsku, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, 340, 20));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, 340, -1));

        price.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        price.setForeground(new java.awt.Color(255, 255, 255));
        price.setText("Price");
        jPanel1.add(price, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, -1, -1));

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("IDR.");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 40, 20));

        txprice.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txprice.setForeground(new java.awt.Color(255, 255, 255));
        txprice.setBorder(null);
        txprice.setOpaque(false);
        txprice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txpriceActionPerformed(evt);
            }
        });
        txprice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txpriceKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txpriceKeyTyped(evt);
            }
        });
        jPanel1.add(txprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 310, 20));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 340, -1));

        stock.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        stock.setForeground(new java.awt.Color(255, 255, 255));
        stock.setText("Stock");
        jPanel1.add(stock, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, -1, -1));

        txstock.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txstock.setForeground(new java.awt.Color(255, 255, 255));
        txstock.setBorder(null);
        txstock.setOpaque(false);
        txstock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txstockActionPerformed(evt);
            }
        });
        txstock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txstockKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txstockKeyTyped(evt);
            }
        });
        jPanel1.add(txstock, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 340, 20));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, 340, -1));

        kategori.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        kategori.setForeground(new java.awt.Color(255, 255, 255));
        kategori.setText("Category");
        jPanel1.add(kategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, -1, -1));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 370, 340, -1));

        save.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        save.setForeground(new java.awt.Color(255, 255, 255));
        save.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        save.setText("SAVE");
        save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                saveMousePressed(evt);
            }
        });
        jPanel1.add(save, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 450, 130, 30));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/btn-login.png"))); // NOI18N
        jLabel8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel8KeyPressed(evt);
            }
        });
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 450, 240, 30));

        kategori1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        kategori1.setForeground(new java.awt.Color(255, 255, 255));
        kategori1.setText("Supplier");
        jPanel1.add(kategori1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, -1, -1));

        cbsupp.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        cbsupp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbsupp.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.add(cbsupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, 340, -1));

        Header.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        Header.setForeground(new java.awt.Color(255, 255, 255));
        Header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Header.setText("ADD PRODUCT");
        jPanel1.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 220, 40));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/add_product-Bg.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 510));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 510));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMousePressed
        dispose();
    }//GEN-LAST:event_exitMousePressed

    private void txstockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txstockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txstockActionPerformed

    private void txpriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txpriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txpriceActionPerformed

    private void txproductidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txproductidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txproductidActionPerformed

    private void txskuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txskuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txskuActionPerformed

    private void txpriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txpriceKeyPressed
        if (evt.getKeyChar()== KeyEvent.VK_ENTER){
            txstock.requestFocusInWindow();
        }
    }//GEN-LAST:event_txpriceKeyPressed

    private void txstockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txstockKeyTyped
        char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txstockKeyTyped

    private void txpriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txpriceKeyTyped
        char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txpriceKeyTyped

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        //bersih();
        aktif();
        txproductid.setEditable(false);
        txsku.grabFocus();
        if(this.status.equals("Edit")){
            txproductid.setText(this.primary);
        }
        else{
            otomatis();
        }
        isiKode();
    }//GEN-LAST:event_formWindowActivated

    private void jLabel8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel8KeyPressed
        // TODO add your handling code here:
       // if(isi==true){
         //       simpan();
           // }else{
             //   update();
            //}
    }//GEN-LAST:event_jLabel8KeyPressed

    private void saveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveMousePressed
        // TODO add your handling code here:
        if(save.getText().equals("SAVE")){
                simpan();
            }else{
                update();
            }
    }//GEN-LAST:event_saveMousePressed

    private void txskuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txskuKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ENTER){
            txprice.requestFocusInWindow();
        }
    }//GEN-LAST:event_txskuKeyPressed

    private void txstockKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txstockKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyChar()== KeyEvent.VK_ENTER){
            cbkategori.requestFocusInWindow();
        }
    }//GEN-LAST:event_txstockKeyPressed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(add_product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(add_product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(add_product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(add_product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new add_product().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Header;
    private javax.swing.JComboBox<String> cbkategori;
    private javax.swing.JComboBox<String> cbsupp;
    private javax.swing.JLabel exit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel kategori;
    private javax.swing.JLabel kategori1;
    private javax.swing.JLabel name;
    private javax.swing.JLabel price;
    private javax.swing.JLabel save;
    private javax.swing.JLabel sku;
    private javax.swing.JLabel stock;
    private javax.swing.JTextField txprice;
    private javax.swing.JTextField txproductid;
    private javax.swing.JTextField txsku;
    private javax.swing.JTextField txstock;
    // End of variables declaration//GEN-END:variables
}
