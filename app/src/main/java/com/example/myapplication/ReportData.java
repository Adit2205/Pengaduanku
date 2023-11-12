package com.example.myapplication;

public class ReportData {
    private String title;
    private String base64Photo;
    private String nama;
    private String lokasi;
    private String tanggal;
    private String laporan;
    private String telepon;

    // Konstruktor default untuk Firebase
    public ReportData() {}

    // Konstruktor untuk inisialisasi data
    public ReportData(String title, String base64Photo, String nama, String lokasi, String tanggal, String laporan, String telepon) {
        this.title = title;
        this.base64Photo = base64Photo;
        this.nama = nama;
        this.lokasi = lokasi;
        this.tanggal = tanggal;
        this.laporan = laporan;
        this.telepon = telepon;
    }

    // Getter dan Setter

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBase64Photo() {
        return base64Photo;
    }

    public void setBase64Photo(String base64Photo) {
        this.base64Photo = base64Photo;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getLaporan() {
        return laporan;
    }

    public void setLaporan(String laporan) {
        this.laporan = laporan;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }
}

