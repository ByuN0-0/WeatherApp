/*
ProgressDialog.java

앱을 실행한 직후 또는 네트워크 환경, 위치 정보 제공 이 불완전할 때 로딩 화면을 띄우는 클래스
Dialog 를 상속받아 대화상자 형식으로 로딩이미지를 띄운다.
 */

package com.syu.WeatherApp.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.syu.WeatherApp.R;

public class ProgressDialog extends Dialog {
    public ProgressDialog(Context context){
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_progress);
    }
}
