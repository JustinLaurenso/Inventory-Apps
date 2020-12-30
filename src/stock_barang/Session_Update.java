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
public class Session_Update {
    private static String update_status = "default";

    
    public static String get_update_status(){
        return update_status;
    }
    public static void update_status(String update_status){
        Session_Update.update_status = update_status;
    }
    
}
