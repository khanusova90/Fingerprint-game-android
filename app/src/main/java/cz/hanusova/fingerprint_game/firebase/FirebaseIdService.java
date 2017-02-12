package cz.hanusova.fingerprint_game.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by khanusova on 11.2.2017.
 */

public class FirebaseIdService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIdService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //TODO: poslat na server
//        sendRegistrationToServer(refreshedToken);
    }
}
