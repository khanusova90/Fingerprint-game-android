package cz.hanusova.fingerprint_game;

import android.app.Application;
import android.content.Context;

import org.androidannotations.annotations.EApplication;

/**
 * Created by khanusova on 26.3.2017.
 */
@EApplication
public class FingerprintApplication extends Application {

    public static FingerprintApplication instance;

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null){
            instance = this;
        }
        initComponents();
    }

    public static Context getContext(){
        return instance.getApplicationContext();
    }

    protected void initComponents(){
        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        component.inject(this);
    }

    public AppComponent getComponent(){
        return component;
    }

}
