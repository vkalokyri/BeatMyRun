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

import java.io.UnsupportedEncodingException;
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


/*
* Created by Careena Btaganza
* Tested by Nirali Shah
* Debugged by Careena Braganza
 */

public class ViewSingleSentChallenge extends AppCompatActivity {

    private EditText editTextId;



    private EditText editTextDistance;
    private EditText editTextDuration;


//    final String currentTime = "2015-12-15 23:08:33";
  private String id, globalDatetime, recid, receiverName;
    public String distance,duration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_sent_challenge);

        Intent intent = getIntent();

       id = intent.getStringExtra(Config.EMP_ID);
        recid = intent.getStringExtra(Config.REC_ID);
        globalDatetime = intent.getStringExtra(Config.DATETIME_ID);
        receiverName = intent.getStringExtra(Config.RECEIVER_NAME);

        editTextId = (EditText) findViewById(R.id.editTextReceiverId);


        editTextDistance = (EditText) findViewById(R.id.editTextDistance);
        editTextDuration = (EditText) findViewById(R.id.editTextDuration);



        editTextId.setText(receiverName);

     //   Toast.makeText(ViewSingleSentChallenge.this, globalDatetime, Toast.LENGTH_LONG).show();
        getChallenge();
        //  getRunInfo();
        // getSenderInfo();

    }
//
    private void getChallenge(){
        class GetChallenge extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewSingleSentChallenge.this,"Fetching...","Wait...",false,false);
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
                String s = rh.sendGetRequestThreeParamDatetime(Config.URL_GET_EMP, id ,recid ,globalDatetime );
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
            String name = c.getString(Config.TAG_RECEIVER_ID);
            String desg = c.getString(Config.TAG_DESG);
          //  globalSender = c.getString(Config.TAG_SAL);

           // editTextId.setText(globalSender);
            editTextId.setText(recid);
//            editTextDesg.setText(desg);
            //   editTextSalary.setText(sal);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        getSenderInfo();
    }


    //change

   // sender run info
    private void getSenderInfo(){



        class GetSenderInfo extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewSingleSentChallenge.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showSenderInfo(s);
            }
            // changed(try catch)
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestTwoParamDatetime(Config.URL_GET_RUN_INFO, id ,globalDatetime );
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


}
