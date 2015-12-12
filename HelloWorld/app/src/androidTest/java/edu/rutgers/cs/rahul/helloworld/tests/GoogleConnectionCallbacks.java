package edu.rutgers.cs.rahul.helloworld.tests;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Rahul
 * */
public class GoogleConnectionCallbacks implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    @Override
    public void onConnected(Bundle bundle) {
        Log.i("Google Callback", "Connected");
    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.e("Google Callback", "Connected");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
