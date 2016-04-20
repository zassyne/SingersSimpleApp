package com.example.yassine.singers;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by yassine on 16.04.16.
 */
public class WebRequest {

    static String response = null;


    public WebRequest() {}

    public String makeWebRequest(String urlAddress) {

        URL url;
        String response = "";
        try {
            url = new URL(urlAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            if(connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while ((line = br.readLine()) != null) {
                    response += line;
                }

            }
            else
                Log.d("http", "http is not ok");
        } catch (MalformedURLException e) {
            Log.d("malformed", e.getMessage());
        } catch (IOException e) {
            Log.d("IOException", e.getMessage());
        }


        return response;
    }


}
