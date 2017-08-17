package cz.hanusova.fingerprint_game.scene.ranking;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.hanusova.fingerprint_game.base.service.UserService;
import cz.hanusova.fingerprint_game.base.service.impl.UserServiceImpl;
import cz.hanusova.fingerprint_game.model.Ranking;
import cz.hanusova.fingerprint_game.rest.RestClient;

/**
 * Created by khanusova on 1.7.2017.
 */
@EBean
public class RankingActivityPresenterImpl implements RankingActivityPresenter {
    RankingActivityView view;

    @RestService
    RestClient restClient;

    @Bean(UserServiceImpl.class)
    UserService userService;

    private List<Ranking> rankings = new ArrayList<>();

    @Override
    public void onAttach(RankingActivityView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    @Override
    @Background
    public void initRankings() {
        rankings = restClient.getRankings();
        sortRankings();
        view.updateView();
    }

    @Override
    public List<Ranking> getRankings() {
        return rankings;
    }

    /**
     * Sorts rankings by users' XP
     */
    private void sortRankings() {
        Collections.sort(rankings, new Comparator<Ranking>() {
            @Override
            public int compare(Ranking o1, Ranking o2) {
                return o2.getXp() - o1.getXp();
            }
        });
    }

    @Override
    public String getActualUsername(){
        return userService.getActualUser().getUsername();
    }
}
