package edu.rutgers.cs.rahul.helloworld;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.api.client.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by valia on 10/30/15.
 */
public class PersonalInfoActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<People.LoadPeopleResult> {

    private EditText emailField;
    private EditText usernameField;
    private EditText heightField;
    private EditText weightField;
    private EditText ageField;
    private Button submitBtnField;
    private ImageButton logoutId ;
    private ImageView picture;
    private String name=null;
    private String email=null;

    GoogleApiClient mGoogleApiClient;
    Person currentPerson =null;
    boolean mSignInClicked;
    private static HttpConnector connector=new HttpConnector();
    PersonalInfoActivity this_obj;


    private ImageView image;
    private Button uploadButton;
    private Bitmap bitmap;
    private Button selectImageButton;

    // number of images to select
    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);
        this_obj=this;

        mGoogleApiClient=LoginActivity.mGoogleApiClient;
        emailField = (EditText) findViewById(R.id.emailField);
        usernameField = (EditText) findViewById(R.id.usernameField);
        heightField = (EditText) findViewById(R.id.heightField);
        weightField = (EditText) findViewById(R.id.weightField);
        ageField = (EditText) findViewById(R.id.ageField);
        submitBtnField = (Button) findViewById(R.id.submit);
        logoutId = (ImageButton) findViewById(R.id.logoutId);
        picture = (ImageView) findViewById(R.id.personId);

        Intent i = getIntent();
        // Receiving the Data
        name = i.getStringExtra("username");
        email = i.getStringExtra("email");
        Log.e("Second Screen", name + "." + email);

        // Displaying Received data
        usernameField.setText(name);
        emailField.setText(email);


        currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        if (name==null){
            new getUser().execute();
        }


        Spinner spinner = (Spinner) findViewById(R.id.PerInfoSpinner_nav);


        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Personal Details");
        spinnerArray.add("Run");
        spinnerArray.add("Challenge");
        spinnerArray.add("Statistics");
        spinnerArray.add("Logout");


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
                        RunActivity.start_run();
                        break;
                    case 2:
                        intent =new Intent(this_obj.getApplicationContext(), ViewAllChallenges.class);
                        break;
                    case 3:
                        intent =new Intent(this_obj.getApplicationContext(), StatisticsActivity.class);
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


        picture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectImageFromGallery();
                new ImageUploadTask().execute();
            }

        });


        // Binding Click event to Button
        submitBtnField.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                String id = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient).getId();
                String height = heightField.getText().toString();
                String weight = weightField.getText().toString();
                String age = ageField.getText().toString();
                new updateUser().execute(id, name, email, height, weight, age);

                Intent nextScreen = new Intent(getApplicationContext(), RunActivity.class);
                RunActivity.start_run();
                //Sending data to another Activity
                startActivity(nextScreen);

            }
        });




        logoutId.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (mGoogleApiClient.isConnected()) {
                    Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                    mGoogleApiClient.disconnect();
                    System.err.println("LOG OUT ^^^^^^^^^^^^^^^^^^^^ SUCCESS");
                }
                Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(nextScreen);



            }
        });






        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            if (person.hasImage()) {

                Person.Image image = person.getImage();


                new AsyncTask<String, Void, Bitmap>() {

                    @Override
                    protected Bitmap doInBackground(String... params) {

                        try {
                            URL url = new URL(params[0]);
                            InputStream in = url.openStream();
                            return BitmapFactory.decodeStream(in);
                        } catch (Exception e) {
                        /* TODO log error */
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        ((ImageView)findViewById(R.id.personId)).setImageBitmap(bitmap);
                    }
                }.execute(image.getUrl());
            }
        }

    }



    private class updateUser extends AsyncTask<String, Void, HttpResponse> {

        @Override
        protected void onPostExecute(HttpResponse response) {
            System.out.println("Response: "+response.getStatusLine());
        }


        @Override
        protected HttpResponse doInBackground(String... args) {
            String name = null;
            String email=null;
            String id=null;
            String height =args[3];
            String weight=args[4];
            String age=args[5];

            try {
                id=URLEncoder.encode(args[0], "UTF-8");
                name = URLEncoder.encode(args[1], "UTF-8");
                email = URLEncoder.encode(args[2],"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String link = "http://beatmyrun.net16.net/updateUser.php?id="+id+"&name="+name+"&email="+email+"&height="+height+"&weight="+weight+"&age="+age+"";
            return connector.request(link);
        }

    }


    private class getUser extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String response) {
            if (response!=null) {

                String [] personalInfo = response.split(";");

                emailField.setText(personalInfo[2]);
                email=personalInfo[2];
                usernameField.setText(personalInfo[1]);
                name=personalInfo[1];
                if(!personalInfo[3].equals("0")){
                    heightField.setText(personalInfo[3]);
                }
                if(!personalInfo[4].equals("0")) {
                    weightField.setText(personalInfo[4]);
                }
                if(!personalInfo[5].equals("0")){
                    ageField.setText(personalInfo[5]);
                }
            }
        }


        @Override
        protected String doInBackground(String... args) {

            String link = "http://beatmyrun.net16.net/getUser.php?id="+currentPerson.getId()+"";
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
                return null;
            }else{
                System.err.println("The user exists in the db!");
                return response;
            }
        }
    }


    @Override
    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub
        mSignInClicked = false;

        // updateUI(true);
        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub
        mGoogleApiClient.connect();
        // updateUI(false);
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub

    }


    public void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE);
    }


    /**
     * Retrives the result returned from selecting image, by invoking the method
     * <code>selectImageFromGallery()</code>
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            decodeFile(picturePath);

        }
    }


    /** The method decodes the image file to avoid out of memory issues. Sets the
     * selected image in to the ImageView.
     *
     * @param filePath
     */
    public void decodeFile(String filePath) {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(filePath, o2);

        image.setImageBitmap(bitmap);
    }

    @Override
    public void onResult(People.LoadPeopleResult loadPeopleResult) {

    }


    class ImageUploadTask extends AsyncTask<Void, Void, String> {
        private String webAddressToPost = "http://your-website-here.com";

        // private ProgressDialog dialog;
        private ProgressDialog dialog = new ProgressDialog(PersonalInfoActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Uploading...");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpContext localContext = new BasicHttpContext();
                HttpPost httpPost = new HttpPost(webAddressToPost);

                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                byte[] data = bos.toByteArray();
                String file = Base64.encodeBase64String(data);

                entity.addPart("uploaded", new StringBody(file));
                //entity.addPart("someOtherStringToSend", new StringBody("your string here"));

                httpPost.setEntity(entity);
                HttpResponse response = httpClient.execute(httpPost,localContext);
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent(), "UTF-8"));

                String sResponse = reader.readLine();
                return sResponse;
            } catch (Exception e) {
                // something went wrong. connection with the server error
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), "file uploaded",Toast.LENGTH_LONG).show();
        }
    }


}

