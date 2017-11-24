package com.example.android.galeryapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private  List<GrideItemModel> list;
    private GrideAdapter adapter;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();



        Button btnChoos = (Button) findViewById(R.id.btn_choos);
        adapter = new GrideAdapter(this, list);


        gridView = (GridView) findViewById(R.id.gridView);

        gridView.setAdapter(adapter);
        gridView.setNumColumns(3);

        btnChoos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent choosePicIntent = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);

                String pictureDirectoriPath = pictureDirectory.getPath();
                Uri data = Uri.parse(pictureDirectoriPath);

                choosePicIntent.setDataAndType(data, "image/*");
                startActivityForResult(choosePicIntent, 100);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 100) {
                if(data != null) {
                    Uri selectImage = data.getData();

                    optimizeImageForListView(selectImage);
                }
            }
        }
    }


    private void optimizeImageForListView(Uri uri){
        try {
            InputStream in = getContentResolver().openInputStream(uri);

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();
            int origWidth = o.outWidth;
            int origHeight = o.outHeight;
            int bytesPerPixel = 2 ;
            int maxSize = 480 * 800 * bytesPerPixel;
            int desiredWidth = 400;
            int desiredHeight =300;
            int desiredSize = desiredWidth * desiredHeight * bytesPerPixel;
            if (desiredSize < maxSize) maxSize = desiredSize;
            int scale = 1;
            if (origWidth > origHeight) {
                scale = Math.round((float) origHeight / (float) desiredHeight);
            } else {
                scale = Math.round((float) origWidth / (float) desiredWidth);
            }

            o = new BitmapFactory.Options();
            o.inSampleSize = scale;
            o.inPreferredConfig = Bitmap.Config.RGB_565;

            in = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(in, null, o);
            GrideItemModel m = new GrideItemModel();

            m.setImg1(bitmap);
            list.add(m);
            adapter.notifyDataSetChanged();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}
