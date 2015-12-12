package edu.rutgers.cs.rahul.helloworld.tests;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.api.services.youtube.YouTubeScopes;

import edu.rutgers.cs.rahul.helloworld.LandingPage;
import edu.rutgers.cs.rahul.helloworld.LoginActivity;
import edu.rutgers.cs.rahul.helloworld.PersonalInfoActivity;
import edu.rutgers.cs.rahul.helloworld.R;

/**
 * Created by valia
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
        Person currentPerson= Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

        Intent intent = new Intent();
        intent.setClassName("edu.rutgers.cs.rahul.helloworld", PersonalInfoActivity.class.getName());
        intent.putExtra("username", currentPerson.getDisplayName());
        intent.putExtra("email", Plus.AccountApi.getAccountName(mGoogleApiClient));
        setActivityIntent(intent);
        activity = getActivity();

    }

    @SmallTest
    public void testActivityExists() {
        assertNotNull("Login Activity can't start", activity);
    }

    @SmallTest
    public void testPersonalActivityIsWorking() {
        PersonalInfoActivity activity = getActivity();
        assertNotNull("Person Info Activity is null",activity);
        final EditText nameEditText = (EditText)activity.findViewById(R.id.usernameField);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                nameEditText.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Valia Kalokyri");


        final EditText emailEditText = (EditText)activity.findViewById(R.id.emailField);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                emailEditText.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("vkalokyri@gmail.com");

        final EditText ageEditText = (EditText)activity.findViewById(R.id.ageField);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                ageEditText.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("27");

        final EditText heightEditText = (EditText)activity.findViewById(R.id.heightField);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                heightEditText.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("160");


        final EditText weightEditText = (EditText)activity.findViewById(R.id.weightField);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                weightEditText.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("54");



        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LandingPage.class.getName(), null, false);

        Button submitButton = (Button) activity.findViewById(R.id.submit);

        TouchUtils.clickView(this, submitButton);


        Activity nextActivity =  getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        assertNotNull("Landing page activity is null", nextActivity);
        assertEquals("Activity is not the landing page", LandingPage.class, nextActivity.getClass());

        nextActivity.finish();


    }

}
