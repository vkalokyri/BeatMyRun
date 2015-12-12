package edu.rutgers.cs.rahul.helloworld.tests;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.model.people.Person;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.youtube.YouTube;

import edu.rutgers.cs.rahul.helloworld.LandingPage;
import edu.rutgers.cs.rahul.helloworld.LoginActivity;
import edu.rutgers.cs.rahul.helloworld.PersonalInfoActivity;
import edu.rutgers.cs.rahul.helloworld.R;
import edu.rutgers.cs.rahul.helloworld.ShowChallenges;

/**
 * Created by valia on 12/11/15.
 */
public class ShowChallengesTest extends ActivityInstrumentationTestCase2<ShowChallenges> {


    ShowChallenges activity;

    public ShowChallengesTest(){
        super(ShowChallenges.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }


    @SmallTest
    public void testActivityExists() {
        assertNotNull("Login Activity can't start", activity);
    }

    @SmallTest
    public void testLoginIsWorking() {


        String distance = activity.distance;
        assertNotNull("Distance doesnot exist", distance);

        String duration = activity.duration;
        assertNotNull("Duration doesnot exist", duration);


        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LandingPage.class.getName(), null, false);

        //com.google.android.gms.common.SignInButton greetButton =
                //(com.google.android.gms.common.SignInButton) activity.findViewById(R.id.sign_in_button);

        //TouchUtils.clickView(this, greetButton);

        TextView wonview = (TextView)activity.findViewById(R.id.totalmiles);
        assertNotNull("Miles are null", wonview);

        TextView calories = (TextView)activity.findViewById(R.id.calories);
        assertNotNull("Calories are null", calories);

        Spinner spinner = (Spinner)activity.findViewById(R.id.ChallengesSpinner_nav);
        assertNotNull("Spineer is null", spinner);


        Activity nextActivity =  getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        assertNotNull("Landing page activity is null", nextActivity);
        assertEquals("Activity is not the landing page", LandingPage.class, nextActivity.getClass());

        nextActivity.finish();

    }

}