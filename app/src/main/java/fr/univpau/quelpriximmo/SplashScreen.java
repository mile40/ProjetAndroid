package fr.univpau.quelpriximmo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import fr.univpau.quelpriximmo.utils.Variables;

public class SplashScreen extends AppCompatActivity {
    private LocationManager lm = null;
    private String fournisseur;
    private Location loc;
    private JSONObject res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        getSupportActionBar().hide();
        if (lm == null) {
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria crit = new Criteria();
            crit.setAccuracy(Criteria.ACCURACY_FINE);
            crit.setAltitudeRequired(false);
            crit.setBearingRequired(false);
            crit.setSpeedRequired(false);

            fournisseur = lm.getBestProvider(crit, true);
            Log.i("Debug", "fournisseur: " + fournisseur);

            if (fournisseur != null) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    new AlertDialog.Builder(this).setMessage(R.string.gps_not_enabled).setPositiveButton(R.string.open_location_settings, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton(R.string.cancel, null).show();
                    return;
                }
                loc = lm.getLastKnownLocation(fournisseur);
                Log.i("Debug", "Localisation: Longitde: "+Double.toString(loc.getLongitude())+" Latitude: "+  Double.toString(loc.getLatitude()));
            }

            try{
                //connection à l'API
                HttpURLConnection urlC = null;
                URL url = new URL("https://api.cquest.org/dvf?lat="+Double.toString(loc.getLatitude())+"&lon="+Double.toString(loc.getLongitude())+"&dist=2000");
                urlC = (HttpURLConnection) url.openConnection();
                urlC.setRequestMethod("GET");
                urlC.setReadTimeout(10000);
                urlC.setConnectTimeout(15000);
                urlC.setDoOutput(true);
                urlC.connect();

                BufferedReader  br = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder sb =  new StringBuilder();
                String line;

                //tant que y'a des données
                while((line = br.readLine()) != null ){
                    sb.append(line = "\n");
                }
                br.close(); //on ferme le strema une fois la récupération du résultat fini;
                String jsonString = sb.toString();
                Log.i("Debug", "résulat de la requête: "+jsonString);
                res = new JSONObject(jsonString);


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                final Intent i = new Intent(SplashScreen.this, SearchActivity.class);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(i);
                        finish();
                    }
                },5000);
            }
        }

    }
}
