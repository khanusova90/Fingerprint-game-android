package cz.hanusova.fingerprint_game.rest;

import android.util.Base64;

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

import cz.hanusova.fingerprint_game.Preferences_;

/**
 * Created by khanusova on 10.10.2016.
 * <p>
 * Interceptor for handling security requests <br />
 * Adds authentication information to request header
 */
@EBean(scope = EBean.Scope.Singleton)
public class AuthInterceptor implements ClientHttpRequestInterceptor {

    @Pref
    Preferences_ preferences;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        HttpAuthentication auth = new HttpBasicAuthentication(preferences.user().get(),
                preferences.password().get());

        headers.setAuthorization(auth);
        return execution.execute(request, body);
    }
}
