# 📑 DOKUMENTASI SISTEM POS KASIR (UNTUK LAPORAN TUGAS BESAR PBO)

Dokumentasi ini disusun berdasarkan **kode sumber final** yang telah diuji dan dikompilasi, bukan berdasarkan asumsi. Diagram digambarkan menggunakan format standard **Mermaid** sehingga dapat langsung dirender di Markdown viewer, GitHub, maupun diekspor ke laporan dokumen.

---

## 1. Use Case Diagram

Diagram ini memetakan interaksi antara dua aktor utama (**Admin** dan **Petugas Kasir**) dengan sistem POS Kasir.

```mermaid
flowchart LR
    Admin([👨‍💼 Admin Toko])
    Kasir([👨‍💻 Petugas Kasir])

    subgraph Sistem Kasir POS
        UC1(Autentikasi / Login)
        UC2(Kelola Data Barang CRUD)
        UC3(Kelola Data Member)
        UC4(Kelola Data Supplier)
        UC5(Registrasi Karyawan)
        UC6(Lihat Riwayat Transaksi)
        UC7(Cari & Cek Stok Barang)
        UC8(Transaksi Penjualan & Bayar)
        UC9(Cetak Struk Belanja)
    end

    Admin --> UC1
    Admin --> UC2
    Admin --> UC3
    Admin --> UC4
    Admin --> UC5
    Admin --> UC6

    Kasir --> UC1
    Kasir --> UC7
    Kasir --> UC8
    Kasir --> UC9
```

---

## 2. Class Diagram

Class diagram ini mencerminkan struktur kelas yang sebenarnya di dalam package `main.model`, `main.dao`, dan interaksinya dengan komponen presentation `main`.

```mermaid
classDiagram
    %% Interface & Abstraction
    class InterfaceDao~T~ {
        <<interface>>
        +simpan(T t)* void
        +ubah(T t)* void
        +hapus(int id)* void
        +ambilSemua()* List~T~
        +cariById(int id)* T
    }

    class Pengguna {
        <<abstract>>
        #int id
        #String username
        #String password
        #String email
        +getId() int
        +getUsername() String
        +getPassword() String
        +getEmail() String
        +getHakAkses()* String
        +tampilkanMenu()* void
        +getDashboardTujuan()* String
    }

    class Admin {
        +Admin()
        +Admin(int id, String username, String password, String email)
        +getHakAkses() String
        +tampilkanMenu() void
        +getDashboardTujuan() String
    }

    class Kasir {
        +Kasir()
        +Kasir(int id, String username, String password, String email)
        +getHakAkses() String
        +tampilkanMenu() void
        +getDashboardTujuan() String
    }

    Pengguna <|-- Admin : Inheritance
    Pengguna <|-- Kasir : Inheritance

    %% Model Domain
    class Barang {
        -long kodeBarang
        -String namaBarang
        -int hargaBeli
        -int hargaJual
        -int stokBarang
        +getKodeBarang() long
        +getNamaBarang() String
        +getHargaBeli() int
        +getHargaJual() int
        +getStokBarang() int
        +kurangiStok(int jumlah) void
        +tambahStok(int jumlah) void
        +hitungMargin() int
    }

    class Member {
        -int idMember
        -String namaMember
        -String noTelepon
        -int poin
        +getIdMember() int
        +getNamaMember() String
        +getNoTelepon() String
        +getPoin() int
        +tambahPoin(int jumlah) void
        +tukarPoin(int jumlah) boolean
    }

    class Supplier {
        -int id
        -String nama
        -String email
        -String alamat
        -String npwp
        -String noRek
        +getId() int
        +getNama() String
        +getEmail() String
        +getAlamat() String
        +getNpwp() String
        +getNoRek() String
    }

    class DetailTransaksi {
        -String noTransaksi
        -long kodeBarang
        -String namaBarang
        -int hargaSatuan
        -int jumlah
        -int subtotal
        +hitungSubtotal() int
        +getNoTransaksi() String
        +getKodeBarang() long
        +getNamaBarang() String
        +getHargaSatuan() int
        +getJumlah() int
        +getSubtotal() int
    }

    class Transaksi {
        -String noTransaksi
        -String petugasKasir
        -long total
        -String waktuTransaksi
        -List~DetailTransaksi~ daftarDetail
        +tambahDetail(DetailTransaksi item) void
        +hitungTotal() long
        +hitungTotal(double persentaseDiskon) long
        +getNoTransaksi() String
        +getPetugasKasir() String
        +getTotal() long
        +getWaktuTransaksi() String
        +getDaftarDetail() List~DetailTransaksi~
    }

    class Struk {
        -Transaksi transaksi
        -long bayar
        -long kembali
        +buatFormatStruk() String
        +tampilkanDialog() void
        +simpanKeFile(String direktori) boolean
    }

    Transaksi "1" *-- "*" DetailTransaksi : Komposisi
    Struk --> Transaksi : Asosiasi

    %% DAO Layer
    class BarangDao {
        +simpan(Barang b) void
        +ubah(Barang b) void
        +hapus(long kodeBarang) void
        +ambilSemua() List~Barang~
        +cari(long kode) Barang
        +cari(String nama) List~Barang~
        +cari(int minStok, int maxStok) List~Barang~
        +kurangiStok(Connection con, long kode, int jumlah) void
    }

    class PenggunaDao {
        +login(String username, String password) Pengguna
        +simpan(Pengguna p) void
        +simpanAdmin(Admin a) void
        +simpanKasir(Kasir k) void
        +ubah(Pengguna p) void
        +hapus(int id) void
        +ambilSemua() List~Pengguna~
        +cariById(int id) Pengguna
    }

    class MemberDao {
        +simpan(Member m) void
        +ubah(Member m) void
        +hapus(int id) void
        +ambilSemua() List~Member~
        +cariById(int id) Member
    }

    class SupplierDao {
        +simpan(Supplier s) void
        +ubah(Supplier s) void
        +hapus(int id) void
        +ambilSemua() List~Supplier~
        +cariById(int id) Supplier
    }

    class TransaksiDao {
        +simpan(Transaksi t) void
        +simpanTransaksiLengkap(Transaksi t) void
        +ambilSemua() List~Transaksi~
        +cariById(int id) Transaksi
        +cariByNoTransaksi(String noTrans) Transaksi
        +ambilDetail(String noTrans) List~DetailTransaksi~
    }

    InterfaceDao~Barang~ <|.. BarangDao : Realization
    InterfaceDao~Pengguna~ <|.. PenggunaDao : Realization
    InterfaceDao~Member~ <|.. MemberDao : Realization
    InterfaceDao~Supplier~ <|.. SupplierDao : Realization
    InterfaceDao~Transaksi~ <|.. TransaksiDao : Realization

    BarangDao ..> Barang : Mengelola
    PenggunaDao ..> Pengguna : Mengelola
    MemberDao ..> Member : Mengelola
    SupplierDao ..> Supplier : Mengelola
    TransaksiDao ..> Transaksi : Mengelola
```

---

## 3. Sequence Diagram Alur Utama

### A. Sequence Diagram: Autentikasi Pengguna (Login Polimorfik)

Alur ini menunjukkan bagaimana class `loginAdmin` atau `loginKasir` berinteraksi dengan `PenggunaDao`, dan bagaimana polymorphism runtime digunakan untuk menentukan hak akses akun.

```mermaid
sequenceDiagram
    autonumber
    actor Pengguna as User (Admin/Kasir)
    participant View as loginAdmin / loginKasir
    participant DAO as PenggunaDao
    participant DB as MySQL Database
    participant Model as Pengguna (Admin / Kasir)

    Pengguna->>View: Input username & password, klik MASUK
    View->>View: Validasi input kosong (trim)
    View->>DAO: login(username, password)
    DAO->>DB: PreparedStatement SELECT FROM admin WHERE username=? AND password=?
    DB-->>DAO: ResultSet
    alt Data Ditemukan di Tabel Admin
        DAO->>Model: new Admin(id, username, password, email)
        DAO-->>View: return objek Admin (sebagai Pengguna)
    else Tidak Ada di Admin
        DAO->>DB: PreparedStatement SELECT FROM petugaskasir WHERE username=? AND password=?
        DB-->>DAO: ResultSet
        alt Data Ditemukan di Tabel Petugas Kasir
            DAO->>Model: new Kasir(id_petugas, username, password, email)
            DAO-->>View: return objek Kasir (sebagai Pengguna)
        else Kredensial Salah
            DAO-->>View: return null
        end
    end

    alt Login Berhasil & Tipe Sesuai (instanceof)
        View->>Model: getHakAkses() (Polymorphic call)
        View->>View: Tampilkan pesan sukses & buka Dashboard tujuan
    else Peran Tidak Sesuai Form
        View->>View: Tampilkan pesan "Akses Ditolak: Bukan peran yang sesuai"
    else Kredensial Gagal
        View->>View: Tampilkan pesan "Username atau password salah"
    end
```

---

### B. Sequence Diagram: Transaksi Penjualan Atomik & Cetak Struk

Alur ini menggambarkan saat kasir menekan tombol **BAYAR** pada `dashbordKasir`, di mana transaksi database atomik dijalankan (`setAutoCommit(false)`), stok barang dikurangi, dan struk dicetak melalui class `Struk`.

```mermaid
sequenceDiagram
    autonumber
    actor Kasir as Petugas Kasir
    participant View as dashbordKasir
    participant Trx as Transaksi
    participant Det as DetailTransaksi
    participant DAO as TransaksiDao
    participant BDao as BarangDao
    participant DB as MySQL Database
    participant Struk as Struk

    Kasir->>View: Input item belanja di tabel & klik BAYAR
    View->>View: Validasi kecukupan pembayaran (bayar >= total)
    View->>Trx: new Transaksi(noTransaksi, kasir, total, waktu)
    
    loop Untuk Setiap Baris Item Belanja
        View->>Det: new DetailTransaksi(noTrans, kode, nama, harga, qty)
        View->>Trx: tambahDetail(detailItem)
    end

    View->>DAO: simpanTransaksiLengkap(transaksi)
    DAO->>DB: setAutoCommit(false) [Mulai Transaksi Atomik]
    
    DAO->>DB: INSERT INTO transaksi (no_transaksi, ...)
    
    loop Untuk Setiap DetailTransaksi
        DAO->>DB: INSERT INTO detail_transaksi (no_transaksi, kode_barang, ...)
        DAO->>BDao: kurangiStok(con, kodeBarang, qty)
        BDao->>DB: UPDATE databarang SET stok_barang = stok_barang - ? WHERE code_barang = ?
    end

    alt Semua Query Berhasil
        DAO->>DB: commit() [Permanenkan Perubahan]
        DAO-->>View: Berhasil
        View->>Struk: new Struk(transaksi, uangBayar, kembalian)
        View->>Struk: tampilkanDialog()
        Struk-->>Kasir: Tampilkan Dialog Nota Struk & Opsi Simpan TXT
        View->>View: Bersihkan keranjang tabel belanja
    else Ada Kegagalan / Exception
        DAO->>DB: rollback() [Batalkan Seluruh Perubahan]
        DAO-->>View: Lempar SQLException
        View->>View: Tampilkan Error Dialog
    end
```

---

## 4. Ringkasan Bab Aplikasi & Struktur Proyek

### A. Fitur-Fitur Utama Aplikasi
1. **Autentikasi Aman & Berbasis Peran (RBAC)**: Login terpisah untuk Admin dan Kasir dengan validasi objek polimorfik `Pengguna`.
2. **Kelola Produk (CRUD Barang)**: Form admin untuk menambah, mengubah, menghapus, dan memantau stok barang dengan validasi angka dan stok non-negatif.
3. **Pencarian Real-Time (List Barang)**: Form kasir untuk mencari barang berdasarkan kata kunci dengan filter dinamis.
4. **Transaksi Penjualan Atomik**: Perhitungan otomatis subtotal, total harga, dan kembalian. Menyimpan header dan rincian item transaksi secara atomik (semua sukses atau semua dibatalkan jika stok gagal).
5. **Manajemen Stok Otomatis**: Stok barang berkurang otomatis di database saat transaksi kasir berhasil dibayar.
6. **Pencetakan Struk (Nota Belanja)**: Class `Struk` menyusun format nota rapi dengan opsi tampilan dialog interaktif dan simpan berkas `.txt`.
7. **Laporan Riwayat Penjualan**: Form `riwayatTransaksi` untuk meninjau data penjualan masa lalu dan mengecek rincian per nota.
8. **Kelola Data Pelanggan (Member)**: Pencatatan identitas pelanggan dan akumulasi poin reward.
9. **Kelola Pemasok (Supplier)**: Manajemen kontak dan rekening supplier pemasok barang toko.

### B. Cara Menjalankan Aplikasi
1. Aktifkan **MySQL** di XAMPP Control Panel (port `3306`).
2. Pastikan database `kasir` telah terbuat dan skrip tambahan di [`schema_tambahan.sql`](file:///c:/Users/NITRO/Downloads/POS-KasirJAVA-PBO/schema_tambahan.sql) sudah dijalankan.
3. Buka PowerShell dan jalankan:
   ```powershell
   & "C:\Users\NITRO\.jdks\openjdk-26.0.2.1\bin\java.exe" -cp "build\classes;lib\*" main.MenuUtama
   ```
   *(Atau buka proyek di Apache NetBeans dan jalankan `MenuUtama.java`)*.

### C. Struktur Package Proyek
- `main` : Form antarmuka pengguna berbasis Java Swing JFrame (`MenuUtama`, `loginAdmin`, `loginKasir`, `dashbordAdmin`, `dashbordKasir`, `tambahBarang`, `listBarang`, `dataMember`, `dataSupplier`, `registrasi`, `riwayatTransaksi`).
- `main.model` : Kelas POJO entitas bisnis (`Barang`, `Member`, `Supplier`, `Transaksi`, `DetailTransaksi`, `Struk`), generic interface `InterfaceDao<T>`, serta kelas abstrak `Pengguna` dengan subkelas `Admin` dan `Kasir`.
- `main.dao` : Kelas Data Access Object berparameter `PreparedStatement` aman dari injeksi SQL (`BarangDao`, `MemberDao`, `SupplierDao`, `PenggunaDao`, `TransaksiDao`).
