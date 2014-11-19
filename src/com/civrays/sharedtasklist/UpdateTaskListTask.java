package com.civrays.sharedtasklist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/**
 *
 * @author Steve
 */
public class UpdateTaskListTask extends AsyncTask<Void, Void, String> {

    private HttpURLConnection connection;
    private final String api_url;
    private final Handler handler;
    
    /**
     * Constructor
     * @param api_url - the URL path for the API.
     * @param handler 
     */
    public UpdateTaskListTask(String api_url, Handler handler) {
        this.api_url = api_url;
        this.handler = handler;
        System.out.println("-- Created ASynchTask");
    }
    
    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL(api_url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "text/plain");
            connection.setReadTimeout(100);
            connection.setConnectTimeout(100);
            connection.connect();
            int statusCode = connection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                System.out.println("-- HTTP error" + Integer.toString(statusCode));
                return "Error: Failed getting tasks from API";
            }
            return readTextFromServer();
        } catch (Exception e) {
            System.out.println("-- ERROR: " + e.getClass());
            return "Error: " + e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    private String readTextFromServer() throws IOException {
        InputStreamReader isr = new InputStreamReader(connection.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line + "\n");
            line = br.readLine();
        }
        System.out.println("-- Text from server: " + sb.toString());
        return sb.toString();
    }
    
    @Override
    protected void onPostExecute(String updateNotice) {
        Message message = new Message();
        Bundle data = new Bundle();
        data.putString("json", updateNotice);
        message.setData(data);
        System.out.println("-- Sending message to handler");
        handler.sendMessage(message);
    }
}