package cz.hanusova.fingerprint_game;

import android.app.Application;
import android.content.Context;

import org.androidannotations.annotations.EApplication;

import cz.hanusova.fingerprint_game.model.fingerprint.Fingerprint;

/**
 * Created by khanusova on 26.3.2017.
 */
@EApplication
public class FingerprintApplication extends Application {

    private static FingerprintApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        if (application == null){
            application = this;
        }
    }

    public static Context getContext(){
        return application.getApplicationContext();
    }


}
