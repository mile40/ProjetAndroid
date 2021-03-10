package fr.univpau.quelpriximmo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.univpau.quelpriximmo.utils.HTTPRequestTask;
import fr.univpau.quelpriximmo.utils.DataBaseHandler;
import fr.univpau.quelpriximmo.utils.Variables;

public class SplashScreen extends AppCompatActivity {
    private JSONObject res;
    private DataBaseHandler db;
    private Location location;
    private LocationManager manager;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationClient;
    private HTTPRequestTask t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        getSupportActionBar().hide();
        db = new DataBaseHandler(this);
        db.openDatabase();
        db.flush();
        db.create();

        locationRequest = new LocationRequest();
        locationRequest.setInterval(30000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        SharedPreferences params = PreferenceManager.getDefaultSharedPreferences(this);
        Variables.distance = params.getInt("rayon_defaut", 500);

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateGPS();
    }


    protected void updateGPS() {
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.i("DEBUG_GPS", "GPS Désactivé");
            buildAlertNoGPS();
        } else if (!isNetworkConnected()) {
            Log.i("DEBUG_GPS", "Internet Desactivé");
            buildAlertNoInternet();
        } else {
            Log.i("DEBUG_GPS", "GPS Activé");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i("DEBUG_LOCATION", "Location no rights");
            }
            else{
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        Log.i("DEBUG_LOCATION", "LOCATION CHANGED");
                    }
                    @Override
                    public void onProviderEnabled(@NonNull String provider) {

                    }

                    @Override
                    public void onProviderDisabled(@NonNull String provider) {

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }
                });
                /*location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location != null){
                    Log.i("DEBUG_LOCATION", location.toString());
                }
                else{
                    Log.i("DEBUG_LOCATION", "Location null GPS");
                }
                while(location == null) {
                    location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                Log.i("DEBUG_LOCATION", location.toString());*/
                /*location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location != null){
                    Log.i("DEBUG_LOCATION", location.toString());
                }
                else{
                    Log.i("DEBUG_LOCATION", "Location null GPS");
                }*/
            }
            /*if(location == null){
                location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if(location != null){
                    Log.i("DEBUG_LOCATION", location.toString());
                }
                else{
                    Log.i("DEBUG_LOCATION", "Location null Network");
                }
            }*/
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        //Log.i("DEBUG_GPS", "Long : " + Double.toString(location.getLongitude()) + " Lat : " + Double.toString(location.getLatitude()) );
                        doHttpRequest(location);
                    }
                });
            }
            else{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 99);
                }
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 99:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    updateGPS();
                }
                else{
                    Log.i("DEBUG_GPS","Pas les permissions pour le GPS");
                    finish();
                }
                break;
        }
    }

    private void buildAlertNoInternet(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("L'application requiert une connection Internet pour continuer, Voulez-vous activer le Wifi ou les données mobile ?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void buildAlertNoGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("L'application requiert les coordonnées GPS de l'appreil pour continuer, Voulez-vous activer le GPS ?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void buildAlertNoRequest(int code) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("La requete n'a pas pu aboutir, veuillez réessayer ultérieurement (code: "+ code + ")")
                .setCancelable(true)
                .setPositiveButton("ok", (dialog, id) -> {
                    dialog.cancel();
                    finish();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    protected void doHttpRequest(Location l){
        t = new HTTPRequestTask(l);
        t.start();
        while( !t.isFinished() ){
            //Log.i("WAIT_THREAD","Wait");
        }
        if(t.getCode() != 200){
            buildAlertNoRequest(t.getCode());
        }else{
            try{


                JSONArray features = t.getRes().getJSONArray("features");
                String type_local;
                int nb_pieces;
                double prix, longitude, latitude;

                for(int i = 0; i< features.length(); i++){
                    JSONObject elt = features.getJSONObject(i).getJSONObject("properties");
                    Log.i("HTTP_RES",elt.toString());
                    if(elt.has("type_local")){
                        type_local = elt.getString("type_local");
                    }else{
                        type_local = "Inconnu";
                    }

                    if(elt.has("valeur_fonciere")){
                        prix = elt.getDouble("valeur_fonciere");
                    }else{
                        prix = -1;
                    }

                    if(elt.has("nombre_pieces_principales")) {
                        nb_pieces = elt.getInt("nombre_pieces_principales");
                    }else{
                        nb_pieces = -1;
                    }

                    StringBuilder builderAddress = new StringBuilder();

                    if(elt.has("numero_voie"))
                    {
                        builderAddress.append(elt.getString("numero_voie"));
                    }

                    if(elt.has("type_voie"))
                    {
                        builderAddress.append(" ");
                        builderAddress.append(elt.getString("type_voie"));
                    }

                    if(elt.has("voie"))
                    {
                        builderAddress.append(" ");
                        builderAddress.append(elt.getString("voie"));
                    }

                    if(elt.has("code_postal"))
                    {
                        builderAddress.append(", ");
                        builderAddress.append(elt.getString("code_postal"));
                    }

                    if(elt.has("commune"))
                    {
                        builderAddress.append(", ");
                        builderAddress.append(elt.getString("commune"));
                    }

                    if(type_local.equals("Appartement") || type_local.equals("Maison")){
                        Log.i("DEBUG_LIST","type = "+type_local);
                        db.insert(type_local, nb_pieces, prix,
                                builderAddress.toString(), elt.getDouble("lon"), elt.getDouble("lat"), l);
                    }

                    Log.i("HTTP_RES",elt.toString());
                }
            } catch (Exception e) {
                Log.e("HTTP_RES", Log.getStackTraceString(e));
            } finally {
                final Intent i = new Intent(SplashScreen.this, SearchActivity.class);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(i);
                        finish();
                    }
                }, 1000);
                Log.i("HTTP_RES","Requete HTTP reussie");
            }

        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
