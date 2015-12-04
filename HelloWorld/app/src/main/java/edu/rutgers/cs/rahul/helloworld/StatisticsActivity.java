package edu.rutgers.cs.rahul.helloworld;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.plus.Plus;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Thara Philips on 12/3/2015.
 */
public class StatisticsActivity extends Activity{

    JsonParser jParser = new JsonParser();


    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ID_RUN = "user_id";
    private static final String TAG_STEPS_RUN = "steps";
    private static final String TAG_DISTANCE_RUN = "distance";
    private static final String TAG_DURATION_RUN = "duration";
    private static final String TAG_CALORIES_RUN = "calories";

    String Latestdistance;
    String Latestcalories;
    String Latesttime;

    String TAG_SUCCESS = "success";
    String TAG_STUFF = "stuff";
    private static HttpConnector connector = new HttpConnector();
    JSONArray products = null;
    ArrayList<HashMap<String, String>> productsList;
    String[] hashmapvalues;
    int[] valuelist = new int[7];
    List<Integer> test = new ArrayList<Integer>();
    double[] valuelist_distance = new double[7];
    double[] valuelist_duration = new double[7];
    double[] valuelist_calories = new double[7];
    double[] valuelist_distance_month = new double[12];
    double[] valuelist_duration_month = new double[12];
    double[] valuelist_calories_month = new double[12];

    public double getTotaldistance_double() {
        return Totaldistance_double;
    }

    public double getTotalduration_double() {
        return Totalduration_double;
    }

    public double getTotalcalories_double() {
        return Totalcalories_double;
    }

    double Totaldistance_double;
    double Totalduration_double;
    double Totalcalories_double;
    String[] valuelist_string = new String[7];
    String exampletara = "Thara";
    StatisticsActivity this_obj;

    //private static Chart chartvalues = new Chart();
    //private static openChartActivity openchartobj = new openChartActivity();

    public TextView TextViewdistance;
    public TextView text1;
    public TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_main_page);
        this_obj=this;
        TextViewdistance =(TextView) findViewById(R.id.totalmiles);
        //  new insertUser().execute();
        // new testing().execute();
        new Totalvalue().execute();
        //  Latestvalue();

        //String totalmilesString1 = Double.toString(Latestdistance);
//
        String totalmilesString = Latestdistance + "miles";
        TextViewdistance.setText(totalmilesString);

        // new MonthValue().execute();

        //openchartobj.openChart(valuelist);
        // String valueliststring=Arrays.toString(valuelist);
        // String type = valuelist.getClass().getSimpleName();
        //  System.out.println(type);
        // Intent new_screen = new Intent(this, Chart.class);
        //Intent new_screen=new Intent(this,Chart.class);//sendingggggggggggggggggggggggg
        //new_screen.putExtra("list111", valuelist_distance);
        Bundle b = new Bundle();
        b.putDoubleArray("key", valuelist_distance);
        //new_screen.putExtras(b);
        Button weekbutton = (Button) findViewById(R.id.summaryday);
        Button monthbutton = (Button) findViewById(R.id.summarymonth);
        //startActivity(new_screen);
        View.OnClickListener clickListener = new View.OnClickListener() {

            Intent new_screen = new Intent(StatisticsActivity.this, Datepicker_days.class);

            @Override
            public void onClick(View v) {
//               setContentView(R.layout.stat_activity_main);

                // Draw the Income vs Expense Chart
                if (v.getId() == R.id.summaryday) {
                    Intent new_screen = new Intent(StatisticsActivity.this, Datepicker_days.class);
                    new_screen.putExtra("period", "first");
                    System.out.println("On the first screen");
                    startActivity(new_screen);
//                   openChart();
//                   openChart1();
//                   openChart2();
                }
                if (v.getId() == R.id.summarymonth) {
                    Intent new_screen = new Intent(StatisticsActivity.this,Monthpicker_month.class);
                    new_screen.putExtra("period", "second");
                    System.out.println("Should go to second screen");
                    startActivity(new_screen);
//                 openChartmnth();
//                 openChartmnth1();
//                 openChartmnth2();
                }
                //if(v.getId() == R.id.button3) {

                //}
            }


        };

        weekbutton.setOnClickListener(clickListener);
        monthbutton.setOnClickListener(clickListener);


        Spinner spinner = (Spinner) findViewById(R.id.StatsSpinner_nav);


        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Statistics");
        spinnerArray.add("Run");
        spinnerArray.add("Challenge");
        spinnerArray.add("Personal Details");
        spinnerArray.add("Logout");
        spinnerArray.add("Contact Us");


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
                        break;
                    case 2:
                        intent =new Intent(this_obj.getApplicationContext(), ViewAllChallenges.class);
                        break;
                    case 3:
                        intent =new Intent(this_obj.getApplicationContext(), PersonalInfoActivity.class);
                        break;
                    case 4:
                        if (LoginActivity.mGoogleApiClient.isConnected()) {
                            Plus.AccountApi.clearDefaultAccount(LoginActivity.mGoogleApiClient);
                            LoginActivity.mGoogleApiClient.disconnect();
                            System.err.println("LOG OUT ^^^^^^^^^^^^^^^^^^^^ SUCCESS");
                        }
                        intent = new Intent(this_obj.getApplicationContext(), LoginActivity.class);
                    case 5:
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




    private class insertUser extends AsyncTask<String, Void, HttpResponse> {

        @Override
        protected void onPostExecute(HttpResponse response) {

        }


        @Override
        protected HttpResponse doInBackground(String... args) {
            String name = "Tessa";
            String email = "tp425";
            String id = "5";
//            try {
//                name = URLEncoder.encode(args[0], "UTF-8");
//                email = URLEncoder.encode(args[1],"UTF-8");
//                id = URLEncoder.encode(args[2],"UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            System.out.println("NAMEEEEEEEEEEEEE="+name);
//            System.out.println("EMAILLLLL="+email);
//            System.out.println("ID"+id);
            String msg1 = "IN";
            Log.d("call url", msg1);
            String link = "http://10.0.2.2:8888/write.php?name=%27" + name + "%27&email=%27" + email + "%27&id=%27" + id + "%27";

            return connector.request(link);


        }
    }

    //-----------------Function for calling search.php and displaying values between min and max date---------
    public class testing extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... args) {
            productsList = new ArrayList<HashMap<String, String>>();
//
            //HashMap<String, String> map = new HashMap <String, String>();

            /* Building Parameters */
            List<NameValuePair> params = new ArrayList<>();

            /* getting JSON string from URL */
            String msg1 = "IN";
            Log.d("inside httprequest", msg1);
            String mindate = "2015-11-27";
            String maxdate = "2015-12-31";
            params.add(new BasicNameValuePair("mindate", mindate));
            params.add(new BasicNameValuePair("maxdate", maxdate));

            //String link = "http://10.0.2.2:8888/fetchdata.php";
            // String link = "http://10.0.2.2:8888/fetchdata_runinfo.php";

            String link = "http://10.0.2.2:8888/Search.php"; //+ mindate + "maxdate="+ maxdate;
            JSONObject json = jParser.makeHttpRequest(link, "GET", params);
            Log.d("All Products: ", json.toString());//
            // --------------------------------------------------------------------
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                String mm = "in";
                Log.d("Arraylist", mm);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        //  String id = c.getString(TAG_PID);
                        //  String name = c.getString(TAG_NAME);
                        //Runinfo-------------------------
                        //String id = c.getString(TAG_ID_RUN);
                        //String steps = c.getString(TAG_STEPS_RUN);
                        String distance = c.getString(TAG_DISTANCE_RUN);
                        String duration = c.getString(TAG_DURATION_RUN);
                        String calories = c.getString(TAG_CALORIES_RUN);

                        double distance_double = Double.parseDouble(distance);//creating runinfo_distance double array
                        double duration_double = Double.parseDouble(duration);//creating runinfo_duration double array
                        double calories_double = Double.parseDouble(calories);//creating runinfo_calories double array
                        //int idint=Integer.parseInt(steps);//runinfo
                        // valuelist_string[i]=steps;
                        // valuelist[i]=idint;
                        valuelist_distance[i] = distance_double;//runinfo_distance
                        valuelist_duration[i] = duration_double;//runinfo_distance
                        valuelist_calories[i] = calories_double;//runinfo_distance


                        //-------------------------------------------
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        //map.put(TAG_PID, id);
                        //map.put(TAG_NAME, name);


                        // adding HashList to ArrayList
                        productsList.add(map);


//----------------------Displaying elements in HashMap-----------------------------
                        Iterator it = productsList.iterator();
                        System.out.println("Elements in HashSet :");
                        while (it.hasNext())
                            System.out.println(it.next());
                        System.out.println(productsList);


                    }
                    //Printing values of id list
                    System.out.println("array elements: " + Arrays.toString(valuelist));
                    System.out.println("distance" + Arrays.toString(valuelist_distance));
                    System.out.println("duration" + Arrays.toString(valuelist_duration));
                    System.out.println("calories" + Arrays.toString(valuelist_calories));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("before return");
            //----------------------------------------------------------------------------
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }


    }

    //---------function for total values from database-------------------------------
    //private void Latestvalue() {
    public class Totalvalue extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... args) {
            productsList = new ArrayList<HashMap<String, String>>();

            /* Building Parameters */
            List<NameValuePair> params = new ArrayList<>();

            /* getting JSON string from URL */
            String msg1 = "IN";
            Log.d("inside httprequest", msg1);
            //String mindate = "2015-11-27";
            //String maxdate = "2015-11-30";
            //params.add(new BasicNameValuePair("mindate", mindate));
            //params.add(new BasicNameValuePair("maxdate", maxdate));


            String link = "http://10.0.2.2:8888/Latest_value.php"; //php url for total values
            JSONObject json = jParser.makeHttpRequest(link, "GET", params);
            Log.d("All Products: ", json.toString());//
            // --------------------------------------------------------------------
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                String mm = "in";
                Log.d("Arraylist", mm);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        String Totaldistance = c.getString(TAG_DISTANCE_RUN);
                        String Totalduration = c.getString(TAG_DURATION_RUN);
                        String Totalcalories = c.getString(TAG_CALORIES_RUN);
                        Latestdistance = Totaldistance;
                        Latesttime = Totalduration;
                        Latestcalories = Totalcalories;


                        Totaldistance_double = Double.parseDouble(Totaldistance);//creating runinfo_distance double array
                        Totalduration_double = Double.parseDouble(Totalduration);//creating runinfo_duration double array
                        Totalcalories_double = Double.parseDouble(Totalcalories);//creating runinfo_calories double array
                        //int idint=Integer.parseInt(steps);//runinfo
                        // valuelist_string[i]=steps;
                        // valuelist[i]=idint;
                        // valuelist_distance[i]=distance_double;//runinfo_distance
                        // valuelist_duration[i]=duration_double;//runinfo_distance
                        // valuelist_calories[i]=calories_double;//runinfo_distance


                        //-------------------------------------------
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
                        productsList.add(map);


//----------------------Displaying elements in HashMap-----------------------------
//                        Iterator it = productsList.iterator();
//                        System.out.println("Elements in HashSet :");
//                        while(it.hasNext())
//                            System.out.println(it.next());
//                        System .out.println(productsList);


                    }


                    //Printing values of id list
                    // System.out.println("array elements: " +  Arrays.toString(valuelist));
                    System.out.println("Total distance" + Totaldistance_double);
                    System.out.println("Total duration" + Totalduration_double);
                    System.out.println("Total calories" + Totalcalories_double);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("before return");
            //----------------------------------------------------------------------------
            return null;

        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            System.out.println("Do stuff to the updated arrays ... " + Latestdistance);
            TextViewdistance =(TextView) findViewById(R.id.totalmiles);
            String totalmilesString = Latestdistance + "miles";
            TextViewdistance.setText(totalmilesString);

            text1 =(TextView) findViewById(R.id.totaltime);
            String totaltimeString=Latesttime+"hours";
            text1.setText(totaltimeString);

            text2 =(TextView) findViewById(R.id.totalcalories);
            String totalcaloriesString=Latestcalories+"calories";
            text2.setText(totalcaloriesString);



        }


    }
    //Latestvalue lv=new Latestvalue();
    //  lv.execute();
    //}
    //-----------------Function for calling search.php and displaying values between min and max month---------
    public class MonthValue extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... args) {
            productsList = new ArrayList<HashMap<String, String>>();
//
            //HashMap<String, String> map = new HashMap <String, String>();

            /* Building Parameters */
            List<NameValuePair> params = new ArrayList<>();

            /* getting JSON string from URL */
            String msg1 = "IN";
            Log.d("inside httprequest", msg1);
            String minmonth = "2015-11-01";
            String maxmonth = "2015-12-31";
            params.add(new BasicNameValuePair("minmonth", minmonth));
            params.add(new BasicNameValuePair("maxmonth", maxmonth));

            //String link = "http://10.0.2.2:8888/fetchdata.php";
            // String link = "http://10.0.2.2:8888/fetchdata_runinfo.php";

            String link = "http://10.0.2.2:8888/Search_month.php"; //+ mindate + "maxdate="+ maxdate;
            JSONObject json = jParser.makeHttpRequest(link, "GET", params);
            Log.d("All Products: ", json.toString());//
            // ------------------------------------------------------------------
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                String mm = "in";
                Log.d("Arraylist", mm);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        //  String id = c.getString(TAG_PID);
                        //  String name = c.getString(TAG_NAME);
                        //Runinfo-------------------------
                        //String id = c.getString(TAG_ID_RUN);
                        //String steps = c.getString(TAG_STEPS_RUN);
                        String distance_month = c.getString(TAG_DISTANCE_RUN);
                        String duration_month = c.getString(TAG_DURATION_RUN);
                        String calories_month = c.getString(TAG_CALORIES_RUN);

                        double distance_month_double = Double.parseDouble(distance_month);//creating runinfo_distance double array
                        double duration_month_double = Double.parseDouble(duration_month);//creating runinfo_duration double array
                        double calories_month_double = Double.parseDouble(calories_month);//creating runinfo_calories double array
                        //int idint=Integer.parseInt(steps);//runinfo
                        // valuelist_string[i]=steps;
                        // valuelist[i]=idint;
                        valuelist_distance_month[i] = distance_month_double;//runinfo_distance
                        valuelist_duration_month[i] = duration_month_double;//runinfo_distance
                        valuelist_calories_month[i] = calories_month_double;//runinfo_distance


                        //-------------------------------------------
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();


                        productsList.add(map);

                    }
                    //Printing values of id list

                    System.out.println("distance_month" + Arrays.toString(valuelist_distance_month));
                    System.out.println("duration_month" + Arrays.toString(valuelist_duration_month));
                    System.out.println("calories_month" + Arrays.toString(valuelist_calories_month));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("before return");
            //----------------------------------------------------------------------------
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }


    }

    //---------function for total values from database-------------------------------
    public class Latestvalue extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... args) {
            productsList = new ArrayList<HashMap<String, String>>();

            /* Building Parameters */
            List<NameValuePair> params = new ArrayList<>();

            /* getting JSON string from URL */
            String msg1 = "IN";
            Log.d("inside httprequest", msg1);
            //String mindate = "2015-11-27";
            //String maxdate = "2015-11-30";
            //params.add(new BasicNameValuePair("mindate", mindate));
            //params.add(new BasicNameValuePair("maxdate", maxdate));


            String link = "http://10.0.2.2:8888/Totalvalues.php"; //php url for total values
            JSONObject json = jParser.makeHttpRequest(link, "GET", params);
            Log.d("All Products: ", json.toString());//
            // --------------------------------------------------------------------
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                String mm = "in";
                Log.d("Arraylist", mm);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        String Totaldistance = c.getString(TAG_DISTANCE_RUN);
                        String Totalduration = c.getString(TAG_DURATION_RUN);
                        String Totalcalories = c.getString(TAG_CALORIES_RUN);

                        Totaldistance_double = Double.parseDouble(Totaldistance);//creating runinfo_distance double array
                        Totalduration_double = Double.parseDouble(Totalduration);//creating runinfo_duration double array
                        Totalcalories_double = Double.parseDouble(Totalcalories);//creating runinfo_calories double array
                        //int idint=Integer.parseInt(steps);//runinfo
                        // valuelist_string[i]=steps;
                        // valuelist[i]=idint;
                        // valuelist_distance[i]=distance_double;//runinfo_distance
                        // valuelist_duration[i]=duration_double;//runinfo_distance
                        // valuelist_calories[i]=calories_double;//runinfo_distance


                        //-------------------------------------------
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
                        productsList.add(map);


//----------------------Displaying elements in HashMap-----------------------------
//                        Iterator it = productsList.iterator();
//                        System.out.println("Elements in HashSet :");
//                        while(it.hasNext())
//                            System.out.println(it.next());
//                        System .out.println(productsList);


                    }


                    //Printing values of id list
                    // System.out.println("array elements: " +  Arrays.toString(valuelist));
                    System.out.println("Total distance" + Totaldistance_double);
                    System.out.println("Total duration" + Totalduration_double);
                    System.out.println("Total calories" + Totalcalories_double);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("before return");
            //----------------------------------------------------------------------------
            return null;
        }


    }
}
