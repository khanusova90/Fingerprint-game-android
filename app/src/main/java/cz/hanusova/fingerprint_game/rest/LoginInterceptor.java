package cz.hanusova.fingerprint_game.rest;

import android.content.Intent;
import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import cz.hanusova.fingerprint_game.FingerprintApplication;
import cz.hanusova.fingerprint_game.R;
import cz.hanusova.fingerprint_game.scene.login.LoginActivity_;

/**
 * Created by khanusova on 23.4.2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class LoginInterceptor implements ClientHttpRequestInterceptor {
    private static final String TAG = "Login Interceptor";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);

        if (response == null || response.getStatusCode() == null) {
            return response;
        }
        switch (response.getStatusCode().value()) {
            case 401:
                Log.d(TAG, "401 from server");
                LoginActivity_.intent(FingerprintApplication.getContext())
                        .errorCode(R.string.login_error_credentials)
                        .flags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .start();
                break;
            case 500:
            case 403:
            case 404:
            case 405:
                Log.d(TAG, "Error from server");
                LoginActivity_.intent(FingerprintApplication.getContext())
                        .errorCode(R.string.login_error_server)
                        .flags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .start();
            default:
                break;
        }
        return response;
    }
}
