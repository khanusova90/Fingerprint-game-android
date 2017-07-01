package cz.hanusova.fingerprint_game.scene.ranking;

import org.androidannotations.annotations.EBean;

/**
 * Created by khanusova on 1.7.2017.
 */
@EBean
public class RankingActivityPresenterImpl implements RankingActivityPresenter {
    RankingActivityView view;

    @Override
    public void onAttach(RankingActivityView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }
}
