/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Scanner;
import model.Kendaraan;
import model.Perizinan;

/**
 * 
 * 
 */
// pada class: view ini tidak diwariskan lagi
public class KendaraanView {

    // pada atribut: scanner hanya diisi sekali lewat constructor
    private final Scanner input;

    public KendaraanView(Scanner input) {
        this.input = input;
    }

    // ===================== MENU =====================

    public void tampilMenu() {

        System.out.println("\n=== SISTEM MONITORING KENDARAAN ===");
        System.out.println("1. Tambah Kendaraan");
        System.out.println("2. Tampilkan Kendaraan");
        System.out.println("3. Update Kondisi");
        System.out.println("4. Hapus Kendaraan");
        System.out.println("5. Tambah Perizinan");
        System.out.println("6. Tampilkan Perizinan");
        System.out.println("7. Keluar");
    }

    public int inputMenu() {
        return inputInteger("Pilih menu: ", 1, 7);
    }

    public void tampilPesan(String pesan) {
        System.out.println(pesan);
    }

    // ===================== TAMPILAN DATA =====================

    public void tampilKendaraan(List<Kendaraan> daftar) {

        if (daftar.isEmpty()) {
            System.out.println("Data kendaraan masih kosong.");
            return;
        }

        System.out.println("\n=== DATA KENDARAAN ===");

        for (int i = 0; i < daftar.size(); i++) {

            Kendaraan k = daftar.get(i);

            System.out.println("Data ke-" + (i + 1));
            System.out.println("ID         : " + k.getIdKendaraan());
            System.out.println("Plat Nomor : " + k.getPlatNomor());
            System.out.println("Jenis      : " + k.getJenis());
            System.out.println("Merk       : " + k.getMerk());
            System.out.println("Tahun      : " + k.getTahun());
            System.out.println("Kondisi    : " + k.getKondisi());

            // Polymorphism: cekStatus() berbeda sesuai tipe objek
            System.out.println(k.cekStatus());
            System.out.println("-------------------------");
        }
    }

    public void tampilPerizinan(List<Perizinan> daftar) {

        System.out.println("\n=== DATA PERIZINAN ===");

        if (daftar.isEmpty()) {
            System.out.println("Data masih kosong.");
            return;
        }

        for (Perizinan p : daftar) {

            System.out.println("\nID Perizinan : " + p.getIdPerizinan());
            System.out.println("ID Kendaraan : " + p.getIdKendaraan());
            System.out.println("Plat Nomor   : " + p.getPlatNomor());
            System.out.println("Keterangan   : " + p.getKeterangan());
            System.out.println("Tanggal      : " + p.getTanggal());
        }
    }

    // ===================== INPUT KHUSUS =====================

    // Plat nomor: contoh KT1234AB, B 1234 XYZ (disimpan huruf besar tanpa spasi)
    public String inputPlat(String pesan) {

        while (true) {

            String data = inputText(pesan, 3, 10);

            if (data.matches("(?i)[A-Z]{1,2} ?\\d{1,4} ?[A-Z]{0,3}")) {
                return data.replace(" ", "").toUpperCase();
            }

            System.out.println(
                "Format plat tidak valid. Contoh: KT1234AB"
            );
        }
    }

    // Jenis kendaraan: HANYA huruf dan spasi, angka/simbol ditolak
    public String inputJenis(String pesan) {
        return inputHuruf(pesan, 3, 30);
    }

    // Merk: boleh ada angka (mis. "Avanza 1"), tapi wajib mengandung huruf
    public String inputMerk(String pesan) {

        while (true) {

            String data = inputText(pesan, 2, 30);

            if (
                data.matches("[\\p{L}\\d .\\-]+")
                && data.matches(".*\\p{L}.*")
            ) {
                return data;
            }

            System.out.println(
                "Merk harus mengandung huruf dan tidak boleh memakai simbol aneh."
            );
        }
    }

    public int inputTahun(String pesan) {
        return inputInteger(pesan, 1900, Year.now().getValue());
    }

    // Tanggal harus format dd-MM-yyyy dan benar-benar ada di kalender
    public String inputTanggal(String pesan) {

        final DateTimeFormatter format = DateTimeFormatter
            .ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

        while (true) {

            String data = inputText(pesan, 8, 10);

            try {
                LocalDate.parse(data, format);
                return data;
            } catch (Exception e) {
                System.out.println(
                    "Tanggal tidak valid. Gunakan format dd-MM-yyyy (contoh: 31-12-2026)."
                );
            }
        }
    }

    // Pilih kondisi: mengembalikan "Layak" atau "Tidak Layak"
    public String pilihKondisi(String pesan) {

        System.out.println("1. " + Kendaraan.LAYAK);
        System.out.println("2. " + Kendaraan.TIDAK_LAYAK);

        int pilih = inputInteger(pesan, 1, 2);

        if (pilih == 1) {
            return Kendaraan.LAYAK;
        }

        return Kendaraan.TIDAK_LAYAK;
    }

    // ===================== INPUT DASAR =====================

    public String inputText(String pesan, int min, int max) {

        while (true) {

            System.out.print(pesan);
            String data = input.nextLine().trim();

            if (data.isEmpty()) {

                System.out.println(
                    "Input tidak boleh kosong."
                );

            } else if (data.length() < min) {

                System.out.println(
                    "Input minimal "
                    + min
                    + " karakter."
                );

            } else if (data.length() > max) {

                System.out.println(
                    "Input maksimal "
                    + max
                    + " karakter."
                );

            } else {
                return data;
            }
        }
    }

    // Input khusus huruf (dan spasi), angka ditolak
    public String inputHuruf(String pesan, int min, int max) {

        while (true) {

            String data = inputText(pesan, min, max);

            if (data.matches("[\\p{L} ]+")) {
                return data;
            }

            System.out.println(
                "Input hanya boleh berisi huruf (tidak boleh angka atau simbol)."
            );
        }
    }

    public int inputInteger(String pesan, int min, int max) {

        while (true) {

            System.out.print(pesan);
            String data = input.nextLine().trim();

            if (data.isEmpty()) {

                System.out.println(
                    "Input tidak boleh kosong."
                );

                continue;
            }

            try {

                int angka = Integer.parseInt(data);

                if (angka < min || angka > max) {

                    System.out.println(
                        "Input harus berada di antara "
                        + min
                        + " dan "
                        + max
                        + "."
                    );

                } else {
                    return angka;
                }

            } catch (NumberFormatException e) {

                System.out.println(
                    "Input harus berupa angka."
                );
            }
        }
    }
}
