package cz.hanusova.fingerprint_game;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by khanusova on 7.10.2016.
 */
@EActivity(R.layout.activity_market)
public class MarketActivity extends AppCompatActivity {

    @AfterViews
    public void init() {
        //TODO: zobrazit aktualni inventar uzivatele
    }
}
