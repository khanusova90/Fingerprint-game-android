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
}
