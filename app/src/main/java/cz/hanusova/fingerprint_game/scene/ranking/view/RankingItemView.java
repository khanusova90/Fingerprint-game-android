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
    @ViewById(R.id.ranking_number)
    TextView rankingTextView;

    private String actualUsername;

    public RankingItemView(Context context, String actualUsername) {
        super(context);
        this.actualUsername = actualUsername;
    }

    public void bind(Ranking ranking) {
        usernameTextView.setText(ranking.getUsername());
        xpTextView.setText(String.valueOf(ranking.getXp()));
        rankingTextView.setText(String.valueOf(ranking.getRanking()));

        if (ranking.getUsername().equals(actualUsername)){
            int colorAccent = getResources().getColor(R.color.colorAccent);
            int colorWhite = getResources().getColor(R.color.colorWhite);

            usernameTextView.setTextColor(colorWhite);
            xpTextView.setTextColor(colorWhite);
            rankingTextView.setTextColor(colorWhite);
            setBackgroundColor(colorAccent);
        }

    }
}
