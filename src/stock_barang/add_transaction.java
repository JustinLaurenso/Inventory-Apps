/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock_barang;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author cent91
 */
public class add_transaction extends javax.swing.JFrame {
    public static Connection conn;
    public static Statement st;
    double subtot;
    int stok;
    double total_price = 0;
    double totall;
    /**
     * Creates new form add_transaction
     */
    public add_transaction() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setBackground(new Color (0,0,0,0));
        jPanel1.setBackground(new Color (0,0,0,0));
        koneksi();
        txemail.setText("");
        txnophone.setText("");
        txcategory.setText("");
        tx_item_price.setText("");
        txsub.setText("");
        txtotal.setText("");
        get_customer_data();
        get_product_data();
        clear_table();
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
    
    void trans_id_otomatis(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
        String tggal = sdf.format(cal.getTime());
        try{
             String sql = "select right(notrans,3) from trans_out";
             ResultSet rs = st.executeQuery(sql);
             if(rs.next()){
                 rs.last();
                 String prod_id = ""+(Integer.parseInt(rs.getString(1))+1);
                 
                
                while(prod_id.length() < 3){
                    prod_id = "0" +prod_id;
                    txnotrx.setText("INV/OUT/"+tggal+prod_id);
                }
                 
             }
             else{
                 txnotrx.setText("INV/OUT"+tggal+"001");
             }
        }
        catch(SQLException e){
            System.out.println("Koneksi Gagal " + e.toString());
        }
    }
    
    void get_customer_data(){
         try{
            String sql = "select * from customer";
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery(sql);
            cbcst.addItem("--Customer--");
            while(result.next()){
                cbcst.addItem(result.getString("cust_id"));
                //cbcst.setSelectedItem(result.getString("cust_id"));
            }
        }
        catch(SQLException e){
            System.out.println("Koneksi Gagal " + e.toString());
        }
    }
    
    void get_product_data(){
        try{
            String sql = "select * from product";
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery(sql);
            cbname.addItem("--Product--");
            while(result.next()){
                cbname.addItem(result.getString("product_id"));
                //cbcst.setSelectedItem(result.getString("cust_id"));
            }
        }
        catch(SQLException e){
            System.out.println("Koneksi Gagal " + e.toString());
        }
    }
    
    void clear_table(){
        try{
            String sql = "truncate temp_trans_out";
           st.executeUpdate(sql);
        }
        catch(SQLException e){
            System.out.println("Koneksi gagal" + e.toString());
        }
    }
    
    void tampil_temp_table(){
        int no = 1;
        try{
            String sql = "select temp_trans_out.*,product.name_product from temp_trans_out inner join product on temp_trans_out.product_id = product.product_id";
            ResultSet rs = st.executeQuery(sql);
            
            DefaultTableModel tabeltrans = new DefaultTableModel();
            tabeltrans.addColumn("No");
            tabeltrans.addColumn("No Trans");
            tabeltrans.addColumn("Product Name");
            tabeltrans.addColumn("Qty");
            tabeltrans.addColumn("Price");
            tabeltrans.addColumn("Total");
            tbtrans.setModel(tabeltrans);
            //setting_table_width_column(tbuser, 480, 5, 35, 55, 15);
            tbtrans.getTableHeader().setReorderingAllowed(false);
            tbtrans.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            while(rs.next()){
                tabeltrans.addRow(new Object[]{
                   rs.getString("notemp"),
                   rs.getString("notrans"),
                   rs.getString("name_product"),
                   rs.getString("qty"),
                   rs.getString("price"),
                   rs.getString("total"),
                });
                this.totall = Double.parseDouble(rs.getString("total"));
            }
        }
        catch(SQLException e){
            System.out.println("Koneksi gagal" + e.toString());
        }
    }
    
    void add_temp(){
        String notrans = txnotrx.getText();
        String prod_id = (String) cbname.getSelectedItem();
        String qty = txqty.getText();
        String price = tx_item_price.getText();
        String total = txsub.getText();
        try{
            String sql = "select * from temp_trans_out where product_id='"+prod_id+"'";
            ResultSet rs = st.executeQuery(sql);
            
            if(rs.next()){
               String qty_awal = rs.getString("qty");
               String sub_awal = rs.getString("total");
               String qty_akhir = "" + (Integer.parseInt(qty_awal)+Integer.parseInt(qty));
               String sub_akhir = "" + (Double.parseDouble(sub_awal)+Double.parseDouble(total));
               String sql1 = "update temp_trans_out set qty='"+qty_akhir+"',total='"+sub_akhir+"' where product_id='"+prod_id+"'";
               st.executeUpdate(sql1);
            }
            else{
                String sql2 = "insert into temp_trans_out (notrans,product_id,qty,price,total) values('"+notrans+"','"+prod_id+"','"+qty+"','"+price+"','"+total+"')";
                st.executeUpdate(sql2);
            }
            
        }
        catch(SQLException e){
            System.out.println("Koneksi transaksi gagal" + e.toString());
        }
    }
    
    void save(){
        String notrans = txnotrx.getText();
        String cust_id = (String) cbcst.getSelectedItem();
        String date = txdate.getText();
        String username = txuser.getText();
        String total = txtotal.getText();
        try{
            String sql = "insert into trans_out values('"+notrans+"','"+date+"','"+cust_id+"','"+username+"','"+total+"')";
            st.executeUpdate(sql);
            
            String sql1 = "select * from temp_trans_out where notrans='"+notrans+"'";
            ResultSet rs = st.executeQuery(sql1);
            
            while(rs.next()){
                String prod_id = rs.getString("product_id");
                String qty = rs.getString("qty");
                String price= rs.getString("price");
                String total1 = rs.getString("total");
                
                Statement state1 = conn.createStatement();
                String sql2 = "insert into trans_out_detail values('"+notrans+"','"+prod_id+"','"+qty+"','"+price+"','"+total1+"')";
                state1.executeUpdate(sql2);
                
                Statement state2 = conn.createStatement();
                String sql3 = "select * from product where product_id='"+prod_id+"'";
                ResultSet rs1 = state2.executeQuery(sql3);
                while(rs1.next()){
                    String stok_brg = rs1.getString("stock");
                    String stok_akhir = "" + (Integer.parseInt(stok_brg) - Integer.parseInt(qty));
                    String sql4 = "Update product set stock='"+stok_akhir+"' where product_id='"+prod_id+"'";
                    Statement state3 = conn.createStatement();
                    state3.executeUpdate(sql4);
                }
            }
            String sql4 = "delete from temp_trans_out where notrans='"+notrans+"'";
            st.executeUpdate(sql4);
            
            JOptionPane.showMessageDialog(this, "Transaction Success !", "Success",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException e){
            System.out.println("Koneksi transaksi gagal" + e.toString());
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
        txsub1 = new javax.swing.JLabel();
        txcategory2 = new javax.swing.JLabel();
        txemail1 = new javax.swing.JLabel();
        txppn2 = new javax.swing.JLabel();
        txnophone1 = new javax.swing.JLabel();
        txcategory1 = new javax.swing.JLabel();
        tx_item_price2 = new javax.swing.JLabel();
        tx_item_price1 = new javax.swing.JLabel();
        txtotal1 = new javax.swing.JLabel();
        txtotal2 = new javax.swing.JLabel();
        txtotal3 = new javax.swing.JLabel();
        txppn1 = new javax.swing.JLabel();
        txdate1 = new javax.swing.JLabel();
        txuser1 = new javax.swing.JLabel();
        txnotrx1 = new javax.swing.JLabel();
        Header = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        Notrx = new javax.swing.JLabel();
        txnotrx = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        txdate = new javax.swing.JLabel();
        User = new javax.swing.JLabel();
        txuser = new javax.swing.JLabel();
        cust = new javax.swing.JLabel();
        cbcst = new javax.swing.JComboBox<>();
        User2 = new javax.swing.JLabel();
        txemail = new javax.swing.JLabel();
        nophone = new javax.swing.JLabel();
        txnophone = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        name = new javax.swing.JLabel();
        cbname = new javax.swing.JComboBox<>();
        category = new javax.swing.JLabel();
        txcategory = new javax.swing.JLabel();
        price = new javax.swing.JLabel();
        tx_item_price = new javax.swing.JLabel();
        qty = new javax.swing.JLabel();
        txqty = new javax.swing.JTextField();
        sub = new javax.swing.JLabel();
        txsub = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        txtotal = new javax.swing.JLabel();
        btnadd = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btndelete = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbtrans = new javax.swing.JTable();
        btnsave = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
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

        txsub1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txsub1.setForeground(new java.awt.Color(76, 178, 178));
        txsub1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txsub1.setText("500000");
        jPanel1.add(txsub1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 300, 110, -1));

        txcategory2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txcategory2.setForeground(new java.awt.Color(255, 255, 255));
        txcategory2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txcategory2.setText(":");
        jPanel1.add(txcategory2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, 10, -1));

        txemail1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txemail1.setForeground(new java.awt.Color(255, 255, 255));
        txemail1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txemail1.setText(":");
        jPanel1.add(txemail1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 130, 10, -1));

        txppn2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txppn2.setForeground(new java.awt.Color(255, 255, 255));
        txppn2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txppn2.setText("Rp.");
        jPanel1.add(txppn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 250, 30, -1));

        txnophone1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txnophone1.setForeground(new java.awt.Color(255, 255, 255));
        txnophone1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txnophone1.setText(":");
        jPanel1.add(txnophone1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 160, 10, -1));

        txcategory1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txcategory1.setForeground(new java.awt.Color(255, 255, 255));
        txcategory1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txcategory1.setText(":");
        jPanel1.add(txcategory1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, 10, -1));

        tx_item_price2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tx_item_price2.setForeground(new java.awt.Color(255, 255, 255));
        tx_item_price2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tx_item_price2.setText("Rp.");
        jPanel1.add(tx_item_price2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 280, 30, -1));

        tx_item_price1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tx_item_price1.setForeground(new java.awt.Color(255, 255, 255));
        tx_item_price1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tx_item_price1.setText(":");
        jPanel1.add(tx_item_price1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, 10, -1));

        txtotal1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtotal1.setForeground(new java.awt.Color(255, 255, 255));
        txtotal1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtotal1.setText(":");
        jPanel1.add(txtotal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 100, 10, -1));

        txtotal2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtotal2.setForeground(new java.awt.Color(255, 255, 255));
        txtotal2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtotal2.setText(":");
        jPanel1.add(txtotal2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 280, 10, -1));

        txtotal3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtotal3.setForeground(new java.awt.Color(255, 255, 255));
        txtotal3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtotal3.setText("Rp.");
        jPanel1.add(txtotal3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 280, 30, -1));

        txppn1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txppn1.setForeground(new java.awt.Color(255, 255, 255));
        txppn1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txppn1.setText(":");
        jPanel1.add(txppn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 250, 10, -1));

        txdate1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txdate1.setForeground(new java.awt.Color(255, 255, 255));
        txdate1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txdate1.setText(":");
        jPanel1.add(txdate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 10, -1));

        txuser1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txuser1.setForeground(new java.awt.Color(255, 255, 255));
        txuser1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txuser1.setText(":");
        jPanel1.add(txuser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 10, -1));

        txnotrx1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txnotrx1.setForeground(new java.awt.Color(255, 255, 255));
        txnotrx1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txnotrx1.setText(":");
        jPanel1.add(txnotrx1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 10, -1));

        Header.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        Header.setForeground(new java.awt.Color(255, 255, 255));
        Header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Header.setText("ADD TRANSACTION (OUT)");
        jPanel1.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 500, 40));

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
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 780, 10));

        Notrx.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        Notrx.setForeground(new java.awt.Color(255, 255, 255));
        Notrx.setText("No.Transaction ");
        jPanel1.add(Notrx, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, -1, -1));

        txnotrx.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txnotrx.setForeground(new java.awt.Color(255, 255, 255));
        txnotrx.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txnotrx.setText("INV/OUT/0012020");
        jPanel1.add(txnotrx, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, 160, -1));

        date.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        date.setForeground(new java.awt.Color(255, 255, 255));
        date.setText("Date");
        jPanel1.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 90, -1));

        txdate.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txdate.setForeground(new java.awt.Color(255, 255, 255));
        txdate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txdate.setText("14/12/2020");
        jPanel1.add(txdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 120, -1));

        User.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        User.setForeground(new java.awt.Color(255, 255, 255));
        User.setText("User");
        jPanel1.add(User, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 90, -1));

        txuser.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txuser.setForeground(new java.awt.Color(255, 255, 255));
        txuser.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txuser.setText("Vartin Suhandi");
        jPanel1.add(txuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 130, -1));

        cust.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cust.setForeground(new java.awt.Color(255, 255, 255));
        cust.setText("Customer");
        jPanel1.add(cust, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 100, 90, -1));

        cbcst.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        cbcst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbcstActionPerformed(evt);
            }
        });
        jPanel1.add(cbcst, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 100, 140, -1));

        User2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        User2.setForeground(new java.awt.Color(255, 255, 255));
        User2.setText("Name");
        jPanel1.add(User2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 130, 90, -1));

        txemail.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txemail.setForeground(new java.awt.Color(255, 255, 255));
        txemail.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txemail.setText("Vartin Suhandi");
        jPanel1.add(txemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 130, 200, -1));

        nophone.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        nophone.setForeground(new java.awt.Color(255, 255, 255));
        nophone.setText("No.Phone");
        jPanel1.add(nophone, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 160, 90, -1));

        txnophone.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txnophone.setForeground(new java.awt.Color(255, 255, 255));
        txnophone.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txnophone.setText("0812-3456-7899");
        jPanel1.add(txnophone, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 130, -1));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 780, 10));

        name.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        name.setForeground(new java.awt.Color(255, 255, 255));
        name.setText("Item Name");
        jPanel1.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 90, -1));

        cbname.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        cbname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbnameActionPerformed(evt);
            }
        });
        jPanel1.add(cbname, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, 140, -1));

        category.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        category.setForeground(new java.awt.Color(255, 255, 255));
        category.setText("Category");
        jPanel1.add(category, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 90, -1));

        txcategory.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txcategory.setForeground(new java.awt.Color(255, 255, 255));
        txcategory.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txcategory.setText("Food");
        jPanel1.add(txcategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 130, -1));

        price.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        price.setForeground(new java.awt.Color(255, 255, 255));
        price.setText("Price");
        jPanel1.add(price, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 90, -1));

        tx_item_price.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tx_item_price.setForeground(new java.awt.Color(255, 255, 255));
        tx_item_price.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tx_item_price.setText("500000");
        jPanel1.add(tx_item_price, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, 130, -1));

        qty.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        qty.setForeground(new java.awt.Color(255, 255, 255));
        qty.setText("QTY                  :");
        jPanel1.add(qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 220, 90, -1));

        txqty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txqtyActionPerformed(evt);
            }
        });
        txqty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txqtyKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txqtyKeyReleased(evt);
            }
        });
        jPanel1.add(txqty, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, 110, -1));

        sub.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        sub.setForeground(new java.awt.Color(255, 255, 255));
        sub.setText("Subtotal");
        jPanel1.add(sub, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, 70, -1));

        txsub.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txsub.setForeground(new java.awt.Color(255, 255, 255));
        txsub.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txsub.setText("500000");
        jPanel1.add(txsub, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 250, 110, -1));

        total.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        total.setForeground(new java.awt.Color(255, 255, 255));
        total.setText("Total");
        jPanel1.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, 70, -1));

        txtotal.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtotal.setForeground(new java.awt.Color(255, 255, 255));
        txtotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtotal.setText("500000");
        jPanel1.add(txtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 280, 110, -1));

        btnadd.setBackground(new java.awt.Color(51, 204, 0));
        btnadd.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_plus_15px.png"))); // NOI18N
        btnadd.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 31, 30));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ADD");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel4MousePressed(evt);
            }
        });
        btnadd.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 0, 35, 30));

        jPanel1.add(btnadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 240, 80, 30));

        btndelete.setBackground(new java.awt.Color(255, 0, 0));
        btndelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btndeleteMousePressed(evt);
            }
        });
        btndelete.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_plus_15px.png"))); // NOI18N
        btndelete.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 31, 30));

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("CANCEL");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel6MousePressed(evt);
            }
        });
        btndelete.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 0, 43, 30));

        jPanel1.add(btndelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 240, 90, 30));

        tbtrans.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        tbtrans.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbtrans.setFillsViewportHeight(true);
        tbtrans.setShowGrid(false);
        tbtrans.setShowHorizontalLines(true);
        tbtrans.setShowVerticalLines(true);
        jScrollPane1.setViewportView(tbtrans);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 760, 110));

        btnsave.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N
        btnsave.setForeground(new java.awt.Color(255, 255, 255));
        btnsave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnsave.setText("SAVE");
        btnsave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnsaveMousePressed(evt);
            }
        });
        jPanel1.add(btnsave, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 440, 130, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/btn-trx-save.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 430, 260, 50));

        BG.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/add_trx_bg.png"))); // NOI18N
        jPanel1.add(BG, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 800, 510));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txqtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txqtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txqtyActionPerformed

    private void cbnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbnameActionPerformed
        // TODO add your handling code here:
        String kode = (String) cbname.getSelectedItem();
        if(kode.equals("--Product=--")){
            txcategory.setText("");
            tx_item_price.setText("");
        }
        else{
             try{
                String sql = "select * from product where product_id='"+kode+"'";
                ResultSet rs = st.executeQuery(sql);
                if(rs.next()){
                    txcategory.setText(rs.getString("category"));
                    tx_item_price.setText(rs.getString("price"));
                    this.stok = Integer.parseInt(rs.getString("stock"));
                }
            }catch(SQLException e){
                System.out.println("Koneksi Gagal " + e.toString());
            }
            txqty.grabFocus();
        }
        
    }//GEN-LAST:event_cbnameActionPerformed

    private void jLabel7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel7KeyPressed

    private void jLabel7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MousePressed
        // TODO add your handling code here:
        this.dispose();
        Session_Update.update_status("Issuing");
    }//GEN-LAST:event_jLabel7MousePressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:\
        tampil_temp_table();
        clear_table();
        txuser.setText(Session_Login.get_namauser());
        trans_id_otomatis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        txdate.setText(sdf.format(cal.getTime()));
    }//GEN-LAST:event_formWindowActivated

    private void cbcstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbcstActionPerformed
        // TODO add your handling code here:
        String kode = (String) cbcst.getSelectedItem();
        try{
            String sql = "select * from customer where cust_id='"+kode+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                txemail.setText(rs.getString("cust_name"));
                txnophone.setText(rs.getString("nohp"));
            }
        }catch(SQLException e){
            System.out.println("Koneksi Gagal " + e.toString());
        }
    }//GEN-LAST:event_cbcstActionPerformed

    private void txqtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txqtyKeyPressed
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(Character.isLetter(c)){
            txqty.setEditable(false);
        }
        else{
            txqty.setEditable(true);
        }
    }//GEN-LAST:event_txqtyKeyPressed

    private void txqtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txqtyKeyReleased
        // TODO add your handling code here:
       if(txqty.getText().equals("")){
           double price = Double.parseDouble(tx_item_price.getText());
            int qty = 0;
            double subtotal = price * qty;
            txsub.setText(Double.toString(subtotal));
       }
       else{
//            double price = Double.parseDouble(tx_item_price.getText());
//            int qty = Integer.parseInt(txqty.getText());
//            double subtotal = price * qty;
//            txsub.setText(Double.toString(subtotal));
//            this.subtot = subtotal;
           }
    }//GEN-LAST:event_txqtyKeyReleased

    private void jLabel4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MousePressed
        // TODO add your handling code here:
        if(this.stok < Integer.parseInt(txqty.getText())){
            JOptionPane.showMessageDialog(this,"Stock is not enough (Stock Available : " + this.stok + ")");
        }
        else{
            double total2 = 0;
            int qty = Integer.parseInt(txqty.getText());
            double harga = Double.parseDouble(tx_item_price.getText());
            double subtotal;
            subtotal = harga * qty;
            txsub.setText(Double.toString(subtotal));
            this.totall = subtotal;
            if(txtotal.getText().equals("")){
                this.total_price = 0;
            }
            else{
                 this.total_price = Double.parseDouble(txtotal.getText());
            }
            total2 = this.total_price + this.totall;
            txtotal.setText(Double.toString(total2));
            add_temp();
            tampil_temp_table();
        }
        
    }//GEN-LAST:event_jLabel4MousePressed

    private void btnsaveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsaveMousePressed
        // TODO add your handling code here:
        save();
        cbcst.setSelectedIndex(0);
        cbname.setSelectedIndex(0);
        txcategory.setText("");
        tx_item_price.setText("");
        txsub.setText("");
        txtotal.setText("");
        txemail.setText("");
        txnophone.setText("");
        txqty.setText("");
    }//GEN-LAST:event_btnsaveMousePressed

    private void jLabel6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MousePressed
        // TODO add your handling code here:
        cbcst.setSelectedIndex(0);
        cbname.setSelectedIndex(0);
        txcategory.setText("");
        tx_item_price.setText("");
        txsub.setText("");
        txtotal.setText("");
        txemail.setText("");
        txnophone.setText("");
        clear_table();
    }//GEN-LAST:event_jLabel6MousePressed

    private void btndeleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndeleteMousePressed
        // TODO add your handling code here:
        cbcst.setSelectedIndex(0);
        cbname.setSelectedIndex(0);
        txcategory.setText("");
        tx_item_price.setText("");
        txsub.setText("");
        txtotal.setText("");
        txemail.setText("");
        txnophone.setText("");
        clear_table();
    }//GEN-LAST:event_btndeleteMousePressed

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
            java.util.logging.Logger.getLogger(add_transaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(add_transaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(add_transaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(add_transaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new add_transaction().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BG;
    private javax.swing.JLabel Header;
    private javax.swing.JLabel Notrx;
    private javax.swing.JLabel User;
    private javax.swing.JLabel User2;
    private javax.swing.JPanel btnadd;
    private javax.swing.JPanel btndelete;
    private javax.swing.JLabel btnsave;
    private javax.swing.JLabel category;
    private javax.swing.JComboBox<String> cbcst;
    private javax.swing.JComboBox<String> cbname;
    private javax.swing.JLabel cust;
    private javax.swing.JLabel date;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel name;
    private javax.swing.JLabel nophone;
    private javax.swing.JLabel price;
    private javax.swing.JLabel qty;
    private javax.swing.JLabel sub;
    private javax.swing.JTable tbtrans;
    private javax.swing.JLabel total;
    private javax.swing.JLabel tx_item_price;
    private javax.swing.JLabel tx_item_price1;
    private javax.swing.JLabel tx_item_price2;
    private javax.swing.JLabel txcategory;
    private javax.swing.JLabel txcategory1;
    private javax.swing.JLabel txcategory2;
    private javax.swing.JLabel txdate;
    private javax.swing.JLabel txdate1;
    private javax.swing.JLabel txemail;
    private javax.swing.JLabel txemail1;
    private javax.swing.JLabel txnophone;
    private javax.swing.JLabel txnophone1;
    private javax.swing.JLabel txnotrx;
    private javax.swing.JLabel txnotrx1;
    private javax.swing.JLabel txppn1;
    private javax.swing.JLabel txppn2;
    private javax.swing.JTextField txqty;
    private javax.swing.JLabel txsub;
    private javax.swing.JLabel txsub1;
    private javax.swing.JLabel txtotal;
    private javax.swing.JLabel txtotal1;
    private javax.swing.JLabel txtotal2;
    private javax.swing.JLabel txtotal3;
    private javax.swing.JLabel txuser;
    private javax.swing.JLabel txuser1;
    // End of variables declaration//GEN-END:variables
}
