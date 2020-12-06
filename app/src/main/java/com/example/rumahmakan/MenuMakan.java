package com.example.rumahmakan;



import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MenuMakan extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView mListView;
    private CustomListAdapterMakan adapter_off;
    private Db_Makan db;
    private List<Makan> ListMakanan = new ArrayList<Makan>();

    public void btn_adddata (View view){
        Intent a = new Intent(MenuMakan.this, AddMakan.class);
        startActivity(a);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_makan);
        getSupportActionBar().setTitle("List Menu Makanan");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new Db_Makan(this);

        adapter_off = new CustomListAdapterMakan(this, ListMakanan );
        mListView = (ListView) findViewById(R.id.list_makanan);
        mListView.setAdapter(adapter_off);
        mListView.setOnItemClickListener(this);
        mListView.setClickable(true);
        ListMakanan.clear();

        List<Makan> contacts = db.ReadMakan();
        for (Makan cn : contacts) {
            Makan judulModel = new Makan();
            judulModel.set_id(cn.get_id());
            judulModel.set_image(cn.get_image());
            judulModel.set_nama(cn.get_nama());
            judulModel.set_bahan(cn.get_bahan());
            judulModel.set_tambahan(cn.get_tambahan());
            judulModel.set_harga(cn.get_harga());
            ListMakanan.add(judulModel);

            if ((ListMakanan.isEmpty()))
                Toast.makeText(MenuMakan.this, "Tidak ada data", Toast.LENGTH_SHORT).show();
            else {

            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        Object o = mListView.getItemAtPosition(i);
        Makan obj_itemDetails = (Makan)o;

        String Sid = obj_itemDetails.get_id();
        byte[] Simage = obj_itemDetails.get_image();
        String Snama = obj_itemDetails.get_nama();
        String Sbahan = obj_itemDetails.get_bahan();
        String Stambahan = obj_itemDetails.get_tambahan();
        String Sharga = obj_itemDetails.get_harga();

        Intent goUpdate = new Intent(MenuMakan.this, EditMakan.class);
        goUpdate.putExtra("Iid", Sid);
        goUpdate.putExtra("Iimage", Simage);
        goUpdate.putExtra("Inama", Snama);
        goUpdate.putExtra("Ibahan", Sbahan);
        goUpdate.putExtra("Itambahan", Stambahan);
        goUpdate.putExtra("Iharga", Sharga);
        startActivity(goUpdate);     }

    @Override
    protected void onResume() {
        super.onResume();
        ListMakanan.clear();
        mListView.setAdapter(adapter_off);

        List<Makan> contacts = db.ReadMakan();
        for (Makan cn : contacts) {
            Makan judulModel = new Makan();
            judulModel.set_id(cn.get_id());
            judulModel.set_image(cn.get_image());
            judulModel.set_nama(cn.get_nama());
            judulModel.set_bahan(cn.get_bahan());
            judulModel.set_tambahan(cn.get_tambahan());
            judulModel.set_harga(cn.get_harga());
            ListMakanan.add(judulModel);

            if ((ListMakanan.isEmpty()))
                Toast.makeText(MenuMakan.this, "Tidak ada data", Toast.LENGTH_SHORT).show();
            else {

            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_list_admin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        int back = item.getItemId();
        if(back == android.R.id.home){
            this.finish();
        }

        else if (id == R.id.action_home){
            Intent a = new Intent(MenuMakan.this, HomeAdmin.class);
            startActivity(a);
            return true;
        }
        else if (id == R.id.action_logout){
            Intent a = new Intent(MenuMakan.this, MainActivity.class);
            startActivity(a);
            return true;
        }
        else if (id == R.id.action_about){
            Intent a = new Intent(MenuMakan.this, MainAbout.class);
            startActivity(a);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

