package cz.hanusova.fingerprint_game;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import java.util.ArrayList;

import cz.hanusova.fingerprint_game.model.Item;

/**
 * Created by khanusova on 7.10.2016.
 */
@EActivity(R.layout.activity_market)
public class MarketActivity extends AppCompatActivity {

    @Extra
    ArrayList<Item> items;

    @AfterViews
    public void init() {
        //TODO: zobrazit aktualni inventar uzivatele
    }

    @Click(R.id.market_buy)
    public void buy(){
        setResult(RESULT_OK);
        finish();
    }
}
