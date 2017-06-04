package cz.hanusova.fingerprint_game;

import android.app.Application;

import cz.hanusova.fingerprint_game.map.MapActivityPresenter;
import cz.hanusova.fingerprint_game.map.MapActivityPresenterImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Created by khanusova on 31.5.2017.
 */
@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application){
        this.application = application;
    }

    @Provides
    public MapActivityPresenter providesMapActivityPresenter(){
        return new MapActivityPresenterImpl();
    }
}
