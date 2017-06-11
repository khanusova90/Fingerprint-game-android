package cz.hanusova.fingerprint_game;

import android.app.Application;

import cz.hanusova.fingerprint_game.map.MapActivityPresenter;
import cz.hanusova.fingerprint_game.map.MapActivityPresenterImpl_;
import cz.hanusova.fingerprint_game.service.UserService;
import cz.hanusova.fingerprint_game.service.impl.UserServiceImpl_;
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
        return MapActivityPresenterImpl_.getInstance_(application.getApplicationContext());
    }

    @Provides
    public UserService providesUserService(){
        return UserServiceImpl_.getInstance_(application.getApplicationContext());
    }
}
