package edu.rutgers.cs.rahul.helloworld;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Handler;

import com.google.android.gms.plus.Plus;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;

import android.widget.Toast;
import android.content.Intent;
import android.widget.Toolbar;

import org.apache.http.HttpResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import edu.rutgers.cs.rahul.helloworld.PlayList;

public class RunActivity extends YouTubeBaseActivity implements SensorEventListener,YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaybackEventListener{

    private TextView timer_label, bpm_label, num_steps_label;
    private Button next_song;
    private SensorManager sensor_manager;
    private Sensor step_counter;
    private Sensor step_detector;
    private static long start_time = 0L;
    private static long number_of_steps = -1L;
    private long number_of_steps_offset = 0L;
    private static boolean is_running = false;
    private Handler timer_handler = new Handler();
    private static boolean run_start = true;
    private static boolean run_start_reset_flag = true;
    YouTubePlayer g_player;
    long last_stopped_time = 0;
    TextView fab;

    //###################################################

    public static final String DEVELOPER_KEY = "AIzaSyDV8a8kz2I1lf1FwbaO7CFcdOfEScChYZ8";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private String VIDEO_ID ;
//    private ArrayList<String> playlist = new ArrayList<String>();
    private PlayList playlist = new PlayList();
    YouTubePlayerFragment myYouTubePlayerFragment;
    RunActivity this_obj;

    private static HttpConnector connector=new HttpConnector();


    public static void start_run()
    {
        run_start = true;
        run_start_reset_flag = true;
        is_running = false;

        System.out.println("Starting the run");
    }


    public static void reset_counters()
    {
        System.out.println("Resetting counters");
        start_time = SystemClock.uptimeMillis();
        number_of_steps = 0L;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_main);
        this_obj = this;

        if(playlist.size()>0)
            VIDEO_ID = playlist.get(0);
        else
            VIDEO_ID = "pY9b6jgbNyc";

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);

        //////////////////////////////////////////////////////////////////////////////////////


        Spinner spinner = (Spinner) findViewById(R.id.spinner_nav);


        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Run");
        spinnerArray.add("Challenge");
        spinnerArray.add("Statistics");
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
                        intent =new Intent(this_obj.getApplicationContext(), ViewAllChallenges.class);
                        break;
                    case 2:
                        intent =new Intent(this_obj.getApplicationContext(), StatActivity.class);
                        break;
                    case 3:
                        intent =new Intent(this_obj.getApplicationContext(), PersonalInfoActivity.class);
                        break;
                    case 4:
                        if (LoginActivity.mGoogleApiClient.isConnected()) {
                            Plus.AccountApi.clearDefaultAccount(LoginActivity.mGoogleApiClient);
                            LoginActivity.mGoogleApiClient.disconnect();
                            System.err.println("LOG OUT ^^^^^^^^^^^^^^^^^^^^ SUCESS");
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

        //////////////////////////////////////////////////////////////////////////////////////

        myYouTubePlayerFragment = (YouTubePlayerFragment)getFragmentManager()
                .findFragmentById(R.id.youtubeplayerfragment);
        myYouTubePlayerFragment.initialize(DEVELOPER_KEY, this);


        fab = (TextView) findViewById(R.id.fab);
        bpm_label = (TextView) findViewById(R.id.BPM);
        timer_label = (TextView) findViewById(R.id.timer);

        timer_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message;
                if (is_running)
                {
                    timer_handler.removeCallbacks(update_timer);timer_handler.removeCallbacks(update_timer);
                    message = "Stopped the run.";
                    System.out.println("Stopped the run");
//                    fab.setImageResource(android.R.drawable.ic_media_play);
//                    fab.setText("Stop");
                    g_player.pause();
                    is_running = false;
//                    run_start = true;

                }
                else
                {
                    if(run_start_reset_flag) {
                        reset_counters();
                        run_start_reset_flag = false;
                    }
                    timer_handler.postDelayed(update_timer, 0);
                    message = "Started the run";
                    System.out.println("Started the run");
//                    number_of_steps_offset = number_of_steps;
//                    fab.setImageResource(android.R.drawable.ic_media_pause);
//                    fab.setText("Stop");
                    g_player.play();
                    is_running = true;
                }



            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               timer_handler.removeCallbacks(update_timer);
                String duration = ""+((SystemClock.uptimeMillis() - start_time)/1000);
                java.util.Date dt = new java.util.Date();
                java.text.SimpleDateFormat sdf =  new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = sdf.format(dt);
                String user_id = Plus.PeopleApi.getCurrentPerson(LoginActivity.mGoogleApiClient).getId();
                String steps = ""+(number_of_steps - number_of_steps_offset);

                reset_counters();
                start_run();


                new insertRunInfo().execute(user_id, currentTime, duration, steps);

               Intent intent =new Intent(getApplicationContext(), ChallengeNewSend.class);
                intent.putExtra("user_id", user_id );
                intent.putExtra("datetime", currentTime);
                intent.putExtra("distance", steps);
                intent.putExtra("duration", duration);
                startActivity(intent);

            }
        });


        num_steps_label = (TextView) findViewById(R.id.num_steps);
        next_song = (Button) findViewById(R.id.next_song);

        next_song.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                loadNextSong();
            }
        });
        sensor_manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        step_counter = sensor_manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        step_detector = sensor_manager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);




    }

    private class insertRunInfo extends AsyncTask<String, Void, HttpResponse> {

        @Override
        protected void onPostExecute(HttpResponse response) {

        }


        @Override
        protected HttpResponse doInBackground(String... args) {



            String user_id = args[0];
            String currentTime = args[1];
            String duration = args[2];
            String steps = args[3];

            try {
                user_id = URLEncoder.encode(user_id, "UTF-8");
                currentTime = URLEncoder.encode(currentTime,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String link = "http://beatmyrun.net16.net/insertRuninfo.php?user_id="+user_id+"&datetime="+currentTime+"&steps="+steps+"&duration="+duration;

            return connector.request(link);

        }

    }

    public void loadNextSong()
    {
        int index = (playlist.indexOf(VIDEO_ID)+1)%playlist.size();
        VIDEO_ID = playlist.get(index);
        System.out.println("Playing video "+index+" : "+VIDEO_ID);
//        g_player.cueVideo(VIDEO_ID);
//        g_player.play();
        last_stopped_time = SystemClock.uptimeMillis();
        g_player.loadVideo(VIDEO_ID);
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
                System.out.println("Calculating steps offset");
                number_of_steps_offset = value;
                run_start = false;
            }
            number_of_steps = value;
        } else if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
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
//            timer_label.setPadding(timer_label.getPaddingLeft(),25, timer_label.getPaddingRight(), timer_label.getPaddingBottom());
            timer_label.setText("" + mins + ":"  + String.format("%02d", secs) + ":" + String.format("%02d", milliseconds/10));

            long steps = (number_of_steps - number_of_steps_offset);
            if (steps<0) {
                steps = 0;
            }
            double bpm = ( steps/ (double)time_in_ms) * 1000 * 60;
            bpm_label.setText(String.format( "%.02f", bpm));
//            bpm_label.setText(number_of_steps_offset + " : " + number_of_steps);
            num_steps_label.setText(steps + "");

            timer_handler.postDelayed(this, 0);

        }
    };

    //###################################################################################


    @Override
    public void onInitializationFailure(Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer (%1$s)",
                    errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            g_player = player;
            player.setManageAudioFocus(false);
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
            player.setPlaybackEventListener(this_obj);
            player.cueVideo(VIDEO_ID);
//            player.loadVideo(VIDEO_ID);
//            player.pause();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(DEVELOPER_KEY, this);
        }
    }

    protected Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView)findViewById(R.id.youtubeplayerfragment);
    }


    @Override
    public void onPlaying() {
        System.out.println("Playing");
//        g_player.play();
    }

    @Override
    public void onPaused() {
        System.out.println("Paused");
//        g_player.pause();
    }

    @Override
    public void onStopped() {
        long current_time = SystemClock.uptimeMillis();
        if((current_time - last_stopped_time)/1000<3)
        {
            last_stopped_time = current_time;
            return;
        }
        last_stopped_time = current_time;
        System.out.println("Stopped");
        if(is_running) {
            loadNextSong();
        }
    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }
}
