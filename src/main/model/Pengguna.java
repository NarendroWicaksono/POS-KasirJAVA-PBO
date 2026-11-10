// [TAMBAHAN] - Pengguna.java
// Abstract class sebagai fondasi hierarki pengguna sistem kasir.
// Menerapkan: Abstraction, Inheritance, Encapsulation, Polymorphism.
package main.model;

/**
 * Abstract class yang merepresentasikan pengguna sistem kasir secara umum.
 *
 * ABSTRACTION: Class ini tidak bisa di-instantiate langsung.
 * Method abstrak getHakAkses() dan tampilkanMenu() dipaksakan
 * untuk diimplementasi oleh setiap subclass.
 *
 * INHERITANCE: Subclass Admin dan Kasir mewarisi semua atribut
 * dan method non-abstrak dari class ini.
 *
 * @author Tambahan OOP
 */
public abstract class Pengguna {

    // [TAMBAHAN] Atribut private — Encapsulation
    private int    id;
    private String username;
    private String password;
    private String email;

    // ── Constructor kosong ────────────────────────────────────
    // [TAMBAHAN]
    public Pengguna() {
    }

    // ── Constructor lengkap ───────────────────────────────────
    // [TAMBAHAN]
    /**
     * @param id       ID unik pengguna
     * @param username nama pengguna untuk login
     * @param password kata sandi pengguna
     * @param email    alamat email pengguna
     */
    public Pengguna(int id, String username, String password, String email) {
        this.id       = id;
        this.username = username;
        this.password = password;
        this.email    = email;
    }

    // ── Getter — Encapsulation ────────────────────────────────
    // [TAMBAHAN]
    public int    getId()       { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail()    { return email; }

    // ── Setter dengan validasi — Encapsulation ────────────────
    // [TAMBAHAN]
    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID harus lebih dari 0.");
        this.id = id;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty())
            throw new IllegalArgumentException("Username tidak boleh kosong.");
        this.username = username.trim();
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("Password tidak boleh kosong.");
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // ── Method abstrak — Abstraction & Polymorphism ───────────

    /**
     * [TAMBAHAN] Mengembalikan deskripsi hak akses pengguna.
     * Tiap subclass (Admin, Kasir) memberikan jawaban berbeda
     * untuk method yang sama — inilah POLYMORPHISM.
     *
     * @return String deskripsi hak akses
     */
    public abstract String getHakAkses();

    /**
     * [TAMBAHAN] Mengembalikan daftar menu yang bisa diakses pengguna.
     * Admin mendapat menu lebih banyak daripada Kasir.
     *
     * @return array String berisi nama-nama menu
     */
    public abstract String[] tampilkanMenu();

    /**
     * [TAMBAHAN] Mengembalikan nama class dashboard tujuan setelah login.
     * Admin diarahkan ke dashbordAdmin, Kasir ke dashbordKasir.
     *
     * @return String nama class dashboard
     */
    public abstract String getDashboardTujuan();

    // ── Method konkret (bisa dipakai langsung oleh subclass) ──
    // [TAMBAHAN]
    /**
     * Memvalidasi bahwa username dan password tidak kosong.
     * Ini adalah logika bersama yang dipakai oleh semua pengguna.
     *
     * @return true jika data login valid
     */
    public boolean validasiDataLogin() {
        return username != null && !username.isEmpty()
            && password != null && !password.isEmpty();
    }

    // ── toString ──────────────────────────────────────────────
    // [TAMBAHAN]
    @Override
    public String toString() {
        return getHakAkses() + "{"
                + "id=" + id
                + ", username='" + username + '\''
                + ", email='" + email + '\''
                + '}';
    }
}

