# 🛒 Sistem Informasi Point of Sale (POS) Kasir Toko Retail
> **Tugas Projek Mata Kuliah Pemrograman Berorientasi Objek (PBO)**  
> Berbasis Java Swing Desktop & Basis Data MySQL.

---

## 📌 Deskripsi Proyek

Aplikasi ini merupakan sistem **Point of Sale (POS)** berbasis desktop untuk manajemen operasional kasir pada toko retail. Sistem mencakup pemrosesan transaksi penjualan, pengelolaan master barang dan stok, pencatatan pemasok (supplier), pengelolaan pelanggan terdaftar (member) dengan sistem poin loyalitas reward, serta pelaporan riwayat penjualan dan cetak nota belanja.

Proyek ini merupakan hasil *refactoring* arsitektur berorientasi objek terhadap basis kode legacy. Melalui proses restrukturisasi, sistem ini menerapkan pemisahan tanggung jawab yang jelas melalui arsitektur tiga lapis (*Layered Architecture*), kebal dari celah *SQL Injection* menggunakan `PreparedStatement`, serta menjamin konsistensi multi-tabel melalui transaksi basis data atomik (ACID).

---

## 🏗️ Arsitektur Tiga Lapis (Layered Architecture)

Sistem memisahkan tanggung jawab kode menjadi 3 lapisan terisolasi:
1. **Presentation Layer (`src/main/`)**: Antarmuka grafis pengguna berbasis Java Swing JFrame (`MenuUtama`, `loginAdmin`, `loginKasir`, `dashbordAdmin`, `dashbordKasir`, `tambahBarang`, `listBarang`, `dataMember`, `dataSupplier`, `registrasi`, `riwayatTransaksi`).
2. **Domain Model Layer (`src/main/model/`)**: Kelas entitas bisnis murni yang menerapkan 4 pilar OOP penuh:
   - Encapsulation: `Barang`, `Member`, `Supplier`, `Transaksi`, `DetailTransaksi`, `Struk`.
   - Inheritance: `Pengguna` ➡️ `Admin` dan `Kasir`.
   - Abstraction: `InterfaceDao<T>` dan `abstract class Pengguna`.
   - Polymorphism: Overriding hak akses dan overloading method bisnis.
3. **Data Access Object Layer (`src/main/dao/`)**: Lapisan persistensi basis data yang menghubungkan aplikasi ke MySQL (`BarangDao`, `MemberDao`, `SupplierDao`, `PenggunaDao`, `TransaksiDao`) menggunakan `PreparedStatement` aman dan koneksi terpusat.

---

## ✨ Fitur-Fitur Utama Aplikasi

- **Autentikasi Berbasis Peran (RBAC)**: Form login terpisah untuk Administrator dan Petugas Kasir yang diverifikasi secara polimorfik melalui `PenggunaDao.login`.
- **Manajemen Barang & Pemasok (CRUD)**: Pengelolaan master produk toko pada `tambahBarang` dengan pemilihan supplier pemasok (relasi One-to-Many).
- **Katalog Realtime Kasir**: Form `listBarang` untuk memantau ketersediaan stok produk dan nama pemasok secara cepat.
- **Transaksi Penjualan Atomik (ACID)**: Eksekusi transaksi penjualan multi-tabel pada `dashbordKasir` yang secara atomik menyimpan header nota, detail item, memotong stok barang, dan menambah poin member. Jika stok tidak mencukupi, seluruh proses dibatalkan otomatis (*rollback*).
- **Program Loyalitas Member & Poin**: Pelanggan member memperoleh 1 poin reward per kelipatan belanja Rp 10.000 yang langsung dicatat pada struk dan basis data.
- **Pencetakan Nota Belanja (Struk)**: Class `Struk` menghasilkan teks nota monospaced rapi, dialog pop-up interaktif, dan opsi ekspor ke berkas fisik `.txt`.
- **Laporan Riwayat Penjualan**: Form `riwayatTransaksi` menyajikan histori transaksi, rincian barang per faktur, serta fitur cetak ulang nota belanja.
- **Manajemen Supplier & Proteksi Data**: Form `dataSupplier` dilengkapi dialog konfirmasi jumlah barang terkait sebelum data supplier dihapus (foreign key bernilai `NULL` via `ON DELETE SET NULL`).
- **Registrasi Pegawai Polimorfik**: Form `registrasi` untuk mendaftarkan akun kasir atau admin baru menggunakan method `PenggunaDao.simpan`.

---

## 📁 Struktur Direktori Proyek

```text
POS-KasirJAVA-PBO/
├── .gitignore               # Daftar pengabaian berkas kompilasi (build/, dist/)
├── build.xml                # Skrip Apache Ant bawaan NetBeans
├── database_kasir.sql       # Dump basis data kasir lengkap (skema tabel & data contoh)
├── schema_tambahan.sql      # Skrip DDL tabel detail_transaksi (khusus migrasi database lama)
├── schema_relasi.sql        # Skrip DDL kolom & relasi baru (khusus migrasi database lama)
├── DOKUMENTASI_LAPORAN.md   # Laporan lengkap sistem (Pendahuluan, OOA, OOD, OOP, ERD)
├── README.md                # Panduan teknis proyek (berkas ini)
└── src/
    └── main/
        ├── MenuUtama.java           # Jendela pembuka utama aplikasi
        ├── loginAdmin.java          # Form autentikasi administrator
        ├── loginKasir.java          # Form autentikasi petugas kasir
        ├── dashbordAdmin.java       # Menu dashboard administrator
        ├── dashbordKasir.java       # Menu dashboard transaksi kasir
        ├── tambahBarang.java        # Form CRUD barang & pemilihan supplier
        ├── listBarang.java          # Form katalog barang untuk kasir
        ├── dataMember.java          # Form CRUD member pelanggan & poin
        ├── dataSupplier.java        # Form CRUD supplier pemasok barang
        ├── registrasi.java          # Form pendaftaran akun karyawan baru
        ├── riwayatTransaksi.java    # Form histori penjualan & cetak ulang struk
        ├── koneksi.java             # Pengelola koneksi basis data terpusat (koneksi.buka())
        │
        ├── model/                   # Layer Domain Model & Kontrak Abstraksi
        │   ├── InterfaceDao.java    # Generic DAO interface (Abstraction)
        │   ├── Pengguna.java        # Abstract class akun (Abstraction & Inheritance)
        │   ├── Admin.java           # Subclass Administrator (Polymorphism Overriding)
        │   ├── Kasir.java           # Subclass Petugas Kasir (Polymorphism Overriding)
        │   ├── Supplier.java        # Model entitas supplier (Encapsulation)
        │   ├── Barang.java          # Model entitas barang (Encapsulation & Asosiasi)
        │   ├── Member.java          # Model entitas member loyalitas (Encapsulation)
        │   ├── DetailTransaksi.java # Model baris rincian item transaksi (Encapsulation)
        │   ├── Transaksi.java       # Model header transaksi (Komposisi & Asosiasi)
        │   └── Struk.java           # Utilitas cetak & ekspor berkas nota belanja (.txt)
        │
        └── dao/                     # Layer Data Access Object (PreparedStatement)
            ├── BarangDao.java       # DAO barang (Overloading cari)
            ├── MemberDao.java       # DAO member pelanggan
            ├── SupplierDao.java     # DAO supplier pemasok
            ├── PenggunaDao.java     # DAO login & registrasi polimorfik
            └── TransaksiDao.java    # DAO transaksi atomik ACID multi-tabel
```

---

## ⚙️ Prasyarat Lingkungan (Environment)

- **Sistem Operasi**: Windows 10 / 11, Linux, atau macOS.
- **Java Development Kit (JDK)**: JDK 8 / 11 / 17 / 21+  
  *(Path JDK yang digunakan saat pengembangan: `C:\Users\NITRO\.jdks\openjdk-26.0.2.1`)*
- **Basis Data**: MySQL Server 5.7+ / 8.x atau MariaDB (via XAMPP) pada `localhost:3306`.
  - User: `root`
  - Password: `(kosong / default)`
  - Nama Database: `kasir`
- **Driver JDBC**: MySQL Connector/J (`mysql-connector-java.jar`, versi 5.1.49 atau 8.x).  
  Driver **tidak disertakan** di dalam repositori ini. Unduh dari [situs resmi MySQL](https://dev.mysql.com/downloads/connector/j/), lalu tambahkan ke proyek secara manual melalui panel **Project → klik kanan Libraries → Add JAR/Folder**.  
  > **Catatan**: Berkas `nbproject/project.properties` di repo ini merujuk path absolut milik pengembang asli (`D:\SCHOOL\PBO\...`). Setelah menambahkan JAR, NetBeans akan memperbarui path tersebut secara otomatis sesuai komputer Anda.

---

## 🚀 Langkah Menjalankan Aplikasi

Ikuti tahapan instalasi basis data dan kompilasi berikut secara berurutan:

### 1. Persiapan Basis Data MySQL
1. Jalankan service **MySQL** melalui XAMPP Control Panel (pastikan port `3306` aktif).
2. Buka antarmuka phpMyAdmin pada peramban web (`http://localhost/phpmyadmin`).
3. Masuk ke tab **Impor** (atau **Import**), klik tombol **Choose File**, pilih berkas `database_kasir.sql`, lalu klik tombol **Impor** / **Kirim** di bagian bawah.
   - Skrip ini akan secara otomatis membuat basis data `kasir` beserta seluruh tabel lengkap (`admin`, `petugaskasir`, `supplier`, `databarang`, `member`, `transaksi`, `detail_transaksi`) dan data contoh yang siap digunakan.
4. **Catatan Skrip Pembaruan Migrasi**:
   - Berkas [`schema_tambahan.sql`](schema_tambahan.sql) dan [`schema_relasi.sql`](schema_relasi.sql) **hanya digunakan untuk memperbarui basis data lama** yang dibuat sebelum relasi ditambahkan.
   - Pada instalasi baru yang menggunakan `database_kasir.sql`, kedua berkas skrip tersebut **tidak perlu dijalankan**.

### 2. Membuka dan Menjalankan Proyek di Apache NetBeans
1. Buka **Apache NetBeans IDE**.
2. Pilih menu **File** ➡️ **Open Project**, lalu pilih folder proyek `POS-KasirJAVA-PBO`.
3. Tambahkan driver MySQL JDBC ke proyek jika belum ada:
   - Unduh MySQL Connector/J dari [situs resmi MySQL](https://dev.mysql.com/downloads/connector/j/).
   - Pada panel Project, klik kanan pada node **Libraries** ➡️ **Add JAR/Folder...**, lalu pilih file JAR yang sudah diunduh.
   - **Catatan**: `nbproject/project.properties` di repo ini merujuk path absolut pengembang asli dan akan diperbarui otomatis oleh NetBeans setelah JAR ditambahkan.
4. Klik kanan nama proyek pada panel Projects, lalu pilih **Clean and Build**.
5. Untuk menjalankan sistem, klik kanan file `src/main/MenuUtama.java`, lalu pilih **Run File** (atau tekan `Shift + F6`).

---

## 👤 Akun Uji Coba (Default Credentials)

Sesuai dengan data akun bawaan yang tercantum di dalam `database_kasir.sql`, akun pengujian berikut dapat langsung digunakan:

| Peran Akun | Form Login | Username | Password | Hak Akses & Keterangan |
| :--- | :--- | :--- | :--- | :--- |
| **Administrator** | Login Admin | `admin` | `admin123` | Akses penuh dashboard admin: kelola barang, supplier, member, registrasi karyawan, dan riwayat transaksi. |
| **Petugas Kasir** | Login Kasir | `kasir1` | `kasir123` | Akses kasir: transaksi penjualan, pemilihan member, kalkulasi poin reward, dan cetak struk nota belanja. |

---

## 🏷️ Atribusi & Riwayat Pengembangan

Proyek ini dikembangkan dari repositori sumber terbuka:
- **Repositori Asal**: [https://github.com/rahmattulus/POS-KasirJAVA](https://github.com/rahmattulus/POS-KasirJAVA)
- **Penulis Asli**: Rahmat Tulus

### Daftar Bagian yang Ditambahkan (Refactoring Berorientasi Objek)
Seluruh bagian kode baru dan hasil modifikasi pada proyek ini ditandai secara konsisten menggunakan komentar `// [TAMBAHAN]`:
1. **Arsitektur Berlapis**: Pembentukan package domain `main.model` dan package persistensi `main.dao`.
2. **Pemberantasan Celah SQL Injection**: Penggantian seluruh query penggabungan string langsung menggunakan `PreparedStatement` berparameter.
3. **Pemusatan Koneksi Basis Data**: Penambahan method statis `koneksi.buka()` pada `main.koneksi` yang dipakai oleh seluruh kelas DAO.
4. **Penerapan 4 Pilar OOP Penuh**:
   - *Encapsulation*: Setter bervalidasi dan method domain bisnis pada `Barang`, `Member`, `Supplier`, `Transaksi`, dan `DetailTransaksi`.
   - *Inheritance*: Pewarisan superclass abstrak `Pengguna` ke subclass `Admin` dan `Kasir`.
   - *Abstraction*: Kontrak generic interface `InterfaceDao<T>` dan abstract class `Pengguna`.
   - *Polymorphism*: Dynamic dispatch pada `PenggunaDao.login()` serta static overloading method pada `BarangDao.cari(...)` dan `Member.tambahPoin(...)`.
5. **Transaksi Penjualan Atomik (ACID)**: Eksekusi transaksi multi-tabel (`transaksi`, `detail_transaksi`, pengurangan stok `databarang`, penambahan poin `member`) di dalam blok atomik `commit` dan `rollback`.
6. **Class Utilitas Struk**: Format nota monospaced rapi, dialog pratinjau, dan ekspor berkas fisik `.txt`.
7. **Form Riwayat Penjualan**: Form `riwayatTransaksi` dengan pemuatan data relasi, rincian barang per faktur, dan opsi cetak ulang nota.
8. **Relasi Supplier ➡️ Barang (One-to-Many)**: Foreign key `id_supplier` (ON DELETE SET NULL), combo box supplier di form barang, dan konfirmasi proteksi sebelum menghapus supplier.
9. **Relasi Member ➡️ Transaksi (One-to-Many) & Poin**: Foreign key `id_member` (ON DELETE SET NULL), kolom persisten `bayar` dan `kembalian`, combo box member di kasir, dan penambahan poin reward belanja otomatis.
