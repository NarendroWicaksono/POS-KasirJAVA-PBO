# 🛒 Sistem Informasi Kasir & Point of Sale (POS) Toko
> **Tugas Projek Mata Kuliah Pemrograman Berorientasi Objek (PBO)**  
> Berbasis Java Swing Desktop & MySQL Database.

---

## 📌 Ringkasan & Informasi Proyek (Legacy & Modern Refactoring)

Aplikasi ini merupakan sistem **Point of Sale (POS)** berbasis desktop untuk manajemen transaksi toko retail, pengelolaan stok barang, data member, supplier, serta pencatatan riwayat transaksi dan struk belanja.

Awalnya proyek ini merupakan kode warisan (*legacy project*) dengan struktur monolitik prosedural (query SQL langsung di dalam event handler JFrame). Melalui audit dan refactoring berorientasi objek (OOP), proyek ini kini telah dipisahkan menjadi arsitektur berlapis:
1. **Layer Presentation (GUI)**: Form Java Swing (`src/main/*.java`).
2. **Layer Data Access Object (DAO)**: Operasi database terisolasi dan aman dari SQL Injection (`src/main/dao/`).
3. **Layer Domain Model**: Objek bisnis yang menerapkan 4 pilar OOP penuh (`src/main/model/`).

---

## 🏛️ Penerapan 4 Pilar OOP

| Pilar OOP | Lokasi Implementasi | Penjelasan / Manfaat |
| :--- | :--- | :--- |
| **Encapsulation** | Semua class di `main.model` (`Barang`, `Member`, `Supplier`, `Transaksi`, `DetailTransaksi`, `Pengguna`) | Atribut `private`, akses hanya via *getter/setter* yang dilengkapi validasi data (misal: stok/harga tidak boleh negatif). |
| **Inheritance** | `Admin extends Pengguna`<br>`Kasir extends Pengguna` | Class turunan mewarisi atribut umum (`id`, `username`, `password`, `email`) dari superclass abstrak `Pengguna`. |
| **Abstraction** | `InterfaceDao<T>` (Interface)<br>`Pengguna` (Abstract Class) | `InterfaceDao<T>` mendefinisikan kontrak CRUD (`simpan`, `ubah`, `hapus`, `ambilSemua`, `cariById`). `Pengguna` memiliki method abstrak `getHakAkses()`, `tampilkanMenu()`, dan `getDashboardTujuan()`. |
| **Polymorphism** | - `PenggunaDao.login()`<br>- Overriding di `Admin` & `Kasir`<br>- Overloading di `BarangDao` | Method `login()` mengembalikan tipe referensi superclass `Pengguna`, namun objek konkretnya adalah `Admin` atau `Kasir`. Overloading method `cari(long)`, `cari(String)`, `cari(int, int)` pada `BarangDao`. |

---

## ⚙️ Prasyarat Lingkungan (Environment)

- **Sistem Operasi**: Windows 10/11
- **Java Development Kit (JDK)**: OpenJDK 8 / 11 / 17 / 21+  
  *(Path JDK terdeteksi di sistem ini: `C:\Users\NITRO\.jdks\openjdk-26.0.2.1`)*
- **Database Server**: MySQL (via XAMPP / MariaDB) di `localhost:3306`
  - User: `root`
  - Password: `(kosong)`
  - Nama Database: `kasir`
- **Driver JDBC**: `mysql-connector-java.jar` (versi 5.1.49 atau 8.x) diletakkan di folder `lib/`.

---

## 🗄️ Skema Database & Akun Awal

### 1. Daftar Tabel Utama
- `admin` : Menyimpan kredensial login akun Administrator toko.
- `petugaskasir` : Menyimpan kredensial login akun Petugas Kasir.
- `databarang` : Menyimpan kode barang (PK), nama barang, harga beli, harga jual, dan stok.
- `member` : Menyimpan data pelanggan member toko beserta poin reward.
- `supplier` : Menyimpan data pemasok barang.
- `transaksi` : Menyimpan header penjualan (no_transaksi, petugas_kasir, total, waktu).
- `detail_transaksi` : *(Skrip tambahan di `schema_tambahan.sql`)* Menyimpan rincian item tiap transaksi.

### 2. Akun Bawaan untuk Pengujian
Jika database Anda baru di-import, pastikan ada baris pengguna di tabel:
- **Tabel `admin`**: Username `admin`, Password `admin` (atau sesuaikan data di phpMyAdmin).
- **Tabel `petugaskasir`**: Username `kasir1`, Password `kasir1`.

---

## 🚀 Panduan Menjalankan Lewat PowerShell

Anda dapat mengompilasi dan menjalankan aplikasi secara langsung melalui terminal **PowerShell** tanpa harus membuka NetBeans.

### Langkah 1: Pastikan Database MySQL XAMPP Aktif
1. Buka **XAMPP Control Panel**.
2. Klik tombol **Start** pada modul **MySQL** (dan **Apache** jika butuh phpMyAdmin).
3. Pastikan port `3306` menyala hijau.

---

### Langkah 2: Kompilasi Semua Kode Java (.java ➡️ .class)

Buka PowerShell di direktori proyek (`c:\Users\NITRO\Downloads\POS-KasirJAVA-PBO`), lalu jalankan:

```powershell
# 1. Tentukan path compiler javac
$javac = "C:\Users\NITRO\.jdks\openjdk-26.0.2.1\bin\javac.exe"

# 2. Buat folder keluaran jika belum ada
if (-not (Test-Path "build\classes")) { New-Item -ItemType Directory -Path "build\classes" -Force }

# 3. Kumpulkan semua file .java (kecuali config.java yang usang)
$files = Get-ChildItem -Path src -Recurse -Filter "*.java" | Where-Object { $_.Name -ne "config.java" } | Select-Object -ExpandProperty FullName

# 4. Eksekusi kompilasi
& $javac -cp "src;lib\*" -d build\classes $files
```

*(Jika berhasil tanpa pesan error merah, semua file class telah siap di folder `build\classes`)*.

---

### Langkah 3: Menjalankan Aplikasi (Run Desktop GUI)

Untuk menjalankan jendela utama aplikasi:

```powershell
# 1. Tentukan path runtime java
$java = "C:\Users\NITRO\.jdks\openjdk-26.0.2.1\bin\java.exe"

# 2. Jalankan aplikasi kasir (Menu Utama)
& $java -cp "build\classes;lib\*" main.MenuUtama
```

Atau jika ingin dijalankan di jendela terpisah di latar belakang (background process):
```powershell
Start-Process "C:\Users\NITRO\.jdks\openjdk-26.0.2.1\bin\java.exe" -ArgumentList "-cp `"build\classes;lib\*`" main.MenuUtama"
```

---

## 🛠️ Penyelesaian Masalah (Troubleshooting)

1. **Error: `No suitable driver found for jdbc:mysql://localhost:3306/kasir`**
   - **Penyebab**: File library MySQL Connector JAR belum dimasukkan ke classpath.
   - **Solusi**: 
     - Buat folder `lib` di dalam proyek (`mkdir lib`).
     - Unduh `mysql-connector-j-8.x.jar` atau `mysql-connector-java-5.1.49.jar` lalu masukkan ke folder `lib/`.
     - Jalankan kembali perintah dengan `-cp "build\classes;lib\*"`.

2. **Error: `Communications link failure / Connection refused`**
   - **Penyebab**: MySQL di XAMPP belum menyala atau port 3306 diblokir firewall.
   - **Solusi**: Buka XAMPP Control Panel, klik **Start** pada MySQL.

3. **Aplikasi Terbuka tapi Tidak Bisa Login**
   - **Penyebab**: Tabel `admin` atau `petugaskasir` di database `kasir` masih kosong.
   - **Solusi**: Tambahkan data akun secara manual lewat phpMyAdmin (`http://localhost/phpmyadmin`).

4. **Tabel `detail_transaksi` belum ada**
   - **Solusi**: Buka phpMyAdmin, pilih database `kasir`, masuk ke tab **SQL**, salin dan jalankan skrip dari file [`schema_tambahan.sql`](file:///c:/Users/NITRO/Downloads/POS-KasirJAVA-PBO/schema_tambahan.sql).

---

## 📁 Struktur Direktori Proyek

```text
POS-KasirJAVA-PBO/
├── .gitignore               # Daftar pengabaian file hasil build
├── build.xml                # Skrip Apache Ant bawaan NetBeans
├── schema_tambahan.sql      # Skrip DDL tabel detail_transaksi
├── README.md                # Dokumentasi & panduan lengkap (file ini)
├── lib/                     # Tempat meletakkan mysql-connector-java.jar
├── build/classes/           # Hasil kompilasi bytecode (.class)
└── src/
    └── main/
        ├── MenuUtama.java           # Entry point aplikasi (Pilihan Login Admin/Kasir)
        ├── loginAdmin.java          # Form autentikasi admin
        ├── loginKasir.java          # Form autentikasi kasir
        ├── dashbordAdmin.java       # Menu utama pengelolaan admin
        ├── dashbordKasir.java       # Menu utama kasir & transaksi penjualan
        ├── tambahBarang.java        # Form CRUD barang (Admin)
        ├── listBarang.java          # Form penelusuran barang (Kasir)
        ├── dataMember.java          # Form pengelolaan member & poin pelanggan
        ├── dataSupplier.java        # Form pengelolaan pemasok barang
        ├── registrasi.java          # Form pendaftaran akun kasir/admin baru
        ├── riwayatTransaksi.java    # Form laporan & pencarian riwayat transaksi
        ├── koneksi.java             # Helper koneksi database legacy
        │
        ├── model/                   # [TAMBAHAN OOP] Model POJO & Interface
        │   ├── InterfaceDao.java    # Generic DAO interface
        │   ├── Pengguna.java        # Abstract class superclass akun
        │   ├── Admin.java           # Concrete class Admin
        │   ├── Kasir.java           # Concrete class Kasir
        │   ├── Barang.java          # Domain model barang
        │   ├── Member.java          # Domain model member
        │   ├── Supplier.java        # Domain model supplier
        │   ├── Transaksi.java       # Header transaksi & komposisi detail
        │   ├── DetailTransaksi.java # Item rincian per transaksi
        │   └── Struk.java           # Logika cetak struk nota belanja
        │
        └── dao/                     # [TAMBAHAN OOP] Data Access Object (PreparedStatement)
            ├── BarangDao.java       # Akses data barang (Overloading cari)
            ├── MemberDao.java       # Akses data member
            ├── SupplierDao.java     # Akses data supplier
            ├── PenggunaDao.java     # Akses login polimorfik & registrasi
            └── TransaksiDao.java    # Transaksi atomik DB (Header + Detail + Stok)
```

