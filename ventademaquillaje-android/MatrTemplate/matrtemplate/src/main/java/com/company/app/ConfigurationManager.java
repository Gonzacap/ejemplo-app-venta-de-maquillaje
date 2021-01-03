package com.company.app;

import android.content.Context;
import android.content.res.AssetManager;

import com.inosur.matrcore.Core;
import com.inosur.matrutil.Configuration;
import com.inosur.matrutil.ILifecycle;
import com.inosur.matrutil.MatrUtils;
import com.inosur.matrutil.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by andreaottonello on 5/2/18.
 */

public class ConfigurationManager {

    // ===========================================================
    // KEYS file config.properties
    // ===========================================================
    public static final String KEY_URL_SERVER = "matr_url_server";
    public static final String KEY_DATABOARD_URL_SERVER = "matr_databoard_url_server";
    public static final String KEY_APP_KEY = "matr_appkey";
    public static final String KEY_FIREBASE_API_KEY = "matr_firebase_apiKey";
    public static final String KEY_FIREBASE_API_ID = "matr_firebase_apiId";
    public static final String KEY_FIREBASE_DATABASE_URL = "matr_firebase_database_url";
    public static final String KEY_UNHANDLE_EXCEPTION = "matr_unhandled_exception";
    public static final String KEY_TIMER_TIMEOUT_MIN = "matr_timer_timeout_min";

    // ===========================================================
    // VALUES file config.properties
    // ===========================================================
    public static final String VALUE_FIREBASE_API_KEY = "${andFirebaseApiKey}";
    public static final String VALUE_FIREBASE_API_ID = "${andFirebaseAppId}";
    public static final String VALUE_FIREBASE_DATABASE_URL = "${firebaseDatabaseUrl}";


    public static Properties properties;
    public static final String FILE_NAME = "matr_config.properties";
    public static final String emptyValue = "";

    public static void openFileConfiguration(Context context) throws IOException {
        ConfigurationManager.properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(ConfigurationManager.FILE_NAME);
        ConfigurationManager.properties.load(inputStream);
        inputStream.close();
    }

    public static String getProperty(String key) {
        return ConfigurationManager.properties.getProperty(key);
    }


    //Exception configuration. If not has value set by default Relaunch mode
    // ===========================================================
    public static int getPropertyUnHandleException() {
        return  MatrUtils.isInteger(getProperty(KEY_UNHANDLE_EXCEPTION))?Integer.parseInt(getProperty(KEY_UNHANDLE_EXCEPTION)):0;
    }

    //FireBase Credentials. If not has value set empty value
    // ===========================================================
    public static String getPropertyFirebaseApiKey() {
        return getProperty(KEY_FIREBASE_API_KEY).equals(VALUE_FIREBASE_API_KEY) ? emptyValue : getProperty(KEY_FIREBASE_API_KEY);
    }

    public static String getPropertyFirebaseApiId() {
        return getProperty(KEY_FIREBASE_API_ID).equals(VALUE_FIREBASE_API_ID) ? emptyValue : getProperty(KEY_FIREBASE_API_ID);
    }

    public static String getPropertyFirebaseDatabaseUrl() {
        return getProperty(KEY_FIREBASE_DATABASE_URL).equals(VALUE_FIREBASE_DATABASE_URL) ? emptyValue : getProperty(KEY_FIREBASE_DATABASE_URL);
    }

    //FireBase Credentials. If not has value set empty value
    // ===========================================================
    public static Integer getPropertyTimerTimeOut() {
        return StringUtil.isNullOrEmpty(getProperty(KEY_TIMER_TIMEOUT_MIN)) ? 60 : Integer.parseInt(getProperty(KEY_TIMER_TIMEOUT_MIN));
    }

    /**
     * Load properties from server and set all configuration tu show dedicated app
     *
     * @param context: context dedicated app
     */
    public static void loadConfigurationsApp(Context context) {
        try {
            ConfigurationManager.openFileConfiguration(context);
            Configuration.openConfiguration(context);
            Configuration.configureAsStandaloneMode(context);
            Configuration.configurePackageName(BuildConfig.APPLICATION_ID);
            Configuration.configureAppKey(context, ConfigurationManager.getProperty(ConfigurationManager.KEY_APP_KEY));
            Configuration.configureBaseServerURL(context, ConfigurationManager.getProperty(ConfigurationManager.KEY_URL_SERVER));
            Configuration.configureDataboardServerURL(context, ConfigurationManager.getProperty(ConfigurationManager.KEY_DATABOARD_URL_SERVER));
            Configuration.configurationExceptions(context, ConfigurationManager.getPropertyUnHandleException());
            Configuration.configureFirebaseCredentials(context,
                    ConfigurationManager.getPropertyFirebaseApiKey(),
                    ConfigurationManager.getPropertyFirebaseApiId(),
                    ConfigurationManager.getPropertyFirebaseDatabaseUrl());
            Configuration.configureTimerTimeOutSatandaloneMode(context, ConfigurationManager.getPropertyTimerTimeOut());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load configurations and run dedicated app
     */
    public static void loadStandaloneApp(Context context, ILifecycle iLifecycle) {

        Core core = new Core();
        core.initAsStandaloneMode(context,iLifecycle);
    }
}
