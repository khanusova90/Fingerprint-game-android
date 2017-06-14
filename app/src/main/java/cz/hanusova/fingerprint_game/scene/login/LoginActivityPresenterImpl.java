package cz.hanusova.fingerprint_game.scene.login;

import org.androidannotations.annotations.EBean;

/**
 * Created by khanusova on 14.6.2017.
 */
@EBean
public class LoginActivityPresenterImpl implements LoginActivityPresenter{
    LoginActivityView view;

    @Override
    public void onAttach(LoginActivityView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }
}
