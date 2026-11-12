
package main.model;

import java.util.ArrayList;
import java.util.List;

public class Transaksi {

    private String noTransaksi;
    private String petugasKasir;
    private String waktuTransaksi;
    private long   total;
    private long   jumlahBayar;
    private long   kembalian;
    private int    diskon;

    private List<DetailTransaksi> listDetail;

    private Member member;

    public Transaksi() {
        this.listDetail = new ArrayList<>();
        this.diskon     = 0;
    }

    public Transaksi(String noTransaksi, String petugasKasir, String waktuTransaksi) {
        this.noTransaksi    = noTransaksi;
        this.petugasKasir   = petugasKasir;
        this.waktuTransaksi = waktuTransaksi;
        this.listDetail     = new ArrayList<>();
        this.diskon         = 0;
    }

    public String getNoTransaksi()    { return noTransaksi; }
    public String getPetugasKasir()   { return petugasKasir; }
    public String getWaktuTransaksi() { return waktuTransaksi; }
    public long   getTotal()          { return total; }
    public long   getJumlahBayar()    { return jumlahBayar; }
    public long   getKembalian()      { return kembalian; }
    public int    getDiskon()         { return diskon; }
    public List<DetailTransaksi> getListDetail() { return listDetail; }

    public Member getMember()         { return member; }
    public void   setMember(Member member) { this.member = member; }

    public int getPoinDidapat() {
        if (member != null && total > 0) {
            return (int) (total / 10000);
        }
        return 0;
    }

    public void setNoTransaksi(String noTransaksi)      { this.noTransaksi    = noTransaksi; }
    public void setPetugasKasir(String petugasKasir)    { this.petugasKasir   = petugasKasir; }
    public void setWaktuTransaksi(String waktuTransaksi){ this.waktuTransaksi = waktuTransaksi; }

    public void setDiskon(int diskon) {
        if (diskon < 0 || diskon > 100)
            throw new IllegalArgumentException("Diskon harus antara 0 dan 100 persen.");
        this.diskon = diskon;
        hitungTotal();
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setKembalian(long kembalian) {
        this.kembalian = kembalian;
    }

    public void setJumlahBayar(long jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
        this.kembalian   = jumlahBayar - this.total;
    }

    public void tambahItem(DetailTransaksi detail) {
        if (detail == null)
            throw new IllegalArgumentException("Detail transaksi tidak boleh null.");
        this.listDetail.add(detail);
        hitungTotal();
    }

    public void hapusItem(int indeks) {
        if (indeks < 0 || indeks >= listDetail.size())
            throw new IndexOutOfBoundsException("Indeks item tidak valid.");
        listDetail.remove(indeks);
        hitungTotal();
    }

    public long hitungTotal() {
        long subtotal = 0;
        for (DetailTransaksi d : listDetail) {
            subtotal += d.getJumlah();
        }

        long nilaiDiskon = (subtotal * diskon) / 100;
        this.total = subtotal - nilaiDiskon;

        if (this.jumlahBayar > 0) {
            this.kembalian = this.jumlahBayar - this.total;
        }
        return this.total;
    }

    public long hitungKembalian(long bayar) {
        this.jumlahBayar = bayar;
        this.kembalian   = bayar - this.total;
        return this.kembalian;
    }

    public boolean sudahAdaItem() {
        return !listDetail.isEmpty();
    }

    @Override
    public String toString() {
        return "Transaksi{no='" + noTransaksi + "', kasir='" + petugasKasir
                + "', total=" + total + ", items=" + listDetail.size() + '}';
    }
}

