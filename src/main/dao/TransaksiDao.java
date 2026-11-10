// [TAMBAHAN] - TransaksiDao.java
// Data Access Object untuk Transaksi dan DetailTransaksi.
// Menerapkan ACID transaction (setAutoCommit(false), commit, rollback)
// dan PreparedStatement aman dari SQL Injection.
package main.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.model.DetailTransaksi;
import main.model.InterfaceDao;
import main.model.Transaksi;

/**
 * Class DAO untuk mengelola transaksi penjualan.
 * Mengimplementasikan InterfaceDao<Transaksi>.
 *
 * Menerapkan Database Transaction atomik:
 * Header transaksi, detail item, dan pengurangan stok barang dieksekusi
 * dalam satu blok koneksi yang sama. Jika ada kegagalan, semua di-rollback.
 *
 * @author Tambahan OOP
 */
public class TransaksiDao implements InterfaceDao<Transaksi> {

    private final BarangDao barangDao = new BarangDao();

    // [TAMBAHAN] Helper koneksi mandiri aman
    private Connection bukaKoneksi() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/kasir", "root", "");
    }

    /**
     * [TAMBAHAN] Menyimpan transaksi secara atomik (ACID):
     * 1. Insert ke tabel transaksi (header)
     * 2. Insert tiap item ke tabel detail_transaksi
     * 3. Update / kurangi stok_barang di tabel databarang
     * 4. Jika semua berhasil -> commit()
     * 5. Jika ada kesalahan/stok habis -> rollback()
     */
    @Override
    public void simpan(Transaksi tr) throws SQLException {
        Connection con = null;
        try {
            con = bukaKoneksi();
            con.setAutoCommit(false); // Memulai transaksi database

            // 1. Simpan header transaksi
            String sqlHeader = "INSERT INTO transaksi (no_transaksi, petugas_kasir, total, waktu_transaksi) VALUES (?, ?, ?, ?)";
            try (PreparedStatement psHeader = con.prepareStatement(sqlHeader)) {
                psHeader.setString(1, tr.getNoTransaksi());
                psHeader.setString(2, tr.getPetugasKasir());
                psHeader.setLong(3, tr.getTotal());
                psHeader.setString(4, tr.getWaktuTransaksi());
                psHeader.executeUpdate();
            }

            // 2. Simpan setiap detail item & kurangi stok
            String sqlDetail = "INSERT INTO detail_transaksi (no_transaksi, kode_barang, nama_barang, harga, qty, jumlah) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psDetail = con.prepareStatement(sqlDetail)) {
                for (DetailTransaksi d : tr.getListDetail()) {
                    psDetail.setString(1, tr.getNoTransaksi());
                    psDetail.setLong(2, d.getKodeBarang());
                    psDetail.setString(3, d.getNamaBarang());
                    psDetail.setInt(4, d.getHarga());
                    psDetail.setInt(5, d.getQty());
                    psDetail.setInt(6, d.getJumlah());
                    psDetail.executeUpdate();

                    // 3. Kurangi stok barang secara aman
                    barangDao.kurangiStok(con, d.getKodeBarang(), d.getQty());
                }
            }

            // 4. Komit seluruh perubahan
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Batalkan semua perubahan jika terjadi error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e; // Lemparkan error agar ditangani oleh pemanggil
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void ubah(Transaksi entitas) throws SQLException {
        // Transaksi yang sudah tersimpan bersifat final untuk integritas pembukuan
        throw new UnsupportedOperationException("Transaksi penjualan yang sudah dicatat tidak dapat diubah!");
    }

    @Override
    public void hapus(int id) throws SQLException {
        throw new UnsupportedOperationException("Gunakan hapusByNoTransaksi(String)!");
    }

    /**
     * [TAMBAHAN] Menghapus transaksi berdasarkan nomor transaksi.
     * @param noTransaksi nomor faktur transaksi
     * @throws SQLException jika ada kendala database
     */
    public void hapusByNoTransaksi(String noTransaksi) throws SQLException {
        String sql = "DELETE FROM transaksi WHERE no_transaksi = ?";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, noTransaksi);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Transaksi> ambilSemua() throws SQLException {
        return ambilRiwayatPenjualan();
    }

    @Override
    public Transaksi cariById(int id) throws SQLException {
        return null;
    }

    /**
     * [TAMBAHAN] Mengambil seluruh riwayat penjualan untuk ditampilkan di laporan.
     * Diurutkan dari transaksi terbaru.
     *
     * @return List transaksi
     * @throws SQLException jika ada error database
     */
    public List<Transaksi> ambilRiwayatPenjualan() throws SQLException {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi ORDER BY waktu_transaksi DESC";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Transaksi t = new Transaksi(
                    rs.getString("no_transaksi"),
                    rs.getString("petugas_kasir"),
                    rs.getString("waktu_transaksi")
                );
                t.setJumlahBayar(rs.getLong("total"));
                list.add(t);
            }
        }
        return list;
    }

    /**
     * [TAMBAHAN] Mengambil rincian detail barang dari satu nomor transaksi.
     *
     * @param noTransaksi nomor transaksi yang ingin dicek
     * @return List item detail transaksi
     * @throws SQLException jika ada kesalahan query
     */
    public List<DetailTransaksi> ambilDetail(String noTransaksi) throws SQLException {
        List<DetailTransaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM detail_transaksi WHERE no_transaksi = ?";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, noTransaksi);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetailTransaksi d = new DetailTransaksi(
                        rs.getString("no_transaksi"),
                        rs.getLong("kode_barang"),
                        rs.getString("nama_barang"),
                        rs.getInt("harga"),
                        rs.getInt("qty")
                    );
                    d.setIdDetail(rs.getInt("id_detail"));
                    list.add(d);
                }
            }
        }
        return list;
    }
}

