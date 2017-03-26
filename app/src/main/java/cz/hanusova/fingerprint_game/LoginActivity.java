package cz.hanusova.fingerprint_game;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

import cz.hanusova.fingerprint_game.model.AppUser;
import cz.hanusova.fingerprint_game.model.Character;
import cz.hanusova.fingerprint_game.model.StagTimetable;
import cz.hanusova.fingerprint_game.model.StagUser;
import cz.hanusova.fingerprint_game.rest.RestClient;
import cz.hanusova.fingerprint_game.rest.StagRestClient;
import cz.hanusova.fingerprint_game.utils.Constants;

/**
 * https://github.com/spring-projects/spring-android-samples/blob/master/spring-android-basic-auth/client/src/org/springframework/android/basicauth/MainActivity.java
 * <p/>
 * Created by khanusova on 21.3.2016.
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends AbstractAsyncActivity {
    private static final String TAG = "LoginActivity";

    protected Context context;

    @Pref
    Preferences_ preferences;

    @RestService
    RestClient restClient;

    @RestService
    StagRestClient stagRestClient;

    @Extra(value = Constants.EXTRA_ERROR)
    boolean showError;

    @ViewById(R.id.username)
    EditText etUsername;
    @ViewById(R.id.password)
    EditText etPassword;
    @ViewById(R.id.login_error)
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        String username = preferences.username().get();
        if (!TextUtils.isEmpty(username) && !showError) {
            signIn(username);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (showError) {
            tvError.setText(R.string.login_error);
        }
    }

    @Click(R.id.btn_sign_in)
    protected void saveUser() {
        String username = etUsername.getText().toString();
        preferences.username().put(username);
        preferences.password().put(etPassword.getText().toString());
        signIn(username);
    }

    @Background
    protected void signIn(String username) {
        showLoadingProgressDialog();
        List<StagUser> cisla = stagRestClient.getUserNumberForLogin(username);
        try {
            if (cisla != null && cisla.size() > 0) {
                List<StagTimetable> timetable = stagRestClient.getTimetableToAuthorize(cisla.get(0).getUserNumbers().get(0));
            }
            AppUser user = restClient.login(username);
            ObjectMapper mapper = new ObjectMapper();
            try {
                preferences.user().put(mapper.writeValueAsString(user));
            } catch (JsonProcessingException e) {
                Log.e(TAG, "Error occurred while trying to save user", e);
            }
            dismissProgressDialog();
            MapActivity_.intent(context).start();
        } catch (Exception e) {
            Log.w(TAG, "Exception while getting timetable");
        }
        finish();
    }
}
