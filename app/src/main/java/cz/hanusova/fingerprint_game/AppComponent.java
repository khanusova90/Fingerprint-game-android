package cz.hanusova.fingerprint_game;

import javax.inject.Singleton;

import cz.hanusova.fingerprint_game.map.MapActivity;
import dagger.Component;

/**
 * Created by khanusova on 31.5.2017.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(FingerprintApplication app);
    void inject(MapActivity activity);
}
