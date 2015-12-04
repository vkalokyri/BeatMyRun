package edu.rutgers.cs.rahul.helloworld;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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


    public void onCreate(Bundle savedInstanceState) {


        String tt = "in";
        Log.d("Reached open chart", tt);//

        super.onCreate(savedInstanceState);
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
            String totalmilesString = miles_mn + "miles";
            TextViewdistance.setText(totalmilesString);

            String totaltimeString=duration_mn+"hours";
            text1.setText(totaltimeString);

            String totalcaloriesString=calories_mn+"calories";
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

    }

}
