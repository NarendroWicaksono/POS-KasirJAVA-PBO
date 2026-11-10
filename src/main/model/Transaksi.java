// [TAMBAHAN] - Transaksi.java
// Class model untuk header transaksi penjualan.
// Menerapkan: Encapsulation + Composition (memiliki List<DetailTransaksi>).
package main.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class model yang merepresentasikan satu sesi transaksi penjualan.
 * Setiap transaksi terdiri dari header (no faktur, kasir, waktu)
 * dan satu atau lebih baris detail item {@link DetailTransaksi}.
 *
 * ENCAPSULATION: Atribut private + getter/setter.
 *
 * COMPOSITION: Transaksi "memiliki" List<DetailTransaksi>.
 * Jika transaksi dihapus, detail-nya ikut tidak berlaku.
 *
 * @author Tambahan OOP
 */
public class Transaksi {

    // [TAMBAHAN] Atribut private — Encapsulation
    private String noTransaksi;
    private String petugasKasir;
    private String waktuTransaksi;
    private long   total;
    private long   jumlahBayar;
    private long   kembalian;
    private int    diskon;        // dalam persen (0–100)

    // [TAMBAHAN] Komposisi: satu Transaksi memiliki banyak DetailTransaksi
    private List<DetailTransaksi> listDetail;

    // ── Constructor ───────────────────────────────────────────
    // [TAMBAHAN]
    public Transaksi() {
        this.listDetail = new ArrayList<>();
        this.diskon     = 0;
    }

    // [TAMBAHAN]
    /**
     * @param noTransaksi   nomor faktur unik
     * @param petugasKasir  username kasir yang melayani
     * @param waktuTransaksi waktu transaksi dalam format String
     */
    public Transaksi(String noTransaksi, String petugasKasir, String waktuTransaksi) {
        this.noTransaksi    = noTransaksi;
        this.petugasKasir   = petugasKasir;
        this.waktuTransaksi = waktuTransaksi;
        this.listDetail     = new ArrayList<>();
        this.diskon         = 0;
    }

    // ── Getter ────────────────────────────────────────────────
    // [TAMBAHAN]
    public String getNoTransaksi()    { return noTransaksi; }
    public String getPetugasKasir()   { return petugasKasir; }
    public String getWaktuTransaksi() { return waktuTransaksi; }
    public long   getTotal()          { return total; }
    public long   getJumlahBayar()    { return jumlahBayar; }
    public long   getKembalian()      { return kembalian; }
    public int    getDiskon()         { return diskon; }
    public List<DetailTransaksi> getListDetail() { return listDetail; }

    // ── Setter ────────────────────────────────────────────────
    // [TAMBAHAN]
    public void setNoTransaksi(String noTransaksi)      { this.noTransaksi    = noTransaksi; }
    public void setPetugasKasir(String petugasKasir)    { this.petugasKasir   = petugasKasir; }
    public void setWaktuTransaksi(String waktuTransaksi){ this.waktuTransaksi = waktuTransaksi; }

    public void setDiskon(int diskon) {
        if (diskon < 0 || diskon > 100)
            throw new IllegalArgumentException("Diskon harus antara 0 dan 100 persen.");
        this.diskon = diskon;
        hitungTotal(); // recalculate
    }

    public void setJumlahBayar(long jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
        this.kembalian   = jumlahBayar - this.total;
    }

    // ── Method bisnis domain ──────────────────────────────────

    /**
     * [TAMBAHAN] Menambahkan satu item ke daftar detail transaksi,
     * kemudian menghitung ulang total secara otomatis.
     *
     * @param detail item barang yang dibeli
     */
    public void tambahItem(DetailTransaksi detail) {
        if (detail == null)
            throw new IllegalArgumentException("Detail transaksi tidak boleh null.");
        this.listDetail.add(detail);
        hitungTotal();
    }

    /**
     * [TAMBAHAN] Menghapus item dari daftar berdasarkan indeks,
     * kemudian menghitung ulang total secara otomatis.
     *
     * @param indeks posisi item dalam list (mulai dari 0)
     */
    public void hapusItem(int indeks) {
        if (indeks < 0 || indeks >= listDetail.size())
            throw new IndexOutOfBoundsException("Indeks item tidak valid.");
        listDetail.remove(indeks);
        hitungTotal();
    }

    /**
     * [TAMBAHAN] Menghitung total transaksi dari semua item,
     * lalu menerapkan diskon jika ada.
     * Dipanggil otomatis setiap kali item ditambah/dihapus atau diskon diubah.
     *
     * @return total setelah diskon
     */
    public long hitungTotal() {
        long subtotal = 0;
        for (DetailTransaksi d : listDetail) {
            subtotal += d.getJumlah();
        }
        // terapkan diskon
        long nilaiDiskon = (subtotal * diskon) / 100;
        this.total = subtotal - nilaiDiskon;
        return this.total;
    }

    /**
     * [TAMBAHAN] Menghitung kembalian pelanggan.
     *
     * @param bayar jumlah uang yang dibayarkan pelanggan
     * @return kembalian (bayar - total), negatif jika kurang bayar
     */
    public long hitungKembalian(long bayar) {
        this.jumlahBayar = bayar;
        this.kembalian   = bayar - this.total;
        return this.kembalian;
    }

    /**
     * [TAMBAHAN] Mengecek apakah transaksi sudah memiliki minimal 1 item.
     * @return true jika ada minimal 1 item
     */
    public boolean sudahAdaItem() {
        return !listDetail.isEmpty();
    }

    // [TAMBAHAN]
    @Override
    public String toString() {
        return "Transaksi{no='" + noTransaksi + "', kasir='" + petugasKasir
                + "', total=" + total + ", items=" + listDetail.size() + '}';
    }
}

