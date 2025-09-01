package edu.uph.m23si2.pertamaapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Prodi extends RealmObject {
    @PrimaryKey
    private int prodiID;
    private String Nama,Fakultas;

    public  Prodi(){}
    public Prodi(int prodiID, String nama, String fakultas) {
        this.prodiID = prodiID;
        Nama = nama;
        Fakultas = fakultas;
    }

    public int getProdiID() {
        return prodiID;
    }

    public void setProdiID(int prodiID) {
        this.prodiID = prodiID;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getFakultas() {
        return Fakultas;
    }

    public void setFakultas(String fakultas) {
        Fakultas = fakultas;
    }
}
