package com.example.rumahmakan;


import android.graphics.drawable.Drawable;

public class About {
    private String nama, nim;
    private Drawable gambar;

    public About(String nama, String nim, Drawable gambar) {
        this.nama = nama;
        this.nim = nim;
        this.gambar = gambar;
    }

    public Drawable getGambar() {
        return gambar;
    }

    public void setGambar(Drawable gambar) {
        this.gambar = gambar;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }
}
