package cz.hanusova.fingerprint_game;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import cz.hanusova.fingerprint_game.model.Place;
import cz.hanusova.fingerprint_game.model.PlaceType;
import cz.hanusova.fingerprint_game.utils.Constants;

/**
 * Created by khanusova on 27.4.2016.
 */
public class PlaceActivity extends AbstractAsyncActivity {
    private static final String TAG = "PlaceActivity";

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
        TextView placeNameTv = (TextView) findViewById(R.id.place_name);
        TextView descriptionTv = (TextView) findViewById(R.id.place_placeDescription);

        idPlaceTv.setText(getString(R.string.place_id, place.getIdPlace()));
        placeNameTv.setText(place.getName());
        descriptionTv.setText(place.getPlaceType().getDescription());

        drawPlaceImage(place.getPlaceType());
    }

    private void drawPlaceImage(PlaceType placeType){
        ImageView imgView = (ImageView) findViewById(R.id.place_image);
        imgView.setImageResource(R.drawable.money_icon);
//        try {
//            URL url = new URL(Constants.IMG_URL_BASE + placeType.getImgUrl());
//            InputStream inputStream = url.openStream();
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//            imgView.setImageBitmap(bitmap);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
