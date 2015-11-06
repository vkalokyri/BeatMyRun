package edu.rutgers.cs.rahul.helloworld;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;

import org.w3c.dom.Text;


//Beat My Run
//Something else
public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView timer_label, bpm_label, num_steps_label;
    private SensorManager sensor_manager;
    private Sensor step_counter;
    private Sensor step_detector;
    private long start_time = 0L;
    private long number_of_steps = -1L;
    private long number_of_steps_offset = 0L;
    private boolean is_running = false;
    private Handler timer_handler = new Handler();

    private boolean run_start = true;


    FloatingActionButton fab;

    private void reset_counters()
    {
        start_time = SystemClock.uptimeMillis();
        number_of_steps = 0L;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message;
                if (is_running)
                {
                    timer_handler.removeCallbacks(update_timer);
                    message = "Stopped the run.";
                    fab.setImageResource(android.R.drawable.ic_media_play);
                    is_running = false;
                    run_start = true;

                }
                else
                {
                    reset_counters();
                    timer_handler.postDelayed(update_timer, 0);
                    message = "Started the run";
//                    number_of_steps_offset = number_of_steps;
                    fab.setImageResource(android.R.drawable.ic_media_pause);
                    is_running = true;
                }
                Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });

        bpm_label = (TextView) findViewById(R.id.BPM);
        timer_label = (TextView) findViewById(R.id.timer);
        num_steps_label = (TextView) findViewById(R.id.num_steps);
        sensor_manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        step_counter = sensor_manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        step_detector = sensor_manager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSensorChanged(SensorEvent event)
    {
        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0) {
            value = (int) values[0];
        }

        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            if(run_start && value>0)
            {
                number_of_steps_offset = value;
                run_start = false;
            }
            number_of_steps = value;
        } else if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            // For test only. Only allowed value is 1.0 i.e. for step taken
//            detector_label.setText("Step Detector : " + value);
        }
    }

    @Override
    protected void onResume() {

        super.onResume();

        sensor_manager.registerListener(this, step_counter,

                SensorManager.SENSOR_DELAY_FASTEST);
        sensor_manager.registerListener(this, step_detector,

                SensorManager.SENSOR_DELAY_FASTEST);

    }

    @Override
    protected void onStop() {
        super.onStop();
        sensor_manager.unregisterListener(this, step_counter);
        sensor_manager.unregisterListener(this, step_detector);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    private Runnable update_timer = new Runnable() {
        @Override
        public void run() {
            long time_in_ms = SystemClock.uptimeMillis() - start_time;
            int secs = (int) (time_in_ms / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (time_in_ms % 1000);
            timer_label.setText("" + mins + ":"  + String.format("%02d", secs) + ":" + String.format("%02d", milliseconds/10));

            long steps = (number_of_steps - number_of_steps_offset);
            if (steps<0) {
                steps = 0;
            }
            double bpm = ( steps/ (double)time_in_ms) * 1000 * 60;
            bpm_label.setText("BPM: "+String.format( "%.02f", bpm));
//            bpm_label.setText(number_of_steps_offset + " : " + number_of_steps);
            num_steps_label.setText(steps + " steps");

            timer_handler.postDelayed(this, 0);

        }
    };

}