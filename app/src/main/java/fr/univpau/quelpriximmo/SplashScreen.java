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
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import fr.univpau.quelpriximmo.utils.DataBaseHandler;
import fr.univpau.quelpriximmo.utils.GPSHandler;
import fr.univpau.quelpriximmo.utils.Variables;

public class SplashScreen extends AppCompatActivity {
    private GPSHandler gp;
    private Location loc;
    private JSONObject res;
    private URL url;
    private DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("SAH", "salut");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        getSupportActionBar().hide();
        gp = new GPSHandler();
        gp.handleGPS(this);
        db.openDatabase();
        db.flush();
        db.create();

            try{
                //URL de connection
                HttpURLConnection urlC = null;
                url = new URL("https://api.cquest.org/dvf?lat="+Double.toString(loc.getLatitude())+"&lon="+Double.toString(loc.getLongitude())+"&dist=2000"); //création de l'URL
                urlC = (HttpURLConnection) url.openConnection(); //démarrage de la connexion
                urlC.setRequestMethod("GET");   //type de méthde (ici on veu juste faire un get)
                urlC.setReadTimeout(10000); //timeout de la lecture
                urlC.setConnectTimeout(15000); //timeout de la connexion
                urlC.setDoOutput(true); //ça donne un résultat
                urlC.connect(); //on se connecte

                //préparation a la lecture de la sortie de la requete
                BufferedReader  br = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder sb =  new StringBuilder();
                String line;

                //tant que y'a des données
                while((line = br.readLine()) != null ){
                    sb.append(line = "\n"); //on concatène
                }
                br.close(); //on ferme le stream une fois la récupération du résultat fini;
                String jsonString = sb.toString(); //on convertis le tout en string
                res = new JSONObject(jsonString); //on instancie un JSON

                /*TODO: lire le JSON et pour chaque élément du tabeau de json insérer dans la base de données les données suivantes:
                    toutes les données qui nous intéresent sont contenues dans un grand tableau appelé "features", il faudra commencer par le récupérer. Chaque élément de ce tableau est un bien.
                    pour chaque élément de ce tableau appeller la fonction db.insert(String type_bien, int nb_pieces, double prix, String adresse, double longitude, double latitude, Location usrPos).
                    La grande majorité des éléments qui nous intéresse sont quand à eux contenus dans un sous objet de cet élement sous la clé "properties"
                    avec:
                       - type_bien le type de bien (contenu dans le json sous la clé "type_local", elle même contenue dans la partie "properties" de l'objet
                       - nb_pieces le nombre de pieces (contenu dans le json sous la clé "nombre_pieces_principales", elle même contenue dans la partie "properties" de l'objet
                       - prix le prix (sans dec') du lor, contenu dans le jron sous la clé "valeur_fonciere" (idem c'est dans "properties")
                       - adresse qui est la concaténationn des valeurs des clés suivantes (cotenues dans properties égelement) dans l'ordre que je t'ai donné:
                                - "numero_voie"
                                - "type_voie"
                                - "voie"
                                - "code_postal"
                                - "commune"
                       - longitude la longitude. la t'as le choix, soit tu récupère l'instance "geometry" de l'élément, puis coordinates et t'auras directement longitude et latitude, soit  tu vas dans properties et tu prend "lon"
                       - latitude la latitude. la t'as le choix, soit tu récupère l'instance "geometry" de l'élément, puis coordinates et t'auras directement longitude et latitude, soit  tu vas dans properties et tu prend "lat"
                       - usrPos, bah la t'as juste à faire passer en paramètre la localisation obtenue par le capteur GPS, la BDD se charge du reste
                   */
                JSONArray features = res.getJSONArray("features");
                for(int i = 0; i< features.length(); i++){
                    JSONObject elt = features.getJSONObject(i).getJSONObject("properties");
                    db.insert(elt.getString("type_local"), elt.getInt("nombre_pieces_principales"), elt.getDouble("valeur_fonciere"),
                            elt.getString("numero_voie")+", "+elt.getString("type_voie")+ " " +elt.getString("voie")+", "+elt.getString("code_postal")+
                            " "+elt.getString("commune"), elt.getDouble("lon"), elt.getDouble("lat"), gp.getLocation());
                }
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
                }, 5000);
            }

    }

    @Override
    protected void onResume(){
        super.onResume();
        gp.handleGPS(this);

    }

}
