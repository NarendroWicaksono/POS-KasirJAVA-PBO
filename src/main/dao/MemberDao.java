// [TAMBAHAN] - MemberDao.java
// Data Access Object untuk entitas Member.
// Menerapkan InterfaceDao<Member> dengan PreparedStatement aman dari SQL Injection.
package main.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.model.InterfaceDao;
import main.model.Member;

/**
 * Class DAO untuk mengelola tabel 'member'.
 * Mengimplementasikan InterfaceDao<Member>.
 *
 * @author Tambahan OOP
 */
public class MemberDao implements InterfaceDao<Member> {

    // [TAMBAHAN] Membuka koneksi database terpusat via koneksi.buka()
    private Connection bukaKoneksi() throws SQLException {
        return main.koneksi.buka();
    }

    @Override
    public void simpan(Member member) throws SQLException {
        String sql = "INSERT INTO member (id_member, username, no_tlp, poin) VALUES (?, ?, ?, ?)";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, member.getIdMember());
            ps.setString(2, member.getNamaMember());
            ps.setString(3, member.getNoTelepon());
            ps.setInt(4, member.getPoin());
            ps.executeUpdate();
        }
    }

    @Override
    public void ubah(Member member) throws SQLException {
        String sql = "UPDATE member SET username = ?, no_tlp = ?, poin = ? WHERE id_member = ?";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, member.getNamaMember());
            ps.setString(2, member.getNoTelepon());
            ps.setInt(3, member.getPoin());
            ps.setInt(4, member.getIdMember());
            ps.executeUpdate();
        }
    }

    @Override
    public void hapus(int idMember) throws SQLException {
        String sql = "DELETE FROM member WHERE id_member = ?";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMember);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Member> ambilSemua() throws SQLException {
        List<Member> list = new ArrayList<>();
        String sql = "SELECT * FROM member";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_member");
                String nama = rs.getString("username");
                String tlp = rs.getString("no_tlp");
                int poin = 0;
                try {
                    poin = rs.getInt("poin");
                } catch (Exception ignored) {
                }
                list.add(new Member(id, nama, tlp, poin));
            }
        }
        return list;
    }

    @Override
    public Member cariById(int idMember) throws SQLException {
        String sql = "SELECT * FROM member WHERE id_member = ? LIMIT 1";
        try (Connection con = bukaKoneksi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMember);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_member");
                    String nama = rs.getString("username");
                    String tlp = rs.getString("no_tlp");
                    int poin = rs.getInt("poin");
                    return new Member(id, nama, tlp, poin);
                }
            }
        }
        return null;
    }
}

