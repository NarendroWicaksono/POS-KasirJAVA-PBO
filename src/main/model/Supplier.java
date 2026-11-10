// [TAMBAHAN] - Supplier.java
// Class model untuk entitas Supplier (pemasok barang) dalam sistem kasir.
// Menerapkan: Encapsulation (atribut private + getter/setter bervalidasi).
package main.model;

/**
 * Class model yang merepresentasikan data Supplier (pemasok) dalam sistem kasir.
 *
 * ENCAPSULATION: Semua atribut bersifat private.
 * Data hanya bisa diakses dan diubah melalui getter/setter.
 *
 * @author Tambahan OOP
 */
public class Supplier {

    // [TAMBAHAN] Atribut private — Encapsulation
    private int    id;
    private String nama;
    private String email;
    private String alamat;
    private String npwp;
    private String noRek;

    // ── Constructor kosong ────────────────────────────────────
    // [TAMBAHAN]
    public Supplier() {
    }

    // ── Constructor lengkap ───────────────────────────────────
    // [TAMBAHAN]
    /**
     * @param id     ID unik supplier
     * @param nama   nama perusahaan/individu supplier
     * @param email  alamat email supplier
     * @param alamat alamat lengkap supplier
     * @param npwp   Nomor Pokok Wajib Pajak (NPWP)
     * @param noRek  nomor rekening bank supplier
     */
    public Supplier(int id, String nama, String email,
                    String alamat, String npwp, String noRek) {
        this.id     = id;
        this.nama   = nama;
        this.email  = email;
        this.alamat = alamat;
        this.npwp   = npwp;
        this.noRek  = noRek;
    }

    // ── Getter ────────────────────────────────────────────────
    // [TAMBAHAN]
    public int    getId()     { return id; }
    public String getNama()   { return nama; }
    public String getEmail()  { return email; }
    public String getAlamat() { return alamat; }
    public String getNpwp()   { return npwp; }
    public String getNoRek()  { return noRek; }

    // ── Setter dengan validasi — Encapsulation ────────────────
    // [TAMBAHAN]
    public void setId(int id) {
        if (id <= 0)
            throw new IllegalArgumentException("ID Supplier harus lebih dari 0.");
        this.id = id;
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty())
            throw new IllegalArgumentException("Nama supplier tidak boleh kosong.");
        this.nama = nama.trim();
    }

    public void setEmail(String email)   { this.email  = email; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public void setNpwp(String npwp)     { this.npwp   = npwp; }
    public void setNoRek(String noRek)   { this.noRek  = noRek; }

    // [TAMBAHAN]
    @Override
    public String toString() {
        return "Supplier{id=" + id + ", nama='" + nama
                + "', email='" + email + "', noRek='" + noRek + "'}";
    }
}

