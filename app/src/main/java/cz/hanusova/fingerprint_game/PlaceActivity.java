package cz.hanusova.fingerprint_game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

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

        new HttpAsyncTask().execute(place.getPlaceType());
    }

    private class HttpAsyncTask extends AsyncTask<PlaceType, Void, Bitmap>{

        ImageView imgView = (ImageView) findViewById(R.id.place_image);

        @Override
        protected Bitmap doInBackground(PlaceType... placeType) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(Constants.IMG_URL_BASE + placeType[0].getImgUrl());
                InputStream inputStream = url.openConnection().getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
            } catch (Exception e) {
                Log.e(TAG, "Error occurred while trying to show image");
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imgView.setImageBitmap(bitmap);
        }
    }

}
