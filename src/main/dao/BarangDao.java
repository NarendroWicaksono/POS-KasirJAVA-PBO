// [TAMBAHAN] - BarangDao.java
// Data Access Object untuk entitas Barang.
// Menerapkan InterfaceDao<Barang> dengan PreparedStatement aman dari SQL Injection.
package main.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.model.Barang;
import main.model.InterfaceDao;

/**
 * Class DAO untuk operasi database pada tabel 'databarang'.
 * Mengimplementasikan InterfaceDao<Barang>.
 * Semua query memakai PreparedStatement dengan parameter "?" untuk mencegah SQL Injection.
 *
 * Menerapkan pilar POLYMORPHISM (Method Overloading nyata):
 * Memiliki beberapa method dengan nama persis 'cari' tetapi tipe parameter berbeda:
 * 1. cari(long kodeBarang)  -> mencari 1 barang berdasarkan kode unik
 * 2. cari(String namaBarang) -> mencari 1 barang berdasarkan nama tepat
 * 3. cari(int minStok, int maxStok) -> mencari daftar barang berdasarkan rentang stok
 *
 * @author Tambahan OOP
 */
public class BarangDao implements InterfaceDao<Barang> {

    // [TAMBAHAN] Helper koneksi mandiri aman sesuai konfigurasi database di koneksi.java
    private Connection bukaKoneksi() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/kasir", "root", "");
    }

    @Override
    public void simpan(Barang barang) throws SQLException {
        String sql = "INSERT INTO databarang (code_barang, nama_barang, harga_beli, harga_jual, stok_barang) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, barang.getKodeBarang());
            ps.setString(2, barang.getNamaBarang());
            ps.setInt(3, barang.getHargaBeli());
            ps.setInt(4, barang.getHargaJual());
            ps.setInt(5, barang.getStokBarang());
            ps.executeUpdate();
        }
    }

    @Override
    public void ubah(Barang barang) throws SQLException {
        String sql = "UPDATE databarang SET nama_barang = ?, harga_beli = ?, harga_jual = ?, stok_barang = ? WHERE code_barang = ?";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, barang.getNamaBarang());
            ps.setInt(2, barang.getHargaBeli());
            ps.setInt(3, barang.getHargaJual());
            ps.setInt(4, barang.getStokBarang());
            ps.setLong(5, barang.getKodeBarang());
            ps.executeUpdate();
        }
    }

    @Override
    public void hapus(int id) throws SQLException {
        hapus((long) id);
    }

    /**
     * [TAMBAHAN] Method Overloading untuk hapus:
     * hapus(int id) dan hapus(long kodeBarang) memiliki nama sama tapi tipe parameter berbeda.
     *
     * @param kodeBarang kode unik barang bertipe long
     * @throws SQLException jika terjadi error database
     */
    public void hapus(long kodeBarang) throws SQLException {
        String sql = "DELETE FROM databarang WHERE code_barang = ?";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, kodeBarang);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Barang> ambilSemua() throws SQLException {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM databarang";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Barang b = new Barang(
                    rs.getLong("code_barang"),
                    rs.getString("nama_barang"),
                    rs.getInt("harga_beli"),
                    rs.getInt("harga_jual"),
                    rs.getInt("stok_barang")
                );
                list.add(b);
            }
        }
        return list;
    }

    @Override
    public Barang cariById(int id) throws SQLException {
        return cari((long) id);
    }

    // ── PENERAPAN METHOD OVERLOADING NYATA (Polymorphism) ─────────────────────

    /**
     * [TAMBAHAN] Method Overloading 1:
     * Mencari barang berdasarkan KODE unik (tipe data long).
     *
     * @param kodeBarang kode unik barang
     * @return objek Barang atau null jika tidak ditemukan
     * @throws SQLException jika ada kesalahan query
     */
    public Barang cari(long kodeBarang) throws SQLException {
        String sql = "SELECT * FROM databarang WHERE code_barang = ? LIMIT 1";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, kodeBarang);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Barang(
                        rs.getLong("code_barang"),
                        rs.getString("nama_barang"),
                        rs.getInt("harga_beli"),
                        rs.getInt("harga_jual"),
                        rs.getInt("stok_barang")
                    );
                }
            }
        }
        return null;
    }

    /**
     * [TAMBAHAN] Method Overloading 2:
     * Mencari barang berdasarkan NAMA barang (tipe data String).
     * Nama method sama ("cari"), tetapi parameternya String.
     *
     * @param namaBarang nama barang yang dicari
     * @return objek Barang atau null jika tidak ditemukan
     * @throws SQLException jika ada kesalahan query
     */
    public Barang cari(String namaBarang) throws SQLException {
        String sql = "SELECT * FROM databarang WHERE nama_barang = ? LIMIT 1";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, namaBarang);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Barang(
                        rs.getLong("code_barang"),
                        rs.getString("nama_barang"),
                        rs.getInt("harga_beli"),
                        rs.getInt("harga_jual"),
                        rs.getInt("stok_barang")
                    );
                }
            }
        }
        return null;
    }

    /**
     * [TAMBAHAN] Method Overloading 3:
     * Mencari daftar barang berdasarkan rentang stok minimum dan maksimum (dua parameter int).
     *
     * @param minStok batas bawah stok
     * @param maxStok batas atas stok
     * @return List barang yang berada dalam rentang stok tersebut
     * @throws SQLException jika ada kesalahan query
     */
    public List<Barang> cari(int minStok, int maxStok) throws SQLException {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM databarang WHERE stok_barang BETWEEN ? AND ?";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, minStok);
            ps.setInt(2, maxStok);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Barang(
                        rs.getLong("code_barang"),
                        rs.getString("nama_barang"),
                        rs.getInt("harga_beli"),
                        rs.getInt("harga_jual"),
                        rs.getInt("stok_barang")
                    ));
                }
            }
        }
        return list;
    }

    // ── Logika Transaksional Database ────────────────────────────────────────

    /**
     * [TAMBAHAN] Mengurangi stok barang secara langsung di database.
     * Digunakan dalam blok transaksi atomik pada TransaksiDao.
     *
     * @param con        koneksi aktif dalam transaksi
     * @param kodeBarang kode barang
     * @param jumlah     jumlah pengurangan
     * @throws SQLException jika gagal update atau stok tidak cukup
     */
    public void kurangiStok(Connection con, long kodeBarang, int jumlah) throws SQLException {
        String sql = "UPDATE databarang SET stok_barang = stok_barang - ? WHERE code_barang = ? AND stok_barang >= ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, jumlah);
            ps.setLong(2, kodeBarang);
            ps.setInt(3, jumlah);
            int barisTerpengaruh = ps.executeUpdate();
            if (barisTerpengaruh == 0) {
                throw new SQLException("Gagal memproses transaksi: Stok barang dengan kode " + kodeBarang + " tidak mencukupi!");
            }
        }
    }
}
