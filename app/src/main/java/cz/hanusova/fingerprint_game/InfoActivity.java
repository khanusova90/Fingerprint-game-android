package cz.hanusova.fingerprint_game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import cz.hanusova.fingerprint_game.model.Place;
import cz.hanusova.fingerprint_game.utils.Constants;

/**
 * Created by khanusova on 4.4.2016.
 */
public class InfoActivity extends AbstractAsyncActivity {
    private static final String TAG = "InfoActivity";

    private static final int RC_BARCODE_CAPTURE = 9001;
    private TextView errorMessageTv;

    private String url;
    private Context context;

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

        initQrBtn(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE){
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra("Barcode");
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                    url = barcode.displayValue;
                    new HttpAsyncTask().execute();
                } else {
                    errorMessageTv.setText(R.string.info_scan_fail);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                errorMessageTv.setText(String.format(getString(R.string.info_scan_fail),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initQrBtn(final Context context) {
        Button loginBtn = (Button) findViewById(R.id.btn_info_scan);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QrActivity.class);
                startActivityForResult(intent, RC_BARCODE_CAPTURE);
            }
        };
        loginBtn.setOnClickListener(listener);
    }

    private class HttpAsyncTask extends AsyncTask <Void, Void, Place>{

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
