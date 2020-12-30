/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock_barang;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author cent91
 */
public class report extends javax.swing.JFrame {
    public static Connection conn;
    public static Statement st;
    /**
     * Creates new form add_transaction
     */
    public report() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setBackground(new Color (0,0,0,0));
        jPanel1.setBackground(new Color (0,0,0,0));
        koneksi();
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
    
   void filter_receiving(){
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       String tanggal1 = sdf.format(tgl1.getDate());
       String tanggal2 = sdf.format(tgl2.getDate());
       String syarat = " ";
       
       if(!tanggal1.equals("") && !tanggal2.equals("")){
           syarat = syarat+"trans_in.date between '"+tanggal1+"' and '"+tanggal2+"'";
       }
       else if(!tanggal1.equals("") && tanggal2.equals("")){
           syarat = syarat+"trans_in.date between '"+tanggal1+"' and '"+tanggal1+"'";
       }
       else if(tanggal1.equals("") && tanggal2.equals("")){
           syarat = syarat+"trans_in.date between '"+tanggal2+"' and '"+tanggal2+"'";
       }
       
       try{
           String sql = "select trans_in.notrans,trans_in.date,supplier.supp_name from trans_in inner join supplier on trans_in.supp_id = supplier.supp_id where" +syarat;
           ResultSet rs = st.executeQuery(sql);
           
           while(rs.next()){
                String notrans = rs.getString("notrans");
                String tgl = rs.getString("date");
                String supp_name = rs.getString("supp_name");
                
                //System.out.println(notrans);                
                DefaultTableModel tabeltrans = new DefaultTableModel();
                tabeltrans.addColumn("Date");
                tabeltrans.addColumn("No Transaction");
                tabeltrans.addColumn("Product Name");
                tabeltrans.addColumn("Supplier Name");
                tabeltrans.addColumn("Qty");
                tabeltrans.addColumn("Price");
                tabeltrans.addColumn("Total");
                tbtrans.setModel(tabeltrans);
                //setting_table_width_column(tbuser, 480, 5, 35, 55, 15);
                tbtrans.getTableHeader().setReorderingAllowed(false);
                tbtrans.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                
                Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String sql2 = "select trans_in_detail.*,product.* from trans_in_detail inner join product on trans_in_detail.product_id = product.product_id where trans_in_detail.notrans='"+notrans+"'";
                ResultSet rs1 = state.executeQuery(sql2);
                while(rs1.next()){
                    String product_name = rs1.getString("name_product");
                    String qty = rs1.getString("qty");
                    String price = rs1.getString("price");
                    String total = rs1.getString("total");
                    
                     tabeltrans.addRow(new Object[]{
                        tgl,
                        notrans,
                        product_name,
                        supp_name,
                        qty,
                        price,
                        total,
                     });
                }
           }
       }
       catch(SQLException e){
           System.out.println("Koneksi Gagal" + e.toString());
       }
   }
    void filter_issuing(){
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       String tanggal1 = sdf.format(tgl1.getDate());
       String tanggal2 = sdf.format(tgl2.getDate());
       String syarat = " ";
       
       if(!tanggal1.equals("") && !tanggal2.equals("")){
           syarat = syarat+"trans_out.date between '"+tanggal1+"' and '"+tanggal2+"'";
       }
       else if(!tanggal1.equals("") && tanggal2.equals("")){
           syarat = syarat+"trans_out.date between '"+tanggal1+"' and '"+tanggal1+"'";
       }
       else if(tanggal1.equals("") && tanggal2.equals("")){
           syarat = syarat+"trans_out.date between '"+tanggal2+"' and '"+tanggal2+"'";
       }
       
       try{
           String sql = "select trans_out.notrans,trans_out.date,customer.cust_name from trans_out inner join customer on trans_out.cust_id = customer.cust_id where" +syarat;
           ResultSet rs = st.executeQuery(sql);
           
           while(rs.next()){
                String notrans = rs.getString("notrans");
                String tgl = rs.getString("date");
                String cust_name = rs.getString("cust_name");
                
                //System.out.println(notrans);                
                DefaultTableModel tabeltrans = new DefaultTableModel();
                tabeltrans.addColumn("Date");
                tabeltrans.addColumn("No Transaction");
                tabeltrans.addColumn("Product Name");
                tabeltrans.addColumn("Customer Name");
                tabeltrans.addColumn("Qty");
                tabeltrans.addColumn("Price");
                tabeltrans.addColumn("Total");
                tbtrans.setModel(tabeltrans);
                //setting_table_width_column(tbuser, 480, 5, 35, 55, 15);
                tbtrans.getTableHeader().setReorderingAllowed(false);
                tbtrans.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                
                Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String sql2 = "select trans_out_detail.*,product.* from trans_out_detail inner join product on trans_out_detail.product_id = product.product_id where trans_out_detail.notrans='"+notrans+"'";
                ResultSet rs1 = state.executeQuery(sql2);
                while(rs1.next()){
                    String product_name = rs1.getString("name_product");
                    String qty = rs1.getString("qty");
                    String price = rs1.getString("price");
                    String total = rs1.getString("total");
                    
                     tabeltrans.addRow(new Object[]{
                        tgl,
                        notrans,
                        product_name,
                        cust_name,
                        qty,
                        price,
                        total,
                     });
                }
           }
       }
       catch(SQLException e){
           System.out.println("Koneksi Gagal" + e.toString());
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
        tgl1 = new com.toedter.calendar.JDateChooser();
        tgl2 = new com.toedter.calendar.JDateChooser();
        btnfilter = new javax.swing.JButton();
        Header = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        cbreport = new javax.swing.JComboBox<>();
        qty3 = new javax.swing.JLabel();
        qty4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbtrans = new javax.swing.JTable();
        BG = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tgl1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tgl1PropertyChange(evt);
            }
        });
        jPanel1.add(tgl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 130, 120, -1));

        tgl2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tgl2PropertyChange(evt);
            }
        });
        jPanel1.add(tgl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 130, 130, -1));

        btnfilter.setBackground(new java.awt.Color(255, 0, 51));
        btnfilter.setForeground(new java.awt.Color(255, 255, 255));
        btnfilter.setText("Filter");
        btnfilter.setBorderPainted(false);
        btnfilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfilterActionPerformed(evt);
            }
        });
        jPanel1.add(btnfilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 410, 100, 30));

        Header.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        Header.setForeground(new java.awt.Color(255, 255, 255));
        Header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Header.setText("REPORT");
        jPanel1.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 510, 40));

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("X");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel7MousePressed(evt);
            }
        });
        jLabel7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel7KeyPressed(evt);
            }
        });
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 30, 30, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 780, 10));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 780, 10));

        cbreport.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        cbreport.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Choose --", "Receiving (IN) Report", "Issuing (OUT) Report" }));
        cbreport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbreportActionPerformed(evt);
            }
        });
        jPanel1.add(cbreport, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, 160, -1));

        qty3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        qty3.setForeground(new java.awt.Color(255, 255, 255));
        qty3.setText("Date          :");
        jPanel1.add(qty3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, 70, -1));

        qty4.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        qty4.setForeground(new java.awt.Color(255, 255, 255));
        qty4.setText("-");
        jPanel1.add(qty4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 130, 10, -1));

        tbtrans.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        tbtrans.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbtrans.setFillsViewportHeight(true);
        tbtrans.setShowGrid(false);
        tbtrans.setShowHorizontalLines(true);
        tbtrans.setShowVerticalLines(true);
        jScrollPane1.setViewportView(tbtrans);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 760, 190));

        BG.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/add_trx_bg.png"))); // NOI18N
        jPanel1.add(BG, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 800, 510));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel7KeyPressed

    private void jLabel7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MousePressed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jLabel7MousePressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
//        cbid.setEnabled(false);
        tgl1.setEnabled(false);
        tgl2.setEnabled(false);
    }//GEN-LAST:event_formWindowActivated

    private void cbreportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbreportActionPerformed
        // TODO add your handling code here:
        String report = (String) cbreport.getSelectedItem();
        if(report.equals("-- Choose --")){
            formWindowActivated(null);
        }
        else if(report.equals("Receiving (IN) Report")){
//            cbid.setEnabled(true);
            tgl1.setEnabled(true);
            tgl2.setEnabled(true);
//            get_supplier_data();
        }
        else if(report.equals("Issuing (OUT) Report")){
//            cbid.setEnabled(true);
            tgl1.setEnabled(true);
            tgl2.setEnabled(true);
        }
        
    }//GEN-LAST:event_cbreportActionPerformed

    private void tgl1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tgl1PropertyChange
        // TODO add your handling code here:
        tgl1.setDateFormatString("yyyy-MM-dd");
    }//GEN-LAST:event_tgl1PropertyChange

    private void tgl2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tgl2PropertyChange
        // TODO add your handling code here:
        tgl2.setDateFormatString("yyyy-MM-dd");
    }//GEN-LAST:event_tgl2PropertyChange

    private void btnfilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfilterActionPerformed
        // TODO add your handling code here:
        String report = (String) cbreport.getSelectedItem();
        if(report.equals("-- Choose --")){
            formWindowActivated(null);
        }
        else if(report.equals("Receiving (IN) Report")){
          filter_receiving();
        }
        else if(report.equals("Issuing (OUT) Report")){
           filter_issuing();
        }
    }//GEN-LAST:event_btnfilterActionPerformed

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
            java.util.logging.Logger.getLogger(report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new report().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BG;
    private javax.swing.JLabel Header;
    private javax.swing.JButton btnfilter;
    private javax.swing.JComboBox<String> cbreport;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel qty3;
    private javax.swing.JLabel qty4;
    private javax.swing.JTable tbtrans;
    private com.toedter.calendar.JDateChooser tgl1;
    private com.toedter.calendar.JDateChooser tgl2;
    // End of variables declaration//GEN-END:variables
}
