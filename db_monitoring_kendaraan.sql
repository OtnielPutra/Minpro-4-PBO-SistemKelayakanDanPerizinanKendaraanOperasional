-- =====================================================
-- Database: Sistem Monitoring Kelayakan dan Perizinan Kendaraan
-- Jalankan file ini sekali di MySQL (phpMyAdmin / MySQL Workbench)
-- =====================================================

DROP DATABASE IF EXISTS db_monitoring_kendaraan;
CREATE DATABASE db_monitoring_kendaraan;
USE db_monitoring_kendaraan;

-- ---------------- TABEL KENDARAAN ----------------
CREATE TABLE kendaraan (
    id_kendaraan INT AUTO_INCREMENT PRIMARY KEY,
    plat_nomor   VARCHAR(10) NOT NULL UNIQUE,
    jenis        VARCHAR(30) NOT NULL,
    merk         VARCHAR(30) NOT NULL,
    tahun        INT NOT NULL,
    kondisi      ENUM('Layak', 'Tidak Layak') NOT NULL
);

-- ---------------- TABEL PERIZINAN ----------------
-- UNIQUE pada id_kendaraan => relasi ONE TO ONE
-- (satu kendaraan hanya boleh punya satu izin)
-- ON DELETE CASCADE => jika kendaraan dihapus, izinnya ikut terhapus
CREATE TABLE perizinan (
    id_perizinan INT AUTO_INCREMENT PRIMARY KEY,
    id_kendaraan INT NOT NULL UNIQUE,
    keterangan   VARCHAR(30) NOT NULL,
    tanggal      DATE NOT NULL,
    CONSTRAINT fk_perizinan_kendaraan
        FOREIGN KEY (id_kendaraan)
        REFERENCES kendaraan (id_kendaraan)
        ON DELETE CASCADE
);

-- ---------------- DUMMY DATA AWAL ----------------
-- id_kendaraan dan id_perizinan diisi otomatis (AUTO_INCREMENT)
INSERT INTO kendaraan (plat_nomor, jenis, merk, tahun, kondisi) VALUES
('KT1234AB', 'Mobil',  'Toyota Avanza', 2022, 'Layak'),
('KT5678CD', 'Truk',   'Hino',          2018, 'Tidak Layak'),
('KT9012EF', 'Motor',  'Honda Beat',    2021, 'Layak');

-- Kendaraan ke-3 sengaja belum punya izin (untuk uji fitur tambah perizinan)
INSERT INTO perizinan (id_kendaraan, keterangan, tanggal) VALUES
(1, 'Operasional', '2026-12-31'),
(2, 'Angkutan Barang', '2025-06-30');
