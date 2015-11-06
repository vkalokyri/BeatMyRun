package edu.rutgers.cs.rahul.helloworld;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;

import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;
import edu.rutgers.cs.rahul.helloworld.PlayList;

public class RunActivity extends YouTubeBaseActivity implements SensorEventListener,YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaybackEventListener{

    private TextView timer_label, bpm_label, num_steps_label;
    private Button next_song;
    private SensorManager sensor_manager;
    private Sensor step_counter;
    private Sensor step_detector;
    private long start_time = 0L;
    private long number_of_steps = -1L;
    private long number_of_steps_offset = 0L;
    private boolean is_running = false;
    private Handler timer_handler = new Handler();
    private boolean run_start = true;
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





    private void reset_counters()
    {
        start_time = SystemClock.uptimeMillis();
        number_of_steps = 0L;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_main);

//        playlist.add("bHstrfWB-V0");
//        playlist.add("oFUYnR90c3s");
//        playlist.add("pY9b6jgbNyc");
//        playlist.add("GemKqzILV4w");
        VIDEO_ID = playlist.get(0);


        myYouTubePlayerFragment = (YouTubePlayerFragment)getFragmentManager()
                .findFragmentById(R.id.youtubeplayerfragment);
        myYouTubePlayerFragment.initialize(DEVELOPER_KEY, this);
        this_obj = this;

        fab = (TextView) findViewById(R.id.fab);
        bpm_label = (TextView) findViewById(R.id.BPM);
        timer_label = (TextView) findViewById(R.id.timer);

        timer_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message;
                if (is_running)
                {
                    timer_handler.removeCallbacks(update_timer);
                    message = "Stopped the run.";
                    System.out.println("Stopped the run");
//                    fab.setImageResource(android.R.drawable.ic_media_play);
//                    fab.setText("Stop");
                    g_player.pause();
                    is_running = false;
                    run_start = true;

                }
                else
                {
                    reset_counters();
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
               Intent intent =new Intent(getApplicationContext(), ChallengeActivity.class);
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
    public void onInitializationFailure(YouTubePlayer.Provider provider,
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

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
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
