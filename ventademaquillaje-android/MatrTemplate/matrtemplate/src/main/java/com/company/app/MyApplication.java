package com.company.app;

import android.app.Application;

import com.inosur.matrcore.StandaloneManager;
import com.inosur.matrutil.ForegroundStandalone;

public class MyApplication extends Application implements ForegroundStandalone.Listener {

    @Override
    public void onCreate() {
        super.onCreate();

        ForegroundStandalone.init(this).addListener(this);
        ConfigurationManager.loadConfigurationsApp(this);
        StandaloneManager.initializeFirebase(this);
    }

    @Override
    public void onBecameForeground() {
        StandaloneManager.onResume();
    }

    @Override
    public void onBecameBackground() {
        StandaloneManager.onEnterBackground();
    }
}
