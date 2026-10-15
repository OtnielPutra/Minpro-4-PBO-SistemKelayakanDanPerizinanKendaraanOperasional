<div align="center">
  
# MINPRO 4 PBO

</div>

Nama : Otniel Putra Wardana

Nim : 2509116081

----

##  Deskripsi Singkat Program

Program ini merupakan Sistem Monitoring Kelayakan dan Perizinan Kendaraan Operasional yang dibuat untuk membantu perusahaan atau instansi dalam mengelola dan memantau kondisi serta perizinan kendaraan operasional. Program ini diperuntukkan bagi pihak yang bertanggung jawab terhadap kendaraan agar dapat mengetahui data kendaraan, kondisi kelayakannya, serta informasi perizinan yang dimiliki. Dengan adanya program ini, proses pengelolaan data kendaraan menjadi lebih terstruktur dan mudah dipantau, sehingga dapat membantu memastikan kendaraan yang digunakan berada dalam kondisi layak dan memiliki perizinan yang sesuai.

----

## Fitur Program

* Tambah Data Kendaraan Operasional
* Menampilkan Data Kendaraan
* Mengubah Kondisi Kelayakan Kendaraan
* Menghapus Data Kendaraan
* Menampilkan Status Kelayakan Kendaraan
* Tambah Data Perizinan Kendaraan
* Menampilkan Data Perizinan Kendaraan

----

## Struktur Package (MVC)

Program ini dibagi menjadi 5 package. Tiga di antaranya adalah komponen MVC yaitu Controller, View, dan Model. serta satu lagi adalah titik awal program dan juga ditambahkannya package baru yaitu `model.dao.java`.

<img width="745" height="412" alt="image" src="https://github.com/user-attachments/assets/5fe5fbd8-77e6-44ad-b192-ea4daae2a4ef" />

### 1. Main

<img width="162" height="42" alt="image" src="https://github.com/user-attachments/assets/98168f82-c992-425d-9632-077f0ffbfed7" />


Package `main` adalah folder tempat program mulai dijalankan, isinya `Main.java`. Ini berbeda dari method `main()`, yaitu syarat Java agar program bisa berjalan. Package ini bukan bagian dari MVC, tugasnya hanya menghubungkan View dan Controller: meminta input ke View, meminta Controller memproses data, lalu meminta View menampilkan hasilnya. Di NetBeans, Main Class diatur ke `main.Main`.

### 2. Model

<img width="248" height="123" alt="image" src="https://github.com/user-attachments/assets/d5bd73dc-ef80-4778-a6a5-5c9be6045cde" />

**Model** adalah bagian yang mengatur, menyimpan, dan mengambil data, berisi aturan bisnis, dan tidak peduli bagaimana data ditampilkan (tidak ada `Scanner` atau `System.out`). Data disimpan di atribut `private`, diambil lewat getter, dan data yang melanggar aturan ditolak dengan `IllegalArgumentException`.

**Class di package `model`:**

- **`Kendaraan`**: abstract class induk yang menyimpan dan memvalidasi data kendaraan, serta punya abstract method `getKeterangan()`.
- **`KendaraanLayak`**: turunan `Kendaraan` untuk kondisi layak.
- **`KendaraanTidakLayak`**: turunan `Kendaraan` untuk kondisi tidak layak.
- **`Perizinan`**: menyimpan data izin kendaraan (nomor izin, plat, jenis izin, tanggal berlaku).

### 3. Controller

<img width="176" height="42" alt="image" src="https://github.com/user-attachments/assets/fa6ff36b-0236-4071-b921-d2cb92fbc678" />

Controller adalah otak yang menghubungkan View dan Model. Controller menerima input dari pengguna, memprosesnya, meminta data yang diperlukan ke Model, lalu mengirimkan hasilnya kembali ke View untuk ditampilkan. Class di package controller:

- **`Monitoring`**: mengelola data kendaraan dan perizinan (tambah, hapus, update kondisi, cek plat) dan menolak plat kembar.

### 4. View

Bagian yang bertugas menampilkan informasi kepada pengguna, contohnya UI. View hanya menerima data yang sudah siap disajikan dan tidak memproses data. Pada proyek ini View berupa tampilan console: menampilkan menu, data, dan animasi, serta menerima input pengguna lalu meneruskannya ke Controller. Class di dalam package view:

- **`Animasi`**: berisi animasi loading, efek mesin tik, dan transisi antar menu supaya program terlihat lebih hidup.
- **`KendaraanView`**: menampilkan menu, data kendaraan, dan data perizinan, serta menerima input pengguna dan memeriksa formatnya (misalnya jenis kendaraan hanya boleh huruf).

<img width="207" height="67" alt="image" src="https://github.com/user-attachments/assets/d80ba6e7-b719-4fa2-8b0c-a29ad3ef1555" />

## 5. Model.dao

<img width="220" height="91" alt="image" src="https://github.com/user-attachments/assets/d1335f53-6929-4c2a-a61e-3147e813b0f3" />

Bagian yang bertugas mengakses database, yaitu menyimpan, mengambil, mengubah, dan menghapus data. DAO (Data Access Object) hanya mengurus perintah SQL dan tidak menampilkan apa pun ke layar. Pada proyek ini DAO memakai JDBC dengan PreparedStatement untuk terhubung ke MySQL, lalu hasilnya dikembalikan ke Controller dalam bentuk objek. Package ini termasuk lapisan Model, jadi letaknya di dalam package model. Class di dalam package model.dao:

- **`Koneksi`**: membuka koneksi ke database MySQL (URL, user, dan password disimpan di sini), sehingga class lain cukup memanggil Koneksi.getKoneksi().
- **`KendaraanDAO`**: mengelola tabel kendaraan, yaitu tambah, tampil semua, cari berdasarkan plat atau id, update kondisi, dan hapus. Data dari database diubah menjadi objek KendaraanLayak atau KendaraanTidakLayak sesuai kondisinya.
- **`PerizinanDAO`**: mengelola tabel perizinan, yaitu tambah izin, tampil semua izin (digabung dengan data kendaraan lewat JOIN), dan memeriksa apakah sebuah kendaraan sudah punya izin agar relasinya tetap one-to-one.

----

## Alur Program

### 1. Menu Utama Program

Jika pengguna menjalankan program ini, maka pengguna akan langsung masuk kedalam menu. Menu ini menjadi pusat navigasi bagi pengguna untuk mengakses berbagai fitur yang tersedia dalam sistem. Pada menu utama, pengguna akan diberikan beberapa pilihan berupa nomor yang dapat dipilih sesuai kebutuhan. Pengguna cukup memasukkan nomor pilihan, kemudian program akan menjalankan fitur yang dipilih. Setelah fitur selesai digunakan, pengguna dapat kembali ke menu utama untuk memilih fitur lainnya atau memilih opsi keluar untuk mengakhiri program.

<img width="557" height="420" alt="image" src="https://github.com/user-attachments/assets/4a9b5fc0-29bd-4e5f-8549-6fc2f0ff8ac7" />

### 2. Menambahkan Kendaraan

Fitur Tambah Kendaraan digunakan untuk memasukkan data kendaraan operasional baru ke dalam sistem. Pengguna akan diminta mengisi beberapa informasi seperti plat nomor, jenis kendaraan, merk, tahun kendaraan, dan kondisi kendaraan. Setiap input akan divalidasi terlebih dahulu agar data yang dimasukkan sesuai dengan ketentuan. Setelah semua data valid, kendaraan akan disimpan ke dalam sistem dan dapat dilihat melalui fitur Tampilkan Kendaraan.

<img width="537" height="322" alt="image" src="https://github.com/user-attachments/assets/9479cf1b-7ff3-4edf-811a-5eaa84f6a88b" />

### 3. Menampilkan Kendaraan 

Fitur Tampilkan Kendaraan digunakan untuk melihat seluruh data kendaraan yang telah tersimpan di dalam sistem. Pada fitur ini, pengguna dapat melihat informasi seperti plat nomor, jenis kendaraan, merk, tahun, dan kondisi kendaraan. Sistem juga menampilkan keterangan mengenai status kendaraan, apakah kendaraan tersebut layak digunakan atau tidak layak dan perlu diperiksa.

<img width="367" height="612" alt="image" src="https://github.com/user-attachments/assets/e9e285f0-5f57-4453-af53-e0e7285891f1" />

### 4. Memperbarui Kondisi Kendaraan

Fitur Update Kondisi digunakan untuk mengubah kondisi atau status kelayakan kendaraan yang sudah tersimpan di dalam sistem. Pengguna memasukkan plat nomor kendaraan yang ingin diperbarui, kemudian memilih kondisi baru, yaitu “Layak” atau “Tidak Layak”. Setelah proses berhasil, data kendaraan akan diperbarui sesuai dengan kondisi yang dipilih.

<img width="456" height="295" alt="image" src="https://github.com/user-attachments/assets/b5707873-7016-4f71-b4f6-b8eda2412800" />

### 5. Menghapus Kendaraan 

Fitur Hapus Kendaraan digunakan untuk menghapus data kendaraan yang sudah tersimpan di dalam sistem. Pengguna cukup memasukkan nomor plat kendaraan yang ingin dihapus, kemudian sistem akan mencari data tersebut. Jika kendaraan ditemukan, data akan dihapus dari sistem dan pengguna akan mendapatkan pemberitahuan bahwa data berhasil dihapus.

<img width="465" height="400" alt="image" src="https://github.com/user-attachments/assets/fb80b52c-0bb0-4614-ac18-e86f70a513ea" />

### 6. Tambah Perizinan

Fitur Tambah Perizinan digunakan untuk memasukkan data izin kendaraan ke dalam sistem. Pengguna akan mengisi informasi seperti nomor izin, nomor plat kendaraan, jenis izin, dan tanggal berlaku. Setelah data yang dimasukkan valid, sistem akan menyimpan data perizinan sehingga dapat digunakan dan ditampilkan kembali melalui fitur Tampilkan Perizinan.

<img width="536" height="276" alt="image" src="https://github.com/user-attachments/assets/dd544929-f6a9-4e3a-bd61-ea574fe0b733" />

### 7. Tampilkan Perizinan

Fitur Tampilkan Perizinan digunakan untuk melihat seluruh data perizinan kendaraan yang telah tersimpan di dalam sistem. Pengguna dapat melihat informasi seperti nomor izin, nomor plat kendaraan, jenis izin, dan tanggal berlaku. Fitur ini membantu pengguna mengetahui informasi perizinan kendaraan secara lebih mudah dan terorganisir.

<img width="276" height="470" alt="image" src="https://github.com/user-attachments/assets/3ad9c54a-0f6a-47c1-b471-3dfcbf1bf211" />

### 8. Menyelesaikan Program

Fitur Keluar digunakan untuk mengakhiri program. Ketika pengguna memilih menu ini, sistem akan menghentikan proses program dan pengguna akan keluar dari sistem.

<img width="475" height="267" alt="image" src="https://github.com/user-attachments/assets/e10e7fe3-de28-472d-9d63-a3304defc8f3" />

----

<div align="center">
  
# Penjelasan Implementasi

</div>

## Encapsulation

Encapsulation adalah konsep dalam pemrograman berorientasi objek yang menggabungkan data dan fungsi dalam satu class serta membatasi akses langsung terhadap data tersebut. Tujuannya adalah menjaga keamanan dan keteraturan data sehingga perubahan atau penggunaan data dapat dikontrol melalui mekanisme yang telah ditentukan oleh class.

- Kendaraan: Kendaraan: menerapkan encapsulation dengan membuat semua atribut `private` dan mengaksesnya melalui getter/setter. Setter melakukan validasi data seperti jenis hanya huruf, merk tidak boleh kosong, dan tahun harus valid. `platNomor` dan `kondisi` bersifat `final`, sehingga hanya memiliki getter tanpa setter.

- Perizinan: menerapkan encapsulation dengan membuat seluruh atribut private dan mengaksesnya melalui getter dan setter. nomorIzin bersifat final, sehingga hanya memiliki getter tanpa setter. Atribut lainnya dapat diubah melalui setter yang sekaligus melakukan validasi agar data tidak boleh kosong.

- Monitoring: menerapkan encapsulation dengan membuat daftarKendaraan dan daftarPerizinan bersifat private final, sehingga data tidak dapat dimanipulasi langsung dari luar class. Akses data diberikan melalui getter yang menggunakan Collections.unmodifiableList(), sehingga pihak luar hanya dapat membaca data tanpa bisa menambah atau menghapusnya secara langsung. Selain itu, method cariIndex() dan buatKendaraan() dibuat private untuk membatasi akses terhadap proses internal controller.

- KendaraanView: menerapkan encapsulation dengan membuat atribut input bersifat private final, sehingga hanya dapat diakses dan digunakan di dalam class KendaraanView. Atribut tersebut diinisialisasi melalui constructor dan tidak dapat diganti setelah objek dibuat. Selain itu, berbagai method seperti inputText(), inputInteger(), inputPlat(), dan inputTanggal() mengatur proses input dan validasi agar data yang diterima sesuai ketentuan program.

- Animasi: menerapkan encapsulation dengan membuat konstanta PANJANG_BAR bersifat private static final, sehingga nilainya hanya dapat digunakan di dalam class dan tidak dapat diubah. Constructor juga dibuat private agar class ini tidak dapat dibuat menjadi objek dari luar karena hanya digunakan sebagai class utilitas. Selain itu, method tidur() dibuat private karena hanya digunakan untuk kebutuhan internal animasi.

## Inheritance

Inheritance diterapkan untuk membuat class turunan dapat mewarisi atribut dan method dari class induknya. Pada program ini, inheritance digunakan agar data dan fungsi dasar kendaraan cukup dibuat pada satu class, kemudian dapat digunakan oleh class kendaraan yang memiliki kondisi berbeda. Class yang menerapkan inheritance:

- Kendaraan -> sebagai superclass atau class induk yang berisi atribut dan fungsi dasar kendaraan.
- KendaraanLayak -> sebagai subclass yang mewarisi Kendaraan dan digunakan untuk kendaraan yang layak digunakan.
- KendaraanTidakLayak -> sebagai subclass yang mewarisi Kendaraan dan digunakan untuk kendaraan yang tidak layak digunakan.

## Polymorphism

Polymorphism adalah konsep OOP yang memungkinkan satu method memiliki bentuk atau perilaku yang berbeda. Dalam program Sistem Monitoring Kendaraan, polymorphism diterapkan dalam dua bentuk, yaitu method overriding dan method overloading. Overriding digunakan ketika subclass memiliki method yang berasal dari superclass tetapi memberikan perilaku yang berbeda, sedangkan overloading digunakan ketika satu class memiliki beberapa method dengan nama yang sama tetapi parameter yang berbeda.

1. Overriding:

- Kendaraan

Bagian getKeterangan() merupakan abstract method yang menjadi dasar bagi subclass untuk memberikan implementasinya masing-masing.

- KendaraanLayak

Bagian getKeterangan() melakukan overriding dari method pada Kendaraan. Implementasinya memberikan keterangan khusus untuk kendaraan yang layak.

- KendaraanTidakLayak

Bagian getKeterangan() juga melakukan overriding, tetapi memberikan keterangan khusus untuk kendaraan yang tidak layak.

- KendaraanView

Pada bagian pemanggilan cekStatus(), class ini menggunakan objek bertipe Kendaraan yang dapat berupa KendaraanLayak atau KendaraanTidakLayak. Jadi, hasil yang ditampilkan dapat berbeda berdasarkan objek sebenarnya.

2. Overloading:

- Kendaraan

Bagian getInfo() memiliki beberapa versi dengan parameter yang berbeda. Ini memungkinkan informasi kendaraan ditampilkan dengan cara yang berbeda sesuai kebutuhan.

- Monitoring

Bagian tambah() memiliki beberapa versi dengan parameter berbeda untuk menambahkan kendaraan.
Bagian hapus() juga memiliki beberapa versi dengan parameter berbeda, misalnya berdasarkan plat nomor atau index.

- Animasi

Bagian loading() memiliki beberapa versi dengan parameter berbeda, sehingga animasi loading dapat digunakan dengan cara yang berbeda sesuai kebutuhan.

## Abstraction

Abstraction adalah konsep OOP yang digunakan untuk menyembunyikan detail implementasi yang tidak perlu ditampilkan dan hanya menyediakan bagian penting yang dibutuhkan oleh pengguna program. Dalam program Sistem Monitoring Kendaraan kamu, abstraction diterapkan terutama melalui abstract class dan abstract method.

Class yang menerapkan abstraction:

* `Kendaraan`

  * Menjadi `abstract class`, sehingga tidak dapat dibuat menjadi objek secara langsung.
  * Menyimpan atribut dan fungsi umum yang dimiliki semua kendaraan.
  * Memiliki `getKeterangan()` sebagai abstract method yang tidak memiliki implementasi langsung dan harus dibuat oleh subclass.

* `KendaraanLayak`

  * Menjadi subclass dari `Kendaraan`.
  * Mengimplementasikan `getKeterangan()` sesuai kondisi kendaraan yang layak.

* `KendaraanTidakLayak`

  * Menjadi subclass dari `Kendaraan`.
  * Mengimplementasikan `getKeterangan()` sesuai kondisi kendaraan yang tidak layak.

Contoh penerapannya dalam struktur program:

`Kendaraan`
→ menyimpan konsep umum kendaraan

`KendaraanLayak`
→ detail kendaraan layak

`KendaraanTidakLayak`
→ detail kendaraan tidak layak

## Interface

Interface adalah konsep OOP yang digunakan sebagai sebuah “kontrak” atau aturan yang harus diikuti oleh class yang mengimplementasikannya. Interface biasanya berisi method yang menentukan fungsi apa yang harus dimiliki oleh suatu class, sedangkan implementasi detail method tersebut dibuat di dalam class yang menggunakan interface.

Pada program Sistem Monitoring Kendaraan kamu, terdapat 2 interface:

1. `Pemeriksaan`

* Diimplementasikan oleh class `Kendaraan`.
* Mengatur bahwa setiap kendaraan harus memiliki fungsi untuk melakukan pemeriksaan atau pengecekan status.
* `Kendaraan` kemudian memberikan implementasi dari fungsi tersebut.
* Interface ini berkaitan dengan pengecekan apakah kendaraan dalam kondisi `Layak` atau `Tidak Layak`.

2. `Manajemen<T>`

* Diimplementasikan oleh class `Monitoring`.
* Menentukan fungsi dasar untuk mengelola data kendaraan.
* Berisi aturan untuk proses menambah data, menghapus data, dan mengambil daftar data.
* `Monitoring` kemudian mengimplementasikan fungsi-fungsi tersebut sesuai kebutuhan sistem.

Jadi struktur interface pada program kamu adalah:

* `Pemeriksaan` → `Kendaraan`
* `Manajemen<Kendaraan>` → `Monitoring`

Kesimpulannya, penggunaan interface membuat struktur program lebih teratur karena class yang mengimplementasikan interface harus mengikuti method yang sudah ditentukan. Ini juga membuat program lebih fleksibel karena aturan atau kontrak fungsi dapat dipisahkan dari implementasi detailnya.

## JBDC & DAO

JDBC (Java Database Connectivity) adalah API pada Java yang digunakan untuk menghubungkan program dengan database, sedangkan DAO (Data Access Object) merupakan pola desain yang digunakan untuk memisahkan proses pengelolaan database dari logika utama program.
Pada program Sistem Monitoring Kendaraan kamu, terdapat 3 class yang berkaitan dengan JDBC dan DAO:
1. KendaraanDAO
- Mengelola akses data kendaraan pada tabel kendaraan.
- Menggunakan JDBC untuk menambahkan, menampilkan, mencari, memperbarui kondisi, dan menghapus data kendaraan.
- Menggunakan PreparedStatement untuk menjalankan query SQL dengan parameter.
- Menggunakan ResultSet untuk membaca hasil query dari database.
- Menggunakan method buatDariRow() untuk mengubah data dari database menjadi objek Kendaraan, KendaraanLayak, atau KendaraanTidakLayak.
2. PerizinanDAO
- Mengelola akses data perizinan pada tabel perizinan.
- Menggunakan JDBC untuk menambahkan dan menampilkan data perizinan.
- Menggunakan JOIN untuk menghubungkan tabel perizinan dengan tabel kendaraan, sehingga plat nomor kendaraan dapat ditampilkan bersama data perizinannya.
- Menggunakan LocalDate, Date, dan DateTimeFormatter untuk menyesuaikan format tanggal antara program Java dan database.
- Memiliki method sudahPunyaIzin() untuk memeriksa apakah kendaraan sudah memiliki data perizinan.
3. Koneksi
- Bertugas menghubungkan program Java dengan database MySQL.
- Menggunakan DriverManager.getConnection() untuk membuat koneksi ke database db_monitoring_kendaraan.
- Menyimpan URL database, username, dan password yang diperlukan untuk koneksi.
- Menyediakan method getKoneksi() yang dipanggil oleh class DAO ketika membutuhkan koneksi database.
Jadi, struktur JDBC dan DAO pada program kamu adalah:
- Koneksi → Menghubungkan Java dengan database MySQL.
- KendaraanDAO → Mengelola data kendaraan menggunakan JDBC.
- PerizinanDAO → Mengelola data perizinan menggunakan JDBC.
Kesimpulannya, JDBC berfungsi sebagai penghubung antara Java dan database, sedangkan DAO mengatur operasi pengelolaan data agar kode lebih terstruktur. Dengan pemisahan ini, proses database tidak perlu ditulis langsung di class utama atau bagian tampilan program, sehingga program Sistem Monitoring Kendaraan menjadi lebih mudah dipelihara dan dikembangkan.
