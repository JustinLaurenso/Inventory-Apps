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
import java.awt.event.KeyEvent;
import javax.swing.JTable;
/**
 *
 * @author cent91
 */
public class cat_mene extends javax.swing.JFrame {
             String timezone = "useLegacyDatetimeCode=false&serverTimezone=UTC";
            String user="root";
            String pwd="";
            String url="jdbc:mysql://localhost/uas?"+timezone;
            Boolean isi=true;
            
    /**
     * Creates new form add_user
     */
    public cat_mene() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setBackground(new Color (0,0,0,0));
        jPanel1.setBackground(new Color (0,0,0,0));
        txcat.setBackground(new Color (0,0,0,0));
        btnadd.setVisible(true);
        btndelete.setVisible(true);
        checkuserok.setVisible(false);
        checkuserno.setVisible(false);
        //koneksi();
       // get_status();
    }
    void aktif(){
        txcat.setEnabled (true);
    }
    void nonaktif(){
        btnadd.setEnabled(true);
        btndelete.setEnabled(true);
    }
    void tambah(){
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement();
            
            String sql = "insert into category (kategori) values('"+txcat.getText()+"')";
        
            st.executeUpdate(sql);
            
            JOptionPane.showMessageDialog(this,"Data berhasil ditambah","info",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException e){
            System.out.println("Koneksi gagal tambah"+e.toString());
        }
        formWindowActivated(null);
    }
    void hapus(){
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String sql = "delete from category where kategori='"+txcat.getText()+"'or no='"+txcat.getText()+"'";
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(this,"Data berhasil dihapus","info",JOptionPane.INFORMATION_MESSAGE);          
        }
        catch(SQLException e){
            System.out.println("Koneksi gagal hapus"+e.toString());
        }
        formWindowActivated(null);
    }
    
    void update(){
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement();
            
            String sql = "update barang set kategori ='"+txcat.getText()+"'";
            
            st.executeUpdate(sql);
            
            JOptionPane.showMessageDialog(this,"Data berhasil diupdate","info",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException e){
           System.out.println("Koneksi gagal update"+e.toString());
        }
        formWindowActivated(null);
    }
    private Object[][]getData(){
        int nomor=1;
        Object[][]data1=null;
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = st.executeQuery("Select * from category");
            rs.last();
            int rowCount=rs.getRow();
            rs.beforeFirst();
            data1=new Object[rowCount][4];
            int no=-1;
            while(rs.next()){
                no=no+1;
                data1[no][0]=rs.getString("no");
                data1[no][1]=rs.getString("kategori");
            }
        } catch(SQLException e){
            System.out.println("Koneksi gagal tampil"+e.toString());
        }
        return data1;          
    }
    void tampil(){
        String[] columnNames = {"No","Kategori"};
        JTable table=new JTable (getData(), columnNames);
        table.setEnabled(false);
        jScrollPane1.setViewportView(table);
    }
      //  private Object[][]getData(){
       // Object[][]data1=null;
        //try{
           // Connection conn = DriverManager.getConnection(url,user,pwd);
            // Statement st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
           // ResultSet rs = st.executeQuery("Select * from product");
           // rs.last();
           // int rowCount=rs.getRow();
           // rs.beforeFirst();
           // data1=new Object[rowCount][4];
          //  int no=-1;
        //    while(rs.next()){
               // no=no+1;
                //data1[no][4]=rs.getString("category");
              // }
      //  } catch(SQLException e){
          //  System.out.println("Koneksi gagal tampil"+e.toString());
        //}
        //return data1;          
    //}
        
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
        username1 = new javax.swing.JLabel();
        checkuserok = new javax.swing.JLabel();
        checkuserno = new javax.swing.JLabel();
        txcat = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        Header = new javax.swing.JLabel();
        btnadd = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btndelete = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
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

        username1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        username1.setForeground(new java.awt.Color(255, 255, 255));
        username1.setText("Category");
        jPanel1.add(username1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, -1, -1));

        checkuserok.setForeground(new java.awt.Color(255, 255, 255));
        checkuserok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_checked_25px.png"))); // NOI18N
        jPanel1.add(checkuserok, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, 30, 30));

        checkuserno.setForeground(new java.awt.Color(255, 255, 255));
        checkuserno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_xbox_x_25px.png"))); // NOI18N
        jPanel1.add(checkuserno, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 30, 30));

        txcat.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txcat.setForeground(new java.awt.Color(255, 255, 255));
        txcat.setBorder(null);
        txcat.setCaretColor(new java.awt.Color(255, 255, 255));
        txcat.setOpaque(false);
        txcat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txcatActionPerformed(evt);
            }
        });
        txcat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txcatKeyReleased(evt);
            }
        });
        jPanel1.add(txcat, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 270, 20));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 340, 10));

        Header.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        Header.setForeground(new java.awt.Color(255, 255, 255));
        Header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Header.setText("CATEGORY MANAGEMENT");
        Header.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel8MousePressed(evt);
            }
        });
        jPanel1.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 340, 40));

        btnadd.setBackground(new java.awt.Color(51, 204, 0));
        btnadd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnaddMouseClicked(evt);
            }
        });
        btnadd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnaddKeyPressed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_plus_15px.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ADD");

        javax.swing.GroupLayout btnaddLayout = new javax.swing.GroupLayout(btnadd);
        btnadd.setLayout(btnaddLayout);
        btnaddLayout.setHorizontalGroup(
            btnaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnaddLayout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnaddLayout.setVerticalGroup(
            btnaddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.add(btnadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 70, 30));

        btndelete.setBackground(new java.awt.Color(255, 0, 0));
        btndelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndeleteMouseClicked(evt);
            }
        });
        btndelete.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btndeleteKeyPressed(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_plus_15px.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("DELETE");

        javax.swing.GroupLayout btndeleteLayout = new javax.swing.GroupLayout(btndelete);
        btndelete.setLayout(btndeleteLayout);
        btndeleteLayout.setHorizontalGroup(
            btndeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btndeleteLayout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
        );
        btndeleteLayout.setVerticalGroup(
            btndeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.add(btndelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 170, -1, 30));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Title 1"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 370, 180));

        jLabel1.setBackground(new java.awt.Color(121, 163, 172));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/add_user_bg.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 510));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 510));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMousePressed
        dispose();
        
    }//GEN-LAST:event_exitMousePressed


    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MousePressed
        // TODO add your handling code here:
//        save();
    }//GEN-LAST:event_jLabel8MousePressed

    private void show_passMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_show_pass1MousePressed
        
    }//GEN-LAST:event_show_pass1MousePressed

    private void txcatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txcatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txcatActionPerformed

    private void txcatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txcatKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txcatKeyReleased

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        aktif();
        tampil();
    }//GEN-LAST:event_formWindowActivated

    private void btndeleteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btndeleteKeyPressed
        // TODO add your handling code here:
       hapus();
    }//GEN-LAST:event_btndeleteKeyPressed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        //String primary = jTable1.getValueAt(jTable1.getSelectedRow(),0).toString();
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnaddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnaddKeyPressed
        // TODO add your handling code here:
        tambah();
    }//GEN-LAST:event_btnaddKeyPressed

    private void btnaddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnaddMouseClicked
        // TODO add your handling code here:
        tambah();
    }//GEN-LAST:event_btnaddMouseClicked

    private void btndeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndeleteMouseClicked
        // TODO add your handling code here:
        hapus();
    }//GEN-LAST:event_btndeleteMouseClicked
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
            java.util.logging.Logger.getLogger(cat_mene.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(cat_mene.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(cat_mene.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(cat_mene.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new cat_mene().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Header;
    private javax.swing.JPanel btnadd;
    private javax.swing.JPanel btndelete;
    private javax.swing.JLabel checkuserno;
    private javax.swing.JLabel checkuserok;
    private javax.swing.JLabel exit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txcat;
    private javax.swing.JLabel username1;
    // End of variables declaration//GEN-END:variables

    private void formWindowActivated(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
