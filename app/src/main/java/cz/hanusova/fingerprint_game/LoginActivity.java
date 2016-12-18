package cz.hanusova.fingerprint_game;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import cz.hanusova.fingerprint_game.model.AppUser;
import cz.hanusova.fingerprint_game.model.Character;
import cz.hanusova.fingerprint_game.rest.RestClient;

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

    @ViewById(R.id.username)
    EditText etUsername;
    @ViewById(R.id.password)
    EditText etPassword;

    @Click(R.id.login_test)
    public void loginTest(){
        AppUser test = new AppUser();
        test.setUsername("test");

        preferences.username().put("zkouska");
        preferences.password().put("zkouska");

        Character character = new Character();
        character.setCharisma(100);
        character.setPower(100);
        character.setXp(100);
        test.setCharacter(character);
        try {
            ObjectMapper mapper = new ObjectMapper();
            preferences.user().put(mapper.writeValueAsString(test));
        } catch (JsonProcessingException e) {
            Log.e(TAG, "Error occurred while trying to save user", e);
        }
        MapActivity_.intent(context).start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }

    @Background
    @Click(R.id.btn_sign_in)
    protected void signIn(){
        showLoadingProgressDialog();
        String username = etUsername.getText().toString();
        preferences.username().put(username);
        preferences.password().put(etPassword.getText().toString());
        AppUser user = restClient.login(username);
        ObjectMapper mapper = new ObjectMapper();
        try {
            preferences.user().put(mapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            Log.e(TAG, "Error occurred while trying to save user", e);
        }
        dismissProgressDialog();
        MapActivity_.intent(context).start();
    }
}
