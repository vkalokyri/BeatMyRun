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
import edu.rutgers.cs.rahul.helloworld.RunActivity;
import edu.rutgers.cs.rahul.helloworld.R;
import edu.rutgers.cs.rahul.helloworld.RunResult;

/**
 * Created by valia
 */
public class RunActivityTest extends ActivityInstrumentationTestCase2<RunActivity> {


    RunActivity activity;
    GoogleConnectionCallbacks callbacks;
    GoogleApiClient mGoogleApiClient;

    public RunActivityTest(){
        super(RunActivity.class);
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
        intent.setClassName("edu.rutgers.cs.rahul.helloworld", RunActivity.class.getName());
        intent.putExtra("username", currentPerson.getDisplayName());
        intent.putExtra("email", Plus.AccountApi.getAccountName(mGoogleApiClient));
        setActivityIntent(intent);
        activity = getActivity();

    }

    @SmallTest
    public void testActivityExists() {
        assertNotNull("Run Activity can't start", activity);
    }

    @SmallTest
    public void testRunActivityIsWorking() {
        RunActivity activity = getActivity();
        assertNotNull("Run Activity is null", activity);

        TextView timerButton = (TextView) activity.findViewById(R.id.timer);

        TouchUtils.clickView(this, timerButton);

        TextView BPM = (TextView) activity.findViewById(R.id.BPM);
        TextView steps = (TextView) activity.findViewById(R.id.num_steps);

        assertNotSame("Timer has not started", "Timer", timerButton.getText());
        assertNotSame("BPM counter has not started", "BPM", BPM.getText());
        assertNotSame("Steps counter has not started", "Steps", steps.getText());


    }


    @SmallTest
    public void testStopButtonWorking(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LandingPage.class.getName(), null, false);

        TextView stopButton = (TextView) activity.findViewById(R.id.fab);

        TouchUtils.clickView(this, stopButton);

        Activity nextActivity =  getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        assertNotNull("Run Result Activity page is null", nextActivity);
        assertEquals("Activity is not the Run Result", RunResult.class, nextActivity.getClass());

        nextActivity.finish();
    }

}
