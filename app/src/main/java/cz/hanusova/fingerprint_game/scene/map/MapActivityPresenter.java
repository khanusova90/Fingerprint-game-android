package cz.hanusova.fingerprint_game.scene.map;

import android.content.Context;

import java.util.List;

import cz.hanusova.fingerprint_game.base.BasePresenter;
import cz.hanusova.fingerprint_game.model.Place;

/**
 * Created by khanusova on 31.5.2017.
 */

public interface MapActivityPresenter extends BasePresenter<MapActivityView> {

    /**
     * Downloads map from server and sets it to view
     *
     * @param currentFloor
     * @param context
     */
    void createMap(int currentFloor, Context context);

    /**
     * @param currentFloor
     * @return List of {@link Place}s that user has already discovered
     */
    List<Place> getPlaces(int currentFloor);

    /**
     * Creates icon list for all user's places and sets the list to view
     *
     * @param context
     * @param currentFloor
     */
    void createIcons(Context context, int currentFloor);
}
