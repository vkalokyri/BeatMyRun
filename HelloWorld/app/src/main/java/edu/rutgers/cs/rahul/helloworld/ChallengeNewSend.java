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

public class ChallengeNewSend extends AppCompatActivity implements View.OnClickListener{

    //Defining views
    private EditText editTextName;
    private EditText editTextDesg;
    private EditText editTextSal;

    private Button buttonAdd;
    private Button buttonView;

    // String sender_id = "careena";
    String testName,gotName;

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
        buttonView = (Button) findViewById(R.id.buttonView);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
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
            gotName = c.getString(Config.TAG_NAME3);
//            String desg = c.getString(Config.TAG_DESG);
//            String sal = c.getString(Config.TAG_SAL);

//            editTextName.setText(name);
//            editTextDesg.setText(desg);
//          //  editTextSalary.setText(sal);


            if (gotName.isEmpty()) {

                Toast.makeText(ChallengeNewSend.this, "user does not exist", Toast.LENGTH_LONG).show();
            }
            else{
                addChallenge();
                Toast.makeText(ChallengeNewSend.this, "User exists", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //Adding an challenge
    private void addChallenge(){

        final String name = editTextName.getText().toString().trim();
        final String desg = editTextDesg.getText().toString().trim();
        final String sal = editTextSal.getText().toString().trim();

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
                editTextDesg.setText("");
                editTextSal.setText("");
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_EMP_NAME,name);
                params.put(Config.KEY_EMP_DESG,desg);
                params.put(Config.KEY_EMP_SAL,sal);

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
            startActivity(new Intent(this,ViewAllChallenges.class));
        }
    }
}
