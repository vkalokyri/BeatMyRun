package edu.rutgers.cs.rahul.helloworld.tests;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.TextView;

import edu.rutgers.cs.rahul.helloworld.Datepicker_days;
import edu.rutgers.cs.rahul.helloworld.Monthpicker_month;
import edu.rutgers.cs.rahul.helloworld.R;
import edu.rutgers.cs.rahul.helloworld.StatisticsActivity;

/**
 * Created by nivethamahalakshmibalasamy
 */
public class StatisticsTest extends ActivityInstrumentationTestCase2<StatisticsActivity> {


    StatisticsActivity activity;

    public StatisticsTest() {
        super(StatisticsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void testActivityExists() {
        StatisticsActivity activity = getActivity();
        assertNotNull(activity);
    }

    @SmallTest
    public void TestCurrentRunStatView() {

        StatisticsActivity activity = getActivity();
        final TextView totalmiles =
                (TextView) activity.findViewById(R.id.totalmiles);
        String actualText = totalmiles.getText().toString();
        assertEquals("totalmiles", actualText);

        final TextView totaltime =
                (TextView) activity.findViewById(R.id.totaltime);
        String actualText1 = totaltime.getText().toString();
        assertEquals("totaltime", actualText1);

        final TextView totalcalories =
                (TextView) activity.findViewById(R.id.totalcalories);
        String actualText2 = totalcalories.getText().toString();
        assertEquals("totalcalories", actualText2);

        getInstrumentation().waitForIdleSync();


//    public void testActivityExistsforDate() {
//        Datepicker_days activity = getActivity();
//        assertNotNull(activity);
//    }

        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(Datepicker_days.class.getName(), null, false);

        Button weekbutton = (Button) activity.findViewById(R.id.summaryday);
        TouchUtils.clickView(this, weekbutton);
        assertNotNull("Date summary is null", weekbutton);

//        Button monthbutton = (Button) activity.findViewById(R.id.summarymonth);
//        TouchUtils.clickView(this, monthbutton);
//        assertNotNull("Month summary is null", monthbutton);

        Activity nextActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        assertNotNull("Datepicker_days activity is null", nextActivity);
        assertEquals("Activity is not the Datepicker_days", Datepicker_days.class, nextActivity.getClass());

        Button monthbutton = (Button) activity.findViewById(R.id.summarymonth);
        TouchUtils.clickView(this, monthbutton);
        assertNotNull("Month summary is null", monthbutton);

        Activity nextActivitymonth = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        assertNotNull("Monthpicker_month activity is null", nextActivitymonth);
        assertEquals("Activity is not the Monthpicker_month", Monthpicker_month.class, nextActivity.getClass());

        nextActivity.finish();
        nextActivitymonth.finish();


    }

}
















