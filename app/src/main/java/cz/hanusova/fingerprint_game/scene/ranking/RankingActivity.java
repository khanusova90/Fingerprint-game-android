package cz.hanusova.fingerprint_game.scene.ranking;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import cz.hanusova.fingerprint_game.R;
import cz.hanusova.fingerprint_game.base.BasePresenter;
import cz.hanusova.fingerprint_game.base.ui.BaseActivity;
import cz.hanusova.fingerprint_game.model.Ranking;
import cz.hanusova.fingerprint_game.scene.ranking.view.RankingItemView;
import cz.hanusova.fingerprint_game.scene.ranking.view.RankingItemView_;

/**
 * Activity to display rankings
 *
 * Created by khanusova on 1.7.2017.
 */
@EActivity(R.layout.activity_ranking)
public class RankingActivity extends BaseActivity implements RankingActivityView {

    @ViewById(R.id.ranking_list)
    ListView rankingList;

    @Bean(RankingActivityPresenterImpl.class)
    RankingActivityPresenter presenter;

    private RankingAdapter adapter;
    private String actualUsername;

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @AfterViews
    void init() {
        actualUsername = presenter.getActualUsername();
        adapter = new RankingAdapter();
        rankingList.setAdapter(adapter);
        presenter.initRankings();
        showProgressDialog(null);
    }

    @Override
    @UiThread
    public void updateView() {
        dismissProgressDialog();
        adapter.notifyDataSetChanged();
    }

    private class RankingAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return presenter.getRankings().size();
        }

        @Override
        public Ranking getItem(int position) {
            Ranking ranking =  presenter.getRankings().get(position);
            ranking.setRanking(position + 1);
            return ranking;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RankingItemView view;
            if (convertView == null) {
                view = RankingItemView_.build(getApplicationContext(), actualUsername);
            } else {
                view = (RankingItemView) convertView;
            }
            view.bind(getItem(position));
            return view;
        }
    }
}
