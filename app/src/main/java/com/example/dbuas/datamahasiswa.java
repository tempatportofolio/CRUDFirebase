package com.example.dbuas;

public class datamahasiswa {
    public String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String namamahasiswa;
    public String jk;
    public String jurusan;
    public String alamat;
    public boolean complete;

    public datamahasiswa(String namamahasiswa, String title,String jurusan, String jk, String alamat, boolean complete) {
        this.title = title;
        this.namamahasiswa = namamahasiswa;
        this.jk = jk;
        this.jurusan = jurusan;
        this.alamat = alamat;
        this.complete = complete;
    }


    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getNamamahasiswa() {
        return namamahasiswa;
    }

    public void setNamamahasiswa(String namamahasiswa) {
        this.namamahasiswa = namamahasiswa;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }


}
