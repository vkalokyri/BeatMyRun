package edu.rutgers.cs.rahul.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

/**
 * Created by valia on 10/30/15.
 */
public class PersonalInfoActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<People.LoadPeopleResult> {

    private EditText emailField;
    private EditText usernameField;
    private Button submitBtnField;
    private ImageButton logoutId ;


    GoogleApiClient mGoogleApiClient;
    boolean mSignInClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);

        mGoogleApiClient=LoginActivity.mGoogleApiClient;
        emailField = (EditText) findViewById(R.id.emailField);
        usernameField = (EditText) findViewById(R.id.usernameField);
        submitBtnField = (Button) findViewById(R.id.submit);
        logoutId = (ImageButton) findViewById(R.id.logoutId);

        Intent i = getIntent();
        // Receiving the Data
        String name = i.getStringExtra("username");
        String email = i.getStringExtra("email");
        Log.e("Second Screen", name + "." + email);

        // Displaying Received data
        usernameField.setText(name);
        emailField.setText(email);


        // Binding Click event to Button
        submitBtnField.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

               /* if (mGoogleApiClient.isConnected()) {
                    Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                    mGoogleApiClient.disconnect();
                   System.err.println("LOG OUT ^^^^^^^^^^^^^^^^^^^^ SUCESS");
                }*/
                //Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);

                Intent nextScreen = new Intent(getApplicationContext(), RunActivity.class);

                //Sending data to another Activity
                startActivity(nextScreen);

            }
        });

        logoutId.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (mGoogleApiClient.isConnected()) {
                    Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                    mGoogleApiClient.disconnect();
                    System.err.println("LOG OUT ^^^^^^^^^^^^^^^^^^^^ SUCESS");
                }
                Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(nextScreen);



            }
        });

    }


    @Override
    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub
        mSignInClicked = false;

        // updateUI(true);
        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub
        mGoogleApiClient.connect();
        // updateUI(false);
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onResult(People.LoadPeopleResult loadPeopleResult) {

    }
}
