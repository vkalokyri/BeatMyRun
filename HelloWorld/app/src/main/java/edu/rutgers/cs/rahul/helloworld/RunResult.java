package edu.rutgers.cs.rahul.helloworld;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.Plus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/*
* Created by Valia Kalokyri
* Tested by Rahul Shome
* Debugged by Rahul Shome
 */
public class RunResult extends Activity implements View.OnClickListener{

    private EditText emailField;
    private RunResult this_obj;
    private TextView challengeView;
    private String JSON_STRING;
    private AutoCompleteTextView auto;
    ArrayList<String> allUserNames=new ArrayList<String>();


    private String challenge_sender, challenge_recv, challenge_datetime, challenge_status;

    String receiverID;


    String testName,gotName, globalDatetime, globalUser;

    String currentUserName;
    String currentUserId = Plus.PeopleApi.getCurrentPerson(LoginActivity.mGoogleApiClient).getId();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_result);
        this_obj=this;

        ((TextView)findViewById(R.id.totalmiles)).setText(getIntent().getStringExtra("distance")+"\nmiles");
        ((TextView)findViewById(R.id.calories)).setText(getIntent().getStringExtra("calories")+"\ncal");
        ((TextView)findViewById(R.id.time)).setText(getIntent().getStringExtra("duration")+"\nmins");

        globalDatetime = getIntent().getStringExtra(Config.GLOBAL_DATETIME);



        Spinner spinner = (Spinner) findViewById(R.id.RunResultSpinner_nav);


        ArrayList<String> spinnerArray = new ArrayList<String>();
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
                    case 1:
                        intent =new Intent(RunResult.this, ShowChallenges.class);
                        break;
                    case 2:
                        intent =new Intent(RunResult.this, StatisticsActivity.class);
                        break;
                    case 3:
                        intent =new Intent(RunResult.this, PersonalInfoActivity.class);
                        break;
                    case 4:
                        if (LoginActivity.mGoogleApiClient.isConnected()) {
                            Plus.AccountApi.clearDefaultAccount(LoginActivity.mGoogleApiClient);
                            LoginActivity.mGoogleApiClient.disconnect();
                            System.err.println("LOG OUT ^^^^^^^^^^^^^^^^^^^^ SUCESS");
                        }
                        intent = new Intent(RunResult.this, LoginActivity.class);
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

        ((ImageView)findViewById(R.id.RunResultToplogo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RunResult.this, LandingPage.class));
            }
        });














        auto = (AutoCompleteTextView) findViewById(R.id.myautocomplete);
        autocomplete();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, allUserNames);
        challengeView = (TextView) findViewById(R.id.challenge);
        challengeView.setOnClickListener(this);

        auto.setThreshold(1);
        auto.setAdapter(adapter);

        findViewById(R.id.RunResultStatus).setVisibility(View.GONE);
        if(getIntent().hasExtra("result"))
        {
            Log.e("RunResult", "from challenge");
            if(getIntent().getStringExtra("result").equals("win"))
            {
                findViewById(R.id.RunResultStatus).setVisibility(View.VISIBLE);
                ((TextView)findViewById(R.id.ChallengeView)).setText("Challenge Won");
                ((ImageView)findViewById(R.id.WinChallenge)).setImageResource(R.drawable.cup);
                Log.e("RunResult", "WON");
            }
            {
                findViewById(R.id.RunResultStatus).setVisibility(View.VISIBLE);
                ((TextView)findViewById(R.id.ChallengeView)).setText("Challenge Lost");
                ((ImageView)findViewById(R.id.WinChallenge)).setImageResource(R.drawable.sad);
                Log.e("RunResult","lost");
            }
            challenge_sender = getIntent().getStringExtra("sender_id");
            challenge_recv = getIntent().getStringExtra("receiver_id");
            challenge_datetime = getIntent().getStringExtra("ch_datetime");
            challenge_status = getIntent().getStringExtra("status");
            updateChallenge();
        }




    }


    private void updateChallenge(final String... args){


        class UpdateChallenge extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_EMP_ID,challenge_sender);
                hashMap.put(Config.KEY_EMP_NAME,challenge_recv);
                hashMap.put(Config.KEY_EMP_DESG,challenge_datetime);
                hashMap.put(Config.KEY_EMP_STATUS,challenge_status);



                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_EMP, hashMap);

                return s;
            }
        }

        UpdateChallenge ue = new UpdateChallenge();
        ue.execute();
    }



    private void getUser(String json) {
        JSONObject jsonObject = null;
        try {
            System.out.println(json);
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                allUserNames.add(jo.getString(Config.TAG_USER_NAME));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkUserExists() throws UnsupportedEncodingException {

        testName = auto.getText().toString().trim();
        testName = URLEncoder.encode(testName, "UTF-8");

        class CheckUserExits extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RunResult.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                System.out.println("USER SSSSSS" +s);
                showUser(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();

                String s = rh.sendGetRequestParam(Config.URL_GET_USER, testName);
                return s;
            }
        }
        CheckUserExits ge = new CheckUserExits();
        ge.execute();
    }

    private void showUser(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY3);
            JSONObject c = result.getJSONObject(0);
            gotName = c.getString(Config.TAG_USER_NAME);
            receiverID = c.getString(Config.TAG_USER_ID);
//            String sal = c.getString(Config.TAG_SAL);



            if (receiverID.isEmpty()) {

                Toast.makeText(RunResult.this, "User does not exist", Toast.LENGTH_LONG).show();
            }
            else{
                addChallenge();
                Toast.makeText(RunResult.this, "Challenge sent!", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void addChallenge(){

        testName = auto.getText().toString().trim();
        class AddChallenge extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RunResult.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(RunResult.this,s,Toast.LENGTH_LONG).show();
                auto.setText("");

            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_EMP_NAME,testName);
                params.put(Config.KEY_EMP_DESG,globalDatetime);
                params.put(Config.KEY_EMP_SAL,currentUserId);
                Log.e("Addemp", testName + "," + globalDatetime + "," + currentUserId);
                params.put(Config.KEY_EMP_STATUS,"Pending");//added

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }
        }

        AddChallenge ae = new AddChallenge();
        ae.execute();
    }
/*
    private void addChallenge(){


        final String datetime = "2015-12-15 23:08:33";

        class AddChallenge extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RunResult.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(RunResult.this,s,Toast.LENGTH_LONG).show();
                auto.setText("");
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_EMP_NAME,receiverID);
                params.put(Config.KEY_EMP_DESG,datetime);
                params.put(Config.KEY_EMP_SAL,currentUserId);
                params.put(Config.KEY_EMP_STATUS,"Pending");//added

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }
        }

        AddChallenge ae = new AddChallenge();
        ae.execute();
    }
*/

    private void autocomplete() {


        class Autocomplete extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RunResult.this, "Fetching...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                getUser(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();

                String s = rh.getAllUsers(Config.URL_ALL_USERS);
                return s;
            }
        }
        Autocomplete ge = new Autocomplete();
        ge.execute();
    }


    @Override
    public void onClick(View v) {
        if(v == challengeView){
            try {
                checkUserExists();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // addChallenge();
        }


    }

}
