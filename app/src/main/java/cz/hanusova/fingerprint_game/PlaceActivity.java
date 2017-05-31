package cz.hanusova.fingerprint_game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.hanusova.fingerprint_game.adapter.ActivitySpinAdapter;
import cz.hanusova.fingerprint_game.base.AbstractAsyncActivity;
import cz.hanusova.fingerprint_game.model.Activity;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        setPlaceInfo();
        showActivities();
        addStartBtnListener();
    }

    private void setPlaceInfo() {
        Bundle extras = getIntent().getExtras();
        place = (Place) extras.get(Constants.EXTRA_PLACE);

        TextView idPlaceTv = (TextView) findViewById(R.id.place_placeId);
        TextView placeNameTv = (TextView) findViewById(R.id.place_name);
        TextView descriptionTv = (TextView) findViewById(R.id.place_placeDescription);

        idPlaceTv.setText(getString(R.string.place_id, place.getIdPlace()));
        placeNameTv.setText(place.getName());
        descriptionTv.setText(place.getDescription());

        new ImageHttpAsyncTask().execute(place.getPlaceType());
    }

    private void showActivities() {
//        List<Activity> activities = place.getPlaceType().getActivities();
        List<Activity> activities = new ArrayList<>();

        Spinner activitySpin = (Spinner) findViewById(R.id.spin_place_activity);
        final ArrayAdapter<Activity> adapter = new ActivitySpinAdapter(this, android.R.layout.simple_spinner_item, activities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activitySpin.setAdapter(adapter);
    }

    private void addStartBtnListener() {
        Button btnStart = (Button) findViewById(R.id.btn_place_activity);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner = (Spinner) findViewById(R.id.spin_place_activity);
                Activity activity = (Activity) spinner.getSelectedItem();
                System.out.println("CHOSEN ACTIVITY: " + activity.getName() + ", ID: " + activity.getIdActivity());
            }
        });
    }

    /**
     * AsyncTask for obtaining place image
     */
    private class ImageHttpAsyncTask extends AsyncTask<PlaceType, Void, Bitmap> {

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

//    private class ActivityHttpAsyncTask extends AsyncTask<Activity, Void, Void>{
//
//        @Override
//        protected Void doInBackground(Activity... params) {
//            try {
//                String url = Constants.URL_BASE + "/place/addActivity";
//                Map<String, String> userMap = new HashMap<>();
//                SharedPreferences sp = getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);
//                userMap.put("username", sp.getString(getString(R.string.username), null));
//                RestTemplate template = new RestTemplate();
//                template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//
//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_JSON);
//
//                //TODO: vic entit v requestu
//                HttpEntity<Place> placeEntity = new HttpEntity<>(place, headers);
//                template.exchange(url, HttpMethod.POST, placeEntity, Void.class, userMap);
//            } catch (Exception e){
//                Log.e(TAG, "Error occured while trying to add user's activity");
//            }
//
//            return null;
//        }
//    }

}
