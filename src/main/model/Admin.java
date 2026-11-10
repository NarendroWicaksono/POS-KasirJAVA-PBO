// [TAMBAHAN] - Admin.java
// Subclass konkret dari Pengguna yang merepresentasikan peran Admin.
// Menerapkan: Inheritance (extends Pengguna) dan Polymorphism (@Override).
package main.model;

/**
 * Class Admin merupakan subclass konkret dari {@link Pengguna}.
 *
 * INHERITANCE: Admin mewarisi id, username, password, email
 * beserta getter/setter-nya dari class Pengguna.
 *
 * POLYMORPHISM: Method getHakAkses() dan tampilkanMenu() di-override
 * untuk memberikan perilaku khas Admin (akses penuh ke semua fitur).
 *
 * @author Tambahan OOP
 */
public class Admin extends Pengguna {

    // [TAMBAHAN] Constructor kosong
    public Admin() {
        super();
    }

    // [TAMBAHAN] Constructor lengkap — memanggil constructor superclass
    /**
     * @param id       ID unik admin
     * @param username nama pengguna untuk login
     * @param password kata sandi
     * @param email    alamat email
     */
    public Admin(int id, String username, String password, String email) {
        super(id, username, password, email);
    }

    // ── Override method abstrak — Polymorphism ────────────────

    /**
     * [TAMBAHAN] Admin memiliki hak akses penuh ke semua fitur sistem.
     * Override dari method abstrak Pengguna.getHakAkses().
     */
    @Override
    public String getHakAkses() {
        return "ADMIN - Akses penuh: kelola barang, member, supplier, registrasi karyawan, dan riwayat transaksi.";
    }

    /**
     * [TAMBAHAN] Menu yang tersedia untuk peran Admin.
     * Override dari method abstrak Pengguna.tampilkanMenu().
     */
    @Override
    public String[] tampilkanMenu() {
        return new String[]{
            "Data Barang",
            "Registrasi Karyawan",
            "Data Member",
            "Data Supplier",
            "Riwayat Transaksi"
        };
    }

    /**
     * [TAMBAHAN] Admin diarahkan ke dashbordAdmin setelah login berhasil.
     * Override dari method abstrak Pengguna.getDashboardTujuan().
     */
    @Override
    public String getDashboardTujuan() {
        return "dashbordAdmin";
    }

    // [TAMBAHAN]
    @Override
    public String toString() {
        return "Admin[id=" + getId() + ", username='" + getUsername() + "']";
    }
}

