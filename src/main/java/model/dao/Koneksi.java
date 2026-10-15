package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Pengatur koneksi ke database MySQL (JDBC).
 * Class utilitas: constructor private, tidak perlu dibuat objeknya.
 */
public class Koneksi {

    // Sesuaikan dengan MySQL di komputer Anda
    private static final String URL =
        "jdbc:mysql://localhost:3306/db_monitoring_kendaraan";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Koneksi() {
    }

    // Setiap pemanggilan membuka koneksi baru; dipakai dengan try-with-resources
    public static Connection getKoneksi() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
