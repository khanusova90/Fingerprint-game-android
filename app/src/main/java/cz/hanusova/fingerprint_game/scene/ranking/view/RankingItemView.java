package cz.hanusova.fingerprint_game.scene.ranking.view;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import cz.hanusova.fingerprint_game.R;
import cz.hanusova.fingerprint_game.model.Ranking;

/**
 * Created by khanusova on 01/07/2017.
 */
@EViewGroup(R.layout.item_ranking)
public class RankingItemView extends RelativeLayout {

    @ViewById(R.id.ranking_user)
    TextView usernameTextView;
    @ViewById(R.id.ranking_xp)
    TextView xpTextView;

    private String actualUsername;

    public RankingItemView(Context context, String actualUsername) {
        super(context);
        this.actualUsername = actualUsername;
    }

    public void bind(Ranking ranking) {
        usernameTextView.setText(ranking.getUsername());
        xpTextView.setText(String.valueOf(ranking.getXp()));

        if (ranking.getUsername().equals(actualUsername)){
            int color = getResources().getColor(R.color.colorAccent);
            usernameTextView.setTextColor(color);
            xpTextView.setTextColor(color);
        }

    }
}
