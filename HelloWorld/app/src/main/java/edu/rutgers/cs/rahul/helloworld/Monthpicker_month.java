package edu.rutgers.cs.rahul.helloworld;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.plus.Plus;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;



/**
 * Created by Thara Philips on 12/3/2015.
 */
public class Monthpicker_month extends Activity implements AdapterView.OnItemSelectedListener {


    private Button SubmitButton_date;

    public String item;
    public String item1;
    int success_value;
    //--------------Asyntask Declaration
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
    int[] valuelist = new int[20];
    List<Integer> test = new ArrayList<Integer>();
    String distance_month;
    String duration_month;
    String calories_month;
    double[] valuelistmonth_distance = new double[20];
    double[] valuelistmonth_duration = new double[20];
    double[] valuelistmonth_calories = new double[20];
    Monthpicker_month this_obj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthpickmonth);
        this_obj=this;
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.DropdownSpinner);
        Spinner spinner1 = (Spinner) findViewById(R.id.DropdownSpinner1);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        spinner1.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("January");
        categories.add("February");
        categories.add("March");
        categories.add("April");
        categories.add("May");
        categories.add("June");
        categories.add("July");
        categories.add("August");
        categories.add("September");
        categories.add("October");
        categories.add("November");
        categories.add("December");

        List<String> categories1 = new ArrayList<String>();
        categories1.add("2015");
        categories1.add("2014");
        categories1.add("2013");
        categories1.add("2012");
        categories1.add("2011");
        categories1.add("2010");
        categories1.add("2009");
        categories1.add("2008");
        categories1.add("2007");
        categories1.add("2006");
        categories1.add("2005");
        categories1.add("2004");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories1);

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter1);
        Button monthSubmit_button = (Button) findViewById(R.id.submit_month);
        View.OnClickListener clickListener = new View.OnClickListener() {

            //  Intent new_screen = new Intent(MainActivity.this, Datepicker_days.class);

            @Override
            public void onClick(View v) {
                Log.e("Month Picker","Trying to get stuff");
                if(v.getId() == R.id.submit_month) {

                    new Selectingmonth().execute(item,item1);
                }

            }
        };
        monthSubmit_button.setOnClickListener(clickListener);


        Spinner menuspinner = (Spinner) findViewById(R.id.MonthPickerSpinner_nav);


        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Pick Month");
        spinnerArray.add("Run");
        spinnerArray.add("Challenge");
        spinnerArray.add("Statistics");
        spinnerArray.add("Personal Details");
        spinnerArray.add("Logout");


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_dropdown_item, spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.dropdown_list);
        menuspinner.setAdapter(spinnerArrayAdapter);


//        spinner.setAdapter(adapter);

        menuspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner3 = (Spinner) parent;

        if (spinner3.getId() == R.id.DropdownSpinner) {
            item = parent.getItemAtPosition(position).toString();

            // Showing selected spinner item
            System.out.println(item);

        }  // What spinner 1 should do

        else {
            item1 = parent.getItemAtPosition(position).toString();
            System.out.println(item1);
        }
    }
    // On selecting a spinner item
    //String item = parent.getItemAtPosition(position).toString();
    //Log.d("value in item", item);

    // Showing selected spinner item
    //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }



    public class Selectingmonth extends AsyncTask<String,String,Void> {

        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(Monthpicker_month.this, "Fetching Data", "Wait...", false, false);

        }

        @Override
        protected Void doInBackground(String ...arg) {
            String minmonth=arg[0];
            String minyear=arg[1];
            System.out.println(minmonth);//2015-11-27
            System .out.println(minyear);
            productsList = new ArrayList<HashMap<String, String>>();
//
            //HashMap<String, String> map = new HashMap <String, String>();

            /* Building Parameters */
            List<NameValuePair> params = new ArrayList<>();

            /* getting JSON string from URL */
            String msg1 = "IN";
            Log.d("inside httprequest", msg1);
            //String mindate = "2015-11-27";
            //String maxdate = "2015-12-31";
            params.add(new BasicNameValuePair("minmonth", minmonth));
            params.add(new BasicNameValuePair("minyear", minyear));

            //String link = "http://beatmyrun.net16.net/fetchdata.php";
            // String link = "http://beatmyrun.net16.net/fetchdata_runinfo.php";

            String link = "http://beatmyrun.net16.net/Search_month.php"; //+ mindate + "maxdate="+ maxdate;
            JSONObject json = jParser.makeHttpRequest(link, "GET", params);
            Log.d("All Products: ", json.toString());//
            // --------------------------------------------------------------------
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                success_value = success;
                System.out.println("success value"+success);

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

                        distance_month=distance;
                        duration_month=duration;
                        calories_month=calories;


//                        double distance_double = Double.parseDouble(distance);//creating runinfo_distance double array
//                        double duration_double = Double.parseDouble(duration);//creating runinfo_duration double array
//                        double calories_double = Double.parseDouble(calories);//creating runinfo_calories double array
//                        //int idint=Integer.parseInt(steps);//runinfo
//                        // valuelist_string[i]=steps;
//                        // valuelist[i]=idint;
//                        valuelistmonth_distance[i] = distance_double;//runinfo_distance
//                        valuelistmonth_duration[i] = duration_double;//runinfo_distance
//                        valuelistmonth_calories[i] = calories_double;//runinfo_distance


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
                    //System.out.println("array elements: " + Arrays.toString(valuelist));
                    System.out.println("distance_monthpicker"+distance_month );
                    System.out.println("distance"+duration_month );
                    System.out.println("distance"+calories_month );

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
            loading.dismiss();
            super.onPostExecute(aVoid);
            System.out.println("before intend");
            Intent new_screen=new Intent(Monthpicker_month.this,Chart_month.class);//sendingggggggggggggggggggggggg
            new_screen.putExtra("valuelist_distance_m",distance_month);
            new_screen.putExtra("valuelist_duration_m",duration_month);
            new_screen.putExtra("valuelist_calories_m",calories_month);
            new_screen.putExtra("success",success_value);
            startActivity(new_screen);
        }


    }

}
