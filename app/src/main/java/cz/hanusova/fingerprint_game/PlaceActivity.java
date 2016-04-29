package cz.hanusova.fingerprint_game;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import cz.hanusova.fingerprint_game.model.Place;
import cz.hanusova.fingerprint_game.utils.Constants;

/**
 * Created by khanusova on 27.4.2016.
 */
public class PlaceActivity extends Activity {
    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_detail);
        setPlaceValues();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setPlaceValues();
    }

    private void setPlaceValues(){
        Bundle extras = getIntent().getExtras();
        place = (Place)extras.get(Constants.EXTRA_PLACE);

        TextView idPlaceTv = (TextView) findViewById(R.id.place_placeId);
        TextView descriptionTv = (TextView) findViewById(R.id.place_placeDescription);

        idPlaceTv.setText(getString(R.string.place_id, place.getIdPlace()));
        descriptionTv.setText(getString(R.string.place_info, place.getDescription()));
    }

}
