
package main.model;

public class DetailTransaksi {

    private int    idDetail;
    private String noTransaksi;
    private String namaBarang;
    private long   kodeBarang;
    private int    harga;
    private int    qty;
    private int    jumlah;

    public DetailTransaksi() {
    }

    public DetailTransaksi(String noTransaksi, long kodeBarang,
                           String namaBarang, int harga, int qty) {
        this.noTransaksi = noTransaksi;
        this.kodeBarang  = kodeBarang;
        this.namaBarang  = namaBarang;
        this.harga       = harga;
        this.qty         = qty;
        this.jumlah      = hitungJumlah();
    }

    public int    getIdDetail()     { return idDetail; }
    public String getNoTransaksi()  { return noTransaksi; }
    public String getNamaBarang()   { return namaBarang; }
    public long   getKodeBarang()   { return kodeBarang; }
    public int    getHarga()        { return harga; }
    public int    getQty()          { return qty; }
    public int    getJumlah()       { return jumlah; }

    public void setIdDetail(int idDetail)         { this.idDetail    = idDetail; }
    public void setNoTransaksi(String noTransaksi){ this.noTransaksi = noTransaksi; }
    public void setNamaBarang(String namaBarang)  { this.namaBarang  = namaBarang; }
    public void setKodeBarang(long kodeBarang)    { this.kodeBarang  = kodeBarang; }

    public void setHarga(int harga) {
        if (harga < 0)
            throw new IllegalArgumentException("Harga tidak boleh negatif.");
        this.harga  = harga;
        this.jumlah = hitungJumlah();
    }

    public void setQty(int qty) {
        if (qty <= 0)
            throw new IllegalArgumentException("Qty harus lebih dari 0.");
        this.qty    = qty;
        this.jumlah = hitungJumlah();
    }

    public final int hitungJumlah() {
        return this.harga * this.qty;
    }

    @Override
    public String toString() {
        return "DetailTransaksi{no='" + noTransaksi + "', barang='" + namaBarang
                + "', harga=" + harga + ", qty=" + qty + ", jumlah=" + jumlah + '}';
    }
}

