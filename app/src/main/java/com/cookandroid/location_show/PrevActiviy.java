package com.cookandroid.location_show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;


public class PrevActiviy extends AppCompatActivity {
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

    private void getPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (Build.VERSION.SDK_INT < 23) {
            Log.d("VERSION_TAG", "Version is under 23");
            onClickShowAlert(0);
        } else {
            // requestPermission의 배열의 index가 아래 grantResults index와 매칭
            // 퍼미션이 승인되면
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.d("PERMISSION_TAG", "Permission: " + permissions[0] + " was " + grantResults[0]);
                Log.d("PERMISSION_TAG", "Permission: " + permissions[1] + " was " + grantResults[1]);
                Intent intent = new Intent(PrevActiviy.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


                // TODO : 퍼미션이 승인되는 경우에 대한 코드

            }
            // 퍼미션이 승인 거부되면
            else {
                Log.d("PERMISSION_TAG", "Permission denied");
                onClickShowAlert(1);
                // TODO : 퍼미션이 거부되는 경우에 대한 코드
            }
        }
    }

    public void onClickShowAlert(int index) {
        AlertDialog.Builder AlertBuilder = new AlertDialog.Builder(PrevActiviy.this);
        if (index == 0) {
            AlertBuilder.setTitle("Version");
            AlertBuilder.setMessage("지원하지 않는 버전입니다.");
            AlertBuilder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
        } else if (index == 1) {
            AlertBuilder.setTitle("권한");
            AlertBuilder.setMessage("권한 설정을 해주셔야 합니다.");
            AlertBuilder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
        }
        AlertBuilder.show();
    }



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prev_activiy);

        if (!hasPermissions(PrevActiviy.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(PrevActiviy.this, PERMISSIONS, PERMISSION_ALL);
        }
        // 권한이 허용되어있다면 다음 화면 진행
        else {
            Intent intent = new Intent(PrevActiviy.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}