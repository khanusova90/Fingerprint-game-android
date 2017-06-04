package cz.hanusova.fingerprint_game.map;

import android.content.Context;

import cz.hanusova.fingerprint_game.base.BasePresenter;

/**
 * Created by khanusova on 31.5.2017.
 */

public interface MapActivityPresenter extends BasePresenter<MapActivityView> {
    void createMap(int currentFloor, Context context);
}
