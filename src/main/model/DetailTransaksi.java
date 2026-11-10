// [TAMBAHAN] - DetailTransaksi.java
// Class model untuk satu baris item dalam transaksi penjualan.
// Menerapkan: Encapsulation (atribut private + getter/setter bervalidasi).
package main.model;

/**
 * Class model yang merepresentasikan satu baris item dalam transaksi penjualan.
 * Misal: "Roti Tawar x 2 = Rp 10.000"
 *
 * ENCAPSULATION: Semua atribut private. Jumlah dihitung otomatis
 * oleh method hitungJumlah(), tidak bisa dimanipulasi sembarangan.
 *
 * Relasi: satu objek {@link Transaksi} memiliki banyak DetailTransaksi
 * (komposisi one-to-many).
 *
 * @author Tambahan OOP
 */
public class DetailTransaksi {

    // [TAMBAHAN] Atribut private — Encapsulation
    private int    idDetail;
    private String noTransaksi;   // FK ke tabel transaksi
    private String namaBarang;
    private long   kodeBarang;
    private int    harga;
    private int    qty;
    private int    jumlah;        // dihitung: harga * qty

    // ── Constructor kosong ────────────────────────────────────
    // [TAMBAHAN]
    public DetailTransaksi() {
    }

    // ── Constructor lengkap ───────────────────────────────────
    // [TAMBAHAN]
    /**
     * @param noTransaksi nomor transaksi induk
     * @param kodeBarang  kode barang yang dibeli
     * @param namaBarang  nama barang yang dibeli
     * @param harga       harga satuan saat transaksi
     * @param qty         jumlah unit yang dibeli
     */
    public DetailTransaksi(String noTransaksi, long kodeBarang,
                           String namaBarang, int harga, int qty) {
        this.noTransaksi = noTransaksi;
        this.kodeBarang  = kodeBarang;
        this.namaBarang  = namaBarang;
        this.harga       = harga;
        this.qty         = qty;
        this.jumlah      = hitungJumlah(); // otomatis dihitung
    }

    // ── Getter ────────────────────────────────────────────────
    // [TAMBAHAN]
    public int    getIdDetail()     { return idDetail; }
    public String getNoTransaksi()  { return noTransaksi; }
    public String getNamaBarang()   { return namaBarang; }
    public long   getKodeBarang()   { return kodeBarang; }
    public int    getHarga()        { return harga; }
    public int    getQty()          { return qty; }
    public int    getJumlah()       { return jumlah; }

    // ── Setter ────────────────────────────────────────────────
    // [TAMBAHAN]
    public void setIdDetail(int idDetail)         { this.idDetail    = idDetail; }
    public void setNoTransaksi(String noTransaksi){ this.noTransaksi = noTransaksi; }
    public void setNamaBarang(String namaBarang)  { this.namaBarang  = namaBarang; }
    public void setKodeBarang(long kodeBarang)    { this.kodeBarang  = kodeBarang; }

    public void setHarga(int harga) {
        if (harga < 0)
            throw new IllegalArgumentException("Harga tidak boleh negatif.");
        this.harga  = harga;
        this.jumlah = hitungJumlah(); // recalculate
    }

    public void setQty(int qty) {
        if (qty <= 0)
            throw new IllegalArgumentException("Qty harus lebih dari 0.");
        this.qty    = qty;
        this.jumlah = hitungJumlah(); // recalculate
    }

    // ── Method bisnis domain ──────────────────────────────────

    /**
     * [TAMBAHAN] Menghitung subtotal baris ini: harga x qty.
     * Dipanggil otomatis saat harga atau qty diubah.
     *
     * @return subtotal dalam rupiah
     */
    public final int hitungJumlah() {
        return this.harga * this.qty;
    }

    // [TAMBAHAN]
    @Override
    public String toString() {
        return "DetailTransaksi{no='" + noTransaksi + "', barang='" + namaBarang
                + "', harga=" + harga + ", qty=" + qty + ", jumlah=" + jumlah + '}';
    }
}

