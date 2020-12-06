package com.example.rumahmakan;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

public class AddMakan extends AppCompatActivity {
    private Db_Makan db;
    private ImageView Eimage;
    private EditText Enama, Ebahan, Etambahan, Eharga;
    private String Snama, Sbahan, Stambahan, Sharga;
    private byte[] Simage;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_makanan);
        getSupportActionBar().setTitle("Tambah Data Makanan");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new Db_Makan(this);
        Eimage = findViewById(R.id.img_mkn);
        Enama = (EditText) findViewById(R.id.create_namamakan);
        Ebahan = (EditText) findViewById(R.id.create_bahanmakan);
        Etambahan = (EditText) findViewById(R.id.create_tambahanmakan);
        Eharga = (EditText) findViewById(R.id.create_hargamakan);

        Eimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        AddMakan.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });


        Button btnCreate = (Button) findViewById(R.id.btn_addmakan);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Simage = imageViewToByte(Eimage) ;
                Snama = String.valueOf(Enama.getText());
                Sbahan = String.valueOf(Ebahan.getText());
                Stambahan = String.valueOf(Etambahan.getText());
                Sharga = String.valueOf(Eharga.getText());

                if (Snama.equals("")){
                    Enama.requestFocus();
                    Toast.makeText(AddMakan.this, "Silahkan isi Nama", Toast.LENGTH_SHORT).show();
                } else if (Sbahan.equals("")){
                    Ebahan.requestFocus();
                    Toast.makeText(AddMakan.this, "Silahkan isi Bahan", Toast.LENGTH_SHORT).show();
                } else if (Stambahan.equals("")){
                    Etambahan.requestFocus();
                    Toast.makeText(AddMakan.this, "Silahkan isi Tambahan", Toast.LENGTH_SHORT).show();
                } else if (Sharga.equals("")){
                    Eharga.requestFocus();
                    Toast.makeText(AddMakan.this, "Silahkan isi Harga", Toast.LENGTH_SHORT).show();
                } else {
                    Eimage.setImageResource(R.drawable.ic_image);
                    Enama.setText("");
                    Ebahan.setText("");
                    Etambahan.setText("");
                    Eharga.setText("");
                    Toast.makeText(AddMakan.this, "Data telah ditambah", Toast.LENGTH_SHORT).show();
                    db.CreateMakan(new Makan(null, Simage, Snama, Sbahan, Stambahan, Sharga));
                    Intent a = new Intent(AddMakan.this, MenuMakan.class);
                    startActivity(a);
                }
            }
        });
    }

    private byte[] imageViewToByte(ImageView eimage) {
        Bitmap bitmap = ((BitmapDrawable)Eimage.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
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
            Intent a = new Intent(AddMakan.this, HomeAdmin.class);
            startActivity(a);
            return true;
        }
        else if (id == R.id.action_logout){
            Intent a = new Intent(AddMakan.this, MainActivity.class);
            startActivity(a);
            return true;
        }
        else if (id == R.id.action_about){
            Intent a = new Intent(AddMakan.this, MainAbout.class);
            startActivity(a);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //gallery intent
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(this, "Don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON) //enable image guidlines
                    .setAspectRatio(1,1)// image will be square
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result =CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                //set image choosed from gallery to image view
                Eimage.setImageURI(resultUri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}

