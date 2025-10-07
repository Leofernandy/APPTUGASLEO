package edu.uph.m23si2.pertamaapp.model;

public class Pasien {
    /*
                "id": 1,
                "nama": "Budi Santoso",
                "nik": "1234567890",
                "alamat": "Jl. Merdeka No. 10",
                "no_hp": "08123456789",
                "created_at": "2025-09-09T08:15:47.000000Z",
                "updated_at": "2025-09-09T08:15:47.000000Z"
     */
    private int id;
    private String nama,nik,alamat,no_hp,created_at,updated_at;

    public Pasien(){}
    public Pasien(String alamat, String created_at, int id, String nama, String nik, String no_hp, String updated_at) {
        this.alamat = alamat;
        this.created_at = created_at;
        this.id = id;
        this.nama = nama;
        this.nik = nik;
        this.no_hp = no_hp;
        this.updated_at = updated_at;
    }

    public Pasien(String nama, String nik, String alamat, String no_hp) {
        this.alamat = alamat;
        this.nama = nama;
        this.nik = nik;
        this.no_hp = no_hp;
    }
    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}