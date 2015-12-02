package edu.rutgers.cs.rahul.helloworld;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
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

public class ViewChallenge extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextId;
    private EditText editTextName;
    private EditText editTextDesg;
    private EditText editTextSalary;


    private EditText editTextDistance;
    private EditText editTextDuration;


    public String distance,duration, distance2, duration2;




    // changed
    public EditText editTextDistance2;
    private EditText editTextDuration2;

    private Button buttonUpdate;
    private Button buttonDelete;
    private Button buttonResult;

    private String id;



    private String recid;
    public String sendid="Thara";
    String name;

    //constructor
    public ViewChallenge() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_challenge);

        Intent intent = getIntent();

        id = intent.getStringExtra(Config.EMP_ID);
        recid = intent.getStringExtra(Config.REC_ID);
        //sendid = intent.getStringExtra(Config.SENDER_ID);

        editTextId = (EditText) findViewById(R.id.editTextId);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDesg = (EditText) findViewById(R.id.editTextDesg);
        editTextSalary = (EditText) findViewById(R.id.editTextSalary);

        editTextDistance = (EditText) findViewById(R.id.editTextDistance);
        editTextDuration = (EditText) findViewById(R.id.editTextDuration);

        // changed
        editTextDistance2 = (EditText) findViewById(R.id.editTextDistance2);
        editTextDuration2 = (EditText) findViewById(R.id.editTextDuration2);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonResult = (Button) findViewById(R.id.buttonResult);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonResult.setOnClickListener(this);

        editTextId.setText(id);
        // editTextSalary.hide();

        getChallenge();
        getRunInfo();
        getSenderInfo();

    }

    private void getChallenge(){
        class GetChallenge extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewChallenge.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showChallenge(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_EMP, id);
                return s;
            }
        }
        GetChallenge ge = new GetChallenge();
        ge.execute();
    }

    private void showChallenge(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            name = c.getString(Config.TAG_NAME);
            String desg = c.getString(Config.TAG_DESG);
            String sal = c.getString(Config.TAG_SAL);

            editTextName.setText(name);
            editTextDesg.setText(desg);
            editTextSalary.setText(sal);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updateChallenge(){
        final String name = editTextName.getText().toString().trim();
        final String desg = editTextDesg.getText().toString().trim();
        final String salary = editTextSalary.getText().toString().trim();

        class UpdateChallenge extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewChallenge.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ViewChallenge.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_EMP_ID,id);
                hashMap.put(Config.KEY_EMP_NAME,name);
                hashMap.put(Config.KEY_EMP_DESG,desg);
                hashMap.put(Config.KEY_EMP_SAL,salary);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_EMP, hashMap);

                return s;
            }
        }

        UpdateChallenge ue = new UpdateChallenge();
        ue.execute();
    }

    private void deleteChallenge(){
        class DeleteChallenge extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewChallenge.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ViewChallenge.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_DELETE_EMP, id);
                return s;
            }
        }

        DeleteChallenge de = new DeleteChallenge();
        de.execute();
    }

    private void confirmDeleteChallenge(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this Challenge?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteChallenge();
                        startActivity(new Intent(ViewChallenge.this,ViewAllChallenges.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //receiver run info
    private void getRunInfo(){
        class GetRunInfo extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewChallenge.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showRunInfo(s);

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_RUN_INFO,recid);
                return s;
            }
        }
        GetRunInfo ge = new GetRunInfo();
        ge.execute();
    }

    private void showRunInfo(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_RUN);
            JSONObject c = result.getJSONObject(0);
            distance2 = c.getString(Config.TAG_DISTANCE);
            duration2 = c.getString(Config.TAG_DURATION);
            // String sal = c.getString(Config.TAG_SAL);

            editTextDistance2.setText(distance2);
            editTextDuration2.setText(duration2);
            // editTextSalary.setText(sal);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    //change

    //sender run info
    private void getSenderInfo(){
        class GetSenderInfo extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewChallenge.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showSenderInfo(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_RUN_INFO,sendid);
                return s;
            }
        }
        GetSenderInfo ge = new GetSenderInfo();
        ge.execute();
    }

    private void showSenderInfo(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_RUN2);
            JSONObject c = result.getJSONObject(0);
            distance = c.getString(Config.TAG_DISTANCE2);
            duration = c.getString(Config.TAG_DURATION2);
            // String sal = c.getString(Config.TAG_SAL);

            editTextDistance.setText(distance);
            editTextDuration.setText(duration);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class Comparison {

        int distance1 = Integer.parseInt(editTextDistance.getText().toString());
        int duration1 = Integer.parseInt(editTextDuration.getText().toString());
        int distance2 = Integer.parseInt(editTextDistance2.getText().toString());
        int duration2 = Integer.parseInt(editTextDuration2.getText().toString());
        String compare() {
            if (distance1 == distance2) {
                if(duration1==duration2)
                {
                    String s = "Its a tie";
                    return  s;
                }
                if (duration1 > duration2) {
                    String s = "Winner is user2";
                    return s;
                } else {
                    String s = "Winner is user1";
                    return s;
                }
            }else if(duration1==duration2)
            {
                if (distance1 > distance2) {
                    String s = "Winner is user1";
                    return s;
                } else {
                    String s = "Winner is user2";
                    return s;
                }
            }

            else if (distance1!=distance2 && duration1!=duration2)
            {
                int speed1=distance1/duration1;
                int speed2=distance2/duration2;
                if(speed1>speed2)
                {
                    String s = "Winner is user1";
                    return s;
                }
                else
                {
                    String s = "Winner is user2";
                    return s;
                }
            }
            return "fail";
        }
    }


    @Override
    public void onClick(View v) {
        if(v == buttonUpdate){
            updateChallenge();
        }

        if(v == buttonDelete){
            confirmDeleteChallenge();
        }

        if(v == buttonResult){

            Comparison comparison = new Comparison();
            String winner = comparison.compare();
            Toast.makeText(ViewChallenge.this, winner, Toast.LENGTH_LONG).show();

            //  distance = editTextDistance2.getText().toString().trim();
            //  Intent intent = new Intent(this, ChallengeResult.class);
            //  intent.putExtra(Config.distance4, myNum);
            //  startActivity(intent);

        }

    }
}
