package com.djinggoo.imageinpainting.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.djinggoo.imageinpainting.R;
import com.djinggoo.imageinpainting.activities.settings.SettingsActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_VIDEO_CAPTURE = 1;
    private static String currentPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ivSettings = findViewById(R.id.iv_settings);
        TextView tvCamera = findViewById(R.id.tv_camera);
        TextView tvGallery = findViewById(R.id.tv_gallery);

        ivSettings.setOnClickListener(this);
        tvCamera.setOnClickListener(this);
        tvGallery.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_settings :
                Intent gotToSettings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(gotToSettings);
                break;
            case R.id.tv_camera :
                takeVideo();
                break;
            case R.id.tv_gallery:
                openGallery();
                break;
        }
    }

    private void takeVideo(){
        Intent video = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (video.resolveActivity(getPackageManager()) != null){
            startActivityForResult(video, REQUEST_VIDEO_CAPTURE);
        }
    }

    private void takeImage(){
        Intent startCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (startCamera.resolveActivity(getPackageManager()) != null){

//            File photoFile = null;
//            try{
//                photoFile = createImageFile();
//            }catch (Exception e){mtp://%5Busb%3A001,022%5D/Phone/Download/1997009_MalikAbdulAziz_CM.pdf
//                e.printStackTrace();
//            }
//
//            if (photoFile != null){
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.example.android.fileprovider",
//                        photoFile);

//                startCamera.putExtra(MediaStore.EXTRA_OUTPUT, "/home/testandroiddata.jpg");
                startActivityForResult(startCamera, REQUEST_IMAGE_CAPTURE);
//            }

        }
    }

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "JPEG"+timeStamp+"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = new File(Environment.getExternalStorageState(), fileName+".jpg");
        File image = File.createTempFile(fileName, ".jpg", storageDir);
        currentPath = image.getAbsolutePath();
        return image;
    }

    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}