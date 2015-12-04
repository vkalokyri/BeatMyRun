package edu.rutgers.cs.rahul.helloworld;

/**
 * Created by valia on 12/4/15.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowChallenges extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private String JSON_STRING;
    List<String> receivedList = new ArrayList<String>();
    public String distance,duration;
    String receiverName;
    String datetime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenges);

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
                System.err.println("child clicked "+childPosition);
                Toast.makeText(getApplicationContext(), "child clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
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

        getJSON();

        // Adding child data

        receivedList.add("The Shawshank Redemption");
        receivedList.add("The Godfather");
        receivedList.add("The Godfather: Part II");
        receivedList.add("Pulp Fiction");
        receivedList.add("The Good, the Bad and the Ugly");
        receivedList.add("The Dark Knight");
        receivedList.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");


       // listDataChild.put(listDataHeader.get(1), nowShowing);
    }


    private void showChallenges(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Config.TAG_ID);
                String name = jo.getString(Config.TAG_NAME);
                datetime = jo.getString(Config.TAG_DESG);
                receiverName = jo.getString(Config.TAG_USER_NAME);


                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_ID,id);
                employees.put(Config.TAG_NAME,name);
                employees.put(Config.TAG_DESG,datetime);
                employees.put(Config.TAG_USER_NAME, receiverName);//changed
                list.add(employees);

                getSenderInfo();



            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    //get the json objects with all received challenges
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ShowChallenges.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showChallenges();
                listDataChild.put(listDataHeader.get(0), receivedList); // Header, Child data

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_ALL_SENT, Plus.PeopleApi.getCurrentPerson(LoginActivity.mGoogleApiClient).getId());
                // String s = rh.sendGetRequest(Config.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }


    private void getSenderInfo() {


//        java.util.Date dt = new java.util.Date();
//
//        java.text.SimpleDateFormat sdf =
//                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//         final  String currentTime = sdf.format(dt);


        class GetSenderInfo extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ShowChallenges.this, "Fetching...", "Wait...", false, false);
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
                // String s = null;
                String s = rh.sendGetRequestTwoParamDatetime(Config.URL_GET_RUN_INFO, Plus.PeopleApi.getCurrentPerson(LoginActivity.mGoogleApiClient).getId(), datetime);
                return s;
            }
        }
        GetSenderInfo gsi = new GetSenderInfo();
        gsi.execute();
    }

    private void showSenderInfo(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_RUN2);
            JSONObject c = result.getJSONObject(0);
            distance = c.getString(Config.TAG_DISTANCE2);
            duration = c.getString(Config.TAG_DURATION2);

            receivedList.add(receiverName+"\t"+distance+"/"+duration);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}