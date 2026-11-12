
package main.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.model.InterfaceDao;
import main.model.Supplier;

public class SupplierDao implements InterfaceDao<Supplier> {

    private Connection bukaKoneksi() throws SQLException {
        return main.koneksi.buka();
    }

    @Override
    public void simpan(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier (nama, email, alamat, npwp, noRek) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, supplier.getNama());
            ps.setString(2, supplier.getEmail());
            ps.setString(3, supplier.getAlamat());
            ps.setString(4, supplier.getNpwp());
            ps.setString(5, supplier.getNoRek());
            ps.executeUpdate();
        }
    }

    @Override
    public void ubah(Supplier supplier) throws SQLException {
        String sql = "UPDATE supplier SET nama = ?, email = ?, alamat = ?, npwp = ?, noRek = ? WHERE id = ?";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, supplier.getNama());
            ps.setString(2, supplier.getEmail());
            ps.setString(3, supplier.getAlamat());
            ps.setString(4, supplier.getNpwp());
            ps.setString(5, supplier.getNoRek());
            ps.setInt(6, supplier.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void hapus(int idSupplier) throws SQLException {
        String sql = "DELETE FROM supplier WHERE id = ?";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idSupplier);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Supplier> ambilSemua() throws SQLException {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM supplier";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Supplier(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("email"),
                    rs.getString("alamat"),
                    rs.getString("npwp"),
                    rs.getString("noRek")
                ));
            }
        }
        return list;
    }

    @Override
    public Supplier cariById(int idSupplier) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE id = ? LIMIT 1";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idSupplier);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Supplier(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getString("email"),
                        rs.getString("alamat"),
                        rs.getString("npwp"),
                        rs.getString("noRek")
                    );
                }
            }
        }
        return null;
    }
}

