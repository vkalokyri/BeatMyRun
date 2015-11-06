package edu.rutgers.cs.rahul.helloworld;

/**
 * Created by Thara Philips on 11/6/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;


public class StatGraphActivity extends Activity {

    private View mChart;
    private Button backButton;
    private String[] mMonth = new String[]{

            "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
            "Sun"
    };

    private String[] mMonth1 = new String[] {
            "Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct",
            "Nov","Dec"

    };

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Button bbutton = (Button) findViewById(R.id.button3);
        System.out.println("On the graph screen");
        setContentView(R.layout.stat_activity_main);

        backButton = (Button)findViewById(R.id.button3);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_screen = new Intent(getApplicationContext(), StatActivity.class);
                startActivity(back_screen);
            }
        });

        Intent intent = getIntent();
        String period = intent.getStringExtra("period");

        if(period.equals("first"))
        {
            openChart();
            openChart1();
            openChart2();
        }
        else
        {
            openChartmnth();
            openChartmnth1();
            openChartmnth2();
        }
        //if(v.getId() == R.id.button3) {

        //}





        // Setting event click listener for the button btn_chart of the StatActivity layout






    }





    public void openChart(){

        int[] x = { 0,1,2,3,4,5,6 };

        int[] mile = { 2000,2500,2700,3000,2800,3500,3700};



        XYSeries mileSeries = new XYSeries("Miles");



        for(int i=0;i<x.length;i++){

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

        multiRenderer.setChartTitleTextSize(28);

        //setting text size of the axis title

        multiRenderer.setAxisTitleTextSize(24);

        //setting text size of the graph lable

        multiRenderer.setLabelsTextSize(24);

        //setting zoom buttons visiblity

        multiRenderer.setZoomButtonsVisible(false);

        //setting pan enablity which uses graph to move on both axis

        multiRenderer.setPanEnabled(false, false);

        //setting click false on graph

        multiRenderer.setClickEnabled(false);

        //setting zoom to false on both axis

        multiRenderer.setZoomEnabled(false, false);

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
        multiRenderer.setYAxisMax(4000);
        //setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(-0.5);
//setting max values to be display in x axis
        multiRenderer.setXAxisMax(11);
        //setting bar size or space between two bars
        multiRenderer.setBarSpacing(1);
        //Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        //Setting margin color of the graph to transparent

        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);


        //setting the margin size for the graph in the order top, left, bottom, right

        multiRenderer.setMargins(new int[]{40, 40, 40, 40});

        for(int i=0; i< x.length;i++){
            multiRenderer.addXTextLabel(i, mMonth[i]);
        }
        multiRenderer.addSeriesRenderer(mileRenderer);

        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);

        chartContainer.removeAllViews();

        //drawing bar chart

        mChart = ChartFactory.getBarChartView(StatGraphActivity.this, dataset, multiRenderer,Type.DEFAULT);

        //adding the view to the linearlayout

        chartContainer.addView(mChart);


    }



    private void openChart1(){

        int[] x = { 0,1,2,3,4,5,6};

        int[] calorie = { 2000,2500,2700,3000,2800,3500,3700};

        //int[] expense = {2200, 2700, 2900, 2800, 2600, 3000, 3300, 3400, 0, 0, 0, 0 };
// Creating an XYSeries for Income

        XYSeries calorieSeries = new XYSeries("Calories");

        for(int i=0;i<x.length;i++){

            calorieSeries.add(i,calorie[i]);

            //calorieSeries.add(i,calorie[i]);

        }

        // Creating a dataset to hold each series

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();


        dataset.addSeries(calorieSeries);

        XYSeriesRenderer calorieRenderer = new XYSeriesRenderer();

        calorieRenderer.setColor(Color.RED); //color of the graph set to red

        calorieRenderer.setFillPoints(true);

        calorieRenderer.setLineWidth(10);

        calorieRenderer.setDisplayChartValues(true);

        calorieRenderer.setDisplayChartValuesDistance(30); //setting chart value distance

        // Creating a XYMultipleSeriesRenderer to customize the whole chart

        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();

        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);

        multiRenderer.setXLabels(0);

        multiRenderer.setChartTitle("Calories Vs Period");

        multiRenderer.setXTitle("Period");

        multiRenderer.setYTitle("Calorie Count");

        multiRenderer.setChartTitleTextSize(28);

        //setting text size of the axis title

        multiRenderer.setAxisTitleTextSize(24);

        //setting text size of the graph lable

        multiRenderer.setLabelsTextSize(24);

        //setting zoom buttons visiblity

        multiRenderer.setZoomButtonsVisible(false);

        //setting pan enablity which uses graph to move on both axis

        multiRenderer.setPanEnabled(false, false);

        //setting click false on graph

        multiRenderer.setClickEnabled(false);

        //setting zoom to false on both axis

        multiRenderer.setZoomEnabled(false, false);

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
        multiRenderer.setYAxisMax(4000);
        //setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(-1);
//setting max values to be display in x axis
        multiRenderer.setXAxisMax(11);
        //setting bar size or space between two bars
        multiRenderer.setBarSpacing(1.5);
        //Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        //Setting margin color of the graph to transparent

        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);


        //setting the margin size for the graph in the order top, left, bottom, right

        multiRenderer.setMargins(new int[]{30, 30, 30, 30});

        for(int i=0; i< x.length;i++){
            multiRenderer.addXTextLabel(i, mMonth[i]);
        }

        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(calorieRenderer);
        // multiRenderer.addSeriesRenderer(expenseRenderer);

        //this part is used to display graph on the xml

        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart1);

        //remove any views before u paint the chart

        chartContainer.removeAllViews();

        //drawing bar chart

        mChart = ChartFactory.getBarChartView(StatGraphActivity.this, dataset, multiRenderer,Type.DEFAULT);

        //adding the view to the linearlayout

        chartContainer.addView(mChart);


    }

    private void openChart2(){

        int[] x = { 0,1,2,3,4,5,6};

        int[] time = { 2000,2500,2700,3000,2800,3500,3700};

        //int[] expense = {2200, 2700, 2900, 2800, 2600, 3000, 3300, 3400, 0, 0, 0, 0 };
// Creating an XYSeries for Income

        XYSeries timeSeries = new XYSeries("Time");

        // Creating an XYSeries for Expense

        //   XYSeries expenseSeries = new XYSeries("Period");

        // Adding data to Income and Expense Series

        for(int i=0;i<x.length;i++){

            timeSeries.add(i,time[i]);

            // expenseSeries.add(i,expense[i]);

        }

        // Creating a dataset to hold each series

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        // Adding Income Series to the dataset

        dataset.addSeries(timeSeries);

        // Adding Expense Series to dataset

        //dataset.addSeries(expenseSeries);

        // Creating XYSeriesRenderer to customize incomeSeries

        XYSeriesRenderer timeRenderer = new XYSeriesRenderer();

        timeRenderer.setColor(Color.RED); //color of the graph set to cyan

        timeRenderer.setFillPoints(true);

        timeRenderer.setLineWidth(10);

        timeRenderer.setDisplayChartValues(true);

        timeRenderer.setDisplayChartValuesDistance(30); //setting chart value distance

        // Creating XYSeriesRenderer to customize expenseSeries

        //XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();

        //expenseRenderer.setColor(Color.GREEN);

        //expenseRenderer.setFillPoints(true);

        //expenseRenderer.setLineWidth(2);

        //expenseRenderer.setDisplayChartValues(true);


        // Creating a XYMultipleSeriesRenderer to customize the whole chart

        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();

        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);

        multiRenderer.setXLabels(0);

        multiRenderer.setChartTitle("Time Vs Period Chart");

        multiRenderer.setXTitle("Period");

        multiRenderer.setYTitle("Time");

        /***

         * Customizing graphs

         */

//setting text size of the title

        multiRenderer.setChartTitleTextSize(28);

        //setting text size of the axis title

        multiRenderer.setAxisTitleTextSize(24);

        //setting text size of the graph lable

        multiRenderer.setLabelsTextSize(24);

        //setting zoom buttons visiblity

        multiRenderer.setZoomButtonsVisible(false);

        //setting pan enablity which uses graph to move on both axis

        multiRenderer.setPanEnabled(false, false);

        //setting click false on graph

        multiRenderer.setClickEnabled(false);

        //setting zoom to false on both axis

        multiRenderer.setZoomEnabled(false, false);

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
        multiRenderer.setYAxisMax(4000);
        //setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(-1);
//setting max values to be display in x axis
        multiRenderer.setXAxisMax(11);
        //setting bar size or space between two bars
        multiRenderer.setBarSpacing(1.5);
        //Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        //Setting margin color of the graph to transparent

        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);


        //setting the margin size for the graph in the order top, left, bottom, right

        multiRenderer.setMargins(new int[]{30,30,30, 30});

        for(int i=0; i< x.length;i++){
            multiRenderer.addXTextLabel(i, mMonth[i]);
        }

        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(timeRenderer);
        //multiRenderer.addSeriesRenderer(expenseRenderer);

        //this part is used to display graph on the xml

        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart2);

        //remove any views before u paint the chart

        chartContainer.removeAllViews();

        //drawing bar chart

        mChart = ChartFactory.getBarChartView(StatGraphActivity.this, dataset, multiRenderer,Type.DEFAULT);

        //adding the view to the linearlayout

        chartContainer.addView(mChart);


    }

    public void openChartmnth()
    {
        int[] x = { 0,1,2,3,4,5,6,7,8,9,10,11};

        int[] mile = { 2000,2500,2700,3000,2800,3500,3700,3800,3900,4000,4100,4200};

        // int[] expense = {2200, 2700, 2900, 2800, 2600, 3000, 3300, 3400, 0, 0, 0, 0 };
// Creating an XYSeries for Income

        XYSeries mileSeries = new XYSeries("Miles");

        // Creating an XYSeries for Expense

        //XYSeries expenseSeries = new XYSeries("Expense");

        // Adding data to Income and Expense Series

        for(int i=0;i<x.length;i++){

            mileSeries.add(i,mile[i]);

            //expenseSeries.add(i,expense[i]);

        }

        // Creating a dataset to hold each series

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        // Adding Income Series to the dataset

        dataset.addSeries(mileSeries);

        // Adding Expense Series to dataset

        //dataset.addSeries(expenseSeries);

        // Creating XYSeriesRenderer to customize incomeSeries

        XYSeriesRenderer mileRenderer = new XYSeriesRenderer();

        mileRenderer.setColor(Color.RED); //color of the graph set to cyan

        mileRenderer.setFillPoints(true);

        mileRenderer.setLineWidth(10);

        mileRenderer.setDisplayChartValues(true);

        mileRenderer.setDisplayChartValuesDistance(20); //setting chart value distance

        // Creating XYSeriesRenderer to customize expenseSeries

        //XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();

        //expenseRenderer.setColor(Color.GREEN);

        // expenseRenderer.setFillPoints(true);

        //expenseRenderer.setLineWidth(2);

        //expenseRenderer.setDisplayChartValues(true);


        // Creating a XYMultipleSeriesRenderer to customize the whole chart

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

        multiRenderer.setChartTitleTextSize(28);

        //setting text size of the axis title

        multiRenderer.setAxisTitleTextSize(24);

        //setting text size of the graph lable

        multiRenderer.setLabelsTextSize(24);

        //setting zoom buttons visiblity

        multiRenderer.setZoomButtonsVisible(false);

        //setting pan enablity which uses graph to move on both axis

        multiRenderer.setPanEnabled(false, false);

        //setting click false on graph

        multiRenderer.setClickEnabled(false);

        //setting zoom to false on both axis

        multiRenderer.setZoomEnabled(false, false);

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
        multiRenderer.setYAxisMax(4000);
        //setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(-0.5);
//setting max values to be display in x axis
        multiRenderer.setXAxisMax(11);
        //setting bar size or space between two bars
        multiRenderer.setBarSpacing(1);
        //Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        //Setting margin color of the graph to transparent

        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);


        //setting the margin size for the graph in the order top, left, bottom, right

        multiRenderer.setMargins(new int[]{40, 40, 40, 40});

        for(int i=0; i< x.length;i++){
            multiRenderer.addXTextLabel(i, mMonth1[i]);
        }

        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(mileRenderer);
        //multiRenderer.addSeriesRenderer(expenseRenderer);

        //this part is used to display graph on the xml

        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);

        //remove any views before u paint the chart

        chartContainer.removeAllViews();

        //drawing bar chart

        mChart = ChartFactory.getBarChartView(StatGraphActivity.this, dataset, multiRenderer,Type.DEFAULT);

        //adding the view to the linearlayout

        chartContainer.addView(mChart);


    }

    public void openChartmnth1(){

        int[] x = { 0,1,2,3,4,5,6,7,8,9,10,11};

        int[] calorie = { 2000,2500,2700,3000,2800,3500,3700,3800,3900,4000,4100,4200};

        //int[] expense = {2200, 2700, 2900, 2800, 2600, 3000, 3300, 3400, 0, 0, 0, 0 };
// Creating an XYSeries for Income

        XYSeries calorieSeries = new XYSeries("Calories");

        // Creating an XYSeries for Expense

        //XYSeries expenseSeries = new XYSeries("Days");

        // Adding data to Income and Expense Series

        for(int i=0;i<x.length;i++){

            calorieSeries.add(i,calorie[i]);

            //calorieSeries.add(i,calorie[i]);

        }

        // Creating a dataset to hold each series

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        // Adding Income Series to the dataset

        dataset.addSeries(calorieSeries);

        // Adding Expense Series to dataset

        //dataset.addSeries(expenseSeries);

        // Creating XYSeriesRenderer to customize incomeSeries

        XYSeriesRenderer calorieRenderer = new XYSeriesRenderer();

        calorieRenderer.setColor(Color.RED); //color of the graph set to cyan

        calorieRenderer.setFillPoints(true);

        calorieRenderer.setLineWidth(10);

        calorieRenderer.setDisplayChartValues(true);

        calorieRenderer.setDisplayChartValuesDistance(30); //setting chart value distance

        // Creating XYSeriesRenderer to customize expenseSeries

        //XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();

        // expenseRenderer.setColor(Color.GREEN);

        //expenseRenderer.setFillPoints(true);

        //expenseRenderer.setLineWidth(2);

        //expenseRenderer.setDisplayChartValues(true);


        // Creating a XYMultipleSeriesRenderer to customize the whole chart

        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();

        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);

        multiRenderer.setXLabels(0);

        multiRenderer.setChartTitle("Calories Vs Period");

        multiRenderer.setXTitle("Period");

        multiRenderer.setYTitle("Calorie Count");

        /***

         * Customizing graphs

         */

//setting text size of the title

        multiRenderer.setChartTitleTextSize(28);

        //setting text size of the axis title

        multiRenderer.setAxisTitleTextSize(24);

        //setting text size of the graph lable

        multiRenderer.setLabelsTextSize(24);

        //setting zoom buttons visiblity

        multiRenderer.setZoomButtonsVisible(false);

        //setting pan enablity which uses graph to move on both axis

        multiRenderer.setPanEnabled(false, false);

        //setting click false on graph

        multiRenderer.setClickEnabled(false);

        //setting zoom to false on both axis

        multiRenderer.setZoomEnabled(false, false);

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
        multiRenderer.setYAxisMax(4000);
        //setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(-1);
//setting max values to be display in x axis
        multiRenderer.setXAxisMax(11);
        //setting bar size or space between two bars
        multiRenderer.setBarSpacing(1.5);
        //Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        //Setting margin color of the graph to transparent

        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);


        //setting the margin size for the graph in the order top, left, bottom, right

        multiRenderer.setMargins(new int[]{30, 30, 30, 30});

        for(int i=0; i< x.length;i++){
            multiRenderer.addXTextLabel(i, mMonth1[i]);
        }

        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(calorieRenderer);
        // multiRenderer.addSeriesRenderer(expenseRenderer);

        //this part is used to display graph on the xml

        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart1);

        //remove any views before u paint the chart

        chartContainer.removeAllViews();

        //drawing bar chart

        mChart = ChartFactory.getBarChartView(StatGraphActivity.this, dataset, multiRenderer,Type.DEFAULT);

        //adding the view to the linearlayout

        chartContainer.addView(mChart);


    }

    private void openChartmnth2(){

        int[] x = { 0,1,2,3,4,5,6,7,8,9,10,11};

        int[] time = { 2000,2500,2700,3000,2800,3500,3700,3800,3900,4000,4100,4200};

        //int[] expense = {2200, 2700, 2900, 2800, 2600, 3000, 3300, 3400, 0, 0, 0, 0 };
// Creating an XYSeries for Income

        XYSeries timeSeries = new XYSeries("Time");

        // Creating an XYSeries for Expense

        //   XYSeries expenseSeries = new XYSeries("Period");

        // Adding data to Income and Expense Series

        for(int i=0;i<x.length;i++){

            timeSeries.add(i,time[i]);

            // expenseSeries.add(i,expense[i]);

        }

        // Creating a dataset to hold each series

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        // Adding Income Series to the dataset

        dataset.addSeries(timeSeries);

        // Adding Expense Series to dataset

        //dataset.addSeries(expenseSeries);

        // Creating XYSeriesRenderer to customize incomeSeries

        XYSeriesRenderer timeRenderer = new XYSeriesRenderer();

        timeRenderer.setColor(Color.RED); //color of the graph set to cyan

        timeRenderer.setFillPoints(true);

        timeRenderer.setLineWidth(10);

        timeRenderer.setDisplayChartValues(true);

        timeRenderer.setDisplayChartValuesDistance(30); //setting chart value distance

        // Creating XYSeriesRenderer to customize expenseSeries

        //XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();

        //expenseRenderer.setColor(Color.GREEN);

        //expenseRenderer.setFillPoints(true);

        //expenseRenderer.setLineWidth(2);

        //expenseRenderer.setDisplayChartValues(true);


        // Creating a XYMultipleSeriesRenderer to customize the whole chart

        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();

        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);

        multiRenderer.setXLabels(0);

        multiRenderer.setChartTitle("Time Vs Period Chart");

        multiRenderer.setXTitle("Period");

        multiRenderer.setYTitle("Time");

        /***

         * Customizing graphs

         */

//setting text size of the title

        multiRenderer.setChartTitleTextSize(28);

        //setting text size of the axis title

        multiRenderer.setAxisTitleTextSize(24);

        //setting text size of the graph lable

        multiRenderer.setLabelsTextSize(24);

        //setting zoom buttons visiblity

        multiRenderer.setZoomButtonsVisible(false);

        //setting pan enablity which uses graph to move on both axis

        multiRenderer.setPanEnabled(false, false);

        //setting click false on graph

        multiRenderer.setClickEnabled(false);

        //setting zoom to false on both axis

        multiRenderer.setZoomEnabled(false, false);

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
        multiRenderer.setYAxisMax(4000);
        //setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(-1);
//setting max values to be display in x axis
        multiRenderer.setXAxisMax(11);
        //setting bar size or space between two bars
        multiRenderer.setBarSpacing(1.5);
        //Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        //Setting margin color of the graph to transparent

        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);


        //setting the margin size for the graph in the order top, left, bottom, right

        multiRenderer.setMargins(new int[]{30,30,30, 30});

        for(int i=0; i< x.length;i++){
            multiRenderer.addXTextLabel(i, mMonth1[i]);
        }

        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(timeRenderer);
        //multiRenderer.addSeriesRenderer(expenseRenderer);

        //this part is used to display graph on the xml

        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart2);

        //remove any views before u paint the chart

        chartContainer.removeAllViews();

        //drawing bar chart

        mChart = ChartFactory.getBarChartView(StatGraphActivity.this, dataset, multiRenderer,Type.DEFAULT);

        //adding the view to the linearlayout

        chartContainer.addView(mChart);


    }



}











