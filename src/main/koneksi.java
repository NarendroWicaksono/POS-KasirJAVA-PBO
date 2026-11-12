
package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class koneksi {

    private static final String URL = "jdbc:mysql://localhost:3306/kasir";
    private static final String USER = "root";
    private static final String PASS = "";

    Connection con;
    Statement stm;

    public static String username;

    public static Connection buka() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public void config(){
        try{
            con = buka();
            stm = con.createStatement();
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Koneksi Gagal: " + e.getMessage());
        }
    }
}

