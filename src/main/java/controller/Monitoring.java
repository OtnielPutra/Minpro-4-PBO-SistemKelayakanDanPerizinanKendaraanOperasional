/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.dao.KendaraanDAO;
import model.dao.PerizinanDAO;
import java.util.Collections;
import java.util.List;
import model.Kendaraan;
import model.KendaraanLayak;
import model.KendaraanTidakLayak;
import model.Perizinan;

/**
 *
 */
interface Manajemen<T> {

    boolean tambah(T data);

    boolean hapus(String kunci);

    List<T> getDaftar();
}

/**
 * Controller: mengelola data kendaraan dan perizinan.
 * Data sekarang disimpan di database lewat DAO (bukan ArrayList lagi).
 * Tidak ada input/output layar di sini (itu tugas View).
 */
public class Monitoring implements Manajemen<Kendaraan> {

    private final KendaraanDAO kendaraanDAO = new KendaraanDAO();
    private final PerizinanDAO perizinanDAO = new PerizinanDAO();

    // Data awal diisi lewat database.sql, bukan lewat constructor ini.
    public Monitoring() {
    }

    // Getter mengembalikan daftar read-only, jadi pihak luar tidak bisa
    // menambah/menghapus data langsung tanpa lewat method di class ini.
    @Override
    public List<Kendaraan> getDaftar() {
        return Collections.unmodifiableList(kendaraanDAO.getSemua());
    }

    public List<Kendaraan> getDaftarKendaraan() {
        return getDaftar();
    }

    public List<Perizinan> getDaftarPerizinan() {
        return Collections.unmodifiableList(perizinanDAO.getSemua());
    }

    // Cek apakah plat nomor sudah terdaftar (untuk validasi di View)
    public boolean platTerdaftar(String plat) {
        return kendaraanDAO.cariByPlat(plat) != null;
    }

    // Mencari kendaraan berdasarkan id (null jika tidak ada)
    public Kendaraan cariKendaraan(int idKendaraan) {
        return kendaraanDAO.cariById(idKendaraan);
    }

    // Relasi one-to-one: satu kendaraan hanya boleh punya satu izin
    public boolean sudahPunyaIzin(int idKendaraan) {
        return perizinanDAO.sudahPunyaIzin(idKendaraan);
    }

    // Overloading tambah: menerima objek
    // Data kosong atau plat kembar ditolak di sini, jadi data selalu valid
    @Override
    public boolean tambah(Kendaraan kendaraan) {

        if (kendaraan == null || platTerdaftar(kendaraan.getPlatNomor())) {
            return false;
        }

        return kendaraanDAO.tambah(kendaraan);
    }

    // Overloading tambah: menerima data mentah
    public boolean tambah(
        String plat,
        String jenis,
        String merk,
        int tahun,
        String kondisi
    ) {
        return tambah(buatKendaraan(plat, jenis, merk, tahun, kondisi));
    }

    // Overloading hapus: berdasarkan plat nomor
    @Override
    public boolean hapus(String plat) {
        return kendaraanDAO.hapusByPlat(plat);
    }

    // Overloading hapus: berdasarkan id_kendaraan
    public boolean hapus(int idKendaraan) {
        return kendaraanDAO.hapusById(idKendaraan);
    }

    // Overloading updateKondisi: berdasarkan plat nomor
    public boolean updateKondisi(String plat, String kondisiBaru) {
        return kendaraanDAO.updateKondisi(plat, kondisiBaru);
    }

    // Overloading updateKondisi: berdasarkan id_kendaraan
    public boolean updateKondisi(int idKendaraan, String kondisiBaru) {
        return kendaraanDAO.updateKondisiById(idKendaraan, kondisiBaru);
    }

    // Izin hanya bisa dibuat untuk kendaraan yang ada dan belum punya izin
    public boolean tambahPerizinan(Perizinan perizinan) {

        if (perizinan == null
            || cariKendaraan(perizinan.getIdKendaraan()) == null
            || sudahPunyaIzin(perizinan.getIdKendaraan())) {
            return false;
        }

        return perizinanDAO.tambah(perizinan);
    }

    private Kendaraan buatKendaraan(
        String plat,
        String jenis,
        String merk,
        int tahun,
        String kondisi
    ) {

        if (kondisi.equals(Kendaraan.LAYAK)) {
            return new KendaraanLayak(plat, jenis, merk, tahun);
        }

        return new KendaraanTidakLayak(plat, jenis, merk, tahun);
    }
}
