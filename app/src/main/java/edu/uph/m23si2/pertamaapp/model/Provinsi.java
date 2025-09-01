package edu.uph.m23si2.pertamaapp.model;

import java.security.PrivateKey;

public class Provinsi {
    private String code, name;

    public Provinsi(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
