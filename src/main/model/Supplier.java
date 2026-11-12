
package main.model;

public class Supplier {

    private int    id;
    private String nama;
    private String email;
    private String alamat;
    private String npwp;
    private String noRek;

    public Supplier() {
    }

    public Supplier(int id, String nama, String email,
                    String alamat, String npwp, String noRek) {
        this.id     = id;
        this.nama   = nama;
        this.email  = email;
        this.alamat = alamat;
        this.npwp   = npwp;
        this.noRek  = noRek;
    }

    public int    getId()     { return id; }
    public String getNama()   { return nama; }
    public String getEmail()  { return email; }
    public String getAlamat() { return alamat; }
    public String getNpwp()   { return npwp; }
    public String getNoRek()  { return noRek; }

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

    @Override
    public String toString() {
        return "Supplier{id=" + id + ", nama='" + nama
                + "', email='" + email + "', noRek='" + noRek + "'}";
    }
}

