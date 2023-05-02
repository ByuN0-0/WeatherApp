package com.cookandroid.location_show;

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