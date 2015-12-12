package edu.rutgers.cs.rahul.helloworld.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.api.services.youtube.YouTubeScopes;

import edu.rutgers.cs.rahul.helloworld.LoginActivity;
import edu.rutgers.cs.rahul.helloworld.PersonalInfoActivity;
import edu.rutgers.cs.rahul.helloworld.R;

/**
 * Created by valia on 12/11/15.
 */
public class PersonalInfoActivityTest extends ActivityInstrumentationTestCase2<PersonalInfoActivity> {


    PersonalInfoActivity activity;
    GoogleConnectionCallbacks callbacks;
    GoogleApiClient mGoogleApiClient;

    public PersonalInfoActivityTest(){
        super(PersonalInfoActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(callbacks)
                .addOnConnectionFailedListener(callbacks)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .addScope(new Scope(Scopes.EMAIL))
                .addScope(new Scope(YouTubeScopes.YOUTUBE))
                .build();

        LoginActivity.mGoogleApiClient = mGoogleApiClient;
    }

    @SmallTest
    public void testActivityExists() {
        PersonalInfoActivity activity = getActivity();
        final EditText nameEditText = (EditText)activity.findViewById(R.id.usernameField);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                nameEditText.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Valia Kalokyri");



    }

}
