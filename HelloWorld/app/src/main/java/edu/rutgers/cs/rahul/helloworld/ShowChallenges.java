package edu.rutgers.cs.rahul.helloworld;

/**
 * Created by valia on 12/4/15.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowChallenges extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    TextView wonView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private String JSON_STRING;
    List<String> receivedList = new ArrayList<String>();
    List<String> sentList = new ArrayList<String>();

    List<Challenge_Bean> receivedAllList;
    List<Challenge_Bean> sentAllList;

    public String distance, duration;
    String receiverName;
    String datetime;
    String id;
    String receiver_id;
    String challengeWon;
    String challengeTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenges);

        receivedAllList = new ArrayList<Challenge_Bean>();
        sentAllList = new ArrayList<Challenge_Bean>();

        Spinner spinner = (Spinner) findViewById(R.id.ChallengesSpinner_nav);

        wonView = (TextView) findViewById(R.id.totalmiles);
        getTotal();

        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Challenge");
        spinnerArray.add("Run");
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
                        RunActivity.start_run();
                        intent =new Intent(ShowChallenges.this, RunActivity.class);
                        break;
                    case 2:
                        intent =new Intent(ShowChallenges.this, StatisticsActivity.class);
                        break;
                    case 3:
                        intent =new Intent(ShowChallenges.this, PersonalInfoActivity.class);
                        break;
                    case 4:
                        if (LoginActivity.mGoogleApiClient.isConnected()) {
                            Plus.AccountApi.clearDefaultAccount(LoginActivity.mGoogleApiClient);
                            LoginActivity.mGoogleApiClient.disconnect();
                            System.err.println("LOG OUT ^^^^^^^^^^^^^^^^^^^^ SUCESS");
                        }
                        intent = new Intent(ShowChallenges.this, LoginActivity.class);
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

        ((ImageView)findViewById(R.id.ChallengesToplogo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowChallenges.this, LandingPage.class));
            }
        });




        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //Nothing here ever fires
                if (groupPosition==0) {
                    System.err.println("child clicked " + childPosition);
                    System.out.println(receivedAllList.get(childPosition + 1).getStatus());
                    if(receivedAllList.get(childPosition + 1).getStatus().equalsIgnoreCase("pending")) {
                        confirmDeleteChallenge(childPosition + 1);
                    }else if (receivedAllList.get(childPosition + 1).getStatus().equals( Plus.PeopleApi.getCurrentPerson(LoginActivity.mGoogleApiClient).getId())){
                        Toast.makeText(ShowChallenges.this, "You have won this challenge!! Yiay!!", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(ShowChallenges.this, "Oops.. You have lost this challenge!!", Toast.LENGTH_LONG).show();
                    }
                }else if (groupPosition==1) {
                    System.err.println("child clicked " + childPosition);
                    System.out.println(sentAllList.get(childPosition + 1).getStatus());
                    if(sentAllList.get(childPosition + 1).getStatus().equalsIgnoreCase("pending")) {
                        Toast.makeText(ShowChallenges.this, "This challenge is still pending...", Toast.LENGTH_LONG).show();
                    }else if (sentAllList.get(childPosition + 1).getStatus().equals( Plus.PeopleApi.getCurrentPerson(LoginActivity.mGoogleApiClient).getId())){
                        Toast.makeText(ShowChallenges.this, "You have won this challenge!! Yiay!!", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(ShowChallenges.this, "Oops.. You have lost this challenge!!", Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });
    }


    private void getTotal(){
        class GetTotal extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ShowChallenges.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showTotal(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_TOTAL_CHALLENGES,  Plus.PeopleApi.getCurrentPerson(LoginActivity.mGoogleApiClient).getId());
                return s;
            }
        }
        GetTotal ge = new GetTotal();
        ge.execute();
    }

    private void showTotal(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            challengeTotal  = c.getString(Config.TAG_ID);
            challengeWon  = c.getString(Config.TAG_RECEIVER_ID);


            //Toast.makeText(Leaderboards.this,challengeWon , Toast.LENGTH_LONG).show();
            //Toast.makeText(Leaderboards.this,"Test" , Toast.LENGTH_LONG).show();
//            String desg = c.getString(Config.TAG_DESG);
//            String sal = c.getString(Config.TAG_SAL);
//
            wonView.setText(challengeWon+"/"+challengeTotal+"\n won");


//            editTextDesg.setText(desg);
//            editTextSalary.setText(sal);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Received Challenges");
        listDataHeader.add("Sent Challenges");

        //getJSON();
        GetReceived gj = new GetReceived();
        gj.execute();


        GetSent gs = new GetSent();
        gs.execute();
        // Adding child data

    }

    //get the json objects with all received challenges

    class GetReceived extends AsyncTask<Void, Void, String> {

        ProgressDialog loading;

        @Override
        protected String doInBackground(Void... params) {
            RequestHandler rh = new RequestHandler();
            String s = rh.sendGetRequestParam(Config.URL_GET_RECEIVED_CHALLENGES, Plus.PeopleApi.getCurrentPerson(LoginActivity.mGoogleApiClient).getId());
            System.out.println("CHALLENGE=" + s);
            JSON_STRING = s;

            JSONObject jsonObject = null;
            ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            try {
                jsonObject = new JSONObject(JSON_STRING);
                JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    id = jo.getString(Config.TAG_ID);
                    receiver_id = jo.getString(Config.TAG_RECEIVER_ID);
                    datetime = jo.getString(Config.TAG_DESG);
                    String status = jo.getString(Config.TAG_STATUS);
                    receiverName = jo.getString(Config.TAG_USER_NAME);
                    distance = jo.getString(Config.TAG_DISTANCE2);
                    duration = jo.getString(Config.TAG_DURATION2);
                    String Latestdistance="";
                    String Latesttime="";

                    try{
                        Latestdistance = String.format("%.02f",Double.parseDouble(distance));
                        Latesttime = String.format("%.02f",Double.parseDouble(duration));
                    }catch(Exception e)
                    {

                    }


                    receivedList.add(receiverName.split(" ")[0] + "   " + Latestdistance + "miles/" + Latesttime + "mins");
                    receivedAllList.add(new Challenge_Bean(id, receiver_id, datetime, distance, duration, status));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(ShowChallenges.this, "Fetching Data", "Wait...", false, false);
        }

        @Override
        protected void onPostExecute(String s) {
            loading.dismiss();
            listDataChild.put(listDataHeader.get(0), receivedList); // Header, Child data


        }


    }


    class GetSent extends AsyncTask<Void, Void, String> {

        ProgressDialog loading;

        @Override
        protected String doInBackground(Void... params) {
            RequestHandler rh = new RequestHandler();
            String s = rh.sendGetRequestParam(Config.URL_GET_SENT_CHALLENGES, Plus.PeopleApi.getCurrentPerson(LoginActivity.mGoogleApiClient).getId());
            System.out.println("CHALLENGE=" + s);
            JSON_STRING = s;

            JSONObject jsonObject = null;
            ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            try {
                jsonObject = new JSONObject(JSON_STRING);
                JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    id = jo.getString(Config.TAG_ID);
                    receiver_id = jo.getString(Config.TAG_RECEIVER_ID);
                    datetime = jo.getString(Config.TAG_DESG);
                    String status = jo.getString(Config.TAG_STATUS);
                    receiverName = jo.getString(Config.TAG_USER_NAME);
                    distance = jo.getString(Config.TAG_DISTANCE2);
                    duration = jo.getString(Config.TAG_DURATION2);


                    String Latestdistance="";
                    String Latesttime="";

                    try{
                        Latestdistance = String.format("%.02f",Double.parseDouble(distance));
                        Latesttime = String.format("%.02f",Double.parseDouble(duration));
                    }catch(Exception e)
                    {

                    }


                    sentList.add(receiverName.split(" ")[0] + "   " + Latestdistance + "miles/" + Latesttime + "mins");
                    sentAllList.add(new Challenge_Bean(id, receiver_id, datetime, distance, duration, status));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(ShowChallenges.this, "Fetching Data", "Wait...", false, false);
        }

        @Override
        protected void onPostExecute(String s) {
            loading.dismiss();
            listDataChild.put(listDataHeader.get(1), sentList); // Header, Child data


        }


    }



    private void confirmDeleteChallenge(final int index){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you wanna accept the challenge?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if(index>=receivedAllList.size())
                            return;
                        Intent intent =new Intent(ShowChallenges.this,RunActivity.class);
                        RunActivity.start_run();
                        intent.putExtra("distance", receivedAllList.get(index).getDistance());
                        intent.putExtra("duration",receivedAllList.get(index).getDuration());
                        intent.putExtra("sender_id",receivedAllList.get(index).getSender_id());
                        intent.putExtra("receiver_id",receivedAllList.get(index).getReceiver_id());
                        intent.putExtra("datetime",receivedAllList.get(index).getDatetime());
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteChallenge();

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteChallenge(){
        class DeleteChallenge extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ShowChallenges.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ShowChallenges.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestThreeParamDatetime(Config.URL_DELETE_EMP, id, receiver_id, datetime);
                return s;
            }
        }

        DeleteChallenge de = new DeleteChallenge();
        de.execute();
    }


}