package fr.univpau.quelpriximmo.utils;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPRequestTask extends Thread{

    protected URL url;
    protected Location loc;
    protected JSONObject res;
    protected boolean finished = false;

    public HTTPRequestTask(Location l){
        try{
            loc = new Location(l);
            url = new URL("https://api.cquest.org/dvf?lat=" + loc.getLatitude() + "&lon=" + loc.getLongitude() + "&dist=2000");
        }catch(Exception e){
            e.printStackTrace();
            Log.e("HTTP_RES", Log.getStackTraceString(e));
        }
    }

    public void run(){
        doHttpRequest();
    }

    protected void doHttpRequest() {
        try {
            finished = false;
            URL url = new URL("https://api.cquest.org/dvf"
                    + "?lat=" + loc.getLatitude()
                    + "&lon=" + loc.getLongitude()
                    + "&dist=2000");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // urlConnection.setReadTimeout(15000);
            // urlConnection.setConnectTimeout(15000);
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            StringBuilder sb = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(in),1000);
            for (String line = r.readLine(); line != null; line =r.readLine()){
                sb.append(line);
            }
            in.close();

            res = new JSONObject(sb.toString());
            finished = true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("HTTP_RES", Log.getStackTraceString(e));
        }
    }

    public JSONObject getRes(){
        return this.res;
    }

    public boolean isFinished() {
        return finished;
    }
}
