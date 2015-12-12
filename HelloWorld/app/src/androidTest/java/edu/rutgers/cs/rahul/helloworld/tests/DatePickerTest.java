package edu.rutgers.cs.rahul.helloworld.tests;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;

import edu.rutgers.cs.rahul.helloworld.Chart;
import edu.rutgers.cs.rahul.helloworld.Datepicker_days;
import edu.rutgers.cs.rahul.helloworld.R;

/**
 * Created by nivethamahalakshmibalasamy
 */
public class DatePickerTest extends ActivityInstrumentationTestCase2<Datepicker_days> {

    Datepicker_days activity;

    public DatePickerTest() {
        super(Datepicker_days.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void testActivityExists() {
        Datepicker_days activity = getActivity();
        assertNotNull(activity);
    }

    @SmallTest
    public void DateSelectionTest() {
        Datepicker_days activity = getActivity();

        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(Chart.class.getName(), null, false);

        //Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(Datepicker_days.class.getName(), null, false);

        Button submitbutton = (Button) activity.findViewById(R.id.submit_date);
        TouchUtils.clickView(this, submitbutton);
        assertNotNull("Date summary is null", submitbutton);

//        Button monthbutton = (Button) activity.findViewById(R.id.summarymonth);
//        TouchUtils.clickView(this, monthbutton);
//        assertNotNull("Month summary is null", monthbutton);

        Activity nextActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        assertNotNull("Datepicker_days activity is null", nextActivity);
        assertEquals("Activity is not the Datepicker_days", Chart.class, nextActivity.getClass());


        final EditText fromDateEtxt = (EditText) activity.findViewById(R.id.etxt_fromdate);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                fromDateEtxt.requestFocus();
            }
        });

        final EditText toDateEtxt = (EditText) activity.findViewById(R.id.etxt_todate);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                toDateEtxt.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("11-03-2015");
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("11-25-2015");

        Activity nextActivity1 =  getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        assertNotNull("Chart activity is null", nextActivity1);
        assertEquals("Activity is not the Chart page", Chart.class, nextActivity1.getClass());

        nextActivity.finish();

    }

}






