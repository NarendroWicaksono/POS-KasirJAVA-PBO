/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * Class koneksi database MySQL terpusat untuk aplikasi POS Kasir.
 * Mengelola konfigurasi JDBC URL, username, dan password di satu tempat.
 *
 * @author ACER & [TAMBAHAN] Tim PBO
 */
public class koneksi {

    // [TAMBAHAN] Konfigurasi koneksi terpusat - URL, user, dan password hanya di sini
    private static final String URL = "jdbc:mysql://localhost:3306/kasir";
    private static final String USER = "root";
    private static final String PASS = "";

    Connection con;
    Statement stm;
    
    public static String username;

    /**
     * [TAMBAHAN] Method statis untuk membuka koneksi baru ke database kasir.
     * Digunakan oleh semua DAO dan komponen aplikasi.
     *
     * @return Connection objek koneksi aktif
     * @throws SQLException jika koneksi gagal dibuka
     */
    public static Connection buka() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    /**
     * Method konfigurasi koneksi warisan (legacy) untuk backward compatibility.
     */
    public void config(){
        try{
            con = buka();
            stm = con.createStatement();
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Koneksi Gagal: " + e.getMessage());
        }
    }
}
