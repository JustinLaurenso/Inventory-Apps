/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock_barang;
import java.awt.Color;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author cent91
 */
public class DASBOARD extends javax.swing.JFrame {
    public static Connection conn;
    public static Statement st;
    String primary;
    String status;
    String current_status = "";
    String update_status;
    String session = "";
    /**
     * Creates new form DASBOARD
     */
    public DASBOARD() {
        initComponents();
        lblusername.setText(Session_Login.get_namauser());
        lblstatus.setText(Session_Login.get_status());
        lbldirect.setText(Session_Login.get_status() + "/" + lblproduct.getText() + "/");
        btnedit.setVisible(false);
        lbl_btnedit.setVisible(false);
        icn_btnedit.setVisible(false);
        btndelete.setVisible(false);
        lbl_btndelete.setVisible(false);
        lbl_btndelete.setVisible(false);
        koneksi();
        check_status();
    }
    
    void check_status(){
        if(Session_Login.get_status().equals("User")){
            Session_Update.update_status("Receiving");
            menu_user.setVisible(false);
            menu_customer.setVisible(true);
            menu_user.setVisible(false);
            menu_prod.setVisible(false);
            menu_supplier.setVisible(false);
            menu_report.setVisible(true);
            
        }
    }
    
    void user(){
        setColor(menu_user);
        resetColor(menu_trx);
        resetColor(menu_prod);
        resetColor(menu_supplier);
        resetColor(menu_in);
        resetColor(menu_customer);
        btnedit.setVisible(false);
        icn_btnedit.setVisible(false);
        lbl_btnedit.setVisible(false);
        btndelete.setVisible(false);
        icn_btndelete.setVisible(false);
        lbl_btndelete.setVisible(false);
        lbldirect.setText(Session_Login.get_status() + " / " + lbluser.getText() + " /");
        lblheader.setText("User History  _________________________________");
        lbladd.setText("Add User");
        tampil_table_user();
        this.current_status = "User";
        btn_add1.setVisible(false);
    }
    
    void product(){
        setColor(menu_prod);
        resetColor(menu_trx);
        resetColor(menu_user);
        resetColor(menu_in);
        resetColor(menu_supplier);
        resetColor(menu_customer);
        btnedit.setVisible(false);
        icn_btnedit.setVisible(false);
        lbl_btnedit.setVisible(false);
        btndelete.setVisible(false);
        icn_btndelete.setVisible(false);
        lbl_btndelete.setVisible(false);
        lbldirect.setText(Session_Login.get_status() + " / " + lblproduct.getText() + " / ");
        lblheader.setText("Product History ________________________");
        lbladd.setText("Add Product"); 
        this.current_status = "Product";
        tampil_table_product();
        btn_add1.setVisible(true);
    }
    
    void customer(){
        setColor(menu_customer);
        resetColor(menu_trx);
        resetColor(menu_in);
        resetColor(menu_user);
        resetColor(menu_prod);
        resetColor(menu_supplier);
        btnedit.setVisible(false);
        icn_btnedit.setVisible(false);
        lbl_btnedit.setVisible(false);
        btndelete.setVisible(false);
        icn_btndelete.setVisible(false);
        lbl_btndelete.setVisible(false);
        lbldirect.setText(Session_Login.get_status() + " / " + lblcustomer.getText() + " /");
        lblheader.setText("Customer History ________________________");
        tampil_table_customer();
        lbladd.setText("Add Customer");
        this.current_status = "Customer";
        btn_add1.setVisible(false);
    }
    
    void supplier(){
         setColor(menu_supplier);
        resetColor(menu_trx);
        resetColor(menu_user);
        resetColor(menu_prod);
        resetColor(menu_in);
        resetColor(menu_customer);
        btnedit.setVisible(false);
        icn_btnedit.setVisible(false);
        lbl_btnedit.setVisible(false);
        btndelete.setVisible(false);
        icn_btndelete.setVisible(false);
        lbl_btndelete.setVisible(false);
        lbldirect.setText(Session_Login.get_status() + " / " + lblsupplier.getText() + " /");
        lblheader.setText("Supplier History ________________________");
        lbladd.setText("Add Supplier");
        tampil_table_supplier();
        this.current_status = "Supplier";
        btn_add1.setVisible(false);
    }
    
    void transaction(){
        setColor(menu_trx);
        resetColor(menu_prod);
        resetColor(menu_user);
        resetColor(menu_supplier);
        resetColor(menu_customer);
        resetColor(menu_in);
        btnedit.setVisible(false);
        icn_btnedit.setVisible(false);
        lbl_btnedit.setVisible(false);
        btndelete.setVisible(false);
        icn_btndelete.setVisible(false);
        lbl_btndelete.setVisible(false);
        lbldirect.setText(Session_Login.get_status() + " / Issuing (Out) /");
        lblheader.setText("Issuing History ____________________");
        lbladd.setText("Add Issuing");
        tampil_table_issuing();
        this.current_status = "Issuing";
        btn_add1.setVisible(false);
    }
    
    void transaction_in(){
        setColor(menu_in);
        resetColor(menu_prod);
        resetColor(menu_trx);
        resetColor(menu_user);
        resetColor(menu_supplier);
        resetColor(menu_customer);
        btnedit.setVisible(false);
        icn_btnedit.setVisible(false);
        lbl_btnedit.setVisible(false);
        btndelete.setVisible(false);
        icn_btndelete.setVisible(false);
        lbl_btndelete.setVisible(false);
        lbldirect.setText(Session_Login.get_status() + " / Receivin (In) /");
        lblheader.setText("Receiving History ____________________");
        lbladd.setText("Add Receiving");
        tampil_table_receiving();
        this.current_status = "Receiving";
        btn_add1.setVisible(false);
    }
    
    void current_tab(){
        if(Session_Update.get_update_status().equals("default")){
            product();
        }
        else{
            this.session = Session_Update.get_update_status();
            if(session.equals("User")){
                user();
            }
            else if(session.equals("Customer")){
                customer();
            }
            else if(session.equals("Supplier")){
                supplier();
            }
            else if(session.equals("Issuing")){
                transaction();
            }
            else if(session.equals("Receiving")){
                transaction_in();
            }
            
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
    
    void tampil_table_user(){
        int no = 1;
        try{
            String sql = "select * from user";
            ResultSet rs = st.executeQuery(sql);
            
            DefaultTableModel tabeluser = new DefaultTableModel();
            tabeluser.addColumn("No");
            tabeluser.addColumn("Username");
            tabeluser.addColumn("Name");
            tabeluser.addColumn("Email");
            tabeluser.addColumn("Position");
            table_dashboard.setModel(tabeluser);
            //setting_table_width_column(tbuser, 480, 5, 35, 55, 15);
            table_dashboard.getTableHeader().setReorderingAllowed(false);
            table_dashboard.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            while(rs.next()){
                tabeluser.addRow(new Object[]{
                   no++,
                   rs.getString("username"),
                   rs.getString("name"),
                   rs.getString("email"),
                   rs.getString("position"),
                });
            }
        }
        catch(SQLException e){
            System.out.println("Koneksi Gagal " + e.toString());
        }
    }
    
    void tampil_table_product(){
        int no = 1;
        try{
            String sql = "select * from product";
            ResultSet rs = st.executeQuery(sql);
            
            DefaultTableModel tabeluser = new DefaultTableModel();
            tabeluser.addColumn("No");
            tabeluser.addColumn("Product ID");
            tabeluser.addColumn("Product Name");
            tabeluser.addColumn("Price");
            tabeluser.addColumn("Stock");
            tabeluser.addColumn("Category");
            tabeluser.addColumn("Supplier ID");
            table_dashboard.setModel(tabeluser);
            //setting_table_width_column(tbuser, 480, 5, 35, 55, 15);
            table_dashboard.getTableHeader().setReorderingAllowed(false);
            table_dashboard.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            while(rs.next()){
                tabeluser.addRow(new Object[]{
                   no++,
                   rs.getString("product_id"),
                   rs.getString("name_product"),
                   rs.getString("price"),
                   rs.getString("stock"),
                   rs.getString("category"),
                   rs.getString("supp_id"),
                });
            }
        }
        catch(SQLException e){
            System.out.println("Koneksi Gagal " + e.toString());
        }
    }
    
    void tampil_table_receiving(){
        int no = 1;
        try{
            String sql = "select * from trans_in";
            ResultSet rs = st.executeQuery(sql);
            
            DefaultTableModel tabeluser = new DefaultTableModel();
            tabeluser.addColumn("No");
            tabeluser.addColumn("No Transaction");
            tabeluser.addColumn("Date");
            tabeluser.addColumn("Supplier ID");
            tabeluser.addColumn("Username");
            tabeluser.addColumn("Total");
            table_dashboard.setModel(tabeluser);
            //setting_table_width_column(tbuser, 480, 5, 35, 55, 15);
            table_dashboard.getTableHeader().setReorderingAllowed(false);
            table_dashboard.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            while(rs.next()){
                tabeluser.addRow(new Object[]{
                   no++,
                   rs.getString("notrans"),
                   rs.getString("date"),
                   rs.getString("supp_id"),
                   rs.getString("username"),
                   rs.getString("total"),
                });
            }
        }
        catch(SQLException e){
            System.out.println("Koneksi Gagal " + e.toString());
        }
    }
    
    void tampil_table_issuing(){
        int no = 1;
        try{
            String sql = "select * from trans_out";
            ResultSet rs = st.executeQuery(sql);
            
            DefaultTableModel tabeluser = new DefaultTableModel();
            tabeluser.addColumn("No");
            tabeluser.addColumn("No Transaction");
            tabeluser.addColumn("Date");
            tabeluser.addColumn("Customer ID");
            tabeluser.addColumn("Username");
            tabeluser.addColumn("Total");
            table_dashboard.setModel(tabeluser);
            //setting_table_width_column(tbuser, 480, 5, 35, 55, 15);
            table_dashboard.getTableHeader().setReorderingAllowed(false);
            table_dashboard.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            while(rs.next()){
                tabeluser.addRow(new Object[]{
                   no++,
                   rs.getString("notrans"),
                   rs.getString("date"),
                   rs.getString("cust_id"),
                   rs.getString("username"),
                   rs.getString("total"),
                });
            }
        }
        catch(SQLException e){
            System.out.println("Koneksi Gagal " + e.toString());
        }
    }
    
    void tampil_table_supplier(){
        int no = 1;
        try{
            String sql = "select * from supplier";
            ResultSet rs = st.executeQuery(sql);
            
            DefaultTableModel tabeluser = new DefaultTableModel();
            tabeluser.addColumn("No");
            tabeluser.addColumn("Supplier ID");
            tabeluser.addColumn("Supplier Name");
            tabeluser.addColumn("Email");
            tabeluser.addColumn("Address");
            tabeluser.addColumn("Phone");
            table_dashboard.setModel(tabeluser);
            //setting_table_width_column(tbuser, 480, 5, 35, 55, 15);
            table_dashboard.getTableHeader().setReorderingAllowed(false);
            table_dashboard.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            while(rs.next()){
                tabeluser.addRow(new Object[]{
                   no++,
                   rs.getString("supp_id"),
                   rs.getString("supp_name"),
                   rs.getString("supp_email"),
                   rs.getString("supp_address"),
                   rs.getString("supp_nohp")
                });
            }
        }
        catch(SQLException e){
            System.out.println("Koneksi Gagal " + e.toString());
        }
    }
    
    void tampil_table_customer(){
        int no = 1;
        try{
            String sql = "select * from customer";
            ResultSet rs = st.executeQuery(sql);
            
            DefaultTableModel tabeluser = new DefaultTableModel();
            tabeluser.addColumn("No");
            tabeluser.addColumn("Customer ID");
            tabeluser.addColumn("Customer Name");
            tabeluser.addColumn("Email");
            tabeluser.addColumn("Address");
            tabeluser.addColumn("Phone");
            table_dashboard.setModel(tabeluser);
            //setting_table_width_column(tbuser, 480, 5, 35, 55, 15);
            table_dashboard.getTableHeader().setReorderingAllowed(false);
            table_dashboard.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            while(rs.next()){
                tabeluser.addRow(new Object[]{
                   no++,
                   rs.getString("cust_id"),
                   rs.getString("cust_name"),
                   rs.getString("cust_email"),
                   rs.getString("address"),
                   rs.getString("nohp")
                });
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        BG = new javax.swing.JPanel();
        Sidepanel = new javax.swing.JPanel();
        Profile = new javax.swing.JPanel();
        lblusername = new javax.swing.JLabel();
        lblstatus = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        menu_prod = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblproduct = new javax.swing.JLabel();
        menu_in = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lbltrans1 = new javax.swing.JLabel();
        menu_trx = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lbltrans = new javax.swing.JLabel();
        menu_user = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lbluser = new javax.swing.JLabel();
        menu_supplier = new javax.swing.JPanel();
        lblsupplier = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        menu_customer = new javax.swing.JPanel();
        lblcustomer = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        menu_report = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        lblcustomer2 = new javax.swing.JLabel();
        Header = new javax.swing.JPanel();
        lbldirect = new javax.swing.JLabel();
        lblheader = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_dashboard = new javax.swing.JTable();
        btn_add = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        lbladd = new javax.swing.JLabel();
        btn_add1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        lbladd1 = new javax.swing.JLabel();
        logout = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnexit = new javax.swing.JLabel();
        btnedit = new javax.swing.JPanel();
        icn_btnedit = new javax.swing.JLabel();
        lbl_btnedit = new javax.swing.JLabel();
        btndelete = new javax.swing.JPanel();
        icn_btndelete = new javax.swing.JLabel();
        lbl_btndelete = new javax.swing.JLabel();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        BG.setBackground(new java.awt.Color(252, 248, 236));
        BG.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Sidepanel.setBackground(new java.awt.Color(121, 163, 172));
        Sidepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Profile.setBackground(new java.awt.Color(121, 163, 172));
        Profile.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblusername.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblusername.setForeground(new java.awt.Color(255, 255, 255));
        lblusername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblusername.setText("USER NAME");
        Profile.add(lblusername, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 210, -1));

        lblstatus.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblstatus.setForeground(new java.awt.Color(255, 255, 255));
        lblstatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblstatus.setText("ADMIN");
        Profile.add(lblstatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 210, -1));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_cat_profile_100px.png"))); // NOI18N
        Profile.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 210, 100));

        Sidepanel.add(Profile, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 180));

        menu_prod.setBackground(new java.awt.Color(118, 153, 160));
        menu_prod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_prodMousePressed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_product_management_15px.png"))); // NOI18N

        lblproduct.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblproduct.setForeground(new java.awt.Color(255, 255, 255));
        lblproduct.setText("Product");

        javax.swing.GroupLayout menu_prodLayout = new javax.swing.GroupLayout(menu_prod);
        menu_prod.setLayout(menu_prodLayout);
        menu_prodLayout.setHorizontalGroup(
            menu_prodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_prodLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblproduct)
                .addGap(0, 100, Short.MAX_VALUE))
        );
        menu_prodLayout.setVerticalGroup(
            menu_prodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menu_prodLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblproduct)
                .addContainerGap())
        );

        Sidepanel.add(menu_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, -1, 40));

        menu_in.setBackground(new java.awt.Color(69, 98, 104));
        menu_in.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_inMousePressed(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_truck_15px.png"))); // NOI18N

        lbltrans1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbltrans1.setForeground(new java.awt.Color(255, 255, 255));
        lbltrans1.setText("Receving / In");

        javax.swing.GroupLayout menu_inLayout = new javax.swing.GroupLayout(menu_in);
        menu_in.setLayout(menu_inLayout);
        menu_inLayout.setHorizontalGroup(
            menu_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_inLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbltrans1)
                .addContainerGap(68, Short.MAX_VALUE))
        );
        menu_inLayout.setVerticalGroup(
            menu_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menu_inLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbltrans1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Sidepanel.add(menu_in, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 210, 50));

        menu_trx.setBackground(new java.awt.Color(118, 153, 160));
        menu_trx.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_trxMousePressed(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_checkout_15px.png"))); // NOI18N

        lbltrans.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbltrans.setForeground(new java.awt.Color(255, 255, 255));
        lbltrans.setText("Issuing / Out");

        javax.swing.GroupLayout menu_trxLayout = new javax.swing.GroupLayout(menu_trx);
        menu_trx.setLayout(menu_trxLayout);
        menu_trxLayout.setHorizontalGroup(
            menu_trxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_trxLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbltrans)
                .addGap(0, 76, Short.MAX_VALUE))
        );
        menu_trxLayout.setVerticalGroup(
            menu_trxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menu_trxLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(lbltrans)
                .addContainerGap())
        );

        Sidepanel.add(menu_trx, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, -1, -1));

        menu_user.setBackground(new java.awt.Color(118, 153, 160));
        menu_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_userMousePressed(evt);
            }
        });

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_user_15px_2.png"))); // NOI18N

        lbluser.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbluser.setForeground(new java.awt.Color(255, 255, 255));
        lbluser.setText("User Management");

        javax.swing.GroupLayout menu_userLayout = new javax.swing.GroupLayout(menu_user);
        menu_user.setLayout(menu_userLayout);
        menu_userLayout.setHorizontalGroup(
            menu_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_userLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbluser)
                .addGap(0, 34, Short.MAX_VALUE))
        );
        menu_userLayout.setVerticalGroup(
            menu_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menu_userLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(lbluser)
                .addContainerGap())
        );

        Sidepanel.add(menu_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 210, -1));

        menu_supplier.setBackground(new java.awt.Color(118, 153, 160));
        menu_supplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_supplierMousePressed(evt);
            }
        });

        lblsupplier.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblsupplier.setForeground(new java.awt.Color(255, 255, 255));
        lblsupplier.setText("Supplier");

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_wrestling_15px_1.png"))); // NOI18N

        javax.swing.GroupLayout menu_supplierLayout = new javax.swing.GroupLayout(menu_supplier);
        menu_supplier.setLayout(menu_supplierLayout);
        menu_supplierLayout.setHorizontalGroup(
            menu_supplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_supplierLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblsupplier)
                .addGap(0, 98, Short.MAX_VALUE))
        );
        menu_supplierLayout.setVerticalGroup(
            menu_supplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menu_supplierLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(menu_supplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblsupplier, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        Sidepanel.add(menu_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 210, -1));

        menu_customer.setBackground(new java.awt.Color(118, 153, 160));
        menu_customer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_customerMousePressed(evt);
            }
        });

        lblcustomer.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblcustomer.setForeground(new java.awt.Color(255, 255, 255));
        lblcustomer.setText("Customer");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_wrestling_15px_1.png"))); // NOI18N

        javax.swing.GroupLayout menu_customerLayout = new javax.swing.GroupLayout(menu_customer);
        menu_customer.setLayout(menu_customerLayout);
        menu_customerLayout.setHorizontalGroup(
            menu_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_customerLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblcustomer)
                .addGap(0, 87, Short.MAX_VALUE))
        );
        menu_customerLayout.setVerticalGroup(
            menu_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menu_customerLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(menu_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblcustomer, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        Sidepanel.add(menu_customer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 210, -1));

        menu_report.setBackground(new java.awt.Color(118, 153, 160));
        menu_report.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_reportMousePressed(evt);
            }
        });

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_report_file_15px.png"))); // NOI18N

        lblcustomer2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblcustomer2.setForeground(new java.awt.Color(255, 255, 255));
        lblcustomer2.setText("Report");

        javax.swing.GroupLayout menu_reportLayout = new javax.swing.GroupLayout(menu_report);
        menu_report.setLayout(menu_reportLayout);
        menu_reportLayout.setHorizontalGroup(
            menu_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_reportLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblcustomer2)
                .addGap(0, 104, Short.MAX_VALUE))
        );
        menu_reportLayout.setVerticalGroup(
            menu_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menu_reportLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblcustomer2)
                .addGap(19, 19, 19))
            .addGroup(menu_reportLayout.createSequentialGroup()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Sidepanel.add(menu_report, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 210, 40));

        BG.add(Sidepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 600));

        Header.setBackground(new java.awt.Color(0, 204, 204));

        lbldirect.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lbldirect.setForeground(new java.awt.Color(255, 255, 255));
        lbldirect.setText("Admin/Product/");

        lblheader.setFont(new java.awt.Font("Roboto", 0, 36)); // NOI18N
        lblheader.setForeground(new java.awt.Color(255, 255, 255));
        lblheader.setText("Product History ________________________");

        javax.swing.GroupLayout HeaderLayout = new javax.swing.GroupLayout(Header);
        Header.setLayout(HeaderLayout);
        HeaderLayout.setHorizontalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HeaderLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(lbldirect))
                    .addGroup(HeaderLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblheader)))
                .addContainerGap(135, Short.MAX_VALUE))
        );
        HeaderLayout.setVerticalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lbldirect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblheader)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        BG.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 800, 100));

        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        table_dashboard.setAutoCreateRowSorter(true);
        table_dashboard.setBackground(new java.awt.Color(252, 248, 236));
        table_dashboard.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        table_dashboard.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Name", "Stock", "Price"
            }
        ));
        table_dashboard.setFillsViewportHeight(true);
        table_dashboard.setGridColor(new java.awt.Color(252, 248, 236));
        table_dashboard.setIntercellSpacing(new java.awt.Dimension(5, 5));
        table_dashboard.setSelectionBackground(new java.awt.Color(51, 255, 255));
        table_dashboard.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        table_dashboard.setShowGrid(false);
        table_dashboard.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                table_dashboardFocusLost(evt);
            }
        });
        table_dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_dashboardMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_dashboard);
        if (table_dashboard.getColumnModel().getColumnCount() > 0) {
            table_dashboard.getColumnModel().getColumn(0).setResizable(false);
            table_dashboard.getColumnModel().getColumn(0).setPreferredWidth(10);
            table_dashboard.getColumnModel().getColumn(1).setResizable(false);
            table_dashboard.getColumnModel().getColumn(1).setPreferredWidth(10);
            table_dashboard.getColumnModel().getColumn(2).setResizable(false);
            table_dashboard.getColumnModel().getColumn(2).setPreferredWidth(10);
        }

        BG.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 770, 240));

        btn_add.setBackground(new java.awt.Color(0, 153, 153));

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_plus_15px.png"))); // NOI18N

        lbladd.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbladd.setForeground(new java.awt.Color(255, 255, 255));
        lbladd.setText("Add Product");
        lbladd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbladdMousePressed(evt);
            }
        });

        javax.swing.GroupLayout btn_addLayout = new javax.swing.GroupLayout(btn_add);
        btn_add.setLayout(btn_addLayout);
        btn_addLayout.setHorizontalGroup(
            btn_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_addLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbladd, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addGap(31, 31, 31))
        );
        btn_addLayout.setVerticalGroup(
            btn_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
            .addComponent(lbladd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        BG.add(btn_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 170, 150, 30));

        btn_add1.setBackground(new java.awt.Color(0, 153, 153));
        btn_add1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_add1MousePressed(evt);
            }
        });

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_plus_15px.png"))); // NOI18N

        lbladd1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbladd1.setForeground(new java.awt.Color(255, 255, 255));
        lbladd1.setText("Add Category");
        lbladd1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbladd1MousePressed(evt);
            }
        });

        javax.swing.GroupLayout btn_add1Layout = new javax.swing.GroupLayout(btn_add1);
        btn_add1.setLayout(btn_add1Layout);
        btn_add1Layout.setHorizontalGroup(
            btn_add1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_add1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbladd1, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addGap(31, 31, 31))
        );
        btn_add1Layout.setVerticalGroup(
            btn_add1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
            .addComponent(lbladd1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        BG.add(btn_add1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 170, 150, 30));

        logout.setBackground(new java.awt.Color(252, 248, 236));
        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                logoutMousePressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("LOGOUT");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
        });

        btnexit.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btnexit.setForeground(new java.awt.Color(255, 0, 51));
        btnexit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_logout_rounded_left_30px_1.png"))); // NOI18N
        btnexit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnexitMousePressed(evt);
            }
        });

        javax.swing.GroupLayout logoutLayout = new javax.swing.GroupLayout(logout);
        logout.setLayout(logoutLayout);
        logoutLayout.setHorizontalGroup(
            logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(btnexit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
        );
        logoutLayout.setVerticalGroup(
            logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnexit, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        BG.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 10, 100, 30));

        btnedit.setBackground(new java.awt.Color(0, 153, 153));
        btnedit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btneditMousePressed(evt);
            }
        });
        btnedit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        icn_btnedit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icn_btnedit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_edit_file_15px.png"))); // NOI18N
        btnedit.add(icn_btnedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 23, 30));

        lbl_btnedit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbl_btnedit.setForeground(new java.awt.Color(255, 255, 255));
        lbl_btnedit.setText("Edit");
        lbl_btnedit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbl_btneditMousePressed(evt);
            }
        });
        btnedit.add(lbl_btnedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 0, -1, 30));

        BG.add(btnedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 170, 90, 30));

        btndelete.setBackground(new java.awt.Color(255, 0, 0));
        btndelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btndeleteMousePressed(evt);
            }
        });
        btndelete.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        icn_btndelete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icn_btndelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stock_barang/images/icons8_delete_trash_15px.png"))); // NOI18N
        btndelete.add(icn_btndelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 23, 30));

        lbl_btndelete.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbl_btndelete.setForeground(new java.awt.Color(255, 255, 255));
        lbl_btndelete.setText("Delete");
        lbl_btndelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbl_btndeleteMousePressed(evt);
            }
        });
        btndelete.add(lbl_btndelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 0, -1, 30));

        BG.add(btndelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 170, 90, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menu_prodMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_prodMousePressed
        // TODO add your handling code here:
        product();
    }//GEN-LAST:event_menu_prodMousePressed

    private void menu_trxMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_trxMousePressed
        // TODO add your handling code here:
        
        transaction();
        
    }//GEN-LAST:event_menu_trxMousePressed

    private void menu_userMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_userMousePressed
        // TODO add your handling code here:
        user();
    }//GEN-LAST:event_menu_userMousePressed

    private void menu_supplierMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_supplierMousePressed
        // TODO add your handling code here:
       
        supplier();
    }//GEN-LAST:event_menu_supplierMousePressed

    private void btnexitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnexitMousePressed
        this.dispose();
        Session_Login.set_namauser("");
        Session_Login.set_username("");
        Session_Login.set_status("");
        Session_Login.set_password("");
        LOGIN flogin = new LOGIN();
        flogin.setLocationRelativeTo(null);
        flogin.setVisible(true);
    }//GEN-LAST:event_btnexitMousePressed

    private void lbladdMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbladdMousePressed
        // TODO add your handling code here:
        if(lbladd.getText().equals("Add User")){
            this.status = "Save";
            add_user fuser = new add_user(primary,status);
            fuser.setLocationRelativeTo(null);
            fuser.setVisible(true);
        }
        else if(lbladd.getText().equals("Add Product")){
            this.status = "Save";
            add_product fproduct = new add_product(primary,status);
            fproduct.setLocationRelativeTo(null);
            fproduct.setVisible(true);
        }
        else if(lbladd.getText().equals("Add Issuing")){
            add_transaction ftrx = new add_transaction();
            this.status = "Save";
            ftrx.setLocationRelativeTo(null);
            ftrx.setVisible(true);
        }
        else if(lbladd.getText().equals("Add Receiving")){
            add_receving ftrx = new add_receving();
            this.status = "Save";
            ftrx.setLocationRelativeTo(null);
            ftrx.setVisible(true);
        }
        else if(lbladd.getText().equals("Add Supplier")){
            this.status = "Save";
            add_supplier fsupp = new add_supplier(primary,status);
            fsupp.setLocationRelativeTo(null);
            fsupp.setVisible(true);
        }
        else if(lbladd.getText().equals("Add Customer")){
            this.status = "Save";
            add_customer fcust = new add_customer(primary, status);
            fcust.setLocationRelativeTo(null);
            fcust.setVisible(true);
        }
        
    }//GEN-LAST:event_lbladdMousePressed

    private void logoutMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMousePressed
        // TODO add your handling code here:
        this.dispose();
        Session_Login.set_namauser("");
        Session_Login.set_username("");
        Session_Login.set_status("");
        Session_Login.set_password("");
        LOGIN flogin = new LOGIN();
        flogin.setLocationRelativeTo(null);
        flogin.setVisible(true);
    }//GEN-LAST:event_logoutMousePressed

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        // TODO add your handling code here:
         this.dispose();
        Session_Login.set_namauser("");
        Session_Login.set_username("");
        Session_Login.set_status("");
        Session_Login.set_password("");
        LOGIN flogin = new LOGIN();
        flogin.setLocationRelativeTo(null);
        flogin.setVisible(true);
    }//GEN-LAST:event_jLabel1MousePressed

    private void menu_customerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_customerMousePressed
       
        customer();
    }//GEN-LAST:event_menu_customerMousePressed

    private void table_dashboardMouseClicked(java.awt.event.MouseEvent evt) {                                             
        // TODO add your handling code here:
        String primary = table_dashboard.getValueAt(table_dashboard.getSelectedRow(), 1).toString();
        this.primary = primary;
        this.status = "Edit";
        if(this.current_status.equals("Issuing") || this.current_status.equals("Receiving")){
            btnedit.setVisible(false);
            icn_btnedit.setVisible(false);
            lbl_btnedit.setVisible(false);
            btndelete.setVisible(true);
            lbl_btndelete.setVisible(true);
            icn_btndelete.setVisible(true);
        }
        else{
            btnedit.setVisible(true);
            icn_btnedit.setVisible(true);
            lbl_btnedit.setVisible(true);
            btndelete.setVisible(true);
            lbl_btndelete.setVisible(true);
            icn_btndelete.setVisible(true);
        }
        
    }                                        

    private void table_dashboardFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_table_dashboardFocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_table_dashboardFocusLost

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        current_tab();
        
    }//GEN-LAST:event_formWindowActivated

    private void lbl_btneditMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btneditMousePressed
        // TODO add your handling code here:
        if(current_status.equals("User")){
            int ok = JOptionPane.showConfirmDialog(this,"Edit user with username = " + primary + " ? ","Confirmation",JOptionPane.YES_NO_OPTION);
            if(ok == 0){
                add_user fuser = new add_user(primary,status);
                fuser.setLocationRelativeTo(null);
                fuser.setVisible(true);
            }
        }
        else if(current_status.equals("Customer")){
            int ok = JOptionPane.showConfirmDialog(this,"Edit customer with customer id = " + primary + " ? ","Confirmation",JOptionPane.YES_NO_OPTION);
            if(ok == 0){
                add_customer fcust = new add_customer(primary,status);
                fcust.setLocationRelativeTo(null);
                fcust.setVisible(true);
            }
        }
        else if(current_status.equals("Supplier")){
            int ok = JOptionPane.showConfirmDialog(this,"Edit supplier with supplier id = " + primary + " ? ","Confirmation",JOptionPane.YES_NO_OPTION);
            if(ok == 0){
                add_supplier fsupp = new add_supplier(primary,status);
                fsupp.setLocationRelativeTo(null);
                fsupp.setVisible(true);
            }
        }
        else if(current_status.equals("Product")){
            int ok = JOptionPane.showConfirmDialog(this,"Edit product with product id = " + primary + " ? ","Confirmation",JOptionPane.YES_NO_OPTION);
            if(ok == 0){
                add_product fprod = new add_product(primary,status);
                fprod.setLocationRelativeTo(null);
                fprod.setVisible(true);
            }
        }
    }//GEN-LAST:event_lbl_btneditMousePressed

    private void lbl_btndeleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btndeleteMousePressed
        // TODO add your handling code here:
        if(current_status.equals("User")){
            int ok = JOptionPane.showConfirmDialog(this,"Confirm delete username = " + primary + " ? ","Confirmation",JOptionPane.YES_NO_OPTION);
            if(ok == 0){
                try{
                    String sql = "delete from user where username='"+primary+"'";
                    st.executeUpdate(sql);

                    JOptionPane.showMessageDialog(this,"Data deleted !", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(SQLException e){
                    System.out.println("Koneksi Gagal" + e.toString());
                }
                user();
            }
            
        }
        else if(current_status.equals("Customer")){
            int ok = JOptionPane.showConfirmDialog(this,"Confirm delete customer id = " + primary + " ? ","Confirmation",JOptionPane.YES_NO_OPTION);
            if(ok == 0){
                try{
                    String sql = "delete from add_cust where cust_id='"+primary+"'";
                    st.executeUpdate(sql);

                    JOptionPane.showMessageDialog(this,"Data deleted !", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(SQLException e){
                    System.out.println("Koneksi Gagal" + e.toString());
                }
                customer();
            }
            
        }
        else if(current_status.equals("Customer")){
            int ok = JOptionPane.showConfirmDialog(this,"Confirm delete customer id = " + primary + " ? ","Confirmation",JOptionPane.YES_NO_OPTION);
            if(ok == 0){
                try{
                    String sql = "delete from add_cust where cust_id='"+primary+"'";
                    st.executeUpdate(sql);

                    JOptionPane.showMessageDialog(this,"Data deleted !", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(SQLException e){
                    System.out.println("Koneksi Gagal" + e.toString());
                }
                customer();
            }
            
        }
        else if(current_status.equals("Product")){
            int ok = JOptionPane.showConfirmDialog(this,"Confirm delete product id = " + primary + " ? ","Confirmation",JOptionPane.YES_NO_OPTION);
            if(ok == 0){
                try{
                    String sql = "delete from product where product_id='"+primary+"'";
                    st.executeUpdate(sql);

                    JOptionPane.showMessageDialog(this,"Data deleted !", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(SQLException e){
                    System.out.println("Koneksi Gagal" + e.toString());
                }
                product();
            }
        }
        else if(current_status.equals("Issuing")){
            int ok = JOptionPane.showConfirmDialog(this,"Confirm delete transaction id = " + primary + " ? ","Confirmation",JOptionPane.YES_NO_OPTION);
            if(ok == 0){
                try{
                    String sql = "delete from trans_out where notrans='"+primary+"'";
                    st.executeUpdate(sql);
                    
                    Statement state = conn.createStatement();
                    String sql1 = "delete from trans_out_detail where notrans='"+primary+"'";
                    state.executeUpdate(sql1);

                    JOptionPane.showMessageDialog(this,"Data deleted !", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(SQLException e){
                    System.out.println("Koneksi Gagal" + e.toString());
                }
                transaction();
            }
        }
        else if(current_status.equals("Receiving")){
            int ok = JOptionPane.showConfirmDialog(this,"Confirm delete transaction id = " + primary + " ? ","Confirmation",JOptionPane.YES_NO_OPTION);
            if(ok == 0){
                try{
                    String sql = "delete from trans_in where notrans='"+primary+"'";
                    st.executeUpdate(sql);
                    
                    Statement state = conn.createStatement();
                    String sql1 = "delete from trans_in_detail where notrans='"+primary+"'";
                    state.executeUpdate(sql1);

                    JOptionPane.showMessageDialog(this,"Data deleted !", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(SQLException e){
                    System.out.println("Koneksi Gagal" + e.toString());
                }
                transaction_in();
            }
        }
        btnedit.setVisible(false);
        btndelete.setVisible(false);
    }//GEN-LAST:event_lbl_btndeleteMousePressed

    private void btneditMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btneditMousePressed
        // TODO add your handling code here:
        if(current_status.equals("User")){
            int ok = JOptionPane.showConfirmDialog(this,"Edit user with username = " + primary + " ? ","Confirmation",JOptionPane.YES_NO_OPTION);
            if(ok == 0){
                this.dispose();
                add_user fuser = new add_user(primary,status);
                fuser.setLocationRelativeTo(null);
                fuser.setVisible(true);
            }
        }
        else if(current_status.equals("Customer")){
            int ok = JOptionPane.showConfirmDialog(this,"Edit customer with customer id = " + primary + " ? ","Confirmation",JOptionPane.YES_NO_OPTION);
            if(ok == 0){
                this.dispose();
                add_customer fcust = new add_customer(primary,status);
                fcust.setLocationRelativeTo(null);
                fcust.setVisible(true);
            }
        }
        else if(current_status.equals("Supplier")){
            int ok = JOptionPane.showConfirmDialog(this,"Edit supplier with supplier id = " + primary + " ? ","Confirmation",JOptionPane.YES_NO_OPTION);
            if(ok == 0){
                this.dispose();
                add_supplier fsupp = new add_supplier(primary,status);
                fsupp.setLocationRelativeTo(null);
                fsupp.setVisible(true);
            }
        }
    }//GEN-LAST:event_btneditMousePressed

    private void btndeleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndeleteMousePressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btndeleteMousePressed

    private void menu_inMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_inMousePressed
        // TODO add your handling code here:
        transaction_in();
        
    }//GEN-LAST:event_menu_inMousePressed

    private void lbladd1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbladd1MousePressed
        // TODO add your handling code here:
       if(lbladd1.getText().equals("Add Category")){
            this.status = "Save";
            cat_mene fcat = new cat_mene();
            fcat.setLocationRelativeTo(null);
            fcat.setVisible(true);
        }
        
    }//GEN-LAST:event_lbladd1MousePressed

    private void btn_add1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_add1MousePressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btn_add1MousePressed

    private void menu_reportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_reportMousePressed
        // TODO add your handling code here:
        report freport = new report();
        freport.setLocationRelativeTo(null);
        freport.setVisible(true);
    }//GEN-LAST:event_menu_reportMousePressed

    
    void setColor(JPanel panel){
        panel.setBackground(new Color(69,98,104));
    }
    void resetColor(JPanel panel){
        panel.setBackground(new Color(118,153,160));
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
            java.util.logging.Logger.getLogger(DASBOARD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DASBOARD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DASBOARD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DASBOARD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DASBOARD().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BG;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel Profile;
    private javax.swing.JPanel Sidepanel;
    private javax.swing.JPanel btn_add;
    private javax.swing.JPanel btn_add1;
    private javax.swing.JPanel btndelete;
    private javax.swing.JPanel btnedit;
    private javax.swing.JLabel btnexit;
    private javax.swing.JLabel icn_btndelete;
    private javax.swing.JLabel icn_btnedit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lbl_btndelete;
    private javax.swing.JLabel lbl_btnedit;
    private javax.swing.JLabel lbladd;
    private javax.swing.JLabel lbladd1;
    private javax.swing.JLabel lblcustomer;
    private javax.swing.JLabel lblcustomer2;
    private javax.swing.JLabel lbldirect;
    private javax.swing.JLabel lblheader;
    private javax.swing.JLabel lblproduct;
    private javax.swing.JLabel lblstatus;
    private javax.swing.JLabel lblsupplier;
    private javax.swing.JLabel lbltrans;
    private javax.swing.JLabel lbltrans1;
    private javax.swing.JLabel lbluser;
    private javax.swing.JLabel lblusername;
    private javax.swing.JPanel logout;
    private javax.swing.JPanel menu_customer;
    private javax.swing.JPanel menu_in;
    private javax.swing.JPanel menu_prod;
    private javax.swing.JPanel menu_report;
    private javax.swing.JPanel menu_supplier;
    private javax.swing.JPanel menu_trx;
    private javax.swing.JPanel menu_user;
    private javax.swing.JTable table_dashboard;
    // End of variables declaration//GEN-END:variables
  }
