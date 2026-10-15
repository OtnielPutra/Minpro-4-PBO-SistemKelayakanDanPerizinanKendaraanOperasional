package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Perizinan;

/**
 * DAO untuk tabel perizinan.
 * Tanggal di model berbentuk String dd-MM-yyyy (sudah divalidasi di View),
 * di sini diubah ke/dari tipe DATE database.
 */
public class PerizinanDAO {

    private static final DateTimeFormatter FORMAT =
        DateTimeFormatter.ofPattern("dd-MM-uuuu");

    // CREATE
    public boolean tambah(Perizinan p) {

        String sql = "INSERT INTO perizinan (id_kendaraan, keterangan, tanggal) "
            + "VALUES (?, ?, ?)";

        try (
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, p.getIdKendaraan());
            ps.setString(2, p.getKeterangan());
            ps.setDate(3, Date.valueOf(LocalDate.parse(p.getTanggal(), FORMAT)));

            if (ps.executeUpdate() == 0) {
                return false;
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setIdPerizinan(rs.getInt(1));
                }
            }

            return true;

        } catch (SQLException e) {
            System.err.println("Gagal menambah perizinan: " + e.getMessage());
            return false;
        }
    }

    // READ semua (JOIN ke kendaraan supaya plat nomor ikut tampil)
    public List<Perizinan> getSemua() {

        List<Perizinan> daftar = new ArrayList<>();

        String sql = "SELECT p.id_perizinan, p.id_kendaraan, k.plat_nomor, "
            + "p.keterangan, p.tanggal "
            + "FROM perizinan p "
            + "JOIN kendaraan k ON p.id_kendaraan = k.id_kendaraan "
            + "ORDER BY p.id_perizinan";

        try (
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {

                Perizinan p = new Perizinan(
                    rs.getInt("id_kendaraan"),
                    rs.getString("keterangan"),
                    rs.getDate("tanggal").toLocalDate().format(FORMAT)
                );

                p.setIdPerizinan(rs.getInt("id_perizinan"));
                p.setPlatNomor(rs.getString("plat_nomor"));
                daftar.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Gagal membaca perizinan: " + e.getMessage());
        }

        return daftar;
    }

    // Cek relasi one-to-one: apakah kendaraan ini sudah punya izin?
    public boolean sudahPunyaIzin(int idKendaraan) {

        String sql = "SELECT 1 FROM perizinan WHERE id_kendaraan = ?";

        try (
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idKendaraan);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("Gagal memeriksa perizinan: " + e.getMessage());
            return false;
        }
    }
}
