package com.kareemwaleed.arxicttask.api_request;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kareem Waleed on 7/3/2017.
 */

public class Request extends AsyncTask<RequestParameters, Void, Object> {

    @Override
    protected Object doInBackground(RequestParameters... params) {
        String result = "";
        HttpURLConnection request = null;
        InputStream inputStream = null;
        /**
         * Tries to open the connection with the url provided and read the result
         */
        try {
            URL reqURL = new URL(params[0].getUrl());
            request = (HttpURLConnection) (reqURL.openConnection());
            request.addRequestProperty("content-Type", "application/x-www-form-urlencoded");
            request.setRequestMethod(params[0].getMethodType());
            request.setConnectTimeout(1000);
            request.connect();
            inputStream = request.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line ="";
            StringBuilder response = new StringBuilder();
            while((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            result = response.toString();
        } catch (IOException e) {
        }
        try {
            Object json = new JSONTokener(result).nextValue();
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {
                }
            }
            if (request != null) {
                request.disconnect();
            }
        }
        return null;
    }
}
