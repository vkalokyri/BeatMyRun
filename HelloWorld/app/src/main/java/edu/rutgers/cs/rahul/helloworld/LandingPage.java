package edu.rutgers.cs.rahul.helloworld;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.apache.http.HttpEntity;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class LandingPage extends AppCompatActivity {


    HttpConnector connector = new HttpConnector();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);



        Spinner spinner = (Spinner) findViewById(R.id.LandingSpinner_nav);


        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Beat.My.Run");
        spinnerArray.add("Run");
        spinnerArray.add("Challenge");
        spinnerArray.add("Statistics");
        spinnerArray.add("Personal Details");
        spinnerArray.add("Logout");


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_dropdown_item, spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.dropdown_list);
        spinner.setAdapter(spinnerArrayAdapter);


//        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position)
                {
                    case 0:
                        break;
                    case 2:
                        intent =new Intent(getApplicationContext(), ShowChallenges.class);
                        break;
                    case 3:
                        intent =new Intent(getApplicationContext(), StatisticsActivity.class);
                        break;
                    case 4:
                        intent =new Intent(getApplicationContext(), PersonalInfoActivity.class);
                        break;
                    case 5:
                        if (LoginActivity.mGoogleApiClient.isConnected()) {
                            Plus.AccountApi.clearDefaultAccount(LoginActivity.mGoogleApiClient);
                            LoginActivity.mGoogleApiClient.disconnect();
                            System.err.println("LOG OUT ^^^^^^^^^^^^^^^^^^^^ SUCESS");
                        }
                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                    case 1:
                        intent =new Intent(getApplicationContext(), RunActivity.class);
                        RunActivity.start_run();
                        break;
                    default:
                        break;
                }
                if(intent != null)
                    startActivity(intent);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        if (Plus.PeopleApi.getCurrentPerson(LoginActivity.mGoogleApiClient) != null) {
            Person person = Plus.PeopleApi.getCurrentPerson(LoginActivity.mGoogleApiClient);
            if (person.hasImage()) {

                Person.Image image = person.getImage();


                new AsyncTask<String, Void, Bitmap>() {

                    @Override
                    protected Bitmap doInBackground(String... params) {

                        try {
                            URL url = new URL(params[0]);
                            InputStream in = url.openStream();
                            return BitmapFactory.decodeStream(in);
                        } catch (Exception e) {
                        /* TODO log error */
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        ((ImageView)findViewById(R.id.personId_landing)).setImageBitmap(bitmap);
                    }
                }.execute(image.getUrl());
            }
            Log.e("Landing",person.getDisplayName());
            ((TextView)findViewById(R.id.name_landing)).setText(person.getDisplayName());


        }


        new AsyncTask<String, Void, String>(){

            protected void onPostExecute(String response) {
                if (response != null) {
                    String[] stats = response.split(";");

                    double distance = 0;
                    double time = 0;

                    try{
                        distance = Double.parseDouble(stats[0]);
                        time = Double.parseDouble(stats[1]);

                    }catch(Exception e)
                    {

                    }
                    stats[0] = String.format("%.02f",distance);
                    stats[1] = String.format("%.02f",time);

                    ((TextView)findViewById(R.id.landingmiles)).setText(stats[0] + "\nmiles");
                    ((TextView)findViewById(R.id.landingtime)).setText(stats[1]+"\nmins");
                    ((TextView)findViewById(R.id.landingcalories)).setText(stats[2]+"\ncals");

                }
            }

            @Override
            protected String doInBackground(String... params) {
                String link = "http://beatmyrun.net16.net/landing_page_stats.php?month=12&year=2015";
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
        }.execute();

        ((TextView)findViewById(R.id.run_landing)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RunActivity.class);
                RunActivity.start_run();
                startActivity(intent);
            }
        });

        ((TextView)findViewById(R.id.challenge_landing)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowChallenges.class);
                startActivity(intent);
            }
        });

        ((TextView)findViewById(R.id.stat_landing)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StatisticsActivity.class);
                startActivity(intent);
            }
        });

    }

}
