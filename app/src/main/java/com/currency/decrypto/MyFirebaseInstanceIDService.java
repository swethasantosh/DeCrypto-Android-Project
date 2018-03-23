package com.currency.decrypto;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;

/**
 * Created by swetha on 1/6/18.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService
{
    private static final String Tag = "Myfirebaseinstservice";

    @Override
    public void onTokenRefresh()
    {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(Tag, "Refreshed token: " + refreshedToken);
       // sendRegistrationToServer(refreshedToken);


    }
}
