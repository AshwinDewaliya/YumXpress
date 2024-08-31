
package yumxpress.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

    public class DBConnection {
    private static Connection conn;
    static{
        try{
            conn=DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xe", "yumxpress", "foodie");
            JOptionPane.showMessageDialog(null,"Connection opened");
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Cannot open the connection");
            ex.printStackTrace();
//            System.exit(0);
        }
    }
    public static Connection getConnection(){
        return conn;
    }
    public static void closeConnection(){
        try{
            conn.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Cannot close the connection");
            ex.printStackTrace();
        }
    }
}

