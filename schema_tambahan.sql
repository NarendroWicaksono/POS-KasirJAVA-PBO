-- ============================================================
-- [TAMBAHAN] SKRIP SKEMA DATABASE UNTUK FITUR DETAIL TRANSAKSI
-- Proyek: POS-KasirJAVA-PBO
-- Database: kasir
-- 
-- CATATAN:
-- 1. Jalankan skrip ini di database MySQL Anda (misal via phpMyAdmin / HeidiSQL).
-- 2. Skrip ini aman dijalankan karena menggunakan IF NOT EXISTS.
-- 3. Menghubungkan nomor transaksi pada tabel header 'transaksi' 
--    dengan rincian barang yang dibeli pada 'detail_transaksi'.
-- ============================================================

USE `kasir`;

-- 1. Membuat tabel detail_transaksi untuk menyimpan rincian barang tiap transaksi
CREATE TABLE IF NOT EXISTS `detail_transaksi` (
    `id_detail` INT NOT NULL AUTO_INCREMENT,
    `no_transaksi` VARCHAR(50) NOT NULL,
    `kode_barang` BIGINT NOT NULL,
    `nama_barang` VARCHAR(100) NOT NULL,
    `harga` INT NOT NULL,
    `qty` INT NOT NULL,
    `jumlah` INT NOT NULL,
    PRIMARY KEY (`id_detail`),
    INDEX `idx_no_transaksi` (`no_transaksi`),
    INDEX `idx_kode_barang` (`kode_barang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Keterangan Relasi:
-- Relasi One-to-Many: 1 transaksi memiliki banyak detail_transaksi
-- no_transaksi merujuk pada transaksi(no_transaksi)

