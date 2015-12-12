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
import edu.rutgers.cs.rahul.helloworld.LandingPage;
import edu.rutgers.cs.rahul.helloworld.R;
import edu.rutgers.cs.rahul.helloworld.RunActivity;
import edu.rutgers.cs.rahul.helloworld.ShowChallenges;
import edu.rutgers.cs.rahul.helloworld.StatisticsActivity;

/**
 * Created by Rahul
 */
public class LandingPageTest extends ActivityInstrumentationTestCase2<LandingPage> {


    LandingPage activity;
    GoogleConnectionCallbacks callbacks;
    GoogleApiClient mGoogleApiClient;
    Person currentPerson;

    public LandingPageTest(){
        super(LandingPage.class);
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
        currentPerson= Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

        Intent intent = new Intent();
        intent.setClassName("edu.rutgers.cs.rahul.helloworld", LandingPage.class.getName());
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
    public void testNameRetrievalIsWorking() {
        LandingPage activity = getActivity();
        assertNotNull("Landing Page Activity is null",activity);
        final TextView nameEditText = (TextView)(activity.findViewById(R.id.name_landing));

        assertNotNull("Name not retrieved",nameEditText.getText());




    }

    @SmallTest
    public void testRunMenuWorking(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LandingPage.class.getName(), null, false);

        TextView runButton = (TextView) activity.findViewById(R.id.run_landing);

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
