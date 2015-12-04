package edu.rutgers.cs.rahul.helloworld;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Policy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Thara Philips on 12/3/2015.
 */
public class Datepicker_days extends Activity implements View.OnClickListener {



    private EditText fromDateEtxt;
    private EditText toDateEtxt;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    private DatePicker dobPicker;



    //--------------Asyntask Declaration
    JsonParser jParser = new JsonParser();
    //String mindate_datepicker;
    //String maxdate_datepicker;


    String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_DISTANCE_RUN = "distance";
    private static final String TAG_DURATION_RUN = "duration";
    private static final String TAG_CALORIES_RUN = "calories";
    private static final String TAG_DATETIME_RUN = "datetime";
    JSONArray products = null;
    ArrayList<HashMap<String, String>> productsList;
    String[] hashmapvalues;
    int[] valuelist = new int[20];
    List<Integer> test = new ArrayList<Integer>();
    double[] valuelist_distance = new double[30];
    double[] valuelist_duration = new double[30];
    double[] valuelist_calories = new double[30];
    String[] valuelist_datetime = new String[30];

    private Button SubmitButton_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datepickdays);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();

        SubmitButton_date =(Button)findViewById(R.id.submit_date);
        SubmitButton_date.setOnClickListener(this);



    }






    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();


        toDateEtxt = (EditText) findViewById(R.id.etxt_todate);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
                System.out.println("from"+newDate);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
                System.out.println("to"+newDate);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void onClick(View view) {
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();

        } else if(view == toDateEtxt) {
            toDatePickerDialog.show();
        }
        else if(view==SubmitButton_date)
        {
            //SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            //String mindate_datepicker = format.format(fromDateEtxt.getText());
            //String maxdate_datepicker = format.format(fromDateEtxt.getText());
            Editable fromdate=fromDateEtxt.getText();
            Editable todate=toDateEtxt.getText();
            String mindate_datepicker=fromdate.toString();
            String maxdate_datepicker=todate.toString();


            System.out.println(mindate_datepicker);
            System.out.println(maxdate_datepicker);
            new Selectingdays().execute(mindate_datepicker,maxdate_datepicker);
        }

    }



    //-----------------Function for calling search.php and displaying values between min and max date---------

    public class Selectingdays extends AsyncTask<String,String,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String ...arg) {
            String mindate=arg[0];
            String maxdate=arg[1];
            System.out.println(mindate);//2015-11-27
            System .out.println(maxdate);
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
                        String date_datepicker=c.getString(TAG_DATETIME_RUN);
                        System.out.println(distance);

                        double distance_double = Double.parseDouble(distance);//creating runinfo_distance double array
                        double duration_double = Double.parseDouble(duration);//creating runinfo_duration double array
                        double calories_double = Double.parseDouble(calories);//creating runinfo_calories double array
                        //int idint=Integer.parseInt(steps);//runinfo
                        // valuelist_string[i]=steps;
                        // valuelist[i]=idint;
                        valuelist_distance[i] = distance_double;//runinfo_distance
                        valuelist_duration[i] = duration_double;//runinfo_distance
                        valuelist_calories[i] = calories_double;//runinfo_distance
                        valuelist_datetime[i]=  date_datepicker;


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
            Intent new_screen=new Intent(Datepicker_days.this,Chart.class);//sendingggggggggggggggggggggggg
            new_screen.putExtra("valuelist_distance_i", valuelist_distance);
            new_screen.putExtra("valuelist_duration_i",valuelist_duration);
            new_screen.putExtra("valuelist_calories_i",valuelist_calories);
            new_screen.putExtra("valuelist_datetime_i",valuelist_datetime);
            startActivity(new_screen);
        }


    }


}
