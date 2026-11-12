
package main.model;

public class Kasir extends Pengguna {

    private int totalTransaksiHariIni;

    public Kasir() {
        super();
        this.totalTransaksiHariIni = 0;
    }

    public Kasir(int id, String username, String password, String email) {
        super(id, username, password, email);
        this.totalTransaksiHariIni = 0;
    }

    public int getTotalTransaksiHariIni() {
        return totalTransaksiHariIni;
    }

    public void tambahTransaksi() {
        this.totalTransaksiHariIni++;
    }

    @Override
    public String getHakAkses() {
        return "KASIR - Akses terbatas: input transaksi penjualan dan lihat daftar barang.";
    }

    @Override
    public String[] tampilkanMenu() {
        return new String[]{
            "Transaksi Penjualan",
            "Daftar Barang"
        };
    }

    @Override
    public String getDashboardTujuan() {
        return "dashbordKasir";
    }

    @Override
    public String toString() {
        return "Kasir[id=" + getId() + ", username='" + getUsername()
                + "', transaksiHariIni=" + totalTransaksiHariIni + "]";
    }
}

