package edu.uph.m23si2.pertamaapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Kelas_Matakuliah extends RealmObject {
    @PrimaryKey
    private int idKelas;
    private String namaKelas;   // pastikan ada field ini
    private Matakuliah mataKuliah;

    // Getter & Setter
    public int getIdKelas() {
        return idKelas;
    }

    public void setIdKelas(int idKelas) {
        this.idKelas = idKelas;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {   // <--- tambahkan ini
        this.namaKelas = namaKelas;
    }

    public Matakuliah getMataKuliah() {
        return mataKuliah;
    }

    public void setMatakuliah(Matakuliah mataKuliah) {
        this.mataKuliah = mataKuliah;
    }
}

