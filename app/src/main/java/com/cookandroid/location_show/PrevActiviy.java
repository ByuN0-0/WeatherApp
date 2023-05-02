package com.cookandroid.location_show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PrevActiviy extends AppCompatActivity {
    Button btn_next;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    public boolean hasPermissions(Context context, String[] permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void getPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (Build.VERSION.SDK_INT >= 23) {

            // requestPermission의 배열의 index가 아래 grantResults index와 매칭
            // 퍼미션이 승인되면
            if(grantResults.length > 0  && grantResults[0]== PackageManager.PERMISSION_GRANTED && grantResults[1]== PackageManager.PERMISSION_GRANTED){
                Log.d("PERMISSION_TAG","Permission: "+permissions[0]+ " was "+grantResults[0]);
                Log.d("PERMISSION_TAG","Permission: "+permissions[1]+ " was "+grantResults[1]);
                Intent intent = new Intent(PrevActiviy.this, MainActivity.class);
                startActivity(intent);
                finish();

                // TODO : 퍼미션이 승인되는 경우에 대한 코드

            }
            // 퍼미션이 승인 거부되면
            else {
                Log.d("PERMISSION_TAG","Permission denied");
                finish();
                // TODO : 퍼미션이 거부되는 경우에 대한 코드
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prev_activiy);

//        btn_next = (Button)findViewById(R.id.btn_next);

        if(!hasPermissions(PrevActiviy.this, PERMISSIONS)){
            ActivityCompat.requestPermissions(PrevActiviy.this, PERMISSIONS, PERMISSION_ALL);
        }
        // 권한이 허용되어있다면 다음 화면 진행
        else {
            Intent intent = new Intent(PrevActiviy.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}