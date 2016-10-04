package cz.hanusova.fingerprint_game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import cz.hanusova.fingerprint_game.model.AppUser;
import cz.hanusova.fingerprint_game.model.Character;
import cz.hanusova.fingerprint_game.model.Inventory;
import cz.hanusova.fingerprint_game.model.Place;
import cz.hanusova.fingerprint_game.utils.Constants;

/**
 * Created by khanusova on 4.4.2016.
 */
@EActivity
public class InfoActivity extends AbstractAsyncActivity {
    private static final String TAG = "InfoActivity";

    private static final int RC_BARCODE_CAPTURE = 9001;
    private TextView errorMessageTv;

    private String url;
    private Context context;

    @Extra
    AppUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        context = this;
        SharedPreferences sp = this.getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);

        TextView usernameTv = (TextView) findViewById(R.id.info_username);
        TextView stagnameTv = (TextView) findViewById(R.id.info_stagname);
        errorMessageTv = (TextView) findViewById(R.id.info_scan_error);

        String username = sp.getString(getString(R.string.username), getString(R.string.username));
        String stagname = sp.getString(getString(R.string.stagname), getString(R.string.stagname));

        usernameTv.setText(username);
        stagnameTv.setText(stagname);

        Bundle extras = getIntent().getExtras();
        if (user == null){
        AppUser user = (AppUser) extras.get(Constants.EXTRA_USER);}
        showInventory(user);

        initQrBtn(this);
        initMapButton();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE){
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra("Barcode");
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                    url = barcode.displayValue;
                    new PlaceAsyncTask().execute();
                } else {
                    errorMessageTv.setText(R.string.info_scan_fail);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                String errorMsg = getString(R.string.info_scan_fail, CommonStatusCodes.getStatusCodeString(resultCode));
                errorMessageTv.setText(errorMsg);
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showInventory(AppUser user){
//        TextView header = (TextView) findViewById(R.id.info_inventory);
        LinearLayout layout = (LinearLayout)findViewById(R.id.layout_info);
        setContentView(layout);

        TextView charHeader = new TextView(this);
        charHeader.setTypeface(Typeface.DEFAULT_BOLD);
        charHeader.setPadding(0, 10, 0, 0);
        charHeader.setText(getString(R.string.info_character));

        layout.addView(charHeader);

        Character character = user.getCharacter();

        TextView charisma = new TextView(this);
        charisma.setText(getString(R.string.character_charisma) + ": " + character.getCharisma());
        layout.addView(charisma);

        TextView power = new TextView(this);
        power.setText(getString(R.string.character_power) + ": " + character.getPower());
        layout.addView(power);

        TextView xp = new TextView(this);
        xp.setText(getString(R.string.character_xp) + ": " + character.getXp());
        layout.addView(xp);

//        Resources res = getResources();

        TextView invHeader = new TextView(this);
        invHeader.setTypeface(Typeface.DEFAULT_BOLD);
        invHeader.setPadding(0, 10, 0, 0);
        invHeader.setText(getString(R.string.info_inventory));
        layout.addView(invHeader);

        for (Inventory i : user.getInventory()){
            TextView tv = new TextView(this);
//            String materialId = "R.string" + i.getMaterial().toString().toLowerCase();
//
//            int stringId = res.getIdentifier(materialId, "string", getPackageName());
//            tv.setText(getString(stringId) + ": " + i.getAmount());
            tv.setText(i.getMaterial() + ": " + i.getAmount());
            layout.addView(tv);
        }
    }

    private void initQrBtn(final Context context) {
        Button loginBtn = (Button) findViewById(R.id.btn_info_scan);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QrActivity_.class);
                startActivityForResult(intent, RC_BARCODE_CAPTURE);
            }
        };
        loginBtn.setOnClickListener(listener);
    }

    private void initMapButton(){
        Button btnShowMap = (Button) findViewById(R.id.btn_info_show_map);
        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MapActivity.class);
                startActivity(i);
            }
        });
    }

    private class PlaceAsyncTask extends AsyncTask <Void, Void, Place>{

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }

        @Override
        protected Place doInBackground(Void... params) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Place place = restTemplate.getForObject(url, Place.class);
            return place;
        }

        @Override
        protected void onPostExecute(Place place) {
            dismissProgressDialog();
            Intent intent = new Intent(context, PlaceActivity.class);
            intent.putExtra(Constants.EXTRA_PLACE, place);
            startActivity(intent);
        }
    }



}
