package edu.rutgers.cs.rahul.helloworld.tests;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.TextView;

import edu.rutgers.cs.rahul.helloworld.Chart_month;
import edu.rutgers.cs.rahul.helloworld.Monthpicker_month;
import edu.rutgers.cs.rahul.helloworld.R;

/**
 * Created by nivethamahalakshmibalasamy on 12/12/15.
 */
public class MonthPickerTest extends ActivityInstrumentationTestCase2<Monthpicker_month> {

    Monthpicker_month activity;

    public MonthPickerTest() {
        super(Monthpicker_month.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void testActivityExists() {
        Monthpicker_month activity = getActivity();
        assertNotNull(activity);
    }

    @SmallTest
    public void TestCurrentRunStatView() {

        Monthpicker_month activity = getActivity();
        final TextView totalmilesmonth =
                (TextView) activity.findViewById(R.id.totalmiles_month);
        String actualText = totalmilesmonth.getText().toString();
        assertEquals("totalmiles", actualText);

        final TextView totaltimemonth =
                (TextView) activity.findViewById(R.id.totaltime_month);
        String actualText1 = totaltimemonth.getText().toString();
        assertEquals("totaltime", actualText1);

        final TextView totalcaloriesmonth =
                (TextView) activity.findViewById(R.id.totalcalories_month);
        String actualText2 = totalcaloriesmonth.getText().toString();
        assertEquals("totalcalories", actualText2);

        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(Chart_month.class.getName(), null, false);

        //Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(Datepicker_days.class.getName(), null, false);

        Button submitbutton = (Button) activity.findViewById(R.id.submit_month);
        TouchUtils.clickView(this, submitbutton);
        assertNotNull("Date summary is null", submitbutton);

//        Button monthbutton = (Button) activity.findViewById(R.id.summarymonth);
//        TouchUtils.clickView(this, monthbutton);
//        assertNotNull("Month summary is null", monthbutton);

        Activity nextActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        assertNotNull("Datepicker_days activity is null", nextActivity);
        assertEquals("Activity is not the Monthpicker_month", Chart_month.class, nextActivity.getClass());




        getInstrumentation().waitForIdleSync();

        getInstrumentation().sendStringSync("32 miles");
        getInstrumentation().sendStringSync("20 hours");
        getInstrumentation().sendStringSync("20156 calories");
    }

}
