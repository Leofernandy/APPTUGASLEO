package edu.uph.m23si2.pertamaapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Matakuliah extends RealmObject {
    @PrimaryKey
    private int matakuliahID;
    private String Nama,Prodi;
    private int Sks;
    private Prodi prodi;

    public Matakuliah(){}

    public Matakuliah(int matakuliahID, String nama, String prodi, Prodi prodi1) {
        this.matakuliahID = matakuliahID;
        Nama = nama;
        Prodi = prodi;
        this.prodi = prodi1;
    }

    public Matakuliah(int matakuliahID, String nama, String prodi, int sks, edu.uph.m23si2.pertamaapp.model.Prodi prodi1) {
        this.matakuliahID = matakuliahID;
        Nama = nama;
        Prodi = prodi;
        Sks = sks;
        this.prodi = prodi1;
    }

    public int getSks() {
        return Sks;
    }

    public void setSks(int sks) {
        Sks = sks;
    }

    public int getMatakuliahID() {
        return matakuliahID;
    }

    public void setMatakuliahID(int matakuliahID) {
        this.matakuliahID = matakuliahID;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getProdi() {
        return Prodi;
    }

    public void setProdi(Prodi prodi) {
        this.prodi = prodi;
    }

    public void setProdi(String prodi) {
        Prodi = prodi;
    }
}
