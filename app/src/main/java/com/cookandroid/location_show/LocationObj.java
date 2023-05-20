package com.cookandroid.location_show;
//안쓰는 클래스
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

public class LocationObj {
    MainActivity mainActivity;
    ContextCompat compat;
    String fineLocation, coarseLocation;

    public LocationObj(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }



    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode){
//            case PERMISSIONS_REQUEST_CODE:
//                if(grantResults.length > 0){
//                    boolean coarselocation = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    boolean finelocation = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//                    if(!coarselocation||!finelocation)
//                        permissionDialog("권한 수락을 해주세요.");
//                } else{
//                    Intent intent = new Intent(this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//        }
//    }
//
//    private void permissionDialog(String msg){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("권한 설정");
//        builder.setMessage(msg);
//        builder.setCancelable(false);
//        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                if (Build.VERSION.SDK_INT >= 23) {
//                    requestPermissions(REQURED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
//                }
//            }
//        });
//        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                finish();
//            }
//        });
//        builder.create().show();
//    }
}
