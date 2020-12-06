package com.example.rumahmakan;


public class Makan {
    private String _id, _nama, _bahan, _tambahan, _harga;
    private byte[] _image;

    public Makan (String id, byte[] image, String nama, String bahan, String tambahan, String harga) {
        this._id = id;
        this._image = image;
        this._nama = nama;
        this._bahan = bahan;
        this._tambahan = tambahan;
        this._harga = harga;
    }
    public Makan() {

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public byte[] get_image() {
        return _image;
    }

    public void set_image(byte[] _image) {
        this._image = _image;
    }

    public String get_nama() {
        return _nama;
    }

    public void set_nama(String _nama) {
        this._nama = _nama;
    }

    public String get_bahan() {

        return _bahan;
    }

    public void set_bahan(String _bahan) {
        this._bahan = _bahan;
    }

    public String get_tambahan() {

        return _tambahan;
    }

    public void set_tambahan(String _tambahan) {
        this._tambahan = _tambahan;
    }

    public String get_harga() {

        return _harga;
    }

    public void set_harga(String _harga) {
        this._harga = _harga;
    }
}

