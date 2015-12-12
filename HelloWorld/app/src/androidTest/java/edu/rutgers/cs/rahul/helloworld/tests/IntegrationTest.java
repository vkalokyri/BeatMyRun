package edu.rutgers.cs.rahul.helloworld.tests;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.model.people.Person;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.youtube.YouTube;

import edu.rutgers.cs.rahul.helloworld.LandingPage;
import edu.rutgers.cs.rahul.helloworld.LoginActivity;
import edu.rutgers.cs.rahul.helloworld.PersonalInfoActivity;
import edu.rutgers.cs.rahul.helloworld.R;
import edu.rutgers.cs.rahul.helloworld.RunActivity;
import edu.rutgers.cs.rahul.helloworld.ShowChallenges;
import edu.rutgers.cs.rahul.helloworld.StatisticsActivity;

/**
 * Created by Rahul
 */
public class IntegrationTest extends ActivityInstrumentationTestCase2<LoginActivity> {


    LoginActivity activity;
    LandingPage landingPage;
    RunActivity runActivity;

    public IntegrationTest(){
        super(LoginActivity.class);
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


        String googleAPIkey = activity.API_KEY;
        assertNotNull("Google Api key not set", googleAPIkey);

        String echonestAPIkey = activity.echonest_API_key;
        assertNotNull("Echonest Api key not set", echonestAPIkey);


        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LandingPage.class.getName(), null, false);

        com.google.android.gms.common.SignInButton signInButton = (com.google.android.gms.common.SignInButton) activity.findViewById(R.id.sign_in_button);

        TouchUtils.clickView(this, signInButton);

        GoogleApiClient gClient = activity.mGoogleApiClient;

        assertNotNull("Google Client is null", gClient);

        Person person = activity.currentPerson;
        assertNotNull("Google person is null", person);

        Activity nextActivity =  getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        assertNotNull("Landing page activity is null", nextActivity);
        assertEquals("Activity is not the landing page", LandingPage.class, nextActivity.getClass());

        setActivity(nextActivity);
        landingPage = (LandingPage)nextActivity;



    }

    @SmallTest
    public void testNameRetrievalIsWorking() {

        assertNotNull("Landing Page Activity is null",landingPage);
        final TextView nameEditText = (TextView)(landingPage.findViewById(R.id.name_landing));

        assertNotNull("Name not retrieved", nameEditText.getText());




    }

    @SmallTest
    public void testRunMenuWorking(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LandingPage.class.getName(), null, false);

        TextView runButton = (TextView) landingPage.findViewById(R.id.run_landing);

        TouchUtils.clickView(this, runButton);

        Activity nextActivity =  getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        assertNotNull("Run Activity page is null", nextActivity);
        assertEquals("Activity is not the Run page", RunActivity.class, nextActivity.getClass());

        nextActivity.finish();
    }

    @SmallTest
    public void testChallengeMenuWorking(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LandingPage.class.getName(), null, false);

        TextView challengeButton = (TextView) activity.findViewById(R.id.challenge_landing);

        TouchUtils.clickView(this, challengeButton);

        Activity nextActivity =  getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        assertNotNull("Challenge Activity page is null", nextActivity);
        assertEquals("Activity is not the Challenge page", ShowChallenges.class, nextActivity.getClass());

        nextActivity.finish();
    }


    @SmallTest
    public void testStatisticsMenuWorking(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LandingPage.class.getName(), null, false);

        TextView statButton = (TextView) activity.findViewById(R.id.stat_landing);

        TouchUtils.clickView(this, statButton);

        Activity nextActivity =  getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        assertNotNull("Stat Activity page is null", nextActivity);
        assertEquals("Activity is not the Stat page", StatisticsActivity.class, nextActivity.getClass());

        nextActivity.finish();
    }


}