package cz.hanusova.fingerprint_game;

import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.ViewById;

import cz.hanusova.fingerprint_game.model.Place;

/**
 * Created by khanusova on 4.10.2016.
 */
@EActivity(R.layout.activity_mine)
public class MineActivity extends AppCompatActivity {

    @ViewById(R.id.mine_free_workers)
    TextView tvFreeWorkers;
    @ViewById(R.id.mine_choose_workers)
    SeekBar seekWorkers;
    @ViewById(R.id.mine_workers_amount)
    TextView tvWorkersAmount;

    @Extra
    Place place;

    @AfterViews
    public void init(){
        //TODO: zobrazit pocet pracovniku k dispozici
        //TODO: nastavit podle poctu pracovniku k dispozici
        //TODO: zobrazit, o jakou surovinu se jedná
        seekWorkers.setProgress(20);
        seekWorkers.setMax(50);
    }

    @SeekBarProgressChange(R.id.mine_choose_workers)
    public void changeWorkersAmount(){
        tvWorkersAmount.setText(String.valueOf(seekWorkers.getProgress()));
    }
}
