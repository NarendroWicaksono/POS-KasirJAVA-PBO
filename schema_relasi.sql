-- ============================================================
-- SKRIP PEMBARUAN SKEMA DATABASE RELASI (schema_relasi.sql)
-- Projek: POS-KasirJAVA-PBO
-- CATATAN: JANGAN DIJALANKAN OTOMATIS OLEH AGEN.
-- USER AKAN MENJALANKANNYA SENDIRI SECARA MANUAL SETELAH BACKUP.
-- ============================================================

USE `kasir`;

-- ------------------------------------------------------------
-- TAHAP 1: PERBAIKAN STRUKTUR TABEL TRANSAKSI
-- Menambahkan kolom bayar dan kembalian untuk menyimpan nominal
-- pembayaran dan uang kembali secara persisten.
-- ------------------------------------------------------------

-- Tambah kolom bayar dan kembalian jika belum ada
ALTER TABLE `transaksi`
  ADD COLUMN `bayar` BIGINT NULL AFTER `total`,
  ADD COLUMN `kembalian` BIGINT NULL AFTER `bayar`;

-- ------------------------------------------------------------
-- TAHAP 2: RELASI SUPPLIER -> BARANG (ONE-TO-MANY)
-- Menambahkan kolom id_supplier pada databarang sebagai foreign key
-- ke supplier(id) dengan aksi ON DELETE SET NULL.
-- Data lama aman karena bernilai NULL secara default.
-- ------------------------------------------------------------

ALTER TABLE `databarang`
  ADD COLUMN `id_supplier` INT NULL AFTER `stok_barang`,
  ADD CONSTRAINT `fk_barang_supplier`
    FOREIGN KEY (`id_supplier`) REFERENCES `supplier`(`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

-- ------------------------------------------------------------
-- TAHAP 3: RELASI MEMBER -> TRANSAKSI (ONE-TO-MANY) DAN POIN REWARD
-- Menambahkan kolom id_member pada tabel transaksi sebagai foreign key
-- ke member(id_member) dengan aksi ON DELETE SET NULL.
-- Pelanggan non-member akan bernilai NULL pada kolom id_member.
-- ------------------------------------------------------------

ALTER TABLE `transaksi`
  ADD COLUMN `id_member` INT NULL AFTER `no_transaksi`,
  ADD CONSTRAINT `fk_transaksi_member`
    FOREIGN KEY (`id_member`) REFERENCES `member`(`id_member`)
    ON DELETE SET NULL
    ON UPDATE CASCADE;


