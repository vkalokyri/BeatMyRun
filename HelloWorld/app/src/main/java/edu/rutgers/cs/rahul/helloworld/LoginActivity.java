package edu.rutgers.cs.rahul.helloworld;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Params;
import com.echonest.api.v4.Song;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Created by valia on 10/30/15.
 */
public class LoginActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = "LoginActivity";

    public static final String API_KEY = "AIzaSyDV8a8kz2I1lf1FwbaO7CFcdOfEScChYZ8";
    public static final String browser_API_KEY = "AIzaSyAKt7_kz7pK42CQs74WUD5dmpCSiVE94cQ";
    public static final String oauth_key = "661591512723-bm18diefo4qeltgsbp1j84qubvv17glt.apps.googleusercontent.com";
    public static String oauth_token;
    public static final String echonest_API_key = "TBFADK1MYMBWRRNHV";

    /* RequestCode for resolutions involving sign-in */
    private static final int RC_SIGN_IN = 1;

    /* RequestCode for resolutions to get GET_ACCOUNTS permission on M */
    private static final int RC_PERM_GET_ACCOUNTS = 2;
    static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
    static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1001;



    /* Keys for persisting instance variables in savedInstanceState */
    private static final String KEY_IS_RESOLVING = "is_resolving";
    private static final String KEY_SHOULD_RESOLVE = "should_resolve";

    /* Client for accessing Google APIs */
    public static GoogleApiClient mGoogleApiClient;
    String mEmail; // Received from newChooseAccountIntent(); passed to getToken()
    String SCOPE = "oauth2:https://www.googleapis.com/auth/youtube.force-ssl";

    private static HttpConnector connector=new HttpConnector();

    private static YouTube youtube;
    Person currentPerson=null;


    /* View to display current status (signed-in, signed-out, disconnected, etc) */
    private TextView mStatus;

    // [START resolution_variables]
    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;

    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;
    // [END resolution_variables]

    private PlayList staticProjectPlayList = new PlayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        ImageView image = (ImageView) findViewById(R.id.logoImage);
        System.out.println("Login On Create");
        // Restore from saved instance state
        // [START restore_saved_instance_state]
        if (savedInstanceState != null) {
            mIsResolving = savedInstanceState.getBoolean(KEY_IS_RESOLVING);
            mShouldResolve = savedInstanceState.getBoolean(KEY_SHOULD_RESOLVE);
        }
        // [END restore_saved_instance_state]

        // Set up button click listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        //findViewById(R.id.sign_out_button).setOnClickListener(this);
        //findViewById(R.id.disconnect_button).setOnClickListener(this);

        // Large sign-in
        ((SignInButton) findViewById(R.id.sign_in_button)).setSize(SignInButton.SIZE_WIDE);

        // Start with sign-in button disabled until sign-in either succeeds or fails
        findViewById(R.id.sign_in_button).setEnabled(true);

        // Set up view instances
        // mStatus = (TextView) findViewById(R.id.status);

        // [START create_google_api_client]
        // Build GoogleApiClient with access to basic profile
        if(mGoogleApiClient != null)
        {
            System.out.println("Not NULL Object");
            if(mGoogleApiClient.isConnected())
            {
                System.out.println("disconnect");
            }

        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .addScope(new Scope(Scopes.EMAIL))
                .addScope(new Scope(YouTubeScopes.YOUTUBE))
                .build();


        // [END create_google_api_client]
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            System.out.println("user is signed In");
            currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

            if (currentPerson != null) {
                // Show signed-in user's name
                String name = currentPerson.getDisplayName();
                System.out.println("USERNAME = " + name);
                //mStatus.setText(getString(R.string.signed_in_fmt, name));

                // Show users' email address (which requires GET_ACCOUNTS permission)
                if (checkAccountsPermission()) {
                    String currentAccount = Plus.AccountApi.getAccountName(mGoogleApiClient);
                    System.out.println("email = " + currentAccount);
                    this.mEmail = currentAccount;
                    //Sending data to another Activity

                   new insertUser().execute(name, currentAccount, currentPerson.getId());
                    AsyncTask<String, Void, String>  authTask = new RetrieveTokenTask().execute(currentAccount);

                }


            } else {
                System.out.println("THE USER IS NULL!!!!");
                // If getCurrentPerson returns null there is generally some error with the
                // configuration of the application (invalid Client ID, Plus API not enabled, etc).
                //Log.w(TAG, getString(R.string.error_null_person));
                //mStatus.setText(getString(R.string.signed_in_err));
            }

            // Set button visibility
            //findViewById(R.id.sign_in_button).setVisibility(View.GONE);

            // findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            System.out.println("user is not signed In");

            // Show signed-out message and clear email field
            //mStatus.setText(R.string.signed_out);
            //((TextView) findViewById(R.id.email)).setText("");

            // Set button visibility
            findViewById(R.id.sign_in_button).setEnabled(true);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            // findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }



    /**
     * Check if we have the GET_ACCOUNTS permission and request it if we do not.
     * @return true if we have the permission, false if we do not.
     */
    private boolean checkAccountsPermission() {
        final String perm = Manifest.permission.GET_ACCOUNTS;
        int permissionCheck = ContextCompat.checkSelfPermission(this, perm);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // We have the permission
            return true;
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
            // Need to show permission rationale, display a snackbar and then request
            // the permission again when the snackbar is dismissed.
            /*
            Snackbar.make(findViewById(R.id.main_layout),
                    R.string.contacts_permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Request the permission again.
                            ActivityCompat.requestPermissions(LoginActivity.this,
                                    new String[]{perm},
                                    RC_PERM_GET_ACCOUNTS);
                        }
                    }).show();*/
            return false;
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{perm},
                    RC_PERM_GET_ACCOUNTS);
            return false;
        }
    }

    private void showSignedInUI() {
        updateUI(true);
    }

    private void showSignedOutUI() {
        updateUI(false);
    }

    // [START on_start_on_stop]
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("ON START CONNECT");
//        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("ON STOP DISCONNECT");
//        mGoogleApiClient.disconnect();
    }
    // [END on_start_on_stop]

    // [START on_save_instance_state]
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IS_RESOLVING, mIsResolving);
        outState.putBoolean(KEY_SHOULD_RESOLVE, mShouldResolve);
    }
    // [END on_save_instance_state]

    // [START on_activity_result]

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);

        if (requestCode == RC_SIGN_IN) {
            // If the error resolution was not successful we should not resolve further.
            if (resultCode != RESULT_OK) {
                mShouldResolve = false;
            }

            mIsResolving = false;
            System.out.println("ON onActivityResult CONNECT");
            mGoogleApiClient.connect();
        }
    }

    // [END on_activity_result]

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult:" + requestCode);
        if (requestCode == RC_PERM_GET_ACCOUNTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                System.out.println("IM INSIDE  onRequestPermissionsResult");
                showSignedInUI();
            } else {
                Log.d(TAG, "GET_ACCOUNTS Permission Denied.");
            }
        }
    }

    // [START on_connected]
    @Override
    public void onConnected(Bundle bundle) {
        // onConnected indicates that an account was selected on the device, that the selected
        // account has granted any requested permissions to our app and that we were able to
        // establish a service connection to Google Play services.
        Log.d(TAG, "onConnected:" + bundle);
        mShouldResolve = false;
        System.out.println("IM INSIDE  onRequestPermissionsResult");


        // Show the signed-in UI
        showSignedInUI();
    }
    // [END on_connected]

    @Override
    public void onConnectionSuspended(int i) {
        // The connection to Google Play services was lost. The GoogleApiClient will automatically
        // attempt to re-connect. Any UI elements that depend on connection to Google APIs should
        // be hidden or disabled until onConnected is called again.
        Log.w(TAG, "onConnectionSuspended:" + i);
    }

    // [START on_connection_failed]
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e(TAG, "Could not resolve ConnectionResult.", e);
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                showErrorDialog(connectionResult);
            }
        } else {
            // Show the signed-out UI
            showSignedOutUI();
        }
    }
    // [END on_connection_failed]

    private void showErrorDialog(ConnectionResult connectionResult) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, RC_SIGN_IN,
                        new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                mShouldResolve = false;
                                showSignedOutUI();
                            }
                        }).show();
            } else {
                Log.w(TAG, "Google Play Services Error:" + connectionResult);
                String errorString = apiAvailability.getErrorString(resultCode);
                Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();

                mShouldResolve = false;
                showSignedOutUI();
            }
        }
    }

    // [START on_click]
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                System.out.println("ON sign in click");
                onSignInClicked();
                break;
            /*case R.id.sign_out_button:
                onSignOutClicked();
                break;
            case R.id.disconnect_button:
                onDisconnectClicked();
                break;*/
        }
    }
    // [END on_click]

    // [START on_sign_in_clicked]
    private void onSignInClicked() {
        // User clicked the sign-in button, so begin the sign-in process and automatically
        // attempt to resolve any errors that occur.
        mShouldResolve = true;
        System.out.println("onSignInClicked CONNECT");
        mGoogleApiClient.connect();

        // Show a message to the user that we are signing in.
        // mStatus.setText(R.string.signing_in);
    }
    // [END on_sign_in_clicked]

    // [START on_sign_out_clicked]
    public static void onSignOutClicked() {
        // Clear the default account so that GoogleApiClient will not automatically
        // connect in the future.
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }

        // showSignedOutUI();
    }
    // [END on_sign_out_clicked]

    // [START on_disconnect_clicked]
    private void onDisconnectClicked() {
        // Revoke all granted permissions and clear the default account.  The user will have
        // to pass the consent screen to sign in again.
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }

        showSignedOutUI();
    }


    private class GetYoutubeList extends AsyncTask<Void, Void, ArrayList<SongInfo>> {

        protected void onPostExecute(ArrayList<SongInfo> SongsFromEchonestList) {
           System.out.println("I finished with getting the liked videos");
           for (SongInfo resultSong: SongsFromEchonestList) {
               String youtube_id = resultSong.getYoutube_id();
               String song_title = resultSong.getTitle();
               String artist = resultSong.getArtist();
               double tempo = resultSong.getTempo();
               double duration = resultSong.getDuration();
               double liveness = resultSong.getLiveness();
               double energy = resultSong.getEnergy();
               double danceability = resultSong.getDanceability();
              /* System.out.println("Title=" + resultSong.getTitle());
               System.out.println("Artist=" + resultSong.getArtist());
               System.out.println("BPM=" + resultSong.getTempo());
               System.out.println("Duration=" + resultSong.getDuration());
               System.out.println("Liveness=" + resultSong.getLiveness());
               System.out.println("Energy=" + resultSong.getEnergy());
               System.out.println("Danceability=" + resultSong.getDanceability());
               System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");*/

               new insertSong().execute(youtube_id, song_title, artist, Double.toString(tempo), Double.toString(duration), Double.toString(liveness), Double.toString(energy), Double.toString(danceability));
               new insertPreference().execute(youtube_id, currentPerson.getId());
           }
        }

        @Override
        protected ArrayList<SongInfo> doInBackground(Void... params) {
            youtube = new YouTube.Builder(new NetHttpTransport(),
                    new JacksonFactory(), new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest hr) throws IOException {
                }
            }).setApplicationName("BeatMyRun").build();

            YouTube.Channels.List channelRequest = null;
            try {
                channelRequest = youtube.channels().list("contentDetails");
                System.out.println("oauth_token in getYoutubeList!!!!!!!!!!!!!!!!!!!!!!!!!" + oauth_token);
                channelRequest.setOauthToken(oauth_token);
                channelRequest.setMine(true);
                channelRequest.setKey(browser_API_KEY);
            } catch (IOException e) {
                e.printStackTrace();
            }


            // In the API response, only include channel information needed
            // for this use case.
            ChannelListResponse channelResult = null;
            try {
                channelResult = channelRequest.execute();
                System.out.println("channelResult"+channelResult);
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<Channel> channelsList = channelResult.getItems();

            String channelId = null;
            if (channelsList != null) {
                // The user's default channel is the first item in the list.
                Channel channel = channelsList.get(0);
                channelId = channel.getId();

                System.out.println("CHANNEL ID" + channelId);
                String likesId = channel.getContentDetails().getRelatedPlaylists().getLikes();
                String historyId = channel.getContentDetails().getRelatedPlaylists().getWatchHistory();
                System.out.println("Likes ID" + likesId);
                System.out.println("history ID" + historyId);


                // Define the API request for retrieving search results.
                /*YouTube.Playlists.List getPlaylistRequest = null;
                try {
                    getPlaylistRequest = youtube.playlists().list("id,status,snippet");
                    getPlaylistRequest.setOauthToken(oauth_token);
                    System.out.println("IN GET PLAYLISTS" + oauth_token);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getPlaylistRequest.setMine(true);
                getPlaylistRequest.setKey(browser_API_KEY);
                PlaylistListResponse playlistListResponse = null;
                System.out.println("IN GET PLAYLISTS request" + getPlaylistRequest);
                try {
                    playlistListResponse = getPlaylistRequest.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                List<Playlist> playLists = playlistListResponse.getItems();



                if (playLists != null) {

                    for (Playlist playList : playLists) {
                        String playlistId = playList.getId();
                        System.out.println("PlaylistId = " + playlistId);*/
                List<PlaylistItem> playlistItemList = new ArrayList<PlaylistItem>();
                YouTube.PlaylistItems.List playlistItemRequest = null;
                try {
                    playlistItemRequest = youtube.playlistItems().list("snippet");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                playlistItemRequest.setPlaylistId(likesId);
                playlistItemRequest.setKey(browser_API_KEY);
                playlistItemRequest.setOauthToken(oauth_token);
                PlaylistItemListResponse playlistListItemResponse = null;

                try {
                    playlistListItemResponse = playlistItemRequest.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                playlistItemList.addAll(playlistListItemResponse.getItems());

                String nextToken = playlistListItemResponse.getNextPageToken();

                // Call the API one or more times to retrieve all items in the
                // list. As long as the API response returns a nextPageToken,
                // there are still more items to retrieve.
                do {
                    playlistItemRequest.setPageToken(nextToken);
                    PlaylistItemListResponse playlistItemResult = null;
                    try {
                        playlistItemResult = playlistItemRequest.execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    playlistItemList.addAll(playlistItemResult.getItems());

                    nextToken = playlistItemResult.getNextPageToken();
                } while (nextToken != null);

                Iterator it = playlistItemList.iterator();
                EchoNestAPI en = new  EchoNestAPI(echonest_API_key);
                ArrayList<SongInfo> SongsFromEchonestList = new ArrayList<SongInfo>();

                while (it.hasNext()) {
                    PlaylistItem playlistItem = (PlaylistItem)it.next();
                    //System.out.println(" video name  = " + playlistItem.getSnippet().getResourceId().getVideoId());
                    //System.out.println("\n-------------------------------------------------------------\n");
                    //Updating the playlist item
                    //System.out.println(playlistItem.getSnippet().getDescription());
                    String youtube_id = playlistItem.getSnippet().getResourceId().getVideoId();
                    String title = playlistItem.getSnippet().getTitle();
                    System.out.println("Search for =" +title);
                    try {
                        Params p = new Params();
                        p.add("combined", title);
                        List<Song> songs = null;
                        songs = en.searchSongs(p);
                        if (songs.size()!=0) {
                            Song resultSong = songs.get(0);
                            String song_title = resultSong.getTitle();
                            String artist = resultSong.getArtistName();
                            double tempo = resultSong.getTempo();
                            double duration = resultSong.getDuration();
                            double liveness = resultSong.getLoudness();
                            double energy = resultSong.getEnergy();
                            double danceability = resultSong.getDanceability();
                            SongInfo song = new SongInfo(youtube_id,song_title, artist, tempo, duration, liveness, energy, danceability);
                            SongsFromEchonestList.add(song);
                        }
                    } catch (EchoNestException e) {
                        e.printStackTrace();
                    }
                    staticProjectPlayList.add(playlistItem.getSnippet().getResourceId().getVideoId(), 0, playlistItem);

                }

                // Prints information about the results.
                return SongsFromEchonestList;
            }

            return null;

        }
    }


    private class insertSong extends AsyncTask<String, Void, HttpResponse> {

        @Override
        protected void onPostExecute(HttpResponse response) {

        }


        @Override
        protected HttpResponse doInBackground(String... args) {
            String youtube_id = null;
            String title=null;
            String artist=null;
            double tempo = Double.valueOf(args[3]);
            double duration=Double.valueOf(args[4]);
            double liveness=Double.valueOf(args[5]);
            double energy=Double.valueOf(args[6]);
            double danceability=Double.valueOf(args[7]);

            try {
                youtube_id = URLEncoder.encode(args[0], "UTF-8");
                title = URLEncoder.encode(args[1],"UTF-8");
                artist = URLEncoder.encode(args[2],"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String link = "http://beatmyrun.net16.net/insertSong.php?id="+youtube_id+"&title="+title+"&artist="+artist+"&tempo="+Double.toString(tempo)+"&duration="+youtube_id+"&title="+title+"&artist="+artist+"&tempo="+Double.toString(tempo)+"&duration="+Double.toString(duration)+"&liveness="+Double.toString(liveness)+"&energy="+Double.toString(energy)+"&danceability="+Double.toString(danceability)+"";
            return connector.request(link);
        }

    }

    private class insertPreference extends AsyncTask<String, Void, HttpResponse> {

        @Override
        protected void onPostExecute(HttpResponse response) {

        }


        @Override
        protected HttpResponse doInBackground(String... args) {
            String youtube_id = null;
            String user_id=null;
            Timestamp timestamp = new Timestamp(new Date().getTime());
            String time=null;

            try {
                youtube_id = URLEncoder.encode(args[0], "UTF-8");
                user_id = URLEncoder.encode(args[1],"UTF-8");
                time = URLEncoder.encode(timestamp.toString(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String link = "http://beatmyrun.net16.net/insertPreference.php?youtube_id="+youtube_id+"&user_id="+user_id+"&timestamp="+time+"";
            Log.e("Insert Preference", link);
            return connector.request(link);
        }

    }


    private class insertUser extends AsyncTask<String, Void, HttpResponse> {

        @Override
        protected void onPostExecute(HttpResponse response) {
            if (response!=null) {
                HttpEntity entity = response.getEntity();

                StringBuilder sb = new StringBuilder();
                try {
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(entity.getContent()), 65728);
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String res = sb.toString();
                if (res.equalsIgnoreCase("success")) {
                    Intent nextScreen = new Intent(getApplicationContext(), PersonalInfoActivity.class);
                    nextScreen.putExtra("username", currentPerson.getDisplayName());
                    nextScreen.putExtra("email", Plus.AccountApi.getAccountName(mGoogleApiClient));
                    startActivity(nextScreen);
                } else if (res.startsWith("Error:")) {
                    Intent nextScreen = new Intent(getApplicationContext(), LandingPage.class);
//                    RunActivity.start_run();
                    //Sending data to another Activity
                    startActivity(nextScreen);
                }
            }else{
                //user exists in the db
                Intent nextScreen = new Intent(getApplicationContext(), LandingPage.class);
//                RunActivity.start_run();
                //Sending data to another Activity
                startActivity(nextScreen);
            }
        }


        @Override
        protected HttpResponse doInBackground(String... args) {
            String name = null;
            String email=null;
            String id=null;
            try {
                name = URLEncoder.encode(args[0], "UTF-8");
                email = URLEncoder.encode(args[1],"UTF-8");
                id = URLEncoder.encode(args[2],"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println("NAMEEEEEEEEEEEEE="+name);
            System.out.println("EMAILLLLL="+email);
            System.out.println("ID"+id);

            //check if the userAlreadyExists
            String link = "http://beatmyrun.net16.net/getUser.php?id="+id+"";
            HttpEntity entity = connector.request(link).getEntity();

            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(entity.getContent()), 65728);
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            catch (IOException e) { e.printStackTrace(); }
            catch (Exception e) { e.printStackTrace(); }

            String response=sb.toString();
            if (response.equalsIgnoreCase("0_Results")){
                System.err.println("The user doesn't exist in the database");
                link = "http://beatmyrun.net16.net/insertUser.php?name="+name+"&email="+email+"&id="+id+"The user exists in the db!";
            }
            return null;
        }
    }


    /**
     * This method is a hook for background threads and async tasks that need to
     * provide the user a response UI when an exception occurs.
     */

    private static final int REQ_SIGN_IN_REQUIRED = 55664;


    private class RetrieveTokenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String accountName = params[0];
            String scopes = "oauth2:https://www.googleapis.com/auth/youtube.force-ssl";
            String token = null;
            try {
                token = GoogleAuthUtil.getToken(getApplicationContext(), accountName, scopes);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            } catch (UserRecoverableAuthException e) {
                startActivityForResult(e.getIntent(), REQ_SIGN_IN_REQUIRED);
            } catch (GoogleAuthException e) {
                Log.e(TAG, e.getMessage());
            }
            return token;
        }

        @Override
        protected void onPostExecute(String s) {

            oauth_token=s;
            System.out.println("token in RetrieveTokenTask= "+s);
            new GetYoutubeList().execute();

            super.onPostExecute(s);

        }
    }



}
