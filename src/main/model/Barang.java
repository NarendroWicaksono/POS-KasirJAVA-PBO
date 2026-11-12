
package main.model;

public class Barang {

    private long   kodeBarang;
    private String namaBarang;
    private int    hargaBeli;
    private int    hargaJual;
    private int    stokBarang;

    private Supplier supplier;

    public Barang() {
    }

    public Barang(long kodeBarang, String namaBarang,
                  int hargaBeli, int hargaJual, int stokBarang) {
        this.kodeBarang  = kodeBarang;
        this.namaBarang  = namaBarang;
        this.hargaBeli   = hargaBeli;
        this.hargaJual   = hargaJual;
        this.stokBarang  = stokBarang;
    }

    public Barang(long kodeBarang, String namaBarang,
                  int hargaBeli, int hargaJual, int stokBarang, Supplier supplier) {
        this(kodeBarang, namaBarang, hargaBeli, hargaJual, stokBarang);
        this.supplier = supplier;
    }

    public long   getKodeBarang()  { return kodeBarang; }
    public String getNamaBarang()  { return namaBarang; }
    public int    getHargaBeli()   { return hargaBeli; }
    public int    getHargaJual()   { return hargaJual; }
    public int    getStokBarang()  { return stokBarang; }
    public Supplier getSupplier()  { return supplier; }

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

    public int hitungMargin() {
        return this.hargaJual - this.hargaBeli;
    }

    public void kurangiStok(int jumlah) {
        if (jumlah <= 0)
            throw new IllegalArgumentException("Jumlah pengurangan harus lebih dari 0.");
        if (jumlah > this.stokBarang)
            throw new IllegalArgumentException(
                "Stok tidak mencukupi. Stok tersedia: " + this.stokBarang + " unit.");
        this.stokBarang -= jumlah;
    }

    public void tambahStok(int jumlah) {
        if (jumlah <= 0)
            throw new IllegalArgumentException("Jumlah penambahan harus lebih dari 0.");
        this.stokBarang += jumlah;
    }

    public boolean tersedia() {
        return this.stokBarang > 0;
    }

    @Override
    public String toString() {
        return "Barang{kode=" + kodeBarang + ", nama='" + namaBarang
                + "', hargaJual=" + hargaJual + ", stok=" + stokBarang + '}';
    }
}

