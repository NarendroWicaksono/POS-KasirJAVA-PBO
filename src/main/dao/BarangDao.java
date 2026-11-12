
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

public class BarangDao implements InterfaceDao<Barang> {

    private Connection bukaKoneksi() throws SQLException {
        return main.koneksi.buka();
    }

    private static final String SELECT_JOIN =
        "SELECT b.code_barang, b.nama_barang, b.harga_beli, b.harga_jual, b.stok_barang, b.id_supplier, "
        + "s.id AS sp_id, s.nama AS sp_nama, s.email AS sp_email, s.alamat AS sp_alamat, s.npwp AS sp_npwp, s.noRek AS sp_noRek "
        + "FROM databarang b LEFT JOIN supplier s ON b.id_supplier = s.id";

    private Barang mapRow(ResultSet rs) throws SQLException {
        Barang b = new Barang(
            rs.getLong("code_barang"),
            rs.getString("nama_barang"),
            rs.getInt("harga_beli"),
            rs.getInt("harga_jual"),
            rs.getInt("stok_barang")
        );
        try {
            int spId = rs.getInt("sp_id");
            if (!rs.wasNull() && spId > 0) {
                main.model.Supplier s = new main.model.Supplier(
                    spId,
                    rs.getString("sp_nama"),
                    rs.getString("sp_email"),
                    rs.getString("sp_alamat"),
                    rs.getString("sp_npwp"),
                    rs.getString("sp_noRek")
                );
                b.setSupplier(s);
            }
        } catch (SQLException ignored) {

        }
        return b;
    }

    @Override
    public void simpan(Barang barang) throws SQLException {

        String sql = "INSERT INTO databarang (code_barang, nama_barang, harga_beli, harga_jual, stok_barang, id_supplier) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, barang.getKodeBarang());
            ps.setString(2, barang.getNamaBarang());
            ps.setInt(3, barang.getHargaBeli());
            ps.setInt(4, barang.getHargaJual());
            ps.setInt(5, barang.getStokBarang());
            if (barang.getSupplier() != null && barang.getSupplier().getId() > 0) {
                ps.setInt(6, barang.getSupplier().getId());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }
            ps.executeUpdate();
        }
    }

    @Override
    public void ubah(Barang barang) throws SQLException {

        String sql = "UPDATE databarang SET nama_barang = ?, harga_beli = ?, harga_jual = ?, stok_barang = ?, id_supplier = ? WHERE code_barang = ?";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, barang.getNamaBarang());
            ps.setInt(2, barang.getHargaBeli());
            ps.setInt(3, barang.getHargaJual());
            ps.setInt(4, barang.getStokBarang());
            if (barang.getSupplier() != null && barang.getSupplier().getId() > 0) {
                ps.setInt(5, barang.getSupplier().getId());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            ps.setLong(6, barang.getKodeBarang());
            ps.executeUpdate();
        }
    }

    @Override
    public void hapus(int id) throws SQLException {
        hapus((long) id);
    }

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
        String sql = SELECT_JOIN + " ORDER BY b.nama_barang ASC";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    @Override
    public Barang cariById(int id) throws SQLException {
        return cari((long) id);
    }

    public Barang cari(long kodeBarang) throws SQLException {
        String sql = SELECT_JOIN + " WHERE b.code_barang = ? LIMIT 1";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, kodeBarang);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public Barang cari(String namaBarang) throws SQLException {
        String sql = SELECT_JOIN + " WHERE b.nama_barang = ? LIMIT 1";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, namaBarang);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public List<Barang> cari(int minStok, int maxStok) throws SQLException {
        List<Barang> list = new ArrayList<>();
        String sql = SELECT_JOIN + " WHERE b.stok_barang BETWEEN ? AND ? ORDER BY b.stok_barang ASC";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, minStok);
            ps.setInt(2, maxStok);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    public int hitungBarangBySupplier(int idSupplier) throws SQLException {
        String sql = "SELECT COUNT(*) FROM databarang WHERE id_supplier = ?";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idSupplier);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

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

