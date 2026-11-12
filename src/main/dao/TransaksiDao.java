
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

public class TransaksiDao implements InterfaceDao<Transaksi> {

    private final BarangDao barangDao = new BarangDao();

    private Connection bukaKoneksi() throws SQLException {
        return main.koneksi.buka();
    }

    @Override
    public void simpan(Transaksi tr) throws SQLException {
        Connection con = null;
        try {
            con = bukaKoneksi();
            con.setAutoCommit(false);

            String sqlHeader = "INSERT INTO transaksi (no_transaksi, id_member, petugas_kasir, total, bayar, kembalian, waktu_transaksi) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psHeader = con.prepareStatement(sqlHeader)) {
                psHeader.setString(1, tr.getNoTransaksi());
                if (tr.getMember() != null && tr.getMember().getIdMember() > 0) {
                    psHeader.setInt(2, tr.getMember().getIdMember());
                } else {
                    psHeader.setNull(2, java.sql.Types.INTEGER);
                }
                psHeader.setString(3, tr.getPetugasKasir());
                psHeader.setLong(4, tr.getTotal());
                psHeader.setLong(5, tr.getJumlahBayar());
                psHeader.setLong(6, tr.getKembalian());
                psHeader.setString(7, tr.getWaktuTransaksi());
                psHeader.executeUpdate();
            }

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

                    barangDao.kurangiStok(con, d.getKodeBarang(), d.getQty());
                }
            }

            if (tr.getMember() != null && tr.getMember().getIdMember() > 0) {
                int poinDidapat = tr.getPoinDidapat();
                if (poinDidapat > 0) {
                    String sqlPoin = "UPDATE member SET poin = poin + ? WHERE id_member = ?";
                    try (PreparedStatement psPoin = con.prepareStatement(sqlPoin)) {
                        psPoin.setInt(1, poinDidapat);
                        psPoin.setInt(2, tr.getMember().getIdMember());
                        psPoin.executeUpdate();
                    }
                }
            }

            con.commit();

            if (tr.getMember() != null && tr.getMember().getIdMember() > 0) {
                tr.getMember().tambahPoin(tr.getTotal());
            }
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
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

        throw new UnsupportedOperationException("Transaksi penjualan yang sudah dicatat tidak dapat diubah!");
    }

    @Override
    public void hapus(int id) throws SQLException {
        throw new UnsupportedOperationException("Gunakan hapusByNoTransaksi(String)!");
    }

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

    private long ambilLongAman(ResultSet rs, String kolom) {
        try {
            Object obj = rs.getObject(kolom);
            if (obj != null) {
                return rs.getLong(kolom);
            }
        } catch (SQLException ignored) {

        }
        return 0;
    }

    public List<Transaksi> ambilRiwayatPenjualan() throws SQLException {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT t.*, m.username AS member_nama, m.no_tlp AS member_tlp, m.poin AS member_poin "
                   + "FROM transaksi t "
                   + "LEFT JOIN member m ON t.id_member = m.id_member "
                   + "ORDER BY t.waktu_transaksi DESC";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Transaksi t = new Transaksi(
                    rs.getString("no_transaksi"),
                    rs.getString("petugas_kasir"),
                    rs.getString("waktu_transaksi")
                );
                t.setTotal(rs.getLong("total"));
                long bayar = ambilLongAman(rs, "bayar");
                long kembalian = ambilLongAman(rs, "kembalian");
                t.setJumlahBayar(bayar);
                t.setKembalian(kembalian);

                int idMember = 0;
                try {
                    idMember = rs.getInt("id_member");
                } catch (SQLException ignored) {}
                if (idMember > 0) {
                    String namaMember = rs.getString("member_nama");
                    String noTlp = rs.getString("member_tlp");
                    int poin = rs.getInt("member_poin");
                    t.setMember(new main.model.Member(idMember, namaMember != null ? namaMember : "-", noTlp, poin));
                }

                list.add(t);
            }
        }
        return list;
    }

    public Transaksi cariByNoTransaksi(String noTransaksi) throws SQLException {
        String sql = "SELECT t.*, m.username AS member_nama, m.no_tlp AS member_tlp, m.poin AS member_poin "
                   + "FROM transaksi t "
                   + "LEFT JOIN member m ON t.id_member = m.id_member "
                   + "WHERE t.no_transaksi = ? LIMIT 1";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, noTransaksi);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Transaksi t = new Transaksi(
                        rs.getString("no_transaksi"),
                        rs.getString("petugas_kasir"),
                        rs.getString("waktu_transaksi")
                    );
                    t.setTotal(rs.getLong("total"));
                    long bayar = ambilLongAman(rs, "bayar");
                    long kembalian = ambilLongAman(rs, "kembalian");
                    t.setJumlahBayar(bayar);
                    t.setKembalian(kembalian);

                    int idMember = 0;
                    try {
                        idMember = rs.getInt("id_member");
                    } catch (SQLException ignored) {}
                    if (idMember > 0) {
                        String namaMember = rs.getString("member_nama");
                        String noTlp = rs.getString("member_tlp");
                        int poin = rs.getInt("member_poin");
                        t.setMember(new main.model.Member(idMember, namaMember != null ? namaMember : "-", noTlp, poin));
                    }

                    return t;
                }
            }
        }
        return null;
    }

    public String ambilNoTransaksiTerakhir() throws SQLException {
        String sql = "SELECT no_transaksi FROM transaksi ORDER BY no_transaksi DESC LIMIT 1";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("no_transaksi");
            }
        }
        return null;
    }

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

