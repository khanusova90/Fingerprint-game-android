package cz.hanusova.fingerprint_game.scene.scan;

import android.content.Context;

import cz.hanusova.fingerprint_game.base.BasePresenter;
import cz.hanusova.fingerprint_game.model.Place;

/**
 * Created by khanusova on 16/06/2017.
 */

public interface ScanActivityPresenter extends BasePresenter<ScanActivityView> {

    /**
     * Initializes presenter - prepares for scan <br />
     * After everything is prepared, calls {@link ScanActivityView#startTracking()}
     *
     * @param context
     */
    void init(Context context);

    /**
     * Stops scanning, stops timer and switches BT and wifi off (in case that it was switched off before scanning)
     */
    void destroy();

    /**
     * Finds place by given code
     *
     * @param code unique String value of place code
     * @return {@link Place} with code from method parameter or <code>null</code> in case that no place is found
     */
    Place getPlace(String code);

    /**
     * Creates new timer instance
     */
    void createTimer();

    /**
     * Starts timer countdown and fingerprint scan.<br />
     * Sends fingerprint to server after scan is finished
     *
     * @param place   {@link Place} that is actually being captured
     * @param context
     */
    void startTimer(Place place, Context context);

    /**
     * Stops timer and unregisteres listeners
     */
    void stopTimer();
}
