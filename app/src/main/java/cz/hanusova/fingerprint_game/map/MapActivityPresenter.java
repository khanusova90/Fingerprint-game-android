package cz.hanusova.fingerprint_game.map;

import android.content.Context;

import java.util.List;

import cz.hanusova.fingerprint_game.base.BasePresenter;
import cz.hanusova.fingerprint_game.model.Place;

/**
 * Created by khanusova on 31.5.2017.
 */

public interface MapActivityPresenter extends BasePresenter<MapActivityView> {
    void createMap(int currentFloor, Context context);

    List<Place> getPlaces(int currentFloor);

    void createIcons(Context context, int currentFloor);
}
