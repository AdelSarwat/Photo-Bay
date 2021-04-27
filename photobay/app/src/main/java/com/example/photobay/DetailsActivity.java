package com.example.photobay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class DetailsActivity extends AppCompatActivity {

    public static final int Permasion_Storage_code = 1000;
    ImageView v ;
    ImageView image;
    Button button;
    String url;
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();
         url = bundle.getString(SearchResultActivity.Url_Key);

        v = findViewById(R.id.img_detail);
        Picasso.with(this).load(url).fit().centerInside().into(v);

        button = findViewById(R.id.btn_download);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                    {

                        String [] Permasion = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(Permasion,Permasion_Storage_code);
                    }
                    else {

                        StartDownloading();
                        ShowMessage("Download..");
                    }

                }
                else {

                    StartDownloading();
                    ShowMessage("Download..");

                }


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                DetailsActivity.this.finish();


            }
        });

    }

    private void StartDownloading() {

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI|DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download");
        request.setDescription("Download file...");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis());

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);


    }


    private  void  ShowMessage(String s){

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){

            case Permasion_Storage_code:{
                if (grantResults.length>0 &&grantResults[0]== PackageManager.PERMISSION_GRANTED){

                    StartDownloading();
                }
                else {

                    Toast.makeText(this, "Permission Denied...! ", Toast.LENGTH_SHORT).show();
                }

            }
        }

    }

}