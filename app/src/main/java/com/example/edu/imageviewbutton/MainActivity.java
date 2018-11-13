package com.example.edu.imageviewbutton;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final int LOAD_IMAGE = 101;
    final int IMAGE_CAPTURE = 102;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button fromGalleryButton = findViewById(R.id.fromGalleryButton);
        Button imageCaptureButton = findViewById(R.id.imageCaptureButton);

        fromGalleryButton.setOnClickListener(this);
        imageCaptureButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fromGalleryButton:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, LOAD_IMAGE);
                break;

            case R.id.imageCaptureButton:

                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                    intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, IMAGE_CAPTURE);
                }
                break;

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        ImageView imageViewFromGallery = findViewById(R.id.imageViewFromGallery);


        switch (requestCode) {
            case LOAD_IMAGE:
                if (data != null) {
                    Uri selectedImage = data.getData();
                    InputStream inputStream = null;
                    try {
                        inputStream = this.getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imageViewFromGallery.setImageBitmap(bitmap);
                }
                break;
            case IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    imageViewFromGallery.setImageBitmap(bitmap);
                }
                break;
        }
    }
}