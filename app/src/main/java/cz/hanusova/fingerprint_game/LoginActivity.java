package cz.hanusova.fingerprint_game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import cz.hanusova.fingerprint_game.model.AppUser;
import cz.hanusova.fingerprint_game.utils.Constants;

/**
 * https://github.com/spring-projects/spring-android-samples/blob/master/spring-android-basic-auth/client/src/org/springframework/android/basicauth/MainActivity.java
 * <p/>
 * Created by khanusova on 21.3.2016.
 */
public class LoginActivity extends AbstractAsyncActivity {
    private static final String TAG = "LoginActivity";

    protected Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLoginBtn();
        context = this;
    }

    private void initLoginBtn() {
        Button loginBtn = (Button) findViewById(R.id.btn_sign_in);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginTask().execute();
            }
        };
        loginBtn.setOnClickListener(listener);
    }

    /*
     * Private classes
     * resource: https://github.com/spring-projects/spring-android-samples/blob/master/spring-android-basic-auth/client/src/org/springframework/android/basicauth/MainActivity.java
     */
    private class LoginTask extends AsyncTask<Void, Void, AppUser> {

        private String username;
        private String password;

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();

            EditText usernameEt = (EditText) findViewById(R.id.username);
            this.username = usernameEt.getText().toString();

            EditText passEt = (EditText) findViewById(R.id.password);
            this.password = BCrypt.hashpw(passEt.getText().toString(), BCrypt.gensalt());
            this.password = passEt.getText().toString();
        }

        @Override
        protected AppUser doInBackground(Void... params) {
            String url = Constants.URL_BASE + "/login";

            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            HttpHeaders reqHeaders = new HttpHeaders();
            reqHeaders.setAuthorization(authHeader);
            reqHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("username", username);

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, reqHeaders);
            try {
                ResponseEntity<AppUser> resp = restTemplate.exchange(url, HttpMethod.POST, entity, AppUser.class);
                return resp.getBody();
            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return null;
            } catch (ResourceAccessException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(AppUser user) {
            dismissProgressDialog();
            SharedPreferences sp = context.getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            editor.putString(getString(R.string.username), user.getUsername());
            editor.putString(getString(R.string.stagname), user.getStagname());
            editor.commit();

            Intent info = new Intent(context, InfoActivity.class);
            startActivity(info);
        }
    }
}
