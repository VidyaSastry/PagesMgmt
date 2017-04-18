package com.vidya.pagesmgmt.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Vidya on 4/17/17.
 */

public class App extends Application {

    public static Application application;

    public static Context getContext(){
        return application.getApplicationContext();
    }

    @Override
    public void onCreate() {
        application = this;
        super.onCreate();
    }
}
