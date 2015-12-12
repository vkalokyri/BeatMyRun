package edu.rutgers.cs.rahul.helloworld.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import edu.rutgers.cs.rahul.helloworld.LoginActivity;
import edu.rutgers.cs.rahul.helloworld.PersonalInfoActivity;
import edu.rutgers.cs.rahul.helloworld.R;

/**
 * Created by valia on 12/11/15.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {


    LoginActivity activity;

    public LoginActivityTest(){
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }


    @SmallTest
    public void testActivityExists() {

        com.google.android.gms.common.SignInButton greetButton =
                (com.google.android.gms.common.SignInButton) activity.findViewById(R.id.sign_in_button);

        TouchUtils.clickView(this, greetButton);


        GoogleApiClient gp = activity.mGoogleApiClient;
        assertNotNull(gp);
    }

}