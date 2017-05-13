package cz.hanusova.fingerprint_game.rest;

import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Created by khanusova on 3.11.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class LogInterceptor implements ClientHttpRequestInterceptor {

    private static final String TAG = "LogInterceptor";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        //Logging request
        Log.i(TAG, "Request: " + request.getMethod() + " on " + request.getURI());
        Log.i(TAG, "Header: " + request.getHeaders());
        String bodyStr = new String(body);
        Log.i(TAG, "Body: " + bodyStr);

        ClientHttpResponse response = execution.execute(request, body);

        //Logging response
        Log.i(TAG, "Response: " + response.getStatusCode() + " (" + response.getStatusText() + ")"); //TODO: zachytavat kody pro prihlasovani a starou verzi systemu
        Log.i(TAG, "Header: " + response.getHeaders());
        Log.i(TAG, "Body: " + response.getBody().toString()); //TODO: log response
        return response;
    }
}
