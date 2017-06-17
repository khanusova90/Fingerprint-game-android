package cz.hanusova.fingerprint_game.scene.scan;

import android.content.Context;

import cz.hanusova.fingerprint_game.base.BaseView;
import cz.hanusova.fingerprint_game.model.Place;

/**
 * Created by khanusova on 16/06/2017.
 */

public interface ScanActivityView extends BaseView {

    /**
     * Starts place tracking. <br />
     * When {@link Place} is found, calls {@link ScanActivityPresenter#startTimer(Place, Context)} to start scan and countdown
     */
    void startTracking();

    /**
     * Updates countdown view
     *
     * @param millisLeft milliseconds left to finish scan
     */
    void updateCountdown(long millisLeft);

    /**
     * Starts new activity after countdown is finished
     */
    void onCountdownFinished();

    /**
     * @return Code of {@link Place} that is actually being captured
     */
    String getPlaceCode();

    /**
     * Hides all views for countdown and clears saved information about place scanning
     */
    void stopCountDown();
}
