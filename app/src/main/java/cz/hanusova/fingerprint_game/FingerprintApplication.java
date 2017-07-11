package cz.hanusova.fingerprint_game;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import org.androidannotations.annotations.EApplication;

import io.fabric.sdk.android.Fabric;

/**
 * Created by khanusova on 26.3.2017.
 */
@EApplication
public class FingerprintApplication extends Application {

    public static FingerprintApplication instance;

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null) {
            instance = this;
        }
        Fabric.with(this, new Crashlytics());
    }

}
