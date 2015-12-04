package edu.rutgers.cs.rahul.helloworld;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.plus.Plus;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


/**
 * Created by Thara Philips on 12/3/2015.
 */
public class Chart_month extends Activity {



    JsonParser jParser = new JsonParser();
    //String mindate_datepicker;
    //String maxdate_datepicker;


    String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_DISTANCE_RUN = "distance";
    private static final String TAG_DURATION_RUN = "duration";
    private static final String TAG_CALORIES_RUN = "calories";

    JSONArray products = null;
    ArrayList<HashMap<String, String>> productsList;
    String[] hashmapvalues;
    int[] valuelist = new int[7];
    List<Integer> test = new ArrayList<Integer>();
    double Totaldistancemonth;
    double Totaltimemonth;
    double Totalcaloriesmonth;
    public TextView TextViewdistance;
    public TextView text1;
    public TextView text2;
    Chart_month this_obj;

    public void onCreate(Bundle savedInstanceState) {


        String tt = "in";
        Log.d("Reached open chart", tt);//

        super.onCreate(savedInstanceState);
        this_obj=this;
        //-------------------Intent and receiving values from Monthpicker_month----------------
        Intent i = getIntent();

        String miles_mn = i.getStringExtra("valuelist_distance_m");//Receivingggggggggggggggggggg
        System.out.println( miles_mn);

        String duration_mn = i.getStringExtra("valuelist_duration_m");//Receivingggggggggggggggggggg
        System.out.println("abc" + duration_mn);
        String calories_mn = i.getStringExtra("valuelist_calories_m");//Receivingggggggggggggggggggg
        System.out.println("abc" + calories_mn);
        int success_month=i.getIntExtra("success",0);
        System.out.println("Success value from monthpicker" + success_month);

        setContentView(R.layout.chart_month);
        TextViewdistance = (TextView) findViewById(R.id.totalmiles_month);
        text1 =(TextView) findViewById(R.id.totaltime_month);
        text2 =(TextView) findViewById(R.id.totalcalories_month);
        //----------------------------------------------------------------------------------------
        if (success_month== 0) {
            miles_mn = "No entries ";
            duration_mn="No entries";
            calories_mn="No entries";
            System.out.println("inside for loop"+miles_mn);
            TextViewdistance.setText(miles_mn);
            text1 .setText(duration_mn);
            text2.setText(calories_mn);


        } else {

            try{
                double ld = Double.parseDouble(miles_mn);
                double lt = Double.parseDouble(duration_mn);
                double lc = Double.parseDouble(calories_mn);

                miles_mn = String.format("%.02f",ld);
                duration_mn = String.format("%.02f",lt);
                calories_mn = String.format("%.02f",lc);
            }catch(Exception e)
            {

            }

            String totalmilesString = miles_mn + "miles";
            TextViewdistance.setText(totalmilesString);

            String totaltimeString=duration_mn+"mins";
            text1.setText(totaltimeString);

            String totalcaloriesString=calories_mn+"cals";
            text2.setText(totalcaloriesString);

        }
        //----------------------------------------------------------------------------------


        //String totalmilesString = miles_mn + "miles";
        //TextViewdistance.setText(totalmilesString);

//        text1 =(TextView) findViewById(R.id.totaltime_month);
//        String totaltimeString=duration_mn+"hours";
//        text1.setText(totaltimeString);
//
//        text2 =(TextView) findViewById(R.id.totalcalories_month);
//        String totalcaloriesString=calories_mn+"calories";
//        text2.setText(totalcaloriesString);


        Spinner spinner = (Spinner) findViewById(R.id.chartWeekSpinner_nav);


        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Monthly Statistics");
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
                        intent =new Intent(this_obj.getApplicationContext(), RunActivity.class);
                        RunActivity.start_run();
                        break;
                    case 2:
                        intent =new Intent(this_obj.getApplicationContext(), ShowChallenges.class);
                        break;
                    case 3:
                        intent =new Intent(this_obj.getApplicationContext(), StatisticsActivity.class);
                        break;
                    case 4:
                        intent =new Intent(this_obj.getApplicationContext(), PersonalInfoActivity.class);
                        break;
                    case 5:
                        if (LoginActivity.mGoogleApiClient.isConnected()) {
                            Plus.AccountApi.clearDefaultAccount(LoginActivity.mGoogleApiClient);
                            LoginActivity.mGoogleApiClient.disconnect();
                            System.err.println("LOG OUT ^^^^^^^^^^^^^^^^^^^^ SUCCESS");
                        }
                        intent = new Intent(this_obj.getApplicationContext(), LoginActivity.class);
                    case 6:
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



    }

}
