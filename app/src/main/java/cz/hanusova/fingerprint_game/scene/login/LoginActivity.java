package cz.hanusova.fingerprint_game.scene.login;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.ResourceAccessException;

import cz.hanusova.fingerprint_game.Preferences_;
import cz.hanusova.fingerprint_game.R;
import cz.hanusova.fingerprint_game.base.BasePresenter;
import cz.hanusova.fingerprint_game.base.ui.BaseActivity;
import cz.hanusova.fingerprint_game.scene.map.MapActivity_;
import cz.hanusova.fingerprint_game.model.AppUser;
import cz.hanusova.fingerprint_game.rest.LoginClient;
import cz.hanusova.fingerprint_game.utils.Constants;
import cz.hanusova.fingerprint_game.utils.ValidationUtils;

/**
 * https://github.com/spring-projects/spring-android-samples/blob/master/spring-android-basic-auth/client/src/org/springframework/android/basicauth/MainActivity.java
 * <p/>↨
 * Created by khanusova on 21.3.2016.
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements LoginActivityView{
    private static final String TAG = "LoginActivity";
    private Boolean stayIn = false;
    private String error;

    protected Context context;

    @Pref
    Preferences_ preferences;

    @Bean (LoginActivityPresenterImpl.class)
    LoginActivityPresenter presenter;

    @RestService
    LoginClient restClient;

    @Extra(value = Constants.EXTRA_ERROR)
    Integer errorCode;

    @ViewById(R.id.username)
    EditText etUsername;
    @ViewById(R.id.password)
    EditText etPassword;
    @ViewById(R.id.login_error)
    TextView tvError;
    @ViewById(R.id.checkBoxIn)
    CheckBox checkBoxIn;

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        String username = preferences.username().get();
        if (preferences.stayIn().get() && !TextUtils.isEmpty(username) && TextUtils.isEmpty(error)) {
            signIn(username);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (errorCode == null) {
            error = "";
            tvError.setVisibility(View.GONE);
        } else {
            setLoginError(getString(errorCode));
        }
    }

    @Click(R.id.btn_sign_in)
    protected void saveUser() {
        String username = etUsername.getText().toString();
        preferences.username().put(username);
        preferences.password().put(etPassword.getText().toString());
        if (ValidationUtils.isNotEmpty(etUsername) && ValidationUtils.isNotEmpty(etPassword)) {
            signIn(username);
        }
    }

    @CheckedChange(R.id.checkBoxIn)
    protected void checkedChange(Boolean ischecked) {
        preferences.stayIn().put(ischecked);
    }

    @Background
    protected void signIn(String username) {
        showProgressDialog(null);
        try {
            AppUser user = restClient.login(username);
            ObjectMapper mapper = new ObjectMapper();
            try {
                preferences.user().put(mapper.writeValueAsString(user));
                MapActivity_.intent(context).start();
                dismissProgressDialog();
                finish();
            } catch (JsonProcessingException e) {
                Log.e(TAG, "Could not save user JSON", e);
                setLoginError(getString(R.string.login_error_json));
            }
        } catch (Exception e) {
            Log.e(TAG, "Could not connect to server", e);
            setLoginError(getString(R.string.login_error_server));
//        } catch (Exception e) {
//            Log.e(TAG, "Exception ocurred while trying to login", e);
//            dismissProgressDialog();
//            finish();
        } finally {
            dismissProgressDialog();
        }
    }

    @UiThread
    protected void setLoginError(String text) {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(text);
        etPassword.setText("");
        preferences.clear();
    }

}