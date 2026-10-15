package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Kendaraan;
import model.KendaraanLayak;
import model.KendaraanTidakLayak;

/**
 * DAO (Data Access Object) untuk tabel kendaraan.
 * Semua query memakai PreparedStatement.
 * Hanya mengurus akses database, tanpa input/output layar.
 */
public class KendaraanDAO {

    private static final String SELECT =
        "SELECT id_kendaraan, plat_nomor, jenis, merk, tahun, kondisi "
        + "FROM kendaraan";

    // CREATE
    public boolean tambah(Kendaraan k) {

        String sql = "INSERT INTO kendaraan "
            + "(plat_nomor, jenis, merk, tahun, kondisi) VALUES (?, ?, ?, ?, ?)";

        try (
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, k.getPlatNomor());
            ps.setString(2, k.getJenis());
            ps.setString(3, k.getMerk());
            ps.setInt(4, k.getTahun());
            ps.setString(5, k.getKondisi());

            if (ps.executeUpdate() == 0) {
                return false;
            }

            // Ambil id hasil AUTO_INCREMENT lalu simpan ke objek
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    k.setIdKendaraan(rs.getInt(1));
                }
            }

            return true;

        } catch (SQLException e) {
            System.err.println("Gagal menambah kendaraan: " + e.getMessage());
            return false;
        }
    }

    // READ semua
    public List<Kendaraan> getSemua() {

        List<Kendaraan> daftar = new ArrayList<>();

        try (
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(
                SELECT + " ORDER BY id_kendaraan");
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                daftar.add(buatDariRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("Gagal membaca kendaraan: " + e.getMessage());
        }

        return daftar;
    }

    // READ berdasarkan plat nomor (null jika tidak ditemukan)
    public Kendaraan cariByPlat(String plat) {

        try (
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(
                SELECT + " WHERE plat_nomor = ?")
        ) {
            ps.setString(1, plat);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return buatDariRow(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Gagal mencari kendaraan: " + e.getMessage());
        }

        return null;
    }

    // READ berdasarkan id (null jika tidak ditemukan)
    public Kendaraan cariById(int id) {

        try (
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(
                SELECT + " WHERE id_kendaraan = ?")
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return buatDariRow(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Gagal mencari kendaraan: " + e.getMessage());
        }

        return null;
    }

    // UPDATE kondisi
    public boolean updateKondisi(String plat, String kondisiBaru) {

        String sql = "UPDATE kendaraan SET kondisi = ? WHERE plat_nomor = ?";

        try (
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, kondisiBaru);
            ps.setString(2, plat);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Gagal update kondisi: " + e.getMessage());
            return false;
        }
    }

    // DELETE berdasarkan plat nomor (izin ikut terhapus lewat ON DELETE CASCADE)
    public boolean hapusByPlat(String plat) {
        return hapus("DELETE FROM kendaraan WHERE plat_nomor = ?", plat, 0);
    }

    // DELETE berdasarkan id
    public boolean hapusById(int id) {
        return hapus("DELETE FROM kendaraan WHERE id_kendaraan = ?", null, id);
    }

    private boolean hapus(String sql, String plat, int id) {

        try (
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            if (plat != null) {
                ps.setString(1, plat);
            } else {
                ps.setInt(1, id);
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Gagal menghapus kendaraan: " + e.getMessage());
            return false;
        }
    }

    // Mengubah satu baris database menjadi objek sesuai kondisinya
    // (polymorphism tetap dipakai: Layak / Tidak Layak)
    private Kendaraan buatDariRow(ResultSet rs) throws SQLException {

        String plat = rs.getString("plat_nomor");
        String jenis = rs.getString("jenis");
        String merk = rs.getString("merk");
        int tahun = rs.getInt("tahun");
        String kondisi = rs.getString("kondisi");

        Kendaraan k;

        if (kondisi.equals(Kendaraan.LAYAK)) {
            k = new KendaraanLayak(plat, jenis, merk, tahun);
        } else {
            k = new KendaraanTidakLayak(plat, jenis, merk, tahun);
        }

        k.setIdKendaraan(rs.getInt("id_kendaraan"));
        return k;
    }
}
