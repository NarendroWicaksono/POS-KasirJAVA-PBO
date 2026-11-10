// [TAMBAHAN] - Barang.java
// Class model untuk entitas Barang dalam sistem kasir.
// Menerapkan: Encapsulation (atribut private + getter/setter bervalidasi).
package main.model;

/**
 * Class model yang merepresentasikan data Barang dalam sistem kasir.
 *
 * ENCAPSULATION: Semua atribut bersifat private. Akses hanya
 * melalui getter dan setter yang dapat memvalidasi data masukan.
 *
 * @author Tambahan OOP
 */
public class Barang {

    // [TAMBAHAN] Atribut private — Encapsulation
    private long   kodeBarang;
    private String namaBarang;
    private int    hargaBeli;
    private int    hargaJual;
    private int    stokBarang;
    // [TAMBAHAN] Asosiasi: satu barang dapat dipasok oleh satu Supplier (boleh null)
    private Supplier supplier;

    // ── Constructor kosong ────────────────────────────────────
    // [TAMBAHAN]
    public Barang() {
    }

    // ── Constructor lengkap ───────────────────────────────────
    // [TAMBAHAN]
    /**
     * @param kodeBarang kode unik/barcode barang
     * @param namaBarang nama barang
     * @param hargaBeli  harga beli dari supplier (Rp)
     * @param hargaJual  harga jual ke pelanggan (Rp)
     * @param stokBarang jumlah stok saat ini
     */
    public Barang(long kodeBarang, String namaBarang,
                  int hargaBeli, int hargaJual, int stokBarang) {
        this.kodeBarang  = kodeBarang;
        this.namaBarang  = namaBarang;
        this.hargaBeli   = hargaBeli;
        this.hargaJual   = hargaJual;
        this.stokBarang  = stokBarang;
    }

    /**
     * [TAMBAHAN] Constructor lengkap dengan asosiasi Supplier.
     *
     * @param kodeBarang kode unik barang
     * @param namaBarang nama barang
     * @param hargaBeli  harga beli
     * @param hargaJual  harga jual
     * @param stokBarang stok barang
     * @param supplier   objek pemasok (boleh null jika tanpa supplier)
     */
    public Barang(long kodeBarang, String namaBarang,
                  int hargaBeli, int hargaJual, int stokBarang, Supplier supplier) {
        this(kodeBarang, namaBarang, hargaBeli, hargaJual, stokBarang);
        this.supplier = supplier;
    }

    // ── Getter ────────────────────────────────────────────────
    // [TAMBAHAN]
    public long   getKodeBarang()  { return kodeBarang; }
    public String getNamaBarang()  { return namaBarang; }
    public int    getHargaBeli()   { return hargaBeli; }
    public int    getHargaJual()   { return hargaJual; }
    public int    getStokBarang()  { return stokBarang; }
    public Supplier getSupplier()  { return supplier; }

    // ── Setter dengan validasi — Encapsulation ────────────────
    // [TAMBAHAN]
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    public void setKodeBarang(long kodeBarang) {
        if (kodeBarang <= 0)
            throw new IllegalArgumentException("Kode barang harus lebih dari 0.");
        this.kodeBarang = kodeBarang;
    }

    public void setNamaBarang(String namaBarang) {
        if (namaBarang == null || namaBarang.trim().isEmpty())
            throw new IllegalArgumentException("Nama barang tidak boleh kosong.");
        this.namaBarang = namaBarang.trim();
    }

    public void setHargaBeli(int hargaBeli) {
        if (hargaBeli < 0)
            throw new IllegalArgumentException("Harga beli tidak boleh negatif.");
        this.hargaBeli = hargaBeli;
    }

    public void setHargaJual(int hargaJual) {
        if (hargaJual < 0)
            throw new IllegalArgumentException("Harga jual tidak boleh negatif.");
        this.hargaJual = hargaJual;
    }

    public void setStokBarang(int stokBarang) {
        if (stokBarang < 0)
            throw new IllegalArgumentException("Stok tidak boleh negatif.");
        this.stokBarang = stokBarang;
    }

    // ── Method bisnis domain ──────────────────────────────────

    /**
     * [TAMBAHAN] Menghitung margin keuntungan per unit barang.
     * @return selisih hargaJual dikurangi hargaBeli
     */
    public int hitungMargin() {
        return this.hargaJual - this.hargaBeli;
    }

    /**
     * [TAMBAHAN] Mengurangi stok saat terjadi penjualan.
     * @param jumlah jumlah unit yang terjual
     * @throws IllegalArgumentException jika stok tidak mencukupi
     */
    public void kurangiStok(int jumlah) {
        if (jumlah <= 0)
            throw new IllegalArgumentException("Jumlah pengurangan harus lebih dari 0.");
        if (jumlah > this.stokBarang)
            throw new IllegalArgumentException(
                "Stok tidak mencukupi. Stok tersedia: " + this.stokBarang + " unit.");
        this.stokBarang -= jumlah;
    }

    /**
     * [TAMBAHAN] Menambah stok saat restock dari supplier.
     * @param jumlah jumlah unit yang ditambahkan
     */
    public void tambahStok(int jumlah) {
        if (jumlah <= 0)
            throw new IllegalArgumentException("Jumlah penambahan harus lebih dari 0.");
        this.stokBarang += jumlah;
    }

    /**
     * [TAMBAHAN] Mengecek apakah stok barang masih tersedia.
     * @return true jika stok > 0
     */
    public boolean tersedia() {
        return this.stokBarang > 0;
    }

    // [TAMBAHAN]
    @Override
    public String toString() {
        return "Barang{kode=" + kodeBarang + ", nama='" + namaBarang
                + "', hargaJual=" + hargaJual + ", stok=" + stokBarang + '}';
    }
}

