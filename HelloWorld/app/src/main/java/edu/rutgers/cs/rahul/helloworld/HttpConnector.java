package edu.rutgers.cs.rahul.helloworld;

/**
 * Created by Rahul on 25-11-2015.
 */

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class HttpConnector {

    String url;
    public HttpConnector(String url)
    {
        this.url = url;
    }

    public HttpConnector()
    {
        url = null;
    }

    public HttpResponse request()
    {
        if(url == null)
            System.out.println("URL not set");
        else
            return request(this.url);

        return null;
    }

    public HttpResponse request(String link) {
        try {
            URL url = new URL(link);

            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            System.out.println("LINK"+link);
            request.setURI(new URI(link));

            HttpResponse response = null;

            response = client.execute(request);
            return response;
        }catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }


    }

    public String requestContent()
    {
        return requestContent(this.url);
    }

    public String requestContent(String url)
    {
        try {
            HttpResponse response = request(url);
            response.getEntity().getContent();
            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";

            while ((line = in.readLine()) != null) {
                sb.append(line);
                break;
            }
            in.close();
            return sb.toString();
        }catch(Exception e)
        {
            return null;
        }
    }


}
