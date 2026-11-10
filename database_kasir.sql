-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 10 Nov 2026 pada 12.35
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kasir`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`, `email`) VALUES
(1, 'admin', 'admin123', 'admin@contoh.com');

-- --------------------------------------------------------

--
-- Struktur dari tabel `databarang`
--

CREATE TABLE `databarang` (
  `code_barang` bigint(20) NOT NULL,
  `nama_barang` varchar(150) NOT NULL,
  `harga_beli` int(11) NOT NULL,
  `harga_jual` int(11) NOT NULL,
  `stok_barang` int(11) NOT NULL DEFAULT 0,
  `id_supplier` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `databarang`
--

INSERT INTO `databarang` (`code_barang`, `nama_barang`, `harga_beli`, `harga_jual`, `stok_barang`, `id_supplier`) VALUES
(8991001000011, 'Beras 5 Kg', 55000, 62000, 20, 1),
(8991001000028, 'Minyak Goreng 1L', 14000, 16500, 30, 1),
(8991001000035, 'Gula Pasir 1 Kg', 13000, 15000, 25, 2),
(8991001000042, 'Mie Instan', 2800, 3500, 100, 2),
(8991001000059, 'Teh Celup', 6000, 7500, 40, NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_transaksi`
--

CREATE TABLE `detail_transaksi` (
  `id_detail` int(11) NOT NULL,
  `no_transaksi` varchar(50) NOT NULL,
  `kode_barang` bigint(20) NOT NULL,
  `nama_barang` varchar(150) NOT NULL,
  `harga` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `member`
--

CREATE TABLE `member` (
  `id_member` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `no_tlp` varchar(30) DEFAULT NULL,
  `poin` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `member`
--

INSERT INTO `member` (`id_member`, `username`, `no_tlp`, `poin`) VALUES
(1, 'Budi', '081234567890', 10),
(2, 'Sari', '081298765432', 0);

-- --------------------------------------------------------

--
-- Struktur dari tabel `petugaskasir`
--

CREATE TABLE `petugaskasir` (
  `id_petugas` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `petugaskasir`
--

INSERT INTO `petugaskasir` (`id_petugas`, `username`, `password`, `email`) VALUES
(1, 'kasir1', 'kasir123', 'kasir1@contoh.com');

-- --------------------------------------------------------

--
-- Struktur dari tabel `supplier`
--

CREATE TABLE `supplier` (
  `id` int(11) NOT NULL,
  `nama` varchar(150) NOT NULL,
  `email` varchar(150) DEFAULT NULL,
  `alamat` varchar(255) DEFAULT NULL,
  `npwp` varchar(50) DEFAULT NULL,
  `noRek` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `supplier`
--

INSERT INTO `supplier` (`id`, `nama`, `email`, `alamat`, `npwp`, `noRek`) VALUES
(1, 'PT Sumber Makmur', 'sumber@contoh.com', 'Purwokerto', '01.234.567.8-901.000', '1234567890'),
(2, 'CV Maju Jaya', 'majujaya@contoh.com', 'Purbalingga', '09.876.543.2-109.000', '0987654321');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `no_transaksi` varchar(50) NOT NULL,
  `id_member` int(11) DEFAULT NULL,
  `petugas_kasir` varchar(100) NOT NULL,
  `total` bigint(20) NOT NULL,
  `bayar` bigint(20) DEFAULT NULL,
  `kembalian` bigint(20) DEFAULT NULL,
  `waktu_transaksi` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `databarang`
--
ALTER TABLE `databarang`
  ADD PRIMARY KEY (`code_barang`),
  ADD KEY `fk_barang_supplier` (`id_supplier`);

--
-- Indeks untuk tabel `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD PRIMARY KEY (`id_detail`),
  ADD KEY `idx_no_transaksi` (`no_transaksi`),
  ADD KEY `idx_kode_barang` (`kode_barang`);

--
-- Indeks untuk tabel `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`id_member`);

--
-- Indeks untuk tabel `petugaskasir`
--
ALTER TABLE `petugaskasir`
  ADD PRIMARY KEY (`id_petugas`);

--
-- Indeks untuk tabel `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`no_transaksi`),
  ADD KEY `fk_transaksi_member` (`id_member`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  MODIFY `id_detail` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `supplier`
--
ALTER TABLE `supplier`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `databarang`
--
ALTER TABLE `databarang`
  ADD CONSTRAINT `fk_barang_supplier` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD CONSTRAINT `fk_detail_transaksi` FOREIGN KEY (`no_transaksi`) REFERENCES `transaksi` (`no_transaksi`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `fk_transaksi_member` FOREIGN KEY (`id_member`) REFERENCES `member` (`id_member`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
