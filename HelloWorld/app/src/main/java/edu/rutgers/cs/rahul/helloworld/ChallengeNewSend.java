package edu.rutgers.cs.rahul.helloworld;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import  java.util.Date;
import java.text.DateFormat;

public class ChallengeNewSend extends AppCompatActivity implements View.OnClickListener{

    //Defining views
    private EditText editTextName;
    private EditText editTextDesg;
    private EditText editTextSal;

    private Button buttonAdd;
    private Button buttonView;
    private Button buttonViewSent;

    // String sender_id = "careena";
    String testName,gotName, globalUser;

    String currentUserName, currentUserId="carid";

    String receiverID;
    //must get datetime from Rahul


// textView is the TextView view that should display it



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_new_send);

        //intents
        Intent intent = getIntent();
//
//        sender_id = intent.getStringExtra(Config.SENDER_ID);

        //Initializing views
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDesg = (EditText) findViewById(R.id.editTextDesg);
        editTextSal = (EditText) findViewById(R.id.editTextSalary);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonViewSent = (Button) findViewById(R.id.buttonViewSent);
        buttonView = (Button) findViewById(R.id.buttonView);

       // editTextDesg.setText(datetime);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
        buttonViewSent.setOnClickListener(this);
    }


    private void checkUserExists(){

        testName = editTextName.getText().toString().trim();

        class CheckUserExits extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ChallengeNewSend.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
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



            if (gotName.isEmpty()) {

                Toast.makeText(ChallengeNewSend.this, "User does not exist", Toast.LENGTH_LONG).show();
            }
            else{
               addChallenge();
                Toast.makeText(ChallengeNewSend.this, receiverID, Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //Adding an challenge
    private void addChallenge(){


       final String datetime = "2015-12-15 23:08:33";

        class AddChallenge extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ChallengeNewSend.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ChallengeNewSend.this,s,Toast.LENGTH_LONG).show();
                editTextName.setText("");
                editTextDesg.setText(datetime);
                editTextSal.setText("");
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

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            checkUserExists();
            // addChallenge();
        }

        if(v == buttonView){
            startActivity(new Intent(this, ViewAllChallenges.class));
        }

        if(v == buttonViewSent){
            startActivity(new Intent(this,ViewSentChallenges.class));
        }
    }
}
