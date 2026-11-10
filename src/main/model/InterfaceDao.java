// [TAMBAHAN] - InterfaceDao.java
// Interface generic untuk operasi data (DAO pattern).
// Menerapkan prinsip OOP: Abstraction dan Polymorphism.
// Setiap class DAO yang mengimplementasikan interface ini
// WAJIB mendefinisikan semua method sesuai kebutuhan entitasnya.
package main.model;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface generic untuk operasi CRUD (Create, Read, Update, Delete).
 * Menggunakan Java Generics (tipe parameter T) agar bisa dipakai
 * oleh semua class DAO tanpa menulis ulang struktur yang sama.
 *
 * Ini adalah penerapan ABSTRACTION: kita mendefinisikan "kontrak"
 * tanpa menentukan detail implementasinya.
 *
 * @param <T> Tipe entitas yang dikelola (misal: Barang, Member, dll.)
 * @author Tambahan OOP
 */
public interface InterfaceDao<T> {

    /**
     * Menyimpan satu entitas baru ke database.
     * @param entitas objek yang akan disimpan
     * @throws SQLException jika terjadi kesalahan database
     */
    void simpan(T entitas) throws SQLException;

    /**
     * Mengubah/memperbarui data entitas yang sudah ada di database.
     * @param entitas objek dengan data yang sudah diperbarui
     * @throws SQLException jika terjadi kesalahan database
     */
    void ubah(T entitas) throws SQLException;

    /**
     * Menghapus entitas dari database berdasarkan ID.
     * @param id ID unik entitas yang akan dihapus
     * @throws SQLException jika terjadi kesalahan database
     */
    void hapus(int id) throws SQLException;

    /**
     * Mengambil seluruh data entitas dari database.
     * @return List berisi semua entitas
     * @throws SQLException jika terjadi kesalahan database
     */
    List<T> ambilSemua() throws SQLException;

    /**
     * Mencari satu entitas berdasarkan ID.
     * @param id ID unik entitas yang dicari
     * @return objek entitas, atau null jika tidak ditemukan
     * @throws SQLException jika terjadi kesalahan database
     */
    T cariById(int id) throws SQLException;
}

