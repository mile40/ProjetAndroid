package fr.univpau.quelpriximmo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import fr.univpau.quelpriximmo.Controlers.OnCLickHandler;
import fr.univpau.quelpriximmo.Controlers.SeekBarHandler;
import fr.univpau.quelpriximmo.Controlers.SpinnerHandler;

public class SearchActivity extends AppCompatActivity{
    private ImageButton btn_param;
    private SeekBar dist_recherche;
    private TextView tw;
    private Button btn_rech;
    private Spinner type_bien, nb_pieces;
    private SeekBarHandler sbh;
    private OnCLickHandler och, och2;
    private SpinnerHandler biensHandler, nbPiecesHandler;
    List<String> biens = new ArrayList<>();
    ArrayAdapter<String> biensAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchactivity);
        getSupportActionBar().hide();


        //récupération des éléments du layout nous intéressant
        btn_param =(ImageButton) findViewById(R.id.settingsButton);
        dist_recherche = (SeekBar) findViewById(R.id.slider_dist);
        tw = (TextView) findViewById(R.id.lab_dist_intersection);
        btn_rech = (Button) findViewById(R.id.btn_recherche);
        type_bien = (Spinner) findViewById(R.id.spin_type_biens);
        nb_pieces = (Spinner) findViewById(R.id.spinner_pieces);

        SharedPreferences params = PreferenceManager.getDefaultSharedPreferences(this);
        int rayon = params.getInt("rayon_defaut", 500);
        dist_recherche.setProgress( rayon );
        tw.setText("0 m - "+ rayon + " m");
        //affectation des éventuels éventHandlers


        sbh = new SeekBarHandler(dist_recherche, tw);
        och = new OnCLickHandler((View) btn_param);
        och2 = new OnCLickHandler((View) btn_rech);
        biensHandler = new SpinnerHandler((AdapterView) type_bien);
        nbPiecesHandler = new SpinnerHandler((AdapterView) nb_pieces);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences params = PreferenceManager.getDefaultSharedPreferences(this);
        int rayon = params.getInt("rayon_defaut", 500);
        dist_recherche.setProgress( rayon );
    }
}