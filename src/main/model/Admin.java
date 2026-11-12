
package main.model;

public class Admin extends Pengguna {

    public Admin() {
        super();
    }

    public Admin(int id, String username, String password, String email) {
        super(id, username, password, email);
    }

    @Override
    public String getHakAkses() {
        return "ADMIN - Akses penuh: kelola barang, member, supplier, registrasi karyawan, dan riwayat transaksi.";
    }

    @Override
    public String[] tampilkanMenu() {
        return new String[]{
            "Data Barang",
            "Registrasi Karyawan",
            "Data Member",
            "Data Supplier",
            "Riwayat Transaksi"
        };
    }

    @Override
    public String getDashboardTujuan() {
        return "dashbordAdmin";
    }

    @Override
    public String toString() {
        return "Admin[id=" + getId() + ", username='" + getUsername() + "']";
    }
}

