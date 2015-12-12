package edu.rutgers.cs.rahul.helloworld.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

import edu.rutgers.cs.rahul.helloworld.PersonalInfoActivity;
import edu.rutgers.cs.rahul.helloworld.R;

/**
 * Created by valia on 12/11/15.
 */
public class PersonalInfoActivityTest extends ActivityInstrumentationTestCase2<PersonalInfoActivity> {


    PersonalInfoActivity activity;

    public PersonalInfoActivityTest(){
        super(PersonalInfoActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
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
