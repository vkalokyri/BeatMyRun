package edu.rutgers.cs.rahul.helloworld;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.Plus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by valia on 12/4/15.
 */
public class RunResult extends Activity implements View.OnClickListener{

    private EditText emailField;
    private RunResult this_obj;
    private TextView challengeView;
    private String JSON_STRING;
    private AutoCompleteTextView auto;
    ArrayList<String> allUserNames=new ArrayList<String>();


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

        auto = (AutoCompleteTextView) findViewById(R.id.myautocomplete);
        autocomplete();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, allUserNames);
        challengeView = (TextView) findViewById(R.id.challenge);
        challengeView.setOnClickListener(this);

        auto.setThreshold(1);
        auto.setAdapter(adapter);

        findViewById(R.id.RunResultStatus).setVisibility(View.GONE);
        if(getIntent().hasExtra("status"))
        {
            if(getIntent().getStringExtra("status").equals("win"))
            {
                findViewById(R.id.RunResultStatus).setVisibility(View.VISIBLE);
                
            }
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
                hashMap.put(Config.KEY_EMP_ID,args[0]);
                hashMap.put(Config.KEY_EMP_NAME,args[1]);
                hashMap.put(Config.KEY_EMP_DESG,args[2]);
                hashMap.put(Config.KEY_EMP_STATUS,args[3]);



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
