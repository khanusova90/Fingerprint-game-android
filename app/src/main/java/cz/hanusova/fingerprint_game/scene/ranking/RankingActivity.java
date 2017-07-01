package cz.hanusova.fingerprint_game.scene.ranking;

import android.app.ListActivity;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import cz.hanusova.fingerprint_game.R;

/**
 * Created by khanusova on 1.7.2017.
 */
@EActivity(R.layout.activity_ranking)
public class RankingActivity extends ListActivity {

    @Bean(RankingActivityPresenterImpl.class)
    RankingActivityPresenter presenter;

    
}
