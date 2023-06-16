package com.mscompany.stickersms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;

public class SplashActivity extends AppCompatActivity {
    private Button btnPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        btnPic =findViewById(R.id.idBtnPic);
        btnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();
            }
        });
    }

    private void checkPermissions(){
        int permission = ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.Q){
            pickImage();
        }else if(permission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(SplashActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }else{
                pickImage();
            }
        }
    private void pickImage(){
        Intent intent =new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            pickImage();
        }else {
            Toast.makeText(this,"Permission denied!!!",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==RESULT_OK){
            Uri uri = data.getData();
            switch (requestCode){
                case 100:
                    Intent intent1 = new Intent(SplashActivity.this, DsPhotoEditorActivity.class);
                    intent1.setData(uri);
                    intent1.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY,"Images");
                    intent1.putExtra(DsPhotoEditorConstants.DS_TOOL_BAR_BACKGROUND_COLOR, getResources().getColor(R.color.blue));
                    intent1.putExtra(DsPhotoEditorConstants.DS_MAIN_BACKGROUND_COLOR, getResources().getColor(R.color.blue));
                    intent1.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE,new int[]{DsPhotoEditorActivity.TOOL_WARMTH,
                    DsPhotoEditorActivity.TOOL_PIXELATE});
                    startActivityForResult(intent1,101);

                case 101:
                    Toast.makeText(this,"Image Saved...",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    }
}