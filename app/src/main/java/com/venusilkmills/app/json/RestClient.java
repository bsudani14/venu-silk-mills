package com.venusilkmills.app.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;


public class RestClient {

    public enum RequestMethod {
        GET,
        POST
    }

    String Myurl = "";
    private String shorturl = com.venusilkmills.app.BuildConfig.API_BASE_URL;

    public RestClient(String url) {
        this.Myurl = shorturl + url;

    }

    public String GetRequestexecute() {
        String response = "";
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(Myurl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            if (urlConnection.getInputStream() != null) {

                InputStream in = urlConnection.getInputStream();
                response = convertStreamToString(in);
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace(); //If you want further info on failure...
            }
        }
        return response;
    }

    public String POST(String json) {
        InputStream inputStream = null;
        String result = "";
        try {

            URL url1 = new URL(Myurl);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            inputStream = conn.getInputStream();

            if (inputStream != null)
                result = convertStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
