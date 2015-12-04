package edu.rutgers.cs.rahul.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by valia on 10/30/15.
 */
public class PersonalInfoActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<People.LoadPeopleResult> {

    private EditText emailField;
    private EditText usernameField;
    private EditText heightField;
    private EditText weightField;
    private EditText ageField;
    private Button submitBtnField;
    private ImageButton logoutId ;
    private String name=null;
    private String email=null;

    GoogleApiClient mGoogleApiClient;
    Person currentPerson =null;
    boolean mSignInClicked;
    private static HttpConnector connector=new HttpConnector();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);

        mGoogleApiClient=LoginActivity.mGoogleApiClient;
        emailField = (EditText) findViewById(R.id.emailField);
        usernameField = (EditText) findViewById(R.id.usernameField);
        heightField = (EditText) findViewById(R.id.heightField);
        weightField = (EditText) findViewById(R.id.weightField);
        ageField = (EditText) findViewById(R.id.ageField);
        submitBtnField = (Button) findViewById(R.id.submit);
        logoutId = (ImageButton) findViewById(R.id.logoutId);

        Intent i = getIntent();
        // Receiving the Data
        name = i.getStringExtra("username");
        email = i.getStringExtra("email");
        Log.e("Second Screen", name + "." + email);

        // Displaying Received data
        usernameField.setText(name);
        emailField.setText(email);


        currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        if (name==null){
            new getUser().execute();
        }


        // Binding Click event to Button
        submitBtnField.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                String id = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient).getId();
                String height = heightField.getText().toString();
                String weight = weightField.getText().toString();
                String age = ageField.getText().toString();
                new updateUser().execute(id, name, email, height, weight, age);

                Intent nextScreen = new Intent(getApplicationContext(), RunActivity.class);
                RunActivity.start_run();
                //Sending data to another Activity
                startActivity(nextScreen);

            }
        });




        logoutId.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (mGoogleApiClient.isConnected()) {
                    Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                    mGoogleApiClient.disconnect();
                    System.err.println("LOG OUT ^^^^^^^^^^^^^^^^^^^^ SUCCESS");
                }
                Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(nextScreen);



            }
        });

    }


    private class updateUser extends AsyncTask<String, Void, HttpResponse> {

        @Override
        protected void onPostExecute(HttpResponse response) {
            System.out.println("Response: "+response.getStatusLine());
        }


        @Override
        protected HttpResponse doInBackground(String... args) {
            String name = null;
            String email=null;
            String id=null;
            String height =args[3];
            String weight=args[4];
            String age=args[5];

            try {
                id=URLEncoder.encode(args[0], "UTF-8");
                name = URLEncoder.encode(args[1], "UTF-8");
                email = URLEncoder.encode(args[2],"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String link = "http://10.0.2.2/updateUser.php?id=%27"+id+"%27&name=%27"+name+"%27&email=%27"+email+"%27&height="+height+"&weight="+weight+"&age="+age+"";
            return connector.request(link);
        }

    }


    private class getUser extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String response) {
            if (response!=null) {

                String [] personalInfo = response.split(";");

                emailField.setText(personalInfo[2]);
                email=personalInfo[2];
                usernameField.setText(personalInfo[1]);
                name=personalInfo[1];
                if(!personalInfo[3].equals("0")){
                    heightField.setText(personalInfo[3]);
                }
                if(!personalInfo[4].equals("0")) {
                    weightField.setText(personalInfo[4]);
                }
                if(!personalInfo[5].equals("0")){
                    ageField.setText(personalInfo[5]);
                }
            }
        }


        @Override
        protected String doInBackground(String... args) {

            String link = "http://10.0.2.2/getUser.php?id=%27"+currentPerson.getId()+"%27";
            HttpEntity entity = connector.request(link).getEntity();

            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(entity.getContent()), 65728);
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            catch (IOException e) { e.printStackTrace(); }
            catch (Exception e) { e.printStackTrace(); }

            String response=sb.toString();
            if (response.equalsIgnoreCase("0_Results")){
                System.err.println("The user doesn't exist in the database");
                return null;
            }else{
                System.err.println("The user exists in the db!");
                return response;
            }
        }
    }


    @Override
    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub
        mSignInClicked = false;

        // updateUI(true);
        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub
        mGoogleApiClient.connect();
        // updateUI(false);
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onResult(People.LoadPeopleResult loadPeopleResult) {

    }
}
