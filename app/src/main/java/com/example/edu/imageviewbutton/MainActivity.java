package com.example.edu.imageviewbutton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    int LOAD_IMAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button fromGalleryButton = findViewById(R.id.fromGalleryButton);
        fromGalleryButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, LOAD_IMAGE);
    }

    protected void onActivityResult(int requestCode, int resultCOde, Intent data) {

        ImageView imageViewFromGallery = findViewById(R.id.imageViewFromGallery);

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
    }

}
