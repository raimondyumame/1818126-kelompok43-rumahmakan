package com.example.rumahmakan;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Db_Makan extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_rmP";
    private static final String tb_makanan = "tb_makanan";
    private static final String tb_makanan_id = "id_makanan";
    private static final String tb_makanan_image = "image_makanan";
    private static final String tb_makanan_nama = "nama_makanan";
    private static final String tb_makanan_bahan = "bahan_makanan";
    private static final String tb_makanan_tambahan = "tambahan_makanan";
    private static final String tb_makanan_harga = "harga_makanan";
    private static final String CREATE_TABLE = "CREATE TABLE "
            + tb_makanan + "("
            + tb_makanan_id + " INTEGER PRIMARY KEY ,"
            + tb_makanan_image + " BLOB,"
            + tb_makanan_nama + " TEXT,"
            + tb_makanan_bahan + " TEXT, "
            + tb_makanan_tambahan + " TEXT, "
            + tb_makanan_harga + " TEXT " + ")";

    public Db_Makan(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void CreateMakan (Makan mdNotif) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tb_makanan_id, mdNotif.get_id());
        values.put(tb_makanan_image, mdNotif.get_image());
        values.put(tb_makanan_nama, mdNotif.get_nama());
        values.put(tb_makanan_bahan, mdNotif.get_bahan());
        values.put(tb_makanan_tambahan, mdNotif.get_tambahan());
        values.put(tb_makanan_harga, mdNotif.get_harga());
        db.insert(tb_makanan, null, values);
        db.close();
    }

    public List<Makan> ReadMakan() {
        List<Makan> judulModelList = new ArrayList<Makan>();
        String selectQuery = "SELECT  * FROM " + tb_makanan;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Makan mdMakanan = new Makan();
                mdMakanan.set_id(cursor.getString(0));
                mdMakanan.set_image(cursor.getBlob(1));
                mdMakanan.set_nama(cursor.getString(2));
                mdMakanan.set_bahan(cursor.getString(3));
                mdMakanan.set_tambahan(cursor.getString(4));
                mdMakanan.set_harga(cursor.getString(5));

                judulModelList.add(mdMakanan);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return judulModelList;
    }

    public int UpdateMakan (Makan mdNotif) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tb_makanan_image, mdNotif.get_image());
        values.put(tb_makanan_nama, mdNotif.get_nama());
        values.put(tb_makanan_bahan, mdNotif.get_bahan());
        values.put(tb_makanan_tambahan, mdNotif.get_tambahan());
        values.put(tb_makanan_harga, mdNotif.get_harga());
        return db.update(tb_makanan, values, tb_makanan_id + " = ?",
                new String[] { String.valueOf(mdNotif.get_id())});
    }

    public void DeleteMakan (Makan mdNotif) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tb_makanan, tb_makanan_id+ " = ?",
                new String[]{String.valueOf(mdNotif.get_id())});
        db.close();
    }
}

