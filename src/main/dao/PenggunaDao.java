// [TAMBAHAN] - PenggunaDao.java
// Data Access Object untuk autentikasi dan manajemen Pengguna (Admin dan Kasir).
// Menerapkan Polymorphism dengan mengembalikan objek turunan Pengguna (Admin atau Kasir).
package main.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.model.Admin;
import main.model.InterfaceDao;
import main.model.Kasir;
import main.model.Pengguna;

/**
 * Class DAO untuk autentikasi login dan manajemen akun pengguna.
 * Mengimplementasikan InterfaceDao<Pengguna>.
 *
 * Menerapkan pilar POLYMORPHISM:
 * Method login() mengembalikan tipe referensi superclass 'Pengguna',
 * yang secara dinamis berisi objek konkret 'Admin' atau 'Kasir'.
 *
 * @author Tambahan OOP
 */
public class PenggunaDao implements InterfaceDao<Pengguna> {

    // [TAMBAHAN] Helper koneksi mandiri aman
    private Connection bukaKoneksi() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/kasir", "root", "");
    }

    /**
     * [TAMBAHAN] Melakukan autentikasi pengguna secara aman dengan PreparedStatement.
     * Mengecek ke tabel 'admin', jika tidak ada maka mengecek ke tabel 'petugaskasir'.
     *
     * @param username username masukan
     * @param password password masukan
     * @return Objek Admin atau Kasir jika berhasil, null jika gagal
     * @throws SQLException jika ada kesalahan koneksi
     */
    public Pengguna login(String username, String password) throws SQLException {
        // 1. Cek di tabel admin terlebih dahulu
        String sqlAdmin = "SELECT * FROM admin WHERE username = ? AND password = ? LIMIT 1";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sqlAdmin)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Admin(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                    );
                }
            }
        }

        // 2. Jika bukan admin, cek di tabel petugaskasir
        String sqlKasir = "SELECT * FROM petugaskasir WHERE username = ? AND password = ? LIMIT 1";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sqlKasir)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Kasir(
                        rs.getInt("id_petugas"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                    );
                }
            }
        }

        return null; // Pengguna tidak ditemukan atau password salah
    }

    @Override
    public void simpan(Pengguna pengguna) throws SQLException {
        // [TAMBAHAN] Polymorphism: cek jenis instance untuk menentukan tabel tujuan
        if (pengguna instanceof Admin) {
            simpanAdmin((Admin) pengguna);
        } else if (pengguna instanceof Kasir) {
            simpanKasir((Kasir) pengguna);
        } else {
            throw new IllegalArgumentException("Peran pengguna tidak dikenali!");
        }
    }

    /**
     * [TAMBAHAN] Menyimpan data Admin baru ke tabel 'admin'
     */
    public void simpanAdmin(Admin admin) throws SQLException {
        String sql = "INSERT INTO admin (id, username, password, email) VALUES (?, ?, ?, ?)";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, admin.getId());
            ps.setString(2, admin.getUsername());
            ps.setString(3, admin.getPassword());
            ps.setString(4, admin.getEmail());
            ps.executeUpdate();
        }
    }

    /**
     * [TAMBAHAN] Menyimpan data Kasir baru ke tabel 'petugaskasir'
     */
    public void simpanKasir(Kasir kasir) throws SQLException {
        String sql = "INSERT INTO petugaskasir (id_petugas, username, password, email) VALUES (?, ?, ?, ?)";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, kasir.getId());
            ps.setString(2, kasir.getUsername());
            ps.setString(3, kasir.getPassword());
            ps.setString(4, kasir.getEmail());
            ps.executeUpdate();
        }
    }

    @Override
    public void ubah(Pengguna pengguna) throws SQLException {
        if (pengguna instanceof Admin) {
            String sql = "UPDATE admin SET username = ?, password = ?, email = ? WHERE id = ?";
            try (Connection con = bukaKoneksi();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, pengguna.getUsername());
                ps.setString(2, pengguna.getPassword());
                ps.setString(3, pengguna.getEmail());
                ps.setInt(4, pengguna.getId());
                ps.executeUpdate();
            }
        } else if (pengguna instanceof Kasir) {
            String sql = "UPDATE petugaskasir SET username = ?, password = ?, email = ? WHERE id_petugas = ?";
            try (Connection con = bukaKoneksi();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, pengguna.getUsername());
                ps.setString(2, pengguna.getPassword());
                ps.setString(3, pengguna.getEmail());
                ps.setInt(4, pengguna.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void hapus(int id) throws SQLException {
        // Default hapus dari kedua tabel jika ID cocok
        hapusAdmin(id);
        hapusKasir(id);
    }

    public void hapusAdmin(int id) throws SQLException {
        String sql = "DELETE FROM admin WHERE id = ?";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void hapusKasir(int id) throws SQLException {
        String sql = "DELETE FROM petugaskasir WHERE id_petugas = ?";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Pengguna> ambilSemua() throws SQLException {
        List<Pengguna> list = new ArrayList<>();
        // Ambil admin
        String sqlAdmin = "SELECT * FROM admin";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sqlAdmin);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Admin(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email")
                ));
            }
        }
        // Ambil kasir
        String sqlKasir = "SELECT * FROM petugaskasir";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sqlKasir);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Kasir(
                    rs.getInt("id_petugas"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email")
                ));
            }
        }
        return list;
    }

    @Override
    public Pengguna cariById(int id) throws SQLException {
        // Cek admin dulu
        String sqlAdmin = "SELECT * FROM admin WHERE id = ? LIMIT 1";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sqlAdmin)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Admin(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                    );
                }
            }
        }
        // Cek kasir
        String sqlKasir = "SELECT * FROM petugaskasir WHERE id_petugas = ? LIMIT 1";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sqlKasir)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Kasir(
                        rs.getInt("id_petugas"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                    );
                }
            }
        }
        return null;
    }
}

