package edu.uph.m23si2.pertamaapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class KRS_detail extends RealmObject {
    @PrimaryKey
    private int idDetail;
    private KRS krs;                      // relasi ke KRS
    private Kelas_Matakuliah kelasMatkul; // relasi ke Kelas_Matakuliah

    // Constructor kosong wajib untuk Realm
    public KRS_detail() {
    }

    // Constructor custom
    public KRS_detail(int idDetail, KRS krs, Kelas_Matakuliah kelasMatkul) {
        this.idDetail = idDetail;
        this.krs = krs;
        this.kelasMatkul = kelasMatkul;
    }

    // Getter & Setter
    public int getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(int idDetail) {
        this.idDetail = idDetail;
    }

    public KRS getKrs() {
        return krs;
    }

    public void setKrs(KRS krs) {
        this.krs = krs;
    }

    public Kelas_Matakuliah getKelasMatkul() {
        return kelasMatkul;
    }

    public void setKelasMatkul(Kelas_Matakuliah kelasMatkul) {
        this.kelasMatkul = kelasMatkul;
    }

    @Override
    public String toString() {
        return "KRS_detail{" +
                "idDetail=" + idDetail +
                ", krs=" + (krs != null ? krs.getIdKrs() : "null") +
                ", kelasMatkul=" + (kelasMatkul != null ? kelasMatkul.getIdKelas() : "null") +
                '}';
    }
}
