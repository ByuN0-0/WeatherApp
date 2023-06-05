/*
App.java
안드로이드 앱의 전역 상태 및 설정을 관리하는 Application 클래스를 상속
Security.insertProviderAt(Conscrypt.newProvider(), 1)- Conscrypt 프로바이더를 등록하는 작업
Conscrypt는 안드로이드 플랫폼의 SSL/TLS 구현을 제공하는 보안 프로바이더로 앱에서 기본 보안 프로바이더를
Conscrypt를 사용하도록 설정

Security.insertProviderAt(Conscrypt.newProvider(), 1)- Conscrypt 프로바이더를 등록하는 작업
insertProviderAt 메서드는 지정된 위치(인덱스)에 보안 프로바이더를 삽입합니다.
 */

package com.syu.WeatherApp;

import android.app.Application;

import org.conscrypt.Conscrypt;

import java.security.Security;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Security.insertProviderAt(Conscrypt.newProvider(), 1);
    }
}