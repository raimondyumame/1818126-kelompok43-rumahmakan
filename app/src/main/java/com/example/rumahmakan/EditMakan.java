package com.example.rumahmakan;



import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class EditMakan extends AppCompatActivity {
    private Db_Makan db;

    private ImageView Eimage;
    private String Sid, Snama, Sbahan, Stambahan, Sharga;
    private EditText Enama, Ebahan, Etambahan, Eharga;
    private byte[] Simage;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("Ubah Data Makanan");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_makan);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new Db_Makan(this);

        Intent i = this.getIntent();
        Sid = i.getStringExtra("Iid");
        Simage = i.getByteArrayExtra("Iimage");
        Snama = i.getStringExtra("Inama");
        Sbahan = i.getStringExtra("Ibahan");
        Stambahan = i.getStringExtra("Itambahan");
        Sharga = i.getStringExtra("Iharga");

        Eimage = (ImageView) findViewById(R.id.edit_imgMkn);
        Enama = (EditText) findViewById(R.id.edit_namamakan);
        Ebahan = (EditText) findViewById(R.id.edit_bahanmakan);
        Etambahan = (EditText) findViewById(R.id.edit_tambahanmakan);
        Eharga = (EditText) findViewById(R.id.edit_hargamakan);

        Eimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        EditMakan.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        Bitmap bitmap = BitmapFactory.decodeByteArray(Simage, 0, Simage.length);
        Eimage.setImageBitmap(bitmap);
        Enama.setText(Snama);
        Ebahan.setText(Sbahan);
        Etambahan.setText(Stambahan);
        Eharga.setText(Sharga);

        Button btnUpdate = (Button) findViewById(R.id.btn_updatemakan);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Simage = imageViewToByte(Eimage);
                Snama = String.valueOf(Enama.getText());
                Sbahan = String.valueOf(Ebahan.getText());
                Stambahan = String.valueOf(Etambahan.getText());
                Sharga = String.valueOf(Eharga.getText());
                if (Snama.equals("")) {
                    Enama.requestFocus();
                    Toast.makeText(EditMakan.this, "Silahkan isi Nama", Toast.LENGTH_SHORT).show();
                } else if (Sbahan.equals("")) {
                    Ebahan.requestFocus();
                    Toast.makeText(EditMakan.this, "Silahkan isi Bahan", Toast.LENGTH_SHORT).show();
                } else if (Stambahan.equals("")) {
                    Etambahan.requestFocus();
                    Toast.makeText(EditMakan.this, "Silahkan isi Tambahan", Toast.LENGTH_SHORT).show();
                } else if (Sharga.equals("")) {
                    Eharga.requestFocus();
                    Toast.makeText(EditMakan.this, "Silahkan isi Harga", Toast.LENGTH_SHORT).show();
                } else {
                    db.UpdateMakan(new Makan(Sid, Simage, Snama, Sbahan, Stambahan, Sharga));
                    Toast.makeText(EditMakan.this, "Data telah diupdate", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        Button btnDelete = (Button) findViewById(R.id.btn_deletemakan);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.DeleteMakan(new Makan(Sid, Simage, Snama, Sbahan, Stambahan, Sharga));
                Toast.makeText(EditMakan.this, "Data telah dihapus", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private byte[] imageViewToByte(ImageView mImageView) {
        Bitmap bitmap = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
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
            Intent a = new Intent(EditMakan.this, HomeAdmin.class);
            startActivity(a);
            return true;
        }
        else if (id == R.id.action_logout){
            Intent a = new Intent(EditMakan.this, MainActivity.class);
            startActivity(a);
            return true;
        }
        else if (id == R.id.action_about){
            Intent a = new Intent(EditMakan.this, MainAbout.class);
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
                // set image choosed from gallery to image view
                Eimage.setImageURI(resultUri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

