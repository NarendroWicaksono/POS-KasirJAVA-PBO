
> **Mata Kuliah**: Pemrograman Berorientasi Objek (PBO)  
> **Projek**: Aplikasi Kasir & Point of Sale (POS) Toko Retail  
> **Bahasa & Platform**: Java Swing + MySQL Database

---

## 🧭 1. ALUR KERJA TIAP FITUR APLIKASI

1. **Alur Login & Pemilihan Peran**:
   - Pengguna membuka jendela `MenuUtama` dan memilih menu **ADMIN** atau **KASIR**.
   - Input username dan password diverifikasi oleh `PenggunaDao.login(username, password)`.
   - Menggunakan konsep **Polymorphism**, method ini memeriksa tabel `admin` terlebih dahulu, lalu tabel `petugaskasir`. Jika ditemukan, objek konkret `Admin` atau `Kasir` dikembalikan sebagai tipe referensi `Pengguna`.
   - Sistem melakukan pengecekan `instanceof` untuk memastikan pengguna hanya bisa masuk ke dashboard yang sesuai hak aksesnya.

2. **Alur Kelola Data Barang (Admin - `tambahBarang`)**:
   - Admin memasukkan kode, nama, harga beli, harga jual, dan stok.
   - Tombol **Add** membungkus input ke dalam objek `Barang` dengan validasi otomatis (misal harga & stok tidak boleh negatif). Objek dikirim ke `BarangDao.simpan(barang)`.
   - Tombol **Update** memanggil `BarangDao.ubah(barang)`.
   - Tombol **Delete** meminta konfirmasi pop-up lalu memanggil `BarangDao.hapus(kode)`.
   - Tabel menampilkan daftar barang yang dimuat secara realtime melalui `BarangDao.ambilSemua()`.

3. **Alur Transaksi Penjualan & Bayar (Kasir - `dashbordKasir`)**:
   - Kasir mencari dan memasukkan item belanjaan pelanggan ke dalam tabel belanja.
   - Sistem menghitung total belanja secara otomatis.
   - Saat kasir memasukkan nominal bayar dan menekan tombol **BAYAR**, sistem memvalidasi kelayakan nominal bayar.
   - Objek `Transaksi` dibuat, lalu seluruh item keranjang dibungkus menjadi koleksi `DetailTransaksi` dan ditambahkan via `transaksi.tambahDetail(item)`.
   - Objek `Transaksi` diproses oleh `TransaksiDao.simpanTransaksiLengkap(transaksi)` dalam **satu transaksi database atomik** (`setAutoCommit(false)`), yang sekaligus memotong stok barang di tabel `databarang`.
   - Jika berhasil, transaksi di-`commit()`, lalu objek `Struk` dipanggil untuk menampilkan pop-up nota belanja dengan opsi penyimpanan file teks `.txt`.

4. **Alur Riwayat Penjualan (`riwayatTransaksi`)**:
   - Admin dapat membuka menu Riwayat Transaksi.
   - `TransaksiDao.ambilSemua()` membaca daftar transaksi dari database dan menampilkannya di tabel.
   - Admin dapat mengklik salah satu baris nota untuk melihat rincian item (`DetailTransaksi`) yang dibeli pada nota tersebut via `TransaksiDao.ambilDetail(noTransaksi)`.

5. **Alur Kelola Member & Poin Pelanggan (`dataMember`)**:
   - Menyimpan data member dan poin belanja pelanggan menggunakan `MemberDao` dan class model `Member`.
   - Class `Member` memiliki method `tambahPoin()` dan `tukarPoin()` untuk memfasilitasi skema loyalitas pelanggan.

6. **Alur Kelola Supplier (`dataSupplier`)**:
   - Mengelola data vendor/pemasok barang toko (nama, email, no rekening, NPWP, alamat) menggunakan class `Supplier` dan `SupplierDao`.

7. **Alur Registrasi Karyawan (`registrasi`)**:
   - Admin toko dapat mendaftarkan akun kasir atau admin baru.
   - Menggunakan method polimorfik `PenggunaDao.simpan(Pengguna p)`, di mana sistem mengenali apakah objek yang dikirimkan adalah `Admin` atau `Kasir` dan menyimpannya ke tabel database yang tepat.

---

## 🏛️ 2. PEMETAAN 4 PILAR OOP PADA KODE PROJEK

| Pilar OOP | File & Class | Baris Kode Relevan | Alasan Teknis & Penerapan di Kode |
| :--- | :--- | :--- | :--- |
| **Encapsulation** | `src/main/model/Barang.java`<br>Class: `Barang` | Baris 14–22 (atribut)<br>Baris 62–95 (setter/getter) | Semua variabel (`kodeBarang`, `namaBarang`, `hargaBeli`, `hargaJual`, `stokBarang`) dibuat bertipe `private`. Data hanya bisa dimodifikasi lewat setter yang memvalidasi bahwa harga jual tidak boleh lebih kecil dari harga beli dan stok tidak boleh negatif. Method bisnis `kurangiStok()` membungkus perubahan stok secara aman. |
| **Inheritance** | `src/main/model/Admin.java`<br>`src/main/model/Kasir.java`<br>Class: `Admin`, `Kasir` | Baris 14 di `Admin.java`<br>Baris 14 di `Kasir.java` | Keduanya menggunakan keyword `extends Pengguna`. Atribut dasar seperti `id`, `username`, `password`, dan `email` didefinisikan sekali di kelas induk `Pengguna` dan diwarisi secara utuh oleh subkelas tanpa duplikasi kode. |
| **Abstraction** | `src/main/model/InterfaceDao.java`<br>`src/main/model/Pengguna.java` | Seluruh baris di `InterfaceDao.java`<br>Baris 38–48 di `Pengguna.java` | 1. `InterfaceDao<T>` menyediakan cetak biru (kontrak) abstrak untuk operasi CRUD (`simpan`, `ubah`, `hapus`, `ambilSemua`, `cariById`) tanpa mempedulikan implementasi SQL di dalamnya.<br>2. `Pengguna` adalah *abstract class* yang mendeklarasikan method abstrak `getHakAkses()`, `tampilkanMenu()`, dan `getDashboardTujuan()` yang **wajib** diimplementasikan secara berbeda oleh tiap turunan. |
| **Polymorphism (Dynamic / Overriding)** | `src/main/model/Admin.java`<br>`src/main/model/Kasir.java`<br>`src/main/dao/PenggunaDao.java` | Baris 37–51 di `Admin.java`<br>Baris 37–51 di `Kasir.java`<br>Baris 44–82 di `PenggunaDao.java` | 1. **Overriding**: `Admin` mengembalikan hak akses "ADMINISTRATOR_FULL_ACCESS", sedangkan `Kasir` mengembalikan "KASIR_TRANSAKSI_PENJUALAN".<br>2. **Polymorphic Return**: Method `PenggunaDao.login()` mengembalikan tipe referensi induk `Pengguna`, namun saat runtime objek yang dikembalikan bisa berupa `Admin` atau `Kasir`. |
| **Polymorphism (Static / Overloading)** | `src/main/dao/BarangDao.java`<br>Class: `BarangDao` | Baris 101–158 di `BarangDao.java` | Terdapat method dengan nama yang sama persis (`cari`) tetapi memiliki parameter berbeda:<br>- `cari(long kodeBarang)` : mencari 1 barang berdasarkan barcode/kode.<br>- `cari(String namaBarang)` : mencari daftar barang berdasarkan kemiripan nama.<br>- `cari(int minStok, int maxStok)` : mencari daftar barang berdasarkan rentang stok. |
| **Composition / Association** | `src/main/model/Transaksi.java`<br>`src/main/model/Struk.java` | Baris 23 di `Transaksi.java`<br>Baris 16 di `Struk.java` | **Komposisi**: Objek `Transaksi` memiliki relasi *has-a* terhadap daftar `List<DetailTransaksi>`. Jika transaksi tidak ada, maka detail transaksi tidak memiliki makna tersendiri.<br>**Asosiasi**: Objek `Struk` menggunakan objek `Transaksi` untuk menghasilkan teks nota. |

---


### Q1: "Mengapa Anda memisahkan kode antara package `main`, `main.model`, dan `main.dao`?"  
> "Kami menerapkan prinsip arsitektur **Separation of Concerns (SoC)** atau arsitektur berlapis. Kode asli sistem ini sebelumnya menumpuk logika tampilan, logika bisnis, dan query SQL dalam satu file JFrame, yang membuat kode sulit diuji dan rawan error. Dengan memisahkannya:
> - `main` murni bertugas mengatur antarmuka pengguna (UI/Swing).
> - `main.model` merepresentasikan entitas bisnis dan memegang aturan validasi domain (OOP murni).
> - `main.dao` bertanggung jawab khusus pada persistensi database menggunakan `PreparedStatement`.  
> Jika suatu saat database diganti dari MySQL ke PostgreSQL, kami hanya perlu mengubah package DAO tanpa menyentuh tampilan GUI sama sekali."

---

### Q2: "Di mana Anda menerapkan Abstraction? Apa beda Interface dan Abstract Class di proyek Anda?"
> "Abstraction kami terapkan di dua tempat:
> 1. **Interface `InterfaceDao<T>`**: Mendefinisikan kontrak perilaku umum operasi persistensi data CRUD. Interface ini memakai generic `<T>`, sehingga bisa diimplementasikan oleh `BarangDao`, `MemberDao`, `SupplierDao`, dan `PenggunaDao`.
> 2. **Abstract Class `Pengguna`**: Menyediakan blueprint entitas pengguna sekaligus menurunkan atribut bersama (`id`, `username`, `password`, `email`). Di dalamnya terdapat abstract method `getHakAkses()` dan `getDashboardTujuan()`.
> **Perbedaannya**: `Pengguna` kami buat abstract class karena memiliki atribut konkret dan implementasi kode bersama (*state & partial behavior*), sedangkan `InterfaceDao<T>` murni sebuah kontrak antarmuka tanpa atribut instansiasi (*pure contract*)."

---

### Q3: "Tunjukkan di mana Anda menerapkan Polymorphism dinamis (Overriding)!"
> "Polymorphism dinamis terlihat jelas pada pewarisan kelas `Pengguna` ke kelas `Admin` dan `Kasir`:
> Pada method `getHakAkses()`, kelas `Admin` melakukan `@Override` untuk mengembalikan *'ADMINISTRATOR_FULL_ACCESS'*, sedangkan kelas `Kasir` mengembalikan *'KASIR_TRANSAKSI_PENJUALAN'*.
> Ketika `PenggunaDao.login()` dipanggil, tipe kembalian variabelnya adalah kelas induk `Pengguna`. Namun pada saat runtime di form login, pemanggilan `pengguna.getHakAkses()` secara dinamis mengeksekusi method milik subclass yang sebenarnya (baik `Admin` maupun `Kasir`)."

---

### Q4: "Apakah ada penerapan Method Overloading (Polymorphism Statis)? Di mana letaknya?"
> "Ada, kami menerapkannya di class `main.dao.BarangDao` pada method `cari`:
> 1. `public Barang cari(long kodeBarang)` : mencari satu barang spesifik berdasarkan Primary Key `code_barang`.
> 2. `public List<Barang> cari(String namaBarang)` : mencari daftar barang berdasarkan kecocokan nama (`LIKE ?`).
> 3. `public List<Barang> cari(int minStok, int maxStok)` : mencari daftar barang berdasarkan batas stok kritis.  
> Ketiganya memiliki nama method yang sama tetapi dibedakan oleh tipe dan jumlah parameternya, yang ditentukan pada saat kompilasi (*compile-time polymorphism*)."

---

### Q5: "Mengapa atribut di class model harus dibuat `private`? Kenapa tidak `public` saja supaya mudah diakses?" 
> "Itu merupakan prinsip fundamental **Encapsulation**. Jika atribut dibuat `public`, bagian luar sistem (misalnya event handler GUI) bisa mengubah isi variabel secara bebas dan tidak terkontrol, misalnya mengisi stok barang dengan nilai negatif (-10) atau harga jual Rp 0.  
> Dengan atribut `private`, semua akses harus melewati `setter` yang sudah ditanami logika validasi (misalnya di `Barang.java`: jika stok < 0, sistem melempar `IllegalArgumentException`). Dengan demikian, integritas status objek selalu terjaga (*data integrity*)."

---

### Q6: "Bagaimana sistem Anda mencegah terjadinya SQL Injection?"
> "Pada kode asli terdahulu, query ditulis dengan konkatenasi string biasa seperti `"SELECT * FROM admin WHERE username = '" + username.getText() + "'"` yang sangat rentan SQL Injection.  
> Pada versi refactoring ini, seluruh query di package `main.dao` telah diganti 100% menggunakan **`PreparedStatement`** dengan parameter penampung tanda tanya (`?`). Nilai input pengguna dikirim terpisah dari struktur perintah SQL via method seperti `ps.setString(1, username)`, sehingga database memperlakukan masukan pengguna murni sebagai data literal, bukan sebagai kode instruksi SQL yang dapat dieksekusi."

---

### Q7: "Apa yang terjadi jika saat transaksi kasir, koneksi listrik putus saat stok barang sedang dikurangi? Bagaimana Anda menjamin integritas datanya?"
> "Kami menerapkan **Database Transaction Management** secara atomik (*ACID properties*) di `TransaksiDao.simpanTransaksiLengkap()`.
> Kami mematikan mode autocommit melalui `con.setAutoCommit(false)`. Urutan operasi penyimpanan:
> 1. Simpan header `transaksi`
> 2. Simpan setiap item di `detail_transaksi`
> 3. Kurangi stok di `databarang`
> 
> Seluruh operasi ini dibungkus dalam blok `try-catch`. Jika seluruh langkah berhasil, barulah `con.commit()` dipanggil. Namun jika terjadi kegagalan atau exception di tengah jalan, blok `catch` akan memanggil `con.rollback()`, sehingga seluruh perubahan dibatalkan dan database kembali ke kondisi awal. Tidak akan terjadi kondisi header tersimpan namun detail atau stok gagal terpotong."

---

### Q8: "Apa relasi antara class `Transaksi` dan `DetailTransaksi`?"  
> "Relasinya adalah **Komposisi (Composition)**, yang merupakan bentuk relasi *has-a* yang kuat.  
> Class `Transaksi` memiliki atribut `List<DetailTransaksi> daftarDetail`. Sebuah `DetailTransaksi` tidak memiliki eksistensi mandiri tanpa adanya objek `Transaksi` induk tempat ia terikat. Jika suatu transaksi dihapus atau dibatalkan, maka seluruh rincian item transaksi di dalamnya ikut terhapus."

---

### Q9: "Mengapa Anda menggunakan try-with-resources pada koneksi database di DAO?"
> "Karena `Connection`, `PreparedStatement`, dan `ResultSet` mengimplementasikan interface `java.lang.AutoCloseable`. Dengan sintaks `try (Connection con = bukaKoneksi(); PreparedStatement ps = con.prepareStatement(sql))`, Java akan secara otomatis menutup koneksi tersebut ketika blok try selesai, baik itu selesai normal maupun saat terjadi exception. Ini mencegah terjadinya **memory leak** dan penumpukan koneksi menggantung (*connection leak*) di server MySQL."

---

### Q10: "Bagaimana cara kerja class `Struk`?"
> "Class `Struk` bertugas memformat informasi dari objek `Transaksi` menjadi teks nota kasir yang rapi (menampilkan nomor transaksi, nama kasir, daftar barang, jumlah, subtotal, total harga, uang bayar, dan kembalian).  
> Method `tampilkanDialog()` menampilkan nota dalam pop-up dialog monospaced (`Font.MONOSPACED`), dan menyediakan opsi interaktif bagi kasir untuk langsung menyimpannya ke dalam file teks `.txt` di penyimpanan komputer via `simpanKeFile()`."

---

### Q11: "Apa fungsi keyword `super` pada konstruktor `Admin` dan `Kasir`?"
> "Keyword `super(id, username, password, email)` digunakan untuk memanggil konstruktor milik superclass-nya, yaitu `Pengguna`. Ini memastikan bahwa inisialisasi atribut-atribut dasar yang dideklarasikan di kelas induk dijalankan dengan benar sebelum subclass menambahkan logika spesifik miliknya."

---

### Q12: "Bagaimana Anda menangani validasi input jika pengguna memasukkan huruf pada field harga atau stok?" 
> "Pada event handler GUI (seperti di `tambahBarang`, `dataMember`, dan `dashbordKasir`), konversi string ke angka dilakukan di dalam blok `try-catch` yang menangkap **`NumberFormatException`**.  
> Jika pengguna memasukkan karakter non-angka, eksekusi kode tidak akan *crash*, melainkan sistem menangkap exception tersebut dan menampilkan pesan dialog ramah: *'Pastikan Kode, Harga, dan Stok berupa angka!'*."

---

### Q13: "Mengapa file `config.java` asli tidak Anda gunakan dan di-exclude?"
> "Berdasarkan audit teknis kami, file `config.java` bawaan repo lama sudah usang dan rusak: file tersebut mengimpor driver kuno `com.mysql.jdbc.Driver` yang sudah di-*deprecate*, memiliki sintaks URL JDBC yang keliru, dan sama sekali tidak pernah dipakai oleh form manapun di proyek asli. Oleh karena itu, koneksi kami standarisasi langsung secara aman di tiap kelas DAO menggunakan konektor modern."

---

### Q14: "Jelaskan alur polimorfisme pada form `registrasi.java`!" 
> "Di `registrasi.java`, terdapat tombol *+ Tambah Kasir* dan *+ Tambah Admin*.
> - Ketika tombol kasir ditekan, form membuat objek `new Kasir(...)`.
> - Ketika tombol admin ditekan, form membuat objek `new Admin(...)`.
> 
> Kedua objek ini kemudian dikirim ke method yang sama: `penggunaDao.simpan(Pengguna pengguna)`.  
> Method `simpan` di DAO menerima parameter bertipe superclass `Pengguna`. Di dalam DAO, sistem mengevaluasi `if (pengguna instanceof Admin)` atau `if (pengguna instanceof Kasir)` untuk menentukan secara otomatis tabel database yang dituju (`admin` atau `petugaskasir`). Ini membuktikan fleksibilitas polimorfisme di mana method yang sama dapat memperlakukan berbagai subtipe secara elegan."

---

### Q15: "Jika aplikasi ini ingin dikembangkan ke skala produksi (enterprise), apa rekomendasi perbaikan Anda selanjutnya?"  
> "Terdapat tiga poin peningkatan utama:
> 1. **Password Hashing**: Mengganti penyimpanan plaintext password di database dengan enkripsi hash modern bergaram seperti **BCrypt** atau **PBKDF2** agar data kredensial tidak terbaca jika database bocor.
> 2. **Connection Pooling**: Menggunakan connection pool seperti **HikariCP** agar pembukaan koneksi database tidak dilakukan berulang kali per query (*overhead cost*).
> 3. **Migrasi UI**: Memigrasikan tampilan Swing lawas ke JavaFX atau framework berbasis web/REST API dengan backend Spring Boot untuk kapabilitas multi-terminal kasir tersinkronisasi."
