package edu.rutgers.cs.rahul.helloworld;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.plus.Plus;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Leaderboards extends AppCompatActivity {


    String challengeTotal, challengeWon, challengeLost;
    private EditText editTextCount, editTextWon;
    String currentUserId = Plus.PeopleApi.getCurrentPerson(LoginActivity.mGoogleApiClient).getId();
    double total, won, lost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);



        editTextCount = (EditText)findViewById(R.id.editTextCount);
        editTextWon = (EditText)findViewById(R.id.editTextWon);

        //  editTextCount.setText("test");
        getTotal();
        // openChart();

    }

    private void getTotal(){
        class GetTotal extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Leaderboards.this,"Fetching...","Wait...",false,false);
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
                String s = rh.sendGetRequestParam(Config.URL_GET_TOTAL_CHALLENGES, currentUserId);
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
            challengeWon  = c.getString(Config.TAG_NAME);


            //Toast.makeText(Leaderboards.this,challengeWon , Toast.LENGTH_LONG).show();
            //Toast.makeText(Leaderboards.this,"Test" , Toast.LENGTH_LONG).show();
//            String desg = c.getString(Config.TAG_DESG);
//            String sal = c.getString(Config.TAG_SAL);
//
            editTextCount.setText(challengeTotal);
            editTextWon.setText(challengeWon);

            total = Double.parseDouble(challengeTotal.toString());
            won = Double.parseDouble(challengeWon.toString());
            lost = total - won;
//            editTextDesg.setText(desg);
//            editTextSalary.setText(sal);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        openChart();
    }

    private void openChart(){

        // getTotal();

        // Pie Chart Section Names
        String[] code = new String[] {
                "Total", "Won"
        };

        // Pie Chart Section Value
        //double[] distribution = { 3.9, 12.9, 55.8, 1.9, 23.7, 1.8 } ;

//        double total = Double.parseDouble(challengeTotal.toString());
//        double won = Double.parseDouble(challengeWon.toString());
//        double lost = Double.parseDouble(challengeLost.toString());

        double[] distribution = { total, won } ;

        // Color of each Pie Chart Sections
        int[] colors = { Color.BLUE, Color.RED };
        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries(" Android version distribution as on October 1, 2012");
        for(int i=0 ;i < distribution.length;i++){
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(code[i], distribution[i]);
        }
        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for(int i = 0 ;i<distribution.length;i++){

            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
            seriesRenderer.setChartValuesTextSize(1000);

            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }
        defaultRenderer.setChartTitle("Challenges Won vs Challenges Lost");
        defaultRenderer.setChartTitleTextSize(60);
        defaultRenderer.setLabelsColor(Color.BLACK);
        defaultRenderer.setLabelsTextSize(40);
        defaultRenderer.setZoomButtonsVisible(true);

        // Creating an intent to plot bar chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries, defaultRenderer, "AChartEnginePieChartDemo");

        // Start Activity
        startActivity(intent);

    }




}
