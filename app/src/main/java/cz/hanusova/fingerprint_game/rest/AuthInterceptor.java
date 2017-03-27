package cz.hanusova.fingerprint_game.rest;

import android.content.Intent;
import android.util.Base64;
import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import cz.hanusova.fingerprint_game.FingerprintApplication;
import cz.hanusova.fingerprint_game.LoginActivity;
import cz.hanusova.fingerprint_game.LoginActivity_;
import cz.hanusova.fingerprint_game.MapActivity_;
import cz.hanusova.fingerprint_game.Preferences_;
import cz.hanusova.fingerprint_game.utils.Constants;

/**
 * Created by khanusova on 10.10.2016.
 * <p>
 * Interceptor for handling security requests <br />
 * Adds authentication information to request header
 */
@EBean(scope = EBean.Scope.Singleton)
public class AuthInterceptor implements ClientHttpRequestInterceptor {
    private static final String TAG = "AuthInterceptor";

    @Pref
    Preferences_ preferences;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        String credentials = preferences.username().get() + ":" + preferences.password().get();
        final String basic =
                "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headers.add("authorization", basic);
        ClientHttpResponse response =  execution.execute(request, body);
        if (response == null || response.getStatusCode() == null){
            return response;
        }
        switch (response.getStatusCode().value()){
            case 401:
                Log.d(TAG, "401 from server");
                LoginActivity_.intent(FingerprintApplication.getContext())
                        .showError(true)
                        .flags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .start();
                break;
            default:
                break;
        }
        return response;
    }
}
