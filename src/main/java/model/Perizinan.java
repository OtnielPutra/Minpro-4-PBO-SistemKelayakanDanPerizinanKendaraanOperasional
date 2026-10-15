/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Perizinan {

    // id_perizinan diisi database (AUTO_INCREMENT), jadi tidak ada di constructor
    private int idPerizinan;

    // id_kendaraan = foreign key ke tabel kendaraan
    private final int idKendaraan;

    // hanya untuk tampilan (hasil JOIN), bukan kolom tabel perizinan
    private String platNomor;

    private String keterangan;
    private String tanggal;

    public Perizinan(
        int idKendaraan,
        String keterangan,
        String tanggal
    ) {

        if (idKendaraan <= 0) {
            throw new IllegalArgumentException("ID kendaraan tidak valid.");
        }

        this.idKendaraan = idKendaraan;
        this.keterangan = periksaIsi(keterangan, "Keterangan");
        this.tanggal = periksaIsi(tanggal, "Tanggal");
    }

    // private: pengecekan data tidak boleh kosong, dipakai constructor dan setter
    private static String periksaIsi(String nilai, String nama) {

        if (nilai == null || nilai.trim().isEmpty()) {
            throw new IllegalArgumentException(nama + " tidak boleh kosong.");
        }

        return nilai.trim();
    }

    public int getIdPerizinan() {
        return idPerizinan;
    }

    public void setIdPerizinan(int idPerizinan) {
        this.idPerizinan = idPerizinan;
    }

    public int getIdKendaraan() {
        return idKendaraan;
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public void setPlatNomor(String platNomor) {
        this.platNomor = platNomor;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = periksaIsi(keterangan, "Keterangan");
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = periksaIsi(tanggal, "Tanggal");
    }
}
