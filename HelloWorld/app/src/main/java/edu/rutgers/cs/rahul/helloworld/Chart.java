package edu.rutgers.cs.rahul.helloworld;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Thara Philips on 12/3/2015.
 */
public class Chart extends Activity{


    int count1;
    int count2;
    int count3;
    private View mChart;
    //int[] mile=new int[7];

    //double max_miles;


    public void onCreate(Bundle savedInstanceState) {


        String tt="in";
        Log.d("Reached open chart", tt);//

        super.onCreate(savedInstanceState);
        //Button bbutton = (Button) findViewById(R.id.button3);
        System.out.println("On the graph screen");
        setContentView(R.layout.chart_week);
        //------Receiving values from Datepicker_days------------------------
        Intent i=getIntent();
        Bundle extras = this.getIntent().getExtras();
        double[] miles = i.getDoubleArrayExtra("valuelist_distance_i");//Receivingggggggggggggggggggg
        System.out.println("abc"+Arrays.toString(miles));
        double[] time = i.getDoubleArrayExtra("valuelist_duration_i");
        System.out.println("abc"+Arrays.toString(time));
        double[] calories = i.getDoubleArrayExtra("valuelist_calories_i");
        String[] datetime=i.getStringArrayExtra("valuelist_datetime_i");
        System.out.println("datetime"+Arrays.toString(datetime));

        double result[] = extras.getDoubleArray("key");
        System.out.println("result"+Arrays.toString(result));
        int h = 1;
//----------------------------------------------------------------
        double[] miles_maxvalue=miles;
        double max_miles=miles[0];

        for (int ii = 0; ii < miles_maxvalue.length; ii++) {
            if (miles_maxvalue[ii] > max_miles) {
                max_miles = miles_maxvalue[ii];
            }
        }
        System .out.println(max_miles);

//---------------------------------------------------------------------------
        double[] time_maxvalue=time;
        double max_time=miles[0];

        for (int ii = 0; ii < time_maxvalue.length; ii++) {
            if (time_maxvalue[ii] > max_time) {
                max_time = time_maxvalue[ii];
            }
        }
        System .out.println(max_time);

//---------------------------------------------------------------------------
        double[] calories_maxvalue=calories;
        double max_calories=miles[0];

        for (int ii = 0; ii < calories_maxvalue.length; ii++) {
            if (calories_maxvalue[ii] > max_calories) {
                max_calories = calories_maxvalue[ii];
            }
        }
        System .out.println(max_calories);

//---------------------------------------------------------------------------
        openChart(miles,max_miles,datetime);
        openChart1(time,max_time,datetime);
        openChart2(calories, max_calories,datetime);



    }


    public void openChart(double mile[],double max_miles,String mday[]){//int mile[]
        //  setContentView(R.layout.chart_week);

        //String[] mday=new String[]{"", "", "", "", "", "",""};
        int[] x = { 0,1,2,3,4,5,6 };

        //int[] mile = { 2000,2500,2700,3000,2800,3500,3700};



        XYSeries mileSeries = new XYSeries("Miles");



        for(int i=0;i<mile.length;i++){

            mileSeries.add(i,mile[i]);


        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        dataset.addSeries(mileSeries);

        XYSeriesRenderer mileRenderer = new XYSeriesRenderer();

        mileRenderer.setColor(Color.RED); //color of the graph set to cyan

        mileRenderer.setFillPoints(true);

        mileRenderer.setLineWidth(10);

        mileRenderer.setDisplayChartValues(true);

        mileRenderer.setDisplayChartValuesDistance(20); //setting chart value distance

        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();

        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);

        multiRenderer.setXLabels(0);

        multiRenderer.setChartTitle("Miles Covered");

        multiRenderer.setXTitle("Period");

        multiRenderer.setYTitle("Miles");

        /***

         * Customizing graphs

         */

//setting text size of the title

        multiRenderer.setChartTitleTextSize(38);



        //setting text size of the axis title

        multiRenderer.setAxisTitleTextSize(24);

        //setting text size of the graph lable

        multiRenderer.setLabelsTextSize(24);

        //setting zoom buttons visiblity

        multiRenderer.setZoomButtonsVisible(true);

        //setting pan enablity which uses graph to move on both axis

        multiRenderer.setPanEnabled(true, true);

        //setting click false on graph

        multiRenderer.setClickEnabled(false);

        //setting zoom to false on both axis

        multiRenderer.setZoomEnabled(true, true);

        //setting lines to display on y axis

        multiRenderer.setShowGridY(false);

        //setting lines to display on x axis

        multiRenderer.setShowGridX(false);

        //setting legend to fit the screen size

        multiRenderer.setFitLegend(true);

        //setting displaying line on grid

        multiRenderer.setShowGrid(false);

        //setting zoom to false

        multiRenderer.setZoomEnabled(false);

        //setting external zoom functions to false

        multiRenderer.setExternalZoomEnabled(false);

        //setting displaying lines on graph to be formatted(like using graphics)

        multiRenderer.setAntialiasing(true);

        //setting to in scroll to false

        multiRenderer.setInScroll(false);

        //setting to set legend height of the graph

        multiRenderer.setLegendHeight(30);
        //setting x axis label align
        multiRenderer.setXLabelsAlign(Paint.Align.CENTER);
        //setting y axis label to align
        multiRenderer.setYLabelsAlign(Paint.Align.LEFT);
        //setting text style
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
        //setting no of values to display in y axis
        multiRenderer.setYLabels(10);
        // setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.

        // if you use dynamic values then get the max y value and set here
        multiRenderer.setYAxisMax(max_miles);
        //setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(-0.5);
//setting max values to be display in x axis
        //----------------------------------------------------------------------

        for(int jj=0;jj<mile.length;jj++)
        {
            if(mile[jj]!=0) {
                count1++;
            }
        }

        System .out.println("count values miles" + count1);


        multiRenderer.setXAxisMax(count1 + 1);




        multiRenderer.setXAxisMax(count1);
        //setting bar size or space between two bars
        multiRenderer.setBarSpacing(1);
        multiRenderer.setBarWidth(30);
        //Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        //Setting margin color of the graph to transparent

        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setYLabelsColor(0, Color.BLACK);
        //multiRenderer.setYLabelsAlign(Paint.Align.LEFT,0);
        multiRenderer.setLabelsColor(Color.BLACK);



        //setting the margin size for the graph in the order top, left, bottom, right

        multiRenderer.setMargins(new int[]{40, 40, 40, 40});

        for(int i=0; i< x.length;i++){
            multiRenderer.addXTextLabel(i, mday[i]);
        }
        multiRenderer.addSeriesRenderer(mileRenderer);

        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);

        chartContainer.removeAllViews();

        //drawing bar chart

        mChart = ChartFactory.getBarChartView(Chart.this, dataset, multiRenderer, BarChart.Type.DEFAULT);

        //adding the view to the linearlayout

        chartContainer.addView(mChart);


    }
    public void openChart1(double time[],double max_time,String mday[]){//int mile[]
        //  setContentView(R.layout.chart_week);

        //String[] mday=new String[]{"", "", "", "", "", "",""};
        int[] x = { 0,1,2,3,4,5,6 };

        //int[] mile = { 2000,2500,2700,3000,2800,3500,3700};



        XYSeries mileSeries = new XYSeries("Time");



        for(int i=0;i<x.length;i++){

            mileSeries.add(i,time[i]);


        }


        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        dataset.addSeries(mileSeries);

        XYSeriesRenderer mileRenderer = new XYSeriesRenderer();

        mileRenderer.setColor(Color.RED); //color of the graph set to cyan

        mileRenderer.setFillPoints(true);

        mileRenderer.setLineWidth(10);

        mileRenderer.setDisplayChartValues(true);

        mileRenderer.setDisplayChartValuesDistance(20); //setting chart value distance

        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();

        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);

        multiRenderer.setXLabels(0);

        multiRenderer.setChartTitle("Run Duration");

        multiRenderer.setXTitle("Period");

        multiRenderer.setYTitle("Time(mins)");

        /***

         * Customizing graphs

         */

//setting text size of the title

        multiRenderer.setChartTitleTextSize(38);

        //setting text size of the axis title

        multiRenderer.setAxisTitleTextSize(24);

        //setting text size of the graph lable

        multiRenderer.setLabelsTextSize(24);

        //setting zoom buttons visiblity

        multiRenderer.setZoomButtonsVisible(true);

        //setting pan enablity which uses graph to move on both axis

        multiRenderer.setPanEnabled(true, true);

        //setting click false on graph

        multiRenderer.setClickEnabled(false);

        //setting zoom to false on both axis

        multiRenderer.setZoomEnabled(true, true);

        //setting lines to display on y axis

        multiRenderer.setShowGridY(false);

        //setting lines to display on x axis

        multiRenderer.setShowGridX(false);

        //setting legend to fit the screen size

        multiRenderer.setFitLegend(true);

        //setting displaying line on grid

        multiRenderer.setShowGrid(false);

        //setting zoom to false

        multiRenderer.setZoomEnabled(false);

        //setting external zoom functions to false

        multiRenderer.setExternalZoomEnabled(false);

        //setting displaying lines on graph to be formatted(like using graphics)

        multiRenderer.setAntialiasing(true);

        //setting to in scroll to false

        multiRenderer.setInScroll(false);

        //setting to set legend height of the graph

        multiRenderer.setLegendHeight(30);
        //setting x axis label align
        multiRenderer.setXLabelsAlign(Paint.Align.CENTER);
        //setting y axis label to align
        multiRenderer.setYLabelsAlign(Paint.Align.LEFT);
        //setting text style
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
        //setting no of values to display in y axis
        multiRenderer.setYLabels(10);
        // setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.

        // if you use dynamic values then get the max y value and set here
        multiRenderer.setYAxisMax(max_time);
        //setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(-0.5);
//setting max values to be display in x axis
        for(int jj=0;jj<time.length;jj++)
        {
            if(time[jj]!=0) {
                count2++;
            }
        }

        System .out.println("count values miles" + count2);




        multiRenderer.setXAxisMax(count2);
        //setting bar size or space between two bars
        multiRenderer.setBarSpacing(1);
        multiRenderer.setBarWidth(30);
        //Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        //Setting margin color of the graph to transparent
        multiRenderer.setYLabelsColor(0, Color.BLACK);
        multiRenderer.setLabelsColor(Color.BLACK);

        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);


        //setting the margin size for the graph in the order top, left, bottom, right

        multiRenderer.setMargins(new int[]{40, 40, 40, 40});

        for(int i=0; i< x.length;i++){
            multiRenderer.addXTextLabel(i, mday[i]);
        }
        multiRenderer.addSeriesRenderer(mileRenderer);

        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart1);

        chartContainer.removeAllViews();

        //drawing bar chart

        mChart = ChartFactory.getBarChartView(Chart.this, dataset, multiRenderer, BarChart.Type.DEFAULT);

        //adding the view to the linearlayout

        chartContainer.addView(mChart);


    }
    public void openChart2(double calories[],double max_calories,String mday[]){//int mile[]
        //  setContentView(R.layout.chart_week);

       // String[] mday=new String[]{"", "", "", "", "", "",""};
        int[] x = { 0,1,2,3,4,5,6 };

        //int[] mile = { 2000,2500,2700,3000,2800,3500,3700};



        XYSeries mileSeries = new XYSeries("Miles");



        for(int i=0;i<calories.length;i++){

            mileSeries.add(i,calories[i]);


        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        dataset.addSeries(mileSeries);

        XYSeriesRenderer mileRenderer = new XYSeriesRenderer();

        mileRenderer.setColor(Color.RED); //color of the graph set to red

        mileRenderer.setFillPoints(true);

        mileRenderer.setLineWidth(10);

        mileRenderer.setDisplayChartValues(true);

        mileRenderer.setDisplayChartValuesDistance(20); //setting chart value distance

        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();

        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);

        multiRenderer.setXLabels(0);

        multiRenderer.setChartTitle("Calories Burnt");

        multiRenderer.setXTitle("Period");

        multiRenderer.setYTitle("Calories");

        /***

         * Customizing graphs

         */

//setting text size of the title

        multiRenderer.setChartTitleTextSize(38);

        //setting text size of the axis title

        multiRenderer.setAxisTitleTextSize(24);

        //setting text size of the graph lable

        multiRenderer.setLabelsTextSize(24);

        //setting zoom buttons visiblity

        multiRenderer.setZoomButtonsVisible(true);

        //setting pan enablity which uses graph to move on both axis

        multiRenderer.setPanEnabled(true, true);

        //setting click false on graph

        multiRenderer.setClickEnabled(false);

        //setting zoom to false on both axis

        multiRenderer.setZoomEnabled(true, true);

        //setting lines to display on y axis

        multiRenderer.setShowGridY(false);

        //setting lines to display on x axis

        multiRenderer.setShowGridX(false);

        //setting legend to fit the screen size

        multiRenderer.setFitLegend(true);

        //setting displaying line on grid

        multiRenderer.setShowGrid(false);

        //setting zoom to false

        multiRenderer.setZoomEnabled(false);

        //setting external zoom functions to false

        multiRenderer.setExternalZoomEnabled(false);

        //setting displaying lines on graph to be formatted(like using graphics)

        multiRenderer.setAntialiasing(true);

        //setting to in scroll to false

        multiRenderer.setInScroll(false);

        //setting to set legend height of the graph

        multiRenderer.setLegendHeight(30);
        //setting x axis label align
        multiRenderer.setXLabelsAlign(Paint.Align.CENTER);
        //setting y axis label to align
        multiRenderer.setYLabelsAlign(Paint.Align.LEFT);
        //setting text style
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
        //setting no of values to display in y axis
        multiRenderer.setYLabels(10);
        // setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.

        // if you use dynamic values then get the max y value and set here
        multiRenderer.setYAxisMax(max_calories);
        //setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(-0.5);
//setting max values to be display in x axis
        //---------------------------------------------------------------------------
        for(int jj=0;jj<calories.length;jj++)
        {
            if(calories[jj]!=0) {
                count3++;
            }
        }

        System .out.println("count values calories" + count3);
        //---------------------------------------------------------------------------


        multiRenderer.setXAxisMax(count3);
        //setting bar size or space between two bars
        multiRenderer.setBarSpacing(1);
        multiRenderer.setBarWidth(30);
        //Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        //Setting margin color of the graph to transparent

        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);

        multiRenderer.setYLabelsColor(0, Color.BLACK);
        //multiRenderer.setXLabelsColor(Color.BLACK);
        multiRenderer.setLabelsColor(Color.BLACK);

        multiRenderer.setYAxisAlign(Paint.Align.LEFT, 0);
        //multiRenderer.setYLabelsAlign(Paint.Align.LEFT,0);

        //setting the margin size for the graph in the order top, left, bottom, right

        multiRenderer.setMargins(new int[]{40, 40, 40, 40});

        for(int i=0; i< x.length;i++){
            multiRenderer.addXTextLabel(i, mday[i]);
        }
        multiRenderer.addSeriesRenderer(mileRenderer);

        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart2);

        chartContainer.removeAllViews();

        //drawing bar chart

        mChart = ChartFactory.getBarChartView(Chart.this, dataset, multiRenderer, BarChart.Type.DEFAULT);

        //adding the view to the linearlayout

        chartContainer.addView(mChart);


    }

}
