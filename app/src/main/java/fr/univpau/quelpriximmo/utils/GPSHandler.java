package fr.univpau.quelpriximmo.utils;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import fr.univpau.quelpriximmo.R;

//tente de gérer le GPS
public class GPSHandler {
        private LocationManager lm = null;
        private String fournisseur;
        Location loc;

        //gère le GPS
        public void handleGPS(Context ctx) {
            if (lm == null) {
                lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

                //pour la liste des fournissetrs, lm.getAllProviders();
                fournisseur = "gps";


                if (fournisseur != null) {
                    //si t'as pas les pers => ouvre une popup pour te dire de les activer
                    //TODO: faire en sorte que le splashcreen s'arrête de charger tant qe t'as pas activé el bousain parce que la c'est pas le cas
                    if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        new AlertDialog.Builder(ctx).setMessage(R.string.gps_not_enabled).setPositiveButton(R.string.open_location_settings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ctx.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        }).setNegativeButton(R.string.cancel, null).show();
                        return;
                    }
                    //est censé te donner la localisation
                    loc = lm.getLastKnownLocation("gps");
                    Log.i("SAH", "Localisation: Longitde: " + Double.toString(loc.getLongitude()) + " Latitude: " + Double.toString(loc.getLatitude()));
                }
                Log.i("SAH", "je suis sorti du test");
            }
        }

        public Location getLocation(){
            //retourne la localisation de la personne, pouour récupérer longitude et latitude c'es loc.getLongitude() et loc.getLatitude()
            return loc;
        }
}


