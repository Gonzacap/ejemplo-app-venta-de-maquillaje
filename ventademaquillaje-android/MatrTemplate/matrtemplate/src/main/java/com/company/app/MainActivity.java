package com.company.app;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.inosur.matrrulerunner.RuleHelper;
import com.inosur.matrutil.ILifecycle;

public class MainActivity extends AppCompatActivity implements ILifecycle {

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Load standalone app
        ConfigurationManager.loadStandaloneApp(getApplicationContext(), this);
        RuleHelper.getInstance().setNotificationFromIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.intent = intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (intent!= null)
            RuleHelper.getInstance().setNotificationFromIntent(intent);
        intent = null;
    }

    @Override
    public void closeApp() {
        this.finishAffinity();
    }
}

