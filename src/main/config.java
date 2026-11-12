
package main;

import java.sql.*;

public class config {
    private static Connection mysqlconfig;

    public static Connection configDB() throws SQLException{
        try{
            String url = "jdbc:mysql://http://localhost/phpmyadmin/kasir/useSSL=false";
            String user = "root";
            String pass = "";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch(Exception e){
            System.out.println (e);
        }
        return mysqlconfig;
    }
}

