# Tugas Praktik: Local Data Storage (Room & Jetpack Compose)

Repository ini berisi source code untuk tugas praktik implementasi penyimpanan data lokal menggunakan **SQLite (Room Database)** dengan antarmuka **Jetpack Compose** dan arsitektur **MVVM**.

## 📱 Tentang Aplikasi
**Topik yang dipilih:** [Pilih salah satu: Daftar Buku / Daftar Tugas / Daftar Kontak]

Aplikasi ini adalah [Jelaskan singkat, misal: manajer daftar buku sederhana] yang memungkinkan pengguna untuk menyimpan data ke database lokal dan menampilkannya kembali dalam bentuk list. Aplikasi ini mendemonstrasikan operasi **Create** (Insert) dan **Read** (Get All) menggunakan Room.

### Fitur Utama:
1.  **Input Data:** Form input sederhana untuk menambahkan data baru (minimal 3 field).
2.  **List Data:** Menampilkan seluruh data yang tersimpan di database menggunakan `LazyColumn`.
3.  **Real-time Update:** Data baru langsung muncul di list setelah disimpan tanpa perlu restart aplikasi (menggunakan `Flow` / `LiveData`).

---

## 📸 Screenshots

Berikut adalah tampilan antarmuka aplikasi:

| **Tampilan Form Input** | **Tampilan List Data** |
|:-------------------:|:------------------:|
| ![(screenshots/form_input.png)](https://raw.githubusercontent.com/mamat1815/Bukurak/refs/heads/master/WhatsApp%20Image%202025-12-06%20at%2022.24.23.jpeg) | ![(screenshots/list_data.png)](https://raw.githubusercontent.com/mamat1815/Bukurak/refs/heads/master/WhatsApp%20Image%202025-12-06%20at%2022.24.23%20(1).jpeg) |
| *Form untuk menambah data baru* | *Daftar data yang tersimpan di DB* |

> **Catatan:** Screenshot di atas menunjukkan implementasi UI menggunakan Jetpack Compose.

---

## 🛠 Teknologi & Arsitektur

Aplikasi ini dibangun menggunakan teknologi dan pola arsitektur berikut:

* **Bahasa:** Kotlin
* **UI Toolkit:** Jetpack Compose
* **Database:** Room Persistence Library (SQLite Abstraction)
* **Architecture Pattern:** MVVM (Model-View-ViewModel)
* **Concurrency:** Kotlin Coroutines & Flow

### Struktur MVVM:
1.  **Model (Room Database):**
    * **Entity:** Merepresentasikan tabel data (misal: `Book`, `Task`, atau `Contact`).
    * **DAO (Data Access Object):** Interface untuk akses ke database (Insert & GetQuery).
2.  **Repository:** Jembatan antara ViewModel dan Database (Single Source of Truth).
3.  **ViewModel:** Mengelola state UI dan logika bisnis, serta berkomunikasi dengan Repository.
4.  **View (Compose UI):** Menampilkan data dari ViewModel dan menangkap input user.

