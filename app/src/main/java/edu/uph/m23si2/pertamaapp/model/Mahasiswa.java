package edu.uph.m23si2.pertamaapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Mahasiswa extends RealmObject {
    @PrimaryKey
    private int studentID;
    private String Nama,JenisKelamin,Hobi,Email,Prodi;

    public Mahasiswa(){}

    public Mahasiswa(String email, String hobi, String jenisKelamin, String nama, String prodi, int studentID) {
        Email = email;
        Hobi = hobi;
        JenisKelamin = jenisKelamin;
        Nama = nama;
        Prodi = prodi;
        this.studentID = studentID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getHobi() {
        return Hobi;
    }

    public void setHobi(String hobi) {
        Hobi = hobi;
    }

    public String getJenisKelamin() {
        return JenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        JenisKelamin = jenisKelamin;
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

    public void setProdi(String prodi) {
        Prodi = prodi;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    @Override
    public String toString() {
        return "Mahasiswa{" +
                "Email='" + Email + '\'' +
                ", studentID=" + studentID +
                ", Nama='" + Nama + '\'' +
                ", JenisKelamin='" + JenisKelamin + '\'' +
                ", Hobi='" + Hobi + '\'' +
                ", Prodi='" + Prodi + '\'' +
                '}';
    }
}
