/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock_barang;

/**
 *
 * @author ROG
 */
public class Session_Login {
    private static String username;
    private static String nama_user;
    private static String status;
    private static String password;
    
    public static String get_username(){
        return username;
    }
    
    public static String get_namauser(){
        return nama_user;
    }
    
    public static String get_password(){
        return password;
    }
    
    public static String get_status(){
        return status;
    }
    
    public static void set_username(String username){
        Session_Login.username = username;
    }
    
    public static void set_namauser(String nama_user){
        Session_Login.nama_user = nama_user;
    }
    
    public static void set_password(String password){
        Session_Login.password = password;
    }
    
    public static void set_status(String status){
        Session_Login.status = status;
    }
}
