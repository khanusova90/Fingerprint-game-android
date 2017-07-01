package cz.hanusova.fingerprint_game.base;

/**
 * Created by khanusova on 31.5.2017.
 */

public interface BasePresenter<V extends BaseView> {

    /**
     * Method called when presenter is attached (links view with presenter)
     *
     * @param view Implementation of {@link BaseView}
     */
    void onAttach(V view);

    /**
     * Method called when presenter is detached. Removes connection between presenter and view
     */
    void onDetach();
}
