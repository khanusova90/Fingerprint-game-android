package cz.hanusova.fingerprint_game.scene.ranking;

import java.util.List;

import cz.hanusova.fingerprint_game.base.BasePresenter;
import cz.hanusova.fingerprint_game.model.Ranking;

/**
 * Created by khanusova on 1.7.2017.
 */

public interface RankingActivityPresenter extends BasePresenter<RankingActivityView> {
    void initRankings();

    List<Ranking> getRankings();

    String getActualUsername();
}
