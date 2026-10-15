/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.Year;

/**
 * Interface untuk objek yang dapat diperiksa kelayakannya.
 * (Dideklarasikan di file ini, jadi tidak ada file class baru.)
 */
interface Pemeriksaan {

    String cekStatus();
}

/**
 * Abstract class induk untuk semua jenis kendaraan.
 */
public abstract class Kendaraan implements Pemeriksaan {

    // final pada konstanta: nilai status tetap dan tidak boleh diubah
    public static final String LAYAK = "Layak";
    public static final String TIDAK_LAYAK = "Tidak Layak";

    private static final int TAHUN_MINIMAL = 1900;

    // final pada atribut: hanya diisi sekali lewat constructor.
    // Kondisi tidak punya setter agar tidak bertentangan dengan tipe class-nya
    // (KendaraanLayak harus selalu "Layak"). Untuk mengubah kondisi,
    // controller membuat objek baru.
    private final String platNomor;
    private final String kondisi;

    private int idKendaraan;
    private String jenis;
    private String merk;
    private int tahun;

    protected Kendaraan(
        String platNomor,
        String jenis,
        String merk,
        int tahun,
        String kondisi
    ) {

        this.platNomor = periksaIsi(platNomor, "Plat nomor");
        this.jenis = periksaJenis(jenis);
        this.merk = periksaIsi(merk, "Merk");
        this.tahun = periksaTahun(tahun);
        this.kondisi = periksaKondisi(kondisi);
    }

    // ===================== VALIDASI DI DALAM MODEL =====================
    // private: hanya dipakai di class ini (constructor dan setter),
    // jadi aturan data tetap terjaga walaupun View lupa memvalidasi.

    private static String periksaIsi(String nilai, String nama) {

        if (nilai == null || nilai.trim().isEmpty()) {
            throw new IllegalArgumentException(nama + " tidak boleh kosong.");
        }

        return nilai.trim();
    }

    private static String periksaJenis(String nilai) {

        String jenisBersih = periksaIsi(nilai, "Jenis kendaraan");

        if (!jenisBersih.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException(
                "Jenis kendaraan hanya boleh berisi huruf."
            );
        }

        return jenisBersih;
    }

    private static int periksaTahun(int nilai) {

        if (nilai < TAHUN_MINIMAL || nilai > Year.now().getValue()) {
            throw new IllegalArgumentException(
                "Tahun harus antara " + TAHUN_MINIMAL
                + " dan " + Year.now().getValue() + "."
            );
        }

        return nilai;
    }

    private static String periksaKondisi(String nilai) {

        if (!LAYAK.equals(nilai) && !TIDAK_LAYAK.equals(nilai)) {
            throw new IllegalArgumentException(
                "Kondisi harus '" + LAYAK + "' atau '" + TIDAK_LAYAK + "'."
            );
        }

        return nilai;
    }

    // Abstract method: wajib di-override oleh kelas turunan
    public abstract String getKeterangan();

    // Implementasi method dari interface Pemeriksaan
    // final pada method: cara cek status dikunci, subclass cukup mengubah getKeterangan()
    @Override
    public final String cekStatus() {
        return getKeterangan();
    }

    // Overloading: getInfo() dan getInfo(boolean)
    public String getInfo() {
        return platNomor + " - " + jenis;
    }

    public String getInfo(boolean lengkap) {

        if (lengkap) {
            return platNomor + " - " + jenis + " "
                + merk + " (" + tahun + ") - " + kondisi;
        }

        return getInfo();
    }

    // final pada method: plat nomor adalah identitas, perilakunya tidak boleh diubah subclass
    public final String getPlatNomor() {
        return platNomor;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = periksaJenis(jenis);
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = periksaIsi(merk, "Merk");
    }

    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = periksaTahun(tahun);
    }

    public String getKondisi() {
        return kondisi;
    }

    // id_kendaraan dibuat otomatis oleh database (AUTO_INCREMENT),
    // jadi tidak ada di constructor; DAO yang mengisinya setelah insert/select.
    public int getIdKendaraan() {
        return idKendaraan;
    }

    public void setIdKendaraan(int idKendaraan) {
        this.idKendaraan = idKendaraan;
    }
}
