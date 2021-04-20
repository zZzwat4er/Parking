package com.example.parking.utility.server_comunnication_api;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class HttpRequest extends Thread{

    private final URL mUrl;// request URL
    private final String mParams;// request Type (assigned automatically depends on constructor)
    private final Listener mListener;// class that must be implemented in order to create a request

    private static String response = "";

    //class that must be implemented in order to create a request
    //onRespond methods calls when request get answer
    public interface Listener{
        void onRespond(String respond);
    }

    public HttpRequest(URL url, String params, Listener listener){
        mUrl = url;
        mParams = params;
        mListener = listener;
    }

    @Override
    public void start(){ super.start(); }

    @Override
    public void run() {
        response = "";
        HttpURLConnection urlCon = null;
        try {
            //region connection setup
            urlCon = (HttpURLConnection) mUrl.openConnection();
            urlCon.setDoOutput(true);
            urlCon.setDoInput(true);
            urlCon.setRequestMethod("POST");
            //endregion

            //region sending data
            OutputStream writer = new BufferedOutputStream(urlCon.getOutputStream());

            writer.write(mParams.getBytes("ISO-8859-1"));

            writer.flush();
            writer.close();
            //endregion

            //region getting response data
            // code from stackoverflow so i don't know whether it fully and correctly works
            int responseCode = urlCon.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
            //endregion

        }
        // if some error has occurred return error massage
        catch (IOException e) {
            Listener listener;
            listener = mListener;
            if(listener != null) listener.onRespond(e.getMessage());
        } catch (Exception e) {
            Listener listener;
            listener = mListener;
            if(listener != null) listener.onRespond(e.getMessage());
        } finally {
            //close our connection and exit the thread
            try {urlCon.disconnect();}
            catch(NullPointerException e){return;}
            //region call a user def function that should be defined
            // code repeats some Volley request code but without some thread guard
            //todo: mb add some guard for response
            if(mListener != null) mListener.onRespond(response);
            response = "";
            //endregion
            return;
        }
    }
}
