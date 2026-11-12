
package main.model;

import java.sql.SQLException;
import java.util.List;

public interface InterfaceDao<T> {

    void simpan(T entitas) throws SQLException;

    void ubah(T entitas) throws SQLException;

    void hapus(int id) throws SQLException;

    List<T> ambilSemua() throws SQLException;

    T cariById(int id) throws SQLException;
}

