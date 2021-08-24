package com.example.aplikasiandroidapi.model;

public class PegawaiModel {
    String PegawaiModel;
    String nama;
    String alamat;

    public PegawaiModel() {

    }

    public PegawaiModel(String nama, String alamat) {
        this.nama = nama;
        this.alamat = alamat;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
