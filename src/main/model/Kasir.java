// [TAMBAHAN] - Kasir.java
// Subclass konkret dari Pengguna yang merepresentasikan peran Kasir.
// Menerapkan: Inheritance (extends Pengguna) dan Polymorphism (@Override).
package main.model;

/**
 * Class Kasir merupakan subclass konkret dari {@link Pengguna}.
 *
 * INHERITANCE: Kasir mewarisi id, username, password, email
 * beserta getter/setter-nya dari class Pengguna.
 *
 * POLYMORPHISM: Method getHakAkses() dan tampilkanMenu() di-override
 * untuk memberikan perilaku khas Kasir (akses terbatas pada transaksi).
 *
 * Perbedaan nyata dengan Admin:
 * - Menu kasir lebih sedikit (hanya transaksi + daftar barang)
 * - Kasir tidak bisa mengakses manajemen data master
 *
 * @author Tambahan OOP
 */
public class Kasir extends Pengguna {

    // [TAMBAHAN] Atribut khusus Kasir yang tidak dimiliki Pengguna umum
    private int totalTransaksiHariIni;

    // [TAMBAHAN] Constructor kosong
    public Kasir() {
        super();
        this.totalTransaksiHariIni = 0;
    }

    // [TAMBAHAN] Constructor lengkap
    /**
     * @param id       ID unik kasir
     * @param username nama pengguna untuk login
     * @param password kata sandi
     * @param email    alamat email
     */
    public Kasir(int id, String username, String password, String email) {
        super(id, username, password, email);
        this.totalTransaksiHariIni = 0;
    }

    // ── Getter/Setter atribut khusus Kasir ───────────────────
    // [TAMBAHAN]
    public int getTotalTransaksiHariIni() {
        return totalTransaksiHariIni;
    }

    // [TAMBAHAN]
    public void tambahTransaksi() {
        this.totalTransaksiHariIni++;
    }

    // ── Override method abstrak — Polymorphism ────────────────

    /**
     * [TAMBAHAN] Kasir memiliki hak akses terbatas pada fitur transaksi.
     * Override dari method abstrak Pengguna.getHakAkses().
     * Ini adalah bukti nyata POLYMORPHISM: class berbeda (Admin vs Kasir)
     * memberikan jawaban berbeda untuk method yang sama.
     */
    @Override
    public String getHakAkses() {
        return "KASIR - Akses terbatas: input transaksi penjualan dan lihat daftar barang.";
    }

    /**
     * [TAMBAHAN] Menu yang tersedia untuk peran Kasir lebih sedikit dari Admin.
     * Override dari method abstrak Pengguna.tampilkanMenu().
     */
    @Override
    public String[] tampilkanMenu() {
        return new String[]{
            "Transaksi Penjualan",
            "Daftar Barang"
        };
    }

    /**
     * [TAMBAHAN] Kasir diarahkan ke dashbordKasir setelah login berhasil.
     * Override dari method abstrak Pengguna.getDashboardTujuan().
     */
    @Override
    public String getDashboardTujuan() {
        return "dashbordKasir";
    }

    // [TAMBAHAN]
    @Override
    public String toString() {
        return "Kasir[id=" + getId() + ", username='" + getUsername()
                + "', transaksiHariIni=" + totalTransaksiHariIni + "]";
    }
}

